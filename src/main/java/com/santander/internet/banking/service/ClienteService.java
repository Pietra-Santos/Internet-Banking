package com.santander.internet.banking.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.santander.internet.banking.entity.ClienteEntity;
import com.santander.internet.banking.entity.HistoricoClienteEntity;
import com.santander.internet.banking.exception.NaoEncontradoException;
import com.santander.internet.banking.factory.ClienteEntityFactory;
import com.santander.internet.banking.factory.vo.ClienteVOFactory;
import com.santander.internet.banking.model.ClienteVO;
import com.santander.internet.banking.repository.ClienteRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteService {
	
	private final  ClienteRepository clienteRepository; 
	
	@Transactional
	public ClienteVO salvarCliente(@Valid ClienteVO cliente) {
		
		ClienteEntity clienteEntity = ClienteEntityFactory.converterVOparaEntity(cliente);
		
		clienteEntity.setHistorico(new ArrayList<>());
		clienteEntity.getHistorico().add(criarHistorico(clienteEntity,clienteEntity.getSaldo(),new BigDecimal(0))); 
		
		clienteRepository.save(clienteEntity);
		
		return ClienteVOFactory.converterEntityparaVO(clienteEntity);
	}
	
	public ResponseEntity<?> depositarSaldo(Long idCliente, BigDecimal valor) {

		try {
			ClienteEntity cliente = clienteRepository.findById(idCliente).orElseThrow(() -> new NaoEncontradoException("Não encontrado"));
			
			BigDecimal saldo = cliente.getSaldo();
			
			BigDecimal soma = saldo.add(valor);
			cliente.setSaldo(soma);
			
			cliente.setHistorico(new ArrayList<>());
			cliente.getHistorico().add(criarHistorico(cliente, valor, new BigDecimal(0)));
			
			clienteRepository.save(cliente);
						
			return ResponseEntity.ok(ClienteVOFactory.converterEntityparaVO(cliente));
			
		} catch (NaoEncontradoException e) {
			Map<String, Object> errors = new HashMap<>();
			errors.put("message", e.getMessage());
			errors.put("time", LocalDateTime.now());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);

		}
		
	}
	
	public ResponseEntity<?> sacarSaldo(Long idCliente, BigDecimal valor) {
		
		try {
			ClienteEntity cliente = clienteRepository.findById(idCliente).orElseThrow(() -> new NaoEncontradoException("Não encontrado"));
			
			BigDecimal taxa = taxasDeSaque(valor,cliente.isPlanoExclusivo()); // 100 , 
			BigDecimal valorComTaxa = valor.add(taxa);
			
			BigDecimal saldo = cliente.getSaldo();
			
			BigDecimal sacarSaldo = saldo.subtract(valorComTaxa);
			
			BigDecimal valorArredondado = sacarSaldo.setScale(2, RoundingMode.HALF_DOWN);
			
			verificarSaldo(valorArredondado,saldo);
			
			cliente.setSaldo(valorArredondado);
			
			cliente.setHistorico(new ArrayList<>());
			cliente.getHistorico().add(criarHistorico(cliente, new BigDecimal(0), valor));
			
			clienteRepository.save(cliente);
						
			return ResponseEntity.ok(ClienteVOFactory.converterEntityparaVO(cliente));
			
		} catch (NaoEncontradoException e) {
			Map<String, Object> errors = new HashMap<>();
			errors.put("message", e.getMessage());
			errors.put("time", LocalDateTime.now());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);

		}
		
	}

	private void verificarSaldo(BigDecimal valorArredondado, BigDecimal saldo) throws NaoEncontradoException {
		if(valorArredondado.compareTo(new BigDecimal(0)) <= 0) {
			throw new  NaoEncontradoException("Voce Não tem saldo suficiente para sacar! Seu saldo é de : R$"+ saldo);
		}
		
	}

	private BigDecimal taxasDeSaque(BigDecimal valor, boolean planoExclusivo) {
		if (!planoExclusivo) {
			if (valor.compareTo(BigDecimal.valueOf(100.00)) > 0 && valor.compareTo(BigDecimal.valueOf(300.00)) <= 0) {

				BigDecimal taxaDesconto = new BigDecimal("0.4").divide(new BigDecimal("100.0"));

				BigDecimal desconto = valor.multiply(taxaDesconto);

				BigDecimal valorTotal = valor.subtract(desconto);

				return valor.subtract(valorTotal);

			} else if (valor.compareTo(BigDecimal.valueOf(300.00)) > 0) {

				BigDecimal taxaDesconto = new BigDecimal("1").divide(new BigDecimal("100.0"));

				BigDecimal desconto = valor.multiply(taxaDesconto);

				BigDecimal valorTotal = valor.subtract(desconto);

				return valor.subtract(valorTotal);

			}
		}

		return valor;
	}

	public Page<ClienteVO> recuperarClientes(Pageable pageable) {
		Page<ClienteEntity> clientes = clienteRepository.findAll(pageable);
		return ClienteVOFactory.converterListPageparaListVO(clientes);
	}
	
private HistoricoClienteEntity criarHistorico(ClienteEntity clienteEntity, BigDecimal deposito, BigDecimal saque ) {
		
	return HistoricoClienteEntity.builder()
		.cliente(clienteEntity)
		.data(LocalDate.now())
		.depositos(deposito)
		.saque(saque)
		.build();
		
		
	}
}

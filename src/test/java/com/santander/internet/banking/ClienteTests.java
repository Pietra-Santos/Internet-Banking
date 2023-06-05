package com.santander.internet.banking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.santander.internet.banking.entity.ClienteEntity;
import com.santander.internet.banking.model.ClienteVO;
import com.santander.internet.banking.repository.ClienteRepository;
import com.santander.internet.banking.service.ClienteService;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ClienteTests {

	@MockBean
	private ClienteRepository clienteRepository;

	@Autowired
	private ClienteService clienteService;

	int pageNumber = 0; // Número da página (começando em 0)
	int pageSize = 10; // Tamanho da página
	Sort sort = Sort.by(Sort.Direction.ASC, "nome"); // Ordenação por nome em ordem ascendente
	Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

	@Test
	@Order(1)
	void deveSalvarCliente() {

		ClienteVO cliente = getCliente();
		ClienteEntity entity = getClienteEntity();

		when(clienteService.salvarCliente(cliente)).thenReturn(cliente);
		when(clienteRepository.save(any())).thenReturn(entity);
		assertNotNull(cliente);

	}

	@Test
	@Order(2)
	void deveRecuperarTodosCliente() {

		ClienteEntity entity = getClienteEntity();

		List<ClienteEntity> listEntity = new ArrayList<>();
		listEntity.add(entity);

		Page<ClienteVO> clientes = getClientePage();

		Page<ClienteEntity> clientesPage = new PageImpl<>(listEntity, pageable, listEntity.size());

		when(clienteRepository.findAll(pageable)).thenReturn((clientesPage));
		when(clienteService.recuperarClientes(pageable)).thenReturn(clientes);
		assertNotNull(clientes);
	}
	
	@Test
	@Order(3)
	void deveDepositarSaldo() {

		 ClienteRepository clienteRepository = mock(ClienteRepository.class);
		    
		    // Criação do objeto a ser testado
		    ClienteService clienteService = new ClienteService(clienteRepository);
		    
		    // Configuração do comportamento esperado do ClienteRepository
		    ClienteEntity clienteMock = new ClienteEntity();
		    clienteMock.setId(1L);
		    clienteMock.setSaldo(BigDecimal.valueOf(1000));
		    when(clienteRepository.findById(1L)).thenReturn(Optional.of(clienteMock));
		    
		    // Execução do método a ser testado
		    ResponseEntity<?> responseEntity = clienteService.depositarSaldo(1L, BigDecimal.valueOf(100));
		    
		    // Verificação dos resultados
		    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		    assertNotNull(responseEntity.getBody());
		    assertTrue(responseEntity.getBody() instanceof ClienteVO);
		    
		    ClienteVO clienteVO = (ClienteVO) responseEntity.getBody();
		    assertEquals(1L, clienteVO.getId());
		    
		    // Verificação da chamada do método save() do ClienteRepository
		    verify(clienteRepository).save(any(ClienteEntity.class));
		    
	}
	
	@Test
	@Order(4)
	void deveSacarSaldo() {

	    ClienteRepository clienteRepository = mock(ClienteRepository.class);
	    
	    // Criação do objeto a ser testado
	    ClienteService clienteService = new ClienteService(clienteRepository);
	    
	    // Configuração do comportamento esperado do ClienteRepository
	    ClienteEntity clienteMock = new ClienteEntity();
	    clienteMock.setId(1L);
	    clienteMock.setSaldo(BigDecimal.valueOf(1000));
	    when(clienteRepository.findById(1L)).thenReturn(Optional.of(clienteMock));
	    
	    // Execução do método a ser testado
	    ResponseEntity<?> responseEntity = clienteService.sacarSaldo(1L, BigDecimal.valueOf(100));
	    
	    // Verificação dos resultados
	    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	    assertNotNull(responseEntity.getBody());
	    assertTrue(responseEntity.getBody() instanceof ClienteVO);
	    
	    ClienteVO clienteVO = (ClienteVO) responseEntity.getBody();
	    assertEquals(1L, clienteVO.getId());
	    
	    // Verificação da chamada do método save() do ClienteRepository
	    verify(clienteRepository).save(any(ClienteEntity.class));

	}

	private Page<ClienteVO> getClientePage() {

		List<ClienteVO> listEntity = new ArrayList<ClienteVO>();

		listEntity.add(getCliente());

		Page<ClienteVO> clientes = new PageImpl<>(listEntity, pageable, listEntity.size());

		return clientes;
	}
	
	


	private ClienteEntity getClienteEntity() {

		return ClienteEntity.builder().dataNascimento(LocalDate.now()).id(null).isPlanoExclusivo(false)
				.nome("Sr. Teste").numeroConta("000000001").saldo(BigDecimal.valueOf(120.000)).build();
	}

	private ClienteVO getCliente() {

		return ClienteVO.builder().dataNascimento(LocalDate.now()).id(null).isPlanoExclusivo(false).nome("Sr. Teste")
				.numeroConta("000000001").saldo(BigDecimal.valueOf(120.000)).build();

	}

}

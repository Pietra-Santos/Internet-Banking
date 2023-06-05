package com.santander.internet.banking.factory.vo;

import org.springframework.data.domain.Page;

import com.santander.internet.banking.entity.ClienteEntity;
import com.santander.internet.banking.model.ClienteVO;

public class ClienteVOFactory {

	public static ClienteVO converterEntityparaVO(ClienteEntity clienteEntity) {
		
		return ClienteVO.builder()
				.dataNascimento(clienteEntity.getDataNascimento())
				.id(clienteEntity.getId())
				.isPlanoExclusivo(clienteEntity.isPlanoExclusivo())
				.nome(clienteEntity.getNome())
				.numeroConta(clienteEntity.getNumeroConta())
				.saldo(clienteEntity.getSaldo())
				.build();
	}

	public static Page<ClienteVO> converterListPageparaListVO(Page<ClienteEntity> clientes) {
		if(!clientes.isEmpty()) {
			
			return clientes.map(ClienteVOFactory::converterEntityparaVO);
		}
		return null;
		
	}

}

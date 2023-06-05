package com.santander.internet.banking.factory;

import com.santander.internet.banking.entity.ClienteEntity;
import com.santander.internet.banking.model.ClienteVO;

import jakarta.validation.Valid;

public class ClienteEntityFactory {

	public static ClienteEntity converterVOparaEntity(@Valid ClienteVO cliente) {
		
		return ClienteEntity.builder()
				.dataNascimento(cliente.getDataNascimento())
				.isPlanoExclusivo(cliente.isPlanoExclusivo())
				.nome(cliente.getNome())
				.numeroConta(cliente.getNumeroConta())
				.saldo(cliente.getSaldo())
				.build();
	}

}

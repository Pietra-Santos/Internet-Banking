package com.santander.internet.banking.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.santander.internet.banking.entity.HistoricoClienteEntity;
import com.santander.internet.banking.model.HistoricoFactory;
import com.santander.internet.banking.model.HistoricoVO;
import com.santander.internet.banking.repository.HistoricoClienteRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HistoricoService {

	private final HistoricoClienteRepository historicoClienteRepository;

	public Page<HistoricoVO> recuperarHistoricosPorIdCliente(Long idCliente, Pageable pageable) {

		
		Page<HistoricoClienteEntity> historicoEntity = historicoClienteRepository.findAllByClienteId(idCliente, pageable);

		Page<HistoricoVO> converterEntityParaPage = HistoricoFactory.converterEntityParaPage(historicoEntity);
	
		return converterEntityParaPage;

	}

}

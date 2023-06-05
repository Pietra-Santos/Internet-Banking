package com.santander.internet.banking.model;

import org.springframework.data.domain.Page;

import com.santander.internet.banking.entity.HistoricoClienteEntity;

public class HistoricoFactory {
	

	public static HistoricoVO converterEntityParaVO(HistoricoClienteEntity historicoEntity) {
		
		if(historicoEntity != null) {
		
		 HistoricoVO build = HistoricoVO.builder()
				.data(historicoEntity.getData())
				.depositos(historicoEntity.getDepositos() != null ? historicoEntity.getDepositos() : null)
				.id(historicoEntity.getId())
				.saque(historicoEntity.getSaque() != null ? historicoEntity.getSaque() : null )
				.build();
		 
		 return build;
		}
		return null;
	}

	public static Page<HistoricoVO> converterEntityParaPage(Page<HistoricoClienteEntity> historicoEntity) {

			return historicoEntity.map(page -> converterEntityParaVO(page));

	}

}

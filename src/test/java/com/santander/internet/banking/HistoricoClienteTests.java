package com.santander.internet.banking;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

import com.santander.internet.banking.entity.HistoricoClienteEntity;
import com.santander.internet.banking.model.HistoricoVO;
import com.santander.internet.banking.repository.HistoricoClienteRepository;
import com.santander.internet.banking.service.HistoricoService;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class HistoricoClienteTests {

	@MockBean
	private HistoricoClienteRepository historicoClienteRepository;

	@Autowired
	public HistoricoService historicoService;

	int pageNumber = 0; // Número da página (começando em 0)
	int pageSize = 10; // Tamanho da página
	Sort sort = Sort.by(Sort.Direction.ASC, "id"); // Ordenação ascendente
	Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);



	@Test
	@Order(1)
	void deveRecuperarTodosCliente() {

		HistoricoClienteEntity entity = getHistoricoEntity();

		List<HistoricoClienteEntity> listEntity = new ArrayList<>();
		listEntity.add(entity);

		Page<HistoricoVO> historicos = getHistoricoPage();

		Page<HistoricoClienteEntity> historicosPage = new PageImpl<>(listEntity, pageable, listEntity.size());

		when(historicoClienteRepository.findAllByClienteId(1l ,pageable)).thenReturn((historicosPage));
		when(historicoService.recuperarHistoricosPorIdCliente(1l,pageable)).thenReturn(historicos);
		assertNotNull(historicos);
	}
	

	
	private Page<HistoricoVO> getHistoricoPage() {

		List<HistoricoVO> listEntity = new ArrayList<HistoricoVO>();

		listEntity.add(getHistorico());

		Page<HistoricoVO> clientes = new PageImpl<>(listEntity, pageable, listEntity.size());

		return clientes;
	}


	private HistoricoClienteEntity getHistoricoEntity() {

		return HistoricoClienteEntity.builder().data(LocalDate.now()).id(null)
				.depositos(BigDecimal.valueOf(120.000)).build();
	}

	private HistoricoVO getHistorico() {

		return HistoricoVO.builder().data(LocalDate.now()).id(null)
				.depositos(BigDecimal.valueOf(120.000)).build();

	}

}

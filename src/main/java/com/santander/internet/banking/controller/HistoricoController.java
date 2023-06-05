package com.santander.internet.banking.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.santander.internet.banking.model.HistoricoVO;
import com.santander.internet.banking.service.HistoricoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("historicos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class HistoricoController {
	
	public final  HistoricoService historicoService;
	
	@GetMapping("/{idCliente}")
	public Page<HistoricoVO> recuperarHistoricosPorIdCliente(@PathVariable Long idCliente, @PageableDefault(size = 20, page = 0) Pageable pageable){
		return historicoService.recuperarHistoricosPorIdCliente(idCliente,pageable);
	}

}

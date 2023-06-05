package com.santander.internet.banking.controller;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.santander.internet.banking.model.ClienteVO;
import com.santander.internet.banking.service.ClienteService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("cliente")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class ClienteController {
	
	private final ClienteService clienteService;
	
	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public ClienteVO salvarCliente(@Valid @RequestBody ClienteVO cliente) {
		return clienteService.salvarCliente(cliente);
	}
	
	@GetMapping
	public Page<ClienteVO> recuperarClientes(@PageableDefault(size = 20) Pageable pageable) {
		return clienteService.recuperarClientes(pageable);
	}
	
	@PutMapping("/depositar/{idCliente}/{valor}")
	public ResponseEntity<?> depositarSaldo(@PathVariable("idCliente") Long idCliente, @PathVariable("valor") BigDecimal valor) {
		return clienteService.depositarSaldo(idCliente,valor);
	}
	
	@PutMapping("/sacar/{idCliente}/{valor}")
	public ResponseEntity<?> sacarSaldo(@PathVariable("idCliente") Long idCliente, @PathVariable("valor") BigDecimal valor) {
		return clienteService.sacarSaldo(idCliente,valor);
	}
	

}

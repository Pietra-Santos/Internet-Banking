package com.santander.internet.banking.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ClienteVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String nome;
	
	private boolean isPlanoExclusivo;
	
	private BigDecimal saldo;
	
	@Size(max = 11,message = "maximo 11 numeros")
//	@Pattern(regexp = "^\\d{4} \\d{5} - \\d$")
	private String numeroConta;
	
	private LocalDate dataNascimento;
}

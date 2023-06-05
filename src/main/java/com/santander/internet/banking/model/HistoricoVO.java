package com.santander.internet.banking.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class HistoricoVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private LocalDate data;

	private BigDecimal saque;

	private BigDecimal depositos;


}

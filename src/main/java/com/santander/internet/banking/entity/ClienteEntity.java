package com.santander.internet.banking.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@Entity
@Builder
@Table(name = "TB_CLIENTE")
public class ClienteEntity  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "NOME")
	private String nome;
	
	@Column(name = "PLANO_EXCLUSIVO")
	private boolean isPlanoExclusivo;
	
	@Column(name = "SALDO")
	private BigDecimal saldo;
	
	@Column(name = "NUMERO_CONTA")
	private String numeroConta;
	
	@Column(name = "DATA_NASCIMENTO")
	private LocalDate dataNascimento;
	
	@OneToMany(fetch = FetchType.LAZY ,cascade = CascadeType.ALL,mappedBy = "cliente")
	private List<HistoricoClienteEntity> historico;
	
	

}

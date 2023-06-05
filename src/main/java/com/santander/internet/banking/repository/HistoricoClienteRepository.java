package com.santander.internet.banking.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.santander.internet.banking.entity.HistoricoClienteEntity;

public interface HistoricoClienteRepository extends JpaRepository<HistoricoClienteEntity, Long> {

	Page<HistoricoClienteEntity> findAllByClienteId(Long id, Pageable pageable);

}

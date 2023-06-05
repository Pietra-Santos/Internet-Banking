package com.santander.internet.banking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.santander.internet.banking.entity.ClienteEntity;

public interface ClienteRepository extends JpaRepository<ClienteEntity, Long> {

}

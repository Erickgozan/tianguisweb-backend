package com.tianguisweb.api.model.daos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tianguisweb.api.model.entities.Cliente;

public interface IClienteDao extends JpaRepository<Cliente, Long>{

}

package com.tianguisweb.api.model.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tianguisweb.api.model.entities.Cliente;

@Repository
public interface IClienteDao extends JpaRepository<Cliente, String>{

}

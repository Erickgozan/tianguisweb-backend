package com.tianguisweb.api.model.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tianguisweb.api.model.entities.Direccion;

@Repository
public interface IDireccionDao extends JpaRepository<Direccion, String>{
	
}

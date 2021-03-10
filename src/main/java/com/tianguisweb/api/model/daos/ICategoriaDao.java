package com.tianguisweb.api.model.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tianguisweb.api.model.entities.Categoria;

@Repository
public interface ICategoriaDao extends JpaRepository<Categoria, String> {
	
	//public List<Categoria> findByCategoria(Categoria categoria);
	
}

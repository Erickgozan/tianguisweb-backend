package com.tianguisweb.api.model.daos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tianguisweb.api.model.entities.Categoria;

public interface ICategoriaDao extends JpaRepository<Categoria, Long> {
	
	//public List<Categoria> findByCategoria(Categoria categoria);
	
}

package com.tianguisweb.api.model.daos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tianguisweb.api.model.entities.Categoria;
import com.tianguisweb.api.model.entities.Producto;

public interface IProductoDao extends JpaRepository<Producto, Long>{
		
	@Query("FROM Categoria")
	public List<Categoria> findAllCategorias();
	
	public List<Producto> findProductoByCategoria(Categoria categoria);
	
}

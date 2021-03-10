package com.tianguisweb.api.model.daos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tianguisweb.api.model.entities.Categoria;
import com.tianguisweb.api.model.entities.Producto;

@Repository
public interface IProductoDao extends JpaRepository<Producto, String>{
		
	@Query("FROM Categoria")
	public List<Categoria> findAllCategorias();
	
	public List<Producto> findProductoByCategoria(Categoria categoria);
	
}

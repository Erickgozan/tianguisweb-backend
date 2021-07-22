package com.tianguisweb.api.model.daos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tianguisweb.api.model.entities.Categoria;
import com.tianguisweb.api.model.entities.Producto;

@Repository
public interface IProductoDao extends JpaRepository<Producto, String>{
		
	@Query("FROM Categoria")
	 List<Categoria> findAllCategorias();

	 List<Producto> findByNombreContainingIgnoreCase(String nombre);

	@Query("FROM Producto p WHERE p.nombre like %?1% OR  p.caracteristicas like %?1% OR p.descripcion like %?1%")
	List<Producto> findProductosByDatos(String datos);

	List<Producto> findProductosByCategoria(Categoria categoria);

	List<Producto> findProductosByOferta(Boolean oferta);

	@Modifying
	@Query("UPDATE Producto p SET p.stock = :stock WHERE p.id = :id")
	 void updateStock(String id,Integer stock);

}

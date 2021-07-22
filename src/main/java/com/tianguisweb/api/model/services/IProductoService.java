package com.tianguisweb.api.model.services;

import java.util.List;

import com.tianguisweb.api.model.entities.Categoria;
import com.tianguisweb.api.model.entities.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IProductoService {

	// Retornar el listado completo de productos
	 List<Producto> findAllProductos();

	//Retornar el listado paginado de productos
     Page<Producto> findAllPageProductos(Pageable pagina);

	//Retornar el listado por nombre de producto
	 List<Producto> findAllProductsByNombre(String nombre);

	//Retornar el listado por nombre de producto
	List<Producto> findAllProductosByDatos(String datos);


	//Retorna los productos pertenecientes a cierta categoria
	 List<Producto> findProductosByCategoria(Categoria categoria);

	//Retorna los listados de productos que estan en oferta
	List<Producto> findProductosByOferta(Boolean oferta);
	
	//Retornar el listado de categorias
	 List<Categoria> finAllCategorias();

	// Retornar el producto por su id
	 Producto findProductoById(String id);

	// Guardar el producto
	 Producto saveProducto(Producto producto);

	// Eliminar el producto
	 void deleteProducto(Producto producto );
}

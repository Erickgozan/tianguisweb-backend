package com.tianguisweb.api.model.services;

import java.util.List;

import com.tianguisweb.api.model.entities.Categoria;
import com.tianguisweb.api.model.entities.Producto;

public interface IProductoService {

	// Retornar el listado completo de productos
	public List<Producto> findAllProductos();
	
	//Retornar el listado de categorias
	public List<Categoria> finAllCategorias();
	
	//Retorna los productos pertenecientes a cierta categoria
	public List<Producto> findProductoByCategoria(Categoria categoria);	

	// Retornar el producto por su id
	public Producto findProductoById(String id);

	// Guardar el producto
	public Producto saveProducto(Producto producto);

	// Eliminar el producto
	public void deleteProducto(Producto producto );
}

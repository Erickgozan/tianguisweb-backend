package com.tianguisweb.api.model.services;

import java.util.List;

import com.tianguisweb.api.model.entities.Producto;

public interface IProductoService {

	// Retornar el listado completo de productos
	public List<Producto> findAllProductos();

	// Retornar el producto por su id
	public Producto findProductoById(Long id);

	// Guardar el producto
	public Producto saveProducto(Producto producto);

	// Eliminar el producto
	public void deleteProducto(Producto producto );
}

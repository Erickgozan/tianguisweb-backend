package com.tianguisweb.api.model.servicesImpl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianguisweb.api.model.daos.IProductoDao;
import com.tianguisweb.api.model.entities.Categoria;
import com.tianguisweb.api.model.entities.Producto;
import com.tianguisweb.api.model.services.IProductoService;

@Service
@Transactional
public class ProductoServiceImpl implements IProductoService{
	
	//Inyecta la interfaz que tiene los metodos CRUD
	@Autowired
	private IProductoDao productoDao;
	
	//Retorna la lista de productos
	@Override
	public List<Producto> findAllProductos() {
		return productoDao.findAll();
	}
	
	//Retorna la lista de categorias
	@Override
	public List<Categoria> finAllCategorias() {
		return productoDao.findAllCategorias();
	}
	
	//Retorna la lista de productos que tengan la categoria en comun
	@Override
	public List<Producto> findProductoByCategoria(Categoria categoria) {
		return this.productoDao.findProductoByCategoria(categoria);
	}
	
	//Buscar el producto por su id
	@Override
	public Producto findProductoById(String id) {
		return this.productoDao.findById(id).orElse(null);
	}	

	//Guarda el producto
	@Override
	public Producto saveProducto(Producto producto) {
		return productoDao.save(producto);
	}

	//Elimina el producto
	@Override
	public void deleteProducto(Producto producto) {
		productoDao.delete(producto);
	}

		

	

}

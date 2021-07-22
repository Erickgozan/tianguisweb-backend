package com.tianguisweb.api.model.servicesImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tianguisweb.api.model.daos.IProductoDao;
import com.tianguisweb.api.model.entities.Categoria;
import com.tianguisweb.api.model.entities.Producto;
import com.tianguisweb.api.model.services.IProductoService;

@Transactional
@Service
public class ProductoServiceImpl implements IProductoService{
	
	//Inyecta la interfaz que tiene los metodos CRUD
	@Autowired
	private IProductoDao productoDao;
	
	//Retorna la lista de productos
	@Override
	public List<Producto> findAllProductos() {
		return productoDao.findAll();
	}

    //Retorna la lista paginada de productos
    @Override
    public Page<Producto> findAllPageProductos(Pageable pagina) {
        return this.productoDao.findAll(pagina);
    }
    //Retorna la lista de productos por nombre
	@Override
	public List<Producto> findAllProductsByNombre(String nombre) {
		return this.productoDao.findByNombreContainingIgnoreCase(nombre);
	}

	@Override
	public List<Producto> findAllProductosByDatos(String datos) {
		return this.productoDao.findProductosByDatos(datos);
	}

	//Retorna la lista de productos por categoria
	@Override
	public List<Producto> findProductosByCategoria(Categoria categoria) {
		return this.productoDao.findProductosByCategoria(categoria);
	}

	//Retorna las lista de productos en oferta
	@Override
	public List<Producto> findProductosByOferta(Boolean oferta) {
		return this.productoDao.findProductosByOferta(oferta);
	}

	//Retorna la lista de categorias
	@Override
	public List<Categoria> finAllCategorias() {
		return productoDao.findAllCategorias();
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

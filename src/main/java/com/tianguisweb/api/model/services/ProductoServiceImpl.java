package com.tianguisweb.api.model.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianguisweb.api.model.daos.IProductoDao;
import com.tianguisweb.api.model.entities.Producto;

@Service
@Transactional
public class ProductoServiceImpl implements IProductoService{
	
	@Autowired
	private IProductoDao productoDao;
	
	@Override
	public List<Producto> findAllProductos() {
		return productoDao.findAll();
	}

	@Override
	public Producto findProductoById(Long id) {
		return productoDao.findById(id).orElse(null);
	}

	@Override
	public Producto saveProducto(Producto producto) {
		return productoDao.save(producto);
	}

	@Override
	public void deleteProducto(Producto producto) {
		productoDao.delete(producto);
	}

}

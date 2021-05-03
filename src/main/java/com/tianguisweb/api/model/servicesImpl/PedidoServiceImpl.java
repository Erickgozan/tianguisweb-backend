package com.tianguisweb.api.model.servicesImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tianguisweb.api.model.daos.IPedidoDao;
import com.tianguisweb.api.model.daos.IProductoDao;
import com.tianguisweb.api.model.entities.Pedido;
import com.tianguisweb.api.model.entities.Producto;
import com.tianguisweb.api.model.services.IPedidoService;

@Transactional
@Service
public class PedidoServiceImpl implements IPedidoService {

	@Autowired
	private IPedidoDao pedidoDao;

	@Autowired
	private IProductoDao productoDao;

	// Retorna el listado de los pedidos
	@Override
	public List<Pedido> finadAllPedidos() {
		return this.pedidoDao.findAll();
	}

	// Guarda el pedido
	@Override
	public Pedido sevePedido(Pedido pedido) {
		this.actualizarProductoStok(pedido);
		return this.pedidoDao.save(pedido);
	}

	// Busca el pedido por su id
	@Override
	public Pedido findPedidoById(String id) {
		return this.pedidoDao.findById(id).orElse(null);
	}

	// Elimina el pedido
	@Override
	public void delatePedido(Pedido pedido) {
		this.pedidoDao.delete(pedido);
	}

	// Metodo para actualizar el stok
	private void actualizarProductoStok(Pedido pedido) {

		Producto producto = new Producto();

		pedido.getItemProductos().forEach(p -> {
			producto.setId(p.getProducto().getId());
			producto.setStock(p.getProducto().getStock() - p.getCantidad());
		});

		this.productoDao.updateStock(producto.getId(), producto.getStock());

	}

}

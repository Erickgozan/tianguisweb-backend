package com.tianguisweb.api.model.servicesImpl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianguisweb.api.model.daos.IPedidoDao;
import com.tianguisweb.api.model.entities.Pedido;
import com.tianguisweb.api.model.services.IPedidoService;

@Service
@Transactional
public class PedidoServiceImpl implements IPedidoService {
	
	@Autowired
	private IPedidoDao pedidoDao;
	
	//Retorna el listado de los pedidos
	@Override
	public List<Pedido> finadAllPedidos() {
		return this.pedidoDao.findAll();
	}

	//Guarda el pedido
	@Override
	public Pedido sevePedido(Pedido pedido) {
		return this.pedidoDao.save(pedido);
	}
	
	//Busca el pedido por su id
	@Override
	public Pedido findPedidoById(String id) {
		return this.pedidoDao.findById(id).orElse(null);
	}

	//Elimina el pedido
	@Override
	public void delatePedido(Pedido pedido) {
		this.pedidoDao.delete(pedido);
	}

}

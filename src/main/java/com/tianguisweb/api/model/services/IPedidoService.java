package com.tianguisweb.api.model.services;

import java.util.List;

import com.tianguisweb.api.model.entities.Pedido;

public interface IPedidoService {

	// Obtener todo los pedidos
	public List<Pedido> finadAllPedidos();

	// Guardar los pedidos
	public Pedido sevePedido(Pedido pedido);

	// Obtener el pedido por id
	public Pedido findPedidoById(String id);

	// Eliminar pedido
	public void delatePedido(Pedido pedido);

}

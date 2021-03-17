package com.tianguisweb.api.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tianguisweb.api.model.entities.Pedido;
import com.tianguisweb.api.model.services.IPedidoService;

@RestController
@CrossOrigin(origins = { "http://localhost:4200" })
@RequestMapping(value = "/api")
public class PedidoController {

	@Autowired
	private IPedidoService pedidoService;

	// Obtener el listado de pedidos
	@GetMapping("/pedidos")
	public List<Pedido> pedidosList() {
		return this.pedidoService.finadAllPedidos();
	}

	// Obtener el pedido por su id
	@GetMapping("/pedidos/{id}")
	public ResponseEntity<?> pedidoId(@PathVariable String id) {

		Pedido pedido = this.pedidoService.findPedidoById(id);
		Map<String, Object> response = new HashMap<String, Object>();

		if (pedido == null) {
			response.put("error_404", "El id " + id + "no se encontro en la base de datos");
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}
	
	//Guardar el pedido
	@PostMapping("/pedidos/create")
	public ResponseEntity<?> sevePedido(@RequestBody Pedido pedido){
		
		Pedido nuevoPedido = null;
		Map<String,Object> response = new HashMap<String, Object>();
		
		
		try {
			nuevoPedido = this.pedidoService.sevePedido(pedido);
		}catch(DataAccessException e) {		
			 response.put("error_500", "Hubo un error en el servidor: " + e.getLocalizedMessage());
			 return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("producto",nuevoPedido);
		response.put("mensaje","El pedido se ha guardado con éxito");
		
		return new ResponseEntity<>(response,HttpStatus.CREATED);
	}
	

}

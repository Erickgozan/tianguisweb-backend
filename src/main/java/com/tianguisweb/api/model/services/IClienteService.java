package com.tianguisweb.api.model.services;

import java.util.List;

import com.tianguisweb.api.model.entities.Cliente;

public interface IClienteService {
	
	//Retorna la lista de clientes
	public List<Cliente> findAll();
	
	//Crear al cliente
	public Cliente saveCliente(Cliente cliente);
	
	//Eliminar al cliente 
	public void deleteCliente(Cliente cliente);
	
	//Retonar un cliente por su id
	public Cliente findClienteById(Long id);

}

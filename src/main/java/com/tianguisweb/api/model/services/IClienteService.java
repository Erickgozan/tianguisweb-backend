package com.tianguisweb.api.model.services;

import java.util.List;

import com.tianguisweb.api.model.entities.Cliente;
import com.tianguisweb.api.model.entities.Usuario;

public interface IClienteService {
	
	//Retorna la lista de clientes
	List<Cliente> findAll();
	
	//Crear al cliente
	Cliente saveCliente(Cliente cliente);
	
	//Actualizar cliente
	Cliente updateCliente(Cliente cliente);

	//Actualizar la contraseña del cliente
	void updatePassword(String password, String id);
	
	//Eliminar al cliente 
	void deleteCliente(Cliente cliente);
	
	//Retonar un cliente por su id
	Cliente findClienteById(String id);
	
	//Buscar cliente por nombre de usuario
	Cliente findUsuarioByEmail(String email);
	
}

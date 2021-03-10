package com.tianguisweb.api.model.servicesImpl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianguisweb.api.model.daos.IClienteDao;
import com.tianguisweb.api.model.entities.Cliente;
import com.tianguisweb.api.model.services.IClienteService;

@Transactional
@Service
public class ClienteServiceImpl implements IClienteService {

	// Inyecta la interfaz que contiene el CRUD del cliente
	@Autowired
	private IClienteDao clienteDao;

	// Retorna la lista de clientes
	@Override
	public List<Cliente> findAll() {
		return this.clienteDao.findAll();
	}

	// Guarda-actualiza el cliente
	@Override
	public Cliente saveCliente(Cliente cliente) {
		return this.clienteDao.save(cliente);
	}

	// Elimina el cliente
	@Override
	public void deleteCliente(Cliente cliente) {
		this.clienteDao.delete(cliente);
	}

	// Retorna el cliente por su id
	@Override
	public Cliente findClienteById(String id) {
		return this.clienteDao.findById(id).orElse(null);
	}

}

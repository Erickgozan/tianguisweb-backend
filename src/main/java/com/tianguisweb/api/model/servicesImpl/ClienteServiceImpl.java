package com.tianguisweb.api.model.servicesImpl;

import java.util.List;

import com.tianguisweb.api.model.entities.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tianguisweb.api.model.daos.IClienteDao;
import com.tianguisweb.api.model.entities.Cliente;
import com.tianguisweb.api.model.services.IClienteService;

@Transactional
@Service
public class ClienteServiceImpl implements IClienteService {

	// Inyecta la interfaz que contiene el CRUD del cliente
	@Autowired
	private IClienteDao clienteDao;

	@Autowired
	private BCryptPasswordEncoder encoder;

	// Retorna la lista de clientes
	@Override
	public List<Cliente> findAll() {
		return this.clienteDao.findAll();
	}

	// Guarda-actualiza el cliente
	@Override
	public Cliente saveCliente(Cliente cliente) {
		cliente.setPassword(encoder.encode(cliente.getPassword()));
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

	@Override
	public Cliente findUsuarioByEmail(String email) {
		return this.clienteDao.findByEmail(email);
	}

	@Override
	public Cliente updateCliente(Cliente cliente) {
		return this.clienteDao.save(cliente);
	}

	@Override
	public void updatePassword(String password, String id) {
		 this.clienteDao.updateUsuarioPassword(encoder.encode(password),id);
	}

}

package com.tianguisweb.api.model.servicesImpl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tianguisweb.api.model.daos.IDireccionDao;
import com.tianguisweb.api.model.entities.Direccion;
import com.tianguisweb.api.model.services.IDireccionService;

@Transactional
@Service
public class DireccionServiceImpl implements IDireccionService {

	@Autowired
	private IDireccionDao direccionesDao;

	// Listar direcciones
	@Override
	public List<Direccion> findAllDirecciones() {
		return this.direccionesDao.findAll();
	}

	// Guardar la direccion
	@Override
	public Direccion saveDireccion(Direccion direccion) {
		return this.direccionesDao.save(direccion);
	}

	// Buscar la direccion por su id
	@Override
	public Direccion findDireccionById(String id) {
		return this.direccionesDao.findById(id).orElse(null);
	}

	// Eliminar la direccion
	@Override
	public void delateDireccion(Direccion direccion) {
		this.direccionesDao.delete(direccion);
	}

}

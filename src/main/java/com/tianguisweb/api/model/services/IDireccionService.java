package com.tianguisweb.api.model.services;

import java.util.List;

import com.tianguisweb.api.model.entities.Direccion;

public interface IDireccionService {

	// Listar direcciones
	public List<Direccion> findAllDirecciones();

	// Guardar/actualizar direcciones
	public Direccion saveDireccion(Direccion direccion);

	// Buscar direccion por id
	public Direccion findDireccionById(String id);

	// Eliminar direcciones
	public void delateDireccion(Direccion direccion);

}

package com.tianguisweb.api.model.services;

import java.util.List;

import com.tianguisweb.api.model.entities.Role;

public interface IRoleService {
	
	//Obtener el listado de roles
	public List<Role> findAllRoles();
	

}

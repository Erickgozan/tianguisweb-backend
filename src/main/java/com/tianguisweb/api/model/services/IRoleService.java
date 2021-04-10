package com.tianguisweb.api.model.services;

import java.util.List;

import com.tianguisweb.api.model.entities.Role;

public interface IRoleService {
	
	//Obtener el listado de roles
	public List<Role> findAllRoles();
	
	public Role findByRoleNombre(String nombre);
	
	public void saveRole(Role role);
	
	
	

}

package com.tianguisweb.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tianguisweb.api.model.entities.Role;
import com.tianguisweb.api.model.services.IRoleService;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class RoleController {
	
	
	@Autowired
	private IRoleService roleService;
	
	
	//Listar los roles
	@GetMapping("/roles")
	public List<Role> getRoles(){
		return this.roleService.findAllRoles();
	}
	
}

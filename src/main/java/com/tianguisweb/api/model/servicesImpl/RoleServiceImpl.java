package com.tianguisweb.api.model.servicesImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tianguisweb.api.model.daos.IRoleDao;
import com.tianguisweb.api.model.entities.Role;
import com.tianguisweb.api.model.services.IRoleService;

@Transactional
@Service
public class RoleServiceImpl implements IRoleService {
	
	
	@Autowired
	public IRoleDao roleDao;
	
	@Override
	public List<Role> findAllRoles() {
		// TODO Auto-generated method stub
		return this.roleDao.findAll();
	}

	@Override
	public Role findByRoleNombre(String nombre) {
		// TODO Auto-generated method stub
		return (Role) this.roleDao.findByRoleNombre(nombre);
	}

	@Override
	public void saveRole(Role role) {
		// TODO Auto-generated method stub
		this.roleDao.save(role);
	}

}

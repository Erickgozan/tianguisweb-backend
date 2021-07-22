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
		return this.roleDao.findAll();
	}

}

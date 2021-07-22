package com.tianguisweb.api.model.daos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tianguisweb.api.model.entities.Usuario;

public interface IUsuarioDao extends JpaRepository<Usuario, String>{
		
	 //Usuario findByUsername(String username);

	 Usuario findByEmail(String email);
}

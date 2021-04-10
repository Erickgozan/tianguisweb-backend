package com.tianguisweb.api.model.daos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tianguisweb.api.model.entities.Usuario;

public interface IUsuarioDao extends JpaRepository<Usuario, String>{
		
	public Usuario findByUsername(String username);
	
	public Optional<Usuario> findByEmail(String email);
	
}

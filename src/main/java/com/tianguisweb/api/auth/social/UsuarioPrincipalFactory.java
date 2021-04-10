package com.tianguisweb.api.auth.social;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.tianguisweb.api.model.entities.Usuario;

public class UsuarioPrincipalFactory {

	public static UsuarioPrincipal build(Usuario usuario) {
		List<GrantedAuthority> authorities 
			=usuario.getRoles().stream()
			.map(role -> new SimpleGrantedAuthority(role.getNombre()))
			.collect(Collectors.toList());
		
		return new UsuarioPrincipal(usuario.getEmail(), usuario.getPassword(), authorities);
	}
	
}

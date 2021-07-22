package com.tianguisweb.api.model.servicesImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tianguisweb.api.model.daos.IUsuarioDao;
import com.tianguisweb.api.model.entities.Usuario;

@Transactional
@Service
@Primary
public class UsuarioService implements UserDetailsService{

	private final static  Logger LOG = LoggerFactory.getLogger(UsuarioService.class); 
	
	@Autowired
	private IUsuarioDao usuarioDao;

	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		
		Usuario usuario = this.usuarioDao.findByEmail(email);
		
		if(usuario==null) {
			LOG.error("Error en el login no existe el usuario: "+email+" en el sistema");
			throw new UsernameNotFoundException("Error en el login no existe el usuario: "+email+" en el sistema");
		}
		
		List<GrantedAuthority> authorities = usuario.getRoles()
				.stream()
				.map(rol -> new SimpleGrantedAuthority(rol.getNombre()))
				.peek(authority -> LOG.info("Role: " + authority.getAuthority()))
				.collect(Collectors.toList());
		
		return new User(usuario.getEmail(), usuario.getPassword(),
				usuario.getHabilitado(), true, true, true, authorities);
	}
	
}

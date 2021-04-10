package com.tianguisweb.api.auth.social;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tianguisweb.api.model.daos.IUsuarioDao;
import com.tianguisweb.api.model.entities.Usuario;

@Service("useremail")
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private IUsuarioDao usuarioDao;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		Usuario usuario = this.usuarioDao.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("Email no encontrado"));

		return UsuarioPrincipalFactory.build(usuario);
	}
	
	public Usuario getByEmail(String email) {
		
		Usuario usuario = this.usuarioDao.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("Email no encontrado"));
		
		return usuario;
	}
	
	public boolean isExistEmail(String email) {
		Usuario usuario = this.usuarioDao.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("Email no encontrado"));
	
		if(usuario!=null) {
			return true;
		}
		
		return false;
	}
	
	

}

package com.tianguisweb.api.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import com.tianguisweb.api.model.entities.Cliente;
import com.tianguisweb.api.model.services.IClienteService;

//Clase que agrega mas informacion al token
@Component
public class InfoAdicionalToken implements TokenEnhancer{

	@Autowired
	private IClienteService clienteService;
	
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		
		Cliente usuario = this.clienteService.findUsuarioByUsername(authentication.getName());
		
		Map<String, Object> info = new HashMap<String, Object>();
				
		info.put("nombre", usuario.getNombre());
		info.put("apellido", usuario.getApellido());
		info.put("email", usuario.getEmail());
		info.put("id", usuario.getId());
		
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
		
		return accessToken;
	}
	
	

}

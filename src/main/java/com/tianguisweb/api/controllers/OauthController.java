package com.tianguisweb.api.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.tianguisweb.api.auth.social.UserDetailsServiceImpl;
import com.tianguisweb.api.auth.social.security.JwtProvider;
import com.tianguisweb.api.model.dto.TokenDto;
import com.tianguisweb.api.model.entities.Role;
import com.tianguisweb.api.model.entities.Usuario;
import com.tianguisweb.api.model.services.IRoleService;

@RestController
@CrossOrigin(origins = { "http://localhost:4200" })
@RequestMapping("/oauth")
public class OauthController {

	@Value("${google.clientId}")
	private String googleClientId;

	@Value("${jwt.secret}")
	private String secretPws;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JwtProvider jwtProvider;

	@Autowired
	UserDetailsServiceImpl usuarioService;

	@Autowired
	IRoleService roleService;

	@PostMapping("/google")
	public ResponseEntity<TokenDto> google(@RequestBody TokenDto token) throws IOException {
		final NetHttpTransport transport = new NetHttpTransport();
		final JacksonFactory jacksonFactory = JacksonFactory.getDefaultInstance();
		GoogleIdTokenVerifier.Builder verifier = new GoogleIdTokenVerifier.Builder(transport, jacksonFactory)
				.setAudience(Collections.singletonList(googleClientId));

		final GoogleIdToken googleIdToken = GoogleIdToken.parse(verifier.getJsonFactory(), token.getValue());
		final GoogleIdToken.Payload payload = googleIdToken.getPayload();

		Usuario usuario = new Usuario();

		if (usuarioService.isExistEmail(payload.getEmail()))
			usuario = usuarioService.getByEmail(payload.getEmail());

		else
			usuario = saveUsuario(payload.getEmail());

		TokenDto tokenRes = login(usuario);
		return new ResponseEntity<>(tokenRes, HttpStatus.OK);

	}

	@PostMapping("/facebook")
	public ResponseEntity<TokenDto> facebook(@RequestBody TokenDto token) throws IOException {

		Facebook facebook = new FacebookTemplate(token.getValue());
		final String[] fields = { "email", "picture" };
		User user = facebook.fetchObject("me", User.class, fields);
		Usuario usuario = new Usuario();

		if (usuarioService.isExistEmail(user.getEmail()))
			usuario = usuarioService.getByEmail(user.getEmail());

		else
			usuario = saveUsuario(user.getEmail());

		TokenDto tokenRes = login(usuario);
		return new ResponseEntity<>(tokenRes, HttpStatus.OK);
	}

	private TokenDto login(Usuario usuario) {

		Authentication authemtication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(usuario.getEmail(), secretPws));

		SecurityContextHolder.getContext().setAuthentication(authemtication);
		String jwt = jwtProvider.generateToken(authemtication);
		TokenDto tokenDto = new TokenDto();
		tokenDto.setValue(jwt);
		return tokenDto;
	}

	private Usuario saveUsuario(String email) {

		Usuario usuario = new Usuario();
		usuario.setEmail(email);
		usuario.setPassword(passwordEncoder.encode(secretPws));

		Role roleUSer = this.roleService.findByRoleNombre("ROLE_USER");
		List<Role> roles = new ArrayList<Role>();
		roles.add(roleUSer);
		usuario.setRoles(roles);
		return usuario;

	}
}

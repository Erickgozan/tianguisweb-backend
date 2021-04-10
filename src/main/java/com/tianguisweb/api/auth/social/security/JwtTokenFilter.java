package com.tianguisweb.api.auth.social.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import com.tianguisweb.api.auth.social.UserDetailsServiceImpl;

public class JwtTokenFilter extends OncePerRequestFilter {

	private final static Logger logger = LoggerFactory.getLogger(JwtTokenFilter.class);

	@Autowired
	JwtProvider provider;

	@Autowired
	@Qualifier("useremail")
	UserDetailsServiceImpl userDetails;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		try {
			String token = getToken(request);
			String email = provider.getEmailFromToken(token);
			UserDetails details = userDetails.loadUserByUsername(email);
			UsernamePasswordAuthenticationToken auth =
					new UsernamePasswordAuthenticationToken(email, null, details.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(auth);
		} catch (Exception e) {
			logger.error("fail en el metodo dofilter");
		}
		
		filterChain.doFilter(request, response);

	}

	private String getToken(HttpServletRequest req) {

		String authReq = req.getHeader("Authorization");
		if (authReq != null && authReq.startsWith("Bearer "))
			return authReq.replace("Bearer ", "");
		return null;

	}

}

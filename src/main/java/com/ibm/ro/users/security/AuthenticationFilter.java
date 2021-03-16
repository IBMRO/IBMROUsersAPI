package com.ibm.ro.users.security;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.ro.users.model.LoginReqModel;
import com.ibm.ro.users.service.UserService;
import com.ibm.ro.users.ui.dto.UserDTO;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	UserService usersService;
	Environment environment;

	public AuthenticationFilter(UserService usersService, Environment environment,
			AuthenticationManager authenticationManager) {
		this.usersService = usersService;
		this.environment = environment;
		super.setAuthenticationManager(authenticationManager);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		try {
			LoginReqModel creds = new ObjectMapper().readValue(request.getInputStream(), LoginReqModel.class);
			Authentication authenticate = getAuthenticationManager()
					.authenticate(new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getPassword()));
			return authenticate;
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	@Override
	public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		String userName = ((User) authResult.getPrincipal()).getUsername();

		StringBuffer authorities = new StringBuffer();
		authResult.getAuthorities().stream().forEach(auth -> authorities.append(auth.getAuthority() + ","));

		UserDTO userDto = usersService.getUserByEmail(userName);
		// hfgry463hf746hf573ydh475fhy5739
		String tokenSecret = environment.getProperty("token.secret");
		// Email is userid
		String token = Jwts.builder().setSubject(userDto.getEmail())
				.claim(SecurityConstants.AUTHORITIES, authorities)
				.setExpiration(new Date(
						System.currentTimeMillis() + Long.parseLong(environment.getProperty("token.expiration_time"))))
				.signWith(SignatureAlgorithm.HS512, tokenSecret).compact();

		response.addHeader("token", token);
		response.addHeader("userId", userDto.getEmail());
	}
}

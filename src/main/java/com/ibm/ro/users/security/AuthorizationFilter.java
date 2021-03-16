package com.ibm.ro.users.security;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;

public class AuthorizationFilter extends BasicAuthenticationFilter {

	Environment environment;

	public AuthorizationFilter(AuthenticationManager authenticationManager, Environment environment) {
		super(authenticationManager);
		this.environment = environment;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		System.out.println("URL: === " + req.getRequestURL());

		String authorizationHeader = req.getHeader(environment.getProperty("authorization.token.header.name"));

		if (authorizationHeader == null
				|| !authorizationHeader.startsWith(environment.getProperty("authorization.token.header.prefix"))) {
			chain.doFilter(req, res);
			return;
		}

		UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(req, res);
	}

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest req) {
		System.out.println("entering");
		String property = environment.getProperty("authorization.token.header.name");
		String authorizationHeader = req.getHeader(property);

		if (authorizationHeader == null) {
			return null;
		}

		String token = authorizationHeader.replace(environment.getProperty("authorization.token.header.prefix"), "");

		String userId = null;
		Claims claims = null;
		try {
			claims = Jwts.parser().setSigningKey(environment.getProperty("token.secret")).parseClaimsJws(token)
					.getBody();
			userId = Jwts.parser().setSigningKey(environment.getProperty("token.secret")).parseClaimsJws(token)
					.getBody().getSubject();
		} catch (ExpiredJwtException expiredJwtException) {
			return allowForRefreshToken(expiredJwtException, req);
		}
		if (userId == null) {
			return null;
		}

		final Collection<? extends GrantedAuthority> authorities = Arrays
				.stream(claims.get(SecurityConstants.AUTHORITIES).toString().split(","))
				.map(SimpleGrantedAuthority::new).collect(Collectors.toList());

		// Here userId is principle object can be used at method level security.
		// will be available in security expression as a principle object
		return new UsernamePasswordAuthenticationToken(userId, null, authorities);

	}

	private UsernamePasswordAuthenticationToken allowForRefreshToken(ExpiredJwtException ex,
			HttpServletRequest request) {

		// create a UsernamePasswordAuthenticationToken with null values.
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				null, null, null);
		// After setting the Authentication in the context, we specify
		// that the current user is authenticated. So it passes the
		// Spring Security Configurations successfully.
		// SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
		// Set the claims so that in controller we will be using it to create
		// new JWT
		request.setAttribute("claims", ex.getClaims());
		return usernamePasswordAuthenticationToken;

	}
}

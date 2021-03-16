package com.ibm.ro.users.security;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ibm.ro.users.service.UserService;

//This is for method level security
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

	Environment environment;
	UserService userService;
	BCryptPasswordEncoder brc;

	@Autowired
	public WebSecurity(Environment environment, UserService userService, BCryptPasswordEncoder brc) {
		super();
		this.environment = environment;
		this.userService = userService;
		this.brc = brc;
	}

	@Override
	public void configure(AuthenticationManagerBuilder authManagerBuilder) throws Exception {
		authManagerBuilder.userDetailsService(userService).passwordEncoder(brc);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		String apigatewayip = environment.getProperty("api.gateway.ip");

		// If you are only creating a service that is used by non-browser clients, you
		// will likely want to disable CSRF protection.
		http.csrf().disable();
		http.authorizeRequests().antMatchers(HttpMethod.POST, "/users").hasIpAddress(apigatewayip)
				.antMatchers("/users/registration").permitAll().anyRequest().authenticated().and()
				.addFilter(getAuthenticationFilter())
				.addFilter(new AuthorizationFilter(authenticationManager(), environment));
		http.headers().frameOptions().disable();
	}

	private Filter getAuthenticationFilter() throws Exception {
		AuthenticationFilter authenticationFilter = new AuthenticationFilter(userService, environment,
				super.authenticationManager());
		authenticationFilter.setFilterProcessesUrl(environment.getProperty("login.url.path"));
		return authenticationFilter;
	}

}

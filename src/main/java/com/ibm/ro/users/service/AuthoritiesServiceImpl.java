package com.ibm.ro.users.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.ibm.ro.users.model.Privilege;
import com.ibm.ro.users.model.UsersRoles;

@Service
public class AuthoritiesServiceImpl {

	public Collection<? extends GrantedAuthority> getRoles(Collection<UsersRoles> usersRoles) {

		List<GrantedAuthority> roles = new ArrayList<>();
		usersRoles.stream().forEach(userRole -> roles.add(new SimpleGrantedAuthority(userRole.getRole().getName())));
		return roles;
	}

	public Collection<? extends GrantedAuthority> getAuthorities(Collection<UsersRoles> usersRoles) {

		return getGrantedAuthorities(getPrivileges(usersRoles));
	}

	private List<String> getPrivileges(Collection<UsersRoles> usersRoles) {

		List<String> privileges = new ArrayList<>();
		List<Privilege> collection = new ArrayList<>();
		usersRoles.forEach(userRole -> collection.addAll(userRole.getRole().getPrivileges()));

		for (Privilege item : collection) {
			privileges.add(item.getName());
		}
		return privileges;
	}

	private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		for (String privilege : privileges) {
			authorities.add(new SimpleGrantedAuthority(privilege));
		}
		return authorities;
	}
}

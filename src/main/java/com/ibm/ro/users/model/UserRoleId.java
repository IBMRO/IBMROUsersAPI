package com.ibm.ro.users.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class UserRoleId implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long userId;
	private Long roleId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

//	@ManyToMany
//	@MapsId("id")
//	@JoinColumn(name = "id")
//	List<User> users;
//	@ManyToMany
//	@MapsId("id")
//	@JoinColumn(name = "id")
//	List<Role> roles;
//
//	public List<User> getUser() {
//		return users;
//	}
//
//	public void setUser(List<User> users) {
//		this.users = users;
//	}
//
//	public List<Role> getRole() {
//		return roles;
//	}
//
//	public void setRole(List<Role> roles) {
//		this.roles = roles;
//	}

}
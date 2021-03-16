package com.ibm.ro.users.ui.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserDTO {

	@JsonIgnore
	private Long id;
	private String name;
	private String password;
	private String email;

	public UserDTO() {
		super();
	}

	public UserDTO(String tcUserId, String name) {
		super();
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "UserDTO [id=" + id + ", name=" + name + ", password=" + password + "]";
	}

}

package com.ibm.ro.users.model;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Users") // , uniqueConstraints = @UniqueConstraint(columnNames = { "email" }))
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	private String encryptedPass;
	@Column(unique = true, nullable = false)
	private String email;

	@OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
	// @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id",
	// referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name =
	// "role_id", referencedColumnName = "id"))
	// @PrimaryKeyJoinColumn
	private Collection<UsersRoles> usersRoles = new HashSet<UsersRoles>(0);

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

	public String getEncryptedPass() {
		return encryptedPass;
	}

	public void setEncryptedPass(String password) {
		this.encryptedPass = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Collection<UsersRoles> getUsersRoles() {
		return usersRoles;
	}

	public void setUsersRoles(Collection<UsersRoles> usersRoles) {
		this.usersRoles = usersRoles;
	}

}

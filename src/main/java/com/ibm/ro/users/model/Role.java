package com.ibm.ro.users.model;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Roles")
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;

	@OneToMany(mappedBy = "role")
	private Collection<UsersRoles> usersRoles = new HashSet<UsersRoles>(0);

	@ManyToMany
	@JoinTable(name = "roles_privileges", joinColumns = @JoinColumn( name = "role_id", referencedColumnName = "id", nullable = false), inverseJoinColumns = @JoinColumn(name = "privilege_id", referencedColumnName = "id", nullable = false))
	private Collection<Privilege> privileges;

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

	public Collection<UsersRoles> getUsersRoles() {
		return usersRoles;
	}

	public void setUsersRoles(Collection<UsersRoles> usersRoles) {
		this.usersRoles = usersRoles;
	}

	public Collection<Privilege> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(Collection<Privilege> privileges) {
		this.privileges = privileges;
	}

}

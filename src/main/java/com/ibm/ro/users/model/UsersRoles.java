package com.ibm.ro.users.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name = "users_roles")
//@AssociationOverrides({ @AssociationOverride(name = "userRoleId.users", joinColumns = @JoinColumn(name = "id")),
//		@AssociationOverride(name = "userRoleId.roles", joinColumns = @JoinColumn(name = "id")) })
//https://thorben-janssen.com/hibernate-tip-many-to-many-association-with-additional-attributes/
public class UsersRoles {

	@EmbeddedId
	private UserRoleId userRoleId = new UserRoleId();
	@ManyToOne
	@MapsId("userId")
	private User user;
	@ManyToOne
	@MapsId("roleId")
	private Role role;
	private String appName;

	public UserRoleId getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(UserRoleId userRoleId) {
		this.userRoleId = userRoleId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

}

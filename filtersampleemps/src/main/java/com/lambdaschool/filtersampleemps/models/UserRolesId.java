package com.lambdaschool.filtersampleemps.models;


import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;


@Embeddable
public class UserRolesId implements Serializable {
	private long user;
	private long role;

	public UserRolesId() {}

	public long getUser() {
		return user;
	}

	public void setUser(long user) {
		this.user = user;
	}

	public long getRole() {
		return role;
	}

	public void setRole(long role) {
		this.role = role;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) { return true; }
		if (o == null || getClass() != o.getClass()) { return false; }
		UserRolesId that = (UserRolesId) o;
		return getUser() == that.getUser() && getRole() == that.getRole();
	}

	@Override
	public int hashCode() {
		return 23;
	}

}

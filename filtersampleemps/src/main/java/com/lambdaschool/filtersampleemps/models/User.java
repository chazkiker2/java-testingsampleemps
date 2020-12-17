package com.lambdaschool.filtersampleemps.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(name="users")
public class User extends Auditable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long userid;

	private String username;

	private String password;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnoreProperties(value = "user", allowSetters = true)
	private Set<UserRoles> roles = new HashSet<>();

	public User() {}

	public User(
			String username,
			String password
	) {
		setUsername(username);
		setPassword(password);
	}

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username.toLowerCase();
	}

	public String getPassword() {
		return password;
	}

	public void setPasswordNoEncrypt(String password) {
		this.password = password;
	}

	public void setPassword(String password) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		this.password = encoder.encode(password);
	}

	public Set<UserRoles> getRoles() {
		return roles;
	}

	public void setRoles(Set<UserRoles> roles) {
		this.roles = roles;
	}

	@JsonIgnore
	public List<SimpleGrantedAuthority> getAuthority() {
		List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
		for (UserRoles ur: this.roles) {
			String newRole = "ROLE_"+ur.getRole().getName().toUpperCase();
			authorityList.add(new SimpleGrantedAuthority(newRole));
		}
		return authorityList;
	}

}

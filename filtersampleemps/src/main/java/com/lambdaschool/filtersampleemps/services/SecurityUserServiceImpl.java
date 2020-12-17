package com.lambdaschool.filtersampleemps.services;


import com.lambdaschool.filtersampleemps.exceptions.ResourceNotFoundException;
import com.lambdaschool.filtersampleemps.models.User;
import com.lambdaschool.filtersampleemps.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service(value = "securityUserService")
public class SecurityUserServiceImpl
		implements UserDetailsService {

	private final UserRepo userRepo;

	@Autowired
	public SecurityUserServiceImpl(UserRepo userRepo) {
		this.userRepo = userRepo;
	}

	@Transactional
	@Override
	public UserDetails loadUserByUsername(String s)
	throws ResourceNotFoundException {
		User user = userRepo.findByUsername(s.toLowerCase());
		if (user == null) {
			throw new ResourceNotFoundException("Invalid username or password");
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(),
				user.getPassword(),
				user.getAuthority()
		);
	}

}

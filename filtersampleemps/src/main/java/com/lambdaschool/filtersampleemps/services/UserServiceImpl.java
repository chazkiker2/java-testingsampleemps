package com.lambdaschool.filtersampleemps.services;


import com.lambdaschool.filtersampleemps.exceptions.ResourceNotFoundException;
import com.lambdaschool.filtersampleemps.models.Role;
import com.lambdaschool.filtersampleemps.models.User;
import com.lambdaschool.filtersampleemps.models.UserRoles;
import com.lambdaschool.filtersampleemps.repos.RoleRepo;
import com.lambdaschool.filtersampleemps.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@Service(value = "userService")
public class UserServiceImpl
		implements UserService {

	private final UserRepo userRepo;

	private final RoleRepo roleRepo;

	@Autowired
	public UserServiceImpl(
			UserRepo userRepo,
			RoleRepo roleRepo
	) {
		this.userRepo = userRepo;
		this.roleRepo = roleRepo;
	}

	@Override
	public User findByName(String name) {
		User uu = userRepo.findByUsername(name.toLowerCase());
		if (uu == null) {
			throw new ResourceNotFoundException("User name " + name + " not found!");
		}
		return uu;
	}

	@Override
	public User save(User user) {
		User newUser = new User();

		if (user.getUserid() != 0) {
			userRepo.findById(user.getUserid())
					.orElseThrow(() -> new ResourceNotFoundException("User id " + user.getUserid() + " not found!"));
			newUser.setUserid(user.getUserid());
		}

		newUser.setUsername(user.getUsername()
				.toLowerCase());
		newUser.setPasswordNoEncrypt(user.getPassword());

		newUser.getRoles()
				.clear();
		for (UserRoles ur : user.getRoles()) {
			Role addRole = roleRepo.findById(ur.getRole()
					.getRoleid())
					.orElseThrow(() -> new ResourceNotFoundException("Role id " + ur.getRole()
							.getRoleid() + " not found!"));

			newUser.getRoles()
					.add(new UserRoles(newUser, addRole));
		}

		return userRepo.save(newUser);
	}

}


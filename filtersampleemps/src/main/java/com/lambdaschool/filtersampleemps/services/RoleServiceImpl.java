package com.lambdaschool.filtersampleemps.services;


import com.lambdaschool.filtersampleemps.exceptions.ResourceNotFoundException;
import com.lambdaschool.filtersampleemps.models.Role;
import com.lambdaschool.filtersampleemps.repos.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@Service(value = "roleService")
public class RoleServiceImpl
		implements RoleService {

	RoleRepo roleRepo;

	@Autowired
	public RoleServiceImpl(RoleRepo roleRepo) {
		this.roleRepo = roleRepo;
	}

	@Override
	public Role findByName(String name) {
		Role rr = roleRepo.findByName(name);

		if (rr != null) {
			return rr;
		} else {
			throw new ResourceNotFoundException(name);
		}
	}

}


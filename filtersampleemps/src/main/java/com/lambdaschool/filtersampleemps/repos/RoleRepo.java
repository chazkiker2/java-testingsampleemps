package com.lambdaschool.filtersampleemps.repos;


import com.lambdaschool.filtersampleemps.models.Role;
import org.springframework.data.repository.CrudRepository;


public interface RoleRepo
		extends CrudRepository<Role, Long> {

	Role findByName(String thename);

}

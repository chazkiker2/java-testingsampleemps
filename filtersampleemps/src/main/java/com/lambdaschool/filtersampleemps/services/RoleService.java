package com.lambdaschool.filtersampleemps.services;


import com.lambdaschool.filtersampleemps.models.Role;


public interface RoleService {

	Role findByName(String name);

}
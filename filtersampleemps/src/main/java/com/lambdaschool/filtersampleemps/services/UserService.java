package com.lambdaschool.filtersampleemps.services;


import com.lambdaschool.filtersampleemps.models.User;


public interface UserService {

	User findByName(String name);

	User save(User user);

}

package com.lambdaschool.filtersampleemps.repos;


import com.lambdaschool.filtersampleemps.models.User;
import org.springframework.data.repository.CrudRepository;


public interface UserRepo
		extends CrudRepository<User, Long> {

	User findByUsername(String username);

}

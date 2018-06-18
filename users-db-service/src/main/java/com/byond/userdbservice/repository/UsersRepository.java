package com.byond.userdbservice.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.byond.userdbservice.model.User;



public interface UsersRepository extends JpaRepository<User, Integer> {	

	
	public Integer findByUsername(String username);
}

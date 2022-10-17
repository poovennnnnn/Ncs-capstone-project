package com.banking.repo;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.banking.entity.User;



public interface UserRepo extends CrudRepository<User, Integer>{

	public Optional<User> findByUsername(String username);
	boolean existsByUsername(String username);
}

package com.banking.service;


import java.util.Optional;

import com.banking.entity.User;




public interface UserService {

	public User saveUser(User user);
	public Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    public void deleteById(int id);
}

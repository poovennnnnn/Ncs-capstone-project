package com.banking.service;


import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.banking.entity.User;
import com.banking.repo.RoleRepo;
import com.banking.repo.UserRepo;


@Service
public class UserServiceImpl implements UserService, UserDetailsService{

	@Autowired
	private UserRepo repo;
	
	@Autowired
	public RoleRepo roleRepo;
	

	
	@Override
	public User saveUser(User user) {
		
		user.setPassword(
				passwordEncoder().encode(user.getPassword())
						);
		return repo.save(user);
	}

	@Override
	public Optional<User> findByUsername(String username) {
		return repo.findByUsername(username);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> optionalUser = repo.findByUsername(username);
		
		// if user is not found throw exception
		if (!optionalUser.isPresent()) 
			throw new UsernameNotFoundException(username +" does not exist");	
		User user = optionalUser.get();

		//returning userdetailService Object
		return new org.springframework.security.core.userdetails.User(
				username,
				user.getPassword(),
				user.getRole().stream()
				.map(role->new SimpleGrantedAuthority(role.getRoleName()))
				.collect(Collectors.toList())
				)
				;
	}
	
	
	 public String getEncodedPassword(String password) {
	        return passwordEncoder().encode(password);
	    }

	
	 @Bean
	 public BCryptPasswordEncoder passwordEncoder() {
	 	return new BCryptPasswordEncoder();
	 }

    @Override
    public boolean existsByUsername(String username) {
        return repo.existsByUsername(username);
    }

    @Override
    public void deleteById(int id) {

        repo.deleteById(id);
    }
	 
}

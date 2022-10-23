package com.banking.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banking.dto.LoginRequest;
import com.banking.dto.UserResponse;
import com.banking.entity.User;
import com.banking.service.UserService;
import com.banking.util.JwtUtil;



@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserRestController {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService service;
	
	@Autowired
	private JwtUtil jwt;
	
	@PostMapping("/save")
	public ResponseEntity<User> saveUser(@RequestBody User user){
		System.out.println("user = " + user);
		User newUser= service.saveUser(user);
		
		return ResponseEntity.ok(newUser);
		 
	}
	
	// validate username and password against database, and then if valid, then
	// using that username as the "subject" of JWT token, generate the token
	
	@PostMapping("/authenticate")
	public ResponseEntity<UserResponse> loginUser(@RequestBody LoginRequest userRequest){
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						userRequest.getUsername(), userRequest.getPassword()));

		// validate user against database
		String token =jwt.generateToken(userRequest.getUsername());
        User user = service.findByUsername(userRequest.getUsername()).get();
        System.out.println(user.getBankAccount());
		
		return ResponseEntity.ok(new UserResponse(token, "Token created Successfully..",user));

	} 
	

	
	@GetMapping("/welcome")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> accessData1(Principal p) {
		return ResponseEntity.ok("Role admin=> " + p.getName());
	}
	
	@GetMapping("/greet")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<String> accessData2(Principal p) {
		return ResponseEntity.ok("Role user => " + p.getName());
	}
	
	@GetMapping("/greet1")
	public ResponseEntity<String> accessData3(Principal p) {
		return ResponseEntity.ok("Role user => " + p.getName());
	}
	
}

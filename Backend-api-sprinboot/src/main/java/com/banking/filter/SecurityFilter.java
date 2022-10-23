package com.banking.filter;


import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import com.banking.util.JwtUtil;

import io.jsonwebtoken.ExpiredJwtException;


@Component
public class SecurityFilter extends OncePerRequestFilter{

	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	
	@Override
	protected void doFilterInternal(
			HttpServletRequest request, 
			HttpServletResponse response, 
			FilterChain filterChain)
			throws ServletException, IOException {
		
		//1 read token from Auth header  from the rquest object
		String token = request.getHeader("Authorization");
		System.out.println(token);
		  if (token != null && token.startsWith("Bearer ")) {
			  token = token.substring(7);
	            try {
	              jwtUtil.getUsername(token);
	            } catch (IllegalArgumentException e) {
	                System.out.println("Unable to get JWT Token");
	            } catch (ExpiredJwtException e) {
	                System.out.println("JWT Token has expired");
	            }
	        } else {
	            System.out.println("JWT token does not start with Bearer");
	        }
		if(token!=null) {
			// do validation. get the username from the jwt that this token belongs to
			String username = jwtUtil.getUsername(token);
			System.out.println("token username=" + username + " " + username.length());
			
			// username should not be empty and SecurityContext.getAuthentication() must be empty
			if(username!=null &&
					SecurityContextHolder.getContext().getAuthentication()==null) {
				// now get the username from the database and compare whether
				// the username of the JWT token and the token username are same
				// in otherwords, check if the datbase contains the username that belongs to the JWT token
				UserDetails usr = userDetailsService.loadUserByUsername(username);	
				// now if both usernames are same, i.e if the username exists in the database
				// then check if the expiration time of the token for this token
				
				
				boolean isTokenValid = jwtUtil.validateToken(token, usr.getUsername());		
				System.out.println("is token valid = " + isTokenValid);
				// if token is valid, then create the Authentication object andplace it inside the SecurituContext				
				// to create an Authentication object, we need to create an object of UserNameAuthenticationToken
				// then attach it to the current HttpServletRequest request object				
				if(isTokenValid) {
					UsernamePasswordAuthenticationToken authToken= 
							new UsernamePasswordAuthenticationToken(
									username, 
									usr.getPassword(), 
									usr.getAuthorities());					
					// the following line of code will link the above created authentication token object with the
					// current request object
					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));					
					// now store the authentication inside the security context holder				
					 SecurityContextHolder.getContext().setAuthentication(authToken);			
				}
			}
		}
		 // if token is null this line will send the request as an invalid user, and there will 
		// be an unauthorized response
		filterChain.doFilter(request, response);
	}

}

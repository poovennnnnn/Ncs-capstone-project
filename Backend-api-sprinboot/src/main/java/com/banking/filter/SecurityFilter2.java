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
public class SecurityFilter2 extends OncePerRequestFilter{

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
		String token = request.getHeader("Authorization");
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
			String username = jwtUtil.getUsername(token);
			System.out.println("token username=" + username + " " + username.length());
			if(username!=null &&
					SecurityContextHolder.getContext().getAuthentication()==null) {
				UserDetails usr = userDetailsService.loadUserByUsername(username);	
				System.out.println("usr.getauthorities = " + usr.getAuthorities());
				System.out.println("usr username = "+ usr.getUsername());
				boolean isTokenValid = jwtUtil.validateToken(token, usr.getUsername());		
				System.out.println("is token valid = " + isTokenValid);
				if(isTokenValid) {
					UsernamePasswordAuthenticationToken authToken= 
							new UsernamePasswordAuthenticationToken(
									username, 
									usr.getPassword(), 
									usr.getAuthorities());					
					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));					
					 SecurityContextHolder.getContext().setAuthentication(authToken);			
				}
			}
		}
		filterChain.doFilter(request, response);
	}

}

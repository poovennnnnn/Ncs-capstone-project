package com.banking.util;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {
	
	  
	//6 validate username in token and database, as well as exp date
	public boolean validateToken(String token, String username) {
		String tokenUsername = getUsername(token);
		return (tokenUsername.equals(username) && !isTokenExpired(token));
	}
    	//5 validate token by checking whether the date is expired
	
	  public boolean isTokenExpired(String token) {
		  Date expDate = getExpirationDate(token);
		  return expDate.before(new Date(System.currentTimeMillis()));
		   // 4.45 .before(4.50)
	  }
	//4 get subject (subject means username, whoever is logged in) 
	public String getUsername(String token) {
		return getClaims(token).getSubject();
	}
	  // 3 get expiry date
	
	 public Date getExpirationDate(String token) {
		 return getClaims(token).getExpiration();
	 }
	// 2 read claims
	public Claims getClaims(String token) {
		System.out.println("token =>" + token);
		return Jwts.parser()
				.setSigningKey("a_very_long_secret_key")
				.parseClaimsJws(token)
				.getBody();
				
				
	}
	// 1 generate the token
	public String generateToken(String subject) {
		return Jwts.builder()
				.setSubject(subject)
				.setIssuer("POOVEN")
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(15)))
				.signWith(SignatureAlgorithm.HS512, "a_very_long_secret_key")
				.compact();
		
	}
}

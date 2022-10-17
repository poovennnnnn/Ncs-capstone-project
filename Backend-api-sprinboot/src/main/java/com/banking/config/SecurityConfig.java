package com.banking.config;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.banking.filter.InvalidUserAuthenticationEntryPoint;
import com.banking.filter.SecurityFilter;



@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private SecurityFilter securityFilter;
	
	@Autowired
	private InvalidUserAuthenticationEntryPoint authenticationEntryPoint;
	
	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManager();
	}
@Autowired
protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	auth
		.userDetailsService(userDetailsService)
		.passwordEncoder(passwordEncoder);
}

@Override
	protected void configure(HttpSecurity http) throws Exception {
	System.out.println("http security...");
		// TODO Auto-generated method stub
	
	http.cors();
		http
		.csrf().disable()
		.authorizeRequests()		
		.antMatchers("/user/save","/user/authenticate").permitAll()
		.anyRequest().authenticated()
		.and()
		.exceptionHandling()
		.authenticationEntryPoint(authenticationEntryPoint)
		.and()
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		//  verify user for 2nd request onwards
		.and()
		// the following line will register the filter mentioned and its functionality 
		// is useful (or executed) 2nd request onwards 
		.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);
}

}

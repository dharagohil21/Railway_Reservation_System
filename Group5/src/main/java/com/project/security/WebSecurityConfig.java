package com.project.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	SecurityAbstractFactory securityAbstractFactory = new SecurityConcreteFactory();
	AuthenticationSuccessHandler successHandler = securityAbstractFactory.createCustomeSuccessHandler();
	DaoAuthenticationProvider authProvider;
	
	@Bean
	public UserDetailsService userDetailsService()
	{
		return securityAbstractFactory.createUserDetailsService();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder()
	{ 
		return securityAbstractFactory.createPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationprovider()
	{
		authProvider = securityAbstractFactory.createAuthenticationprovider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception
	{ 
		auth.authenticationProvider(authenticationprovider());
	}
	
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception
	{
		http.authorizeRequests()
//		.antMatchers("/user/home").permitAll()
		.antMatchers("/signup").permitAll()
		.antMatchers("/admin*").hasAnyAuthority("ADMIN")
		.antMatchers("/user*").hasAnyAuthority("USER")
//		.antMatchers("/new").hasAnyAuthority("ADMIN", "CREATOR")
		.anyRequest().authenticated()
		.and()
		.formLogin().loginPage("/login").usernameParameter("userName").passwordParameter("password").successHandler(successHandler)
		.failureUrl("/login-error")
		.permitAll()
		.and().logout().permitAll()
		.and()
		.exceptionHandling().accessDeniedPage("/403");

		
		http.csrf().disable();
		http.headers().frameOptions().disable();
	}	
}

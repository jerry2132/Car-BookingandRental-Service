package com.example.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.example.service.UserService;



@Configuration
@EnableWebSecurity
public class SecurityConfig {

//	 @Autowired
//	 private UserDetailsServiceImpl userDetailsService;

	@Autowired
	private AuthenticationSuccessHandler successHandler;
	
	
	
	
		
		public SecurityConfig(AuthenticationSuccessHandler successHandler) {
		super();
		this.successHandler = successHandler;
	}

//
		@Bean
		public static PasswordEncoder passwordEncoder() {
			return new BCryptPasswordEncoder();
		}

		
//		@Bean
//		public AuthenticationProvider authenticationProvider() {
//		    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//		    provider.setUserDetailsService(customUserDetailsServiceImpl);
//		    provider.setPasswordEncoder(passwordEncoder()); // Make sure to provide your password encoder
//		    return provider;
//		}

		
		
//		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		    auth.userDetailsService(userDetailsService);
//		}

		
		@Bean
		public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
			
			http.csrf().disable().authorizeHttpRequests().requestMatchers("/user/**").hasRole("USER").
			requestMatchers("/admin/**").hasRole("ADMIN").
			requestMatchers("/login","/signup","/static/**").permitAll()
			.requestMatchers("/**").permitAll().
			and().formLogin().loginPage("/login").successHandler(successHandler).permitAll()
		
			.and().logout().logoutUrl("/logout").permitAll();
			
			return http.build();
		}
		
//		 protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		        auth.inMemoryAuthentication();
//		    }
//		
	}
	//.failureUrl("/login?error")   // requestMatchers("/admin/**").hasRole("ADMIN").
//.requestMatchers("/user/**").hasRole("USER").
//requestMatchers("/admin/**").hasRole("ADMIN").
/*
		http.csrf().disable().authorizeHttpRequests().
			requestMatchers("/login","/signup","/static/**").permitAll()
			.requestMatchers("/**").permitAll().
			and().formLogin().loginPage("/login").successHandler(successHandler).permitAll()
			.failureUrl("/login?error")

			.and().logout().logoutUrl("/logout").permitAll();
			
			return http.build();
		}
 */
	

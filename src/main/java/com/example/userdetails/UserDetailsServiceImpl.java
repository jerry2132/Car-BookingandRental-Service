package com.example.userdetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.Entity.User;
import com.example.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	
	@Autowired
	private  UserRepository userRepository;
	
	
	
	@Autowired
	public UserDetailsServiceImpl(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}




	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		User user = userRepository.findByEmail(username);
	
		
		if( user == null) {
			
			System.out.println("not found "+username);
			throw new UsernameNotFoundException("notRegistered 	"+username);
		}
//		
		UserDetailsImpl userDetailsImpl = new UserDetailsImpl(user);
		
		System.out.println(userDetailsImpl.getUsername());
		
		return userDetailsImpl;
		
	}

}

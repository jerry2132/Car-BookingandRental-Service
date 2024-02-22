package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.Entity.User;
import com.example.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public User save(User user) {
		// TODO Auto-generated method stub
		
		String encoder = passwordEncoder.encode(user.getPassword());
		user.setPassword(encoder);
		user.setRole("ROLE_USER");
		return userRepository.save(user);
	}

}

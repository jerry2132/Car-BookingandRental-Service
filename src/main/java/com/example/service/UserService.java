package com.example.service;

import org.springframework.stereotype.Service;

import com.example.Entity.User;

@Service
public interface UserService {

	User save(User user);
	
}

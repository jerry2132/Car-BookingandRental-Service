package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Entity.ForgotPassword;

public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword , Long>{

	ForgotPassword findByToken(String token);
	
}

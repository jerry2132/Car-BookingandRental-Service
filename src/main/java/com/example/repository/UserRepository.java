package com.example.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Entity.User;


@Repository
public interface UserRepository extends JpaRepository<User,Long>{


	User findByEmail(String email);
	
}

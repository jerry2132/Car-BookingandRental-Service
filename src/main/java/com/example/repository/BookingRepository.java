package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Entity.MyBooking;

public interface BookingRepository extends JpaRepository<MyBooking , Long>{

	  List<MyBooking> findByUserId(Long userId);
	
}

package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Entity.Vehicle;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle , Long>{
	

}

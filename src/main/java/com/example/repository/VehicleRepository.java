package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.Entity.Vehicle;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle , Long>{
	
	@Query("select p from Vehicle p where "+"CONCAT(p.brand, p.name, p.color, p.type, p.price, p.transmission)" + " LIKE %?1%")
	public List<Vehicle> findAll(String keyword);

}

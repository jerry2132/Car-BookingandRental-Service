package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.Entity.Vehicle;

@Service
public interface VehicleService {
	
public String save(Vehicle vehicle,MultipartFile file,Model model);

public List<Vehicle> getAllVehicles();

public void deleteVehicle(Long vehicleId);

}

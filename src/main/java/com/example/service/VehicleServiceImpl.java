package com.example.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.Entity.User;
import com.example.Entity.Vehicle;
import com.example.repository.VehicleRepository;


@Service
public class VehicleServiceImpl implements VehicleService{

	
	@Autowired
	private VehicleRepository vehicleRepository;
	
	@Autowired
	private User user;
	
	
	
	
	public VehicleServiceImpl(VehicleRepository vehicleRepository) {
		super();
		this.vehicleRepository = vehicleRepository;
	}




	@Override
	public String save(Vehicle vehicle,MultipartFile file,Model model) {
		// TODO Auto-generated method stub
		
		String formattedDateTime = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss").format(LocalDateTime.now());
        String uniqueFilename = formattedDateTime + "_" + file.getOriginalFilename();
        
   
        if (file.isEmpty()) {
            model.addAttribute("errorMessage", "Image is empty, cannot save.");
            // Return the view where you want to show the error message
            return "redirect:/admin/addVehicle";
        }
        
     

        	
        	
        	vehicle.setImage(uniqueFilename);
        	
        	try {
        	String uploadDirectory;

				if (System.getProperty("user.dir").contains("Intellij Projects")) {
				    uploadDirectory = "src/main/resources/static/image/";
				}
				else {
					ClassPathResource classPathResource = new ClassPathResource("static/image/");
					uploadDirectory = classPathResource.getFile().getAbsolutePath();
				}
				
				Path path = Paths.get(uploadDirectory, uniqueFilename);
				Files.createDirectories(path.getParent());
				//System.out.println(path.toAbsolutePath());
				Files.copy(file.getInputStream(), path , StandardCopyOption.REPLACE_EXISTING);
				//vehicle.setUser(user);
				 vehicleRepository.save(vehicle);
				 model.addAttribute("successMessage", "Vehicle saved successfully");
				 
				 return "redirect:/admin/addVehicle";
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				
				model.addAttribute("errorMessage", "unable to save user"+e.getMessage());
				e.printStackTrace();
				
				 return "redirect:/admin/addVehicle";
			}

		
	}




	@Override
	public List<Vehicle> getAllVehicles() {
		// TODO Auto-generated method stub
		return vehicleRepository.findAll();
	}


}

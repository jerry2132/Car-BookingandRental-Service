package com.example.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatcher;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.example.Entity.User;
import com.example.Entity.Vehicle;
//import com.example.dto.SearchVehicleDto;
//import com.example.dto.VehicleDtoList;
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




	@Override
	public void deleteVehicle(Long vehicleId) {
		// TODO Auto-generated method stub
		
		
			// TODO Auto-generated method stub

			Optional<Vehicle> vehicleOptional = vehicleRepository.findById(vehicleId);

			if (vehicleOptional.isPresent()) {
				Vehicle vehicle = vehicleOptional.get();

				String imagePath =vehicle.getImage();
				System.out.println(imagePath);
				vehicle.setUser(null);

				vehicleRepository.save(vehicle);

				vehicleRepository.deleteById(vehicleId);

				if (imagePath != null && !imagePath.isEmpty()) {

					deleteImageFile(imagePath);
				}
			}
		
	}




	public void deleteImageFile(String imagePath) {
		// TODO Auto-generated method stub
		
		if(imagePath == null) {
			return;
		}
		
		try {
		Path imageFilePath = Paths.get("src/main/resources/static/image/", imagePath);
		Files.deleteIfExists(imageFilePath);
		System.out.println("File deleted" + imageFilePath);
		}catch(IOException e) {
			
			System.err.println("error deleting file " + e);
		}
		
		
	}




//	@Override
//	public VehicleDtoList searchVehicle(SearchVehicleDto searchVehicleDto) {
//		// TODO Auto-generated method stub
//		
//		Vehicle vehicle = new Vehicle();
//		
//		vehicle.setBrand(searchVehicleDto.getBrand());
//		vehicle.setType(searchVehicleDto.getType());
//		vehicle.setColor(searchVehicleDto.getColor());
//		vehicle.setTransmission(searchVehicleDto.getTransmission());
//		
////		ExampleMatcher exampleMatcher;
////		
////			 exampleMatcher = 
////					ExampleMatcher.matchingAll()
////					.withMatcher("brand", ExampleMatcher.GenericPropertyMatcher.contains().ignoreCase())
////					.withMatcher("type", ExampleMatcher.GenericPropertyMatcher.contains().ignoreCase())
////					.withMatcher("color", ExampleMatcher.GenericPropertyMatcher.contains().ignoreCase())
////					.withMatcher("transmission", ExampleMatcher.GenericPropertyMatcher.contains().ignoreCase());
////			
////		
//	
//		ExampleMatcher.GenericPropertyMatcher propertyMatcher = new GenericPropertyMatcher();
//
//		ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll()
//		    .withMatcher("brand", propertyMatcher.contains().ignoreCase())
//		    .withMatcher("type", propertyMatcher.contains().ignoreCase())
//		    .withMatcher("color", propertyMatcher.contains().ignoreCase())
//		    .withMatcher("transmission", propertyMatcher.contains().ignoreCase());
//
//
//			
//		Example<Vehicle> vehicleExample = Example.of(vehicle,exampleMatcher);
//		
//		List<Vehicle> vehicleList = vehicleRepository.findAll(vehicleExample);
//		
//		VehicleDtoList vehicleDtoList = new VehicleDtoList();
//		
//		vehicleDtoList.setVehicleDtoList(vehicleList.stream().map(Vehicle :: getVehicleDto).collect(Collectors.toList()));
//		
//		
//		return vehicleDtoList;
//	}
//	
	

	public List<Vehicle> listAll(String keyword){

		if(keyword != null && !keyword.isEmpty()) {
			
			return vehicleRepository.findAll("%" + keyword + "%");
		}
		return vehicleRepository.findAll();
	}

}

package com.example.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.Entity.MyBooking;
import com.example.Entity.User;
import com.example.Entity.Vehicle;
//import com.example.dto.SearchVehicleDto;
//import com.example.dto.VehicleDtoList;
import com.example.repository.VehicleRepository;
import com.example.service.BookingService;
import com.example.service.UserService;
import com.example.service.VehicleService;
import com.example.service.VehicleServiceImpl;
import com.example.userdetails.UserDetailsServiceImpl;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private  UserDetailsServiceImpl userDetailsServiceImpl;
	
	@Autowired
	private User user;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private VehicleService vehicleService;
	
	@Autowired
	private VehicleRepository vehicleRepository;
	
	@Autowired
	private VehicleServiceImpl vehicleServiceImpl;
	
	@Autowired
	private BookingService bookingService;
	
	
	
	
	@ModelAttribute
	public void commonDashboard(Model model, Principal principal) {
		UserDetails userDetails  = userDetailsServiceImpl.loadUserByUsername(principal.getName());
		
		

		model.addAttribute("userdetails", userDetails);
		
	}
	
	
	
	@GetMapping("/dashboard")
	public String dashboard(Model model,Principal principal) {
		
//		UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(principal.getName());
//		User user = userService.findByEmail(userDetails.getUsername());
		
		List<Vehicle> vehicles = vehicleService.getAllVehicles();
		
		model.addAttribute("vehicles", vehicles);
		
		return "admin/admin-dashboard";
	}
	
	@GetMapping("/addVehicle")
	public String showAddVehicle(Model model ) {
		
		model.addAttribute("vehicle", new Vehicle());
		model.addAttribute("vehicleValue", true);
		return "admin/addVehicle";
	}
	
	@PostMapping("/process-vehicle")
	public String saveVehicle(@ModelAttribute Vehicle vehicle,@RequestParam("imageUrl")MultipartFile file,
			Principal principal,Model model,RedirectAttributes redirectAttributes) {
		
		
		UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(principal.getName());
		User user = userService.findByEmail(userDetails.getUsername());
		
		 if (file.isEmpty()) {
			// redirectAttributes.("errorMessage", "Image is empty, cannot save.");
			 redirectAttributes.addFlashAttribute("errorMessage", "Image is empty, cannot save.");
		        // Return the view where you want to show the error message
		        return "redirect:/admin/addVehicle";
		    }
		 else {
			 
			 vehicle.setUser(user);
			 
			 vehicleService.save(vehicle, file, model);	
			 //model.addAttribute("successMessage", );
			 redirectAttributes.addFlashAttribute("successMessage","Vehicle savedd ");
		 }
		
		   
	
		
		return "redirect:/admin/addVehicle";
		
		
	}
	
	
	@GetMapping("/delete/{id}")
	public String deleteVehicle(@PathVariable("id")Long vehicleId,RedirectAttributes redirectAttributes)
	{
		try {
			
			vehicleService.deleteVehicle(vehicleId);
			redirectAttributes.addFlashAttribute("successMessage", "deleted successfully");
			return "redirect:/admin/dashboard";
			
		}catch(Exception e) {
			
			redirectAttributes.addFlashAttribute("errorMessage", "unable to delete");
			
			return "redirect:/admin/dashboard";
		}
		
		
	}
	
	@GetMapping("/update-vehicle/{id}")
	public String showUdateVehicle(@PathVariable("id")Long vehicleId,Model model) {
		
		
		Vehicle vehicle = vehicleRepository.findById(vehicleId).get();
		
		
		model.addAttribute("vehicleValue", false);
		model.addAttribute("vehicle", vehicle);
		
		
		return "admin/addVehicle";
	}
	
	@PostMapping("/process-update/{id}")
	public String updateVehicle(@PathVariable("id")Long vehicleId,Model model,@ModelAttribute Vehicle vehicle, 
			@RequestParam("imageUrl") MultipartFile file,Principal principal,
			RedirectAttributes redirectAttributes)
	{
		UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(principal.getName());
		User user = userService.findByEmail(userDetails.getUsername());
		
		Vehicle existingVehicle = vehicleRepository.findById(vehicleId).orElse(null);
		String previousFileName = (existingVehicle != null) ? existingVehicle.getImage() : null;
        
		System.out.println("Previosus file a=naem "+previousFileName);
		
		
		if(!file.isEmpty()) {
			
			vehicle.setUser(user);
			vehicleService.save(vehicle, file, model);
			
			redirectAttributes.addFlashAttribute("successMessage", "updated successfully");
			
			String newFileName = file.getOriginalFilename();
			System.out.println("new file name "+newFileName);
			vehicleService.deleteImageFile(previousFileName);
			return "redirect:/admin/dashboard";
		}
			
		
		redirectAttributes.addFlashAttribute("errorMessage", " not updated successfully");
		
		return "redirect:/admin/dashboard";
	}
	
	@GetMapping("/search")
	public String displaySearch() {
		
		return "admin/search";
	}
	
//    @PostMapping("/searchVehicle")
//    public String searchVehicles(@ModelAttribute SearchVehicleDto searchVehicleDto, Model model) {
//        VehicleDtoList vehicleDtoList = vehicleService.searchVehicle(searchVehicleDto);
//        
//     
//        
//        model.addAttribute("vehicleDtoList", vehicleDtoList);
//        return "admin/search"; // Thymeleaf template name
//    }
	
	@PostMapping("/searchVehicle")
	public String searchVehicle(@RequestParam("brand")String keyword,Model model) {
		
		List<Vehicle> listVehicles = vehicleServiceImpl.listAll(keyword);
		
		model.addAttribute("listVehicles", listVehicles);
		
		return "admin/search";
	}
	
	
	@GetMapping("/showBookings")
	public String showBookingData(Model model) {
		
		List<MyBooking> allBookings = bookingService.getAllBookings();
		
		model.addAttribute("bookings", allBookings);
		
		return "admin/bookings";
		
	}
	
	@PostMapping("/bookingStatusApprove/{userId}")
	public String approveBooking(@PathVariable("userId")Long userId,Model model) {
		
		return bookingService.approveBooking(userId,model);
	}
	
	@PostMapping("/bookingStatusReject/{userId}")
	public String rejectBooking(@PathVariable("userId")Long userId, Model model) {
		
		return bookingService.rejectBooking(userId, model);
	}
}

package com.example.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.Entity.Vehicle;
import com.example.Entity.MyBooking;
import com.example.Entity.User;
import com.example.repository.VehicleRepository;
import com.example.service.BookingService;
import com.example.service.UserService;
import com.example.service.VehicleService;
import com.example.userdetails.UserDetailsServiceImpl;

@Controller
@RequestMapping("/user")
public class UserController {


	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	@Autowired
	private VehicleRepository vehicleRepository;
	
	@Autowired
	private VehicleService vehicleService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BookingService bookingService;
	
	
	@ModelAttribute
	public void commonDashboard(Model model, Authentication authentication) {
		if (authentication != null) {
            UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(authentication.getName());
            model.addAttribute("userDetails", userDetails);
        }
		
	}
	
	
	
	@GetMapping("/dashboard")
	public String homePage(Model model) {
		
		model.addAttribute("user", "user");
		
		List<Vehicle> vehicles = vehicleService.getAllVehicles();
		
		model.addAttribute("vehicles", vehicles);
		
		return "user/user-dashboard";
	}	
	
	
	@GetMapping("/search")
	public String searchCar() {
		return "user/Search";
	}
	
	@GetMapping("/mybookings")
	public String showMyBookings(Model model,Principal principal) {
		
		UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(principal.getName());
		User user = userService.findByEmail(userDetails.getUsername());
		
		Long keyword  = user.getId();
		
//		System.out.println("my bookings keyword " + keyword);
		
		List<MyBooking> bookings = bookingService.getBookingsForCurrentUser(user.getId());
		
		System.out.println(bookings);
		
		model.addAttribute("bookings", bookings);
		
		return "user/MyBookings";
	}
	
	@GetMapping("/bookVehicle/{id}")
	public String showBookedVehicle(@PathVariable("id")Long vehicleId,Model model,Principal principal)
	{
		UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(principal.getName());
		User user = userService.findByEmail(userDetails.getUsername());
		
		
		Optional<Vehicle> vehicleOptional = vehicleRepository.findById(vehicleId);
		Vehicle vehicle = vehicleOptional.get();
		
			model.addAttribute("vehicles", vehicle);
		
		return "user/booking";
	}
	
	
	@PostMapping("/saveVehicle/{vehicleId}")
	public String saveBookedVehicle(@PathVariable("vehicleId")Long vehicleId,@ModelAttribute MyBooking myBooking
			,Model model,Principal principal) {
		
		UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(principal.getName());
		User user = userService.findByEmail(userDetails.getUsername());
		
		Long userId = user.getId();
		
		System.out.println(userId);
		
		System.out.println(vehicleId);
		
		return bookingService.saveBookedVehicle(myBooking,  vehicleId, userId,model);
		
	}
}

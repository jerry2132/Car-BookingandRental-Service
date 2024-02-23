package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

	
	
	@GetMapping("/login")
	public String login() {
		
		return "login";
	}
	
	
//	@PostMapping("/login-process")
//	public String postLogin(HttpServletRequest request) {
//		
//		if (request.isUserInRole("ROLE_USER")) {
//          return "redirect:/home";
//      } else if (request.isUserInRole("ROLE_ADMIN")) {
//          return "redirect:/admin/dashboard";
//      } else {
//          // Handle other roles or situations
//          return "redirect:/default-dashboard";
//      }
//		
//		
//	}
	
	
	@GetMapping("/default-dashboard")
	public String defaultDashboard() {
		
		return "default_dashboard";
	}
	
	
	
	@GetMapping("/home")
	public String homePage() {
		
		return "home";
	}
}

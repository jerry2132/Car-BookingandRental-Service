package com.example.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.userdetails.UserDetailsServiceImpl;

@Controller
@RequestMapping("/user")
public class UserController {


	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	
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
		
		return "user/user-dashboard";
	}	
	
}

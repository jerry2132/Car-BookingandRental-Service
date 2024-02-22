package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.Entity.User;
import com.example.repository.UserRepository;
import com.example.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
public class SignUpController {
	
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserService userService;
	
	
	@RequestMapping("/")
	public String basePage() {
		
		return "signup";
	}

	@GetMapping("/signup")
	public String showSignupPage() {
		
		return "signup";
	}
	
	
	@PostMapping("/signup_process")
	public String processSignupPage(@Valid @ModelAttribute("user") User user,BindingResult bindingresult
 			,RedirectAttributes redirectAttributes,HttpServletRequest request) {
		
		User findUser = userRepository.findByEmail(user.getEmail());
		
		if(bindingresult.hasErrors()) {
 			
 			return "signup";
 		}
		
		if(findUser != null) {
 			
// 			model.addAttribute("message", "user already present");
 			
 			redirectAttributes.addFlashAttribute("errorMessage", "User already present");
 			
 			return "redirect:/signup";
 		}
		
		
		String password = request.getParameter("password");
		String confirmPassword = request.getParameter("confirm_password");
		System.out.println("new pass  "+password);
		System.out.println("confrim pass "+confirmPassword);
		
		if (!password.equals(confirmPassword)) {
			redirectAttributes.addFlashAttribute("errorMessage", "Passwords do not match");
	        
	        return "redirect:/signup";
	    }
		
		
		userService.save(user);
		redirectAttributes.addFlashAttribute("successMessage", "User saved successfully");
	
		return  "redirect:/signup";
	}
}

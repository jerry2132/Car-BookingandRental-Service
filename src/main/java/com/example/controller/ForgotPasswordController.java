package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.Entity.ForgotPassword;
import com.example.Entity.User;
import com.example.repository.ForgotPasswordRepository;
import com.example.service.ForgotPasswordService;
import com.example.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ForgotPasswordController {

	@Autowired
	private ForgotPasswordRepository forgotPasswordRepository;
	
	@Autowired
	private ForgotPasswordService forgotPasswordService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/password-request")
	public String showEmailRequest() {
		
		return "password-request";
		
	}
	
	@GetMapping("/reset-password")
	public String showResetPassword(@Param(value="token")String token, Model model,HttpSession session) {
		
		session.setAttribute("token", token);
		ForgotPassword forgotPasswordToken = forgotPasswordRepository.findByToken(token);
		return forgotPasswordService.checkValidity(forgotPasswordToken, model);
		
	}
	
	@PostMapping("/process-password-request")
	public String sendEmailLink(@RequestParam("email")String email,Model model) {
		
		User user = userService.findByEmail(email);
		
		if(user==null)
		{
			model.addAttribute("error", "email is not registered");
			return "password-request";
		}
		
		ForgotPassword forgotPassword = new ForgotPassword();
		
		forgotPassword.setExpireTime(null)
		
	}
	
}

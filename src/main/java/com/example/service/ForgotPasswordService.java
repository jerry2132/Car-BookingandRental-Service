package com.example.service;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;

import org.springframework.ui.Model;

import com.example.Entity.ForgotPassword;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public interface ForgotPasswordService {

	
	public String generateToken();
	
	public LocalDateTime expireTimeRange();
	
	public void sendMail(String to, String subject, String emailLink) throws MessagingException, UnsupportedEncodingException;
	
	public boolean isExpired(ForgotPassword forgotPassword);
	
	public String checkValidity(ForgotPassword forgotPassword, Model model);
	
	public String changePassword(HttpServletRequest request,HttpSession session,Model model);
}

package com.example.service;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.example.Entity.ForgotPassword;
import com.example.Entity.User;
import com.example.repository.ForgotPasswordRepository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Service
public class ForgotPasswordServiceImpl implements ForgotPasswordService{

	@Autowired
	JavaMailSender javaMailSender;
	
	@Autowired
	private ForgotPasswordRepository forgotPasswordRepository;
	
	@Autowired
	private UserService userService;
	
	private final int MINUTES = 5;
	
	public String generateToken()
	{
		return UUID.randomUUID().toString();
	}
	 
	public LocalDateTime expireTimeRange()
	{
		return LocalDateTime.now().plusMinutes(MINUTES);
	}
	
	public void sendMail(String to, String subject, String emailLink) throws MessagingException, UnsupportedEncodingException
	{
		
		MimeMessage message  = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message); 
		
		String emailContent  = "<p>Hello user</p>"+"click the link below to reset password"+"<p><a href=\" " + emailLink + "\"> change my password</a></p>"
				+"<br>"
				+"ignore if not done by you";
		
		helper.setText(emailContent,true);
		helper.setFrom("sumitsinghrawat34@gmail.com", "sumitSR");
		helper.setSubject(subject);
		helper.setTo(to);
		javaMailSender.send(message);
		
		
	}
	
	public boolean isExpired(ForgotPassword forgotPassword)
	{
		return LocalDateTime.now().isAfter(forgotPassword.getExpireTime());
	}
	
	public String checkValidity(ForgotPassword forgotPassword, Model model)
	{
		if(forgotPassword == null)
		{
			model.addAttribute("error", "Invalid Token");
			return "error";
		}
		
		else if(forgotPassword.isUsed())
		{
			model.addAttribute("error", "Already Used");
			return "error";
		}
		else if (isExpired(forgotPassword))
		{
			model.addAttribute("error", "Token Expired");
			return "error";
		}
		
		return "reset-password";
	}

	@Override
	public String changePassword(HttpServletRequest request, HttpSession session, Model model) {
		// TODO Auto-generated method stub
		
		String newPass = request.getParameter("newPassword");
		String confirmPass = request.getParameter("confirmPassword");
		
		if(!newPass.equals(confirmPass))
		{
			model.addAttribute("errorMessage", "Password Mismatch, try again");
			
			return "reset-password";
		}
		
		
		String token  = (String)session.getAttribute("token");
		
		ForgotPassword forgotPassword = forgotPasswordRepository.findByToken(token);
		
		User user  = forgotPassword.getUser();
		
		user.setPassword(newPass);
		
		
		forgotPassword.setUsed(true);
		
		userService.save(user);
		
		forgotPasswordRepository.save(forgotPassword);
		
		model.addAttribute("successMessage", "PAssword saved successfully");
		return "login";
	}
	
}

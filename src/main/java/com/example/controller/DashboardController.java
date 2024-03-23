package com.example.controller;

import java.util.Collection;
import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class DashboardController {

	@GetMapping("/dashboard")
	public String redirectToDashboard(HttpServletRequest request)
	{
	
	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	
	System.out.println(auth);
	
	if (auth != null) {
       // Set<String> roles = AuthorityUtils.authorityListToSet(auth.getAuthorities());
        
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        	
        System.out.println("User Authorities " + authorities);
        
       // System.out.println(roles);
        
        if (authorities.stream().anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"))) {
        	System.out.println("admin role");
            return "redirect:/admin/dashboard";
        }else if(authorities.stream().anyMatch(r -> r.getAuthority().equals("ROLE_USER"))) {
        	System.out.println("User role");
            return "redirect:/user/dashboard";
    }
	
}
	return "redirect:/default_dashboard";
	
	}
	
}
//		
//		
//		if (request.isUserInRole("ROLE_USER")) {
//          return "redirect:/home";
//      } else if (request.isUserInRole("ROLE_ADMIN")) {
//          return "redirect:/admin/dashboard";
//      } else {
//          // Handle other roles or situations
//          return "redirect:/default-dashboard";
//      }
//  }
//
//	
//}
package com.example.security;

import java.io.IOException;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler{

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		// TODO Auto-generated method stub
		
		String redirectUrl = null;
		
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		System.out.println(authorities);
		
		for(GrantedAuthority grantedAuthority : authorities) {
			
			if(grantedAuthority.getAuthority().equals("ROLE_USER")) {
				
				System.out.println("Authority is  "+ grantedAuthority.getAuthority());
				redirectUrl = "/user/dashboard";
				break;
			}
			
			else if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
				
				System.out.println("Authority is  "+ grantedAuthority.getAuthority());
				redirectUrl = "/admin/dashboard";
				break;
			}
		}
		
		if (redirectUrl == null) {
			throw new IllegalStateException();
			
		}
    	
		new DefaultRedirectStrategy().sendRedirect(request, response , redirectUrl);
		
	}

}

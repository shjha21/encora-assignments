package com.example.demo.security;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.util.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtRequestFilter extends OncePerRequestFilter{

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authHeader=request.getHeader("Authorization");
		if(authHeader!=null && authHeader.startsWith("Bearer ")) {
			String token=authHeader.substring(7);
			
			try {
				Map<String, Object> claims=JwtUtil.validateToken(token);
				String username=(String) claims.get("sub");
				
				List<String> roles=(List<String>)claims.get("roles");
				var authorities=roles.stream()
						.map(SimpleGrantedAuthority::new)
						.collect(Collectors.toList());
				var authentication=new UsernamePasswordAuthenticationToken(username, null, authorities);
				//Set authentication in security context
				SecurityContextHolder.getContext().setAuthentication(authentication);
			} catch(Exception e) {
				System.out.println("Invalid or expired token: "+e.getMessage());
			}
		}
		
		filterChain.doFilter(request, response);
		
	}

}

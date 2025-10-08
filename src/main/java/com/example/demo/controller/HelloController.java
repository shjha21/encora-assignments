package com.example.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.util.JwtUtil;

@RestController
@RequestMapping("/api")
public class HelloController {
	
	@PostMapping("/admin")
	public String admin(Authentication auth) {
		
		return "Welcome Admin, "+auth.getName()+"! You have speical access.";
	}
	
	
	@GetMapping("/hello")
	public String hello(Authentication auth){
		
			return "Hello, "+auth.getName()+"! You have special access.";
		
	}
	
	@PostMapping("/login") 
	 public ResponseEntity<?> login(@RequestParam String username) { 
		 if(username.equals("sam"))		{
		 	 String token=JwtUtil.generateToken(username,List.of("ROLE_ADMIN"));
		 			 return ResponseEntity.ok(token);
		     }
		     else {
		    	 String token=JwtUtil.generateToken(username,List.of("ROLE_USER"));
					 return ResponseEntity.ok(token);
		     }
	 } 

}

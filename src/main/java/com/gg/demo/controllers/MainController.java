package com.gg.demo.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.providers.ExpiringUsernameAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gg.demo.models.UserModel;

@RestController
@RequestMapping("/")
public class MainController {

	@GetMapping
	public ResponseEntity<?> main(ExpiringUsernameAuthenticationToken userToken){
		UserModel model = (UserModel) userToken.getPrincipal();
		return ResponseEntity.ok(model);
	}
	
}

package com.gtl.notis.controller;

import org.springframework.web.bind.annotation.RestController;

import com.gtl.notis.service.TokenService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class HelloController {
	
	@Autowired
    private TokenService tokenService;

	@RequestMapping("/token")
	public String index() {
		tokenService.authenticate();
		return "Greetings from Spring Boot!";
	}

}

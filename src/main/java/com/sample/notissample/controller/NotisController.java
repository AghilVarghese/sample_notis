package com.sample.notissample.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sample.notissample.service.TokenService;

@RestController
public class NotisController {

	@Autowired
    private TokenService tokenService;
	
	@RequestMapping("/token")
	public String index() {
		
		return "Greetings from Spring Boot!";
	}

}

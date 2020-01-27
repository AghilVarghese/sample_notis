package com.gtl.notis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.gtl.notis.EntityManager;

@Service("tokenService")
public class TokenService {

	private static final String TOKEN_HOST = "token.host";

	@Autowired
	private Environment env;

	public void login() {
		String tokenhost = env.getProperty(TOKEN_HOST);
		String contentType = env.getProperty("token.endpoint");

		
		System.out.println("authenticate the request :: TOKEN HOST :: " + tokenhost);
	}

}

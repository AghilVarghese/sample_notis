package com.gtl.notis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:application.properties")
public class EntityManager {

	@Autowired
	private Environment env;

	public void readProperty(String propertyName) {
		env.getProperty(propertyName);
	}

}
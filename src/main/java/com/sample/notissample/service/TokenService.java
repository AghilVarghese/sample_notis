package com.sample.notissample.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.sample.notissample.Util;

@Service("tokenService")
public class TokenService {

	private static final String GRANT_TYPE = "client_credentials";

	@Autowired
	private Environment env;

	public String login() throws IOException {
		String consumerKey = env.getProperty("consumer_key");
		String consumerSecret = env.getProperty("consuer_secret");
		String authorization = Util.getAuthorization(consumerKey, consumerSecret);
		String nonce = Util.getNonceValue();

		String tokenRequestHost = env.getProperty("token.host");
		String tokenRequestEndpoint = env.getProperty("token.endpoint");
		String tokenRequestContentType = env.getProperty("token.content_type");

		String urlString = "https://" + tokenRequestHost + tokenRequestEndpoint;
		URL url = new URL(urlString);
		HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", tokenRequestContentType);
		connection.setRequestProperty("Authorization", authorization);
		connection.setRequestProperty("nonce", nonce);
		
		// https://stackoverflow.com/questions/51421953/how-to-send-url-encoded-data-using-post-method-in-java
		
		
	}

	private String postTokenRequest(HttpsURLConnection connection) {
		String input;
		if (connection != null) {
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				while ((input = br.readLine()) != null) {
					System.out.println(input);
				}
				br.close();
				
				JsonOb

			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
}

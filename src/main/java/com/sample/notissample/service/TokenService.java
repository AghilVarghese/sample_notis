package com.sample.notissample.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javax.net.ssl.HttpsURLConnection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample.notissample.Util;

@Service("tokenService")
public class TokenService {

	private static final String GRANT_TYPE_URL_DATA = "grant_type=client_credentials";

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

		// https://stackoverflow.com/questions/40574892/how-to-send-post-request-with-x-www-form-urlencoded-body
		byte[] postData = GRANT_TYPE_URL_DATA.getBytes(StandardCharsets.UTF_8);
		int postDataLength = postData.length;
		connection.setRequestProperty("charset", "utf-8");
		connection.setRequestProperty("Content-Length", Integer.toString(postDataLength));
		try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
			wr.write(postData);
		}

		return this.postTokenRequest(connection);
	}

	private String postTokenRequest(HttpsURLConnection connection) {
		// https://stackoverflow.com/questions/34899480/how-to-read-json-data-from-httpurlconnection
		String input;
		String accessToken = "";
		if (connection != null) {
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				while ((input = br.readLine()) != null) {
					System.out.println(input);
				}
				br.close();

				// https://www.danvega.dev/blog/2017/07/05/read-json-data-spring-boot-write-database/
				// https://attacomsian.com/blog/jackson-convert-json-string-to-json-node

				// sample response data => { "access_token":
				// "ee1073de-45d0-4040-b9c2-eddfa80280c0", "token_type": "bearer", "expires_in":
				// "3600", "scope": "api_scope" }

				ObjectMapper mapper = new ObjectMapper();
				JsonNode node = mapper.readTree(input);
				accessToken = node.get("access_token").asText();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return accessToken;
	}
}

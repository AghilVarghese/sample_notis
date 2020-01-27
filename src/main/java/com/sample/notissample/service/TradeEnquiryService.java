package com.sample.notissample.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sample.notissample.Util;

@Service("tradeEnquiryService")
public class TradeEnquiryService {
	private static final String DEFAULT_QUERY_STRING = "0,ALL,,";
	private static final String DATA_FORMAT = "CSV:CSV";

	@Autowired
	private Environment env;

	public String getAllTradeData() throws IOException {
		String tokenRequestHost = env.getProperty("tradeenquiry.host");
		String tokenRequestEndpoint = env.getProperty("tradeenquiry.endpoint");

		String consumerKey = env.getProperty("consumer_key");
		String consumerSecret = env.getProperty("consuer_secret");
		String authorization = Util.getAuthorization(consumerKey, consumerSecret);
		String nonce = Util.getNonceValue();

		String postData = this.getAllTradeRequestData();

		// https://www.baeldung.com/httpurlconnection-post

		String urlString = "https://" + tokenRequestHost + tokenRequestEndpoint;
		URL url = new URL(urlString);
		HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Authorization", authorization);
		connection.setRequestProperty("nonce", nonce);
		connection.setDoOutput(true);
		try (OutputStream os = connection.getOutputStream()) {
			byte[] input = postData.getBytes("utf-8");
			os.write(input, 0, input.length);
		}

		// Read the Response from Input Stream
		String responseLine = "";
		try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
			StringBuilder response = new StringBuilder();
			responseLine = null;
			while ((responseLine = br.readLine()) != null) {
				response.append(responseLine.trim());
			}
			System.out.println(response.toString());
		}

		return responseLine;
	}

	private String getAllTradeRequestData() {
		String version = env.getProperty("notis.api.version");

		// Sample request json data => { "version": "1.0", "data": { "msgId":
		// "00240201310140000001", "dataFormat": "CSV:CSV", "tradesInquiry": "0,ALL,," }
		// }

		ObjectMapper mapper = new ObjectMapper();
		ObjectNode data = mapper.createObjectNode();
		data.put("msgId", Util.getMessageId());
		data.put("dataFormat", DATA_FORMAT);
		data.put("tradesInquiry", DEFAULT_QUERY_STRING);
		ObjectNode jsonData = mapper.createObjectNode();
		jsonData.put("version", version);
		jsonData.put("data", jsonData);

		return jsonData.asText();
	}
}

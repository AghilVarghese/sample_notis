package com.sample.notissample.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sample.notissample.service.TokenService;
import com.sample.notissample.service.TradeEnquiryService;

@RestController
public class NotisController {

	@Autowired
	private TokenService tokenService;

	private TradeEnquiryService tradeEnquiryService;

	@RequestMapping("/token")
	public String token() {
		String accessToken = "";
		try {
			accessToken = tokenService.login();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return accessToken;
	}

	@RequestMapping("/trade")
	public String tradeInquiry() {
		String data = "";
		try {
			data = tradeEnquiryService.getAllTradeData();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}
}

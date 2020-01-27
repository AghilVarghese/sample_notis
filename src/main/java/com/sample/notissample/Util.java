package com.sample.notissample;

public class Util {

	public static String getAuthorization(String consumerKey, String consumerSecret) {
		// Will be of format: Basic <member_credentials>
		// Where, member_credentials is a base64 encoding of the
		// following data: cons_key:cons_secret

		return "";
	}

	public static String getNonceValue() {
		// An N-once value, that uniquely identifies each request sent to server.
		// Has to be a base64 encoding of the
		// following data: ddMMyyyyHHmmssSSS:<6-digit random number>

		return "";
	}

	public static String getMessageId() {
		// Unique request number for the each request <CODE><YYYYMMDD><nnnnnnn> •
		// MEMBERCODE – Member code (Length : 5) • YYYYMMDD – Date format • nnnnnnn –
		// Sequence no. starting from one i.e. For first request of the day, it should
		// be (0000001).
		return "";
	}
}

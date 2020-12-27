package com.shop.models;

import lombok.Data;

public @Data class AuthenticationRequest {

	private String username;
	private String password;
	
	public AuthenticationRequest() {}
	public AuthenticationRequest(String username, String password) {
	this.username=username;
	this.password=password;
	}

}

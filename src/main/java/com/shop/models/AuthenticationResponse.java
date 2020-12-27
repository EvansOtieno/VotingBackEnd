 package com.shop.models;

import lombok.Data;

public @Data class AuthenticationResponse {
	private final String jwt;
	private final Users user;
	
	public AuthenticationResponse(String jwt,Users user) {
		this.jwt=jwt;
		this.user=user;
	}
	
}
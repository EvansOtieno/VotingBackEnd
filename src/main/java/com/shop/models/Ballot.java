package com.shop.models;

import lombok.Data;

public @Data class Ballot {

	private Contestant chair;
	
	private Contestant vchair;
	
	private Contestant secgen;
	
	private Contestant academics;

	private Contestant halls;
	
	private Contestant faculty;
	
	private int voter;
}

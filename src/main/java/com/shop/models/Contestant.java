package com.shop.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

@Entity
@Table
public @Data class Contestant {

	@Id 
	private int id;	
	@Column
	private String position;
	@Column
	private int votes;
	@Column
	private String extension;
	@Column
	@Lob
	private byte[] picture;
	@Transient
	private String base64image;
	@Transient
	private String name;
	@Transient
	private String email;

}

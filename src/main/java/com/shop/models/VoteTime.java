package com.shop.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import java.util.Date;
import lombok.Data;

@Entity
@Table
public @Data class VoteTime {

	@Id
	private int id=1;
	@Column
	private String starttime;
	@Column
	private String stoptime;
	
}

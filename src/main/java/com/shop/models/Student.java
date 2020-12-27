package com.shop.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table
public @Data class Student extends Users {
	public Student(String email) {
	super(email);
	}
	public Student() {}
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column
	private String faculty;
	@Column
	private String firstname;
	@Column
	private String lastname;
	@Column
	private String residence;
	@Column
	private boolean voted;
	@Column
	private String phoneno;
	
}

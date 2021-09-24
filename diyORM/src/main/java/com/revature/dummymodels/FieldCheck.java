package com.revature.dummymodels;

import java.util.Date;

import com.revature.annotations.Column;
import com.revature.annotations.Entity;
import com.revature.annotations.Id;

@Entity(tableName = "FieldCheck")
public class FieldCheck {
	
	@Id(columnName= "id")
	public int id;
	
	@Column(columnName = "username")
	public String testUsername;
	
	@Column(columnName = "pass")
	public String testPassword;
	
	@Column(columnName = "testChar")
	public char testChar;
	
	@Column(columnName = "date")
	public Date date;

	public FieldCheck(String testUsername, String testPassword, char testChar, Date date) {
		super();
		this.testUsername = testUsername;
		this.testPassword = testPassword;
		this.testChar = testChar;
		this.date = date;
	}
	
	
}

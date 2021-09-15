package com.revature.dummymodels;

import com.revature.annotations.Column;
import com.revature.annotations.Entity;
import com.revature.annotations.Id;

@Entity(tableName = "test_table")
public class Test {
	
	@Id(columnName= "test_id")
	private int id;
	
	@Column(columnName = "test_username")
	private String testUsername;
	
	@Column(columnName = "test_password")
	private String testPassword;
}

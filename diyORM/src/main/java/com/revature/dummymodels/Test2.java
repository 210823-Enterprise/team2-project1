package com.revature.dummymodels;

import com.revature.annotations.Column;
import com.revature.annotations.Entity;
import com.revature.annotations.Getter;
import com.revature.annotations.Id;
import com.revature.annotations.Setter;

@Entity(tableName = "test2")
public class Test2 {
	
	@Id(columnName= "id")
	public int id;
	
	@Column(columnName = "username")
	public String testUsername;
	
	@Column(columnName = "pass")
	public String testPassword;
	
	@Column(columnName = "balance")
	public double cash;

	public Test2(int id, String testUsername, String testPassword, double cash) {
		super();
		this.id = id;
		this.testUsername = testUsername;
		this.testPassword = testPassword;
		this.cash = cash;
	}
	
	@Getter(name = "getTestId")
	public int getId() {
		return id;
	}
	@Setter(name = "setTestId")
	public void setId(int id) {
		this.id = id;
	}

	@Getter(name = "getTestUsername")
	public String getTestUsername() {
		return testUsername;
	}
	
	@Setter(name = "setTestUsername")
	public void setTestUsername(String testUsername) {
		this.testUsername = testUsername;
	}

	@Getter(name = "getTestPassword")
	public String getTestPassword() {
		return testPassword;
	}

	@Setter(name = "setTestPassword")
	public void setTestPassword(String testPassword) {
		this.testPassword = testPassword;
	}
}


package com.revature.dummymodels;

import com.revature.annotations.Column;
import com.revature.annotations.Entity;
import com.revature.annotations.Getter;
import com.revature.annotations.Id;
import com.revature.annotations.Setter;

@Entity(tableName = "test")
public class Test {
	
	@Id(columnName= "id")
	public int id;
	
	@Column(columnName = "username")
	public String testUsername;
	
	@Column(columnName = "pass")
	public String testPassword;
	
	public Test() {
		
	}
	
	public Test(int id, String testUsername, String testPassword) {
		super();
		this.id = id;
		this.testUsername = testUsername;
		this.testPassword = testPassword;
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

	@Override
	public String toString() {
		return "Test [id=" + id + ", testUsername=" + testUsername + ", testPassword=" + testPassword + "]";
	}
	
	
}

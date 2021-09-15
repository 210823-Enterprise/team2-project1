package com.revature.util;

import java.sql.Connection;

public class ConnectionUtil {
	
	// Idea: Maybe you could use this to grab JDBC configuration details 
	// from your Configuration.java class
	
	// The user's job might be to provide those somewhere in the app.
	
	// Another idea: set up connection pool here....
	// Research DATASOURCE in custom ORM's
	public void getConnection() {
		Connection con = DBCPDataSource.getConnection();
	}
	
}

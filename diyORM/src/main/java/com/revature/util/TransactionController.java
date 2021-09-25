package com.revature.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TransactionController {

	//begin databse commit.
	public void beginCommit(Connection cn) {
		
	}
	
	//Enable auto commits on the database.
	public void enableAutoCommit(Connection cn) {
		
	}
	
	// Rollback to previous commit.
	public void Rollback(Connection cn) {
		String sql = "ROLLBACK";
		try {
			Statement stmt = cn.createStatement();
			stmt.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	// Rollback to previous commit with given name.
	public void Rollback(final String name, Connection cn) {
		String sql = "ROLLBACK TO " + name;
		try {
			Statement stmt = cn.createStatement();
			stmt.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	//Release the savepoint with the given name.
	public void ReleaseSavepoint(final String name, Connection cn) {
		String sql = "RELEASE SAVEPOINT " + name;
		try {
			Statement stmt = cn.createStatement();
			stmt.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//Set a savepoint with the given name.
	public void setSavepoint(final String name, Connection cn) {
		String sql = "SAVEPOINT " + name;
		try {
			Statement stmt = cn.createStatement();
			stmt.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//Start a transaction block.
	public void setTransaction(Connection cn) {
		
	}
}

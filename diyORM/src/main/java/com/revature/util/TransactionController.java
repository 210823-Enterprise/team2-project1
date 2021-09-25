package com.revature.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import org.apache.log4j.Logger;

public class TransactionController {
	private static Logger log = Logger.getLogger(TransactionController.class);


	// begin databse commit.
	public void beginCommit(Connection cn) {
		try {
			cn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			log.warn(e);
		}
	}

	// Enable auto commits on the database.
	public void enableAutoCommit(Connection cn) {
		try {
				cn.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
			log.warn(e);
		}
	
	//disable auto commits on the db
	public void disableAutoCommit(Connection cn) {
		try {
				cn.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
			log.warn(e);
		}

	}

	// Rollback to previous commit.
	public void Rollback(Connection cn) {
		String sql = "ROLLBACK";
		try {
			Statement stmt = cn.createStatement();
			stmt.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			log.warn(e);
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

			log.warn(e);
		}
	}

	// Release the savepoint with the given name.
	public void ReleaseSavepoint(final String name, Connection cn) {
		String sql = "RELEASE SAVEPOINT " + name;
		try {
			Statement stmt = cn.createStatement();
			stmt.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();

			log.warn(e);
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
			log.warn(e);
		}
	}

	// Start a transaction block.
	public void setTransaction(Connection cn) {
		disableAutoCommit(cn);
		}
	}

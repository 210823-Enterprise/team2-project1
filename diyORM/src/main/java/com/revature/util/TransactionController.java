package com.revature.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class TransactionController {
	private static Logger log = Logger.getLogger(TransactionController.class);
	private static Map<String,Savepoint> savepoints = new HashMap<String,Savepoint>();
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
		try {
			cn.rollback();
			log.info("Rollback committed");
		} catch (SQLException e) {
			e.printStackTrace();
			log.info("Rollback failed");
			log.warn(e);
		}
	}

	// Rollback to previous commit with given name.
	public void Rollback(final String name, Connection cn) {
		try {
			cn.rollback(savepoints.get(name));
			log.info("Rollback committed");
		} catch (SQLException e) {
			e.printStackTrace();
			log.info("Rollback with savepoint name "+ name+" failed.");
			log.warn(e);
		}
	}

	// Release the savepoint with the given name.
	public void ReleaseSavepoint(final String name, Connection cn) {
		try {
			cn.releaseSavepoint(savepoints.get(name));
		} catch (SQLException e) {
			e.printStackTrace();
			log.warn("Release Savepoint of name "+name+" failed.");
		}
	}

	// Set a savepoint with the given name.
	public void setSavepoint(final String name, Connection cn) {
		try {
			Savepoint sp = cn.setSavepoint(name); 
			savepoints.put(name,sp);
		} catch (SQLException e) {
			e.printStackTrace();
			log.warn("Savepoint failed");
		}
	}

	// Start a transaction block.
	public void setTransaction(Connection cn) {
		disableAutoCommit(cn);

	}
}

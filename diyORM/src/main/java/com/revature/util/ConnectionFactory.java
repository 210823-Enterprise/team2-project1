package com.revature.util;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;

public class ConnectionFactory {
	private static BasicDataSource ds = new BasicDataSource();
    private static Logger log = Logger.getLogger(ConnectionFactory.class);
    private static final ConnectionFactory connection_factory = new ConnectionFactory();
    static {
    	try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    private ConnectionFactory(){
    	Properties prop = new Properties();
		
		try {
			prop.load(new FileReader("src/main/resources/application.properties"));
			ds.setUrl(prop.getProperty("url"));
			ds.setUsername(prop.getProperty("username"));
			ds.setPassword(prop.getProperty("password"));
	        ds.setMinIdle(5);
	        ds.setMaxIdle(10);
	        ds.setDefaultAutoCommit(false);
	        ds.setMaxOpenPreparedStatements(100);
			
		} catch (IOException e) {
			log.error("Cannout locate application.properties file.");
			e.printStackTrace();
		} 

    }
    
    public static Connection getConnection() {
        try {
        	log.info("Database Connection Established");
			return ds.getConnection();
		} catch (SQLException e) {
			log.error("Cannot establish database connection");
		}
        return null;
    }
    
    public static ConnectionFactory getInstance() {
    	return connection_factory;
    }
    
  
    
    

}

package com.revature.util;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;

public class DBCPDataSource {
	private static BasicDataSource ds = new BasicDataSource();
    static Configuration config = new Configuration();
    private static Logger log = Logger.getLogger(DBCPDataSource.class);
    static {
    	Properties prop = new Properties();
		
		String url = "";
		String username = "";
		String password = "";
		
		
		try {
			prop.load(new FileReader("C:\\Users\\ryanm\\Desktop\\Project_0\\project-0-ryan-mcomber\\src\\main\\resources\\application.properties"));
			ds.setUrl(prop.getProperty("url"));
			ds.setUsername(prop.getProperty("username"));
			ds.setPassword(prop.getProperty("password"));
			
			log.info("Database Connection Established");
		} catch (IOException e) {
			log.error("Cannout locate application.properties file.");
			e.printStackTrace();
		} 
        ds.setMinIdle(5);
        ds.setMaxIdle(10);
        ds.setMaxOpenPreparedStatements(100);
    }
    
    public static Connection getConnection() {
        try {
			return ds.getConnection();
		} catch (SQLException e) {
			log.error("Cannot establish database connection");
		}
        return null;
    }
    
    public DBCPDataSource(){ }

}

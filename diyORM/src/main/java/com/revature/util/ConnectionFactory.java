package com.revature.util;

//import java.io.FileReader;
//import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;

public class ConnectionFactory {
	private static BasicDataSource ds = new BasicDataSource();
    private static Logger log = Logger.getLogger(ConnectionFactory.class);
    public static String path = "jdbc:postgresql://team-2-ncc.cvtq9j4axrge.us-east-1.rds.amazonaws.com:5432/postgres?currentSchema=team2project1";
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
		
//		try {
			//prop.load(new FileReader(path));
			ds.setUrl(path);
			ds.setUsername("postgres");
			ds.setPassword("postgres");
	        ds.setMinIdle(5);
	        ds.setMaxIdle(10);
	        ds.setDefaultAutoCommit(false);
	        ds.setMaxOpenPreparedStatements(100);
			
//		} catch (IOException e) {
//			log.error("Cannout locate application.properties file at "+path);
//			e.printStackTrace();
//		} 

    }
    
    public static Connection getConnection() {
        try {
        	Connection conn = ds.getConnection();
        	log.info("Database Connection Established with path: "+path);
			return conn;
		} catch (SQLException e) {
			log.error("Cannot establish database connection");
		}
        return null;
    }
    
    public static ConnectionFactory getInstance(String path2) {
    	path = path2;
    	return connection_factory;
    }

	public static void setPath(String string) {
		// TODO Auto-generated method stub
		path = string;
		
	}
    
  
    
    

}

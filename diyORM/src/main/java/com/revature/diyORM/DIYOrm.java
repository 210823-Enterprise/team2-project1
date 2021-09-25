package com.revature.diyORM;

import java.sql.Connection;


import com.revature.util.ConnectionFactory;
import com.revature.util.TransactionController;
import com.revature.objectmapper.ObjectMapper;
import com.revature.objectmapper.ObjectRemover;

public class DIYOrm {
	
	final private static DIYOrm diyorm = new DIYOrm();
	private final Connection conn;
	private final ObjectMapper obj_mapper;
	// obj getter, etc.....
	
	private DIYOrm() {
		conn = ConnectionFactory.getConnection();
		obj_mapper =   ObjectMapper.getInstance();
		
		
	}
	

	
	// return a a static instance of this singleton class
	public static DIYOrm getInstance() {
		return diyorm;
	}
	
	//Adds a class to the ORM. This is the method to use to declare a Class is an object inside of the database.
	public boolean addClass(final Class<?> clazz) {
		return false;
	}
	

	
	// when someone wants to delete an object from their database
	// DIYOrm.deleteObjFromDB
	public boolean deleteObjFromDB(Object obj) {
		
		return obj_mapper.removeObjectFromDb(obj, conn);
		
	}

}

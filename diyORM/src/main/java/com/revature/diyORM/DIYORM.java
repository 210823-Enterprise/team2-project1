package com.revature.diyORM;

import java.sql.Connection;

import com.revature.util.ConnectionFactory;
import com.revature.objectmapper.ObjectRemover;

public class DIYORM {
	
	final private static DIYORM diyorm = new DIYORM();
	private final Connection conn;
	private final ObjectRemover obj_remover;
	// obj getter, etc.....
	
	private DIYORM() {
		conn = ConnectionFactory.getInstance().getConnection();
		obj_remover =   ObjectRemover.getInstance();
		
		
	}
	
	// return a a static instanc of this singleton class
	public static DIYORM getInstance() {
		return diyorm;
	}
	
	
	
	// when someone wants to delete an object from their database
	// DIYORM.deleteObjFromDB
	public boolean deleteObjFromDB(Object obj) {
		
		return obj_remover.removeObjectFromDb(obj, conn);
		
	}

}
package com.revature.diyORM;

import java.sql.Connection;

import com.revature.objectmapper.ObjectRemover;
import com.revature.util.ConnectionFactory;

public class DIYOrm {
	final private static DIYOrm diyorm = new DIYOrm();
	private final Connection conn;
	private final ObjectRemover obj_remover = new ObjectRemover();
	// obj getter, etc...
	private DIYOrm() {
		conn = ConnectionFactory.getInstance().getConnection();
		//obj_remover = ObjectRemover.getInstance(); TODO: make singletons for each
	}
	
	// return a static instance of this singleton class
	public static DIYOrm getInstance() {
		return diyorm;
	}

	// when someone wants to delete an object from their db
	public boolean deleteObjFromDb(Object obj) {
		return obj_remover.removeObjectFromDb(obj, conn);
	}
	
}

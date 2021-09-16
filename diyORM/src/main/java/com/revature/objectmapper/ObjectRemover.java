package com.revature.objectmapper;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.revature.util.MetaModel;

public class ObjectRemover extends ObjectMapper {
	
	public boolean removeObjectFromDb(Object obj, Connection conn) {
		
		MetaModel<?> model = MetaModel.of(obj.getClass()); //use this to create an instance of the object
		String primaryKey = model.getPrimaryKey().getName();
		String sql = "DELETE from "+model.getSimpleClassName()+" where "+primaryKey+"= ?"; //TODO: create some type of method that return the table name in MetaModel
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			// we want to grab metadata from this statement 
			ParameterMetaData pd = pstmt.getParameterMetaData();
			// then call a custom setStatement method
			//setStatement(pstmt,pd,new Object(), 1);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return false;
	}
	
	

}

package com.revature.objectmapper;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.util.ConnectionFactory;
import com.revature.util.IdField;
import com.revature.util.MetaModel;


public class ObjectRemover extends ObjectMapper{
	
	@Override
	public boolean removeObjectFromDb(Object obj, Connection conn) {
		
		MetaModel<?> model = MetaModel.of(obj.getClass()); // use this to create an instance of the object
		
		
		IdField primaryKey = model.getPrimaryKey(); // change this to IdField\
		//String sql = "INSERT INTO team2project1.test (username,pass) VALUES ('ryano','passwsadford') RETURNING id";
		String sql = "DELETE FROM team2project1." + model.getSimpleClassName().toLowerCase() + " WHERE " + primaryKey.getName() + " = ? RETURNING id"; // create some type of method that returns the table name in MetaModel;
		System.out.println(sql);
		
		
		// we want to grab meta data from this statement
		try {
			 System.out.println("? = "+primaryKey.get(obj));
			 PreparedStatement pstmt = conn.prepareStatement(sql);
			 pstmt.setInt(1, primaryKey.get(obj));
			 ResultSet rs;
			 
			 if ((rs = pstmt.executeQuery()) != null) {
				 rs.next();
				 System.out.println(rs.getInt(1));
				 ParameterMetaData pd = pstmt.getParameterMetaData();
				 conn.commit();
				 return true;
			 }
			 

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
		// instead of Method, maybe pass in a hashmap containing info about the object that you
		//setStatement(pstmt, pd, obj, 1);
		
		//ObjectCache class...
		// then call acustom setStatement method	
		
	}

}

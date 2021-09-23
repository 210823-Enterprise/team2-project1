package com.revature.objectmapper;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import com.revature.annotations.Column;
import com.revature.annotations.Entity;
import com.revature.annotations.Id;

public class ObjectSaver extends ObjectMapper {

	// Update columns will be comma separated string
	public boolean UpdateObjectInDB(final Object obj, final String update_columns) {
		return false;
	}

	public boolean addObjectToDB(final Object obj, Connection conn) {
		// get a list of fields in the object
		Field[] fields = obj.getClass().getDeclaredFields();
		// begin sql statement, inserting into the table
		String sql = "INSERT INTO " + "\"" + obj.getClass().getAnnotation(Entity.class).tableName() + "\"" + " (";
		
		int fieldCounter = 0;
		for (Field f : fields) {
			System.out.println();
			fieldCounter++;
			// add field names to sql string
			try{
				f.getAnnotation(Id.class).columnName();
			}catch (NullPointerException e){
				sql += " " + f.getAnnotation(Column.class).columnName();
				if (fields.length > fieldCounter)
					sql += ",";
			}

		}

		sql += " ) values (";

		// insert question marks
		int fieldCounter2 = 0;
		for (Field f : fields) {
			fieldCounter2++;
			try{
				f.getAnnotation(Id.class).columnName();
			}catch (NullPointerException e){
				sql += "?";
				if (fields.length > fieldCounter2)
					sql += ",";
			}
		}
		sql += ");";
		System.out.println(sql);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Long newId = 0L;
		try {
			pstmt = conn.prepareStatement(sql);
			int fieldCounter3 = 1;
			for (Field f : fields) {
				try {
					// add more types when needed, for now, just testing with the two i'm likely to
					// use
					if (f.getType() == String.class) {
						if (f.get(obj) != null) {
							pstmt.setString(fieldCounter3, f.get(obj).toString());
						} else {
							pstmt.setNull(fieldCounter3, Types.VARCHAR);
						}
					} else if (f.getType() == int.class || f.getType() == Integer.class) {
						if (f.get(obj) != null) {
							try{
								f.getAnnotation(Id.class).columnName();
							}catch (NullPointerException e){
								pstmt.setInt(fieldCounter3, (int) f.get(obj));
							}
						} else {
							pstmt.setNull(fieldCounter3, Types.INTEGER);
						}
					}
					try{
						f.getAnnotation(Id.class).columnName();
					}catch (NullPointerException e){ fieldCounter3++;}
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//TODO log something here
			pstmt.execute();
			rs = pstmt.getGeneratedKeys();
			//newId = rs.getLong("id"); //in case we want to return the id.
			//System.out.println(newId); //here for debugging
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (pstmt != null) {
					try {
						pstmt.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}

		return true;
	}
}

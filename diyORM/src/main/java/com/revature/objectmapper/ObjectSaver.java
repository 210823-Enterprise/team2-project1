package com.revature.objectmapper;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import java.util.Arrays;

import com.revature.annotations.Column;
import com.revature.annotations.Entity;
import com.revature.annotations.Id;

public class ObjectSaver extends ObjectMapper {
	private static Logger log = Logger.getLogger(ObjectSaver.class);
	// Update columns will be comma separated string that shouldn't contain id, since that is how the function finds the entry to update.
	public boolean UpdateObjectInDB(final Object obj, final String update_columns, Connection conn) throws IllegalArgumentException {
		update_columns.replace(" ", "");
		String[] columns = update_columns.split(",");
		Field[] fields = obj.getClass().getDeclaredFields();
		List<Field> fieldsToUpdate = new ArrayList<Field>();
		int id = 0;
		String idName = "";
		for (Field f: fields) {
			for (String s: columns) {
				try{
					if (f.getAnnotation(Id.class).columnName().equals(s)) {
						//check if the field is an id and matches the fields being edited
						throw new IllegalArgumentException("Check that update string does not contain the name of the table's primary key");
					}
					id = (int) f.get(obj);
					idName = f.getAnnotation(Id.class).columnName();
					
				}catch (NullPointerException e){
					if (f.getAnnotation(Column.class).columnName().equals(s)) {
						//check if the field is a column and matches the fields being edited
						fieldsToUpdate.add(f);
					}
				} catch (IllegalAccessException e) {
					e.printStackTrace();
					log.warn(e);
				}
			}
		}
		if (fieldsToUpdate.isEmpty()) {
			//log something went wrong
			throw new IllegalArgumentException("Check that update string has correct column names");
		}
		String sql = "UPDATE " + "\"" + obj.getClass().getAnnotation(Entity.class).tableName() + "\"" + " SET";
		
        int fieldCounter = 0;
        for (Field field : fieldsToUpdate) {

                fieldCounter++;
                sql += " " + field.getAnnotation(Column.class).columnName() + " = ?";
                
                if (fieldsToUpdate.size() > fieldCounter) sql += ",";    
        }
        sql+=" WHERE "+idName+" = "+id+";";
       	log.info("SQL UPDATE called with query: "+sql);
        
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Long newId = 0L;
		try {
			pstmt = conn.prepareStatement(sql);
			int fieldCounter3 = 1;
			for (Field f : fieldsToUpdate) {
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
					log.warn(e);
				} catch (SQLException e) {
					e.printStackTrace();
					log.warn(e);
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
					e.printStackTrace();
					log.warn(e);
				}
				if (pstmt != null) {
					try {
						pstmt.close();
					} catch (SQLException e) {
						e.printStackTrace();
						log.warn(e);
					}
				}
			}
		}
        
		log.info("UPDATE OBJECT IN DB SUCCESFUL");
		return true;
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
		log.info("SQL INSERT called with query: "+sql);
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
					log.warn(e);
				} catch (SQLException e) {
					e.printStackTrace();
					log.warn(e);
				}
			}
			//TODO log something here
			pstmt.execute();
			rs = pstmt.getGeneratedKeys();
			//newId = rs.getLong("id"); //in case we want to return the id.
			//System.out.println(newId); //here for debugging
			
		} catch (SQLException e) {
			e.printStackTrace();
			log.warn(e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
					log.warn(e);
				}
				if (pstmt != null) {
					try {
						pstmt.close();
					} catch (SQLException e) {
						e.printStackTrace();
						log.warn(e);
					}
				}
			}
		}
		log.info("INSERT OBJECT IN DB SUCCESFUL");
		return true;
	}
}

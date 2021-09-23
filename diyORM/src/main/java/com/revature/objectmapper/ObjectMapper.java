  
package com.revature.objectmapper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.log4j.Logger;

import com.revature.util.ConnectionFactory;


public class ObjectMapper {

	private static final ObjectMapper objectMapper = new ObjectMapper();
	private static final ObjectRemover objectRemover = new ObjectRemover();
	private static final ObjectSaver objectSaver = new ObjectSaver();
	private static final ObjectGetter objectGetter = new ObjectGetter();
	private static Logger log = Logger.getLogger(ObjectMapper.class);
	protected void setStatement(PreparedStatement pstmt, ParameterMetaData pd, Method getter, Object obj, int index) {
    
			try {
				setPreparedStatementByType(pstmt,pd.getParameterTypeName(index),String.valueOf(getter.invoke(obj)),index);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | SQLException e) {
				log.error("Encoutered error " + e + " at method setStatement()");
			}
	}
	
	public static ObjectMapper getInstance() {
		// TODO Auto-generated method stub
		return objectMapper;
	}
	/**
	 * @param prepares  statement to set
	 * @param parameter type
	 * @param input     that represents the value to be placed in the preapred
	 *                  statement
	 * @param index     to plave the value at
	 */
	protected void setPreparedStatementByType(PreparedStatement pstmt, String type, String input, int index) {

		// find some way to evalutate the Java type of the type param
		try {
    
		switch(type) {
		case "text":
		case "String":
		case "varchar":
			pstmt.setString(index, input);
			break;
		case "int":
			pstmt.setInt(index, Integer.parseInt(input));
			break;
		case "double":
			pstmt.setDouble(index, Double.parseDouble(input));
			break;
		case "long":
			pstmt.setLong(index, Long.parseLong(input));
			break;
		case "float":
			pstmt.setFloat(index, Float.parseFloat(input));
			break;
		case "byte":
			pstmt.setByte(index, Byte.parseByte(input));
			break;
		case "Timestamp":
			pstmt.setTimestamp(index, Timestamp.valueOf(input));
			break;
		case "date":
			pstmt.setDate(index, Date.valueOf(input));
			break;
			
		// TODO fill out all remaining possible database types
			
		}
		
		} catch (SQLException e) {
			log.error("Encoutered error " + e + " at method setPreparedStatement()");
		}

	}

	public boolean removeObjectFromDb(Object obj, Connection conn) {
		return objectRemover.removeObjectFromDb(obj, conn);
	}
	public boolean addObjectToDB(final Object obj,Connection conn) {
		return objectSaver.addObjectToDB(obj,conn);
	}
	

}

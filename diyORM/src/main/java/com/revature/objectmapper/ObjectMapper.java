package com.revature.objectmapper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ObjectMapper {
	
	protected void setStatement(PreparedStatement pstmt, ParameterMetaData pd, Method getter, Object obj, int index) {
			try {
				setPreparedStatementByType(pstmt,pd.getParameterTypeName(index),String.valueOf(getter.invoke(obj)),index);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	/**
	 * @param prepared statement to set
	 * @param parameter type
	 * @param input that represents the value to be placed in the prepared statement
	 * @param index to place the value at
	 */
	protected void setPreparedStatementByType(PreparedStatement pstmt, String type, String input, int index) {
		
		//find some way to evaluate the Java type of the type param
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
			
		//may want to account for others. Timestamp, float, etc...
			
		}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}

}

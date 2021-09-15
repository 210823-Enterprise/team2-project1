package com.revature.util;

import java.lang.reflect.Field;

import com.revature.annotations.Column;

public class ColumnField {
	//@Column
	//private String name; // how do I determine if this is a Varchar or Numeric or Serial primary Key?
	
	
	private Field field;
	
	public ColumnField(Field field) {
		
		if (field.getAnnotation(Column.class) == null) {
			// If the field object that we pass through DOESN't have the column annotation, then it returns null
			throw new IllegalStateException("Cannot creat ColumnField Object! Provided field "+getName()+" is not annotated with @Column");
			//throw an exception
		}
		
		this.field = field;
		
	}
	
	public String getName() {
		return field.getName();
	}
	
	public Class<?> getType(){
		return field.getType();
	}
	
	public String getColumnName() {
		return field.getAnnotation(Column.class).columnName();
	}
	

}




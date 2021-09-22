package com.revature.objectmapper;

import java.lang.reflect.Field;

public class ObjectSaver extends ObjectMapper{
	
	//Update columns will be comma separated string
	public boolean UpdateObjectInDB(final Object obj,final String update_columns) {
		return false;
	}
	
	public boolean addObjectToDB(final Object obj) {
		Field[] fields = obj.getClass().getDeclaredFields();
		for (Field f:fields) {
			Class t = f.getType();
			try {
				System.out.println(f.getName()+" with value: "+f.get(obj));
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}
}

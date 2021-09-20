package com.revature.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.revature.annotations.Column;
import com.revature.annotations.Entity;
import com.revature.annotations.Id;
import com.revature.annotations.JoinColumn;

public class MetaModel<T> {

	private Class<T> clazz;
	private IdField primarykeyField;
	private List<ColumnField> columnFields;
	private List<ForeignKeyField> foreignKeyFields;
	
	// of() method to take in a class and transform it to a meta model
	public static <T> MetaModel<T> of(Class<T> clazz) {
		
		// first we have to check that it's marked with the Entity annotation
		if (clazz.getAnnotation(Entity.class) == null) {
			throw new IllegalStateException("Cannot create Metamodel object! Provided class " + clazz.getName() + " is not annotated with @Entity");
		}
		return new MetaModel<>(clazz);		
	}
	
	public MetaModel(Class<T> clazz) {
		this.clazz = clazz;
		this.columnFields = new LinkedList<>();
		
	}
	
	public MetaModel(Class<T> clazz, IdField primarykeyField, List<ColumnField> columnFields,
			List<ForeignKeyField> foreignKeyFields) {
		super();
		this.clazz = clazz;
		this.primarykeyField = primarykeyField;
		this.columnFields = columnFields;
		this.foreignKeyFields = foreignKeyFields;
	}

	public MetaModel(Class<T> clazz, HashMap<String, Method> getters, HashMap<Method, String[]> setters,
			Constructor<?> constructor, String entity_name, String pk) {
		// TODO Auto-generated constructor stub
		super();
		this.clazz = clazz;
		
		
	}

	// class name is com.revature.MyClass
	public String getClassName() {
		return clazz.getName();
	}
	
	
	// simple class name is just MyClass
	public String getSimpleClassName() {
		return clazz.getSimpleName();
	}
	
    public IdField getPrimaryKey() {

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            Id primaryKey = field.getAnnotation(Id.class);
            if (primaryKey != null) {
                return new IdField(field);
            }
        }
        throw new RuntimeException("Did not find a field annotated with @Id in: " + clazz.getName());
    }

    public List<ColumnField> getColumns() {

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            Column column = field.getAnnotation(Column.class);
            if (column != null) {
                columnFields.add(new ColumnField(field));
            }
        }

        if (columnFields.isEmpty()) {
            throw new RuntimeException("No columns found in: " + clazz.getName());
        }

        return columnFields;
    }

    public List<ForeignKeyField> getForeignKeys() {

        List<ForeignKeyField> foreignKeyFields = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();
        
        for (Field field : fields) {
        	
            JoinColumn column = field.getAnnotation(JoinColumn.class);
            
            if (column != null) {
                foreignKeyFields.add(new ForeignKeyField(field));
            }
        }

        return foreignKeyFields;

    }
}

package com.revature.objectmapper;

import java.util.List;
import java.util.Optional;

public class ObjectGetter extends ObjectMapper{
	/*
	 * Gets a list of all objects in the database which match the included search criteria
	columns - comma separated list of columns to search by.
	conditions - comma separated list the values the columns should match to.
	operators - comma separated list of operators to apply to columns (AND/OR) in order that they should be applied.
	 */
	public Optional<List<Object>> getListObjectFromDB(final Class <?> clazz, final String columns, final String conditions){
		return null;
	}
	public Optional<List<Object>> getListObjectFromDB(final Class <?> clazz, final String columns, final String conditions,final String operators){
		return null;
	}
	public Optional<List<Object>> getListObjectFromDB(final Class<?> clazz){
		return null;
	}

}

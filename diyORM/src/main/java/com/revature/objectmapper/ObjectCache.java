package com.revature.objectmapper;

import java.util.HashMap;
import java.util.HashSet;


/**
 * This class handles caching object stored in db tables.
 * Singleton (only one cache at any time)
 *
 */
public class ObjectCache {
	
	private final HashMap<Class<?>,HashSet<Object>> cache;
	static private final ObjectCache obj_cache = new ObjectCache();
	
	private ObjectCache() {
		super();
		cache = new HashMap<>();
		
	}
	
	public static ObjectCache getInstance() {
		return obj_cache;
	}
	
	//methods like: add object to cache, remove from cache...
	

}

package com.revature.objectmapper;

import java.util.HashMap;
import java.util.HashSet;

import org.apache.log4j.Logger;


/**
 * This class handles caching object stored in db tables.
 * Singleton (only one cache at any time)
 *
 */
public class ObjectCache {
	
	public final HashMap<Class<?>,HashSet<Object>> cache = new HashMap<Class<?>, HashSet<Object>>();
	static private final ObjectCache obj_cache = new ObjectCache();
	
	public static ObjectCache getInstance() {
		return obj_cache;
	}
	
	//methods like: add object to cache, remove from cache...
	public static void addObjToCache(Class<?> clazz, Object obj) {
		if(getInstance().cache.containsKey(clazz)) {
			getInstance().cache.get(clazz).add(obj);
		} else {
			getInstance().cache.put(clazz, new HashSet<>());
			getInstance().cache.get(clazz).add(obj);
		}
	}
	
	public static void removeObjFromCache(Class<?> clazz, Object obj) {
		getInstance().cache.get(clazz).remove(obj);
		if(getInstance().cache.get(clazz).isEmpty()) {
			getInstance().cache.remove(clazz);
		}
	}

}

package com.revature.objectmapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.exceptions.NotInCacheException;

/**
 * This class handles caching object stored in db tables. Singleton (only one
 * cache at any time)
 *
 */
public class ObjectCache {

	public final HashMap<Class<?>, HashSet<Object>> cache = new HashMap<Class<?>, HashSet<Object>>();
	static private final ObjectCache obj_cache = new ObjectCache();

	static Logger log = Logger.getLogger(ObjectCache.class);
	
	// To return the cache you need to use: getInstance().cache
	public static ObjectCache getInstance() {
		return obj_cache;
	}

	// Adds an Object to the cache and adds the Class if it does not exist
	public static void addObjToCache(Class<?> clazz, Object obj) throws NotInCacheException {
		if (getInstance().cache.containsKey(clazz)) {
			if (getInstance().cache.get(clazz).contains(obj)) {
				throw new NotInCacheException("Object " + obj.toString() + " already exists in cache");
			} else
				getInstance().cache.get(clazz).add(obj);

		} else {
			getInstance().cache.put(clazz, new HashSet<>());
			getInstance().cache.get(clazz).add(obj);
		}
	}
	
	// Remove an Object from the cache & if it is the last Object of that class then the class is removed as well
	public static void removeObjFromCache(Class<?> clazz, Object obj) throws NotInCacheException {
		if (!getInstance().cache.containsKey(clazz)) {
			throw new NotInCacheException("Class " + clazz.getSimpleName() +" does not exist in the cache.");
		} else if (!getInstance().cache.get(clazz).contains(obj)) {
			throw new NotInCacheException("Obj " + obj.toString() +" does not exist in the cache.");
		} else {
			getInstance().cache.get(clazz).remove(obj);
			if (getInstance().cache.get(clazz).isEmpty()) {
				getInstance().cache.remove(clazz);
			}
		}
	}
	
	// Returns the list of Objects from a Class in the cache
	public static HashSet<Object> getObjsFromCache(Class<?> clazz) throws NotInCacheException {
		if (!getInstance().cache.containsKey(clazz)) {
			throw new NotInCacheException("Class " + clazz.getSimpleName() +" does not exist in the cache.");
		} else return getInstance().cache.get(clazz);
	}
	
	// Clears the cache
	public static void emptyCache() {
		getInstance().cache.clear();
	}

}
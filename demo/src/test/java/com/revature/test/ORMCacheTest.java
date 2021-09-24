package com.revature.test;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.HashSet;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.revature.exceptions.NotInCacheException;
import com.revature.models.Account;
import com.revature.objectmapper.ObjectCache;

public class ORMCacheTest {
	
	private Account acc;
	private Class<?> clazz;
	private HashMap<Class<?>, HashSet<Object>> hmTest;
	
	@Before
	public void setup() {
		acc = new Account(4, "nate", 3, 700.65);
		hmTest = new HashMap<Class<?>, HashSet<Object>>();
		hmTest.put(clazz, new HashSet<>());
		hmTest.get(clazz).add(acc);
	}
	
	@After
	public void teardown() {
		acc = null;
		hmTest = null;
		ObjectCache.emptyCache();
	}
	
	@Test
	public void addObjToCache_success() throws NotInCacheException {
		clazz = Account.class; 
		
		ObjectCache.addObjToCache(clazz, acc);
		
		hmTest = ObjectCache.getInstance().cache;
		
		assertEquals(hmTest.get(clazz), hmTest.get(clazz));
	}
	
	@Test
	public void removeObjFromCache_success() throws NotInCacheException {
		clazz = Account.class;
		
		ObjectCache.addObjToCache(clazz, acc);
		
		hmTest = ObjectCache.getInstance().cache;
		
		ObjectCache.removeObjFromCache(clazz, acc);
		
		assertEquals(hmTest.get(clazz), hmTest.get(clazz));
	}

}
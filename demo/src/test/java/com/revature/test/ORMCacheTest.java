package com.revature.test;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.HashSet;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.revature.models.Account;
import com.revature.objectmapper.ObjectCache;

public class ORMCacheTest {
	
	private Account acc;
	private Class<?> clazz;
	private HashMap<Class<?>, HashSet<Object>> hmTest = new HashMap<Class<?>, HashSet<Object>>();
	
	@Before
	public void setup() {
		acc = new Account(4, "nate", 3, 700.65);
	}
	
	@After
	public void teardown() {
		acc = null;
	}
	
	@Test
	public void addObjToCache_success() {
		clazz = Account.class; 
		hmTest.put(clazz, new HashSet<>());
		hmTest.get(clazz).add(acc);
		
		ObjectCache.addObjToCache(clazz, acc);
		
		ObjectCache.getInstance();
		HashMap<Class<?>, HashSet<Object>> hm = ObjectCache.getInstance().cache;
		
		
		assertEquals(hmTest.get(clazz), hm.get(clazz));
	}
	
	@Test
	public void removeObjToCache_success() {
		clazz = Account.class;
		
		ObjectCache.addObjToCache(clazz, acc);
		
		HashMap<Class<?>, HashSet<Object>> hm = ObjectCache.getInstance().cache;
		
		ObjectCache.removeObjFromCache(clazz, acc);
		
		assertEquals(hmTest.get(clazz), hm.get(clazz));
	}

}

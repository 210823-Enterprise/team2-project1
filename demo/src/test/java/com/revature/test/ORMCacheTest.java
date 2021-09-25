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
	private HashMap<Class<?>, HashSet<Object>> hmAccountTest;
	
	@Before
	public void setup() {
		clazz = Account.class;
		acc = new Account(4, "nate", 3, 700.65);
		hmAccountTest = new HashMap<Class<?>, HashSet<Object>>();
		hmAccountTest.put(clazz, new HashSet<>());
		hmAccountTest.get(clazz).add(acc);
	}
	
	@After
	public void teardown() {
		acc = null;
		hmAccountTest = null;
		ObjectCache.emptyCache();
	}
	
	@Test
	public void getCache_success() throws NotInCacheException {
		
		ObjectCache.addObjToCache(clazz, acc);
		
		assertEquals(hmAccountTest, ObjectCache.getCache());
	}
	
	@Test
	public void addObjToCache_success() throws NotInCacheException {
		
		ObjectCache.addObjToCache(clazz, acc);
		
		assertEquals(hmAccountTest.get(clazz), hmAccountTest.get(clazz));
	}
	
	@Test
	public void removeObjFromCache_success() throws NotInCacheException {
		
		ObjectCache.addObjToCache(clazz, acc);
		
		ObjectCache.removeObjFromCache(clazz, acc);
		
		assertEquals(hmAccountTest.get(clazz), hmAccountTest.get(clazz));
	}
	

}
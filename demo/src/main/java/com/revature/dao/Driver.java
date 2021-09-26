package com.revature.dao;
import java.sql.Connection;

import com.revature.dummymodels.Test;
import com.revature.models.Account;
import com.revature.objectmapper.ObjectMapper;
import com.revature.util.Configuration;
import com.revature.util.ConnectionFactory;
import com.revature.util.TransactionController;

public class Driver {
	public static void main(String[] args) {
		Configuration cfg = new Configuration();
		Connection cn = ConnectionFactory.getConnection();
		ObjectMapper om = new ObjectMapper();
		TransactionController tc = new TransactionController();
		
		System.out.println("============================================================================");
		System.out.println("========================= Transaction Demo =================================");
		System.out.println("============================================================================");
		System.out.println("======================= Database Start State ===============================");
		for (Object o:om.getListObjectFromDB(Account.class, cn)) {
			System.out.println(o);
		}
		System.out.println("==========================Transaction Start=================================");
		tc.setTransaction(cn);
		
		
	}
	
}

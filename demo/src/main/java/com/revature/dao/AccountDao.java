package com.revature.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import com.revature.models.Account;
import com.revature.objectmapper.ObjectMapper;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.revature.util.ConnectionFactory;
import com.revature.util.HibernateUtil;
import com.revature.util.TransactionController;

public class AccountDao {
	Connection cn = null;
	TransactionController tc = null;
	ObjectMapper om = null;
	
	
	public AccountDao() {
		
		this.tc = new TransactionController();
		this.om = new ObjectMapper();
		ConnectionFactory.setPath("C:/Users/ryanm/Desktop/Project1/team2-project1/demo/src/main/resources");
		this.cn = ConnectionFactory.getConnection();
	}

	public boolean insert(Account a) {

		
		

		boolean inserted = om.addObjectToDB(a, cn);
		
		tc.beginCommit(cn);

		return inserted;

	}

	public boolean update(Account a){
		
		
		boolean updated = om.UpdateObjectInDB(a, "accountName, ownerId, balance", cn);
		
		tc.beginCommit(cn);
		
		//TODO check updated object or do try/catch for hibernate expection
		return updated;
	}

	public boolean delete(Account a) {
	    
		boolean deleted = om.removeObjectFromDb(a, cn);
        
		tc.beginCommit(cn);
		
		//TODO check deleted object or do try/catch for hibernate expection
		return deleted;
	}

	public List<Account> findAll() {
		
		ArrayList<Account> arrAcc = new ArrayList<Account>();
		
		List<Object> arrObj = om.getListObjectFromDB(Account.class, cn);
		
		for(Object o : arrObj) {
			arrAcc.add((Account) o);
		}
		return arrAcc;
	}
}

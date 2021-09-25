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

	public boolean insert(Account a) {

		Connection cn = ConnectionFactory.getConnection();

		TransactionController tc = new TransactionController();
		
		ObjectMapper om = new ObjectMapper();

		boolean inserted = om.addObjectToDB(a, cn);
		
		tc.beginCommit(cn);

		return inserted;

	}

	public boolean update(Account a){
		
		Connection cn = ConnectionFactory.getConnection();

		TransactionController tc = new TransactionController();
		
		ObjectMapper om = new ObjectMapper();
		
		boolean updated = om.UpdateObjectInDB(a, "id, accountName, ownerId, balance", cn);
		
		tc.beginCommit(cn);
		
		//TODO check updated object or do try/catch for hibernate expection
		return updated;
	}

	public boolean delete(Account a) {
		
		Connection cn = ConnectionFactory.getConnection();

		TransactionController tc = new TransactionController();
		
		ObjectMapper om = new ObjectMapper();
	    
		boolean deleted = om.removeObjectFromDb(a, cn);
        
		tc.beginCommit(cn);
		
		//TODO check deleted object or do try/catch for hibernate expection
		return deleted;
	}

	public List<Account> findAll() {

		Connection cn = ConnectionFactory.getConnection();

		TransactionController tc = new TransactionController();
		
		ObjectMapper om = new ObjectMapper();
		
		ArrayList<Account> arrAcc = new ArrayList<Account>();
		
		List<Object> arrObj = om.getListObjectFromDB(Account.class, cn);
		
		for(Object o : arrObj) {
			arrAcc.add((Account) o);
		}

		return arrAcc;
	}
}

package com.revature.dao;

import java.util.List;
import com.revature.models.Account;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.revature.util.HibernateUtil;

public class AccountDao {

	public int insert(Account a) {

		Session ses = HibernateUtil.getSession();
		Transaction tx = ses.beginTransaction();

		int pk = (int) ses.save(a);
		
		tx.commit();

		return pk;

	}

	public boolean update(Account a) {
		
		Session ses = HibernateUtil.getSession();
		Transaction tx = ses.beginTransaction();
		ses.update(a);
		
		tx.commit();
		
		//TODO check updated object or do try/catch for hibernate expection
		return false;
	}

	public boolean delete(int id) {
		
		Session ses = HibernateUtil.getSession();
		Transaction tx = ses.beginTransaction();
		
	    Query q = ses.createQuery("delete from Account where id = :id ");
	    q.setParameter("id", id);
        q.executeUpdate();
        
		tx.commit();
		
		//TODO check deleted object or do try/catch for hibernate expection
		return false;
	}

	public List<Account> findAll() {

		Session ses = HibernateUtil.getSession();

		List<Account> accounts = ses.createQuery("from Account", Account.class).list();

		return accounts;
	}
}

package com.revature.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.revature.models.User;
import com.revature.util.HibernateUtil;

public class UserDao {

	public int insert(User u) {

		Session ses = HibernateUtil.getSession();
		Transaction tx = ses.beginTransaction();

		int pk = (int) ses.save(u);
		tx.commit();

		return pk;
	}

	public boolean update(User u) {
		
		Session ses = HibernateUtil.getSession();
		Transaction tx = ses.beginTransaction();
		ses.update(u);
		tx.commit();
		
		//TODO check updated object or do try/catch for hibernate expection
		return false;
	}

	public boolean delete(User u) {
		
		Session ses = HibernateUtil.getSession();
		Transaction tx = ses.beginTransaction();
		ses.delete(u);
		tx.commit();
		
		//TODO check deleted object or do try/catch for hibernate expection
		return false;
	}

	public List<User> findAll() {

		Session ses = HibernateUtil.getSession();

		List<User> users = ses.createQuery("from User", User.class).list();

		return users;
	}
}

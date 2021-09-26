package com.revature.service;

import java.util.List;

import com.revature.dao.AccountDao;
import com.revature.models.Account;

public class AccountService {

	private AccountDao adao;
	
	public AccountService(AccountDao dao)
	{
		super();
		this.adao = dao;
	}

	public boolean insert(Account e) {
		return adao.insert(e);

	}

	public boolean update(Account e) {

		return adao.update(e);

	}

	public boolean delete(Account a) {
		return adao.delete(a);

	}

	public List<Account> findAll() {
		return adao.findAll();

	}
}

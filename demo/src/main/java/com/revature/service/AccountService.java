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

	public int insert(Account e) {

		return adao.insert(e);

	}

	public boolean update(Account e) {

		return adao.update(e);

	}

	public boolean delete(Account e) {

		return adao.delete(e);

	}

	public List<Account> findAll() {
		return adao.findAll();

	}
}

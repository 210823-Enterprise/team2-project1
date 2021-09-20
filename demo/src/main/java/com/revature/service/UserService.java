package com.revature.service;

import java.util.List;

import com.revature.dao.UserDao;
import com.revature.models.User;

/**
 * This class is currently not in use.
 * @author jbwyk
 *
 */
public class UserService {

	private UserDao udao;

	public UserService(UserDao dao) {
		super();
		this.udao = dao;
	}

	public int insert(User u) {

		return udao.insert(u);

	}

	public boolean update(User u) {

		return udao.update(u);

	}

	public boolean delete(User u) {

		return udao.delete(u);

	}

	public List<User> findAll() {
		return udao.findAll();

	}
}

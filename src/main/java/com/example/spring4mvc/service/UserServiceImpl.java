package com.example.spring4mvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.spring4mvc.dao.UserDao;
import com.example.spring4mvc.model.User;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao userDao;
	
	@Override
	@Transactional(readOnly=true)
	public List<User> findAllUsers() {
		return userDao.findAllUser();
	}

}

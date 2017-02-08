package com.example.spring4mvc.dao;

import com.example.spring4mvc.model.User;

public interface UserDao {

	public User findById(int id);
	
	public Integer save(User user);
}

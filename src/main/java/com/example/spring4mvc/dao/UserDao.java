package com.example.spring4mvc.dao;

import java.util.List;

import com.example.spring4mvc.model.User;

public interface UserDao {

	public User findById(int id);
	
	public Integer save(User user);
	
	public List<User> findAllUser();
}

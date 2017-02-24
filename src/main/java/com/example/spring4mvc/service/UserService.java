package com.example.spring4mvc.service;

import java.util.List;

import com.example.spring4mvc.model.User;

public interface UserService {

	List<User> findAllUsers();
	
}

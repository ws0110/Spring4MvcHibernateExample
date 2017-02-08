package com.example.spring4mvc.dao;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.spring4mvc.model.User;

@Repository
public class UserDaoImpl implements UserDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	private Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
	private Criteria createEntityCriteria(){
		return getSession().createCriteria(User.class);
	}
	
	@Override
	public User findById(int id) {
		User user = (User) getSession().get(User.class, id);
		if(user != null){
			Hibernate.initialize(user.getUserProfiles());
		}
		return user;
	}

	@Override
	public Integer save(User user) {
		return (Integer) getSession().save(user);
		
	}

}

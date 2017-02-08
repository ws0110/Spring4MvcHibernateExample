package com.example.spring4mvc.dao;

import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.example.spring4mvc.configuration.HibernateConfigurationTest;
import com.example.spring4mvc.model.User;
import com.example.spring4mvc.model.UserProfile;

import junit.framework.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=HibernateConfigurationTest.class)
@Transactional
@TransactionConfiguration(defaultRollback=false)
public class UserDaoTest {

	@Autowired
	UserDao userDao;
	
	private User getUser(){
		User user = new User();
		user.setSsoId("22345");
		user.setName("user2");
		return user;
	}
	
	@Test
	public void saveUser(){
		Set<UserProfile> profiles = new HashSet<>();
		UserProfile profile = new UserProfile();
		profile.setId(1);
		profile.setType("ADMIN");
		profiles.add(profile);
		
		User user = getUser();
		user.setUserProfiles(profiles);
		Integer id = userDao.save(user);
		System.out.println(id);
		Assert.assertNotNull(id);
	}
	
	@Test
	public void findById(){
		User user = userDao.findById(3);
		System.out.println(user);
	}
}

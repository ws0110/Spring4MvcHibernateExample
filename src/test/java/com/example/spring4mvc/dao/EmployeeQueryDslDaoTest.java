package com.example.spring4mvc.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.example.spring4mvc.configuration.HibernateConfigurationTest;
import com.example.spring4mvc.model.Employee;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=HibernateConfigurationTest.class)
@Transactional
@TransactionConfiguration(defaultRollback=false)
public class EmployeeQueryDslDaoTest {
	
	@Autowired
	@Qualifier("employeeQueryDslDaoImpl")
	EmployeeDao employeeDao;
	
	@Test
	public void countAll(){
		Long count = employeeDao.countAllEmployee();
		System.out.println(count);
		Assert.assertNotNull(count);
	}
	
	@Test
	public void findAll(){
		List<Employee> list = employeeDao.findAllEmployees();
		for(Employee em: list){
			System.out.println(em);
		}
	}
	
	@Test
	public void findBySsn(){
		String ssn = "12345";
		Employee employee = employeeDao.findEmployeeBySsn(ssn);
		System.out.println(employee);
		Assert.assertEquals(employee.getSsn(), ssn);
	}
	
}

package com.example.spring4mvc.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.transaction.Transactional;

import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.example.spring4mvc.configuration.HibernateConfigurationTest;
import com.example.spring4mvc.model.Employee;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HibernateConfigurationTest.class})
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class EmployeeDaoTest {

	@Autowired
	@Qualifier("employeeDaoImpl")
	EmployeeDao employeeDao;
	
	public Employee getSampleEmployee(){
		Employee employee = new Employee();
		employee.setName("Karen");
		employee.setSsn("12345");
		employee.setSalary(new BigDecimal(10980));
		employee.setJoiningDate(new LocalDate());
		return employee;
	}
	
	@Test
	public void saveEmployeeTest(){
		employeeDao.saveEmployee(getSampleEmployee());
		
		Assert.assertEquals(employeeDao.countAllEmployee(), Long.valueOf(1));
	}
	
	@Test
	public void findByIdTest(){
		Assert.assertNull(employeeDao.findById(1));
		Assert.assertNotNull(employeeDao.findById(6));
	}
	
	@Test
	public void findAllTest(){
		List<Employee> list = employeeDao.findAllEmployees();
		for(Employee em: list){
			System.out.println(em);
		}
	}
	
	@Test
	public void findBySsn(){
		Assert.assertNull(employeeDao.findEmployeeBySsn("99999"));
		Assert.assertNotNull(employeeDao.findEmployeeBySsn("12345"));
	}
	
}

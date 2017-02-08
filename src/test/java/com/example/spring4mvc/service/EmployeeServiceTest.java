package com.example.spring4mvc.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.spring4mvc.configuration.HibernateConfigurationTest;
import com.example.spring4mvc.dao.EmployeeDao;
import com.example.spring4mvc.model.Employee;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HibernateConfigurationTest.class})
public class EmployeeServiceTest {

	@Mock
	EmployeeDao dao;
	
	@InjectMocks
	EmployeeServiceImpl service;
	
	@Spy
	List<Employee> employees = new ArrayList<>();
	
	public List<Employee> getEmployeeList(){
		Employee e1 = new Employee();
		e1.setId(1);
		e1.setName("Axel");
		e1.setJoiningDate(new LocalDate());
		e1.setSalary(new BigDecimal(10000));
		e1.setSsn("XXX111");
		
		Employee e2 = new Employee();
		e2.setId(2);
		e2.setName("Jeremy");
		e2.setJoiningDate(new LocalDate());
		e2.setSalary(new BigDecimal(20000));
		e2.setSsn("XXX222");
		
		employees.add(e1);
		employees.add(e2);
		return employees;
	}
	
	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		employees = getEmployeeList();
	}
	
	@Test
	public void findById(){
		Employee em = employees.get(0);
		when(dao.findById(anyInt())).thenReturn(em);
		Assert.assertEquals(service.findById(anyInt()), em);
	}
	
	@Test
	public void saveEmployee(){
		doNothing().when(dao).saveEmployee(any(Employee.class));
		
		service.saveEmployee(any(Employee.class));
		verify(dao, atLeastOnce()).saveEmployee(any(Employee.class));
	}
	
	@Test
	public void updateEmployee(){
		Employee em = employees.get(0);
		when(dao.findById(anyInt())).thenReturn(em);
		service.updateEmployee(em);
		verify(dao, atLeastOnce()).findById(anyInt());
	}
	
	@Test
	public void findAllEmployees(){
		when(dao.findAllEmployees()).thenReturn(employees);
		Assert.assertEquals(service.findAllEmployees(), employees);
	}
	
	@Test
	public void findEmployeeBySsn(){
		Employee em = employees.get(0);
		when(dao.findEmployeeBySsn(anyString())).thenReturn(em);
		Assert.assertEquals(service.findEmployeeBySsn(anyString()), em);
	}
	
	
	@Test
	public void isEmployeeSsnUnique(){
		Employee em = employees.get(0);
		when(dao.findEmployeeBySsn(anyString())).thenReturn(em);
		Assert.assertEquals(service.isEmployeeSsnUnique(em.getId(), em.getSsn()), true);
	}
	
	
}

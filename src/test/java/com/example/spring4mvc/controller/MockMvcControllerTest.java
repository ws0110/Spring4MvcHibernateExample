package com.example.spring4mvc.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.meta.When;
import javax.net.ssl.SSLEngineResult.Status;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.spring4mvc.configuration.AppConfig;
import com.example.spring4mvc.configuration.AppConfigTest;
import com.example.spring4mvc.configuration.AppInitializer;
import com.example.spring4mvc.configuration.HibernateConfigurationTest;
import com.example.spring4mvc.model.Employee;
import com.example.spring4mvc.service.EmployeeService;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={AppConfigTest.class})
@WebAppConfiguration
public class MockMvcControllerTest {

	MockMvc mockMvc;
	
	@Mock
	EmployeeService employeeService;
	
	@InjectMocks
	AppController apiController;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(apiController).build();
		
		employees = getEmployeeList();
	}
	
	@Spy
	List<Employee> employees = new ArrayList<Employee>();
	
	@Test
	public void listTest() throws Exception{
		
		when(employeeService.findAllEmployees()).thenReturn(employees);
		
		mockMvc.perform(get("/list"))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();
		
	}
	
	
	
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
}

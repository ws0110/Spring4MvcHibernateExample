package com.example.spring4mvc.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.context.MessageSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import com.example.spring4mvc.configuration.HibernateConfigurationTest;
import com.example.spring4mvc.model.Employee;
import com.example.spring4mvc.service.EmployeeService;

import junit.framework.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={HibernateConfigurationTest.class})
public class EmployeeControllerTest {

	@Mock
	EmployeeService employeeService;
	
	@Mock
	MessageSource messageSource;
	
	@InjectMocks
	AppController appController;
	
	@Spy
	List<Employee> employees = new ArrayList<Employee>();
	
	@Spy
	ModelMap model;
	
	@Mock
	BindingResult result;
	
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
	public void listEmployees(){
		when(employeeService.findAllEmployees()).thenReturn(employees);
		
		Assert.assertEquals(appController.listEmployee(model), "allemployees");
		Assert.assertEquals(model.get("employees"), employees);
		verify(employeeService, atLeastOnce()).findAllEmployees();
	}
	
	@Test
	public void newEmployee(){
		Assert.assertEquals(appController.newEmployee(model), "registration");
		Assert.assertNotNull(model.get("employee"));
		Assert.assertFalse((Boolean)model.get("edit"));
		Assert.assertEquals(((Employee)model.get("employee")).getId(), 0);
	}
	
	@Test
	public void saveEmployeeWithValidationError(){
		when(result.hasErrors()).thenReturn(true);
		doNothing().when(employeeService).saveEmployee(any(Employee.class));
		
		Assert.assertEquals(appController.saveEmployee(employees.get(0), result, model), "registration");
	}
	
	@Test
	public void saveEmployeeWithErrorNonUniqueSSN(){
		when(result.hasErrors()).thenReturn(false);
		when(employeeService.isEmployeeSsnUnique(anyInt(), anyString())).thenReturn(false);
		
		Assert.assertEquals(appController.saveEmployee(employees.get(0), result, model), "registration");
	}
	
	@Test
	public void saveEmployeeSuccess(){
		when(result.hasErrors()).thenReturn(false);
		when(employeeService.isEmployeeSsnUnique(anyInt(), anyString())).thenReturn(true);
		doNothing().when(employeeService).saveEmployee(any(Employee.class));
		
		Assert.assertEquals(appController.saveEmployee(employees.get(0), result, model), "success");
	}
	
	@Test
	public void editEmployee(){
		Employee em = employees.get(0);
		when(employeeService.findEmployeeBySsn(anyString())).thenReturn(em);
		Assert.assertEquals(appController.editEmployee(anyString(), model), "registration");
		Assert.assertEquals(model.get("edit"), true);
		Assert.assertEquals(model.get("employee"), em);
	}
	
	@Test
	public void updateEmployeeWithValidationError(){
		when(result.hasErrors()).thenReturn(true);
		doNothing().when(employeeService).updateEmployee(any(Employee.class));
		Assert.assertEquals(appController.updateEmployee(employees.get(0), result, model,""), "registration");
	}

	@Test
	public void updateEmployeeWithValidationErrorNonUniqueSSN(){
		when(result.hasErrors()).thenReturn(false);
		when(employeeService.isEmployeeSsnUnique(anyInt(), anyString())).thenReturn(false);
		Assert.assertEquals(appController.updateEmployee(employees.get(0), result, model,""), "registration");
	}

	@Test
	public void updateEmployeeWithSuccess(){
		when(result.hasErrors()).thenReturn(false);
		when(employeeService.isEmployeeSsnUnique(anyInt(), anyString())).thenReturn(true);
		doNothing().when(employeeService).updateEmployee(any(Employee.class));
		Assert.assertEquals(appController.updateEmployee(employees.get(0), result, model, ""), "success");
		Assert.assertEquals(model.get("success"), "Employee Axel updated successfully");
	}
	
	@Test
	public void deleteEmployee(){
		doNothing().when(employeeService).deleteEmployeeBySsn(anyString());
		Assert.assertEquals(appController.deleteEmployee("123"), "redirect:/list");
	}
	
}

package com.example.spring4mvc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring4mvc.model.Employee;
import com.example.spring4mvc.model.User;
import com.example.spring4mvc.service.EmployeeService;
import com.example.spring4mvc.service.UserService;

@RestController
@RequestMapping("/api/")
public class ApiController {

	@Autowired
	EmployeeService employeeService;
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value={"/", "/list"}, method=RequestMethod.GET)
	//@ResponseBody
	public List<Employee> listEmployee(ModelMap model){
		
		List<Employee> list = employeeService.findAllEmployees();
		return list;
	}
	
	
	@RequestMapping(value={"/users"}, method=RequestMethod.GET)
	public List<User> listUser(ModelMap model){
		
		return userService.findAllUsers();
	}
}

package com.example.spring4mvc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring4mvc.model.Employee;
import com.example.spring4mvc.service.EmployeeService;

@RestController
@RequestMapping("/api/")
public class ApiController {

	@Autowired
	EmployeeService employeeService;
	
	
	@RequestMapping(value={"/", "/list"}, method=RequestMethod.GET)
	//@ResponseBody
	public List<Employee> listEmployee(ModelMap model){
		
		List<Employee> list = employeeService.findAllEmployees();
		return list;
	}
}

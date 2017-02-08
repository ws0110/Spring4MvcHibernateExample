package com.example.spring4mvc.controller;

import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.spring4mvc.model.Employee;
import com.example.spring4mvc.service.EmployeeService;

@Controller
@RequestMapping("/")
public class AppController {

	@Autowired
	EmployeeService employeeService;
	
	@Autowired
	MessageSource messageSource;
	
	@RequestMapping(value={"/", "/list"}, method=RequestMethod.GET)
	public String listEmployee(ModelMap model){
		
		List<Employee> list = employeeService.findAllEmployees();
		model.addAttribute("employees", list);
		return "allemployees";
	}
	
	@RequestMapping(value={"/new"}, method=RequestMethod.GET)
	public String newEmployee(ModelMap model){
		Employee emp = new Employee();
		model.addAttribute("employee", emp);
		model.addAttribute("edit", false);
		return "registration";
	}
	
	@RequestMapping(value={"/new"}, method=RequestMethod.POST)
	public String saveEmployee(@Valid Employee emp, BindingResult result, ModelMap model){
		
		if(result.hasErrors()){
			return "registration";
		}
		
		if(!employeeService.isEmployeeSsnUnique(emp.getId(), emp.getSsn())){
			FieldError ssnError = new FieldError("employee", "ssn", messageSource.getMessage("non.unique.ssn", new String[]{emp.getSsn()}, Locale.getDefault()));
			result.addError(ssnError);
			return "registration";
		}
		
		employeeService.saveEmployee(emp);
		
		model.addAttribute("success", "Employee " + emp.getName()  + " updated successfully");
       return "success";
	}
	
	@RequestMapping(value={"/edit-{ssn}-employee"}, method=RequestMethod.GET)
	public String editEmployee(@PathVariable String ssn, ModelMap model){
		Employee emp = employeeService.findEmployeeBySsn(ssn);
		model.addAttribute("employee", emp);
       model.addAttribute("edit", true);
       return "registration";
	}
	
	@RequestMapping(value={"/edit-{ssn}-employee"}, method=RequestMethod.POST)
	public String updateEmployee(@Valid Employee emp, BindingResult result, ModelMap model, @PathVariable String ssn){
		if(result.hasErrors()){
			return "registration";
		}
		
		if(!employeeService.isEmployeeSsnUnique(emp.getId(), emp.getSsn())){
			FieldError ssnError = new FieldError("employee", "ssn", messageSource.getMessage("non.unique.ssn", new String[]{emp.getSsn()}, Locale.getDefault()));
			result.addError(ssnError);
			return "registration";
		}
		
		employeeService.updateEmployee(emp);
		 
       model.addAttribute("success", "Employee " + emp.getName()  + " updated successfully");
       return "success";
	}
	
	
	@RequestMapping(value={"/delete-{ssn}-employee"}, method=RequestMethod.GET)
	public String deleteEmployee(@PathVariable String ssn){
		employeeService.deleteEmployeeBySsn(ssn);
       return "redirect:/list";
	}
}

package com.example.spring4mvc.dao;

import java.util.List;

import com.example.spring4mvc.model.Employee;

public interface EmployeeDao {

	Employee findById(int id);
	 
    void saveEmployee(Employee employee);
     
    void deleteEmployeeBySsn(String ssn);
     
    List<Employee> findAllEmployees();
 
    Long countAllEmployee();
    
    Employee findEmployeeBySsn(String ssn);
	
}

package com.example.spring4mvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.spring4mvc.dao.EmployeeDao;
import com.example.spring4mvc.model.Employee;

@Service
public class EmployeeServiceImpl implements EmployeeService{

	@Autowired
	private EmployeeDao employeeDao;
	
	
	@Override
	@Transactional(readOnly=true)
	public Employee findById(int id) {
		return employeeDao.findById(id);
	}

	@Override
	@Transactional
	public void saveEmployee(Employee employee) {
		employeeDao.saveEmployee(employee);
		
	}

	@Override
	@Transactional
	public void updateEmployee(Employee employee) {
		Employee entity = employeeDao.findById(employee.getId());
		if(entity!=null){
            entity.setName(employee.getName());
            entity.setJoiningDate(employee.getJoiningDate());
            entity.setSalary(employee.getSalary());
            entity.setSsn(employee.getSsn());
        }
	}

	@Override
	@Transactional
	public void deleteEmployeeBySsn(String ssn) {
		employeeDao.deleteEmployeeBySsn(ssn);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Employee> findAllEmployees() {
		return employeeDao.findAllEmployees();
	}

	@Override
	@Transactional(readOnly=true)
	public Employee findEmployeeBySsn(String ssn) {
		return employeeDao.findEmployeeBySsn(ssn);
	}

	@Override
	@Transactional(readOnly=true)
	public boolean isEmployeeSsnUnique(Integer id, String ssn) {
		Employee employee = employeeDao.findEmployeeBySsn(ssn);
		return (employee == null || (id != null && employee.getId() == id));
	}

}

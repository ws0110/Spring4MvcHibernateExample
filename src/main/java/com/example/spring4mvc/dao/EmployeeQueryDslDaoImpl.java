package com.example.spring4mvc.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.spring4mvc.model.Employee;
import com.example.spring4mvc.model.QEmployee;
import com.mysema.query.jpa.hibernate.HibernateQuery;

@Repository
public class EmployeeQueryDslDaoImpl implements EmployeeDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	private Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public Employee findById(int id) {
		return (Employee) getSession().get(Employee.class, id);
	}

	@Override
	public void saveEmployee(Employee employee) {
		getSession().persist(employee);
	}

	@Override
	public void deleteEmployeeBySsn(String ssn) {
		getSession().delete(findEmployeeBySsn(ssn));
	}

	@Override
	public List<Employee> findAllEmployees() {
		HibernateQuery query = new HibernateQuery(getSession());
		QEmployee qEmployee = QEmployee.employee;
		
		return query.from(qEmployee).list(qEmployee);
	}

	@Override
	public Long countAllEmployee() {
		HibernateQuery query = new HibernateQuery(getSession());
		QEmployee qEmployee = QEmployee.employee;
		
		return query.from(qEmployee).uniqueResult(qEmployee.count());
	}

	@Override
	public Employee findEmployeeBySsn(String ssn) {
		HibernateQuery query = new HibernateQuery(getSession());
		QEmployee qEmployee = QEmployee.employee;
		
		return query.from(qEmployee).where(qEmployee.ssn.eq(ssn)).uniqueResult(qEmployee);
	}

}

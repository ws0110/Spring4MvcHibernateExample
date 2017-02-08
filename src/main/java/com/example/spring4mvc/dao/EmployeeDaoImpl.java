package com.example.spring4mvc.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.spring4mvc.model.Employee;


@Repository
public class EmployeeDaoImpl implements EmployeeDao{

	@Autowired
	SessionFactory sessionFactory;
	
	private Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
	private Criteria createEntityCriteria(){
		return getSession().createCriteria(Employee.class);
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
		Query query = getSession().createQuery("DELETE FROM EMPLOYEE WHERE ssn = :ssn; ");
		query.setString("ssn", ssn);
		query.executeUpdate();
	}

	@Override
	public List<Employee> findAllEmployees() {
		
		return (List<Employee>)createEntityCriteria().list();
	}

	@Override
	public Long countAllEmployee(){
		return (Long)createEntityCriteria().setProjection(Projections.rowCount()).uniqueResult();
	}
	
	@Override
	public Employee findEmployeeBySsn(String ssn) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("ssn", ssn));
		return (Employee) criteria.uniqueResult();
	}

}

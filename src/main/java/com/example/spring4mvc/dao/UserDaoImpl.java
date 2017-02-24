package com.example.spring4mvc.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.spring4mvc.model.User;

@Repository
public class UserDaoImpl implements UserDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	private Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
	private Criteria createEntityCriteria(){
		return getSession().createCriteria(User.class);
	}
	
	@Override
	public User findById(int id) {
		User user = (User) getSession().get(User.class, id);
		if(user != null){
			/**
			 *  Lazy 설정으로 되어 있는 경우 세션이 닫히고 자식객체 콜하면 LazyInitializationException 발생
			 *   -> 필요한 객체만 미리 가져옴
			 *   
			 *   * 다른 방법 : "Open Session In View 패턴" 으로 인터셉터나 필터를 적용,
			 *               "hibernate.enable_lazy_load_no_trans" 설정 등 뷰가 완전히 로딩될 때까지 세션을 열어두는 방법도 있다.
			 */
			Hibernate.initialize(user.getUserProfiles());
		}
		return user;
	}

	@Override
	public Integer save(User user) {
		return (Integer) getSession().save(user);
		
	}

	@Override
	public List<User> findAllUser() {
		return createEntityCriteria().list();
	}

	
	
}

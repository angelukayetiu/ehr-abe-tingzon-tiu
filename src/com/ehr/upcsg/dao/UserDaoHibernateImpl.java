package com.ehr.upcsg.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ehr.upcsg.exceptions.DataAccessException;
import com.ehr.upcsg.exceptions.UserExistException;
import com.ehr.upcsg.model.User;

@Repository("userDao")
@Transactional
public class UserDaoHibernateImpl implements UserDao {

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	public User findUserByUsername(String username){
		Session session = sessionFactory.getCurrentSession();
				
		List<User> users = (List<User>) session.createQuery
				("FROM User user WHERE user.username='"+username+"'").list();
		
		//check for sensitivity
		if (users.isEmpty() || !users.get(0).getUsername().equals(username))
			return null;
		return (User) users.get(0);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public int countUserLikeUsername(String username){
		Session session = sessionFactory.getCurrentSession();
		System.out.println("counting username dao");
		List<User> users = (List<User>) session.createQuery
				("FROM User user WHERE user.username LIKE '"+username+"%'").list();
		return users.size();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public User findUserByEmail(String email){
		Session session = sessionFactory.getCurrentSession();
		System.err.println("findUserByEmail");
		
		List<User> users = (List<User>) session.createQuery
				("FROM User user WHERE user.email='"+email+"'").list();
				
		//check for sensitivity
		if (users.isEmpty() || !users.get(0).getEmail().equals(email))
			return null;
		return (User) users.get(0);
		
	}

	@Override
	public void createUser(User user) throws DataAccessException, UserExistException {
		User databaseUser = findUserByEmail(user.getEmail());
		System.out.println("try to save from dao createUser");
		//check if user exists
		if (databaseUser==null){
			try{
				Session session = sessionFactory.getCurrentSession();				
				session.save(user);	
			} catch (HibernateException e){
				throw new DataAccessException();
			}
		}
		else throw new UserExistException();
	}

	@Override
	public User findUserById(Long id) {
		Session session = sessionFactory.getCurrentSession();
		List<User> users = (List<User>) session.createQuery
				("FROM User user WHERE user.id='"+id+"'").list();		
		//check for sensitivity
		if (users.isEmpty())
			return null;
		return (User) users.get(0);
	}

	@Override
	public void save(User user) throws DataAccessException, UserExistException {
		createUser(user);
	}

	@Override
	public void update(User user) throws DataAccessException, UserExistException {
		User databaseUser = findUserByEmail(user.getEmail());
		
		//check if user exists
		if (databaseUser!=null){
			try{
				Session session = sessionFactory.getCurrentSession();
				session.update(user);
				System.out.println("updated");
			} catch (HibernateException e){
				throw new DataAccessException("Can not access database.");
			}
		}
		else throw new UserExistException("User does not exist");
	}

	@Override
	public List<User> list() {
		Session session = sessionFactory.getCurrentSession();
		List<User> list = (List<User>) session.createCriteria("User").list();
		return list;
	}

	@Override
	public List<User> findAllActive() {
		String hql = "FROM User WHERE enabled = '1'";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		List<User> result = query.list();
		if(result.size() > 0){
			return result;
		}else{
			return null;
		}
	}

	@Override
	public List<User> findAllInactive() {
		String hql = "FROM User WHERE enabled = '0' ORDER BY registerTimestamp DESC";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		List<User> result = query.list();
		if(result.size() > 0){
			return result;
		}else{
			return null;
		}
	}

	@Override
	public List<User> findInactive(String searchKey, String[] searchedFieldNames) {
		StringBuilder hql = new StringBuilder("FROM User WHERE enabled = '0' AND (");
		hql = addSearchRestriction(hql, searchedFieldNames);
		hql.append(')');
		Query query = sessionFactory.getCurrentSession().createQuery(hql.toString());
		query.setParameter("searchKey", '%'+searchKey+'%');
		return query.list();
	}

	private StringBuilder addSearchRestriction(StringBuilder hql,
			String[] searchedFieldNames) {
		hql.append(' ');
		for(String fieldName : searchedFieldNames) {
			hql.append("lower(" + fieldName +") LIKE lower(:searchKey) OR ");
		}
		hql.delete(hql.lastIndexOf(" OR "), hql.length());
		return hql;
	}

	@Override
	public List<User> findActive(String searchKey, String[] searchedFieldNames) {
		StringBuilder hql = new StringBuilder("FROM User WHERE enabled = '1' AND (");
		hql = addSearchRestriction(hql, searchedFieldNames);
		hql.append(')');
		Query query = sessionFactory.getCurrentSession().createQuery(hql.toString());
		query.setParameter("searchKey", '%'+searchKey+'%');
		return query.list();
	}

	@Override
	public void delete(User user) throws DataAccessException,
			UserExistException {
		User databaseUser = findUserByEmail(user.getEmail());
		
		//check if user exists
		if (databaseUser!=null){
			try{
				Session session = sessionFactory.getCurrentSession();
				session.delete(user);
				System.out.println("deleted");
			} catch (HibernateException e){
				throw new DataAccessException("Can not access database.");
			}
		}
		else throw new UserExistException("User does not exist");		
	}
	
}

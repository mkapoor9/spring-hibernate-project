package com.luv2code.springdemo.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.luv2code.springdemo.entity.Customer;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	
	@Override
	public List<Customer> getCustomers(){
		
		Session currentSession = sessionFactory.getCurrentSession();
		
		Query<Customer> theQuery = 
				 currentSession.createQuery("from Customer order by lastName",Customer.class);
		
		List<Customer> customers = theQuery.getResultList();
		
		return customers;
	}


	@Override
	public void saveCustomer(Customer theCustomer) {
		
		Session currentSession = sessionFactory.getCurrentSession();
		
		currentSession.saveOrUpdate(theCustomer);
		
		
	}


	@Override
	public Customer getCustomers(int theId) {
		// TODO Auto-generated method stub
		Session currentSession = sessionFactory.getCurrentSession();
		
		Customer theCustomer = currentSession.get(Customer.class, theId);
		
		return theCustomer;
	}


	@Override
	public void deleteCustomer(int theId) {
		
		Session currentSession = sessionFactory.getCurrentSession();
		
		Customer theCustomer = currentSession.get(Customer.class, theId);
		
		currentSession.delete(theCustomer);
		
	}


	@Override
	public List<Customer> searchCustomers(String theSearchName) {
		
		Session currentSession = sessionFactory.getCurrentSession();
		
		Query<Customer> theQuery = null;
		
		if(theSearchName!=null && theSearchName.trim().length()>0) {
			theQuery = currentSession.createQuery("from Customer where lower(firstName) like :theName or lower(lastName) like :theName"
					                                ,Customer.class);
			theQuery.setParameter("theName","%" + theSearchName.toLowerCase()+"%");
		}
		
		else {
			theQuery = currentSession.createQuery("from Customer",Customer.class);
		}
		
		List<Customer> customers = theQuery.getResultList();
		
		return customers;
		
	}
	
	

	

}

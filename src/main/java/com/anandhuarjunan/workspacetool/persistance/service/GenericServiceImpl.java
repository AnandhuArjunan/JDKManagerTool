package com.anandhuarjunan.workspacetool.persistance.service;

import java.io.Serializable;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.anandhuarjunan.workspacetool.HibernateUtils;
import com.anandhuarjunan.workspacetool.persistance.models.Ides;

public class GenericServiceImpl<T,K extends Serializable> implements GenericService<T,K> {

	Session session = null;


	 public GenericServiceImpl(){
		 session = HibernateUtils.getSessionFactory().openSession();
	 }



	public Session getSession() {
		    return session;
	}

	@Override
	public T find(Class<T> clazz,K id) {
		return  this.getSession().find(clazz, id);
	}

	@Override
	public List<T> findAll(Class<T> clazz) {

		 CriteriaBuilder cb = session.getCriteriaBuilder();
		    CriteriaQuery<T> cq = cb.createQuery(clazz);
		    Root<T> rootEntry = cq.from(clazz);
		    CriteriaQuery<T> all = cq.select(rootEntry);

		    TypedQuery<T> allQuery = this.getSession().createQuery(all);
		    return allQuery.getResultList();
	}


	@Override
	public List<T> find(String queryName, String[] paramNames, Object[] bindValues) {
		return null;
	}

	@Override
	public K save(T instance) {
		return null;
	}

	@Override
	public void update(T instance) {

	}

	@Override
	public void delete(T instance) {

	}



}

package com.sblm.daoImpl;

import java.io.Serializable;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.sblm.dao.ILogDAO;
import com.sblm.model.Auditoria;

@Repository(value = "logDAO")
public class LogDAOImpl implements ILogDAO, Serializable {
	private static final long serialVersionUID = -7132329520845816103L;

	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}


	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}


	@Override
	public void save(String adt) {
		//System.out.println("entro2"+adt.getUrl());
		try {
			getSessionFactory().getCurrentSession().save(adt);
		
				} catch (Exception e) {
		e.printStackTrace();		}
		
	}
	
	
	

}

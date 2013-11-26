package com.sblm.daoImpl;

import java.io.Serializable;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.sblm.dao.ICuentaBancariaDAO;
import com.sblm.model.Banco;
import com.sblm.model.Cuentabancaria;

@Repository(value = "cuentabancariaDAO")
public class CuentaBancariaDAO implements ICuentaBancariaDAO,Serializable{


	private static final long serialVersionUID = 1L;

	@Autowired
	private SessionFactory sessionFactory;


	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}


	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}


	@Override
	public void grabarCuentaBancaria(Cuentabancaria cuentabancaria) {
		getSessionFactory().getCurrentSession().merge(cuentabancaria);
		
	}


	@Override
	public List<Cuentabancaria> obtenerTodasCuentasBancarias() {
		Session session = sessionFactory.openSession();
		return session.createQuery("from Cuentabancaria order by fechacreacion desc").list();
	}


	@Override
	public void eliminarCuentabancaria(Cuentabancaria cuentabancaria) {
		sessionFactory.getCurrentSession().createSQLQuery("delete Cuentabancaria where idcuentabancaria='"+cuentabancaria.getIdcuentabancaria()+"'").executeUpdate();
	}


	@Override
	public int nroCuentasBancarias() {
		Long count = (Long) sessionFactory.openSession()
				.createQuery("select COUNT(IDCUENTABANCARIA) from Cuentabancaria").uniqueResult();
				return count.intValue();
	}


	@Override
	public List<Banco> listBancos() {
		Session session = sessionFactory.openSession();
		return session.createQuery("from Banco").list();
	}
	
	}

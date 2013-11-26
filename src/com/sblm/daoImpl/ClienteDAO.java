package com.sblm.daoImpl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.sblm.dao.IClienteServiceDAO;
import com.sblm.model.Cliente;

@Repository(value = "clienteDAO")
public class ClienteDAO implements IClienteServiceDAO,Serializable{


	private static final long serialVersionUID = 1L;

	@Autowired
	private SessionFactory sessionFactory;


	@Override
	public void grabarCliente(Cliente cliente) {
		getSessionFactory().getCurrentSession().merge(cliente);
	//	sessionFactory.getCurrentSession().merge(cliente);
	}


	@Override
	public List<Cliente> obtenerTodosClientes() {
		Session session = sessionFactory.openSession();

		return session.createQuery("from Cliente order by fechacreacion desc").list();
	}

	@Override
	public void eliminarCliente(Cliente cliente) {
		sessionFactory.getCurrentSession().createSQLQuery("delete Cliente where idCliente='"+cliente.getIdcliente()+"'").executeUpdate();
	}


	@Override
	public int nroClientes() {
		Long count = (Long) sessionFactory.openSession()
				.createQuery("select COUNT(IDCliente) from Cliente").uniqueResult();
				return count.intValue();
	}


	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}


	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	}

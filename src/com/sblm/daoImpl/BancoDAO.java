package com.sblm.daoImpl;

import java.io.Serializable;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.sblm.dao.IBancoServiceDAO;
import com.sblm.model.Banco;

@Repository(value = "bancoDAO")
public class BancoDAO implements IBancoServiceDAO,Serializable{


	private static final long serialVersionUID = 1L;

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void grabarbanco(Banco banco) {
		getSessionFactory().getCurrentSession().merge(banco);
	}


	@Override
	public List<Banco> obtenerTodosBancos() {
		Session session = sessionFactory.openSession();

		return session.createQuery("from Banco order by fechacreacion desc").list();
	}

	@Override
	public void eliminarBanco(Banco banco) {
		sessionFactory.getCurrentSession().createSQLQuery("delete Banco where idbanco='"+banco.getIdbanco()+"'").executeUpdate();
	}


	@Override
	public int nroBancos() {
		Long count = (Long) sessionFactory.openSession()
				.createQuery("select COUNT(IDBANCO) from Banco").uniqueResult();
				return count.intValue();
	}


	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}


	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	}

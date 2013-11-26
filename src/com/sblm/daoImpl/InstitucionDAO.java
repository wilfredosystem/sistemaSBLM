package com.sblm.daoImpl;

import java.io.Serializable;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.sblm.dao.IInstitucionServiceDAO;
import com.sblm.model.Institucion;

@Repository(value = "institucionDAO")
public class InstitucionDAO implements IInstitucionServiceDAO,Serializable{


	private static final long serialVersionUID = 1L;

	@Autowired
	private SessionFactory sessionFactory;


	@Override
	public void grabarInstitucion(Institucion institucion) {
		getSessionFactory().getCurrentSession().merge(institucion);
	//	sessionFactory.getCurrentSession().merge(institucion);
	}


	@Override
	public List<Institucion> obtenerTodosInstituciones() {
		Session session = sessionFactory.openSession();

		return session.createQuery("from Institucion order by fechacreacion desc").list();
	}

	@Override
	public void eliminarInstitucion(Institucion institucion) {
		sessionFactory.getCurrentSession().createSQLQuery("delete Institucion where idinstitucion='"+institucion.getIdinstitucion()+"'").executeUpdate();
	}


	@Override
	public int nroInstituciones() {
		Long count = (Long) sessionFactory.openSession()
				.createQuery("select COUNT(IDINSTITUCION) from Institucion").uniqueResult();
				return count.intValue();
	}


	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}


	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	}

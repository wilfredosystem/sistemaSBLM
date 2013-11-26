package com.sblm.daoImpl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sblm.dao.IIgvDAO;
import com.sblm.dao.IRepresentanteServiceDAO;
import com.sblm.model.Auditoria;
import com.sblm.model.Estadoauditoria;
import com.sblm.model.Eventoauditoria;
import com.sblm.model.Igv;
import com.sblm.model.Modulo;
import com.sblm.model.Representante;
import com.sblm.model.Uso;
import com.sblm.model.Usuario;
import com.sblm.util.FuncionesHelper;
@Repository(value = "representateDAO")
public class RepresentanteDAO implements IRepresentanteServiceDAO,Serializable{


	private static final long serialVersionUID = 1L;

	@Autowired
	private SessionFactory sessionFactory;


	@Override
	public void grabarRepresentante(Representante representante) {
		sessionFactory.getCurrentSession().merge(representante);
	}

	@Override
	public List<Representante> obtenerTodosRepresentantes() {
		Session session = sessionFactory.openSession();

		return session.createQuery("from Representante order by fechacreacion desc").list();
	}

	@Override
	public void eliminarRepresentante(Representante representante) {
		sessionFactory.getCurrentSession().createSQLQuery("delete Representante where idrepresentante='"+representante.getIdrepresentante()+"'").executeUpdate();
	}


	@Override
	public int nrorepresentantes() {
		Long count = (Long) sessionFactory.openSession()
				.createQuery("select COUNT(IDREPRESENTANTE) from Representante").uniqueResult();
				return count.intValue();
	}
	}

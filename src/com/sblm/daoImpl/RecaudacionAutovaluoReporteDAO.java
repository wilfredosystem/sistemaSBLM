package com.sblm.daoImpl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sblm.dao.IRecaudacionAutovaluoReporteDAO;
import com.sblm.model.Cartera;
import com.sblm.model.Contrato;
import com.sblm.model.Detallecartera;
import com.sblm.model.Ubigeo;
import com.sblm.model.Upa;

@Repository(value = "autovaluoReporteDAO")
public class RecaudacionAutovaluoReporteDAO implements
		IRecaudacionAutovaluoReporteDAO, Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private SessionFactory sessionFactory;
	Session sesion = null;
	@Override
	public List<Upa> listarUpas() {
		
		
		Session session = getSessionFactory().openSession();

		try {
			return session.createQuery("from Upa").list();
		} catch (HibernateException e) {
			System.out.println("error listado listarUpas dao:::" + e);
			throw e;
		}
	}
	
	@Override
	public List<Ubigeo> listarUbigeos() {
		
		
		Session session = getSessionFactory().openSession();

		try {
			return session.createQuery("from Ubigeo where DEPA='lima' and PROV='lima'  ").list();
		} catch (HibernateException e) {
			System.out.println("error listado listarUbigeos dao:::" + e);
			throw e;
		}
	}
	
	@Override
	public List<Upa> listarUpasXDistrito(String ubigeo) {
		
		
		Session session = getSessionFactory().openSession();
		
		try {//VERIFICAR LOS DISTRITOS
			//return session.createQuery("from Upa u where u.ubigeo.ubigeo= '"+ubigeo+"'     ").list();
			System.out.println("verrrr nnn");
			return session.createQuery("select u from Upa u where u.inmueble.ubigeo.ubigeo= '"+ubigeo+"'     ").list();
		} catch (HibernateException e) {
			System.out.println("error listarUpasXDistrito dao:::" + e);
			throw e;
		}
	}
	@Override
	public List<Upa> listarUpasXDistritosLima() {
		Session session = getSessionFactory().openSession();

		try {
			return session.createQuery("from Upa u where u.ubigeo.ubigeo BETWEEN '150101' AND '150143' ").list();
		} catch (HibernateException e) {
			System.out.println("error listarUpasXDistritosLimadao:::" + e);
			throw e;
		}
	}
	@Override
	public List<Upa> listarUpasXInmueble() {
		Session session = getSessionFactory().openSession();

		try {
			//return session.createQuery("from Upa u order by u.inmueble.idinmueble ").list();
			return session.createQuery("select dc.contrato.upa from Detallecartera dc ").list();
		} catch (HibernateException e) {
			System.out.println("error listarUpasXInmueble dao:::" + e);
			throw e;
		}
	}
	@Override
	public List<Detallecartera> listarDetallescarteras() {
		Session session = getSessionFactory().openSession();

		try {
			//return session.createQuery("from Upa u order by u.inmueble.idinmueble ").list();
			return session.createQuery("select dc from Detallecartera dc  order by dc.contrato.upa.inmueble").list();
		} catch (HibernateException e) {
			System.out.println("error listarUpasXInmueble dao:::" + e);
			throw e;
		}
	}
	
	@Override
	public Contrato obtenerContratoXUpa(int idupa) { 
		return (Contrato) getSessionFactory()
				.openSession()
				.createQuery(
						"select c from Contrato c where c.upa.idupa="+idupa+" and c.estado='VIGENTE' ")
				.uniqueResult();
	}
	@Override
	public Double obtenerMontoPorAnho(int idcontrato, String anho) { 
	 Double valor=	(Double) getSessionFactory().openSession().createQuery("select sum(dc.montosoles) from Detallecuota dc join dc.cuota cu join cu.contrato con where con.idcontrato='"+idcontrato+"' and  year(dc.feccre ) ='"+anho+"' ")
		.uniqueResult();
		if(valor==null){
			return 0.0;
		}else{
			return (Double) getSessionFactory().openSession().createQuery("select sum(dc.montosoles) from Detallecuota dc join dc.cuota cu join cu.contrato con where con.idcontrato='"+idcontrato+"' and  year(dc.feccre ) ='"+anho+"' ")
					.uniqueResult();
		}
		
	}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}


}

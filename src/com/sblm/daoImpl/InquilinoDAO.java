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

import com.sblm.dao.IInquilinoDAO;
import com.sblm.model.Auditoria;
import com.sblm.model.Califica;
import com.sblm.model.Estadoauditoria;
import com.sblm.model.Eventoauditoria;
import com.sblm.model.Inmueble;
import com.sblm.model.Inquilino;
import com.sblm.model.Modulo;
import com.sblm.model.Tipoentidad;
import com.sblm.model.Tipopersona;
import com.sblm.model.Usuario;
import com.sblm.util.FuncionesHelper;

@Repository(value = "inquilinoDAO")
public class InquilinoDAO implements IInquilinoDAO, Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private SessionFactory sessionFactory;

	Session sesion = null;

	@Override
	public void registrarInquilino(Inquilino inquilino) {
		getSessionFactory().getCurrentSession().merge(inquilino);
		try {
			settingLog(23);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actualizarInquilino(Inquilino inquilino) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eliminarInquilino(Inquilino inquilino) {
		try {
			getSessionFactory()
					.getCurrentSession()
					.createSQLQuery(
							"delete from Inquilino WHERE idinquilino='"
									+ inquilino.getIdinquilino() + "'")
					.executeUpdate();
		} catch (Exception e) {
			System.out.println("error en dao eliminar inquilino:::"
					+ e.getMessage());
		}
		
	}

	@Override
	public List<Inquilino> listarInquilinos() {
		Session session = getSessionFactory().openSession();

		try {
			return session.createQuery("from Inquilino order by feccre desc").list();
		} catch (HibernateException e) {
			System.out.println("error listado Inquilino dao:::" + e);
			throw e;
		}
	}
	@Override
	public List<Califica> listarCalificacion(Inquilino inqui) {
		Session session = getSessionFactory().openSession();

		try {
			return session.createQuery("from Califica where  idinquilino='"+inqui.getIdinquilino()+"' ").list();
		} catch (HibernateException e) {
			System.out.println("error listado listarCalificacion dao:::" + e);
			throw e;
		}
	}
	
	@Override
	public void eliminarCalificacion(Inquilino inquilino) {
		try {
			getSessionFactory()
					.getCurrentSession()
					.createSQLQuery(
							"delete from Califica WHERE idinquilino='"
									+ inquilino.getIdinquilino() + "'")
					.executeUpdate();
		} catch (Exception e) {
			System.out.println("error en dao eliminar inquilino:::"
					+ e.getMessage());
		}
		
	}
	@Override
	public Inquilino obtenerUltimoInquilino() {
		return (Inquilino) getSessionFactory()
				.openSession()
				.createQuery(
						"select t from Inquilino t where idinquilino=( select max(idinquilino) from Inquilino)")
				.uniqueResult();
	}
	
	
	@Override
	public Inquilino validarRepetido(String val) {
		return (Inquilino) getSessionFactory()
				.openSession()
				.createQuery(
						"select t from Inquilino t where dni='"+val+"'  ")
				.uniqueResult();
	}
	@Override
	public Inquilino obtenerInquilinoPorId(int id) {
		Session session = getSessionFactory().openSession();
		return (Inquilino) session.load(Inquilino.class, id);
	}

	@Override
	public int obtenerNumeroRegistros() {
		Long count = (Long) getSessionFactory().openSession()
				.createQuery("select count(*) from Inquilino").uniqueResult();
		return count.intValue();
	}

	@Override
	public List<Tipoentidad> listarTipoEntidad() {
		Session session = getSessionFactory().openSession();

		try {
			return session.createQuery("from Tipoentidad order by feccre desc").list();
		} catch (HibernateException e) {
			System.out.println("error listado Tipoentidad dao:::" + e);
			throw e;
		}
	}
	@Override
	public List<Califica> listarCalificacion() {
		Session session = getSessionFactory().openSession();

		try {
			return session.createQuery("from Califica order by concepto").list();
		} catch (HibernateException e) {
			System.out.println("error listado Califica dao:::" + e);
			throw e;
		}
	}
	
	@Override
	public List<Tipopersona> listarTipoPersona() {
		Session session = getSessionFactory().openSession();

		try {
			return session.createQuery("from Tipopersona order by feccre desc").list();
		} catch (HibernateException e) {
			System.out.println("error listado Tipopersona dao:::" + e);
			throw e;
		}
	}
	@Override
	public void registrarCalificacion(Califica calificacion) {
		
		System.out.println("------------------------->"+calificacion.getIdcalifica());
		
		try {
			getSessionFactory().getCurrentSession().save(calificacion);
		} catch (Exception e) {
			System.out.println("####errorx::"+e.getMessage());
		}
	}
	
	public void settingLog(int ideventoauditoria) {
		String url = FuncionesHelper.getURL().toString();

		try {
			int index = url.indexOf("pages/");
			url = url.substring(index + 6, url.length());
			url = url.substring(0, url.length() - 4);
		} catch (Exception e) {
		}
		Auditoria Adt = new Auditoria();
		Usuario usr = new Usuario();
		usr.setIdusuario((Integer) (FuncionesHelper.getUsuario()));

		Modulo mod = new Modulo();
		Session session = getSessionFactory().openSession();

		Query numero = session
				.createSQLQuery("select  m.IDMODULO from PAGINAMODULO as pm inner join MODULO m on pm.IDMODULO= m.IDMODULO  inner join PAGINA as p on p.IDPAGINA=pm.IDPAGINA where P.NOMBREPAGINA='"
						+ url + "'");

		int var = (Integer) numero.uniqueResult();
		System.out.println("###########::::" + var);
		mod.setIdmodulo(var);
		Estadoauditoria esa = new Estadoauditoria();
		esa.setIdestadoauditoria(4);
		Eventoauditoria eva = new Eventoauditoria();
		eva.setIdeventoauditoria(ideventoauditoria);
		Adt.setUsuario(usr);
		Adt.setModulo(mod);
		Adt.setEstadoauditoria(esa);
		Adt.setEventoauditoria(eva);
		Adt.setFecentrada(new Date());
		Adt.setNompantalla(url);
		Adt.setUrl(FuncionesHelper.getURL().toString());
		if (FuncionesHelper.getTerminal().toString().equals("0:0:0:0:0:0:0:1")) {
			Adt.setIp("127.0.0.1");
		} else {
			Adt.setIp(FuncionesHelper.getTerminal().toString());
		}
		Adt.setEstado(true);
		Adt.setCodauditoria(0);
		try {
			getSessionFactory().getCurrentSession().save(Adt);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}


}

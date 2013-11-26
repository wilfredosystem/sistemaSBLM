package com.sblm.daoImpl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sblm.dao.ITipoCambioDAO;
import com.sblm.model.Auditoria;
import com.sblm.model.Estadoauditoria;
import com.sblm.model.Eventoauditoria;
import com.sblm.model.Inmueble;
import com.sblm.model.Modulo;
import com.sblm.model.Tipocambio;
import com.sblm.model.Usuario;
import com.sblm.util.FuncionesHelper;



@Repository(value = "tipocambioDAO")
public class TipoCambioDAO implements ITipoCambioDAO,Serializable{

	
	private static final long serialVersionUID = 1L;

	@Autowired
	private SessionFactory sessionFactory;

	Session sesion = null;
	Transaction txt = null;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void registrarTipoCambio(Tipocambio tipoCambio) {
		getSessionFactory().getCurrentSession().merge(tipoCambio);
		 try {
			 //idestadoauditoria 0 auditoria / 1 notificacion
			 settingLog(11);
			 } catch (Exception e) {
			 e.printStackTrace();
			 }
	}
	


	public Tipocambio obtenerTipoCambio() {

		//sesion = getSessionFactory().openSession();
		//txt = sesion.beginTransaction();
		// recuperamos el obj con el id.
		Tipocambio t= (Tipocambio) getSessionFactory().openSession().createQuery(
				"select t from Tipocambio t where idtipocambio=( select max(idtipocambio) from Tipocambio)").uniqueResult();
		 
		
		 return t;
	}
	public String obtenerMes() {

		//sesion = getSessionFactory().openSession();
		//txt = sesion.beginTransaction();
		// recuperamos el obj con el id.
		String val= (String) getSessionFactory().openSession().createQuery(
				"select DATENAME(month, t.fecha) from Tipocambio t where idtipocambio=( select max(idtipocambio) from Tipocambio)").uniqueResult();
		return val;
		
	}
	
	public void settingLog( int ideventoauditoria) {
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

	@Override
	public Tipocambio listarTipoCambioPorId(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Tipocambio> listarTipoCambios() {
	
	    
Session session = getSessionFactory().openSession();
		
	    try{
	    	return session.createQuery("from Tipocambio order by feccre desc").list();
	    }
	    catch(HibernateException e){
	    	System.out.println("error listarTipoCambios:::"+e);
	    	throw e;
	    }
	    finally{
	    	session.close();
	    }
	   
	}
	
	
	
	@Override
	public Tipocambio obtenerUltimoTipocambio() {
		return (Tipocambio) getSessionFactory()
				.openSession()
				.createQuery(
						"select t from Tipocambio t where idtipocambio=( select max(idtipocambio) from Tipocambio)")
				.uniqueResult();
	}

}

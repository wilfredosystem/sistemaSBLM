package com.sblm.daoImpl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.sblm.dao.INotificacionesDAO;
import com.sblm.model.Auditoria;
import com.sblm.model.Eventoauditoria;
import com.sblm.model.Usuario;
import com.sblm.util.FuncionesHelper;

@Repository(value = "notificacionesDAO")
public class NotificacionesDAOImpl implements INotificacionesDAO, Serializable {
	private static final long serialVersionUID = -7132329520845816103L;
	Calendar fecha = Calendar.getInstance();
	private int nroMes=fecha.getTime().getMonth()+1;

	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private SessionFactory sessionFactory2;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}


	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	
	

	public SessionFactory getSessionFactory2() {
		return sessionFactory2;
	}


	public void setSessionFactory2(SessionFactory sessionFactory2) {
		this.sessionFactory2 = sessionFactory2;
	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List listarNotificaciones(int estado, String mesSeleccionado,
			String anioSeleccionado) {
		
		System.out.println(estado+" - "+mesSeleccionado+" "+anioSeleccionado);
		Session session = getSessionFactory().openSession();
		
		
		String query="";
		String queryMes = "";
		String queryAnio ="";
		
		

		if(!mesSeleccionado.equals("")){
			queryMes = "MONTH(A.fecentrada) = '"+(Integer.parseInt(mesSeleccionado)+1)+"' and "; 
		}
		if(!anioSeleccionado.equals("")){
			queryAnio = "YEAR(A.fecentrada)='"+anioSeleccionado+"' and ";
		}

		
		List<List> list = null;
		
		String queryPendientes="select new List(EA.tipoevento,U.rutaimgusr,A.idauditoria,A.fecentrada,U.nombres,U.apellidopat,U.apellidomat,A.mensajepersonalizado) from Auditoria A inner join A.usuario U inner join A.usuariodestino UD inner join A.eventoauditoria EA" +
				" WHERE A.codauditoria=1  AND UD.idusuario='"+FuncionesHelper.getUsuario().toString()+"' AND A.estadoauditoria.idestadoauditoria="+estado+" and "+queryAnio+queryMes+"  1=1 order by A.fecentrada  desc  ";
		
		
		
		list = session.createQuery(queryPendientes).list(); System.out.println("my query is: "+query+"size List : "+list.size());
		List<Auditoria> lista = new ArrayList<Auditoria>();
		
		for(int i=0;i<list.size();i++){
			Usuario usu= new Usuario();
			usu.setRutaimgusr((String) list.get(i).get(1));
			usu.setNombres((String) list.get(i).get(4));
			usu.setApellidopat((String) list.get(i).get(5));
			usu.setApellidomat((String) list.get(i).get(6));
			
			Eventoauditoria eve= new Eventoauditoria();
			eve.setTipoevento((String) list.get(i).get(0));
			
			Auditoria auditori = new Auditoria();
			auditori.setIdauditoria((Integer)list.get(i).get(2));
			auditori.setFecentrada((Date)list.get(i).get(3));
			auditori.setMensajepersonalizado((String)list.get(i).get(7));
			auditori.setUsuario(usu);
			auditori.setEventoauditoria(eve);
			
			lista.add(auditori);
		}
		
		return lista;
		

	}


	@Override
	public void actualizarPendienteToRevisado(int idauditoria) {
String updateQuery="UPDATE AUDITORIA SET IDESTADOAUDITORIA=2 WHERE IDAUDITORIA='"+idauditoria+"'";
		
		getSessionFactory().getCurrentSession().createSQLQuery(updateQuery).executeUpdate();			
	}

	@Override
	public void actualizarPendienteToCancelado(int idauditoria) {
String updateQuery="UPDATE AUDITORIA SET IDESTADOAUDITORIA=3 WHERE IDAUDITORIA='"+idauditoria+"'";
		
		getSessionFactory().getCurrentSession().createSQLQuery(updateQuery).executeUpdate();		
	}
	
	
	
	@Override
	public Object nroNotificacionesRevisado() {
		
		Session session = getSessionFactory().openSession();
		
		Query numero=session.createQuery("select count(*) from Auditoria A inner join A.usuario U inner join A.usuariodestino UD where A.codauditoria='1 ' and A.estadoauditoria.idestadoauditoria='2' and MONTH(A.fecentrada)="+nroMes+" AND UD.idusuario='"+FuncionesHelper.getUsuario().toString()+"' and A.codauditoria='1'");

	      try {
	    	  return numero.list().get(0);
	      } catch (HibernateException e) { 
	    	  throw e;
	      }  finally { 
	           session.close();
	      }
	}


	@Override
	public Object nroNotificacionesPendiente() {
		Session session = getSessionFactory().openSession();
		Query numero=session.createQuery("select count(*) from Auditoria A inner join A.usuario U inner join A.usuariodestino UD where A.codauditoria='1' and A.estadoauditoria.idestadoauditoria='1' and MONTH(A.fecentrada)="+nroMes+" AND UD.idusuario='"+FuncionesHelper.getUsuario().toString()+"' and A.codauditoria='1'");

	      try {
	    	  return numero.list().get(0);
	      } catch (HibernateException e) { 
	    	  throw e;
	      }  finally { 
	           session.close();
	      }
	}


	@Override
	public Object nroNotificacionesCancelado() {
		Session session = getSessionFactory().openSession();
		Query numero=session.createQuery("select count(*) from Auditoria A inner join A.usuario U inner join A.usuariodestino UD where A.codauditoria='1' and A.estadoauditoria.idestadoauditoria='3' and MONTH(A.fecentrada)="+nroMes+" AND UD.idusuario='"+FuncionesHelper.getUsuario().toString()+"' and A.codauditoria='1'");

	      try {
	    	  return numero.list().get(0);
	      } catch (HibernateException e) { 
	    	  throw e;
	      }  finally { 
	           session.close();
	      }
	}


	@Override
	public Object nroNotificacionesDelMes() {
		Session session = getSessionFactory().openSession();
		
		Query numero=session.createQuery("select count(*) from Auditoria A inner join A.usuario U inner join A.usuariodestino UD where A.codauditoria='1' and MONTH(A.fecentrada)="+nroMes+" and UD.idusuario='"+FuncionesHelper.getUsuario().toString()+"' and A.codauditoria='1'");
		
		
	      try {
	    	  return numero.list().get(0);
	      } catch (HibernateException e) { 
	    	  throw e;
	      }  finally { 
	           session.close();
	      }
	}


	@Override
	public Object nroNotificacionesTotal() {
		Session session = getSessionFactory().openSession();

		
		Query numero=session.createQuery("select count(*) from Auditoria A inner join A.usuario U inner join A.usuariodestino UD where A.codauditoria='1'  and UD.idusuario='"+FuncionesHelper.getUsuario().toString()+"' and A.estadoauditoria.idestadoauditoria='1' and A.codauditoria='1'");
		
	      try {
	    	  return numero.list().get(0);
	      } catch (HibernateException e) { 
	    	  throw e;
	      }  finally { 
	           session.close();
	      }


	}



}

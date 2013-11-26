package com.sblm.util;

import java.util.Date;

import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.sblm.model.Auditoria;
import com.sblm.model.Estadoauditoria;
import com.sblm.model.Eventoauditoria;
import com.sblm.model.Modulo;
import com.sblm.model.Usuario;


public class AuditLogUtil {

	@Autowired
	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}


	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Transactional(readOnly = true)
	public  void settingLog(int idusuario, int idmodulo, int idestadoauditoria, int ideventoauditoria, Date fechaentrada, String nombrepantalla,
							String url,Boolean estado, int codauditoria){
		System.out.println("Entro metodo de Clase auditoria");
		
		Auditoria Adt= new Auditoria();
		
		Usuario usr= new Usuario();
		usr.setIdusuario(idusuario);
		
		Modulo mod= new Modulo();
		mod.setIdmodulo(idmodulo);
		
		Estadoauditoria esa= new Estadoauditoria();
		esa.setIdestadoauditoria(idestadoauditoria);
		
		Eventoauditoria eva= new Eventoauditoria();
		eva.setIdeventoauditoria(ideventoauditoria);
		
		Adt.setUsuario(usr);
		Adt.setModulo(mod);
		Adt.setEstadoauditoria(esa);
		Adt.setEventoauditoria(eva);
		
		Adt.setFecentrada(fechaentrada);
		Adt.setNompantalla(nombrepantalla);
		Adt.setUrl(url);
		Adt.setEstado(estado);
		Adt.setCodauditoria(codauditoria);
		
	try {
		
		//sessionFactory.getCurrentSession().createSQLQuery("select * from auditoria").executeUpdate();
		//getSessionFactory().getCurrentSession().save(Adt);
	} catch (HibernateException e) 	{
			e.printStackTrace();	}
	
	
	
		
		
	
	}
	
	
	
}




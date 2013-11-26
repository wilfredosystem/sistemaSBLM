package com.sblm.daoImpl;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.sblm.dao.IMonitoreoMesaPartesDAO;
import com.sblm.model.Archivodocumento;
import com.sblm.model.Auditoria;
import com.sblm.model.Estadoauditoria;
import com.sblm.model.Eventoauditoria;
import com.sblm.model.Extensionarchivo;
import com.sblm.model.Modulo;
import com.sblm.model.Usuario;
import com.sblm.modelMP.Documento;
import com.sblm.modelMP.Flujodoc;
import com.sblm.util.FuncionesHelper;

@Repository(value = "monitoreoMesaPartesDAO")
public class monitoreoMesaPartesDAOImpl implements IMonitoreoMesaPartesDAO, Serializable {
	private static final long serialVersionUID = -7132329520845816103L;
 
	
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


	 
	@Override
	public List<com.sblm.model.Documento> listarDocumentosRegistrados(int mes) {
		//Pendiente
		Session session = getSessionFactory().openSession();
		
		
		if(mes==0){
			 return session.createQuery("from Documento D where D.estado='ninguno' order by D.fechadocumento  desc").list();
		}else{
			  return session.createQuery("from Documento D where D.estado='ninguno' and  Month(D.fechadocumento)='"+mes+"'  order by D.fechadocumento  desc").list();
		}
			
	}
	@Override
	public List<com.sblm.model.Documento>  listaFiltroDocumentos(String resp){
		
		Session session = getSessionFactory().openSession();
		return session.createQuery("from Documento D where D.estado='atendido' and D.respuesta='"+resp+"' order by D.fechadocumento  desc").list();
									
	}
	@Override
	public List<com.sblm.model.Documento>  listaFiltroDocumentosMes(String resp){
		
		Session session = getSessionFactory().openSession();
		Calendar fecha = Calendar.getInstance();
		 int nroMes=fecha.getTime().getMonth()+1;
		return session.createQuery("from Documento D where D.estado='atendido' and D.respuesta='"+resp+"' and Month(D.fecharegistro)='"+nroMes+"' order by D.fechadocumento  desc").list();
									
	}
	

	public SessionFactory getSessionFactory2() {
		return sessionFactory2;
	}


	public void setSessionFactory2(SessionFactory sessionFactory2) {
		this.sessionFactory2 = sessionFactory2;
	}


	@Override
	public Object countExternalDB() {
		Session session = getSessionFactory2().openSession();

		String cadena="SELECT count(*) FROM Flujodoc  F inner join  F.documento D " +
				"where  D.idare='10800000' and F.numflj=1 and D.fecdoc>'01/07/13' ";
		
		
		Query numero=session.createQuery(cadena);
		
		
		
	    return numero.list().get(0);
	
	}


	@Override
	public Object countInternalDB() {
		Session session = getSessionFactory().openSession();
		
		String cadena="SELECT count(*) FROM Documento";
		
		
		Query numero=session.createQuery(cadena);
		
		
		
	    return numero.list().get(0);
		
	}


	@Override
	public List<Flujodoc> getNewInserts(int val) {
		//wilfredo modificado quitar try
		try {
			String A= "SELECT F.IDFLJ,F.NUMFOL,D.TITDOC,D.FECDOC,D.DESDOC,D.DESASN,D.DESRMT,D.IDDOC,D.idtipdoc,D.idare  "+
					"FROM Flujodoc AS F inner join  Documento AS D  ON D.IDDOC=F.IDDOC where  (D.idare='10800000' and F.NUMFLJ=1 and D.fecdoc>'01/07/13' and idtipdoc='17') or (D.idare='10800000' and F.NUMFLJ=1 and D.fecdoc>'01/07/13' and idtipdoc='18') or  (D.idare='10502060'  and F.NUMFLJ=1 and D.fecdoc>'01/07/13' and D.idtipdoc='14') order by D.fecdoc  asc";

					Session session = getSessionFactory2().openSession();
					 Query Q = session.createSQLQuery(A);
						
						List<Object> objectList = Q.list();
						Iterator iterator = objectList.iterator();
						List<Flujodoc> lista = new ArrayList<Flujodoc>();
						while(iterator.hasNext()){ 
							Object []obj = (Object[])iterator.next();
							
							Documento doc= new Documento();
							doc.setTitdoc((String) obj[2]);
							doc.setFecdoc((Date)obj[3]);
							doc.setDesdoc((String)obj[4]);
							doc.setDesasn((String)obj[5]);
							
							doc.setDesrmt((String)obj[6]);
							doc.setIddoc((String)obj[7]);
							doc.setIdtipdoc((String)obj[8]);

							Flujodoc flujo = new Flujodoc();
							flujo.setIdflj((String) obj[0]);
							flujo.setNumfol((String) obj[1]);
							

							flujo.setDocumento(doc);
							
										
							lista.add(flujo);
							
							}
						
						
						return lista;
		} catch (HibernateException e) {
			System.out.println("error listado obtenerMontoAcumuladoDetallecuota dao:::" + e.getMessage());
			throw e;
		}
	
	}


	@Override
	public void save(com.sblm.model.Documento doc) {
		
		try {
			getSessionFactory().getCurrentSession().save(doc);

		} catch (Exception e) {
		e.printStackTrace();
		}
		
	}


	@Override
	public Object countPendientes() {

		Session session = getSessionFactory().openSession();
		
		Query numero=session.createQuery("select count(*) from Documento D where D.estado='ninguno'");

	      try {
	    	  return numero.list().get(0);
	      } catch (HibernateException e) { 
	    	  throw e;
	      }  finally { 
	           session.close();
	      }
	}
//
//
	@Override
	public Object countAtendidos() {
		
		Session session = getSessionFactory().openSession();
		
		Query numero=session.createQuery("select count(*) from Documento D where D.estado='atendido'");

	      try {
	    	  return numero.list().get(0);
	      } catch (HibernateException e) { 
	    	  throw e;
	      }  finally { 
	           session.close();
	      }
	}

	
	@Override
	public Object countDocumentosFiltrados(String resp) {
		
		Session session = getSessionFactory().openSession();
		Calendar fecha = Calendar.getInstance();
		 int nroMes=fecha.getTime().getMonth()+1;
		Query numero=session.createQuery("select count(*) from Documento D where D.estado='atendido' and  D.respuesta ='"+resp+"'");
		
	      try {
	    	  return numero.list().get(0);
	      } catch (HibernateException e) { 
	    	  throw e;
	      }  finally { 
	           session.close();
	      }
	}
	@Override
	public Object countDocumentosFiltradosMes(String resp, int mes) {
		
		Session session = getSessionFactory().openSession();
		Calendar fecha = Calendar.getInstance();
		 int nroMes=fecha.getTime().getMonth()+1;
		if(mes==0){
			Query numero=session.createQuery("select count(*) from Documento D where D.estado='atendido' and  D.respuesta ='"+resp+"'  ");
			System.out.println("entro a mes 0000:::");
		    	  return numero.list().get(0);
		}else{
			System.out.println("entro a mes diferente a 0000:::");
		Query numero=session.createQuery("select count(*) from Documento D where D.estado='atendido' and  D.respuesta ='"+resp+"' and Month(D.fecharegistro)='"+mes+"' ");
		 try {
	    	  return numero.list().get(0);
	      } catch (HibernateException e) { 
	    	  throw e;
	      }  finally { 
	           session.close();
	      }
		}
	     
	}
	
	@Override
	public Object countPendientesMes(int mes) {
		
		Session session = getSessionFactory().openSession();
		
		Query numero=session.createQuery("select count(*) from Documento D where D.estado='ninguno'  and month(D.fechadocumento) ='"+mes+"'");
		
	      try {
	    	  return numero.list().get(0);
	      } catch (HibernateException e) { 
	    	  throw e;
	      }  finally { 
	           session.close();
	      }
	}
	@Override
	public Object countAtendidosMes(int mes) {
		
		Session session = getSessionFactory().openSession();
		
		Query numero=session.createQuery("select count(*) from Documento D where D.estado='atendido' and month(D.fechadocumento) ='"+mes+"'");

	      try {
	    	  return numero.list().get(0);
	      } catch (HibernateException e) { 
	    	  throw e;
	      }  finally { 
	           session.close();
	      }
	}
	@Override
	public Object countRechazadosMes() {
		
		Session session = getSessionFactory().openSession();
		Calendar fecha = Calendar.getInstance();
		 int nroMes=fecha.getTime().getMonth()+1;
		Query numero=session.createQuery("select count(*) from Documento D where D.estado='atendido' and D.respuesta='RECHAZADO' and month(D.fechaderivaciondgai) ='"+nroMes+"'");

	      try {
	    	  return numero.list().get(0);
	      } catch (HibernateException e) { 
	    	  throw e;
	      }  finally { 
	           session.close();
	      }
	}
	
	@Override
	public void actualizarEstadoToAtendido(int iddoc,List<Usuario> listaUsuarioSeleccionados) {
		SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String updateQuery="UPDATE DOCUMENTO SET ESTADO='atendido', FECHAREGISTRO='"+formateador.format(new Date())+"' WHERE IDDOCUMENTO='"+iddoc+"'";
		
		//SQL
		getSessionFactory().getCurrentSession().createSQLQuery(updateQuery).executeUpdate();
		
		//Notificacion
		for (int i = 0; i < listaUsuarioSeleccionados.size(); i++) {
			settingLog(1, 13, listaUsuarioSeleccionados.get(i).getIdusuario(), "");
		}
		
		
		
	}


	@Override
	public List<com.sblm.model.Documento> listarDocumentosAtendidos() {
		Session session = getSessionFactory().openSession();
		
		return session.createQuery("from Documento D where D.estado='atendido' order by D.fecharegistro  desc").list();
	}
	@Override
	public List<com.sblm.model.Documento> listarDocumentosAtendidosPorMes(int mes) {
		Session session = getSessionFactory().openSession();
		
		return session.createQuery("from Documento D where D.estado='atendido'  and month(D.fecharegistro) ='"+mes+"' ").list();
	}
	
	@Override
	public String obtenerUltimoDespacho() {
		Session session = getSessionFactory().openSession();
		
		Query q=session.createQuery("from Documento D where D.estado='atendido' order by D.fecharegistro desc");
		q.setFirstResult(0);
		q.setMaxResults(1);
		
		com.sblm.model.Documento  D= new com.sblm.model.Documento();
		
		try {
			D=(com.sblm.model.Documento) q.list().get(0);
		} catch (Exception e) {
			System.out.println("error trycatch:::"+e.getMessage());
		}
		
		
		
		System.out.println(D.getTitulo());
		
		return D.getTitulo();
	}
	

	@Override
	public Usuario getDirectorDGAI() {
		Session session = getSessionFactory().openSession(); 
		return (Usuario) session.createQuery("from Usuario U where U.cargo='Director DGAI'").uniqueResult();
	}


	@Override
	public List<Extensionarchivo> getListaExtensionArhivos() {
		Session session = getSessionFactory().openSession();
		return session.createQuery("from Extensionarchivo").list();
	}


	@Override
	public void agregarArchivosDocumento(Archivodocumento archivodocu) {
		
		try {
			getSessionFactory().getCurrentSession().save(archivodocu);

		} catch (Exception e) {
		e.printStackTrace();
		}
		
	}


	@Override
	public List<com.sblm.model.Documento> listarDocumentosRegistrados() {
			Session session = getSessionFactory().openSession();
			 return session.createQuery("from Documento D where D.estado='ninguno' order by D.fechadocumento  desc").list();
				
			
	}


public  void settingLog(int idestadoauditoria, int ideventoauditoria, int idusuariodestino, String mensajePersonalizado){
		
		String url=FuncionesHelper.getURL().toString();
	
		try {
			int index = url.indexOf("pages/");
			url=url.substring(index+6, url.length());
			url=url.substring(0, url.length()-4);
		} catch (Exception e) {e.getMessage();	}

				Session session = getSessionFactory().openSession();
				Auditoria Adt= new Auditoria();
				Usuario usr= new Usuario();
				usr.setIdusuario((Integer)(FuncionesHelper.getUsuario()));
				
				Usuario usrdes= new Usuario();
				usrdes.setIdusuario(idusuariodestino);
				
				Modulo mod= new Modulo();
			//	mod.setIdmodulo((Integer) session.createSQLQuery("select  m.IDMODULO from PAGINAMODULO as pm inner join MODULO m on pm.IDMODULO= m.IDMODULO  inner join PAGINA as p on p.IDPAGINA=pm.IDPAGINA where P.NOMBREPAGINA='"+url+"'").uniqueResult());
				Estadoauditoria esa= new Estadoauditoria();
				esa.setIdestadoauditoria(idestadoauditoria);
				Eventoauditoria eva= new Eventoauditoria();
				eva.setIdeventoauditoria(ideventoauditoria);
				Adt.setUsuario(usr);
				Adt.setUsuariodestino(usrdes);
				Adt.setModulo(mod);
				Adt.setEstadoauditoria(esa);
				Adt.setEventoauditoria(eva);
				Adt.setFecentrada( new Date());
				Adt.setNompantalla(url);
				Adt.setUrl(FuncionesHelper.getURL().toString());
				Adt.setIp(FuncionesHelper.getTerminal().toString());
				Adt.setEstado(true);
				Adt.setCodauditoria(1);
				Adt.setMensajepersonalizado(mensajePersonalizado);
				
		try {
			getSessionFactory().getCurrentSession().save(Adt);
		
		} catch (Exception e) {
		e.printStackTrace();
		}
	

}

	@Override
	public List<com.sblm.model.Documento> listarDocumentosAtendidos(int mes) {
		Session session = getSessionFactory().openSession();
		
		return session.createQuery("from Documento D where D.estado='atendido' and month(D.fecharegistro) ='"+mes+"' ").list();
	}
	

@Override
public List<com.sblm.model.Documento> getListLocal() {
	Session session = getSessionFactory().openSession();
	
	return session.createQuery("from Documento D order by D.fecharegistro desc").list();
}



@Override
public Object countPendientesMes() {
	// TODO Auto-generated method stub
	return null;
}


@Override
public Object countAtendidosMes() {
	// TODO Auto-generated method stub
	return null;
}


@Override
public void eliminarDocumentoNoEncontrado(String codigodocumento) {
	Session session = getSessionFactory().openSession();
	String queryDelete="delete Documento where codigodocumento=:id";
	session.createQuery(queryDelete).setParameter("id",codigodocumento).executeUpdate();
}



	
	
}

package com.sblm.daoImpl;

import java.io.Serializable;
import java.text.SimpleDateFormat;
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

import com.sblm.dao.IFlujoDocumentoDAO;
import com.sblm.model.Auditoria;
import com.sblm.model.Documento;
import com.sblm.model.Estadoauditoria;
import com.sblm.model.Eventoauditoria;
import com.sblm.model.Flujodocumento;
import com.sblm.model.Modulo;
import com.sblm.model.Perfil;
import com.sblm.model.Perfilusuario;
import com.sblm.model.Usuario;
import com.sblm.util.FuncionesHelper;

@Repository(value = "flujodocumentoDAO")
public class FlujoDocumentoDAO implements IFlujoDocumentoDAO, Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void registrarFlujoDocumento(Flujodocumento flujodocumento) {
		try {
		getSessionFactory().getCurrentSession().save(flujodocumento);
		
		settingLog(1, 9, flujodocumento.getUsuario().getIdusuario(),"");
		} catch (Exception e) {
			e.getMessage();
		}
		

	}
	

		
		@Override
		public void enviarNotificaciónPersonalizable(
				int idusuarioNotificacionPersonalizada,
				String contenidoMensajePersonalizado) {
			try {
				settingLog(1, 12, idusuarioNotificacionPersonalizada,contenidoMensajePersonalizado);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}




	

	@Override
	public void actualizarFlujoDocumento(Flujodocumento flujodocumento) {
		// TODO Auto-generated method stub

	}

	//RECHAZO
	@Override
	public void actualizarComentarioDocumento(int iddocumento,String comentarioRechazo, List<Flujodocumento> flujoDocumentoIndex) {
		SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String updateQuery="UPDATE DOCUMENTO SET COMENTARIO='"+comentarioRechazo+"' , RESPUESTA='RECHAZADO', fechaaprobacionrechazo='"+formateador.format(new Date())+"' WHERE IDDOCUMENTO='"+iddocumento+"'";
		getSessionFactory().getCurrentSession().createSQLQuery(updateQuery).executeUpdate();
		
		for(int i=0;i<flujoDocumentoIndex.size();i++){
			settingLog(1, 10, flujoDocumentoIndex.get(i).getUsuario().getIdusuario(),"");
		}
		
	}
	//APROBACION
	@Override
	public void regresarMesaPartes(int iddocumento) {
		SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String updateQuery="UPDATE DOCUMENTO SET RESPUESTA='APROBADO', fechaaprobacionrechazo='"+formateador.format(new Date())+"' WHERE IDDOCUMENTO='"+iddocumento+"'";
		getSessionFactory().getCurrentSession().createSQLQuery(updateQuery).executeUpdate();
		
	}

	@Override
	public void eliminarFlujoDocumento(Flujodocumento flujodocumento) {
		try {
			getSessionFactory()
					.getCurrentSession()
					.createSQLQuery(
							"delete from Flujodocumento WHERE idflujodocumento='"
									+ flujodocumento.getIdflujodocumento()
									+ "'").executeUpdate();
		} catch (Exception e) {
			System.out.println("error en dao eliminar perfil usuario:::"
					+ e.getMessage());
		}
	}

	@Override
	public Modulo listarFlujoDocumentoPorId(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Flujodocumento> listarFlujoDocumento() {

		Session session = getSessionFactory().openSession();

		try {
			return session.createQuery("from Flujodocumento").list();
		} catch (HibernateException e) {
			System.out.println("error:::" + e);
			throw e;
		}
		// finally{
		// session.close();
		// }

	}
	
	@Override
	public List<Flujodocumento> obtenerFlujodeDocumento(int idDocumento) {
		Session session = getSessionFactory().openSession();
		List<List> list = null;
		
		list = session.createQuery("select new List(F.idflujodocumento,F.numero,F.respuesta,F.estadoenvio,U.apellidopat,U.apellidomat,U.nombres,U.idusuario,U.rutaimgusr,U.cargo) from Flujodocumento F inner join F.documento D inner join F.usuario U where D.iddocumento='"+idDocumento+"'").list();
		List<Flujodocumento> lista = new ArrayList<Flujodocumento>();
		
		
		for(int i=0;i<list.size();i++){
			
			Flujodocumento flu= new Flujodocumento();
			
			flu.setIdflujodocumento((Integer) list.get(i).get(0));
			flu.setNumero((Integer) list.get(i).get(1));
			flu.setRespuesta((String) list.get(i).get(2));
			flu.setEstadoenvio((Boolean) list.get(i).get(3));
			
			Usuario usuario= new Usuario();
			usuario.setApellidopat((String) list.get(i).get(4));
			usuario.setApellidomat((String) list.get(i).get(5));
			usuario.setNombres((String) list.get(i).get(6));
			usuario.setIdusuario((Integer) list.get(i).get(7));
			usuario.setRutaimgusr((String) list.get(i).get(8));
			usuario.setCargo((String) list.get(i).get(9));
			
			flu.setUsuario(usuario);
			
			
			lista.add(flu);
		}
		
		
		return lista;
	}

	
	
	@Override
	public List<Documento> listarDocumentoDerivados() {
		Session session = getSessionFactory().openSession();

		try {
			return session.createQuery("from Documento D where D.estadodgai='derivado' order by D.fechaderivaciondgai desc").list();
		} catch (HibernateException e) {
			System.out.println("error:::" + e);
			throw e;
		}
	}
	@Override
	public List<Documento> listarDocumentoDerivadosMes(int mes) {
		Session session = getSessionFactory().openSession();

		try {
			return session.createQuery("from Documento D where D.estadodgai='derivado'  and month(D.fecharegistro)='"+mes+"' order by D.fechaderivaciondgai desc").list();
		} catch (HibernateException e) {
			System.out.println("error:::" + e);
			throw e;
		}
	}
	
	
	@Override
	public List<Documento> listaDocumentosSinDerivar(int mes) {
		Session session = getSessionFactory().openSession();

		try {
			return session.createQuery("from Documento D where D.estado='atendido' and D.estadodgai is null and month(D.fecharegistro)='"+mes+"'  order by D.fecharegistro desc").list();
		} catch (HibernateException e) {
			System.out.println("error:::" + e);
			throw e;
		}

	}
	
	@Override
	public void actualizarRespuestaToAtendido(int iddoc) {
		String updateQuery = "UPDATE DOCUMENTO SET RESPUESTA='recibido' WHERE IDDOCUMENTO='"
				+ iddoc + "'";

		getSessionFactory().getCurrentSession().createSQLQuery(updateQuery)
				.executeUpdate();
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	//Despacho
	
	@Override
	public int obtenerNumeroDespachados() {
		//Atendido Para mesa de partes
	
		Long count = (Long) getSessionFactory().openSession()
				.createQuery("select COUNT(IDDOCUMENTO) from Documento  where RESPUESTA ='APROBADO' AND estado='atendido'").uniqueResult();
		return count.intValue();
	}

	
	@Override
	public int obtenerNumeroDocumentos(String val) {
		
		Long count = (Long) getSessionFactory().openSession()
		.createQuery("select COUNT(IDDOCUMENTO) from Documento D where D.estadodgai='derivado' and RESPUESTA ='"+val+"' and estado='atendido'").uniqueResult();
		return count.intValue();
	}
	@Override
	public int obtenerNumeroDocumentosMes(String val, int mes) {
		
		Long count = (Long) getSessionFactory().openSession()
		.createQuery("select COUNT(IDDOCUMENTO) from Documento D where D.estadodgai='derivado' and RESPUESTA ='"+val+"' and estado='atendido' and month(fecharegistro)='"+mes+"' ").uniqueResult();
		return count.intValue();
	}
	@Override
	public int obtenerNumeroDocumentosMes(String val) {
		Calendar fecha = Calendar.getInstance();
		 int nroMes=fecha.getTime().getMonth()+1;
		Long count = (Long) getSessionFactory().openSession()
		.createQuery("select COUNT(IDDOCUMENTO) from Documento D where D.estadodgai='derivado' and RESPUESTA ='"+val+"' and estado='atendido' and month(fecharegistro)='"+nroMes+"' ").uniqueResult();
		return count.intValue();
	}
	
	@Override
	public int obtenerNumeroRechazados() {
		
		Long count = (Long) getSessionFactory().openSession()
		.createQuery("select COUNT(IDDOCUMENTO) from Documento where RESPUESTA ='RECHAZADO' and estado='atendido'").uniqueResult();
		return count.intValue();
	}
	
	@Override
	public int obtenerNumeroPendientesSalida() {
		Long count = (Long) getSessionFactory().openSession()
				.createQuery("select COUNT(IDDOCUMENTO) from Documento where RESPUESTA ='en proceso' and estado='atendido'").uniqueResult();
				return count.intValue();
	}

	
	//Entrada
	

@Override
public int obtenerNumeroDerivados() {
	Long count = (Long) getSessionFactory().openSession() 
			.createQuery("select COUNT(IDDOCUMENTO) from Documento where ESTADODGAI ='derivado' ").uniqueResult();
			return count.intValue();
}


@Override
public int obtenertamanioFlujoDocumento(int iddocumento) {
	Long count = (Long) getSessionFactory().openSession()
			.createQuery("select count(*) from Flujodocumento F inner join F.documento D inner join F.usuario U where D.iddocumento='"+iddocumento+"'").uniqueResult();
			return count.intValue();
}



@Override
public Boolean estaDocumentoFinalizado(int iddocumento) {
	Long count = (Long) getSessionFactory().openSession()
			.createQuery("select count(*) from Documento D  where D.iddocumento='"+iddocumento+"' and D.respuesta='en proceso'").uniqueResult();
	if(count.intValue()==0){
		return true;
	}else{
		return false;
	}
	
}


@Override
public Boolean estaFlujoFinalizado(int iddocumento) {
	Long count = (Long) getSessionFactory().openSession()
			.createQuery("select count(*) from Flujodocumento F inner join F.documento D inner join F.usuario U where D.iddocumento='"+iddocumento+"' and F.respuesta='en proceso'").uniqueResult();
	if(count.intValue()==0){
		return true;
	}else{
		return false;
	}
}


	@Override	
	public int obtenerNumeroPendientes() {
		
		//Mesa de partes ponen documento en proceso
		
		Long count = (Long) getSessionFactory().openSession()
		.createQuery("select COUNT(IDDOCUMENTO) from Documento where RESPUESTA ='en proceso' and estado='atendido' and ESTADODGAI is null").uniqueResult();
		return count.intValue();
	}
	
	@Override
	public int obtenerNumeroPendientesMes(int mes) {
		
	
		Long count = (Long) getSessionFactory().openSession()
		.createQuery("select COUNT(IDDOCUMENTO) from Documento where RESPUESTA ='en proceso' and estado='atendido' and month(fecharegistro)='"+mes+"'  and ESTADODGAI is null").uniqueResult();
	
		return count.intValue();
				
		
	}
	@Override
	public int obtenerNumeroPendientesMes() {
		
		//Mesa de partes ponen documento en proceso
		Calendar fecha = Calendar.getInstance();
		 int nroMes=fecha.getTime().getMonth()+1;
		
		Long count = (Long) getSessionFactory().openSession()
		.createQuery("select COUNT(IDDOCUMENTO) from Documento where RESPUESTA ='en proceso' and estado='atendido' and month(fecharegistro)='"+nroMes+"'  and ESTADODGAI is null").uniqueResult();
	
		return count.intValue();
				
		
	}
	@Override
	public int obtenerNumeroDerivadosMes(int  mes) {
		
		Long count = (Long) getSessionFactory().openSession() 
				.createQuery("select COUNT(IDDOCUMENTO) from Documento where ESTADODGAI ='derivado'  and month(fecharegistro)='"+mes+"'").uniqueResult();
				return count.intValue();
	}
	@Override
	public int obtenerNumeroDerivadosMes() {
		Calendar fecha = Calendar.getInstance();
		 int nroMes=fecha.getTime().getMonth()+1;
		Long count = (Long) getSessionFactory().openSession() 
				.createQuery("select COUNT(IDDOCUMENTO) from Documento where ESTADODGAI ='derivado'  and month(fecharegistro)='"+nroMes+"'").uniqueResult();
				return count.intValue();
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
				
				Query modulo=session.createSQLQuery("select  m.IDMODULO from PAGINAMODULO as pm inner join MODULO m on pm.IDMODULO= m.IDMODULO  inner join PAGINA as p on p.IDPAGINA=pm.IDPAGINA where P.NOMBREPAGINA='"+url+"'");
				
				if (modulo.list().size()==1) {
					int var=(Integer) modulo.list().get(0);
					mod.setIdmodulo(var);
				} else {
					int var=(Integer) modulo.list().get(0);
					mod.setIdmodulo(var);
				}
				
				
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
public Documento obtenerUltimodocumento() {
//	return  (Flujodocumento) getSessionFactory().openSession()
//			.createQuery("select COUNT(IDDOCUMENTO) from Documento  where RESPUESTA ='recibido' and estado='atendido'").uniqueResult();
	Session session = getSessionFactory().openSession();
	return (Documento) session.createQuery("from Documento D where D.estado='atendido' and D.respuesta='en proceso' ").uniqueResult();
	
}



@Override
public List<Usuario> obtenerUsuarios() {
	Session session = getSessionFactory().openSession();
	return session.createQuery("from Usuario").list();
}



@Override
public List<Perfilusuario> getPerfilesUsuario(int idUsuarioSeleccionado) {
	Session session = sessionFactory.openSession();
	List<List> list = null;
	Calendar fecha = Calendar.getInstance();
	 int nroMes=fecha.getTime().getMonth()+1;
	 
	 list = session.createQuery("select new List (p.idperfil,p.nombreperfil) from Perfilusuario pu inner join pu.usuario u inner join pu.perfil p where u.idusuario='"+idUsuarioSeleccionado+"'").list();
		
		
		List<Perfilusuario> lista = new ArrayList<Perfilusuario>();
		
		
		for(int i=0;i<list.size();i++){
			
			Perfilusuario pu = new Perfilusuario();
			
			Perfil p= new Perfil();
			
			p.setIdperfil((Integer) list.get(i).get(0));
			p.setNombreperfil((String) list.get(i).get(1));
			

			
			pu.setPerfil(p);
			
			
			lista.add(pu);
		}
		return lista;
}



@Override
public List<Perfil> obtenerPerfiles() {
	Session session = sessionFactory.openSession();
	return session.createQuery("from Perfil").list();
}








//@Override
//public List<Documento> listarDocumentosDgai() {
//	Session session = getSessionFactory().openSession();
//	try {
//		return session.createQuery("from Documento D where D.estado='atendido' and D.respuesta='en proceso' ").list();
//	} catch (HibernateException e) {
//		System.out.println("error:::" + e);
//		throw e;
//	} finally {
//		session.close();
//	}
//}	

}

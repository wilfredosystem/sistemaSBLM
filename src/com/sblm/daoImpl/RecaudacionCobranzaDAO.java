package com.sblm.daoImpl;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.sblm.dao.IRecaudacionAutovaluoDAO;
import com.sblm.dao.IRecaudacionCobranzaDAO;
import com.sblm.model.Auditoria;
import com.sblm.model.Documento;
import com.sblm.model.Estadoauditoria;
import com.sblm.model.Eventoauditoria;
import com.sblm.model.Flujodocumento;
import com.sblm.model.Modulo;
import com.sblm.model.Perfil;
import com.sblm.model.Perfilusuario;
import com.sblm.model.Seguimientoflujo;
import com.sblm.model.Usuario;
import com.sblm.util.FuncionesHelper;

@Repository(value = "recaudacionCobranzaDAO")
public class RecaudacionCobranzaDAO implements IRecaudacionCobranzaDAO, Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<Seguimientoflujo> obtenerSeguimientoFlujodeDocumento(int mesSeleccionado) {
		Session session = sessionFactory.openSession();
		List<List> list = null;
		if(mesSeleccionado==0){
			list = session.createQuery("select new List(F.idflujodocumento,D.fechaderivaciondgai,D.titulo,D.nombreremitente,D.asunto,D.descripcion,U.idusuario,D.iddocumento,F.estadosupervisor,S.fecharecepcion,S.idusuarioremitente,S.idsegflujodocumento,U.idusuario) from Seguimientoflujo S inner join S.flujodocumento F   inner join S.usuario U inner join F.documento D where U.idusuario='"+FuncionesHelper.getUsuario().toString()+"' and S.idperfilusuario='"+124+"' and S.estado='NINGUNO' order by S.fecharecepcion desc ").list();
			
		}else {
			
			list = session.createQuery("select new List(F.idflujodocumento,D.fechaderivaciondgai,D.titulo,D.nombreremitente,D.asunto,D.descripcion,U.idusuario,D.iddocumento,F.estadosupervisor,S.fecharecepcion,S.idusuarioremitente,S.idsegflujodocumento,U.idusuario) from Seguimientoflujo S inner join S.flujodocumento F   inner join S.usuario U inner join F.documento D where U.idusuario='"+FuncionesHelper.getUsuario().toString()+"' and S.idperfilusuario='"+124+"' and S.estado='NINGUNO' and month(S.fecharecepcion)='"+mesSeleccionado+"' order by S.fecharecepcion desc ").list();
		}
			
		
		
		
		List<Seguimientoflujo> lista = new ArrayList<Seguimientoflujo>();
		
		
		for(int i=0;i<list.size();i++){
			
			Seguimientoflujo seg= new Seguimientoflujo();
			seg.setIdsegflujodocumento((Integer) list.get(i).get(11));
			seg.setIdusuarioremitente((Integer) list.get(i).get(10));
			seg.setFecharecepcion((Date) list.get(i).get(9));
			
			Flujodocumento flu= new Flujodocumento();
			
			flu.setIdflujodocumento((Integer) list.get(i).get(0));
			
			
			Documento doc= new Documento();
			doc.setFechaderivaciondgai((Date) list.get(i).get(1));
			doc.setTitulo((String) list.get(i).get(2));
			doc.setNombreremitente((String) list.get(i).get(3));
			doc.setAsunto((String) list.get(i).get(4));
			doc.setDescripcion((String) list.get(i).get(5));
			doc.setIddocumento((Integer) list.get(i).get(7));
			
			flu.setDocumento(doc);
			seg.setFlujodocumento(flu);
			
			lista.add(seg);
		}
		return lista;
		
	}

	@Override
	public void regresarSupervisor(int idsegflujodocumento) {
		SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String updateQuery="UPDATE SEGUIMIENTOFLUJO SET ESTADO='ATENDIDO', RESPUESTA='APROBADO', fechaaprobacionrechazo='"+formateador.format(new Date())+"' WHERE IDSEGFLUJODOCUMENTO='"+idsegflujodocumento+"'";
		sessionFactory.getCurrentSession().createSQLQuery(updateQuery).executeUpdate();
	}

	@Override
	public List<Seguimientoflujo> obtenerSeguimientoFlujodeDocumentoAtendido(
			int mesSeleccionado) {
		Session session = sessionFactory.openSession();
		List<List> list = null;
		if(mesSeleccionado==0){
			list = session.createQuery("select new List(F.idflujodocumento,D.fechaderivaciondgai,D.titulo,D.nombreremitente,D.asunto,D.descripcion,U.idusuario,D.iddocumento,F.estadosupervisor,F.fecharecepcion,S.idusuarioremitente,S.idsegflujodocumento,U.idusuario,S.respuesta,S.fechaaprobacionrechazo) from Seguimientoflujo S inner join S.flujodocumento F   inner join S.usuario U inner join F.documento D where U.idusuario='"+FuncionesHelper.getUsuario().toString()+"' and S.estado!='NINGUNO' and S.idperfilusuario='"+124+"' order by S.fechaaprobacionrechazo desc ").list();
			
		}else {
			list = session.createQuery("select new List(F.idflujodocumento,D.fechaderivaciondgai,D.titulo,D.nombreremitente,D.asunto,D.descripcion,U.idusuario,D.iddocumento,F.estadosupervisor,F.fecharecepcion,S.idusuarioremitente,S.idsegflujodocumento,U.idusuario,S.respuesta,S.fechaaprobacionrechazo) from Seguimientoflujo S inner join S.flujodocumento F   inner join S.usuario U inner join F.documento D where U.idusuario='"+FuncionesHelper.getUsuario().toString()+"' and S.estado!='NINGUNO' and month(S.fechaaprobacionrechazo)='"+mesSeleccionado+"' and S.idperfilusuario='"+124+"' order by S.fechaaprobacionrechazo desc ").list();
		}
			
		
		
		
		List<Seguimientoflujo> lista = new ArrayList<Seguimientoflujo>();
		
		
		for(int i=0;i<list.size();i++){
			
			Seguimientoflujo seg= new Seguimientoflujo();
			seg.setIdusuarioremitente((Integer) list.get(i).get(10));
			seg.setRespuesta((String) list.get(i).get(13));
			seg.setFechaaprobacionrechazo((Date) list.get(i).get(14));
			
			Flujodocumento flu= new Flujodocumento();
			
			flu.setIdflujodocumento((Integer) list.get(i).get(0));
			
			
			Documento doc= new Documento();
			
			doc.setTitulo((String) list.get(i).get(2));
			doc.setNombreremitente((String) list.get(i).get(3));
			doc.setAsunto((String) list.get(i).get(4));
			doc.setDescripcion((String) list.get(i).get(5));
			doc.setIddocumento((Integer) list.get(i).get(7));
			
			flu.setDocumento(doc);
			seg.setFlujodocumento(flu);
			
			lista.add(seg);
		}
		return lista;
		
	}

	@Override
	public void actualizarComentarioRespuestaSeguimientoFlujoDocumento(
			int idsegflujodocumento, String comentarioRechazo,
			int idusuarioremitente) {

		SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String updateQuery="UPDATE SEGUIMIENTOFLUJO SET COMENTARIO='"+comentarioRechazo+"' , RESPUESTA='RECHAZADO', ESTADO='ATENDIDO', fechaaprobacionrechazo='"+formateador.format(new Date())+"' WHERE IDSEGFLUJODOCUMENTO='"+idsegflujodocumento+"'";
		sessionFactory.getCurrentSession().createSQLQuery(updateQuery).executeUpdate();
		
			settingLog(1, 10, idusuarioremitente,"");
		
	}
	
public  void settingLog(int idestadoauditoria, int ideventoauditoria, int idusuariodestino, String mensajePersonalizado){
		
		String url=FuncionesHelper.getURL().toString();
	
		try {
			int index = url.indexOf("pages/");
			url=url.substring(index+6, url.length());
			url=url.substring(0, url.length()-4);
		} catch (Exception e) {e.getMessage();	}

				Session session = sessionFactory.openSession();
				Auditoria Adt= new Auditoria();
				Usuario usr= new Usuario();
				usr.setIdusuario((Integer)(FuncionesHelper.getUsuario()));
				
				Usuario usrdes= new Usuario();
				usrdes.setIdusuario(idusuariodestino);
				
				Modulo mod= new Modulo();
				mod.setIdmodulo((Integer) session.createSQLQuery("select  m.IDMODULO from PAGINAMODULO as pm inner join MODULO m on pm.IDMODULO= m.IDMODULO  inner join PAGINA as p on p.IDPAGINA=pm.IDPAGINA where P.NOMBREPAGINA='"+url+"'").uniqueResult());
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
			sessionFactory.getCurrentSession().save(Adt);
		
		} catch (Exception e) {
		e.printStackTrace();
		}
	
	}

	@Override
	public List<Seguimientoflujo> listaFiltroDocumentosMes(String respuesta) {
	
		Session session = sessionFactory.openSession();
		List<List> list = null;
		
		Calendar fecha = Calendar.getInstance();
		 int nroMes=fecha.getTime().getMonth()+1;
		 
			list = session.createQuery("select new List(F.idflujodocumento,D.fechaderivaciondgai,D.titulo,D.nombreremitente,D.asunto,D.descripcion,U.idusuario,D.iddocumento,F.estadosupervisor,S.fechaaprobacionrechazo,S.idusuarioremitente,S.idsegflujodocumento,U.idusuario,S.respuesta) from Seguimientoflujo S inner join S.flujodocumento F   inner join S.usuario U inner join F.documento D where U.idusuario='"+FuncionesHelper.getUsuario().toString()+"' and S.respuesta='"+respuesta+"' and  S.estado='ATENDIDO' and Month(S.fechaaprobacionrechazo)='"+nroMes+"' and S.idperfilusuario='"+124+"' order by S.fechaaprobacionrechazo  desc").list();
			
		
		
		
		List<Seguimientoflujo> lista = new ArrayList<Seguimientoflujo>();
		
		
		for(int i=0;i<list.size();i++){
			
			Seguimientoflujo seg= new Seguimientoflujo();
			seg.setIdusuarioremitente((Integer) list.get(i).get(10));
			seg.setFechaaprobacionrechazo((Date) list.get(i).get(9));
			seg.setRespuesta((String) list.get(i).get(13));
			
			Flujodocumento flu= new Flujodocumento();
			
			flu.setIdflujodocumento((Integer) list.get(i).get(0));
			
			
			Documento doc= new Documento();
			doc.setFechaderivaciondgai((Date) list.get(i).get(1));
			doc.setTitulo((String) list.get(i).get(2));
			doc.setNombreremitente((String) list.get(i).get(3));
			doc.setAsunto((String) list.get(i).get(4));
			doc.setDescripcion((String) list.get(i).get(5));
			doc.setIddocumento((Integer) list.get(i).get(7));
			
			flu.setDocumento(doc);
			seg.setFlujodocumento(flu);
			
			lista.add(seg);
		}
		return lista;
		
	}
	
	@Override
	public int obtenerNumeroDerivadosMes(int mesActualcapturado) {
		Long count = (Long) sessionFactory.openSession() 
				.createQuery("select COUNT(IDSEGFLUJODOCUMENTO) from Seguimientoflujo  S   inner join S.usuario U where U.idusuario='"+FuncionesHelper.getUsuario().toString()+"' and S.estado='atendido' and month(S.fechaaprobacionrechazo)='"+mesActualcapturado+"' and S.idperfilusuario='"+124+"' ").uniqueResult();
				return count.intValue();
	}
	
	@Override
	public int obtenerNumeroDocumentosMes(String respuesta) {
		Long count = (Long) sessionFactory.openSession()
				.createQuery("select COUNT(IDSEGFLUJODOCUMENTO) from Seguimientoflujo S  inner join S.usuario U  where  U.idusuario='"+FuncionesHelper.getUsuario().toString()+"' and S.estado='atendido' and S.respuesta ='"+respuesta+"' and S.idperfilusuario='"+124+"'").uniqueResult();
				return count.intValue();
	}
	
	@Override
	public int obtenerNumeroDerivados() {
		Long count = (Long) sessionFactory.openSession() 
				.createQuery("select COUNT(IDSEGFLUJODOCUMENTO) from Seguimientoflujo S  inner join S.usuario U  where  U.idusuario='"+FuncionesHelper.getUsuario().toString()+"' and S.estado='atendido' and S.idperfilusuario='"+124+"' ").uniqueResult();
				return count.intValue();
	}
	
	@Override
	public int obtenerNumeroPendientesMes(int mesActualcapturado) {
		Long count = (Long) sessionFactory.openSession()
				.createQuery("select COUNT(IDSEGFLUJODOCUMENTO) from Seguimientoflujo S  inner join S.usuario U  where  U.idusuario='"+FuncionesHelper.getUsuario().toString()+"' and S.estado='atendido' and month(S.fecharecepcion)='"+mesActualcapturado+"' and S.idperfilusuario='"+124+"' ").uniqueResult();
				return count.intValue();
	}
	
	@Override
	public int obtenerNumeroPendientes() {
		Long count = (Long) sessionFactory.openSession()
				.createQuery("select COUNT(IDSEGFLUJODOCUMENTO) from Seguimientoflujo S  inner join S.usuario U  where  U.idusuario='"+FuncionesHelper.getUsuario().toString()+"' and S.estado='NINGUNO'  and S.idperfilusuario='"+124+"'").uniqueResult();
				return count.intValue();
	}
	
	@Override
	public int obtenerNumeroDocumentosMes(String respuesta, int mesActualcapturado) {
		Long count = (Long) sessionFactory.openSession()
		.createQuery("select COUNT(IDSEGFLUJODOCUMENTO) from Seguimientoflujo S  inner join S.usuario U  where  U.idusuario='"+FuncionesHelper.getUsuario().toString()+"' and S.estado='derivado' and S.respuesta ='"+respuesta+"' and month(S.fechaaprobacionrechazo)='"+mesActualcapturado+"' and S.idperfilusuario='"+124+"'").uniqueResult();
		return count.intValue();
	}

	@Override
	public Boolean estaDocumentoFinalizado(int idsegflujodocumento) {
		Long count = (Long) sessionFactory.openSession()
				.createQuery("select count(*) from Seguimientoflujo S  where S.idsegflujodocumento='"+idsegflujodocumento+"' and S.respuesta='en proceso' and S.idperfilusuario='"+124+"'").uniqueResult();
		if(count.intValue()==0){
			return true;
		}else{
			return false;
		}
	}

}

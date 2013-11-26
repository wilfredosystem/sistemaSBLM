package com.sblm.daoImpl;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sblm.dao.IArrendamientoSupervisorDAO;
import com.sblm.dao.IRecaudacionSupervisorDAO;
import com.sblm.model.Area;
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

@Repository(value = "arrendamientoSupervisorDAO")
public class ArrendamientoSupervisorDAO implements IArrendamientoSupervisorDAO, Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<Flujodocumento> getFlujoDocumentosSupervisor() {
		Session session = sessionFactory.openSession();
		List<List> list = null;
		
		list = session.createQuery("select new List(F.idflujodocumento,D.fechaderivaciondgai,D.titulo,D.nombreremitente,D.asunto,D.descripcion,U.idusuario,D.iddocumento,F.estadosupervisor,F.fecharecepcion,F.idusuarioremitente) from Flujodocumento F   inner join F.usuario U inner join F.documento D where U.idusuario='"+FuncionesHelper.getUsuario().toString()+"' and F.estadosupervisor='NINGUNO' and F.idperfilusuario='"+127+"' order by F.fecharecepcion desc ").list();
		List<Flujodocumento> lista = new ArrayList<Flujodocumento>();
		
		
		for(int i=0;i<list.size();i++){
			
			Flujodocumento flu= new Flujodocumento();
			
			flu.setIdflujodocumento((Integer) list.get(i).get(0));
			flu.setIdusuarioremitente((Integer) list.get(i).get(10));
			
			Documento doc= new Documento();
			doc.setFechaderivaciondgai((Date) list.get(i).get(1));
			doc.setTitulo((String) list.get(i).get(2));
			doc.setNombreremitente((String) list.get(i).get(3));
			doc.setAsunto((String) list.get(i).get(4));
			doc.setDescripcion((String) list.get(i).get(5));
			doc.setIddocumento((Integer) list.get(i).get(7));
			
			flu.setDocumento(doc);
			
			
			lista.add(flu);
		}
		
		
		return lista;
	
	}
	
	
	@Override
	public List<Flujodocumento> getFlujoDocumentosSupervisorAtendidos() {
		Session session = sessionFactory.openSession();
		List<List> list = null;
		
		list = session.createQuery("select new List(F.idflujodocumento,F.fechaderivacionsupervisor,D.titulo,D.nombreremitente,D.asunto,D.descripcion,U.idusuario,D.iddocumento,F.idusuarioremitente,F.respuesta,F.fechaaprobacionrechazo) from Flujodocumento  F   inner join F.usuario U inner join F.documento D where U.idusuario='"+FuncionesHelper.getUsuario().toString()+"' and F.estadosupervisor='DERIVADO' and F.idperfilusuario='"+127+"' order by F.fechaderivacionsupervisor desc").list();
		List<Flujodocumento> lista = new ArrayList<Flujodocumento>();
		
		
		for(int i=0;i<list.size();i++){
			
			Flujodocumento flu= new Flujodocumento();
			
			flu.setIdflujodocumento((Integer) list.get(i).get(0));
			flu.setFechaderivacionsupervisor((Date) list.get(i).get(1));
			flu.setIdusuarioremitente((Integer) list.get(i).get(8));
			flu.setRespuesta((String) list.get(i).get(9));
			flu.setFechaaprobacionrechazo((Date) list.get(i).get(10));
			
			Documento doc= new Documento();
			doc.setTitulo((String) list.get(i).get(2));
			doc.setNombreremitente((String) list.get(i).get(3));
			doc.setAsunto((String) list.get(i).get(4));
			doc.setDescripcion((String) list.get(i).get(5));
			doc.setIddocumento((Integer) list.get(i).get(7));
			
			flu.setDocumento(doc);
			
			
			lista.add(flu);
		}
		return lista;
	}

	@Override
	public List<Usuario> obtenerUsuarios() {
		Session session = sessionFactory.openSession();
		List<List> list = null;
		
		
		list = session.createQuery("select new List(U.idusuario,U.rutaimgusr,U.cargo,A.desare,U.nombrescompletos) from Usuario U inner join U.area A").list();
		List<Usuario> lista = new ArrayList<Usuario>();
		
		
		for(int i=0;i<list.size();i++){
			
			Area area= new Area();
			
			area.setDesare((String) list.get(i).get(3));
			
			System.out.println(area.getDesare());
			
			
			Usuario usuario= new Usuario();
			usuario.setRutaimgusr((String) list.get(i).get(1));
			usuario.setCargo((String) list.get(i).get(2));
			usuario.setIdusuario((Integer) list.get(i).get(0));
			usuario.setNombrescompletos((String)list.get(i).get(4));
			
			usuario.setArea(area);
			
			
			lista.add(usuario);
		}
		
		
		return lista;
		
	}

	@Override
	public void registrarSeguimientoFlujo(Seguimientoflujo segflujo, int idusuario) {
		sessionFactory.getCurrentSession().save(segflujo);
		
		try {
			settingLog(1, 9, idusuario,"");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void actualizarFlujoDocumentoSupervisor(int iddocumento) {
		SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String updateQuery="UPDATE FLUJODOCUMENTO SET ESTADOSUPERVISOR='DERIVADO', FECHADERIVACIONSUPERVISOR='"+formateador.format(new Date())+"' WHERE IDFLUJODOCUMENTO='"+iddocumento+"' ";
		sessionFactory.getCurrentSession().createSQLQuery(updateQuery).executeUpdate();
	}


	@Override
	public List<Seguimientoflujo> obtenerSeguimientoFlujodeDocumento(int idflujoDocumento) {
		Session session = sessionFactory.openSession();
		List<List> list = null;
		
		list = session.createQuery("select new List(S.idsegflujodocumento,S.respuesta,U.apellidopat,U.apellidomat,U.nombres,U.idusuario,U.rutaimgusr,U.cargo) from Seguimientoflujo S inner join S.flujodocumento F inner join F.documento D inner join S.usuario U where F.idflujodocumento='"+idflujoDocumento+"'").list();
		List<Seguimientoflujo> lista = new ArrayList<Seguimientoflujo>();
		
		
		for(int i=0;i<list.size();i++){
			
			Seguimientoflujo seg= new Seguimientoflujo();
			
			seg.setIdsegflujodocumento((Integer) list.get(i).get(0));
			//flu.setNumero((Integer) list.get(i).get(1));
			seg.setRespuesta((String) list.get(i).get(1));
			
			
			//flu.setEstadoenvio((Boolean) list.get(i).get(3));
			
			Usuario usuario= new Usuario();
			usuario.setApellidopat((String) list.get(i).get(2));
			usuario.setApellidomat((String) list.get(i).get(3));
			usuario.setNombres((String) list.get(i).get(4));
			usuario.setIdusuario((Integer) list.get(i).get(5));
			usuario.setRutaimgusr((String) list.get(i).get(6));
			usuario.setCargo((String) list.get(i).get(7));
			
			seg.setUsuario(usuario);
			
			
			lista.add(seg);
		}
		
		
		return lista;
	}


	@Override
	public void actualizarComentarioRespuestaFlujoDocumento(
			int idflujodocumento, String comentarioRechazo,
			List<Seguimientoflujo> seguimientoFlujoDocumento) {
		SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String updateQuery="UPDATE FLUJODOCUMENTO SET COMENTARIO='"+comentarioRechazo+"' , RESPUESTA='RECHAZADO', fechaaprobacionrechazo='"+formateador.format(new Date())+"' WHERE IDFLUJODOCUMENTO='"+idflujodocumento+"'";
		sessionFactory.getCurrentSession().createSQLQuery(updateQuery).executeUpdate();
		
		for(int i=0;i<seguimientoFlujoDocumento.size();i++){
			settingLog(1, 10, seguimientoFlujoDocumento.get(i).getUsuario().getIdusuario(),"");
		}
	}





	@Override
	public void regresarDGAI(int idflujodocumento) {
		SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String updateQuery="UPDATE FLUJODOCUMENTO SET RESPUESTA='APROBADO', fechaaprobacionrechazo='"+formateador.format(new Date())+"' WHERE IDFLUJODOCUMENTO='"+idflujodocumento+"'";
		sessionFactory.getCurrentSession().createSQLQuery(updateQuery).executeUpdate();
		
	}


	@Override
	public int obtenertamanioFlujoDocumento(int idflujodocumento) {
		Long count = (Long) sessionFactory.openSession()
				.createQuery("select count(*) from Seguimientoflujo S inner join S.flujodocumento F where F.idflujodocumento='"+idflujodocumento+"'").uniqueResult();
				return count.intValue();
	}


	@Override
	public Boolean estaDocumentoFinalizado(int idflujodocumento) {
		Long count = (Long) sessionFactory.openSession()
				.createQuery("select count(*) from Flujodocumento F  where F.idflujodocumento='"+idflujodocumento+"' and F.respuesta='en proceso'").uniqueResult();
		if(count.intValue()==0){
			return true;
		}else{
			return false;
		}
	}


	@Override
	public Boolean estaFlujoFinalizado(int idflujodocumento) {
		Long count = (Long) sessionFactory.openSession()
				.createQuery("select count(*) from Seguimientoflujo S inner join S.flujodocumento F  where F.idflujodocumento='"+idflujodocumento+"' and S.respuesta='en proceso'").uniqueResult();
		if(count.intValue()==0){
			return true;
		}else{
			return false;
		}
	}

	/**/
	@Override
	public int obtenerNumeroDocumentosMes(String respuesta,
			int mesActualcapturado) {
		Long count = (Long) sessionFactory.openSession()
		.createQuery("select COUNT(IDFLUJODOCUMENTO) from Flujodocumento F  inner join F.usuario U  where  U.idusuario='"+FuncionesHelper.getUsuario().toString()+"' and F.idperfilusuario='"+127+"' and F.estadosupervisor='derivado' and F.respuesta ='"+respuesta+"' and month(F.fecharecepcion)='"+mesActualcapturado+"' ").uniqueResult();
		return count.intValue();
	}


	@Override
	public int obtenerNumeroDocumentosMes(String respuesta) {
		Long count = (Long) sessionFactory.openSession()
		.createQuery("select COUNT(IDFLUJODOCUMENTO) from Flujodocumento F  inner join F.usuario U  where  U.idusuario='"+FuncionesHelper.getUsuario().toString()+"' and F.idperfilusuario='"+127+"'  and F.estadosupervisor='derivado' and F.respuesta ='"+respuesta+"'").uniqueResult();
		return count.intValue();
	}

/**/
	@Override
	public int obtenerNumeroDerivadosMes(int mesActualcapturado) {
		Long count = (Long) sessionFactory.openSession() 
				.createQuery("select COUNT(IDFLUJODOCUMENTO) from Flujodocumento  F   inner join F.usuario U where U.idusuario='"+FuncionesHelper.getUsuario().toString()+"' and F.idperfilusuario='"+127+"'  and F.estadosupervisor='DERIVADO' and month(F.fechaderivacionsupervisor)='"+mesActualcapturado+"'  ").uniqueResult();
				return count.intValue();
	}




	@Override
	public int obtenerNumeroDerivados() {
		Long count = (Long) sessionFactory.openSession() 
				.createQuery("select COUNT(IDFLUJODOCUMENTO) from Flujodocumento  F   inner join F.usuario U where U.idusuario='"+FuncionesHelper.getUsuario().toString()+"' and F.idperfilusuario='"+127+"'  and F.estadosupervisor='DERIVADO' ").uniqueResult();
				return count.intValue();
	}


	
	
	/**/
	@Override
	public int obtenerNumeroPendientes() {
		Long count = (Long) sessionFactory.openSession()
				.createQuery("select COUNT(IDFLUJODOCUMENTO) from Flujodocumento F inner join F.usuario U  where U.idusuario='"+FuncionesHelper.getUsuario().toString()+"' and F.idperfilusuario='"+127+"'  and F.estadosupervisor='NINGUNO' ").uniqueResult();
				return count.intValue();
	}


	@Override
	public int obtenerNumeroPendientesMes(int mesActualcapturado) {
		Long count = (Long) sessionFactory.openSession()
				.createQuery("select COUNT(IDFLUJODOCUMENTO) from Flujodocumento F inner join F.usuario U  where U.idusuario='"+FuncionesHelper.getUsuario().toString()+"' and F.idperfilusuario='"+127+"'  and F.estadosupervisor='NINGUNO' and month(F.fecharecepcion)='"+mesActualcapturado+"'  ").uniqueResult();
				return count.intValue();
	}

	
	
	
	
	@Override
	public List<Flujodocumento> getFlujoDocumentosSupervisorAtendidos(
			int mesSeleccionado) {
		Session session = sessionFactory.openSession();
		List<List> list = null;
		
		if(mesSeleccionado==0){
			list = session.createQuery("select new List(F.idflujodocumento,F.fechaderivacionsupervisor,D.titulo,D.nombreremitente,D.asunto,D.descripcion,U.idusuario,D.iddocumento,F.idusuarioremitente,F.respuesta,F.fechaaprobacionrechazo) from Flujodocumento  F   inner join F.usuario U inner join F.documento D where U.idusuario='"+FuncionesHelper.getUsuario().toString()+"' and F.idperfilusuario='"+127+"'  and F.estadosupervisor='DERIVADO' order by F.fechaderivacionsupervisor desc").list();

		}
		else{
			list = session.createQuery("select new List(F.idflujodocumento,F.fechaderivacionsupervisor,D.titulo,D.nombreremitente,D.asunto,D.descripcion,U.idusuario,D.iddocumento,F.idusuarioremitente,F.respuesta,F.fechaaprobacionrechazo) from Flujodocumento  F   inner join F.usuario U inner join F.documento D where U.idusuario='"+FuncionesHelper.getUsuario().toString()+"' and F.idperfilusuario='"+127+"'  and F.estadosupervisor='DERIVADO'  and month(F.fechaderivacionsupervisor)='"+mesSeleccionado+"'order by F.fechaderivacionsupervisor desc").list();
		}
		
		
		List<Flujodocumento> lista = new ArrayList<Flujodocumento>();
		
		
		for(int i=0;i<list.size();i++){
			
			Flujodocumento flu= new Flujodocumento();
			
			flu.setIdflujodocumento((Integer) list.get(i).get(0));
			flu.setFechaderivacionsupervisor((Date) list.get(i).get(1));
			flu.setIdusuarioremitente((Integer) list.get(i).get(8));
			flu.setRespuesta((String) list.get(i).get(9));
			flu.setFechaaprobacionrechazo((Date) list.get(i).get(10));
			
			Documento doc= new Documento();
			doc.setTitulo((String) list.get(i).get(2));
			doc.setNombreremitente((String) list.get(i).get(3));
			doc.setAsunto((String) list.get(i).get(4));
			doc.setDescripcion((String) list.get(i).get(5));
			doc.setIddocumento((Integer) list.get(i).get(7));
			
			flu.setDocumento(doc);
			
			flu.setDocumento(doc);
			
			
			lista.add(flu);
		}
		return lista;

			
	}


	
	
	
	@Override
	public List<Flujodocumento> getFlujoDocumentosSupervisor(int mesSeleccionado) {
		Session session = sessionFactory.openSession();
		List<List> list = null;
		if(mesSeleccionado==0){
			list = session.createQuery("select new List(F.idflujodocumento,D.fechaderivaciondgai,D.titulo,D.nombreremitente,D.asunto,D.descripcion,U.idusuario,D.iddocumento,F.estadosupervisor,F.fecharecepcion,F.idusuarioremitente) from Flujodocumento F   inner join F.usuario U inner join F.documento D where U.idusuario='"+FuncionesHelper.getUsuario().toString()+"' and F.idperfilusuario='"+127+"'  and F.estadosupervisor='NINGUNO' order by F.fecharecepcion desc ").list();
			
		}else {
			list= session.createQuery("select new List(F.idflujodocumento,D.fechaderivaciondgai,D.titulo,D.nombreremitente,D.asunto,D.descripcion,U.idusuario,D.iddocumento,F.estadosupervisor,F.fecharecepcion,F.idusuarioremitente) from Flujodocumento F inner join F.usuario U  where U.idusuario='"+FuncionesHelper.getUsuario().toString()+"' and F.idperfilusuario='"+127+"'  and F.estadosupervisor='NINGUNO'  and month(F.fecharecepcion)='"+mesSeleccionado+"'  order by F.fecharecepcion desc").list();	
		}
			
		
		
		
		List<Flujodocumento> lista = new ArrayList<Flujodocumento>();
		
		
		for(int i=0;i<list.size();i++){
			
			Flujodocumento flu= new Flujodocumento();
			
			flu.setIdflujodocumento((Integer) list.get(i).get(0));
			flu.setFecharecepcion((Date) list.get(i).get(9));
			flu.setIdusuarioremitente((Integer) list.get(i).get(10));
			
			Documento doc= new Documento();
			doc.setFechaderivaciondgai((Date) list.get(i).get(1));
			doc.setTitulo((String) list.get(i).get(2));
			doc.setNombreremitente((String) list.get(i).get(3));
			doc.setAsunto((String) list.get(i).get(4));
			doc.setDescripcion((String) list.get(i).get(5));
			doc.setIddocumento((Integer) list.get(i).get(7));
			
			flu.setDocumento(doc);
			
			
			lista.add(flu);
		}
		return lista;
		
		
		
	}


	@Override
	public List<Flujodocumento> listaFiltroDocumentosMes(String respuesta) {
		Session session = sessionFactory.openSession();
		List<List> list = null;
		Calendar fecha = Calendar.getInstance();
		 int nroMes=fecha.getTime().getMonth()+1;
		 
		 list = session.createQuery("select new List(F.idflujodocumento,F.fechaderivacionsupervisor,D.titulo,D.nombreremitente,D.asunto,D.descripcion,U.idusuario,D.iddocumento,F.idusuarioremitente,F.respuesta,F.fechaaprobacionrechazo) from Flujodocumento F inner join F.usuario U inner join F.documento D where U.idusuario='"+FuncionesHelper.getUsuario().toString()+"' and F.idperfilusuario='"+127+"'  and F.respuesta='"+respuesta+"' and  F.estadosupervisor='derivado' and Month(F.fechaderivacionsupervisor)='"+nroMes+"' order by F.fecharecepcion  desc").list();
			
			
			List<Flujodocumento> lista = new ArrayList<Flujodocumento>();
			
			
			for(int i=0;i<list.size();i++){
				
				Flujodocumento flu= new Flujodocumento();
				
				flu.setIdflujodocumento((Integer) list.get(i).get(0));
				flu.setFechaderivacionsupervisor((Date) list.get(i).get(1));
				flu.setIdusuarioremitente((Integer) list.get(i).get(8));
				flu.setRespuesta((String) list.get(i).get(9));
				flu.setFechaaprobacionrechazo((Date) list.get(i).get(10));
				
				Documento doc= new Documento();
				doc.setTitulo((String) list.get(i).get(2));
				doc.setNombreremitente((String) list.get(i).get(3));
				doc.setAsunto((String) list.get(i).get(4));
				doc.setDescripcion((String) list.get(i).get(5));
				doc.setIddocumento((Integer) list.get(i).get(7));
				
				flu.setDocumento(doc);
				
				
				lista.add(flu);
			}
			return lista;
			
		 
		
	}


	@Override
	public void enviarNotificaciónPersonalizable(
			String contenidoMensajePersonalizado,
			int idusuarioNotificacionPersonalizada) {
		try {
			settingLog(1, 12, idusuarioNotificacionPersonalizada,contenidoMensajePersonalizado);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
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
				
				Query modulo=session.createSQLQuery("select  m.IDMODULO from PAGINAMODULO as pm inner join MODULO m on pm.IDMODULO= m.IDMODULO  inner join PAGINA as p on p.IDPAGINA=pm.IDPAGINA where P.NOMBREPAGINA='"+url+"'");
				
				if (modulo.list().size()==1) {
					int var=(Integer) modulo.list().get(0);
					mod.setIdmodulo(var);
				} else {
					//mod.setIdmodulo(idmodulo)
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
			sessionFactory.getCurrentSession().save(Adt);
		
		} catch (Exception e) {
		e.printStackTrace();
		}
	
	}


@Override
public List<Perfilusuario> getPerfilesUsuario(int idusuario) {
	Session session = sessionFactory.openSession();
	List<List> list = null;
	Calendar fecha = Calendar.getInstance();
	 int nroMes=fecha.getTime().getMonth()+1;
	 
	 list = session.createQuery("select new List (p.idperfil,p.nombreperfil) from Perfilusuario pu inner join pu.usuario u inner join pu.perfil p where u.idusuario='"+idusuario+"'").list();
		
		
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
	
	
}

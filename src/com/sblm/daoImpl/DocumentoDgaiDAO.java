package com.sblm.daoImpl;

import java.io.Serializable;
import java.text.ParseException;
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

import com.sblm.dao.IDocumentoDgaiDAO;
import com.sblm.dao.IModuloDAO;
import com.sblm.model.Archivodocumento;
import com.sblm.model.Auditoria;
import com.sblm.model.Documento;
import com.sblm.model.Estadoauditoria;
import com.sblm.model.Eventoauditoria;
import com.sblm.model.Extensionarchivo;
import com.sblm.model.Flujodocumento;
import com.sblm.model.Modulo;
import com.sblm.model.Usuario;
import com.sblm.util.FuncionesHelper;

@Repository(value = "documentodgaiDAO")
public class DocumentoDgaiDAO implements IDocumentoDgaiDAO, Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void registrarDocumentoDgai(Documento documento) {
		getSessionFactory().getCurrentSession().merge(documento);

	}

	@Override
	public void actualizarDocumentoDgai(int id) {
		SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String updateQuery="UPDATE DOCUMENTO SET ESTADODGAI='DERIVADO', FECHADERIVACIONDGAI='"+formateador.format(new Date())+"' WHERE IDDOCUMENTO='"+id+"' ";
		getSessionFactory().getCurrentSession().createSQLQuery(updateQuery).executeUpdate();
		
	}

	@Override
	public void eliminarDocumentoDgai(Documento documento) {
		// TODO Auto-generated method stub

	}

	@Override
	public Modulo listarDocumentoDgaiPorId(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	

	@Override
	public List<Documento> getAllDocumentos() {
		Session session = getSessionFactory().openSession();
		return session.createQuery("from Documento D where D.estado='atendido'").list();
	}
	
	

	@Override
	public List<Documento> listaDocumentosSinDerivar() {
		Session session = getSessionFactory().openSession();
		return session.createQuery("from Documento D where D.estado='atendido' and D.estadodgai is null  order by D.fecharegistro  desc").list();
	}
	
	
	@Override
	public List<Documento> listaFiltroDocumentos(String val) {
		Session session = getSessionFactory().openSession();
		return session.createQuery("from Documento D where D.estado='atendido' and D.respuesta='"+val+"' and  D.estadodgai='derivado' order by D.fechadocumento  desc").list();
		
	}
	@Override
	public List<Documento> listaFiltroDocumentosMes(String val) {
		Session session = getSessionFactory().openSession();
		Calendar fecha = Calendar.getInstance();
		 int nroMes=fecha.getTime().getMonth()+1;
		return session.createQuery("from Documento D where D.estado='atendido' and D.respuesta='"+val+"' and  D.estadodgai='derivado' and Month(D.fecharegistro)='"+nroMes+"' order by D.fechadocumento  desc").list();
	
	}
	@Override
	public List<Documento> listaDocumentosDespachados() {
		Session session = getSessionFactory().openSession();
		return session.createQuery("from Documento D where D.estado='atendido' and D.estadodgai ='derivado' and D.respuesta='APROBADO'").list();
		
	}
	@Override
	public List<Documento> listaDocumentosPendientes() {
		Session session = getSessionFactory().openSession();
		return session.createQuery("from Documento D where D.estado='atendido' and D.estadodgai ='derivado' and D.respuesta='EN PROCESO'").list();
		
	}
	@Override
	public List<Documento> listaDocumentosRechazados() {
		Session session = getSessionFactory().openSession();
		return session.createQuery("from Documento D where D.estado='atendido' and D.estadodgai ='derivado' and D.respuesta='RECHAZADO'").list();
		
	}
	
	@Override
	public List<Documento> busquedaIntervaloFecha(Date fechaFin,
			Date fechaInicio) {
		Session session = getSessionFactory().openSession();
		java.text.SimpleDateFormat sdf=new java.text.SimpleDateFormat("dd/MM/yyyy");
		String fInicio = sdf.format(fechaInicio);
		Calendar c = Calendar.getInstance();
		String fFin= sdf.format(fechaFin);
		
		try {
			c.setTime(sdf.parse(fFin));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		c.add(Calendar.DATE, 1);
		fFin = sdf.format(c.getTime());
	
	String query="From Documento where fecharegistro>='"+fInicio+"' and fecharegistro<'"+fFin+"'";
		
		
		return session.createQuery(query).list();
	}
	



	
	@Override
	public List<Documento> listarDocumentosDgai() {
		Session session = getSessionFactory().openSession();
		try {
			return session.createQuery("from Documento D where D.estado='atendido' and D.respuesta='en proceso' ").list();
		} catch (HibernateException e) {
			System.out.println("error:::" + e);
			throw e;
		} finally {
			session.close();
		}
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public int totalPendientesDerivacion() {
	Long count = (Long)getSessionFactory().openSession().createQuery("select count(*) from Documento D where D.estado='atendido'").uniqueResult();
		
		return count.intValue();
 
	}
	public  void settingLog(int idusuario, int idmodulo, int idestadoauditoria, int ideventoauditoria, Date fechaentrada, String nombrepantalla,
			String url,Boolean estado, int codauditoria){


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
			getSessionFactory().getCurrentSession().save(Adt);
			
			} catch (Exception e) {
			e.printStackTrace();		}
}

	@Override
	public Documento obtenerUltimodocumento() {
		//wilfredo
		Session session = getSessionFactory().openSession();
		System.out.println("fff");
		Query q=session.createQuery("from Documento D where D.estado='atendido' and D.estadodgai='derivado'  ");
		q.setFirstResult(0);
		q.setMaxResults(1);
		
		com.sblm.model.Documento  D= new com.sblm.model.Documento();
		
		
		try {
			D=(com.sblm.model.Documento) q.list().get(0);
		} catch (Exception e) {
			D=null;
			System.out.println("error dao obtenerUltimodocumento::"+e.getMessage());
		}
		
		
		
		return D;
	}
	@Override
	public Documento obtenerUltimodocumentoDespachado() {
		//wilfredo
		Session session = getSessionFactory().openSession();
		
		Query q=session.createQuery("from Documento D where D.estado='atendido' and D.estadodgai='derivado' and D.respuesta='APROBADO' order by D.fecharespuestadgai desc ");
		q.setFirstResult(0);
		q.setMaxResults(1);
		
		com.sblm.model.Documento  D= new com.sblm.model.Documento();
		try {
			D=(com.sblm.model.Documento) q.list().get(0);
		} catch (Exception e) {
			D=null;
			System.out.println("error dao obtenerUltimodocumentoDespachado::"+e.getMessage());
		}
		
		
		System.out.println("aaaaaaaaa");
		
		return D;
	}
	

	@Override
	public List<Archivodocumento> cargarArchivosDocumento(int iddocumento) {
		
		
		Session session = getSessionFactory().openSession();
		List<List> list = null;
		System.out.println("consulta cargaarchivo##########");
		list = session.createQuery("select new List(A.idarchivodocumento,A.ruta,A.autorArchivo,A.area,EA.rutaIcono,A.usuarioCreador,A.fechaCreacion) from Archivodocumento A inner join A.documento D inner join  A.extensionarchivo EA where D.iddocumento='"+iddocumento+"'").list(); 
		
		
		List<Archivodocumento> lista = new ArrayList<Archivodocumento>();
		
		
		for(int i=0;i<list.size();i++){
			
			Archivodocumento ad= new Archivodocumento();
			Extensionarchivo ea= new Extensionarchivo();
			ea.setRutaIcono((String) list.get(i).get(4));
			
			ad.setIdarchivodocumento((Integer) list.get(i).get(0));
			ad.setRuta((String) list.get(i).get(1));
			ad.setAutorArchivo((Integer) list.get(i).get(2));
			ad.setArea((String) list.get(i).get(3));
			ad.setUsuarioCreador((String) list.get(i).get(5));
			ad.setFechaCreacion((Date) list.get(i).get(6));
			ad.setExtensionarchivo(ea);
			
			
			lista.add(ad);
		}
		return  lista;
				
		
		
	}



}

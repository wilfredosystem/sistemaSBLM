package com.sblm.daoImpl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sblm.dao.IArchivoAdjuntoDAO;
import com.sblm.model.Archivodocumento;
import com.sblm.model.Extensionarchivo;
import com.sblm.model.Inmueble;
import com.sblm.model.Inquilino;
import com.sblm.model.Upa;
@Repository(value = "archivoadjuntoDAO")
public class ArchivoAdjuntoDAO implements IArchivoAdjuntoDAO, Serializable {

	private static final long serialVersionUID = 1L;
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void registrarArchivosDocumento(Archivodocumento archivodocu) {
		System.out.println("paso dao:::"+archivodocu.getRuta());
		try {
			getSessionFactory().getCurrentSession().save(archivodocu);

		} catch (Exception e) {
		System.out.println("error:::"+e.getMessage());
		}
	}
	
	@Override
	public List<Extensionarchivo> getListaExtensionArhivos() {
		Session session = getSessionFactory().openSession();
		return session.createQuery("from Extensionarchivo").list();
	}
	@Override
	public List<Extensionarchivo> getListaExtensionArhivosPorRutaIcono(String ruta) {
		Session session = getSessionFactory().openSession();
		return session.createQuery("from Extensionarchivo where rutaIcono='"+ruta+"' ").list();
	}
	
	
	
	@Override
	public Extensionarchivo obtenerExtensionArhivoPorRutaIcono(String ruta) {
		Session session = getSessionFactory().openSession();
		return (Extensionarchivo) session.createQuery("from Extensionarchivo where rutaIcono='"+ruta+"' ").uniqueResult();
	}
	@Override
	public Extensionarchivo obtenerExtensionArhivoporId(int id) {
		Session session = getSessionFactory().openSession();
		return (Extensionarchivo) session.load(Extensionarchivo.class, id);
	}
	@Override
	public Extensionarchivo obtenerExtensionArhivoPorExtension(String ext) {
		Session session = getSessionFactory().openSession();
		return (Extensionarchivo) session.createQuery("from Extensionarchivo where ext='"+ext+"' ").uniqueResult();
	}
	@Override
	public Inmueble obtenerUltimoInmueble() {
		return (Inmueble) getSessionFactory()
				.openSession()
				.createQuery(
						"select t from Inmueble t where idinmueble=( select max(idinmueble) from Inmueble)")
				.uniqueResult();
	}
	@Override
	public Upa obtenerUltimoUpa() {
		return (Upa) getSessionFactory()
				.openSession()
				.createQuery(
						"select t from Upa t where idupa=( select max(idupa) from Upa)")
				.uniqueResult();
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
	public void eliminarArhivoDocumento(Inmueble inm) { 
		try {
			getSessionFactory()
					.getCurrentSession()
					.createSQLQuery(
							"delete from Archivodocumento WHERE idinmueble='"
									+ inm.getIdinmueble() + "'")
					.executeUpdate();
		} catch (Exception e) {
			System.out.println("error en dao eliminar eliminarArhivoDocumento inmueble:::"
					+ e.getMessage());
		}
	}
	@Override
	public void eliminarArhivoDocumentoUpa(Upa upa) { 
		try {
			getSessionFactory()
					.getCurrentSession()
					.createSQLQuery(
							"delete from Archivodocumento WHERE idupa='"
									+ upa.getIdupa() + "'")
					.executeUpdate();
		} catch (Exception e) {
			System.out.println("error en dao eliminar eliminarArhivoDocumento upa:::"
					+ e.getMessage());
		}
	}
	@Override
	public void eliminarArhivoDocumentoInquilino(Inquilino inquilino) { 
		try {
			getSessionFactory()
					.getCurrentSession()
					.createSQLQuery(
							"delete from Archivodocumento WHERE idinquilino='"
									+ inquilino.getIdinquilino() + "'")
					.executeUpdate();
		} catch (Exception e) {
			System.out.println("error en dao eliminar eliminarArhivoDocumento inquilino:::"
					+ e.getMessage());
		}
	}
	
	@Override
	public List<Archivodocumento> listarArchivodocumentos() {
		Session session = getSessionFactory().openSession();
		return session.createQuery("from Archivodocumento").list();
	}
	@Override
	public Archivodocumento obtenerUltimoArchivodocumento() {  
		return (Archivodocumento) getSessionFactory()
				.openSession()
				.createQuery(
						"select t from Archivodocumento t where idarchivodocumento=( select max(idarchivodocumento) from Archivodocumento)")
				.uniqueResult();
	}
	@Override
	public Archivodocumento obtenerArchivodocumentoPorId(int id) {
		Session session = getSessionFactory().openSession();
		return (Archivodocumento) session.load(Archivodocumento.class, id);
	}
	@Override
	public List<Archivodocumento> obtenerArchivodocumentosPorIdInmueble(int id) {
		Session session = getSessionFactory().openSession();
		return session.createQuery("from Archivodocumento ad where ad.inmueble.idinmueble='"+id+"' ").list();//aca verifcar
	}
	@Override
	public List<Archivodocumento> obtenerArchivodocumentosPorIdUpa(int id) {
		Session session = getSessionFactory().openSession();
		return session.createQuery("from Archivodocumento ad where ad.upa.idupa='"+id+"' ").list();//aca verifcar
	}
	@Override
	public List<Archivodocumento> obtenerArchivodocumentosPorIdInquilino(int id) {
		Session session = getSessionFactory().openSession();
		return session.createQuery("from Archivodocumento ad where ad.inquilino.idinquilino='"+id+"' ").list();//aca verifcar
	}
	
	@Override
	public int obtenerNumeroRegistros() {
		Long count = (Long) getSessionFactory().openSession()
				.createQuery("select count(*) from Archivodocumento").uniqueResult(); 
		return count.intValue();
	}
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	
}

package com.sblm.daoImpl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sblm.dao.IRecaudacionAutovaluoArbitrioDAO;
import com.sblm.model.Arbitrio;
import com.sblm.model.Archivodocumento;
import com.sblm.model.Auditoria;
import com.sblm.model.Contrato;
import com.sblm.model.Detallearbitrio;
import com.sblm.model.Estadoauditoria;
import com.sblm.model.Eventoauditoria;
import com.sblm.model.Extensionarchivo;
import com.sblm.model.Inquilino;
import com.sblm.model.Modulo;
import com.sblm.model.Uso;
import com.sblm.model.Usuario;
import com.sblm.util.FuncionesHelper;

@Repository(value = "recaudacionautovaluoarbitrioDAO")
public class RecaudacionAutovaluoArbitrioDAO implements IRecaudacionAutovaluoArbitrioDAO,Serializable{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void grabarCabeceraArbitrio(Arbitrio arbitrio) {
		getSessionFactory().getCurrentSession().save(arbitrio);
		settingLog(4, 25);
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public List<Detallearbitrio> obtenerDetalleArbitrio(int idarbitrio) {
		Session session = getSessionFactory().openSession();
		List<List> list = null;
		list = session.createQuery("select new List(D.montopagado,D.periodoanio,D.periodo,D.fecharegistro) from Detallearbitrio D inner join D.arbitrio A where A.idarbitrio='"+idarbitrio+"'").list();
		
		List<Detallearbitrio> lista = new ArrayList<Detallearbitrio>();

		for(int i=0;i<list.size();i++){
			Detallearbitrio det= new Detallearbitrio();
			det.setMontopagado((Double) list.get(i).get(0));
			det.setPeriodoanio((String) list.get(i).get(1));
			det.setPeriodo((String) list.get(i).get(2));
			det.setFecharegistro((Date) list.get(i).get(3));
						
			lista.add(det);
		}
		
		return lista;
	}

	@Override
	public List<Arbitrio> listarArbitrioConsulta() {
		Session session = getSessionFactory().openSession();
		List<Arbitrio> list = null;
		list = session.createQuery("from Arbitrio").list();
		
		return list;
		
	}

	@Override
	public void grabarDetalleArbitrio(Detallearbitrio detalleArbitrio) {
		getSessionFactory().getCurrentSession().save(detalleArbitrio);
		settingLog(4, 29);
	}

	@Override
	public List<Uso> getListaUso() {
		Session session = getSessionFactory().openSession();
		return session.createQuery("from Uso").list();
	}

	@Override
	public List<Inquilino> getListaInquilino() {
		Session session = getSessionFactory().openSession();
		return session.createQuery("from Inquilino").list();
	}

	@Override
	public void grabarArchivosAdjuntos(Archivodocumento archivodocu) {
		getSessionFactory().getCurrentSession().save(archivodocu);
		
	}

	@Override
	public List<Archivodocumento> obtenerArchivosArbitrio(int idarbitrio) {
		Session session = getSessionFactory().openSession();
		List<List> list = null;
		list = session.createQuery("select new List(AD.idarchivodocumento,AD.ruta,E.rutaIcono) from Archivodocumento AD inner join AD.arbitrio A inner join AD.extensionarchivo E  where A.idarbitrio='"+idarbitrio+"'").list();
		
		List<Archivodocumento> lista = new ArrayList<Archivodocumento>();

		for(int i=0;i<list.size();i++){
			Archivodocumento ext= new Archivodocumento();
			ext.setRuta((String) list.get(i).get(1));
			ext.setIdarchivodocumento((Integer) list.get(i).get(0));
			
			Extensionarchivo ea= new Extensionarchivo();
			ea.setRutaIcono((String) list.get(i).get(2));
			
			ext.setExtensionarchivo(ea);
						
			lista.add(ext);
		}
		
		return lista;
	}
	@Transactional
	@Override
	public void actualizarCabeceraArbitrio(Arbitrio arbitrio) {
		
		String updateQuery=	"UPDATE ARBITRIO  SET  "+
													",[JRAVCALLE]='" +arbitrio.getJravcalle()+"'"+
													",[INTDTOSTAND]='" +arbitrio.getIntdtostand()+"'"+
													",[NRO] ='" +arbitrio.getNro()+"'"+
													",[MONEDA] ='" +arbitrio.getMoneda()+"'"+
													",[MONTO] ='" +arbitrio.getMonto()+"'"+
													",[FRECUENCIAPAGO] ='" +arbitrio.getFrecuenciapago()+"'"+
													",[CLAVE]='" +arbitrio.getClave()+"'"+
													",[IDUSUARIOCREADOR]='" +arbitrio.getIdusuariocreador()+"'"+
													" WHERE IDARBITRIO='"+arbitrio.getIdarbitrio()+"'";
		
		getSessionFactory().getCurrentSession().createSQLQuery(updateQuery).executeUpdate();
		settingLog(4, 27);
	}

	@Override
	public void eliminarArchivoDocumentoNoEncontrado(int idarchivodocumento) {
		Session session = getSessionFactory().openSession();
		String queryDelete="delete Archivodocumento where idarchivodocumento=:id";
		session.createQuery(queryDelete).setParameter("id",idarchivodocumento).executeUpdate();
	}

	@Override
	public Arbitrio getUltimaCabeceraGrabada() {
		Session session = getSessionFactory().openSession();
		int tamanio;
		
		Query q = session.createQuery("FROM Arbitrio");
		tamanio= q.list().size();
		
		Arbitrio A = new Arbitrio();
		
		q.setFirstResult(tamanio-1);
		q.setMaxResults(tamanio);
		
		A=(Arbitrio) q.list().get(0);
		
		return A;
	}

	@Override
	public void actualizarvalorNroArchivos(int size,int idcarta) {
		String updateQuery=	"UPDATE CARTA  SET"+
				"[NROARCHIVOS]=NROARCHIVOS+'" +size+"'"+
				" WHERE IDCARTA='"+idcarta+"'";

				getSessionFactory().getCurrentSession().createSQLQuery(updateQuery).executeUpdate();
	}
	
	public  void settingLog(int idestadoauditoria, int ideventoauditoria){
		String url=FuncionesHelper.getURL().toString();
		
		try {
			int index = url.indexOf("pages/");
			url=url.substring(index+6, url.length());
			url=url.substring(0, url.length()-4);
		} catch (Exception e) {
		}
	

	
		Auditoria Adt= new Auditoria();
		Usuario usr= new Usuario();
		usr.setIdusuario((Integer)(FuncionesHelper.getUsuario()));
		
//		Area area = new Area();
//		area.setIdare("10800000");
//		usr.setArea(area);
		
		Modulo mod= new Modulo();
		Session session = getSessionFactory().openSession();
		
		
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
		Adt.setModulo(mod);
		Adt.setEstadoauditoria(esa);
		Adt.setEventoauditoria(eva);
		Adt.setFecentrada( new Date());
		Adt.setNompantalla(url);
		Adt.setUrl(FuncionesHelper.getURL().toString());
		if(FuncionesHelper.getTerminal().toString().equals("0:0:0:0:0:0:0:1")){
			Adt.setIp("127.0.0.1");
		}else{
			Adt.setIp(FuncionesHelper.getTerminal().toString());
		}
		Adt.setEstado(true);
		Adt.setCodauditoria(0);
		try {
		getSessionFactory().getCurrentSession().save(Adt);
		
		} catch (Exception e) {
		e.printStackTrace();}
		
		}

	@Override
	public Double obtenerTotalActual(int idarbitrio) {
		Session session = getSessionFactory().openSession();
		Query q=session.createQuery("select A.total from Arbitrio A where A.idarbitrio='"+idarbitrio+"'");
		q.setFirstResult(0);
		q.setMaxResults(1);
		
		
		
		return (Double)q.list().get(0) ;
	}

	@Override
	public void actualizarSumaTotalDetalleArbitrio(int idarbitrio,Double totalActual) {
		String updateQuery=	"UPDATE ARBITRIO  SET     [TOTAL] ='"+totalActual+"' where IDARBITRIO='"+idarbitrio+"'";
		getSessionFactory().getCurrentSession().createSQLQuery(updateQuery).executeUpdate();
	}

	@Override
	public List<Contrato> listaContratos() {
		Session session = getSessionFactory().openSession();
		return session.createQuery("from Contrato").list();
	}




	
}

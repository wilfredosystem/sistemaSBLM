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
import com.sblm.dao.IRecaudacionAutovaluoCartaDAO;
import com.sblm.model.Arbitrio;
import com.sblm.model.Archivodocumento;
import com.sblm.model.Auditoria;
import com.sblm.model.Carta;
import com.sblm.model.Detallearbitrio;
import com.sblm.model.Estadoauditoria;
import com.sblm.model.Eventoauditoria;
import com.sblm.model.Extensionarchivo;
import com.sblm.model.Inquilino;
import com.sblm.model.Modulo;
import com.sblm.model.Uso;
import com.sblm.model.Usuario;
import com.sblm.util.FuncionesHelper;

@Repository(value = "recaudacionautovaluocartaDAO")
public class RecaudacionAutovaluoCartaDAO implements IRecaudacionAutovaluoCartaDAO,Serializable{

	@Autowired
	private SessionFactory sessionFactory;
	

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void grabarCabeceraArbitrio(Carta carta) {
		getSessionFactory().getCurrentSession().merge(carta);
		
		settingLog(4, 26);
	}

	@Override
	public List<Carta> listarArbitrioConsulta() {
		Session session = getSessionFactory().openSession();

		return session.createQuery("from Carta order by fechacreacion desc ").list();
	}

	@Override
	public Carta getUltimaCabeceraGrabada() {
		Session session = getSessionFactory().openSession();
		int tamanio;
		
		Query q = session.createQuery("FROM Carta");
		tamanio= q.list().size();
		
		Carta A = new Carta();
		
		q.setFirstResult(tamanio-1);
		q.setMaxResults(tamanio);
		
		A=(Carta) q.list().get(0);
		
		return A;
	}

	@Override
	public List<Archivodocumento> obtenerArchivosCarta(int idcarta) {
		Session session = getSessionFactory().openSession();
		List<List> list = null;
		list = session.createQuery("select new List(AD.idarchivodocumento,AD.ruta,E.rutaIcono) from Archivodocumento AD inner join AD.carta C inner join AD.extensionarchivo E  where C.idcarta='"+idcarta+"'").list();
		
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

	@Override
	public void actualizarCabeceraCarta(Carta carta) {
		String updateQuery=	"UPDATE CARTA  SET"+
				",[JRAVCALLE]='" +carta.getJravcalle()+"'"+
				",[INTDTOSTAND]='" +carta.getIntdtostand()+"'"+
				",[NRO] ='" +carta.getNro()+"'"+
				",[CLAVE]='" +carta.getClave()+"'"+
				",[IDUSUARIOCREADOR]='" +carta.getIdusuariocreador()+"'"+
				" WHERE IDCARTA='"+carta.getIdcarta()+"'";

			getSessionFactory().getCurrentSession().createSQLQuery(updateQuery).executeUpdate();
			
			settingLog(4, 28);	}

	
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
	
}

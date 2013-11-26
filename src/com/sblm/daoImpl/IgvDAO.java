package com.sblm.daoImpl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sblm.dao.IIgvDAO;
import com.sblm.model.Auditoria;
import com.sblm.model.Estadoauditoria;
import com.sblm.model.Eventoauditoria;
import com.sblm.model.Igv;
import com.sblm.model.Modulo;
import com.sblm.model.Uso;
import com.sblm.model.Usuario;
import com.sblm.util.FuncionesHelper;
@Repository(value = "igvDAO")
public class IgvDAO implements IIgvDAO,Serializable{


	private static final long serialVersionUID = 1L;

	@Autowired
	private SessionFactory sessionFactory;

	Session sesion = null;
	@Override
	public void registrarIgv(Igv igv) {
		getSessionFactory().getCurrentSession().merge(igv);
		 try {
			 settingLog(15);
			 } catch (Exception e) {
			 e.printStackTrace();
			 }
	}

	@Override
	public void actualizarIgv(Igv igv) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eliminarIgv(Igv igv) {
		try {
			getSessionFactory()
					.getCurrentSession()
					.createSQLQuery(
							"delete from Igv WHERE idigv='"
									+ igv.getIdigv() + "'")
					.executeUpdate();
		} catch (Exception e) {
			System.out.println("error en dao eliminar igv:::"
					+ e.getMessage());
		}
	}

	@Override
	public List<Igv> listarIgvs() {

Session session = getSessionFactory().openSession();
		
	    try{
	    	return session.createQuery("from Igv order by feccre desc").list();
	    }
	    catch(HibernateException e){
	    	System.out.println("error listarIgvs:::"+e.getMessage());
	    	throw e;
	    }
	    finally{
	    	session.close();
	    }
	}

	@Override
	public Igv obtenerUltimoIgv() {
		 return (Igv) getSessionFactory().openSession().createQuery(
					"select t from Igv t where idigv=( select max(idigv) from Igv)").uniqueResult();
	}

	@Override
	public Igv obtenerIgvPorId(int id) {
		Session session = getSessionFactory().openSession();
		return (Igv) session.load(Igv.class, id);
	}

	@Override
	public int obtenerNumeroRegistros() {
		 Long count = (Long) getSessionFactory().openSession()
					.createQuery("select count(*) from Igv").uniqueResult();
       return count.intValue();
	}
	public void settingLog( int ideventoauditoria) {
		String url = FuncionesHelper.getURL().toString();

		try {
			int index = url.indexOf("pages/");
			url = url.substring(index + 6, url.length());
			url = url.substring(0, url.length() - 4);
		} catch (Exception e) {
		}
		Auditoria Adt = new Auditoria();
		Usuario usr = new Usuario();
		usr.setIdusuario((Integer) (FuncionesHelper.getUsuario()));

		Modulo mod = new Modulo();
		Session session = getSessionFactory().openSession();

		Query numero = session
				.createSQLQuery("select  m.IDMODULO from PAGINAMODULO as pm inner join MODULO m on pm.IDMODULO= m.IDMODULO  inner join PAGINA as p on p.IDPAGINA=pm.IDPAGINA where P.NOMBREPAGINA='"
						+ url + "'");

		int var = (Integer) numero.uniqueResult();
		System.out.println("###########::::" + var);
		mod.setIdmodulo(var);
		Estadoauditoria esa = new Estadoauditoria();
		esa.setIdestadoauditoria(4);
		Eventoauditoria eva = new Eventoauditoria();
		eva.setIdeventoauditoria(ideventoauditoria);
		Adt.setUsuario(usr);
		Adt.setModulo(mod);
		Adt.setEstadoauditoria(esa);
		Adt.setEventoauditoria(eva);
		Adt.setFecentrada(new Date());
		Adt.setNompantalla(url);
		Adt.setUrl(FuncionesHelper.getURL().toString());
		if (FuncionesHelper.getTerminal().toString().equals("0:0:0:0:0:0:0:1")) {
			Adt.setIp("127.0.0.1");
		} else {
			Adt.setIp(FuncionesHelper.getTerminal().toString());
		}
		Adt.setEstado(true);
		Adt.setCodauditoria(0);
		try {
			getSessionFactory().getCurrentSession().save(Adt);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}

package com.sblm.daoImpl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sblm.dao.IPerfilDAO;
import com.sblm.model.Auditoria;
import com.sblm.model.Estadoauditoria;
import com.sblm.model.Eventoauditoria;
import com.sblm.model.Modulo;
import com.sblm.model.Perfil;
import com.sblm.model.Usuario;
import com.sblm.util.FuncionesHelper;
import com.sblm.util.PerfilModuloPermiso;

@Repository(value = "perfilDAO")
public class PerfilDAO implements IPerfilDAO, Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private SessionFactory sessionFactory;

	Session sesion = null;
	Transaction txt = null;

	@Override
	public void registrarPerfil(Perfil perfil) {
		System.out.println(":::xxx registro perfil DAO xxx:::");
		getSessionFactory().getCurrentSession().merge(perfil);
		 try {
		 //idestadoauditoria 0 auditoria / 1 notificacion
		 settingLog(6);
		 } catch (Exception e) {
		 e.printStackTrace();
		 }
	}

	@Override
	public void actualizarPerfil(Perfil perfil) {
		// TODO Auto-generated method stub

	}

	@Override
	public void eliminarPerfil(Perfil perfil) {
		try {
			getSessionFactory()
					.getCurrentSession()
					.createSQLQuery(
							"delete from Perfil WHERE idperfil='"
									+ perfil.getIdperfil() + "'")
					.executeUpdate();
		} catch (Exception e) {
			System.out.println("error en dao eliminar eliminarPerfil:::"
					+ e.getMessage());
		}
	}

	@Override
	public Perfil listarPerfilPorId(int id) {
		Session session = getSessionFactory().openSession();
		return (Perfil) session.load(Perfil.class, id);
	}

	@Override
	public List<Perfil> listarPerfiles() {
		Session session = getSessionFactory().openSession();

		try {
			return session.createQuery("from Perfil order by feccre desc")
					.list();
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
	public int obtenerUltimoIdPerfil() {

		Integer val = (Integer) getSessionFactory().openSession()
				.createQuery("  select max(p.idperfil) from Perfil p")
				.uniqueResult();
		// if(val == null){
		// System.out.println("ultimo perfil#####");
		// return 0;}
		// else
		return val;

	}

	@Override
	public List<PerfilModuloPermiso> listarPerfilesModulosPermisos() {
		Session session = getSessionFactory().openSession();

		try {
			return session.createQuery("from PerfilModuloPermiso").list();
		} catch (HibernateException e) {
			System.out.println("error:::" + e);
			throw e;
		} finally {
			session.close();
		}
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


	@Override
	public String obtenerUltimoPerfil() {
		return (String) getSessionFactory()
				.openSession()
				.createQuery(
						"select p.nombreperfil from Perfil p where p.idperfil=( select max(idperfil) from Perfil)")
				.uniqueResult();
	}

	@Override
	public Date obtenerFechaUltimoPerfil() {
		Date fecha = (Date) getSessionFactory()
				.openSession()
				.createQuery(
						"select p.feccre from Perfil p where p.idperfil=( select max(idperfil) from Perfil)")
				.uniqueResult();
		return fecha;
	}

	@Override
	public int obtenerNumeroPerfiles() {

		Long count = (Long) getSessionFactory().openSession()
				.createQuery("select count(*) from Perfil").uniqueResult();

		return count.intValue();

	}
}

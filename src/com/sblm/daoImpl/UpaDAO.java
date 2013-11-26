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

import com.sblm.dao.IUpaDAO;
import com.sblm.model.Auditoria;
import com.sblm.model.Estadoauditoria;
import com.sblm.model.Eventoauditoria;
import com.sblm.model.Inmueble;
import com.sblm.model.Materialpredominante;
import com.sblm.model.Mep;
import com.sblm.model.Modulo;
import com.sblm.model.Tipointerior;
import com.sblm.model.Tipotitularidad;
import com.sblm.model.Tipovia;
import com.sblm.model.Titularidad;
import com.sblm.model.Upa;
import com.sblm.model.Uso;
import com.sblm.model.Usuario;
import com.sblm.model.Valuacion;
import com.sblm.util.FuncionesHelper;

@Repository(value = "upaDAO")
public class UpaDAO  implements IUpaDAO, Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private SessionFactory sessionFactory;

	Session sesion = null;

	
	@Override
	public void registrarUpa(Upa upa) {
		getSessionFactory().getCurrentSession().merge(upa);
		System.out.println("reg. upa ok!!!");
//		try {
//			settingLog(22);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
	}

	@Override
	public void actualizarUpa(Upa upa) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eliminarUpa(Upa upa) {
		try {
			getSessionFactory()
					.getCurrentSession()
					.createSQLQuery(
							"delete from Upa WHERE idupa='"
									+ upa.getIdupa() + "'")
					.executeUpdate();
		} catch (Exception e) {
			System.out.println("error en dao eliminar upa:::"
					+ e.getMessage());
		}

	}

	@Override
	public List<Upa> listarUpas() {
		
		
		Session session = getSessionFactory().openSession();

		try {
			return session.createQuery("from Upa order by feccre desc").list();
		} catch (HibernateException e) {
			System.out.println("error listado Paginamodulo dao:::" + e);
			throw e;
		}
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
	public Upa validarRepetido(String numreg) {
		return (Upa) getSessionFactory()
				.openSession()
				.createQuery(
						"select t from Upa t where numregistrosbn='"+numreg+"'  ")
				.uniqueResult();
	}
	@Override
	public Upa obtenerUpaPorId(int id) {
		Session session = getSessionFactory().openSession();
		return (Upa) session.load(Upa.class, id);
	}
	@Override
	public Inmueble obtenerInmueblePorParametro(String valor) {
		Session session = getSessionFactory().openSession();

		try {
			return (Inmueble) session.createQuery("from Inmueble inmu where  inmu.numregistrosbn='"+valor+"' ")
					.uniqueResult();
		} catch (HibernateException e) {
			System.out.println("error  obtenerInmueblePorParametro:::" + e.getMessage());
			throw e;
		} finally {
			session.close();
		}
	}
	
	@Override
	public int obtenerNumeroRegistros() {
		Long count = (Long) getSessionFactory().openSession()
				.createQuery("select count(*) from Upa").uniqueResult();
		return count.intValue();
	}

	@Override
	public List<Tipovia> listarTipoVia() {
		Session session = getSessionFactory().openSession();

		try {
			return session.createQuery("from Tipovia order by descripcion ")
					.list();
		} catch (HibernateException e) {
			System.out.println("error listar Tipovia:::" + e.getMessage());
			throw e;
		} finally {
			session.close();
		}
	}

	
	@Override
	public List<Valuacion> listarValuacion() {
		Session session = getSessionFactory().openSession();

		try {
			return session.createQuery("from Valuacion order by descripcion ")
					.list();
		} catch (HibernateException e) {
			System.out.println("error listar valuacion:::" + e.getMessage());
			throw e;
		} finally {
			session.close();
		}
	}
	
	
	@Override
	public List<Uso> listarUso() {
		Session session = getSessionFactory().openSession();

		try {
			return session.createQuery("from Uso order by descripcion ")
					.list();
		} catch (HibernateException e) {
			System.out.println("error listar uso:::" + e.getMessage());
			throw e;
		} finally {
			session.close();
		}
	}
	@Override
	public List<Mep> listarMep() {
		Session session = getSessionFactory().openSession();

		try {
			return session.createQuery("from Mep order by descripcion ")
					.list();
		} catch (HibernateException e) {
			System.out.println("error listar mep:::" + e.getMessage());
			throw e;
		} finally {
			session.close();
		}
	}
	@Override
	public List<Titularidad> listarTitularidad() {
		Session session = getSessionFactory().openSession();

		try {
			return session.createQuery("from Titularidad order by nombre ")
					.list();
		} catch (HibernateException e) {
			System.out.println("error listar Tipovia:::" + e.getMessage());
			throw e;
		} finally {
			session.close();
		}
	}
	@Override
	public List<Tipotitularidad> listarTipoTitularidad() {
		Session session = getSessionFactory().openSession();

		try {
			return session.createQuery("from Tipotitularidad order by descripcion ")
					.list();
		} catch (HibernateException e) {
			System.out.println("error listar Tipovia:::" + e.getMessage());
			throw e;
		} finally {
			session.close();
		}
	}
	@Override
	public List<Materialpredominante> listarMaterialPredominante() {
		Session session = getSessionFactory().openSession();

		try {
			return session.createQuery("from Materialpredominante order by descripcion ")
					.list();
		} catch (HibernateException e) {
			System.out.println("error listar Materialpredominante:::" + e.getMessage());
			throw e;
		} finally {
			session.close();
		}
	}
	@Override
	public List<Tipointerior> listarTipoInterior() {
		Session session = getSessionFactory().openSession();

		try {
			return session.createQuery("from Tipointerior order by descripcion ")
					.list();
		} catch (HibernateException e) {
			System.out.println("error listar Materialpredominante:::" + e.getMessage());
			throw e;
		} finally {
			session.close();
		}
	}
	
	@Override
	public List<String> listaInmuebles() {
		Session session = getSessionFactory().openSession();

		try {
			return session.createQuery(" select  distinct numregistrosbn from Inmueble order by numregistrosbn ")
					.list();
		} catch (HibernateException e) {
			System.out.println("error listar listarDepartamentos:::" + e.getMessage());
			throw e;
		} finally {
			session.close();
		}
	}
	@Override
	public List<String> listarDepartamentos() {
		Session session = getSessionFactory().openSession();

		try {
			return session.createQuery(" select  distinct depa from Ubigeo order by depa ")
					.list();
		} catch (HibernateException e) {
			System.out.println("error listar listarDepartamentos:::" + e.getMessage());
			throw e;
		} finally {
			session.close();
		}
	}
	@Override
	public List<String> listarProvincias(String dpto) {
		Session session = getSessionFactory().openSession();

		try {
			return session.createQuery(" select  distinct prov from Ubigeo  where depa='"+dpto+"' order by prov ")
					.list();
		} catch (HibernateException e) {
			System.out.println("error listar listarProvincias:::" + e.getMessage());
			throw e;
		} finally {
			session.close();
		}
	}
	@Override
	public List<String> listarDistritos(String prov) {
		Session session = getSessionFactory().openSession();

		try {
			return session.createQuery(" select  distinct dist from Ubigeo  where prov='"+prov+"' order by dist ")
					.list();
		} catch (HibernateException e) {
			System.out.println("error listar listarProvincias:::" + e.getMessage());
			throw e;
		} finally {
			session.close();
		}
	}
	@Override
	public List<String> listarUbigeoPorDistrito( String prov,String dist) {
		Session session = getSessionFactory().openSession();

		try {
			return session.createQuery(" select  ubigeo from Ubigeo  where prov='"+prov+"' and dist='"+dist+"' ")
					.list();
		} catch (HibernateException e) {
			System.out.println("error listar listarUbigeoPorDistrito:::" + e.getMessage());
			throw e;
		} finally {
			session.close();
		}
	}
	@Override
	public List<String> listarUbigeos() {
		Session session = getSessionFactory().openSession();

		try {
			return session.createQuery(" select  ubigeo from Ubigeo order by ubigeo")
					.list();
		} catch (HibernateException e) {
			System.out.println("error listar listarUbigeos:::" + e.getMessage());
			throw e;
		} finally {
			session.close();
		}
	}
	@Override
	public List<String> listarDptoPorUbigeo(String ubigeo) {
		Session session = getSessionFactory().openSession();

		try {
			return session.createQuery(" select  depa from Ubigeo where ubigeo='"+ubigeo+"' ")
					.list();
		} catch (HibernateException e) {
			System.out.println("error listar listarUbigeos:::" + e.getMessage());
			throw e;
		} finally {
			session.close();
		}
	}
	@Override
	public List<String> listarProvinciaPorUbigeo(String ubigeo) {
		Session session = getSessionFactory().openSession();

		try {
			return session.createQuery(" select  prov from Ubigeo where ubigeo='"+ubigeo+"' ")
					.list();
		} catch (HibernateException e) {
			System.out.println("error listar listarUbigeos:::" + e.getMessage());
			throw e;
		} finally {
			session.close();
		}
	}
	@Override
	public List<String> listarDistritoPorUbigeo(String ubigeo) {
		Session session = getSessionFactory().openSession();

		try {
			return session.createQuery(" select  dist from Ubigeo where ubigeo='"+ubigeo+"' ")
					.list();
		} catch (HibernateException e) {
			System.out.println("error listar listarUbigeos:::" + e.getMessage());
			throw e;
		} finally {
			session.close();
		}
	}
	public void settingLog(int ideventoauditoria) {
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

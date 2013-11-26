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

import com.sblm.dao.ICarteraDAO;
import com.sblm.dao.IContratoDAO;
import com.sblm.model.Auditoria;
import com.sblm.model.Cartera;
import com.sblm.model.Contrato;
import com.sblm.model.Cuota;
import com.sblm.model.Detallecartera;
import com.sblm.model.Detallecuota;
import com.sblm.model.Estadoauditoria;
import com.sblm.model.Eventoauditoria;
import com.sblm.model.Inquilino;
import com.sblm.model.Modulo;
import com.sblm.model.Uso;
import com.sblm.model.Usuario;
import com.sblm.util.FuncionesHelper;

@Repository(value = "carteraDAO")
public class CarteraDAO implements ICarteraDAO, Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private SessionFactory sessionFactory;

	Session sesion = null;
	@Override
	public void registrarCartera(Cartera cartera) {
		getSessionFactory().getCurrentSession().merge(cartera);
//		try {
//			settingLog(24);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
	@Override
	public void registrarCuota(Cuota cuota) {
		getSessionFactory().getCurrentSession().merge(cuota);

	}
	@Override
	public void registrarDetalleCuota(Detallecuota detallecuota) {
		getSessionFactory().getCurrentSession().merge(detallecuota);

	}
	
	@Override
	public void registrarDetalleCartera(Detallecartera detallecartera) {
		getSessionFactory().getCurrentSession().merge(detallecartera);

	}
	
	@Override
	public void actualizarCartera(Cartera cartera) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eliminarCartera(Cartera cartera) {
		try {
			getSessionFactory()
					.getCurrentSession()
					.createSQLQuery(
							"delete from Cartera WHERE idcartera='"
									+ cartera.getIdcartera() + "'")
					.executeUpdate();
		} catch (Exception e) {
			System.out.println("error en dao eliminar cartera:::"
					+ e.getMessage());
		}
		
	}
	@Override
	public void eliminarCarteraDetalle(Detallecartera detallecartera) {
		try {
			getSessionFactory()
					.getCurrentSession()
					.createSQLQuery(
							"delete from Detallecartera WHERE iddetallecartera='"
									+ detallecartera.getIddetallecartera() + "'")
					.executeUpdate();
		} catch (Exception e) {
			System.out.println("error en dao eliminar eliminarCarteraDetalle:::"
					+ e.getMessage());
		}
		
	}
	
	@Override
	public List<Cartera> listarCarteras() {
		Session session = getSessionFactory().openSession();

		try {
			return session.createQuery("from Cartera order by feccre desc").list();
		} catch (HibernateException e) {
			System.out.println("error listado Cartera dao:::" + e);
			throw e;
		}
	}
	@Override
	public List<Usuario> listarUsuarios() {
		Session session = getSessionFactory().openSession();

		try {
			return session.createQuery("from Usuario order by nombrescompletos").list();
		} catch (HibernateException e) {
			System.out.println("error listado usuariox dao:::" + e);
			throw e;
		}
	}
	@Override
	public List<String> listarNombresUsuarios() {
		Session session = getSessionFactory().openSession();

		try {
			return session.createQuery("select u.nombrescompletos from Usuario u order by nombrescompletos").list();
		} catch (HibernateException e) {
			System.out.println("error listado listarNombresUsuarios dao:::" + e);
			throw e;
		}
	}
	
	@Override
	public List<Detallecartera> listarDetalleCarteras() {
		Session session = getSessionFactory().openSession();

		try {
			return session.createQuery("from Detallecartera order by feccre desc").list();
		} catch (HibernateException e) {
			System.out.println("error listado listarDetalleCarteras dao:::" + e);
			throw e;
		}
	}
	@Override
	public List<Detallecartera> listarDetalleCarterasPorIdCartera(int idcartera) {
		Session session = getSessionFactory().openSession();

		try {
			return session.createQuery("from Detallecartera  where idcartera="+idcartera+" order by feccre desc").list();
		} catch (HibernateException e) {
			System.out.println("error listado listarDetalleCarteras dao:::" + e);
			throw e;
		}
	}
	
	
	@Override
	public List<Contrato> listarContratos() {
		Session session = getSessionFactory().openSession();

		try {
			return session.createQuery("from Contrato ").list();
		} catch (HibernateException e) {
			System.out.println("error listado listarContratos dao:::" + e);
			throw e;
		}
	}
	@Override
	public List<Detallecuota> listarDetallecuotas() {
		Session session = getSessionFactory().openSession();

		try {
			return session.createQuery("from Detallecuota ").list();
		} catch (HibernateException e) {
			System.out.println("error listado listarDetallecuotas dao:::" + e);
			throw e;
		}
	}
	@Override
	public List<Detallecuota> listardetallecuotasxcontrato(int idcontrato) {
		Session session = getSessionFactory().openSession();

		try {
			return session.createQuery("select dc from Detallecuota dc   join dc.cuota c join c.contrato con where con.idcontrato="+idcontrato+" ").list();
		} catch (HibernateException e) {
			System.out.println("error listado listarDetallecuotas dao:::" + e);
			throw e;
		}
	}
	@Override
	public List<Detallecuota> listardetallecuotasxcontratoycuota(int idcontrato,int idcuota) {
		Session session = getSessionFactory().openSession();

		try {
			return session.createQuery("select dc from Detallecuota dc   join dc.cuota c join c.contrato con where con.idcontrato="+idcontrato+" and c.idcuota="+idcuota+"  ").list();
		} catch (HibernateException e) {
			System.out.println("error listado listardetallecuotasxcontratoycuota dao:::" + e);
			throw e;
		}
	}
	
	@Override
	public List<Cuota> listarcuotasxcontrato(int idcontrato) {
		Session session = getSessionFactory().openSession();

		try {
			return session.createQuery("select c from Cuota c join c.contrato con where con.idcontrato="+idcontrato+" ").list();
		} catch (HibernateException e) {
			System.out.println("error listado listarcuotasxcontrato dao:::" + e);
			throw e;
		}
	}
	@Override
	public List<Cuota> listartodascuotasxcartera(int idcartera) {
		Session session = getSessionFactory().openSession();

		try {
			return session.createQuery("select c from Cuota c join c.contrato con where c.cartera.idcartera="+idcartera+" ").list();
		} catch (HibernateException e) {
			System.out.println("error listado listartodascuotasxcontrato dao:::" + e);
			throw e;
		}
	}
	
	
	@Override
	public List<Contrato> listarContratosdisponibles() {
		Session session = getSessionFactory().openSession();

		try {
			return session.createQuery("select c from Detallecartera dc  right join dc.contrato c  where dc.contrato.idcontrato is null").list();
		} catch (HibernateException e) {
			System.out.println("error listado listarContratosdisponibles dao:::" + e);
			throw e;
		}
	}
	@Override
	public List<Contrato> listarContratosxcartera(int idcartera) {
		Session session = getSessionFactory().openSession();

		try {System.out.println("consultaxxx");
			return session.createQuery("select c from Detallecartera dc   join dc.contrato c  where dc.cartera.idcartera="+idcartera+" ").list();
		} catch (HibernateException e) {
			System.out.println("error listado listarContratosdisponibles dao:::" + e);
			throw e;
		}
	}
	
	@Override
	public List<Contrato>  listarContratosdecartera(int idcartera, int idcontrato) {
		
		
		Session session = getSessionFactory().openSession();

		try {System.out.println("cambio x reporte 06112013");
			return session.createQuery("select c from Detallecartera dc   join dc.contrato c  where dc.cartera.idcartera="+idcartera+" and  c.idcontrato= "+idcontrato+" ").list();
		//return session.createQuery("select con from Detallecuota dc join dc.cuota cu join cu.contrato con join con.detallecarteras dcar join dcar.cartera car where car.idcartera="+idcartera+" and con.idcontrato="+idcontrato+"    ").list();
	//	return session.createQuery("select c from Detallecartera dc join   cu.contrato c where dc.cartera.idcartera="+idcartera+" and  c.idcontrato= "+idcontrato+"    ").list();
		
		} catch (HibernateException e) {
			System.out.println("error listado listarContratosdecartera dao:::" + e);
			throw e;
		}
	}
	@Override
	public List<Detallecuota>  listardetallecuotaxcontrato(int idcartera, int idcontrato) {
		
		
		Session session = getSessionFactory().openSession();

		try {
			return session.createQuery("select dc from Detallecuota dc join dc.cuota cu join cu.contrato con join con.detallecarteras dcar join dcar.cartera car where car.idcartera="+idcartera+" and con.idcontrato="+idcontrato+"   ").list();
		} catch (HibernateException e) {
			System.out.println("error listado listardetallecuotaxcontrato dao:::" + e);
			throw e;
		}
	}
	
	@Override
	public List<Uso> listarUsos() { 
		Session session = getSessionFactory().openSession();

		try {
			return session.createQuery("from Uso ").list();
		} catch (HibernateException e) {
			System.out.println("error listado listarUsos dao:::" + e);
			throw e;
		}
	}
	@Override
	public List<Cuota> listarCuotas() { 
		Session session = getSessionFactory().openSession();

		try {
			return session.createQuery("from Cuota ").list();
		} catch (HibernateException e) {
			System.out.println("error listado listarCuotas dao:::" + e);
			throw e;
		}
	}
	@Override
	public List<Cuota> listarCuotasPorIdCartera(int idcartera) { 
		Session session = getSessionFactory().openSession();

		try {
			System.out.println("listarCuotasPorIdCarteraxxxxxxxx");
			return session.createQuery("from Cuota where idcartera='"+idcartera+"' ").list();
		} catch (HibernateException e) {
			System.out.println("error listado listarCuotasPorIdCartera dao:::" + e);
			throw e;
		}
	}
	@Override
	public List<Detallecartera> listarDetallecarteraPorIdCartera(int idcartera) { 
		Session session = getSessionFactory().openSession();

		try {
			System.out.println("listarDetallecarteraPorIdCartera");
			return session.createQuery("from Detallecartera where idcartera='"+idcartera+"' ").list();
		} catch (HibernateException e) {
			System.out.println("error listado listarCuotasPorIdCartera dao:::" + e);
			throw e;
		}
	}
	@Override
	public List<Detallecartera> listarDetalleCarterasPorNroContrato(String nrocontrato) { 
		Session session = getSessionFactory().openSession();

		try {
			System.out.println("listarDetalleCarterasPorNroContrato");
			return session.createQuery("from Detallecartera detcar where detcar.contrato.nrocontrato='"+nrocontrato+"' ").list();
		} catch (HibernateException e) {
			System.out.println("error listado listarDetalleCarterasPorNroContrato dao:::" + e);
			throw e;
		}
	}
	
	
	@Override
	public Cartera obtenerUltimoCartera() {
		return (Cartera) getSessionFactory()
				.openSession()
				.createQuery(
						"select t from Cartera t where idcartera=( select max(idcartera) from Cartera)")
				.uniqueResult();
	}
	@Override
	public Cuota obtenerUltimoCuota() {
		return (Cuota) getSessionFactory()
				.openSession()
				.createQuery(
						"select t from Cuota t where idcuota=( select max(idcuota) from Cuota)")
				.uniqueResult();
	}
	@Override
	public Detallecuota obtenerUltimoDetalleCuota() {
		return (Detallecuota) getSessionFactory()
				.openSession()
				.createQuery(
						"select t from Detallecuota t where iddetallecuota=( select max(iddetallecuota) from Detallecuota)")
				.uniqueResult();
	}
	@Override
	public double obtenerMontoAcumuladoDetallecuota(int idcuota) {
		
		try {
			System.out.println("listarDetalleCarterasPorNroContrato");
			Double valor= (Double) getSessionFactory().openSession().createQuery("select sum(dc.montosoles) from Detallecuota dc where dc.cuota.idcuota= "+idcuota+" ").uniqueResult();
			if(valor==null){
				valor=0.0;
			}
			
			return valor;
		} catch (HibernateException e) {
			System.out.println("error listado obtenerMontoAcumuladoDetallecuota dao:::" + e.getMessage());
			throw e;
		}
	}
	@Override
	public double obtenerMontoAcumuladoDetallecuotadolar(int idcuota) {
		
		try {
			System.out.println("obtenerMontoAcumuladoDetallecuotadolar");
			Double valor= (Double) getSessionFactory().openSession().createQuery("select sum(dc.montodolar) from Detallecuota dc where dc.cuota.idcuota= "+idcuota+" ").uniqueResult();
			if(valor==null){
				valor=0.0;
			}
			
			return valor;
		} catch (HibernateException e) {
			System.out.println("error listado obtenerMontoAcumuladoDetallecuota dao:::" + e.getMessage());
			throw e;
		}
	}
	
	@Override
	public Cartera obtenerCarteraPorId(int id) {
		Session session = getSessionFactory().openSession();
		return (Cartera) session.load(Cartera.class, id);
	}
	@Override
	public Cuota obtenerCuotaPorId(int id) {
		Session session = getSessionFactory().openSession();
		return (Cuota) session.load(Cuota.class, id);
	}
	
	@Override
	public Contrato obtenerContratoPorId(int id) {
		Session session = getSessionFactory().openSession();
		return (Contrato) session.load(Contrato.class, id);
	}
	
	@Override
	public int obtenerNumeroRegistros() {
		Long count = (Long) getSessionFactory().openSession()
				.createQuery("select count(*) from Cartera").uniqueResult();
		return count.intValue();
	}
	@Override
	public double obtenerMontocuotaspagadas(int idcontrato) {
		
		
		try {
			System.out.println("obtenerMontocuotaspagadas");
			return (Double) getSessionFactory().openSession()
					.createQuery("select sum(c.montoacumuladosoles) from Cuota c where c.contrato.idcontrato="+idcontrato+" ").uniqueResult();
			
		} catch (HibernateException e) {
			System.out.println("error listado obtenerMontocuotaspagadas dao:::" + e);
			throw e;
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

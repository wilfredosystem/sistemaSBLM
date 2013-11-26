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

import com.sblm.dao.IContratoDAO;
import com.sblm.model.Area;
import com.sblm.model.Auditoria;
import com.sblm.model.Cliente;
import com.sblm.model.Contrato;
import com.sblm.model.Cuentabancaria;
import com.sblm.model.Estadoauditoria;
import com.sblm.model.Eventoauditoria;
import com.sblm.model.Inmueble;
import com.sblm.model.Inquilino;
import com.sblm.model.Institucion;
import com.sblm.model.Modulo;
import com.sblm.model.Representante;
import com.sblm.model.Tipocambio;
import com.sblm.model.Upa;
import com.sblm.model.Uso;
import com.sblm.model.Usuario;
import com.sblm.util.FuncionesHelper;

@Repository(value = "contratoDAO")
public class ContratoDAO implements IContratoDAO, Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private SessionFactory sessionFactory;

	Session sesion = null;

	@Override
	public void registrarContrato(Contrato contrato) {
		getSessionFactory().getCurrentSession().merge(contrato);
		try {
			settingLog(23);
		} catch (Exception e) {
			e.printStackTrace();
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

	@Override
	public List<Uso> getListaUsos() {
		Session session = getSessionFactory().openSession();
		return session.createQuery("from Uso ").list();
	}

	
	@Override
	public List<Inmueble> getListaInmueble() {
		Session session = getSessionFactory().openSession();
		return session.createQuery("from Inmueble ").list();
	}

	@Override
	public List<Upa> getListaUpa() {
		Session session = getSessionFactory().openSession();
		List<List> list = null;
		list = session.createQuery("select new List(U,I,O) from Upa U inner join U.inmueble I inner join U.uso O").list();
		
		List<Upa> lista = new ArrayList<Upa>();

		for(int i=0;i<list.size();i++){
			Upa upa= new Upa();
			Inmueble inmueble= new Inmueble();
			Uso uso= new Uso();
			
			upa=(Upa)list.get(i).get(0);
			inmueble=(Inmueble)list.get(i).get(1);
			uso= (Uso)list.get(i).get(2);
			
			
			upa.setInmueble(inmueble);
			upa.setUso(uso);
			lista.add(upa);
		}
		
		return lista;
		
	}

	@Override
	public List<Inquilino> getListaInquilino() {
		Session session = getSessionFactory().openSession();

			return session.createQuery("from Inquilino").list();
	}

	@Override
	public List<Upa> buscarUpasXInmueble(int idinmueble) {
		Session session = getSessionFactory().openSession();
		List<List> list = null;
		list = session.createQuery("select new List(U) from Upa U inner join U.inmueble I where I.idinmueble='"+idinmueble+"'").list();
		List<Upa> lista = new ArrayList<Upa>();
		for(int i=0;i<list.size();i++){
			Upa upa= new Upa();
			upa=(Upa)list.get(i).get(0);
			lista.add(upa);
		}
		
		return lista;
		
	}

	@Override
	public List<Contrato> getListaContrato() {
		Session session = getSessionFactory().openSession();
		return session.createQuery("from Contrato ").list();
	}

	@Override
	public List<Institucion> getListaInstitucion() {
		Session session = getSessionFactory().openSession();
		return session.createQuery("from Institucion ").list();
	}


	@Override
	public List<Representante> getListaRepresentante() {
		Session session = getSessionFactory().openSession();
		return session.createQuery("from Representante ").list();
	}


	@Override
	public void cancelarContrato(int idcontratoGlobal) {
		String updateQuery="UPDATE CONTRATO SET ESTADO='CANCELADO' WHERE IDCONTRATO='"+idcontratoGlobal+"'";
		
		getSessionFactory().getCurrentSession().createSQLQuery(updateQuery).executeUpdate();
	}


	@Override
	public List<Cliente> getListaCliente() {
		Session session = getSessionFactory().openSession();
		return session.createQuery("from Cliente ").list();
	}


	@Override
	public List<Usuario> obtenerUsuarios() {
		Session session = sessionFactory.openSession();
		List<List> list = null;
		
		
		list = session.createQuery("select new List(U.idusuario,U.rutaimgusr,U.cargo,A.desare,U.nombrescompletos,U.emailusr) from Usuario U inner join U.area A").list();
		List<Usuario> lista = new ArrayList<Usuario>();
		
		
		for(int i=0;i<list.size();i++){
			
			Area area= new Area();
			
			area.setDesare((String) list.get(i).get(3));
			
			Usuario usuario= new Usuario();
			usuario.setRutaimgusr((String) list.get(i).get(1));
			usuario.setCargo((String) list.get(i).get(2));
			usuario.setIdusuario((Integer) list.get(i).get(0));
			usuario.setNombrescompletos((String)list.get(i).get(4));
			usuario.setEmailusr((String)list.get(i).get(5));
			
			usuario.setArea(area);
			
			
			lista.add(usuario);
		}
		
		
		return lista;
	}


	@Override
	public void enviarNotificaciónPersonalizable(
			String contenidoMensajePersonalizado, int idusuariodestino) {
		try {
			settingLog(1, 30, idusuariodestino,contenidoMensajePersonalizado);
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

				Session session = getSessionFactory().openSession();
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
					int var=(Integer) modulo.list().get(0);
					mod.setIdmodulo(var);
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
			getSessionFactory().getCurrentSession().save(Adt);
		
		} catch (Exception e) {
		e.printStackTrace();
		}
	
	}


@Override
public Double getValorTipoCambio() {
	Session session = getSessionFactory().openSession();
	return  (Double) session.createQuery("select t.tipocambio from Tipocambio t where idtipocambio=( select max(idtipocambio) from Tipocambio)").uniqueResult();
}


@Override
public List<Cuentabancaria> getCtasBancarias() {
	Session session = getSessionFactory().openSession();
	return session.createQuery("from Cuentabancaria ").list();
}
	

	
	
}

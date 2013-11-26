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

import com.sblm.dao.IUsuarioDAO;
import com.sblm.model.Area;
import com.sblm.model.Auditoria;
import com.sblm.model.Estadoauditoria;
import com.sblm.model.Eventoauditoria;
import com.sblm.model.Modulo;
import com.sblm.model.Perfilmodulo;
import com.sblm.model.Perfilusuario;
import com.sblm.model.Usuario;
import com.sblm.util.FuncionesHelper;

@Repository(value = "usuarioDAO") 
public class UsuarioDAO implements IUsuarioDAO, Serializable {
	private static final long serialVersionUID = -7132329520845816103L;

	@Autowired
	private SessionFactory sessionFactory;

	Session sesion = null;
	Transaction txt = null;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Override
	public void crearUsuario(Usuario usuario) {
		getSessionFactory().getCurrentSession().save(usuario);
	}

	@Override
	public void actualizarUsuario(Usuario usuario) {
		getSessionFactory().getCurrentSession().update(usuario);
	}

	@Override
	public void eliminarUsuario(Usuario usuario) {
		getSessionFactory().getCurrentSession().delete(usuario);
	}

	@Override
	public Usuario listarUsuarioPorId(int id) {
		Session session = getSessionFactory().openSession();
		return (Usuario) session.load(Usuario.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Usuario> listarUsuarios() {
		Session session = getSessionFactory().openSession();
		return session.createQuery("from Usuario").list();
	}

	@Override
	public List<Usuario> listarUsuarios(int iduser) {
		Session session = getSessionFactory().openSession();
		return session.createQuery("from Usuario").list();
	//	return session.createQuery("from Usuario where idusuario != "+iduser+" ").list();
	}

	@Override
	public String obtenerPass(String mail) {
		Session session = getSessionFactory().openSession();
		Query query = session
				.createQuery("SELECT u.contrasenausr FROM Usuario u where u.emailusr = :email");
		query.setParameter("email", mail);
		String password = (String) query.uniqueResult();

		return password;
	}

	public Usuario buscarUsuario(Usuario usuario) {
		Session session = getSessionFactory().openSession();
		try {
			Query query = session
					.createQuery("select u from Usuario u where nombreusr = '"+usuario.getNombreusr()+"' and contrasenausr = '"+usuario.getContrasenausr()+"' ");
//			query.setParameter("login", usuario.getNombreusr());
//			query.setParameter("password", usuario.getContrasenausr());
			
		
			return (Usuario) query.uniqueResult();

		} catch (HibernateException e) {
			System.out.println("error:::" + e);
			throw e;
		} 
//		finally {
//			session.close();
//		}

	}
	
	@Override
	public void grabarLogueo() {
	
			
			try {
				
				settingLog(4,7,0);
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

	public void cambiarContrasenha(int id, String password) {

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Perfilmodulo> obtenerEstadoModulo(int idperfil) {

		Session session = getSessionFactory().openSession();
		return session.createQuery(
				"from Perfilmodulo  where perfil.idperfil=" + idperfil + "")
				.list();

	}

	@Override
	public int obtenerIdPerfil(Usuario usuario) {
		Session session = getSessionFactory().openSession();
		try {
			Query query = session
					.createSQLQuery("Select pu.idperfil from Perfilusuario pu  where pu.idusuario="
							+ usuario.getIdusuario() + " and activo='true'");// verificar

			return Integer.parseInt(query.uniqueResult().toString());
		} catch (HibernateException e) {
			System.out.println("error:::" + e);
			throw e;
		} finally {
			session.close();
		}
	}

	@Override
	public List<String> loguear(Usuario usuario) {
		// System.out.println("paso logueo dao::::::::::::::");
		// return getSessionFactory()
		// .openSession()
		// .createQuery(
		// " select nombreusr from PERFILUSUARIO pu inner join USUARIO u on pu.IDUSUARIO=u.IDUSUARIO where CONTRASENAUSR = "+usuario.getContrasenausr()
		// +" and NOMBREUSR='"+usuario.getNombreusr()+"' and ACTIVO=1")
		// .list();

		// Session session = getSessionFactory().openSession();
		// Query query =
		// session.createQuery("from PERFILUSUARIO pu inner join USUARIO u on pu.IDUSUARIO=u.IDUSUARIO  where NOMBREUSR= = :usuario and CONTRASENAUSR = :pass and ACTIVO=1");
		// query.setParameter("usuario", usuario.getIdusuario());
		// query.setParameter("pass", usuario.getIdusuario());
		//
		// return query.list();

		// return(List<String>) getSessionFactory().openSession().createQuery(
		// " select * from PERFILUSUARIO pu inner join USUARIO u on pu.IDUSUARIO=u.IDUSUARIO  where CONTRASENAUSR = "+usuario.getContrasenausr()
		// +" and NOMBREUSR='"+usuario.getNombreusr()+"' and ACTIVO=1").list();

		return (List<String>) getSessionFactory()
				.getCurrentSession()
				.createSQLQuery(
						" select u.idusuario,u.nombreusr,u.contrasenausr from PERFILUSUARIO as pu inner join USUARIO as u on pu.IDUSUARIO=u.IDUSUARIO  where u.CONTRASENAUSR = '"
								+ usuario.getContrasenausr()
								+ "' and u.NOMBREUSR='"
								+ usuario.getNombreusr() + "' and pu.ACTIVO=1")
				.list();
		// query.setParameter("login", usuario.getNombreusr());
		// query.setParameter("password", usuario.getContrasenausr());
		// "select nombreusr from Perfilusuario pu inner join Usuario u on pu.idusuario=u.idusuario  where u.nombreusr= :login and u.contrasenausr= :password"
		// return (List<String>) query.list();
	}

	@Override
	public Perfilmodulo obtenerEstadoModulo(int idperfil, int idmodulo) {
		Session session = getSessionFactory().openSession();
		return (Perfilmodulo) session.createQuery(
				"from Perfilmodulo  where perfil.idperfil=" + idperfil
						+ " and idmodulo=" + idmodulo + " ").uniqueResult();

	}
	
	@Override
	public Usuario buscarUsuarioxId(int parseInt) {
		Session session = getSessionFactory().openSession();
		
		try {
			Query query = session
					.createQuery("select u from Usuario u where u.idusuario = '"+parseInt+"'");
		
			return (Usuario) query.uniqueResult();

		} catch (HibernateException e) {
			System.out.println("error:::" + e);
			throw e;
		} 
	}


		
	
	public  void settingLog(int idestadoauditoria, int ideventoauditoria, int idusuariodestino){
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
		
		Query numero=session.createSQLQuery("select  m.IDMODULO from PAGINAMODULO as pm inner join MODULO m on pm.IDMODULO= m.IDMODULO  inner join PAGINA as p on p.IDPAGINA=pm.IDPAGINA where P.NOMBREPAGINA='"+url+"'");
		
		int var=(Integer) numero.list().get(0);
		
		mod.setIdmodulo(var);
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
	public String obtenerNombrePerfilSeleccionado(Usuario u) {
		Session session = getSessionFactory().openSession();
		try {
			Query query = session
					.createSQLQuery("Select p.nombreperfil from Perfilusuario pu  INNER JOIN Usuario u ON pu.idusuario="
							+ u.getIdusuario() + " inner join Perfil p on p.idperfil=pu.idperfil and activo='true'");// verificar
			
			query.setMaxResults(1);


			return String.valueOf(query.uniqueResult());
		} catch (HibernateException e) {
			System.out.println("error:::" + e);
			throw e;
		} finally {
			session.close();
		}
	}



}

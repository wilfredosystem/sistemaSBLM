package com.sblm.daoImpl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sblm.dao.IPerfilModuloDAO;
import com.sblm.model.Auditoria;
import com.sblm.model.Estadoauditoria;
import com.sblm.model.Eventoauditoria;
import com.sblm.model.Modulo;
import com.sblm.model.Perfil;
import com.sblm.model.Perfilmodulo;
import com.sblm.model.Perfilusuario;
import com.sblm.model.Usuario;

@Repository(value = "perfilmoduloDAO")
public class PerfilModuloDAO implements IPerfilModuloDAO,Serializable{

	private static final long serialVersionUID = 1L;

	@Autowired
	private SessionFactory sessionFactory;
	
	
	@Override
	public void registrarPerfilModulo(Perfilmodulo perfilmodulo) {
		System.out.println(":::registro perfil modulo DAO:::");
		
		getSessionFactory().getCurrentSession().merge(perfilmodulo);
		
	}
	


	@Override
	public void actualizarPerfilModulo(Perfilmodulo perfilmodulo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eliminarPerfilModulo(Perfilmodulo perfilmodulo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Perfil listarPerfilModuloPorId(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Perfilmodulo> listarPerfilmodulos() {
		
		Session session = getSessionFactory().openSession();
		
	    try{
	    	return session.createQuery("from Perfilmodulo").list();
	    }
	    catch(HibernateException e){
	    	System.out.println("error:::"+e);
	    	throw e;
	    }
	    finally{
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
	public List<Perfilmodulo> listarPerfilModuloPorIdPerfil(int idperfil) {
		System.out.println("paso###listarPerfilModuloPorIdPerfil###::idperfil:::whr::::::"+idperfil);
		Session session = getSessionFactory().openSession();
		return session.createQuery("from Perfilmodulo  where perfil.idperfil="+idperfil+" ").list();
	}

	@Override
	public void eliminarPerfilModuloId(int idperfil) {
		try {System.out.println("#############paso a dao eliminarPerfilModuloId");
		getSessionFactory().getCurrentSession().createSQLQuery("delete from PERFILMODULO WHERE idperfil='"+idperfil+"'").executeUpdate();
		} catch (Exception e) {
			System.out.println("error en dao:::"+e.getMessage());
		}
		
	}
	
}

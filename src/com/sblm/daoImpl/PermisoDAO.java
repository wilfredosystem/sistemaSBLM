package com.sblm.daoImpl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sblm.dao.IPermisoDAO;
import com.sblm.model.Auditoria;
import com.sblm.model.Estadoauditoria;
import com.sblm.model.Eventoauditoria;
import com.sblm.model.Modulo;
import com.sblm.model.Perfil;
import com.sblm.model.Permiso;
import com.sblm.model.Usuario;

@Repository(value = "permisoDAO")
public class PermisoDAO implements IPermisoDAO,Serializable{

	private static final long serialVersionUID = 1L;
	@Autowired
	private SessionFactory sessionFactory;
	
	
	@Override
	public void registrarPermiso(Permiso permiso) {
		getSessionFactory().getCurrentSession().save(permiso);
		
	}

	@Override
	public void actualizarPermiso(Permiso permiso) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eliminarPermiso(Permiso permiso) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Permiso listarPermisoPorId(int id) {
		Session session=getSessionFactory().openSession();
        return (Permiso) session.load(Permiso.class, id);
	}

	@Override
	public List<Permiso> listarPermisos() {
		Session session = getSessionFactory().openSession();
		
	    try{
	    	return session.createQuery("from Permiso").list();
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
	
	public  void settingLog(int idusuario, int idmodulo, int idestadoauditoria, int ideventoauditoria, Date fechaentrada, String nombrepantalla,
			String url,Boolean estado, int codauditoria){


Auditoria Adt= new Auditoria();
Usuario usr= new Usuario();
usr.setIdusuario(idusuario);
Modulo mod= new Modulo();
mod.setIdmodulo(idmodulo);
Estadoauditoria esa= new Estadoauditoria();
esa.setIdestadoauditoria(idestadoauditoria);
Eventoauditoria eva= new Eventoauditoria();
eva.setIdeventoauditoria(ideventoauditoria);
Adt.setUsuario(usr);
Adt.setModulo(mod);
Adt.setEstadoauditoria(esa);
Adt.setEventoauditoria(eva);
Adt.setFecentrada(fechaentrada);
Adt.setNompantalla(nombrepantalla);
Adt.setUrl(url);
Adt.setEstado(estado);
Adt.setCodauditoria(codauditoria);
try {
getSessionFactory().getCurrentSession().save(Adt);

} catch (Exception e) {
e.printStackTrace();		}



}

}

package com.sblm.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sblm.dao.IPermisoDAO;
import com.sblm.model.Permiso;
import com.sblm.service.IPermisoService;

@Transactional(readOnly = true)
@Service(value="permisoService")
public class PermisoService implements IPermisoService{

	@Autowired
	private IPermisoDAO permisoDAO;

	
	@Transactional(readOnly = false)
	@Override
	public void registrarPermiso(Permiso permiso) {
		getPermisoDAO().registrarPermiso(permiso);
		
	}
	@Transactional(readOnly = false)
	@Override
	public void actualizarPermiso(Permiso permiso) {
		// TODO Auto-generated method stub
		
	}
	@Transactional(readOnly = false)
	@Override
	public void eliminarPermiso(Permiso permiso) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Permiso listarPermisoPorId(int id) {
		return getPermisoDAO().listarPermisoPorId(id);
	}

	@Override
	public List<Permiso> listarPermisos() {
		return getPermisoDAO().listarPermisos();
	}
	public IPermisoDAO getPermisoDAO() {
		return permisoDAO;
	}
	public void setPermisoDAO(IPermisoDAO permisoDAO) {
		this.permisoDAO = permisoDAO;
	}
}

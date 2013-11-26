package com.sblm.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sblm.dao.IPerfilUsuarioDAO;
import com.sblm.model.Perfilusuario;
import com.sblm.service.IPerfilUsuarioService;
@Transactional(readOnly = true)
@Service(value="perfilusuarioService")
public class PerfilUsuarioServices  implements IPerfilUsuarioService{

	@Autowired
	private IPerfilUsuarioDAO perfilusuarioDAO;
	
	@Transactional(readOnly = false)
	
	@Override
	public void registrarPerfilUsuario(Perfilusuario perfilusuario) {
		// TODO Auto-generated method stub
		
	}
	@Transactional(readOnly = false)
	@Override
	public void actualizarPerfilUsuario(Perfilusuario perfilusuario) {
		getPerfilusuarioDAO().actualizarPerfilUsuario(perfilusuario);
		
	}
	@Transactional(readOnly = false)
	@Override
	public void eliminarPerfilUsuario(Perfilusuario perfilusuario) {
		getPerfilusuarioDAO().eliminarPerfilUsuario(perfilusuario);
		
	}

	@Override
	public Perfilusuario listarPerfilUsuarioPorId(int id) {
		return getPerfilusuarioDAO().listarPerfilUsuarioPorId(id);
	}

	@Override
	public List<Perfilusuario> listarPerfilUsuarios() {
		return getPerfilusuarioDAO().listarPerfilUsuarios();
	}
	public IPerfilUsuarioDAO getPerfilusuarioDAO() {
		return perfilusuarioDAO;
	}
	public void setPerfilusuarioDAO(IPerfilUsuarioDAO perfilusuarioDAO) {
		this.perfilusuarioDAO = perfilusuarioDAO;
	}
	@Override
	public List<Perfilusuario> listarPerfilUsuariosPorId(int id) {
		return getPerfilusuarioDAO().listarPerfilUsuariosPorId(id);
	}

}

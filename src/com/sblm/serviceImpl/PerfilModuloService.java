package com.sblm.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sblm.dao.IPerfilModuloDAO;
import com.sblm.model.Perfil;
import com.sblm.model.Perfilmodulo;
import com.sblm.service.IPerfilModuloService;

@Transactional(readOnly = true)
@Service(value="perfilmoduloService")
public class PerfilModuloService  implements IPerfilModuloService{

	@Autowired
	private IPerfilModuloDAO perfilmoduloDAO;
	
	
	@Transactional(readOnly = false)
	@Override
	public void registrarPerfilModulo(Perfilmodulo perfilmodulo) {
		getPerfilmoduloDAO().registrarPerfilModulo(perfilmodulo);
		 
	}
	@Transactional(readOnly = false)
	@Override
	public void actualizarPerfilModulo(Perfilmodulo perfilmodulo) {
		// TODO Auto-generated method stub
		
	}
	@Transactional(readOnly = false)
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
		return getPerfilmoduloDAO().listarPerfilmodulos();
	}

	public IPerfilModuloDAO getPerfilmoduloDAO() {
		return perfilmoduloDAO;
	}

	public void setPerfilmoduloDAO(IPerfilModuloDAO perfilmoduloDAO) {
		this.perfilmoduloDAO = perfilmoduloDAO;
	}
	@Override
	public List<Perfilmodulo> listarPerfilModuloPorIdPerfil(int idperfil) {
		return getPerfilmoduloDAO().listarPerfilModuloPorIdPerfil(idperfil);
	}
	@Override
	public void eliminarPerfilModuloId(int idperfil) {
		getPerfilmoduloDAO().eliminarPerfilModuloId(idperfil);
	}

}

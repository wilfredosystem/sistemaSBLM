package com.sblm.serviceImpl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sblm.dao.IPerfilDAO;
import com.sblm.dao.ITipoCambioDAO;
import com.sblm.model.Perfil;
import com.sblm.service.IPerfilService;
import com.sblm.util.PerfilModuloPermiso;

@Transactional(readOnly = true)
@Service(value="perfilService")
public class PerfilService implements IPerfilService{

	@Autowired
	private IPerfilDAO perfilDAO;
	
	
	@Transactional(readOnly = false)
	@Override
	public void registrarPerfil(Perfil perfil) {
		getPerfilDAO().registrarPerfil(perfil);
		
	}
	@Transactional(readOnly = false)
	@Override
	public void actualizarPerfil(Perfil perfil) {
		// TODO Auto-generated method stub
		
	}
	@Transactional(readOnly = false)
	@Override
	public void eliminarPerfil(Perfil perfil) {
		getPerfilDAO().eliminarPerfil(perfil);
		
	}

	@Override
	public Perfil listarPerfilPorId(int id) {
		return getPerfilDAO().listarPerfilPorId(id);
	}

	@Override
	public List<Perfil> listarPerfiles() {
		return getPerfilDAO().listarPerfiles();
	}
	@Override
	public List<PerfilModuloPermiso> listarPerfilesModulosPermisos() {
		return getPerfilDAO().listarPerfilesModulosPermisos();
	}
	

	public IPerfilDAO getPerfilDAO() {
		return perfilDAO;
	}

	public void setPerfilDAO(IPerfilDAO perfilDAO) {
		this.perfilDAO = perfilDAO;
	}
	@Override
	public int obtenerUltimoIdPerfil() {
		return getPerfilDAO().obtenerUltimoIdPerfil();
	}
	@Override
	public int obtenerNumeroPerfiles() {
		return getPerfilDAO().obtenerNumeroPerfiles();
	}
	@Override
	public String obtenerUltimoPerfil() {
		return getPerfilDAO().obtenerUltimoPerfil();
	}
	@Override
	public Date obtenerFechaUltimoPerfil() {
		return getPerfilDAO().obtenerFechaUltimoPerfil();
	}

}

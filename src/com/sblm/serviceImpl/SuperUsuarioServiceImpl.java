package com.sblm.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sblm.dao.ISuperUsuarioDAO;
import com.sblm.model.Area;
import com.sblm.model.Perfil;
import com.sblm.model.Perfilusuario;
import com.sblm.model.Usuario;
import com.sblm.service.ISuperUsuarioService;

@Transactional(readOnly = true)
@Service(value="panelSuperUsuarioServiceImpl")
public class SuperUsuarioServiceImpl implements ISuperUsuarioService{

	@Autowired
	private ISuperUsuarioDAO superusuarioDAO;

	@Autowired
	private ISuperUsuarioDAO usuarioDAO;

	
	@Override
	public List<Usuario> listarUsuarios() {
		return superusuarioDAO.listarUsuarios();
	}


	@Override
	public List getUsuarioEdit(String selectIdRegistroUsuario) {
		// TODO Auto-generated method stub
		return superusuarioDAO.getUsuarioEdit(selectIdRegistroUsuario);	
		}


	@Override
	public void save(Perfilusuario perfilusuario) {
		superusuarioDAO.save(perfilusuario);			
	}


	@Override
	public void nuevoUsuario(Usuario usr) {
		superusuarioDAO.nuevoUsuario(usr);			
		
	}


	@Override
	public List<Perfil> listPerfilbyNom() {
		return superusuarioDAO.listPerfilbyNom();	
	}


	@Override
	public Object getLastIdUsuario() {
		
		return superusuarioDAO.getLastIdUsuario();	
	}


	@Override
	public List<Usuario> getUsuarioEditSinPerfil(String selectIdRegistroUsuario) {

		return superusuarioDAO.getUsuarioEditSinPerfil(selectIdRegistroUsuario);
	}


	@Override
	public List getUsuarioEditConPerfil(String selectIdRegistroUsuario) {
		// TODO Auto-generated method stub
		return superusuarioDAO.getUsuarioEditConPerfil(selectIdRegistroUsuario);
	}


	@Override
	public void eliminarUsuario(String selectIdRegistroUsuario) {
		superusuarioDAO.eliminarUsuario(selectIdRegistroUsuario);
		
	}


	@Override
	public String existenciaUsuario() {
		return null;
	}


	@Override
	public void actualizarUsuario(Usuario u, String fechaUpdate) {
		usuarioDAO.actualizarUsuario(u,fechaUpdate);
		
	}
	
	@Override
	public void eliminarAntiguoPerfilUsuario(int idusuario) {
		usuarioDAO.eliminarAntiguoPerfilUsuarios(idusuario);		
	}


	public ISuperUsuarioDAO getSuperusuarioDAO() {
		return superusuarioDAO;
	}


	public void setSuperusuarioDAO(ISuperUsuarioDAO superusuarioDAO) {
		this.superusuarioDAO = superusuarioDAO;
	}


	public ISuperUsuarioDAO getUsuarioDAO() {
		return usuarioDAO;
	}


	public void setUsuarioDAO(ISuperUsuarioDAO usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}


	@Override
	public List<Area> getAllArea() {
		return superusuarioDAO.getAllArea();	
	}


	
}

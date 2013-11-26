package com.sblm.service;

import java.util.List;

import com.sblm.model.Area;
import com.sblm.model.Perfil;
import com.sblm.model.Perfilusuario;
import com.sblm.model.Usuario;

public interface ISuperUsuarioService {

	public List<Usuario> listarUsuarios();

	public  List getUsuarioEdit(String selectIdRegistroUsuario);

	public void save(Perfilusuario perfilusuario);

	public void nuevoUsuario(Usuario usr);

	public  List<Perfil> listPerfilbyNom();

	public  Object getLastIdUsuario();

	public List<Usuario> getUsuarioEditSinPerfil(String selectIdRegistroUsuario);

	public List getUsuarioEditConPerfil(String selectIdRegistroUsuario);

	public void eliminarUsuario(String selectIdRegistroUsuario);

	public String existenciaUsuario();

	public void actualizarUsuario(Usuario u, String fechaUpdate);

	void eliminarAntiguoPerfilUsuario(int idusuario);

	public List<Area> getAllArea();

	



}

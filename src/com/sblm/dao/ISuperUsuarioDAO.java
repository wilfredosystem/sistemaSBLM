package com.sblm.dao;

import java.util.List;

import com.sblm.model.Area;
import com.sblm.model.Perfil;
import com.sblm.model.Perfilusuario;
import com.sblm.model.Usuario;

public interface ISuperUsuarioDAO {

	public List<Usuario> listarUsuarios();

	public List getUsuarioEdit(String selectIdRegistroUsuario);

	public void save(Perfilusuario perfilusuario);

	public void nuevoUsuario(Usuario usr);

	public List<Perfil> listPerfilbyNom();

	public Object getLastIdUsuario();

	public List<Usuario> getUsuarioEditSinPerfil(String s);

	public List getUsuarioEditConPerfil(String selectIdRegistroUsuario);

	public void eliminarUsuario(String selectIdRegistroUsuario);

	public void actualizarUsuario(Usuario u, String fechaUpdate);

	public void eliminarAntiguoPerfilUsuarios(int idusuario);

	public void actualizarUsuario(String nombreUsuario,String nombreRegistro, String pat,
			String mat, String cargoRegistro, String emailRegistro,
			String passRegistro, String formatearFechaString, String ruta);

	public List<Area> getAllArea();



}

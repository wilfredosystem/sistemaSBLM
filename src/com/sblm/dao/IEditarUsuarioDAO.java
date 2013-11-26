package com.sblm.dao;

import java.util.List;

import com.sblm.model.Usuario;

public interface IEditarUsuarioDAO {

	List<Usuario> getUsuarioEditSinPerfil(String selectIdRegistroUsuario);

	void actualizarUsuario(String nombreUsuario, String nombreRegistro,
			String pat, String mat, String cargoRegistro, String emailRegistro,
			String passRegistro, String formatearFechaString, String ruta);

}

package com.sblm.dao;

import java.util.List;

import com.sblm.model.Perfilusuario;

public interface IPerfilUsuarioDAO {

	public void registrarPerfilUsuario(Perfilusuario perfilusuario);

	public void actualizarPerfilUsuario(Perfilusuario perfilusuario);

	public void eliminarPerfilUsuario(Perfilusuario perfilusuario);

	public Perfilusuario listarPerfilUsuarioPorId(int id);

	public List<Perfilusuario> listarPerfilUsuarios();
	
	public List<Perfilusuario> listarPerfilUsuariosPorId(int id);
}

package com.sblm.dao;

import java.util.List;

import com.sblm.model.Perfil;
import com.sblm.model.Perfilmodulo;

public interface IPerfilModuloDAO {

	public void registrarPerfilModulo(Perfilmodulo perfilmodulo);

	public void actualizarPerfilModulo(Perfilmodulo perfilmodulo);

	public void eliminarPerfilModulo(Perfilmodulo perfilmodulo);

	public Perfil listarPerfilModuloPorId(int id);

	public List<Perfilmodulo> listarPerfilmodulos();
	
	public List<Perfilmodulo> listarPerfilModuloPorIdPerfil (int idperfil);
	
	public void eliminarPerfilModuloId(int idperfil);
}

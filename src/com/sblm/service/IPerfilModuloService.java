package com.sblm.service;

import java.util.List;

import com.sblm.model.Perfil;
import com.sblm.model.Perfilmodulo;

public interface IPerfilModuloService {

	public void registrarPerfilModulo(Perfilmodulo perfilmodulo);

	public void actualizarPerfilModulo(Perfilmodulo perfilmodulo);

	public void eliminarPerfilModulo(Perfilmodulo perfilmodulo);

	public Perfil listarPerfilModuloPorId(int id);

	public List<Perfilmodulo> listarPerfilmodulos();
	
	//
	public void eliminarPerfilModuloId(int idperfil);
	
	public List<Perfilmodulo> listarPerfilModuloPorIdPerfil (int idperfil);
}

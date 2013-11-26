package com.sblm.service;

import java.util.List;

import com.sblm.model.Perfilmodulo;
import com.sblm.model.Usuario;


public interface IUsuarioService {

	public void crearUsuario(Usuario usuario);

	public void actualizarUsuario(Usuario usuario);

	public void eliminarUsuario(Usuario usuario);

	public Usuario listarUsuarioPorId(int id);

	public List<Usuario> listarUsuarios();
	
	public String obtenerContrasenha(String mail);
	//login
	
	public  Usuario buscarUsuario(Usuario usuario);
	public void cambiarContrasenha(int id, String pass);
	
	public int obtenerIdPerfil(Usuario usuario);
	public List<String> loguear(Usuario usuario);
	
	public List<Perfilmodulo> obtenerEstadoModulo(int idperfil);
	
	public Perfilmodulo obtenerEstadoModulo(int idperfil , int idmodulo);

	public void grabarLogueo();
	
	public List<Usuario> listarUsuarios(int iduser);

	public Usuario buscarUsuarioxId(int parseInt);

	public String obtenerNombrePerfilSeleccionado(Usuario u);
	
}

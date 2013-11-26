package com.sblm.service;

import java.util.Date;
import java.util.List;

import com.sblm.model.Modulo;
import com.sblm.model.Pagina;
import com.sblm.model.Paginamodulo;
import com.sblm.model.Perfil;
import com.sblm.model.Perfilmodulo;

public interface IModuloService {
	public void registrarModulo(Modulo modulo);

	public void actualizarModulo(Modulo modulo);

	public void eliminarModulo(Modulo modulo);

	public Modulo listarModuloPorId(int id);

	public List<Modulo> listarModulos();
	
	public int obtenerNumeroModulos();
	public String obtenerUltimoModulo();
	public Date obtenerFechaUltimoModulo();

	Modulo obtenerUltimoModulocreado();

	void registrarPaginamodulo(Paginamodulo paginamodulo);


	List<Pagina> listarPaginasDeModulos(int idmodulo);

	List<Pagina> listarPaginas();

	void eliminarPaginaModulo(int idmodulo);

	public List<Perfilmodulo> verficarModuloEnPerfil(int idmodulo);

	public Perfil  listarPerfilPorId(int idperfil);
	

}

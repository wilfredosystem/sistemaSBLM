package com.sblm.serviceImpl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sblm.dao.IModuloDAO;
import com.sblm.model.Modulo;
import com.sblm.model.Pagina;
import com.sblm.model.Paginamodulo;
import com.sblm.model.Perfil;
import com.sblm.model.Perfilmodulo;
import com.sblm.service.IModuloService;

@Transactional(readOnly = true)
@Service(value="moduloService")
public class ModuloService implements IModuloService {

	@Autowired
	private IModuloDAO moduloDAO;
	
	@Transactional(readOnly = false)
	@Override
	public void registrarModulo(Modulo modulo) {
		getModuloDAO().registrarModulo(modulo);
		
	}

	@Transactional(readOnly = false)
	@Override
	public void actualizarModulo(Modulo modulo) {
		// TODO Auto-generated method stub
		
	}

	@Transactional(readOnly = false)
	@Override
	public void eliminarModulo(Modulo modulo) {
		getModuloDAO().eliminarModulo(modulo);
		
	}
	
	@Transactional(readOnly = false)
	@Override
	public void eliminarPaginaModulo(int idmodulo) {
		getModuloDAO().eliminarPaginaModulo(idmodulo);
		
	}
	@Override
	public Perfil  listarPerfilPorId(int idperfil) {
		return getModuloDAO().listarPerfilPorId(idperfil);
		
	}
	
	
	@Override
	public List<Perfilmodulo> verficarModuloEnPerfil(int idmodulo) {
		return	getModuloDAO().verficarModuloEnPerfil(idmodulo);
		
	}
	
	@Override
	public Modulo listarModuloPorId(int id) {
		return getModuloDAO().listarModuloPorId(id);
	}

	@Override
	public List<Modulo> listarModulos() {
		return getModuloDAO().listarModulos();
	}

	public IModuloDAO getModuloDAO() {
		return moduloDAO;
	}

	public void setModuloDAO(IModuloDAO moduloDAO) {
		this.moduloDAO = moduloDAO;
	}

	@Override
	public int obtenerNumeroModulos() {
		return getModuloDAO().obtenerNumeroModulos();
		
	}

	@Override
	public String obtenerUltimoModulo() {
		return getModuloDAO().obtenerUltimoModulo();
	}
	@Override
	public Date obtenerFechaUltimoModulo() {
		return getModuloDAO().obtenerFechaUltimoModulo();
	}
	
	@Override
	public Modulo obtenerUltimoModulocreado() {
		return getModuloDAO().obtenerUltimoModulocreado();
	}
	@Override
	public void registrarPaginamodulo(Paginamodulo paginamodulo) {
		getModuloDAO().registrarPaginamodulo(paginamodulo);
		
	}
	@Override
	public List<Pagina> listarPaginasDeModulos(int idmodulo) {
		return getModuloDAO().listarPaginasDeModulos(idmodulo);
	}
	@Override
	public List<Pagina> listarPaginas() {
		return getModuloDAO().listarPaginas();
	}
	
}

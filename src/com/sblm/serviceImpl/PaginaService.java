package com.sblm.serviceImpl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sblm.dao.IPaginaDAO;
import com.sblm.model.Pagina;
import com.sblm.model.Paginamodulo;
import com.sblm.service.IPaginaService;
@Transactional(readOnly = true)
@Service(value="paginaService")

public class PaginaService implements IPaginaService{

	@Autowired
	private IPaginaDAO paginaDAO;
	
	@Transactional(readOnly = false)
	@Override
	public void registrarPagina(Pagina pagina) {
		getPaginaDAO().registrarPagina(pagina);
		
	}
	@Transactional(readOnly = false)
	@Override
	public void actualizarPagina(Pagina pagina) {
		// TODO Auto-generated method stub
		
	}
	@Transactional(readOnly = false)
	@Override
	public void eliminarPagina(Pagina pagina) {
		getPaginaDAO().eliminarPagina(pagina);
		
	}



	@Override
	public List<Pagina> listarPaginas() {
		return getPaginaDAO().listarPaginas();
	}

	@Override
	public int obtenerNumeroPaginas() {
		return getPaginaDAO().obtenerNumeroPaginas();
	}

	@Override
	public String obtenerUltimaPagina() {
		return getPaginaDAO().obtenerUltimaPagina();
	}
	@Override
	public Pagina verificarPaginaEnModulo(String descripcionpagina,String nombrepagina, int idmodulo,int idpagina) {
		return getPaginaDAO().verificarPaginaEnModulo(descripcionpagina,nombrepagina,idmodulo,idpagina);
	}
	public IPaginaDAO getPaginaDAO() {
		return paginaDAO;
	} 

	public void setPaginaDAO(IPaginaDAO paginaDAO) {
		this.paginaDAO = paginaDAO;
	}
	@Override
	public List<Paginamodulo> listarPaginasModulos() {
		return getPaginaDAO().listarPaginasModulos();
	}
	@Override
	public Date obtenerFechaUltimaPagina() {
		return getPaginaDAO().obtenerFechaUltimaPagina();
	}
	@Override
	public void registrarPaginamodulo(Paginamodulo paginamodulo) {
		 getPaginaDAO().registrarPaginamodulo(paginamodulo);
		
	}
	@Override
	public void actualizarPaginamodulo(Paginamodulo paginamodulo) {
		 getPaginaDAO().actualizarPaginamodulo(paginamodulo);
		
	}
	@Override
	public void eliminarPaginamodulo(Paginamodulo paginamodulo) {
		getPaginaDAO().eliminarPaginamodulo(paginamodulo);
		
	}
	@Override
	public Pagina obtenerUltimaPaginaCreada() {
		return getPaginaDAO().obtenerUltimaPaginaCreada();
	}
	@Override
	public List<Paginamodulo> listarPaginamodulos() {
		return getPaginaDAO().listarPaginamodulos();
	}

}

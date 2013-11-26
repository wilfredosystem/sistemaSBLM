package com.sblm.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sblm.daoImpl.IPertenenciaDAO;
import com.sblm.model.Pertenencia;
import com.sblm.service.IPertenenciaService;
@Transactional(readOnly = true)
@Service(value="pertenenciaService")
public class PertenenciaService implements IPertenenciaService{
	@Autowired
	private IPertenenciaDAO pertenenciaDAO;
	
	@Transactional(readOnly = false)
	
	@Override
	public void registrarPertenencia(Pertenencia pertenencia) {
		getPertenenciaDAO().registrarPertenencia(pertenencia);
		
	}
	@Transactional(readOnly = false)
	@Override
	public void actualizarUso(Pertenencia pertenencia) {
		getPertenenciaDAO().actualizarUso(pertenencia);
		
	}
	@Transactional(readOnly = false)
	@Override
	public void eliminarPertenencia(Pertenencia pertenencia) {
		getPertenenciaDAO().eliminarUso(pertenencia);
		
	}

	@Override
	public List<Pertenencia> listarPertenencias() {
		// TODO Auto-generated method stub
		return getPertenenciaDAO().listarPertenencias();
	}

	@Override
	public Pertenencia obtenerUltimoPertenencia() {
		// TODO Auto-generated method stub
		return getPertenenciaDAO().obtenerUltimoPertenencia();
	}

	@Override
	public Pertenencia obtenerPertenenciaPorId(int id) {
		// TODO Auto-generated method stub
		return getPertenenciaDAO().obtenerPertenenciaPorId(id);
	}

	@Override
	public int obtenerNumeroRegistros() {
		// TODO Auto-generated method stub
		return getPertenenciaDAO().obtenerNumeroRegistros();
	}
	public IPertenenciaDAO getPertenenciaDAO() {
		return pertenenciaDAO;
	}
	public void setPertenenciaDAO(IPertenenciaDAO pertenenciaDAO) {
		this.pertenenciaDAO = pertenenciaDAO;
	}

}

package com.sblm.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sblm.dao.IRamoDAO;
import com.sblm.model.Ramo;
import com.sblm.service.IRamoService;

@Transactional(readOnly = true)
@Service(value="ramoService")
public class RamoService implements IRamoService {
	@Autowired
	private IRamoDAO ramoDAO;

	@Transactional(readOnly = false)
	@Override
	public void registrarRamo(Ramo ramo) {
		getRamoDAO().registrarRamo(ramo);
		
	}
	@Transactional(readOnly = false)
	@Override
	public void actualizarRamo(Ramo ramo) {
		getRamoDAO().actualizarRamo(ramo);
	}
	@Transactional(readOnly = false)
	@Override
	public void eliminarRamo(Ramo ramo) {
		getRamoDAO().eliminarRamo(ramo);
		
	}

	@Override
	public List<Ramo> listarRamos() {
		
		return getRamoDAO().listarRamos();
	}

	@Override
	public Ramo obtenerUltimoRamo() {
		// TODO Auto-generated method stub
		return getRamoDAO().obtenerUltimoRamo();
	}

	@Override
	public Ramo obtenerRamoPorId(int id) {
		// TODO Auto-generated method stub
		return getRamoDAO().obtenerRamoPorId(id);
	}

	@Override
	public int obtenerNumeroRegistros() {
		// TODO Auto-generated method stub
		return getRamoDAO().obtenerNumeroRegistros();
	}

	public IRamoDAO getRamoDAO() {
		return ramoDAO;
	}

	public void setRamoDAO(IRamoDAO ramoDAO) {
		this.ramoDAO = ramoDAO;
	}
	
}

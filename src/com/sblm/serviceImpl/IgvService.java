package com.sblm.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sblm.dao.IIgvDAO;
import com.sblm.dao.IUsoDAO;
import com.sblm.model.Igv;
import com.sblm.service.IIgvService;

@Transactional(readOnly = true)
@Service(value="igvService")
public class IgvService implements IIgvService {
	@Autowired
	private IIgvDAO igvDAO;
	
	@Transactional(readOnly = false)
	@Override
	public void registrarIgv(Igv igv) {
		getIgvDAO().registrarIgv(igv);
		
	}

	@Override
	public void actualizarIgv(Igv igv) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eliminarIgv(Igv igv) {
		 getIgvDAO().eliminarIgv(igv);
		
	}

	@Override
	public List<Igv> listarIgvs() {
		return getIgvDAO().listarIgvs();
	}

	@Override
	public Igv obtenerUltimoIgv() {
		return getIgvDAO().obtenerUltimoIgv();
	}

	@Override
	public Igv obtenerIgvPorId(int id) {
		return getIgvDAO().obtenerIgvPorId(id);
	}

	@Override
	public int obtenerNumeroRegistros() {
		return getIgvDAO().obtenerNumeroRegistros();
	}

	public IIgvDAO getIgvDAO() {
		return igvDAO;
	}

	public void setIgvDAO(IIgvDAO igvDAO) {
		this.igvDAO = igvDAO;
	}

}

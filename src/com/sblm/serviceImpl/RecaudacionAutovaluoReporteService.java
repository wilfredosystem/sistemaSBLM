package com.sblm.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sblm.dao.IRecaudacionAutovaluoReporteDAO;
import com.sblm.model.Contrato;
import com.sblm.model.Detallecartera;
import com.sblm.model.Ubigeo;
import com.sblm.model.Upa;
import com.sblm.service.IRecaudacionAutovaluoReporteService;

@Transactional(readOnly = true)
@Service(value="autovaluoReporteService")
public class RecaudacionAutovaluoReporteService implements IRecaudacionAutovaluoReporteService{
	@Autowired
	private IRecaudacionAutovaluoReporteDAO autovaluoReporteDAO;
	


	@Override
	public List<Upa> listarUpas() {
		return getAutovaluoReporteDAO().listarUpas(); 
	}
	@Override
	public List<Ubigeo> listarUbigeos() { 
		return getAutovaluoReporteDAO().listarUbigeos(); 
	}
	
	@Override
	public List<Upa> listarUpasXDistrito(String ubigeo) {
		return getAutovaluoReporteDAO().listarUpasXDistrito(ubigeo); 
	}
	
	@Override
	public List<Upa> listarUpasXDistritosLima() {
		return getAutovaluoReporteDAO().listarUpasXDistritosLima(); 
	}
	@Override
	public List<Upa> listarUpasXInmueble() {
		return getAutovaluoReporteDAO().listarUpasXInmueble(); 
	}
	@Override 
	public List<Detallecartera> listarDetallescarteras() {
		return getAutovaluoReporteDAO().listarDetallescarteras(); 
	}
	
	@Override
	public Contrato obtenerContratoXUpa(int  idupa) {
		return getAutovaluoReporteDAO().obtenerContratoXUpa(idupa); 
	}
	@Override
	public Double obtenerMontoPorAnho(int  idcontrato, String anho) {
		return getAutovaluoReporteDAO().obtenerMontoPorAnho( idcontrato,  anho); 
	}
	
	public IRecaudacionAutovaluoReporteDAO getAutovaluoReporteDAO() {
		return autovaluoReporteDAO;
	}

	public void setAutovaluoReporteDAO(
			IRecaudacionAutovaluoReporteDAO autovaluoReporteDAO) {
		this.autovaluoReporteDAO = autovaluoReporteDAO;
	}

	

}

package com.sblm.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sblm.dao.IInmuebleDAO;
import com.sblm.dao.IInquilinoDAO;
import com.sblm.model.Califica;
import com.sblm.model.Inquilino;
import com.sblm.model.Tipoentidad;
import com.sblm.model.Tipopersona;
import com.sblm.service.IInquilinoService;
@Transactional(readOnly = true)
@Service(value="inquilinoService")
public class InquilinoService implements IInquilinoService{
	@Autowired
	private IInquilinoDAO inquilinoDAO;

	@Transactional(readOnly = false)
	@Override
	public void registrarInquilino(Inquilino inquilino) {
		getInquilinoDAO().registrarInquilino(inquilino);
		
	}
	@Transactional(readOnly = false)
	@Override
	public void actualizarInquilino(Inquilino inquilino) {
		getInquilinoDAO().actualizarInquilino(inquilino);
		
	}
	@Transactional(readOnly = false)
	@Override
	public void eliminarInquilino(Inquilino inquilino) {
		getInquilinoDAO().eliminarInquilino(inquilino);
		
	}
	
	@Override
	public List<Inquilino> listarInquilinos() {
		return getInquilinoDAO().listarInquilinos();
	}
	@Override
	public List<Califica> listarCalificacion(Inquilino inqui) {
		return getInquilinoDAO().listarCalificacion(inqui);
	}
	@Transactional(readOnly = false)
	@Override
	public void eliminarCalificacion(Inquilino inquilino) {
		getInquilinoDAO().eliminarCalificacion(inquilino);
		
	}
	@Override
	public Inquilino obtenerUltimoInquilino() {
		return getInquilinoDAO().obtenerUltimoInquilino();
	}
	@Override
	public Inquilino validarRepetido(String val) {
		return getInquilinoDAO().validarRepetido(val);
	}
	
	@Override
	public Inquilino obtenerInquilinoPorId(int id) {
		// TODO Auto-generated method stub
		return getInquilinoDAO().obtenerInquilinoPorId(id);
	}

	@Override
	public int obtenerNumeroRegistros() {
		// TODO Auto-generated method stub
		return getInquilinoDAO().obtenerNumeroRegistros();
	}

	@Override
	public List<Tipoentidad> listarTipoEntidad() {
		// TODO Auto-generated method stub
		return getInquilinoDAO().listarTipoEntidad();
	}
	@Override
	public List<Califica> listarCalificacion() {
		// TODO Auto-generated method stub
		return getInquilinoDAO().listarCalificacion();
	}
	
	@Override
	public List<Tipopersona> listarTipoPersona() {
		// TODO Auto-generated method stub
		return getInquilinoDAO().listarTipoPersona();
	}
	
	@Override
	public void registrarCalificacion(Califica calificacion) {
		getInquilinoDAO().registrarCalificacion(calificacion);
		
	}
	public IInquilinoDAO getInquilinoDAO() {
		return inquilinoDAO;
	}

	public void setInquilinoDAO(IInquilinoDAO inquilinoDAO) {
		this.inquilinoDAO = inquilinoDAO;
	}

}

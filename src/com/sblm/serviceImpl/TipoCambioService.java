package com.sblm.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sblm.dao.ITipoCambioDAO;
import com.sblm.model.Inmueble;
import com.sblm.model.Tipocambio;
import com.sblm.service.ITipoCambioService;

@Transactional(readOnly = true)
@Service(value="tipocambioService")
public class TipoCambioService implements ITipoCambioService{

	@Autowired
	private ITipoCambioDAO tipocambioDAO;
	
	public ITipoCambioDAO getTipocambioDAO() {
		return tipocambioDAO;
	}

	public void setTipocambioDAO(ITipoCambioDAO tipocambioDAO) {
		this.tipocambioDAO = tipocambioDAO;
	}

	@Transactional(readOnly = false)
	@Override
	public void registrarTipoCambio(Tipocambio tipoCambio) {
		getTipocambioDAO().registrarTipoCambio(tipoCambio);
		
	}

	public Tipocambio obtenerTipoCambio() {
		return getTipocambioDAO().obtenerTipoCambio();
	}

	@Override
	public String obtenerMes() {
		// TODO Auto-generated method stub
		return getTipocambioDAO().obtenerMes();
	}

	@Override
	public Tipocambio listarTipoCambioPorId(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Tipocambio> listarTipoCambios() {
		return getTipocambioDAO().listarTipoCambios();
	}
	
	@Override
	public Tipocambio obtenerUltimoTipocambio() {
		return getTipocambioDAO().obtenerUltimoTipocambio();
	}
}

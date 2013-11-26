package com.sblm.service;

import java.util.List;

import com.sblm.model.Tipocambio;

public interface ITipoCambioService {

	public void registrarTipoCambio(Tipocambio tipoCambio);

	public Tipocambio obtenerTipoCambio();

	public String obtenerMes();
	
	public Tipocambio listarTipoCambioPorId(int id);

	public List<Tipocambio> listarTipoCambios();

	Tipocambio obtenerUltimoTipocambio();
}

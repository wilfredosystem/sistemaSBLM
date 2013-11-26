package com.sblm.dao;

import java.util.List;

import com.sblm.model.Tipocambio;

public interface ITipoCambioDAO {


	public Tipocambio obtenerTipoCambio();
	public String obtenerMes();
	public Tipocambio listarTipoCambioPorId(int id);

	public List<Tipocambio> listarTipoCambios();
	public void registrarTipoCambio(Tipocambio tipoCambio);
	Tipocambio obtenerUltimoTipocambio();
}

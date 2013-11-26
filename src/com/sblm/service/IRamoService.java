package com.sblm.service;

import java.util.List;

import com.sblm.model.Ramo;

public interface IRamoService {
	public void registrarRamo(Ramo ramo);
	public void actualizarRamo(Ramo ramo);

	public void eliminarRamo(Ramo ramo);
	public List<Ramo> listarRamos();
	
	public Ramo obtenerUltimoRamo();

	public Ramo obtenerRamoPorId(int id);
	int obtenerNumeroRegistros();

}

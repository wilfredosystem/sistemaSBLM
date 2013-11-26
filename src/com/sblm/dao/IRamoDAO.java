package com.sblm.dao;

import java.util.List;

import com.sblm.model.Ramo;

public interface IRamoDAO {
	public void registrarRamo(Ramo ramo);
	public void actualizarRamo(Ramo ramo);

	public void eliminarRamo(Ramo ramo);
	public List<Ramo> listarRamos();
	
	public Ramo obtenerUltimoRamo();

	public Ramo obtenerRamoPorId(int id);
	int obtenerNumeroRegistros();

}

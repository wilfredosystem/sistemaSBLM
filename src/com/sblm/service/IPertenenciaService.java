package com.sblm.service;

import java.util.List;

import com.sblm.model.Pertenencia;

public interface IPertenenciaService {
	public void registrarPertenencia(Pertenencia pertenencia);
	public void actualizarUso(Pertenencia pertenencia);

	public void eliminarPertenencia(Pertenencia pertenencia);
	public List<Pertenencia> listarPertenencias();
	
	public Pertenencia obtenerUltimoPertenencia();

	public Pertenencia obtenerPertenenciaPorId(int id);
	int obtenerNumeroRegistros();
}

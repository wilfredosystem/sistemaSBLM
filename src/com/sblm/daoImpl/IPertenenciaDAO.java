package com.sblm.daoImpl;

import java.util.List;

import com.sblm.model.Pertenencia;

public interface IPertenenciaDAO {
	public void registrarPertenencia(Pertenencia pertenencia);
	public void actualizarUso(Pertenencia pertenencia);

	public void eliminarUso(Pertenencia pertenencia);
	public List<Pertenencia> listarPertenencias();
	
	public Pertenencia obtenerUltimoPertenencia();

	public Pertenencia obtenerPertenenciaPorId(int id);
	int obtenerNumeroRegistros();
}

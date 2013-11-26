package com.sblm.dao;

import java.util.List;

import com.sblm.model.Uso;

public interface IUsoDAO {
	public void registrarUso(Uso uso);
	public void actualizarUso(Uso uso);

	public void eliminarUso(Uso uso);
	public List<Uso> listarUsos();
	
	public Uso obtenerUltimoUso();

	public Uso obtenerUsoPorId(int id);
	int obtenerNumeroRegistros();
}

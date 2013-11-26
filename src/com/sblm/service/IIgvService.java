package com.sblm.service;

import java.util.List;

import com.sblm.model.Igv;

public interface IIgvService {
	public void registrarIgv(Igv igv);
	public void actualizarIgv(Igv igv);

	public void eliminarIgv(Igv igv);
	public List<Igv> listarIgvs();
	
	public Igv obtenerUltimoIgv();

	public Igv obtenerIgvPorId(int id);
	int obtenerNumeroRegistros();

}

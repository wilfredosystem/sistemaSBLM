package com.sblm.service;

import java.util.List;

import com.sblm.model.Representante;

public interface IRepresentanteService {

	public void grabarRepresentante(Representante representante);

	public List<Representante> obtenerTodosRepresentantes();

	public void eliminarRepresentante(Representante representante);

	public int nrorepresentantes();

}

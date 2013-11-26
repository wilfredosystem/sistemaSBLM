package com.sblm.dao;

import java.util.List;

import com.sblm.model.Representante;

public interface IRepresentanteServiceDAO {

	void grabarRepresentante(Representante representante);

	List<Representante> obtenerTodosRepresentantes();

	void eliminarRepresentante(Representante representante);

	int nrorepresentantes();

}

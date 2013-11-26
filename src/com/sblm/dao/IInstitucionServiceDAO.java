package com.sblm.dao;

import java.util.List;
import com.sblm.model.Institucion;

public interface IInstitucionServiceDAO {

	void grabarInstitucion(Institucion institucion);

	List<Institucion> obtenerTodosInstituciones();

	void eliminarInstitucion(Institucion institucion);

	int nroInstituciones();

}

package com.sblm.service;

import java.util.List;
import com.sblm.model.Institucion;

public interface IInstitucionService {

	public void grabarInstitucion(Institucion institucion);

	public List<Institucion> obtenerTodosInstituciones();

	public void eliminarInstitucion(Institucion institucion);

	public int nroInstituciones();

}

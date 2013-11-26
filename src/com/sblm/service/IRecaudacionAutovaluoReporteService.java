package com.sblm.service;

import java.util.List;

import com.sblm.model.Contrato;
import com.sblm.model.Detallecartera;
import com.sblm.model.Ubigeo;
import com.sblm.model.Upa;

public interface IRecaudacionAutovaluoReporteService {

	public List<Upa> listarUpas();

	List<Upa> listarUpasXDistrito(String ubigeo);

	List<Ubigeo> listarUbigeos();

	Contrato obtenerContratoXUpa(int idupa);

	Double obtenerMontoPorAnho(int idcontrato, String anho);

	List<Upa> listarUpasXDistritosLima();

	List<Upa> listarUpasXInmueble();

	List<Detallecartera> listarDetallescarteras();
}

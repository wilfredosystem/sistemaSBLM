package com.sblm.service;

import java.util.List;

import com.sblm.model.Cartera;
import com.sblm.model.Contrato;
import com.sblm.model.Cuota;
import com.sblm.model.Detallecartera;
import com.sblm.model.Detallecuota;
import com.sblm.model.Uso;
import com.sblm.model.Usuario;

public interface ICarteraService {
	public void registrarCartera(Cartera cartera);

	public void actualizarCartera(Cartera cartera);

	public void eliminarCartera(Cartera cartera);

	public List<Cartera> listarCarteras();

	public Cartera obtenerUltimoCartera();

	public Cartera obtenerCarteraPorId(int id);

	int obtenerNumeroRegistros();

	List<Usuario> listarUsuarios();

	void registrarDetalleCartera(Detallecartera detallecartera);

	List<Detallecartera> listarDetalleCarteras();

	List<Contrato> listarContratos();

	List<Uso> listarUsos();

	void eliminarCarteraDetalle(Detallecartera detallecartera);

	List<Cuota> listarCuotas();

	void registrarCuota(Cuota cuota);

	List<Cuota> listarCuotasPorIdCartera(int idcartera);

	List<Detallecartera> listarDetalleCarterasPorIdCartera(int idcartera);

	List<String> listarNombresUsuarios();

	List<Detallecartera> listarDetallecarteraPorIdCartera(int idcartera);

	List<Detallecartera> listarDetalleCarterasPorNroContrato(String nrocontrato);

	List<Contrato> listarContratosdisponibles();

	List<Contrato> listarContratosxcartera(int idcartera);

	Contrato obtenerContratoPorId(int id);

	List<Detallecuota> listarDetallecuotas();

	Cuota obtenerUltimoCuota();

	void registrarDetalleCuota(Detallecuota detallecuota);

	List<Detallecuota> listardetallecuotasxcontrato(int idcontrato);

	List<Cuota> listarcuotasxcontrato(int idcontrato);

	
	double obtenerMontocuotaspagadas(int idcontrato);

	List<Cuota> listartodascuotasxcartera(int idcartera);

	List<Contrato> listarContratosdecartera(int idcartera, int idcontrato);

	Detallecuota obtenerUltimoDetalleCuota();

	double obtenerMontoAcumuladoDetallecuota(int idcuota);

	List<Detallecuota> listardetallecuotasxcontratoycuota(int idcontrato,
			int idcuota);

	List<Detallecuota> listardetallecuotaxcontrato(int idcartera, int idcontrato);

	double obtenerMontoAcumuladoDetallecuotadolar(int idcuota);

	Cuota obtenerCuotaPorId(int id);

	

	
}

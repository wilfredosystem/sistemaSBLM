package com.sblm.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sblm.dao.ICarteraDAO;
import com.sblm.model.Cartera;
import com.sblm.model.Contrato;
import com.sblm.model.Cuota;
import com.sblm.model.Detallecartera;
import com.sblm.model.Detallecuota;
import com.sblm.model.Uso;
import com.sblm.model.Usuario;
import com.sblm.service.ICarteraService;

@Transactional(readOnly = true)
@Service(value="carteraService")
public class CarteraService implements ICarteraService{
	@Autowired
	private ICarteraDAO carteraDAO;

	@Transactional(readOnly = false)
	@Override
	public void registrarCartera(Cartera cartera) {
		getCarteraDAO().registrarCartera(cartera);
		
	}
	@Transactional(readOnly = false)
	@Override
	public void registrarCuota(Cuota cuota) {
		getCarteraDAO().registrarCuota(cuota);
		
	}
	@Transactional(readOnly = false)
	@Override
	public void registrarDetalleCuota(Detallecuota detallecuota) {
		getCarteraDAO().registrarDetalleCuota(detallecuota);
		
	}
	
	@Override
	public void registrarDetalleCartera(Detallecartera detallecartera) {
		getCarteraDAO().registrarDetalleCartera(detallecartera);
		
	}
	
	@Transactional(readOnly = false)
	@Override
	public void actualizarCartera(Cartera contrato) {
		getCarteraDAO().actualizarCartera(contrato);
		
	}
	@Transactional(readOnly = false)
	@Override
	public void eliminarCartera(Cartera cartera) {
		getCarteraDAO().eliminarCartera(cartera);
		
	}
	@Transactional(readOnly = false)
	@Override
	public void eliminarCarteraDetalle(Detallecartera detallecartera) {
		getCarteraDAO().eliminarCarteraDetalle(detallecartera);
		
	}
	
	@Override
	public List<Cartera> listarCarteras() {
		return getCarteraDAO().listarCarteras();
	}
	@Override
	public List<Usuario> listarUsuarios() {
		return getCarteraDAO().listarUsuarios();
	}
	@Override
	public List<String> listarNombresUsuarios() {
		return getCarteraDAO().listarNombresUsuarios();
	}
	
	@Override
	public List<Detallecartera> listarDetalleCarteras() {
		return getCarteraDAO().listarDetalleCarteras();
	}
	@Override
	public List<Detallecartera> listarDetalleCarterasPorIdCartera(int idcartera) {
		return getCarteraDAO().listarDetalleCarterasPorIdCartera(idcartera);
	}
	
	@Override
	public List<Contrato> listarContratos() {
		return getCarteraDAO().listarContratos();
	}
	@Override
	public List<Detallecuota> listarDetallecuotas() {
		return getCarteraDAO().listarDetallecuotas();
	}
	@Override
	public List<Detallecuota> listardetallecuotasxcontrato(int idcontrato) {
		return getCarteraDAO().listardetallecuotasxcontrato(idcontrato);
	}
	@Override
	public List<Detallecuota> listardetallecuotasxcontratoycuota(int idcontrato, int idcuota) {
		return getCarteraDAO().listardetallecuotasxcontratoycuota(idcontrato,idcuota);
	}
	
	@Override
	public List<Cuota> listarcuotasxcontrato(int idcontrato) {
		return getCarteraDAO().listarcuotasxcontrato(idcontrato);
	}
	@Override
	public List<Cuota> listartodascuotasxcartera(int idcartera) {
		return getCarteraDAO().listartodascuotasxcartera(idcartera);
	}
	
	@Override
	public List<Contrato> listarContratosdisponibles() {
		return getCarteraDAO().listarContratosdisponibles();
	}
	@Override
	public List<Contrato> listarContratosxcartera(int idcartera) {
		return getCarteraDAO().listarContratosxcartera(idcartera); 
	}
	@Override
	public List<Contrato> listarContratosdecartera(int idcartera, int idcontrato) {
		return getCarteraDAO().listarContratosdecartera(idcartera, idcontrato); 
	}
	@Override
	public List<Detallecuota> listardetallecuotaxcontrato(int idcartera, int idcontrato) {
		return getCarteraDAO().listardetallecuotaxcontrato(idcartera, idcontrato); 
	}
	
	@Override
	public List<Uso> listarUsos() {
		return getCarteraDAO().listarUsos();
	}
	@Override
	public List<Cuota> listarCuotas() {
		return getCarteraDAO().listarCuotas();
	}
	@Override
	public List<Cuota> listarCuotasPorIdCartera(int idcartera) {
		return getCarteraDAO().listarCuotasPorIdCartera(idcartera);
	}
	@Override
	public List<Detallecartera> listarDetallecarteraPorIdCartera(int idcartera) {
		return getCarteraDAO().listarDetallecarteraPorIdCartera(idcartera);
	}
	@Override
	public List<Detallecartera> listarDetalleCarterasPorNroContrato(String nrocontrato) {
		return getCarteraDAO().listarDetalleCarterasPorNroContrato(nrocontrato);
	}
	
	@Override
	public Cartera obtenerUltimoCartera() {
		return getCarteraDAO().obtenerUltimoCartera();
	}
	@Override
	public Cuota obtenerUltimoCuota() {
		return getCarteraDAO().obtenerUltimoCuota();
	}
	@Override
	public Detallecuota obtenerUltimoDetalleCuota() {
		return getCarteraDAO().obtenerUltimoDetalleCuota();
	}
	@Override
	public double obtenerMontoAcumuladoDetallecuota(int idcuota) {
		return getCarteraDAO().obtenerMontoAcumuladoDetallecuota(idcuota);
	}
	@Override
	public double obtenerMontoAcumuladoDetallecuotadolar(int idcuota) {
		return getCarteraDAO().obtenerMontoAcumuladoDetallecuotadolar(idcuota);
	}
	
	@Override
	public Cartera obtenerCarteraPorId(int id) {
		return getCarteraDAO().obtenerCarteraPorId(id);
	}
	@Override
	public Cuota obtenerCuotaPorId(int id) {
		return getCarteraDAO().obtenerCuotaPorId(id);
	}
	
	@Override
	public Contrato obtenerContratoPorId(int id) {
		return getCarteraDAO().obtenerContratoPorId(id);
	}
	
	@Override
	public int obtenerNumeroRegistros() {
		return getCarteraDAO().obtenerNumeroRegistros();
	}
	
	@Override
	public double obtenerMontocuotaspagadas(int idcontrato) {
		return getCarteraDAO().obtenerMontocuotaspagadas(idcontrato);
	}
	
	public ICarteraDAO getCarteraDAO() {
		return carteraDAO;
	}

	public void setCarteraDAO(ICarteraDAO carteraDAO) {
		this.carteraDAO = carteraDAO;
	}
	
	

}

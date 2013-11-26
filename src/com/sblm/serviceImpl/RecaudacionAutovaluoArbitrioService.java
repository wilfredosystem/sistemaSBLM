package com.sblm.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sblm.dao.IRecaudacionAutovaluoArbitrioDAO;
import com.sblm.model.Arbitrio;
import com.sblm.model.Archivodocumento;
import com.sblm.model.Contrato;
import com.sblm.model.Detallearbitrio;
import com.sblm.model.Extensionarchivo;
import com.sblm.model.Inquilino;
import com.sblm.model.Uso;
import com.sblm.service.IRecaudacionAutovaluoArbitrioService;

@Transactional(readOnly = true)
@Service(value="recaudacionautovaluoarbitrioService")
public class RecaudacionAutovaluoArbitrioService implements IRecaudacionAutovaluoArbitrioService{
	@Autowired
	private IRecaudacionAutovaluoArbitrioDAO recaudacionautovaluoarbitrioDAO;

	@Override
	public void grabarCabeceraArbitrio(Arbitrio arbitrio) {
		recaudacionautovaluoarbitrioDAO.grabarCabeceraArbitrio(arbitrio);
		
	}

	public IRecaudacionAutovaluoArbitrioDAO getRecaudacionautovaluoarbitrioDAO() {
		return recaudacionautovaluoarbitrioDAO;
	}

	public void setRecaudacionautovaluoarbitrioDAO(
			IRecaudacionAutovaluoArbitrioDAO recaudacionautovaluoarbitrioDAO) {
		this.recaudacionautovaluoarbitrioDAO = recaudacionautovaluoarbitrioDAO;
	}

	@Override
	public List<Detallearbitrio> obtenerDetalleArbitrio(int idarbitrio) {
		// TODO Auto-generated method stub
		return recaudacionautovaluoarbitrioDAO.obtenerDetalleArbitrio(idarbitrio);
	}

	@Override
	public List<Arbitrio> listarArbitrioConsulta() {
		// TODO Auto-generated method stub
		return recaudacionautovaluoarbitrioDAO.listarArbitrioConsulta();
	}

	@Override
	public void grabarDetalleArbitrio(Detallearbitrio detalleArbitrio) {
		recaudacionautovaluoarbitrioDAO.grabarDetalleArbitrio(detalleArbitrio);
		
	}

	@Override
	public List<Uso> getListaUso() {
		// TODO Auto-generated method stub
		return recaudacionautovaluoarbitrioDAO.getListaUso();
	}

	@Override
	public List<Inquilino> getListaInquilino() {
		// TODO Auto-generated method stub
		return recaudacionautovaluoarbitrioDAO.getListaInquilino();
	}

	@Override
	public void grabarArchivosAdjuntos(Archivodocumento archivodocu) {
		recaudacionautovaluoarbitrioDAO.grabarArchivosAdjuntos(archivodocu);
		
	}

	@Override
	public List<Archivodocumento> obtenerArchivosArbitrio(int idarbitrio) {
		// TODO Auto-generated method stub
		return recaudacionautovaluoarbitrioDAO.obtenerArchivosArbitrio(idarbitrio);
	}

	@Override
	public void actualizarCabeceraArbitrio(Arbitrio arbitrio) {
		recaudacionautovaluoarbitrioDAO.actualizarCabeceraArbitrio(arbitrio);
		
	}

	@Override
	public void eliminarArchivoDocumentoNoEncontrado(int idarchivodocumento) {
		recaudacionautovaluoarbitrioDAO.eliminarArchivoDocumentoNoEncontrado(idarchivodocumento);
		
	}

	@Override
	public Arbitrio getUltimaCabeceraGrabada() {
		// TODO Auto-generated method stub
		return recaudacionautovaluoarbitrioDAO.getUltimaCabeceraGrabada();
	}

	@Override
	public void actualizarvalorNroArchivos(int size,int idcarta) {
		recaudacionautovaluoarbitrioDAO.actualizarvalorNroArchivos(size,idcarta);
		
	}

	@Override
	public Double obtenerTotalActual(int idarbitrio) {
		return recaudacionautovaluoarbitrioDAO.obtenerTotalActual(idarbitrio);
	}

	@Override
	public void actualizarSumaTotalDetalleArbitrio(int idarbitrio,Double totalActual) {
		recaudacionautovaluoarbitrioDAO.actualizarSumaTotalDetalleArbitrio(idarbitrio,totalActual);
		
	}

	@Override
	public List<Contrato> listaContratos() {
		// TODO Auto-generated method stub
		
		return recaudacionautovaluoarbitrioDAO.listaContratos();
	}


	
}

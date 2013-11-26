package com.sblm.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sblm.dao.IRecaudacionAutovaluoArbitrioDAO;
import com.sblm.dao.IRecaudacionAutovaluoCartaDAO;
import com.sblm.model.Arbitrio;
import com.sblm.model.Archivodocumento;
import com.sblm.model.Carta;
import com.sblm.model.Detallearbitrio;
import com.sblm.model.Extensionarchivo;
import com.sblm.model.Inquilino;
import com.sblm.model.Uso;
import com.sblm.service.IRecaudacionAutovaluoArbitrioService;
import com.sblm.service.IRecaudacionAutovaluoCartaService;

@Transactional(readOnly = true)
@Service(value="recaudacionautovaluocartaService")
public class RecaudacionAutovaluoCartaService implements IRecaudacionAutovaluoCartaService{
	@Autowired
	private IRecaudacionAutovaluoCartaDAO recaudacionautovaluocartaDAO;


	@Override
	public void grabarCabeceraCarta(Carta carta) {
		
		recaudacionautovaluocartaDAO.grabarCabeceraArbitrio(carta);
		
	}


	@Override
	public List<Carta> listarArbitrioConsulta() {
		// TODO Auto-generated method stub
		return recaudacionautovaluocartaDAO.listarArbitrioConsulta();
	}


	@Override
	public Carta getUltimaCabeceraGrabada() {
		// TODO Auto-generated method stub
		return recaudacionautovaluocartaDAO.getUltimaCabeceraGrabada();
	}


	@Override
	public List<Archivodocumento> obtenerArchivosCarta(int idcarta) {
		// TODO Auto-generated method stub
		return recaudacionautovaluocartaDAO.obtenerArchivosCarta(idcarta);
	}


	@Override
	public void actualizarCabeceraCarta(Carta carta) {
		recaudacionautovaluocartaDAO.actualizarCabeceraCarta(carta);
		
	}


	
}

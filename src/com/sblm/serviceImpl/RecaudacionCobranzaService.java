package com.sblm.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sblm.dao.IRecaudacionCobranzaDAO;
import com.sblm.model.Perfilusuario;
import com.sblm.model.Seguimientoflujo;
import com.sblm.service.IRecaudacionAutovaluoService;
import com.sblm.service.IRecaudacionCobranzaService;

@Transactional(readOnly = true)
@Service(value="recaudacionCobranzaService")
public class RecaudacionCobranzaService implements IRecaudacionCobranzaService{

	@Autowired
	private IRecaudacionCobranzaDAO recaudacionCobranzaDAO;

	@Override
	public List<Seguimientoflujo> obtenerSeguimientoFlujodeDocumento(int mes) {
		return recaudacionCobranzaDAO.obtenerSeguimientoFlujodeDocumento(mes);
	}

	@Override
	public void regresarSupervisor(int idsegflujodocumento) {
		recaudacionCobranzaDAO.regresarSupervisor(idsegflujodocumento);
	}

	@Override
	public List<Seguimientoflujo> obtenerSeguimientoFlujodeDocumentoAtendido(
			int mesSeleccionado) {
		// TODO Auto-generated method stub
		return recaudacionCobranzaDAO.obtenerSeguimientoFlujodeDocumentoAtendido(mesSeleccionado);
	}

	@Override
	public void actualizarComentarioRespuestaSeguimientoFlujoDocumento(
			int idsegflujodocumento, String comentarioRechazo,
			int idusuarioremitente) {

		recaudacionCobranzaDAO.actualizarComentarioRespuestaSeguimientoFlujoDocumento(idsegflujodocumento,comentarioRechazo,idusuarioremitente);
		
	}

	@Override
	public int obtenerNumeroDocumentosMes(String respuesta) {
		// TODO Auto-generated method stub
		return 	recaudacionCobranzaDAO.obtenerNumeroDocumentosMes(respuesta);
	}

	@Override
	public int obtenerNumeroDocumentosMes(String respuesta,
			int mesActualcapturado) {
		// TODO Auto-generated method stub
		return 	recaudacionCobranzaDAO.obtenerNumeroDocumentosMes(respuesta,mesActualcapturado);
	}

	@Override
	public int obtenerNumeroPendientes() {
		// TODO Auto-generated method stub
		return 	recaudacionCobranzaDAO.obtenerNumeroPendientes();
	}

	@Override
	public int obtenerNumeroPendientesMes(int mesActualcapturado) {
		// TODO Auto-generated method stub
		return 	recaudacionCobranzaDAO.obtenerNumeroPendientesMes(mesActualcapturado);
	}

	@Override
	public int obtenerNumeroDerivados() {
		// TODO Auto-generated method stub
		return 	recaudacionCobranzaDAO.obtenerNumeroDerivados();
	}

	@Override
	public int obtenerNumeroDerivadosMes(int mesActualcapturado) {
		// TODO Auto-generated method stub
		return 	recaudacionCobranzaDAO.obtenerNumeroDerivadosMes(mesActualcapturado);
	}

	@Override
	public List<Seguimientoflujo> listaFiltroDocumentosMes(String respuesta) {
		// TODO Auto-generated method stub
		return 	recaudacionCobranzaDAO.listaFiltroDocumentosMes(respuesta);
	}

	@Override
	public Boolean estaDocumentoFinalizado(int idsegflujodocumento) {
		// TODO Auto-generated method stub
		return 	recaudacionCobranzaDAO.estaDocumentoFinalizado(idsegflujodocumento);
	}

}

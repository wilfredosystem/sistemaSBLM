package com.sblm.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sblm.dao.IRecaudacionAutovaluoDAO;
import com.sblm.model.Perfilusuario;
import com.sblm.model.Seguimientoflujo;
import com.sblm.service.IRecaudacionAutovaluoService;

@Transactional(readOnly = true)
@Service(value="recaudacionAutovaluoService")
public class RecaudacionAutovaluoService implements IRecaudacionAutovaluoService{

	@Autowired
	private IRecaudacionAutovaluoDAO recaudacionAutovaluoDAO;

	@Override
	public List<Seguimientoflujo> obtenerSeguimientoFlujodeDocumento(int mes) {
		return recaudacionAutovaluoDAO.obtenerSeguimientoFlujodeDocumento(mes);
	}

	@Override
	public void regresarSupervisor(int idsegflujodocumento) {
		recaudacionAutovaluoDAO.regresarSupervisor(idsegflujodocumento);
	}

	@Override
	public List<Seguimientoflujo> obtenerSeguimientoFlujodeDocumentoAtendido(
			int mesSeleccionado) {
		// TODO Auto-generated method stub
		return recaudacionAutovaluoDAO.obtenerSeguimientoFlujodeDocumentoAtendido(mesSeleccionado);
	}

	@Override
	public void actualizarComentarioRespuestaSeguimientoFlujoDocumento(
			int idsegflujodocumento, String comentarioRechazo,
			int idusuarioremitente) {

		recaudacionAutovaluoDAO.actualizarComentarioRespuestaSeguimientoFlujoDocumento(idsegflujodocumento,comentarioRechazo,idusuarioremitente);
		
	}

	@Override
	public int obtenerNumeroDocumentosMes(String respuesta) {
		// TODO Auto-generated method stub
		return 	recaudacionAutovaluoDAO.obtenerNumeroDocumentosMes(respuesta);
	}

	@Override
	public int obtenerNumeroDocumentosMes(String respuesta,
			int mesActualcapturado) {
		// TODO Auto-generated method stub
		return 	recaudacionAutovaluoDAO.obtenerNumeroDocumentosMes(respuesta,mesActualcapturado);
	}

	@Override
	public int obtenerNumeroPendientes() {
		// TODO Auto-generated method stub
		return 	recaudacionAutovaluoDAO.obtenerNumeroPendientes();
	}

	@Override
	public int obtenerNumeroPendientesMes(int mesActualcapturado) {
		// TODO Auto-generated method stub
		return 	recaudacionAutovaluoDAO.obtenerNumeroPendientesMes(mesActualcapturado);
	}

	@Override
	public int obtenerNumeroDerivados() {
		// TODO Auto-generated method stub
		return 	recaudacionAutovaluoDAO.obtenerNumeroDerivados();
	}

	@Override
	public int obtenerNumeroDerivadosMes(int mesActualcapturado) {
		// TODO Auto-generated method stub
		return 	recaudacionAutovaluoDAO.obtenerNumeroDerivadosMes(mesActualcapturado);
	}

	@Override
	public List<Seguimientoflujo> listaFiltroDocumentosMes(String respuesta) {
		// TODO Auto-generated method stub
		return 	recaudacionAutovaluoDAO.listaFiltroDocumentosMes(respuesta);
	}

	@Override
	public Boolean estaDocumentoFinalizado(int idsegflujodocumento) {
		// TODO Auto-generated method stub
		return 	recaudacionAutovaluoDAO.estaDocumentoFinalizado(idsegflujodocumento);
	}



	

}

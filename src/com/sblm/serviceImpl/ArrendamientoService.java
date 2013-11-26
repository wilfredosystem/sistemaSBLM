package com.sblm.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sblm.dao.IArrendamientoDAO;
import com.sblm.model.Seguimientoflujo;
import com.sblm.service.IArrendamientoService;

@Transactional(readOnly = true)
@Service(value="arrendamientoService")
public class ArrendamientoService implements IArrendamientoService{

	@Autowired
	private IArrendamientoDAO arrendamientoDAO;

	@Override
	public List<Seguimientoflujo> obtenerSeguimientoFlujodeDocumento(int mes) {
		return arrendamientoDAO.obtenerSeguimientoFlujodeDocumento(mes);
	}

	@Override
	public void regresarSupervisor(int idsegflujodocumento) {
		arrendamientoDAO.regresarSupervisor(idsegflujodocumento);
	}

	@Override
	public List<Seguimientoflujo> obtenerSeguimientoFlujodeDocumentoAtendido(
			int mesSeleccionado) {
		// TODO Auto-generated method stub
		return arrendamientoDAO.obtenerSeguimientoFlujodeDocumentoAtendido(mesSeleccionado);
	}

	@Override
	public void actualizarComentarioRespuestaSeguimientoFlujoDocumento(
			int idsegflujodocumento, String comentarioRechazo,
			int idusuarioremitente) {

		arrendamientoDAO.actualizarComentarioRespuestaSeguimientoFlujoDocumento(idsegflujodocumento,comentarioRechazo,idusuarioremitente);
		
	}

	@Override
	public int obtenerNumeroDocumentosMes(String respuesta) {
		// TODO Auto-generated method stub
		return 	arrendamientoDAO.obtenerNumeroDocumentosMes(respuesta);
	}

	@Override
	public int obtenerNumeroDocumentosMes(String respuesta,
			int mesActualcapturado) {
		// TODO Auto-generated method stub
		return 	arrendamientoDAO.obtenerNumeroDocumentosMes(respuesta,mesActualcapturado);
	}

	@Override
	public int obtenerNumeroPendientes() {
		// TODO Auto-generated method stub
		return 	arrendamientoDAO.obtenerNumeroPendientes();
	}

	@Override
	public int obtenerNumeroPendientesMes(int mesActualcapturado) {
		// TODO Auto-generated method stub
		return 	arrendamientoDAO.obtenerNumeroPendientesMes(mesActualcapturado);
	}

	@Override
	public int obtenerNumeroDerivados() {
		// TODO Auto-generated method stub
		return 	arrendamientoDAO.obtenerNumeroDerivados();
	}

	@Override
	public int obtenerNumeroDerivadosMes(int mesActualcapturado) {
		// TODO Auto-generated method stub
		return 	arrendamientoDAO.obtenerNumeroDerivadosMes(mesActualcapturado);
	}

	@Override
	public List<Seguimientoflujo> listaFiltroDocumentosMes(String respuesta) {
		// TODO Auto-generated method stub
		return 	arrendamientoDAO.listaFiltroDocumentosMes(respuesta);
	}

	@Override
	public Boolean estaDocumentoFinalizado(int idsegflujodocumento) {
		// TODO Auto-generated method stub
		return 	arrendamientoDAO.estaDocumentoFinalizado(idsegflujodocumento);
	}

}

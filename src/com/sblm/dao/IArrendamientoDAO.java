package com.sblm.dao;

import java.util.List;
import com.sblm.model.Seguimientoflujo;

public interface IArrendamientoDAO {

	List<Seguimientoflujo> obtenerSeguimientoFlujodeDocumento(int mes);
	void regresarSupervisor(int idsegflujodocumento);
	List<Seguimientoflujo> obtenerSeguimientoFlujodeDocumentoAtendido(
			int mesSeleccionado);
	void actualizarComentarioRespuestaSeguimientoFlujoDocumento(
			int idsegflujodocumento, String comentarioRechazo,
			int idusuarioremitente);
	List<Seguimientoflujo> listaFiltroDocumentosMes(String respuesta);
	int obtenerNumeroDerivadosMes(int mesActualcapturado);
	int obtenerNumeroDerivados();
	int obtenerNumeroPendientesMes(int mesActualcapturado);
	int obtenerNumeroPendientes();
	int obtenerNumeroDocumentosMes(String respuesta, int mesActualcapturado);
	int obtenerNumeroDocumentosMes(String respuesta);
	Boolean estaDocumentoFinalizado(int idsegflujodocumento);



}

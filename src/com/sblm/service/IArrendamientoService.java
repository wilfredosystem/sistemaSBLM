package com.sblm.service;

import java.util.List;

import com.sblm.model.Archivodocumento;
import com.sblm.model.Perfilusuario;
import com.sblm.model.Seguimientoflujo;

public interface IArrendamientoService {

	List<Seguimientoflujo> obtenerSeguimientoFlujodeDocumento(int mes);

	void regresarSupervisor(int idsegflujodocumento);

	List<Seguimientoflujo> obtenerSeguimientoFlujodeDocumentoAtendido(
			int mesSeleccionado);

	void actualizarComentarioRespuestaSeguimientoFlujoDocumento(
			int idsegflujodocumento, String comentarioRechazo,
			int idusuarioremitente);

	int obtenerNumeroDocumentosMes(String respuesta);

	int obtenerNumeroDocumentosMes(String respuesta, int mesActualcapturado);

	int obtenerNumeroPendientes();

	int obtenerNumeroPendientesMes(int mesActualcapturado);

	int obtenerNumeroDerivados();

	int obtenerNumeroDerivadosMes(int mesActualcapturado);

	List<Seguimientoflujo> listaFiltroDocumentosMes(String respuesta);

	Boolean estaDocumentoFinalizado(int idsegflujodocumento);



}

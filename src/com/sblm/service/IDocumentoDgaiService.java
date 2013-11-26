package com.sblm.service;

import java.util.Date;
import java.util.List;

import com.sblm.model.Archivodocumento;
import com.sblm.model.Documento;
import com.sblm.model.Modulo;

public interface IDocumentoDgaiService {

	public void registrarDocumentoDgai(Documento documento);

	public void actualizarDocumentoDgai(int id);

	public void eliminarDocumentoDgai(Documento documento);

	public Modulo listarDocumentoDgaiPorId(int id);

	public List<Documento> listarDocumentosDgai();
	
	public int totalPendientesDerivacion(); 
	
	public Documento obtenerUltimodocumento();

	public List<Documento> getAllDocumentos();

	public List<Documento> busquedaIntervaloFecha(Date fechaFin,
			Date fechaInicio);

	public List<Archivodocumento> cargarArchivosDocumento(int iddocumento);

	public List<Documento> listaDocumentosSinDerivar();

	Documento obtenerUltimodocumentoDespachado();

	List<Documento> listaDocumentosDespachados();

	List<Documento> listaDocumentosPendientes();

	List<Documento> listaDocumentosRechazados();

List<Documento> listaFiltroDocumentos(String val);

List<Documento> listaFiltroDocumentosMes(String val);

}

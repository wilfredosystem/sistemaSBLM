package com.sblm.dao;

import java.util.List;

import com.sblm.model.Archivodocumento;
import com.sblm.model.Extensionarchivo;
import com.sblm.model.Usuario;
import com.sblm.modelMP.Documento;
import com.sblm.modelMP.Flujodoc;

public interface IMonitoreoMesaPartesDAO {

	public List<com.sblm.model.Documento> listarDocumentosRegistrados();  

	public Object countExternalDB();

	public Object countInternalDB();

	public List<Flujodoc> getNewInserts(int val);

	public void save(com.sblm.model.Documento doc);

	public Object countPendientes();
//
	public Object countAtendidos();

	public void actualizarEstadoToAtendido(int iddoc, List<Usuario> listaUsuarioSeleccionados);

	public List<com.sblm.model.Documento> listarDocumentosAtendidos(int mes);

	public Usuario getDirectorDGAI();

	public List<Extensionarchivo> getListaExtensionArhivos();

	public void agregarArchivosDocumento(Archivodocumento archivodocu);

	String obtenerUltimoDespacho();

//	List<com.sblm.model.Documento> listaDocumentosDespachados();
//
//	List<com.sblm.model.Documento> listaDocumentosRechazados();
//
//	List<com.sblm.model.Documento> listaDocumentosPendientes();

	Object countPendientesMes();

	Object countAtendidosMes();

//	Object countRechazados();

	Object countRechazadosMes();

	List<com.sblm.model.Documento> listaFiltroDocumentos(String resp);

	Object countDocumentosFiltrados(String resp);

	List<com.sblm.model.Documento> listaFiltroDocumentosMes(String resp);

	List<com.sblm.model.Documento> listarDocumentosAtendidosPorMes(int mes);

	Object countDocumentosFiltradosMes(String resp, int mes);

	List<com.sblm.model.Documento> listarDocumentosAtendidos();

	List<com.sblm.model.Documento> listarDocumentosRegistrados(int mes);

	Object countPendientesMes(int mes);

	Object countAtendidosMes(int mes);

	public List<com.sblm.model.Documento> getListLocal();

	public void eliminarDocumentoNoEncontrado(String codigodocumento);



}

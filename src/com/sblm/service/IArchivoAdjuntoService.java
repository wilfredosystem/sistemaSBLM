package com.sblm.service;

import java.util.List;

import com.sblm.model.Archivodocumento;
import com.sblm.model.Extensionarchivo;
import com.sblm.model.Inmueble;
import com.sblm.model.Inquilino;
import com.sblm.model.Upa;

public interface IArchivoAdjuntoService {
	public void registrarArchivosDocumento(Archivodocumento archivodocu);

	List<Extensionarchivo> getListaExtensionArhivos();

	List<Archivodocumento> listarArchivodocumentos();

	Archivodocumento obtenerUltimoArchivodocumento();

	Archivodocumento obtenerArchivodocumentoPorId(int id);

	int obtenerNumeroRegistros();

	List<Archivodocumento> obtenerArchivodocumentosPorIdInmueble(int id);

	Extensionarchivo obtenerExtensionArhivoporId(int id);

	List<Extensionarchivo> getListaExtensionArhivosPorRutaIcono(String ruta);

	Extensionarchivo obtenerExtensionArhivoPorRutaIcono(String ruta);

	void eliminarArhivoDocumento(Inmueble inm);

	Extensionarchivo obtenerExtensionArhivoPorExtension(String ext);

	Inmueble obtenerUltimoInmueble();

	

	Upa obtenerUltimoUpa();

	List<Archivodocumento> obtenerArchivodocumentosPorIdUpa(int id);

	void eliminarArhivoDocumentoUpa(Upa upa);

	Inquilino obtenerUltimoInquilino();

	void eliminarArhivoDocumentoInquilino(Inquilino inquilino);

	List<Archivodocumento> obtenerArchivodocumentosPorIdInquilino(int id);

//	List<Archivodocumento> obtenerArchivodocumentosPorId(int id);
}

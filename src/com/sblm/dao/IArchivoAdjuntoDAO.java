package com.sblm.dao;

import java.util.List;

import com.sblm.model.Archivodocumento;
import com.sblm.model.Extensionarchivo;
import com.sblm.model.Inmueble;
import com.sblm.model.Inquilino;
import com.sblm.model.Upa;

public interface IArchivoAdjuntoDAO {
	public void registrarArchivosDocumento(Archivodocumento archivodocu);

	List<Extensionarchivo> getListaExtensionArhivos();

	int obtenerNumeroRegistros();

	List<Archivodocumento> listarArchivodocumentos();

	Archivodocumento obtenerUltimoArchivodocumento();

	Archivodocumento obtenerArchivodocumentoPorId(int id);

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
}

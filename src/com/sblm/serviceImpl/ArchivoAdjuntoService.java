package com.sblm.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sblm.dao.IArchivoAdjuntoDAO;
import com.sblm.model.Archivodocumento;
import com.sblm.model.Extensionarchivo;
import com.sblm.model.Inmueble;
import com.sblm.model.Inquilino;
import com.sblm.model.Upa;
import com.sblm.service.IArchivoAdjuntoService;
@Transactional(readOnly = true)
@Service(value="archivoadjuntoService")
public class ArchivoAdjuntoService implements IArchivoAdjuntoService{
	@Autowired
	private IArchivoAdjuntoDAO archivoadjuntoDAO;

	
	@Transactional(readOnly = false)
	@Override
	public void registrarArchivosDocumento(Archivodocumento archivodocu) {
		getArchivoadjuntoDAO().registrarArchivosDocumento(archivodocu);
		
	}
	
	@Override
	public List<Archivodocumento> listarArchivodocumentos() {
		return getArchivoadjuntoDAO().listarArchivodocumentos();
	}

	@Override
	public Archivodocumento obtenerUltimoArchivodocumento() {
		return getArchivoadjuntoDAO().obtenerUltimoArchivodocumento();
	}

	@Override
	public Archivodocumento obtenerArchivodocumentoPorId(int id) {
		return getArchivoadjuntoDAO().obtenerArchivodocumentoPorId(id);
	}
	@Override
	public List<Archivodocumento> obtenerArchivodocumentosPorIdInmueble(int id) {
		return getArchivoadjuntoDAO().obtenerArchivodocumentosPorIdInmueble(id); 
	}
	@Override
	public List<Archivodocumento> obtenerArchivodocumentosPorIdUpa(int id) {
		return getArchivoadjuntoDAO().obtenerArchivodocumentosPorIdUpa(id); 
	}
	@Override
	public List<Archivodocumento> obtenerArchivodocumentosPorIdInquilino(int id) {
		return getArchivoadjuntoDAO().obtenerArchivodocumentosPorIdInquilino(id); 
	}
	@Override
	public int obtenerNumeroRegistros() {
		return getArchivoadjuntoDAO().obtenerNumeroRegistros();
	}
	 
	@Override
	public List<Extensionarchivo> getListaExtensionArhivos() {
		return getArchivoadjuntoDAO().getListaExtensionArhivos();
	}
	@Override
	public Extensionarchivo obtenerExtensionArhivoPorRutaIcono(String ruta) {
		return getArchivoadjuntoDAO().obtenerExtensionArhivoPorRutaIcono(ruta);
	}
	@Override
	public List<Extensionarchivo> getListaExtensionArhivosPorRutaIcono(String ruta) {
		return getArchivoadjuntoDAO().getListaExtensionArhivosPorRutaIcono(ruta);
	}
	@Override
	public Extensionarchivo obtenerExtensionArhivoporId(int id) {
		return getArchivoadjuntoDAO().obtenerExtensionArhivoporId(id); 
	}
	@Override
	public Extensionarchivo obtenerExtensionArhivoPorExtension(String ext) {
		return getArchivoadjuntoDAO().obtenerExtensionArhivoPorExtension(ext); 
	}
	@Override
	public Inmueble obtenerUltimoInmueble() {
		return getArchivoadjuntoDAO().obtenerUltimoInmueble();
	}
	@Override
	public Upa obtenerUltimoUpa() {
		return getArchivoadjuntoDAO().obtenerUltimoUpa();
	}
	@Override
	public Inquilino obtenerUltimoInquilino() {
		return getArchivoadjuntoDAO().obtenerUltimoInquilino();
	}
	@Override
	public void eliminarArhivoDocumento(Inmueble inm) { 
		 getArchivoadjuntoDAO().eliminarArhivoDocumento(inm);
	}
	@Override
	public void eliminarArhivoDocumentoUpa(Upa upa) { 
		 getArchivoadjuntoDAO().eliminarArhivoDocumentoUpa(upa);
	}
	@Override
	public void eliminarArhivoDocumentoInquilino(Inquilino inquilino) { 
		 getArchivoadjuntoDAO().eliminarArhivoDocumentoInquilino(inquilino);
	}
	public IArchivoAdjuntoDAO getArchivoadjuntoDAO() {
		return archivoadjuntoDAO;
	}

	public void setArchivoadjuntoDAO(IArchivoAdjuntoDAO archivoadjuntoDAO) {
		this.archivoadjuntoDAO = archivoadjuntoDAO;
	}


	
	
}

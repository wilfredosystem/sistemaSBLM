package com.sblm.serviceImpl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sblm.dao.IDocumentoDgaiDAO;
import com.sblm.model.Archivodocumento;
import com.sblm.model.Documento;
import com.sblm.model.Modulo;
import com.sblm.service.IDocumentoDgaiService;

@Transactional(readOnly = true)
@Service(value="documentodgaiService")
public class DocumentoDgaiService implements IDocumentoDgaiService {

	@Autowired
	private IDocumentoDgaiDAO documentodgaiDAO;

	@Transactional(readOnly = false)
	@Override
	public void registrarDocumentoDgai(Documento documento) {
		getDocumentodgaiDAO().registrarDocumentoDgai(documento);
		
	}
	@Transactional(readOnly = false)
	@Override
	public void actualizarDocumentoDgai(int id) {
		getDocumentodgaiDAO().actualizarDocumentoDgai(id);
		
	}
	@Transactional(readOnly = false)
	@Override
	public void eliminarDocumentoDgai(Documento documento) {
		getDocumentodgaiDAO().eliminarDocumentoDgai(documento);
	}

	@Override
	public Modulo listarDocumentoDgaiPorId(int id) {
		return getDocumentodgaiDAO().listarDocumentoDgaiPorId(id);
	}

	@Override
	public List<Documento> listarDocumentosDgai() {
		return getDocumentodgaiDAO().listarDocumentosDgai();
	}
	public IDocumentoDgaiDAO getDocumentodgaiDAO() {
		return documentodgaiDAO;
	}
	public void setDocumentodgaiDAO(IDocumentoDgaiDAO documentodgaiDAO) {
		this.documentodgaiDAO = documentodgaiDAO;
	}
	@Override
	public int totalPendientesDerivacion() {
		return getDocumentodgaiDAO().totalPendientesDerivacion();
	}
	@Override
	public Documento obtenerUltimodocumento() {
		return getDocumentodgaiDAO().obtenerUltimodocumento();
	}
	@Override
	public Documento obtenerUltimodocumentoDespachado() {
		return getDocumentodgaiDAO().obtenerUltimodocumentoDespachado();
	}
	
	@Override
	public List<Documento> getAllDocumentos() {
		// TODO Auto-generated method stub
		return getDocumentodgaiDAO().getAllDocumentos();	}
	@Override
	public List<Documento> busquedaIntervaloFecha(Date fechaFin,
			Date fechaInicio) {
		// TODO Auto-generated method stub
		return getDocumentodgaiDAO().busquedaIntervaloFecha(fechaFin,fechaInicio);
	}
	@Override
	public List<Archivodocumento> cargarArchivosDocumento(int iddocumento) {
		// TODO Auto-generated method stub
		return getDocumentodgaiDAO().cargarArchivosDocumento(iddocumento);
	}
	@Override
	public List<Documento> listaDocumentosSinDerivar() {
		// TODO Auto-generated method stub
		return getDocumentodgaiDAO().listaDocumentosSinDerivar();
	}
	
	@Override
	public List<Documento> listaFiltroDocumentos(String val) {
		// TODO Auto-generated method stub
		return getDocumentodgaiDAO().listaFiltroDocumentos(val);
	}
	@Override
	public List<Documento> listaFiltroDocumentosMes(String val) {
		// TODO Auto-generated method stub
		return getDocumentodgaiDAO().listaFiltroDocumentosMes(val);
	}
	
	@Override
	public List<Documento> listaDocumentosDespachados() {
		// TODO Auto-generated method stub
		return getDocumentodgaiDAO().listaDocumentosDespachados();
	}
	@Override
	public List<Documento> listaDocumentosPendientes() {
		// TODO Auto-generated method stub
		return getDocumentodgaiDAO().listaDocumentosPendientes();
	}
	@Override
	public List<Documento> listaDocumentosRechazados() {
		// TODO Auto-generated method stub
		return getDocumentodgaiDAO().listaDocumentosRechazados();
	}
	

}

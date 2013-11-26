package com.sblm.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sblm.dao.IFlujoDocumentoDAO;
import com.sblm.model.Documento;
import com.sblm.model.Flujodocumento;
import com.sblm.model.Modulo;
import com.sblm.model.Perfil;
import com.sblm.model.Perfilusuario;
import com.sblm.model.Usuario;
import com.sblm.service.IFlujoDocumentoService;

@Transactional(readOnly = true)
@Service(value="flujodocumentoService")
public class FlujoDocumentoService implements IFlujoDocumentoService{

	@Autowired
	private IFlujoDocumentoDAO flujodocumentoDAO;

	@Transactional(readOnly = false)
	@Override
	public void registrarFlujoDocumento(Flujodocumento flujodocumento) {
		getFlujodocumentoDAO().registrarFlujoDocumento(flujodocumento);
		
	}
	@Transactional(readOnly = false)
	@Override
	public void actualizarFlujoDocumento(Flujodocumento flujodocumento) {
		// TODO Auto-generated method stub
		
	}
	@Transactional(readOnly = false)
	@Override
	public void eliminarFlujoDocumento(Flujodocumento flujodocumento) {
		getFlujodocumentoDAO().eliminarFlujoDocumento(flujodocumento);
		
	}

	@Override
	public Modulo listarFlujoDocumentoPorId(int id) {
		return getFlujodocumentoDAO().listarFlujoDocumentoPorId(id);
	}

	@Override
	public List<Flujodocumento> listarFlujoDocumento() {
		return getFlujodocumentoDAO().listarFlujoDocumento();
	}

	@Override
	public void actualizarRespuestaToAtendido(int iddoc) {
		getFlujodocumentoDAO().actualizarRespuestaToAtendido(iddoc);
		
	}

	public IFlujoDocumentoDAO getFlujodocumentoDAO() {
		return flujodocumentoDAO;
	}

	public void setFlujodocumentoDAO(IFlujoDocumentoDAO flujodocumentoDAO) {
		this.flujodocumentoDAO = flujodocumentoDAO;
	}
	@Override
	public int obtenerNumeroDespachados() {
		return getFlujodocumentoDAO().obtenerNumeroDespachados();
	}
	@Override
	public int obtenerNumeroPendientes() {
		return getFlujodocumentoDAO().obtenerNumeroPendientes();
	}
	@Override
	public int obtenerNumeroPendientesMes() {
		return getFlujodocumentoDAO().obtenerNumeroPendientesMes();
	}
	@Override
	public int obtenerNumeroPendientesMes(int mes) {
		return getFlujodocumentoDAO().obtenerNumeroPendientesMes(mes);
	}
	
	@Override
	public int obtenerNumeroRechazados() {
		return getFlujodocumentoDAO().obtenerNumeroRechazados();
	}
	@Override
	public int obtenerNumeroDocumentos(String val) {
		return getFlujodocumentoDAO().obtenerNumeroDocumentos(val);
	}
	@Override
	public int obtenerNumeroDocumentosMes(String val) {
		return getFlujodocumentoDAO().obtenerNumeroDocumentosMes(val);
	}
	@Override
	public int obtenerNumeroDocumentosMes(String val, int mes) {
		return getFlujodocumentoDAO().obtenerNumeroDocumentosMes(val,mes);
	}
	
	public Documento obtenerUltimodocumento() {
		return getFlujodocumentoDAO().obtenerUltimodocumento();
	}
	@Override
	public List<Documento> listarDocumentoDerivados() {
		// TODO Auto-generated method stub
		return getFlujodocumentoDAO().listarDocumentoDerivados();	}
	@Override
	public List<Documento> listarDocumentoDerivadosMes(int mes) {
		// TODO Auto-generated method stub
		return getFlujodocumentoDAO().listarDocumentoDerivadosMes(mes);	}

	
	
	@Override
	public List<Documento> listaDocumentosSinDerivar(int mes) {
		// TODO Auto-generated method stub
		return getFlujodocumentoDAO().listaDocumentosSinDerivar(mes);	}
	
	
	@Override
	public List<Flujodocumento> obtenerFlujodeDocumento(int idDocumento) {
		return getFlujodocumentoDAO().obtenerFlujodeDocumento(idDocumento);	}

	@Override
	public void actualizarComentarioDocumento(int iddocumento,
			String comentarioRechazo, List<Flujodocumento> flujoDocumentoIndex) {
		getFlujodocumentoDAO().actualizarComentarioDocumento(iddocumento,comentarioRechazo,flujoDocumentoIndex);
		
	}
	@Override
	public void regresarMesaPartes(int iddocumento) {
		getFlujodocumentoDAO().regresarMesaPartes(iddocumento);
		
	}

	@Override
	public int obtenerNumeroPendientesSalida() {
		// TODO Auto-generated method stub
		return getFlujodocumentoDAO().obtenerNumeroPendientesSalida();
	}
	@Override
	public int obtenerNumeroDerivados() {
		// TODO Auto-generated method stub
		return getFlujodocumentoDAO().obtenerNumeroDerivados();
	}
	@Override
	public int obtenerNumeroDerivadosMes() {
		// TODO Auto-generated method stub
		return getFlujodocumentoDAO().obtenerNumeroDerivadosMes();
	}
	@Override
	public int obtenerNumeroDerivadosMes(int mes) {
		// TODO Auto-generated method stub
		return getFlujodocumentoDAO().obtenerNumeroDerivadosMes(mes);
	}
	
	@Override
	public int obtenertamanioFlujoDocumento(int iddocumento) {
		// TODO Auto-generated method stub
		return getFlujodocumentoDAO().obtenertamanioFlujoDocumento(iddocumento);
	}
	@Override
	public Boolean estaDocumentoFinalizado(int iddocumento) {
		// TODO Auto-generated method stub
		return getFlujodocumentoDAO().estaDocumentoFinalizado(iddocumento);
	}
	@Override
	public Boolean estaFlujoFinalizado(int iddocumento) {
		// TODO Auto-generated method stub
		return getFlujodocumentoDAO().estaFlujoFinalizado(iddocumento);
	}
	@Override
	public void enviarNotificaciónPersonalizable(
			String contenidoMensajePersonalizado,
			int idusuarioNotificacionPersonalizada) {
		getFlujodocumentoDAO().enviarNotificaciónPersonalizable(idusuarioNotificacionPersonalizada,contenidoMensajePersonalizado);
		
	}
	@Override
	public List<Usuario> obtenerUsuarios() {
		// TODO Auto-generated method stub
		return getFlujodocumentoDAO().obtenerUsuarios();
	}
	@Override
	public List<Perfilusuario> getPerfilesUsuario(int idUsuarioSeleccionado) {
		// TODO Auto-generated method stub
		return getFlujodocumentoDAO().getPerfilesUsuario(idUsuarioSeleccionado);
	}
	@Override
	public List<Perfil> obtenerPerfiles() {
		// TODO Auto-generated method stub
		return getFlujodocumentoDAO().obtenerPerfiles();
	}


}

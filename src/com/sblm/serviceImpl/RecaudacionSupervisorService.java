package com.sblm.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sblm.dao.IDocumentoDgaiDAO;
import com.sblm.dao.IRecaudacionSupervisorDAO;
import com.sblm.model.Flujodocumento;
import com.sblm.model.Perfil;
import com.sblm.model.Perfilusuario;
import com.sblm.model.Seguimientoflujo;
import com.sblm.model.Usuario;
import com.sblm.modelMP.Flujodoc;
import com.sblm.service.IRecaudacionSupervisionService;

@Transactional(readOnly = true)
@Service(value="recaudacionSupervisionService")
public class RecaudacionSupervisorService implements IRecaudacionSupervisionService {

	@Autowired
	private IRecaudacionSupervisorDAO recaudacionSupervisorDAO;

	@Override
	public List<Flujodocumento> getFlujoDocumentosSupervisor() {
		// TODO Auto-generated method stub
		return recaudacionSupervisorDAO.getFlujoDocumentosSupervisor();
	}
	
	@Override
	public List<Flujodocumento> getFlujoDocumentosSupervisorAtendidos() {
		// TODO Auto-generated method stub
		return recaudacionSupervisorDAO.getFlujoDocumentosSupervisorAtendidos();
	}




	public IRecaudacionSupervisorDAO getRecaudacionSupervisorDAO() {
		return recaudacionSupervisorDAO;
	}

	public void setRecaudacionSupervisorDAO(
			IRecaudacionSupervisorDAO recaudacionSupervisorDAO) {
		this.recaudacionSupervisorDAO = recaudacionSupervisorDAO;
	}

	@Override
	public List<Usuario> obtenerUsuarios() {
		// TODO Auto-generated method stub
		return recaudacionSupervisorDAO.obtenerUsuarios();
	}

	@Override
	public void registrarSeguimientoFlujo(Seguimientoflujo segflujo, int idusuario) {
		recaudacionSupervisorDAO.registrarSeguimientoFlujo(segflujo,idusuario);
		
	}

	@Override
	public void actualizarFlujoDocumentoSupervisor(int iddocumento) {
		recaudacionSupervisorDAO.actualizarFlujoDocumentoSupervisor(iddocumento);
		
	}

	@Override
	public List<Seguimientoflujo> obtenerSeguimientoFlujodeDocumento(
			int idflujoDocumento) {
		// TODO Auto-generated method stub
		return recaudacionSupervisorDAO.obtenerSeguimientoFlujodeDocumento(idflujoDocumento);
	}

	@Override
	public void actualizarComentarioRespuestaFlujoDocumento(
			int idflujodocumento, String comentarioRechazo,
			List<Seguimientoflujo> seguimientoFlujoDocumento) {
		recaudacionSupervisorDAO.actualizarComentarioRespuestaFlujoDocumento(idflujodocumento,comentarioRechazo,seguimientoFlujoDocumento);
		
	}

	@Override
	public void regresarDGAI(int idflujodocumento) {
		recaudacionSupervisorDAO.regresarDGAI(idflujodocumento);
		
	}

	@Override
	public int obtenertamanioFlujoDocumento(int idflujodocumento) {
		// TODO Auto-generated method stub
		return recaudacionSupervisorDAO.obtenertamanioFlujoDocumento(idflujodocumento);
	}

	@Override
	public Boolean estaDocumentoFinalizado(int idflujodocumento) {
		// TODO Auto-generated method stub
		return recaudacionSupervisorDAO.estaDocumentoFinalizado(idflujodocumento);
	}

	@Override
	public Boolean estaFlujoFinalizado(int idflujodocumento) {
		// TODO Auto-generated method stub
		return recaudacionSupervisorDAO.estaFlujoFinalizado(idflujodocumento);
	}

	@Override
	public List<Flujodocumento> getFlujoDocumentosSupervisor(int mesSeleccionado) {
		// TODO Auto-generated method stub
		return recaudacionSupervisorDAO.getFlujoDocumentosSupervisor(mesSeleccionado);
	}

	@Override
	public List<Flujodocumento> getFlujoDocumentosSupervisorAtendidos(
			int mesSeleccionado) {
		// TODO Auto-generated method stub
		return recaudacionSupervisorDAO.getFlujoDocumentosSupervisorAtendidos(mesSeleccionado);
	}

	@Override
	public int obtenerNumeroPendientes() {
		// TODO Auto-generated method stub
		return recaudacionSupervisorDAO.obtenerNumeroPendientes();
	}

	@Override
	public int obtenerNumeroDerivados() {
		// TODO Auto-generated method stub
		return recaudacionSupervisorDAO.obtenerNumeroDerivados();
	}

	@Override
	public int obtenerNumeroPendientesMes(int mesActualcapturado) {
		// TODO Auto-generated method stub
		return recaudacionSupervisorDAO.obtenerNumeroPendientesMes(mesActualcapturado);
	}

	@Override
	public int obtenerNumeroDerivadosMes(int mesActualcapturado) {
		// TODO Auto-generated method stub
		return recaudacionSupervisorDAO.obtenerNumeroDerivadosMes(mesActualcapturado);
	}

	@Override
	public int obtenerNumeroDocumentosMes(String respuesta) {
		// TODO Auto-generated method stub
		return recaudacionSupervisorDAO.obtenerNumeroDocumentosMes(respuesta);
	}

	@Override
	public int obtenerNumeroDocumentosMes(String respuesta, int mesActualcapturado) {
		// TODO Auto-generated method stub
		return recaudacionSupervisorDAO.obtenerNumeroDocumentosMes(respuesta,mesActualcapturado);
	}

	@Override
	public List<Flujodocumento> listaFiltroDocumentosMes(String respuesta) {
		// TODO Auto-generated method stub
		return recaudacionSupervisorDAO.listaFiltroDocumentosMes(respuesta);
	}

	@Override
	public void enviarNotificaciónPersonalizable(
			String contenidoMensajePersonalizado,
			int idusuarioNotificacionPersonalizada) {

		recaudacionSupervisorDAO.enviarNotificaciónPersonalizable(contenidoMensajePersonalizado,idusuarioNotificacionPersonalizada);
	}

	@Override
	public List<Perfilusuario> getPerfilesUsuario(int idusuario) {
		// TODO Auto-generated method stub
		return recaudacionSupervisorDAO.getPerfilesUsuario(idusuario);
	}

	@Override
	public List<Perfil> obtenerPerfiles() {
		// TODO Auto-generated method stub
		return recaudacionSupervisorDAO.obtenerPerfiles();
	}


}

package com.sblm.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sblm.dao.IArrendamientoSupervisorDAO;
import com.sblm.dao.IDocumentoDgaiDAO;
import com.sblm.dao.IRecaudacionSupervisorDAO;
import com.sblm.model.Flujodocumento;
import com.sblm.model.Perfil;
import com.sblm.model.Perfilusuario;
import com.sblm.model.Seguimientoflujo;
import com.sblm.model.Usuario;
import com.sblm.modelMP.Flujodoc;
import com.sblm.service.IArrendamientoSupervisionService;
import com.sblm.service.IRecaudacionSupervisionService;

@Transactional(readOnly = true)
@Service(value="arrendamientoSupervisionService")
public class ArrendamientoSupervisorService implements IArrendamientoSupervisionService{

	@Autowired
	private IArrendamientoSupervisorDAO arrendamientoSupervisorDAO;

	@Override
	public List<Flujodocumento> getFlujoDocumentosSupervisor() {
		// TODO Auto-generated method stub
		return arrendamientoSupervisorDAO.getFlujoDocumentosSupervisor();
	}
	
	@Override
	public List<Flujodocumento> getFlujoDocumentosSupervisorAtendidos() {
		// TODO Auto-generated method stub
		return arrendamientoSupervisorDAO.getFlujoDocumentosSupervisorAtendidos();
	}

	@Override
	public List<Usuario> obtenerUsuarios() {
		// TODO Auto-generated method stub
		return arrendamientoSupervisorDAO.obtenerUsuarios();
	}

	@Override
	public void registrarSeguimientoFlujo(Seguimientoflujo segflujo, int idusuario) {
		arrendamientoSupervisorDAO.registrarSeguimientoFlujo(segflujo,idusuario);
		
	}

	@Override
	public void actualizarFlujoDocumentoSupervisor(int iddocumento) {
		arrendamientoSupervisorDAO.actualizarFlujoDocumentoSupervisor(iddocumento);
		
	}

	@Override
	public List<Seguimientoflujo> obtenerSeguimientoFlujodeDocumento(
			int idflujoDocumento) {
		// TODO Auto-generated method stub
		return arrendamientoSupervisorDAO.obtenerSeguimientoFlujodeDocumento(idflujoDocumento);
	}

	@Override
	public void actualizarComentarioRespuestaFlujoDocumento(
			int idflujodocumento, String comentarioRechazo,
			List<Seguimientoflujo> seguimientoFlujoDocumento) {
		arrendamientoSupervisorDAO.actualizarComentarioRespuestaFlujoDocumento(idflujodocumento,comentarioRechazo,seguimientoFlujoDocumento);
		
	}

	@Override
	public void regresarDGAI(int idflujodocumento) {
		arrendamientoSupervisorDAO.regresarDGAI(idflujodocumento);
		
	}

	@Override
	public int obtenertamanioFlujoDocumento(int idflujodocumento) {
		// TODO Auto-generated method stub
		return arrendamientoSupervisorDAO.obtenertamanioFlujoDocumento(idflujodocumento);
	}

	@Override
	public Boolean estaDocumentoFinalizado(int idflujodocumento) {
		// TODO Auto-generated method stub
		return arrendamientoSupervisorDAO.estaDocumentoFinalizado(idflujodocumento);
	}

	@Override
	public Boolean estaFlujoFinalizado(int idflujodocumento) {
		// TODO Auto-generated method stub
		return arrendamientoSupervisorDAO.estaFlujoFinalizado(idflujodocumento);
	}

	@Override
	public List<Flujodocumento> getFlujoDocumentosSupervisor(int mesSeleccionado) {
		// TODO Auto-generated method stub
		return arrendamientoSupervisorDAO.getFlujoDocumentosSupervisor(mesSeleccionado);
	}

	@Override
	public List<Flujodocumento> getFlujoDocumentosSupervisorAtendidos(
			int mesSeleccionado) {
		// TODO Auto-generated method stub
		return arrendamientoSupervisorDAO.getFlujoDocumentosSupervisorAtendidos(mesSeleccionado);
	}

	@Override
	public int obtenerNumeroPendientes() {
		// TODO Auto-generated method stub
		return arrendamientoSupervisorDAO.obtenerNumeroPendientes();
	}

	@Override
	public int obtenerNumeroDerivados() {
		// TODO Auto-generated method stub
		return arrendamientoSupervisorDAO.obtenerNumeroDerivados();
	}

	@Override
	public int obtenerNumeroPendientesMes(int mesActualcapturado) {
		// TODO Auto-generated method stub
		return arrendamientoSupervisorDAO.obtenerNumeroPendientesMes(mesActualcapturado);
	}

	@Override
	public int obtenerNumeroDerivadosMes(int mesActualcapturado) {
		// TODO Auto-generated method stub
		return arrendamientoSupervisorDAO.obtenerNumeroDerivadosMes(mesActualcapturado);
	}

	@Override
	public int obtenerNumeroDocumentosMes(String respuesta) {
		// TODO Auto-generated method stub
		return arrendamientoSupervisorDAO.obtenerNumeroDocumentosMes(respuesta);
	}

	@Override
	public int obtenerNumeroDocumentosMes(String respuesta, int mesActualcapturado) {
		// TODO Auto-generated method stub
		return arrendamientoSupervisorDAO.obtenerNumeroDocumentosMes(respuesta,mesActualcapturado);
	}

	@Override
	public List<Flujodocumento> listaFiltroDocumentosMes(String respuesta) {
		// TODO Auto-generated method stub
		return arrendamientoSupervisorDAO.listaFiltroDocumentosMes(respuesta);
	}

	@Override
	public void enviarNotificaciónPersonalizable(
			String contenidoMensajePersonalizado,
			int idusuarioNotificacionPersonalizada) {

		arrendamientoSupervisorDAO.enviarNotificaciónPersonalizable(contenidoMensajePersonalizado,idusuarioNotificacionPersonalizada);
	}

	@Override
	public List<Perfilusuario> getPerfilesUsuario(int idusuario) {
		// TODO Auto-generated method stub
		return arrendamientoSupervisorDAO.getPerfilesUsuario(idusuario);
	}

	@Override
	public List<Perfil> obtenerPerfiles() {
		// TODO Auto-generated method stub
		return arrendamientoSupervisorDAO.obtenerPerfiles();
	}


}

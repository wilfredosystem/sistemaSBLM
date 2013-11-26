package com.sblm.dao;

import java.util.List;

import com.sblm.model.Flujodocumento;
import com.sblm.model.Perfil;
import com.sblm.model.Perfilusuario;
import com.sblm.model.Seguimientoflujo;
import com.sblm.model.Usuario;
import com.sblm.modelMP.Flujodoc;

public interface IRecaudacionSupervisorDAO {

	public List<Flujodocumento> getFlujoDocumentosSupervisor();

	public List<Usuario> obtenerUsuarios();

	public void registrarSeguimientoFlujo(Seguimientoflujo segflujo, int idusuario);

	public void actualizarFlujoDocumentoSupervisor(int iddocumento);

	public List<Flujodocumento> getFlujoDocumentosSupervisorAtendidos();

	public List<Seguimientoflujo> obtenerSeguimientoFlujodeDocumento(
			int idflujoDocumento);

	public void actualizarComentarioRespuestaFlujoDocumento(
			int idflujodocumento, String comentarioRechazo,
			List<Seguimientoflujo> seguimientoFlujoDocumento);

	public void regresarDGAI(int idflujodocumento);

	public int obtenertamanioFlujoDocumento(int idflujodocumento);

	public Boolean estaDocumentoFinalizado(int idflujodocumento);

	public Boolean estaFlujoFinalizado(int idflujodocumento);

	public int obtenerNumeroDocumentosMes(String respuesta,
			int mesActualcapturado);

	public int obtenerNumeroDocumentosMes(String respuesta);

	public int obtenerNumeroDerivadosMes(int mesActualcapturado);

	public int obtenerNumeroPendientesMes(int mesActualcapturado);

	public int obtenerNumeroDerivados();

	public int obtenerNumeroPendientes();

	public List<Flujodocumento> getFlujoDocumentosSupervisorAtendidos(
			int mesSeleccionado);

	public List<Flujodocumento> getFlujoDocumentosSupervisor(int mesSeleccionado);

	public List<Flujodocumento> listaFiltroDocumentosMes(String respuesta);

	public void enviarNotificaciónPersonalizable(
			String contenidoMensajePersonalizado,
			int idusuarioNotificacionPersonalizada);

	public List<Perfilusuario> getPerfilesUsuario(int idusuario);

	public List<Perfil> obtenerPerfiles();



}

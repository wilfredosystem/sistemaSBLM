package com.sblm.service;

import java.util.List;

import com.sblm.model.Documento;
import com.sblm.model.Flujodocumento;
import com.sblm.model.Modulo;
import com.sblm.model.Perfil;
import com.sblm.model.Perfilusuario;
import com.sblm.model.Usuario;

public interface IFlujoDocumentoService {

	public void registrarFlujoDocumento(Flujodocumento flujodocumento);

	public void actualizarFlujoDocumento(Flujodocumento flujodocumento);

	public void eliminarFlujoDocumento(Flujodocumento flujodocumento);

	public Modulo listarFlujoDocumentoPorId(int id);

	public List<Flujodocumento> listarFlujoDocumento();
	
	void actualizarRespuestaToAtendido(int iddoc);//recibido dgai
	public int obtenerNumeroDespachados();
	public int obtenerNumeroPendientes();
	public int obtenerNumeroRechazados();
	public Documento obtenerUltimodocumento();

	public List<Documento> listarDocumentoDerivados();

	public List<Flujodocumento> obtenerFlujodeDocumento(int idDocumento);

	public void actualizarComentarioDocumento(int iddocumento,
			String comentarioRechazo, List<Flujodocumento> flujoDocumentoIndex);

	public void regresarMesaPartes(int iddocumento);


	public int obtenerNumeroPendientesSalida();

	public int obtenerNumeroDerivados();

	public int obtenertamanioFlujoDocumento(int iddocumento);

	public Boolean estaDocumentoFinalizado(int iddocumento);

	public Boolean estaFlujoFinalizado(int iddocumento);

	int obtenerNumeroDocumentos(String val);
	public void enviarNotificaciónPersonalizable(
			String contenidoMensajePersonalizado,
			int idusuarioNotificacionPersonalizada);

	int obtenerNumeroPendientesMes();

	int obtenerNumeroDocumentosMes(String val);

	int obtenerNumeroDerivadosMes();

	int obtenerNumeroPendientesMes(int mes);

	int obtenerNumeroDerivadosMes(int mes);

	
	List<Documento> listaDocumentosSinDerivar(int mes);

	int obtenerNumeroDocumentosMes(String val, int mes);

	List<Documento> listarDocumentoDerivadosMes(int mes);

	public List<Usuario> obtenerUsuarios();

	public List<Perfilusuario> getPerfilesUsuario(int idUsuarioSeleccionado);

	public List<Perfil> obtenerPerfiles();

}

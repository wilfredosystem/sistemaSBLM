package com.sblm.controller;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import com.sblm.model.Archivodocumento;
import com.sblm.model.Seguimientoflujo;
import com.sblm.model.Usuario;
import com.sblm.service.IDocumentoDgaiService;
import com.sblm.service.IRecaudacionCobranzaService;
import com.sblm.util.FuncionesHelper;



@ManagedBean(name = "recaudacioncobranzaMB")
@ViewScoped
public class RecaudacionCobranzaManagedBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{recaudacionCobranzaService}")
	private transient IRecaudacionCobranzaService recaudacionCobranzaService;
	
	@ManagedProperty(value = "#{documentodgaiService}")
	private transient IDocumentoDgaiService documentodgaiService;
	
	@ManagedProperty(value = "#{usuarioMB}")
	UsuarioManagedBean userMB;
	
	private List<Archivodocumento> listaAdjuntoDocumento;
	
	private List<Seguimientoflujo> listSeguimientoFLujoDocumentosAutovaluoPendiente;
	private List<Seguimientoflujo> listSeguimientoFLujoDocumentosAutovaluoAtendido;
	
	private List<Seguimientoflujo> documentosSeleccionadosPendientes;
	private List<Seguimientoflujo> documentosSeleccionadosAtendidos;
	
	private Seguimientoflujo documentosegflujoseleccionado;
	
	private int iddocumentoSeleccionado;
	private String descripcionDocumentoSeleccionado;
	private String comentarioRechazo="Escribir motivo de rechazo";
	private List<Usuario> listaUsuariosRemitentes;
	
	int mesActualcapturado;
	int numeroDerivadosSalida;
	int  numeroPendientesSalida;
	int idusuarioNotificacionPersonalizada;
	/****/
	int numeroAprobados;
	int numeroProceso;
	int numeroRechazados;
	
	int numeroAprobadosMes;
	int numeroProcesoMes;
	int numeroRechazadosMes;
	
	int numeroPendientes;
	int numeroDerivados;
	
	int numeroPendientesMes;
	int numeroDerivadosMes;
	
	String contenidoMensajePersonalizado;
	Boolean visible;
	
	@PostConstruct
	public void initObjects(){

		ejecutar();
		//contadoresEstadoGeneral();
		//contadoresGeneral();
	}
	
	public void name() {
		
	}
	
	public void  ejecutar() {
		
		if (grantedAccess()) {
			getSeguimientoFlujoDocumentosPendientes(0);
			getSeguimientoFlujoDocumentosAtendidos(0);
			contadoresEstadoGeneral(); 
			contadoresGeneral();
		}else {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Advertencia", "No tiene el perfil adecuado, seleccione el perfil correspondiente");  
			FacesContext.getCurrentInstance().addMessage(null, msg);  
		}
	}
	
	
	public Boolean grantedAccess(){
		
		//Accederan Inicialmente todos los que tengan el perfil cobranza
		if(FuncionesHelper.nombreperfilSeleccionado.equals("Cobranza")){
			visible=true;
			System.out.println("GRANTED ACCESS - COBRANZA");
		}else {
			visible=false;
			System.out.println("DENIED ACCESS - COBRANZA");
     	}
		return visible;
		
	}
	
	public void inicializadorPrimerDocumentoSeleccionadoAtendido(){
		
		if(visible){
			if(documentosSeleccionadosPendientes.size()!=0){
				setDescripcionDocumentoSeleccionado(documentosSeleccionadosPendientes.get(0).getFlujodocumento().getDocumento().getDescripcion());
				setIddocumentoSeleccionado(documentosSeleccionadosPendientes.get(0).getFlujodocumento().getDocumento().getIddocumento());
				listaUsuariosRemitentes= new ArrayList<Usuario>();
				
				listaUsuariosRemitentes.add(buscarDatosUsuarioOrigen(documentosSeleccionadosPendientes.get(0).getIdusuarioremitente()));
				cargarArchivosDocumento();
				//obtenerSeguimientoFlujodeDocumento();
			}
		}
	}
	
	public Usuario buscarDatosUsuarioOrigen(int id){
		
		List<Usuario> todosUsuarios= new ArrayList<Usuario>();
		todosUsuarios=userMB.getUsuarios();
		
		for (Usuario usuario : todosUsuarios) {
			if(usuario.getIdusuario()==id) {
				return usuario;
			}
		}
		
		return null;
	}
	
	public void cargarArchivosDocumento(){
		listaAdjuntoDocumento =new ArrayList<Archivodocumento>();
		listaAdjuntoDocumento=documentodgaiService.cargarArchivosDocumento(iddocumentoSeleccionado);
	}
	
	public void cargarArchivosDocumento(int iddoc){
		listaAdjuntoDocumento =new ArrayList<Archivodocumento>();
		listaAdjuntoDocumento=documentodgaiService.cargarArchivosDocumento(iddoc);
	}
	
	 public void regresarSupervisor(){
		 
			for(int i=0;i<documentosSeleccionadosPendientes.size();i++){
				recaudacionCobranzaService.regresarSupervisor(documentosSeleccionadosPendientes.get(i).getIdsegflujodocumento());
			}
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Exito","Se Despacho El expediente correctamente!!!"); 
			FacesContext.getCurrentInstance().addMessage(null, msg);
			getSeguimientoFlujoDocumentosPendientes(0);
			getSeguimientoFlujoDocumentosAtendidos(0);
			
			contadoresEstadoGeneral();
			contadoresGeneral();
	 }
	 
	 public void notificarRechazo(){
			if(getComentarioRechazo()!=null){
				recaudacionCobranzaService.actualizarComentarioRespuestaSeguimientoFlujoDocumento(documentosSeleccionadosPendientes.get(0).getIdsegflujodocumento(), comentarioRechazo,documentosSeleccionadosPendientes.get(0).getIdusuarioremitente());
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Exito","Se Rechazó  expediente correctamente!!!"); 
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}
			else{
				System.out.println("Mensaje de alerta");
			}
			
			//actualizarContadoresDGAI();
			
			getSeguimientoFlujoDocumentosPendientes(0);
			getSeguimientoFlujoDocumentosAtendidos(0);
			contadoresEstadoGeneral();
			contadoresGeneral();
		}
	 
	public void cargarPendientesPorMes(){
		
		if (mesActualcapturado == 0) {
			
			contadoresGeneral();
			getSeguimientoFlujoDocumentosPendientes(mesActualcapturado);
			
		} else {
			
			contadoresMensual();
			getSeguimientoFlujoDocumentosPendientes(mesActualcapturado);
		}
	}
	
	public void cargarDerivadosPorMes(){

		if (mesActualcapturado == 0) {
			
			contadoresEstadoGeneral();
			getSeguimientoFlujoDocumentosAtendidos(mesActualcapturado);
		} else {
			
			contadoresEstadoMensual();
			getSeguimientoFlujoDocumentosAtendidos(mesActualcapturado);
		}
	}
	
	public void verificarFinalizadoRechazo(){
    	
		RequestContext contextRequest = RequestContext.getCurrentInstance(); 
		FacesContext contextFaces = FacesContext.getCurrentInstance();  

		if(documentosSeleccionadosPendientes.size()==1){
			Boolean finalizadoDocumento=recaudacionCobranzaService.estaDocumentoFinalizado(documentosSeleccionadosPendientes.get(0).getIdsegflujodocumento());
			
			if(!finalizadoDocumento){
				contextRequest.execute("dlgRechazar.show();");
			}else{
				contextFaces.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Advertencia", "documento se encuentra ya finalizado "));	
				}
		}
		else{ 
			if(documentosSeleccionadosPendientes.size()>1){
			contextFaces.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"Advertencia", "Seleccionar solo un documento "));	
			}else{
				contextFaces.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"Advertencia", "Seleccione documento "));	
			}
		}
	}
	
	public void verificarFinalizadoDespacho(){
		
		RequestContext contextRequest = RequestContext.getCurrentInstance();  
		FacesContext contextFaces = FacesContext.getCurrentInstance();  

		if(documentosSeleccionadosPendientes.size()!=0){
			Boolean finalizadoDocumento=recaudacionCobranzaService.estaDocumentoFinalizado(documentosSeleccionadosPendientes.get(0).getIdsegflujodocumento());
			
			if(!finalizadoDocumento){
				contextRequest.execute("dlgDespachar.show();");
			}else{
				contextFaces.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Advertencia", "documento se encuentra ya finalizado "));	
				}
		}
		else{
			contextFaces.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"Advertencia", "Seleccione documento "));	
			
		}
	}

	 
		
	private void getSeguimientoFlujoDocumentosAtendidos(int mesSeleccionado) {
		if(visible){
		listSeguimientoFLujoDocumentosAutovaluoAtendido=recaudacionCobranzaService.obtenerSeguimientoFlujodeDocumentoAtendido(mesSeleccionado);
		}
	}

	 
	public void getSeguimientoFlujoDocumentosPendientes(int mesSeleccionado){
		if(visible){
			listSeguimientoFLujoDocumentosAutovaluoPendiente=recaudacionCobranzaService.obtenerSeguimientoFlujodeDocumento(mesSeleccionado);
		}
	}
	
	
	public void contadoresEstadoGeneral(){
		if(visible){
		numeroAprobadosMes=recaudacionCobranzaService.obtenerNumeroDocumentosMes("APROBADO");
		numeroProcesoMes=recaudacionCobranzaService.obtenerNumeroDocumentosMes("EN PROCESO");
		numeroRechazadosMes=recaudacionCobranzaService.obtenerNumeroDocumentosMes("RECHAZADO");
		}
		}
	
	public void contadoresEstadoMensual(){
		numeroAprobadosMes=recaudacionCobranzaService.obtenerNumeroDocumentosMes("APROBADO",mesActualcapturado);
		numeroProcesoMes=recaudacionCobranzaService.obtenerNumeroDocumentosMes("EN PROCESO",mesActualcapturado);
		numeroRechazadosMes=recaudacionCobranzaService.obtenerNumeroDocumentosMes("RECHAZADO",mesActualcapturado);
	}
	
	public void contadoresGeneral(){
		if(visible){
		numeroPendientesMes=recaudacionCobranzaService.obtenerNumeroPendientes();
		numeroDerivadosMes=recaudacionCobranzaService.obtenerNumeroDerivados();
		}
	}
	 
	public void contadoresMensual(){
		numeroPendientesMes=recaudacionCobranzaService.obtenerNumeroPendientesMes(mesActualcapturado);
		numeroDerivadosMes=recaudacionCobranzaService.obtenerNumeroDerivadosMes(mesActualcapturado);
	}
	

	
	//FILTRADO POR MES
	
	public void filtrarAprobadosMes(){
		listSeguimientoFLujoDocumentosAutovaluoAtendido=recaudacionCobranzaService.listaFiltroDocumentosMes("APROBADO");
		
	}
	public void filtrarRechazadosMes(){
		listSeguimientoFLujoDocumentosAutovaluoAtendido=recaudacionCobranzaService.listaFiltroDocumentosMes("RECHAZADO");
	}
	public void filtrarProcesoMes(){
		listSeguimientoFLujoDocumentosAutovaluoAtendido=recaudacionCobranzaService.listaFiltroDocumentosMes("EN PROCESO");
	}
	
	public IRecaudacionCobranzaService getRecaudacionCobranzaService() {
		return recaudacionCobranzaService;
	}

	public void setRecaudacionCobranzaService(
			IRecaudacionCobranzaService recaudacionCobranzaService) {
		this.recaudacionCobranzaService = recaudacionCobranzaService;
	}

	public List<Seguimientoflujo> getListSeguimientoFLujoDocumentosAutovaluoPendiente() {
		return listSeguimientoFLujoDocumentosAutovaluoPendiente;
	}

	public void setListSeguimientoFLujoDocumentosAutovaluoPendiente(
			List<Seguimientoflujo> listSeguimientoFLujoDocumentosAutovaluoPendiente) {
		this.listSeguimientoFLujoDocumentosAutovaluoPendiente = listSeguimientoFLujoDocumentosAutovaluoPendiente;
	}

	public UsuarioManagedBean getUserMB() {
		return userMB;
	}

	public void setUserMB(UsuarioManagedBean userMB) {
		this.userMB = userMB;
	}

	public List<Seguimientoflujo> getDocumentosSeleccionadosPendientes() {
		return documentosSeleccionadosPendientes;
	}

	public void setDocumentosSeleccionadosPendientes(
			List<Seguimientoflujo> documentosSeleccionadosPendientes) {
		this.documentosSeleccionadosPendientes = documentosSeleccionadosPendientes;
	}

	public int getIddocumentoSeleccionado() {
		return iddocumentoSeleccionado;
	}

	public void setIddocumentoSeleccionado(int iddocumentoSeleccionado) {
		this.iddocumentoSeleccionado = iddocumentoSeleccionado;
	}

	public String getDescripcionDocumentoSeleccionado() {
		return descripcionDocumentoSeleccionado;
	}

	public void setDescripcionDocumentoSeleccionado(
			String descripcionDocumentoSeleccionado) {
		this.descripcionDocumentoSeleccionado = descripcionDocumentoSeleccionado;
	}

	public String getComentarioRechazo() {
		return comentarioRechazo;
	}

	public void setComentarioRechazo(String comentarioRechazo) {
		this.comentarioRechazo = comentarioRechazo;
	}

	public List<Usuario> getListaUsuariosRemitentes() {
		return listaUsuariosRemitentes;
	}

	public void setListaUsuariosRemitentes(List<Usuario> listaUsuariosRemitentes) {
		this.listaUsuariosRemitentes = listaUsuariosRemitentes;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public IDocumentoDgaiService getDocumentodgaiService() {
		return documentodgaiService;
	}

	public void setDocumentodgaiService(
			IDocumentoDgaiService documentodgaiService) {
		this.documentodgaiService = documentodgaiService;
	}

	public List<Archivodocumento> getListaAdjuntoDocumento() {
		return listaAdjuntoDocumento;
	}

	public void setListaAdjuntoDocumento(
			List<Archivodocumento> listaAdjuntoDocumento) {
		this.listaAdjuntoDocumento = listaAdjuntoDocumento;
	}

	public List<Seguimientoflujo> getListSeguimientoFLujoDocumentosAutovaluoAtendido() {
		return listSeguimientoFLujoDocumentosAutovaluoAtendido;
	}

	public void setListSeguimientoFLujoDocumentosAutovaluoAtendido(
			List<Seguimientoflujo> listSeguimientoFLujoDocumentosAutovaluoAtendido) {
		this.listSeguimientoFLujoDocumentosAutovaluoAtendido = listSeguimientoFLujoDocumentosAutovaluoAtendido;
	}

	public List<Seguimientoflujo> getDocumentosSeleccionadosAtendidos() {
		return documentosSeleccionadosAtendidos;
	}

	public void setDocumentosSeleccionadosAtendidos(
			List<Seguimientoflujo> documentosSeleccionadosAtendidos) {
		this.documentosSeleccionadosAtendidos = documentosSeleccionadosAtendidos;
	}

	public int getMesActualcapturado() {
		return mesActualcapturado;
	}

	public void setMesActualcapturado(int mesActualcapturado) {
		this.mesActualcapturado = mesActualcapturado;
	}

	public int getNumeroDerivadosSalida() {
		return numeroDerivadosSalida;
	}

	public void setNumeroDerivadosSalida(int numeroDerivadosSalida) {
		this.numeroDerivadosSalida = numeroDerivadosSalida;
	}

	public int getNumeroPendientesSalida() {
		return numeroPendientesSalida;
	}

	public void setNumeroPendientesSalida(int numeroPendientesSalida) {
		this.numeroPendientesSalida = numeroPendientesSalida;
	}

	public int getIdusuarioNotificacionPersonalizada() {
		return idusuarioNotificacionPersonalizada;
	}

	public void setIdusuarioNotificacionPersonalizada(
			int idusuarioNotificacionPersonalizada) {
		this.idusuarioNotificacionPersonalizada = idusuarioNotificacionPersonalizada;
	}

	public int getNumeroAprobados() {
		return numeroAprobados;
	}

	public void setNumeroAprobados(int numeroAprobados) {
		this.numeroAprobados = numeroAprobados;
	}

	public int getNumeroProceso() {
		return numeroProceso;
	}

	public void setNumeroProceso(int numeroProceso) {
		this.numeroProceso = numeroProceso;
	}

	public int getNumeroRechazados() {
		return numeroRechazados;
	}

	public void setNumeroRechazados(int numeroRechazados) {
		this.numeroRechazados = numeroRechazados;
	}

	public int getNumeroAprobadosMes() {
		return numeroAprobadosMes;
	}

	public void setNumeroAprobadosMes(int numeroAprobadosMes) {
		this.numeroAprobadosMes = numeroAprobadosMes;
	}

	public int getNumeroProcesoMes() {
		return numeroProcesoMes;
	}

	public void setNumeroProcesoMes(int numeroProcesoMes) {
		this.numeroProcesoMes = numeroProcesoMes;
	}

	public int getNumeroRechazadosMes() {
		return numeroRechazadosMes;
	}

	public void setNumeroRechazadosMes(int numeroRechazadosMes) {
		this.numeroRechazadosMes = numeroRechazadosMes;
	}

	public int getNumeroPendientes() {
		return numeroPendientes;
	}

	public void setNumeroPendientes(int numeroPendientes) {
		this.numeroPendientes = numeroPendientes;
	}

	public int getNumeroDerivados() {
		return numeroDerivados;
	}

	public void setNumeroDerivados(int numeroDerivados) {
		this.numeroDerivados = numeroDerivados;
	}

	public int getNumeroPendientesMes() {
		return numeroPendientesMes;
	}

	public void setNumeroPendientesMes(int numeroPendientesMes) {
		this.numeroPendientesMes = numeroPendientesMes;
	}

	public int getNumeroDerivadosMes() {
		return numeroDerivadosMes;
	}

	public void setNumeroDerivadosMes(int numeroDerivadosMes) {
		this.numeroDerivadosMes = numeroDerivadosMes;
	}

	public String getContenidoMensajePersonalizado() {
		return contenidoMensajePersonalizado;
	}

	public void setContenidoMensajePersonalizado(
			String contenidoMensajePersonalizado) {
		this.contenidoMensajePersonalizado = contenidoMensajePersonalizado;
	}

	public Seguimientoflujo getDocumentosegflujoseleccionado() {
		return documentosegflujoseleccionado;
	}

	public void setDocumentosegflujoseleccionado(
			Seguimientoflujo documentosegflujoseleccionado) {
		this.documentosegflujoseleccionado = documentosegflujoseleccionado;
	}
	
	
	
}

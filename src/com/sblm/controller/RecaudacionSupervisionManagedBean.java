package com.sblm.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.ToggleEvent;
import com.sblm.model.Archivodocumento;
import com.sblm.model.Area;
import com.sblm.model.Flujodocumento;
import com.sblm.model.Perfil;
import com.sblm.model.Perfilusuario;
import com.sblm.model.Seguimientoflujo;
import com.sblm.model.Usuario;
import com.sblm.service.IDocumentoDgaiService;
import com.sblm.service.IRecaudacionSupervisionService;
import com.sblm.util.Correo;
import com.sblm.util.FuncionesHelper;



@ManagedBean(name = "recaudacionsupervisionMB")
@ViewScoped
public class RecaudacionSupervisionManagedBean implements Serializable {
	
	@ManagedProperty(value = "#{recaudacionSupervisionService}")
	private transient IRecaudacionSupervisionService recaudacionSupervisionService;
	
	@ManagedProperty(value = "#{documentodgaiService}")
	private transient IDocumentoDgaiService documentodgaiService;


	private static final long serialVersionUID = 2357552147489612469L;
	private List<Flujodocumento> listFLujoDocumentosSupervisorPendiente;
	
	private List<Flujodocumento> listFLujoDocumentosSupervisorAtendido;
	
	
	private List<Archivodocumento> listaAdjuntoDocumento;
	
	private List<Usuario> listadousuariosSeleccionados;
	private Flujodocumento documentoSeleccionadoDerivacion;
	private List<Flujodocumento> documentosSeleccionadosDerivacion;
	private List<Flujodocumento> documentosSeleccionadosAtendidos;
	
	private Flujodocumento documentoflujoseleccionado;
	
	private List<Seguimientoflujo> seguimientoFlujoDocumento;
	
	private int iddocumentoSeleccionado;
	private String descripcionDocumentoSeleccionado;
	private String comentarioRechazo="Escribir motivo de rechazo";
	private List<Usuario> listaUsuariosRemitentes;
	
	private List<Perfilusuario> perfilesusuario;
	private Usuario selectRegistroUsuario;
	List<Usuario> todosUsuarios;
	List<Usuario> listaUsuariosEnvio;
	
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
	
	private int  idPerfilSeleccionado;
	private int idUsuarioSeleccionado;
	
	private int usuarioGeneral;
	
	private int indicesalvador;
	private List<Perfil> listaPerfiles;
	
	UsuarioManagedBean userMB;
	Boolean visible;
	
	@PostConstruct
	public void initObjects(){
		ejecutar();
	}
	
	
	public void ejecutar(){
		if (grantedAccess()) {
			getFlujoDocumentosSupervisor(0);
			getFlujoDocumentosSupervisorAtendidos(0);
			contadoresEstadoGeneral();
			contadoresGeneral();
			ObjectsInitialization();
			
		}else {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Advertencia", "No tiene permiso suficiente o seleccione el perfil correspondiente");  
			FacesContext.getCurrentInstance().addMessage(null, msg);  
		}
		
	}
	
	public void ObjectsInitialization(){
		todosUsuarios=recaudacionSupervisionService.obtenerUsuarios();
		perfilesusuario= new ArrayList<Perfilusuario>();
		listaUsuariosEnvio = new ArrayList<Usuario>();
		listadousuariosSeleccionados= new ArrayList<Usuario>();
		
		listaPerfiles= new ArrayList<Perfil>();
		listaPerfiles= recaudacionSupervisionService.obtenerPerfiles();
		
	}
	
	public Boolean grantedAccess(){
		
		if(FuncionesHelper.nombreperfilSeleccionado.equals("Supervisor Recaudación")){
			visible=true;
			System.out.println("GRANTED ACCESS - RECAUDACION SUPERVISOR");
		}else {
			visible=false;
			System.out.println("DENIED ACCESS - RECAUDACION SUPERVISOR");
		}
			return visible;
	}


	
	public void agregarUsuarioLista(){
		boolean usuarioVacio = false;

		if (!usuarioVacio) {
		}
			Usuario Usu = new Usuario();
			Usu.setEmailusr("Escriba Nombre Usuario");
			Usu.setRutaimgusr("default.jpg");
			Usu.setContrasenausr("deleteUsuario.png");
			listadousuariosSeleccionados.add(Usu);
			setIndicesalvador(listadousuariosSeleccionados.size()-1);
			
		
		}
	
	public void onCellEdit(CellEditEvent event) { 
		
		
		if(Integer.parseInt(event.getColumn().getWidth())==99){
	        Object newValue = event.getNewValue();
	        
	        
	        FacesContext contextFaces = FacesContext.getCurrentInstance();  

	       
	       //buscamos id usuario
	       int id = 0;
	       for(int k=0;k<todosUsuarios.size();k++){	
				if((todosUsuarios.get(k).getNombrescompletos()).equals(newValue)){
					id=k;
				}

			}

			boolean flag = true;
		

			for (int i = 0; i < listadousuariosSeleccionados.size() - 1; i++) {
				System.out.println("i=" + i);
				if ((listadousuariosSeleccionados.get(i).getEmailusr())
						.equals(newValue)) { 
					
					contextFaces.addMessage(null, new FacesMessage(
							FacesMessage.SEVERITY_WARN, "Advertencia",
							"usuario ya se encuentra en la lista"));
					listadousuariosSeleccionados.remove(getIndicesalvador());
					flag = false;

				}
			}

			if (flag) {
				System.out.println("nuevo");
				listadousuariosSeleccionados.get(getIndicesalvador()).setRutaimgusr(todosUsuarios.get(id).getRutaimgusr());
				listadousuariosSeleccionados.get(getIndicesalvador()).setCargo(todosUsuarios.get(id).getCargo());
				listadousuariosSeleccionados.get(getIndicesalvador()).setIdusuario(todosUsuarios.get(id).getIdusuario());
				listadousuariosSeleccionados.get(getIndicesalvador()).setUsrmod("Ingrese Perfil");
				
				Area A= new Area();
				A.setDesare(todosUsuarios.get(id).getArea().getDesare());
				listadousuariosSeleccionados.get(getIndicesalvador()).setArea(A);
				setIdUsuarioSeleccionado(todosUsuarios.get(id).getIdusuario());
				
				buscarPerfilUsuario();
			}
			
		}else{
	        Object newValue = event.getNewValue();
			
			listadousuariosSeleccionados.get(getIndicesalvador()).setUsrmod(newValue.toString());
			
		}

		
    }
	
	public List<String> autoCompleteUsuario(String query){
		
		List<String> result = new ArrayList<String>();
		 
		
		for(Usuario usu : (List<Usuario>)todosUsuarios){
			
			String nomComplusu=usu.getNombrescompletos();
			
			if(nomComplusu.toLowerCase().contains(query.toLowerCase())){
				result.add(nomComplusu);
			}
		}
		
		return result;
		
		}

	
	public void buscarPerfilUsuario(){
		
		
		perfilesusuario.clear();
		
		perfilesusuario=recaudacionSupervisionService.getPerfilesUsuario(getIdUsuarioSeleccionado());
		
		if(!perfilesusuario.isEmpty()){
		}else
			System.out.println("No tiene ningun perfil");
		
	}	

	
	
	public void cargarArchivosDocumento(){
		
		listaAdjuntoDocumento =new ArrayList<Archivodocumento>();
		listaAdjuntoDocumento=documentodgaiService.cargarArchivosDocumento(iddocumentoSeleccionado);
		
	}
	
	public void cargarArchivosDocumento(int iddoc){
		listaAdjuntoDocumento =new ArrayList<Archivodocumento>();
		listaAdjuntoDocumento=documentodgaiService.cargarArchivosDocumento(iddoc);
		
	
		
	}
	
	public void inicializadorPrimerDocumentoSeleccionadoPendiente(){
		if(documentosSeleccionadosDerivacion.size()!=0){
			setDescripcionDocumentoSeleccionado(documentosSeleccionadosDerivacion.get(0).getDocumento().getDescripcion());
			setIddocumentoSeleccionado(documentosSeleccionadosDerivacion.get(0).getDocumento().getIddocumento());
			cargarArchivosDocumento();
		}
		
	}
	
	public void inicializadorPrimerDocumentoSeleccionadoAtendido(){
		
		if(documentosSeleccionadosAtendidos.size()!=0){
			setDescripcionDocumentoSeleccionado(documentosSeleccionadosAtendidos.get(0).getDocumento().getDescripcion());
			setIddocumentoSeleccionado(documentosSeleccionadosAtendidos.get(0).getDocumento().getIddocumento());
			listaUsuariosRemitentes= new ArrayList<Usuario>();
			
			listaUsuariosRemitentes.add(buscarDatosUsuarioOrigen(documentosSeleccionadosAtendidos.get(0).getIdusuarioremitente()));
			cargarArchivosDocumento();
			obtenerSeguimientoFlujodeDocumento();
			
		}
		
		
	}
	

	
	public void registrarSeguimientoFlujo(){
		
		for (int i = 0; i < documentosSeleccionadosDerivacion.size(); i++) {
			recaudacionSupervisionService.actualizarFlujoDocumentoSupervisor(documentosSeleccionadosDerivacion.get(i).getIdflujodocumento());


			for (Usuario user : listadousuariosSeleccionados) {
				
				Correo correo =new Correo();
				String msj="Estimado Sr(a). <b> "+user.getNombres()+" "+user.getApellidopat()+"</b> <br /><br />"+
						"Se le ha derivado el <a href='http://192.168.1.10:8081/sistemaSBLM/pages/pgRecaudacionSupervisor.jsf'>"+documentosSeleccionadosDerivacion.get(i).getDocumento().getTitulo()+"</a><br /><br />"+
						"Atte <br />"+
						"Equipo SGI <br />"+
						"Oficina de Informática <br />"+
						"<b>Sociedad de Beneficencia de Lima</b>";
						correo.enviarCorreo(user.getEmailusr(), "Notificacion de Expediente",msj);
				
				Flujodocumento flujo= new Flujodocumento();
				flujo.setIdflujodocumento(documentosSeleccionadosDerivacion.get(i).getIdflujodocumento());
				
				Seguimientoflujo segflujo= new Seguimientoflujo();
				segflujo.setEstado("NINGUNO");
				segflujo.setFecharecepcion(new  Date());
				segflujo.setFlujodocumento(flujo);
				segflujo.setIdusuarioremitente((Integer)FuncionesHelper.getUsuario());
				segflujo.setRespuesta("EN PROCESO");
				segflujo.setUsuario(user);
				segflujo.setIdperfilusuario(retornarIdPerfilPorNombre((user.getUsrmod())));
				recaudacionSupervisionService.registrarSeguimientoFlujo(segflujo,user.getIdusuario());
			}
		
		}
		
		getFlujoDocumentosSupervisor(0);
		getFlujoDocumentosSupervisorAtendidos(0);
		
	}
	
	public int retornarIdPerfilPorNombre(String nombrePerfil){
		
		for (Perfil perfil : listaPerfiles) {
			
			if (perfil.getNombreperfil().equals(nombrePerfil)) {
				return perfil.getIdperfil();
			}
			
		}
		
		return 0;
	}
	
	public Usuario buscarDatosUsuarioOrigen(int id){
		
		for (Usuario usuario : todosUsuarios) {
			if(usuario.getIdusuario()==id) {
				return usuario;
			}
		}
		
		return null;
	}
	
    public void obtenerSeguimientoFlujodeDocumento(){
    	
    	seguimientoFlujoDocumento = new ArrayList<Seguimientoflujo>();
    	int idflujoDocumento;
    	
    	if(documentosSeleccionadosAtendidos.size()!=0){
    		idflujoDocumento=documentosSeleccionadosAtendidos.get(0).getIdflujodocumento();
    		seguimientoFlujoDocumento=recaudacionSupervisionService.obtenerSeguimientoFlujodeDocumento(idflujoDocumento);
    	
       } 
    	
    }

	public void notificarRechazo(){
		if(getComentarioRechazo()!=null){
			recaudacionSupervisionService.actualizarComentarioRespuestaFlujoDocumento(documentosSeleccionadosAtendidos.get(0).getIdflujodocumento(), comentarioRechazo,seguimientoFlujoDocumento);
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Exito","Se Rechazó  expediente correctamente!!!"); 
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		else{
			System.out.println("Mensaje de alerta");
		}
		
		//actualizarContadoresDGAI();
		
		getFlujoDocumentosSupervisorAtendidos(0);
	}
	
	 public void regresarDGAI(){
		 
		 
			for(int i=0;i<documentosSeleccionadosAtendidos.size();i++){
				recaudacionSupervisionService.regresarDGAI(documentosSeleccionadosAtendidos.get(i).getIdflujodocumento());
			}
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Exito","Se Despacho El expediente correctamente!!!"); 
			FacesContext.getCurrentInstance().addMessage(null, msg);
			getFlujoDocumentosSupervisorAtendidos(0);
	 }
	 
		public void cargarPendientesPorMes(){
				
				if (mesActualcapturado == 0) {
					contadoresGeneral();
					getFlujoDocumentosSupervisor(mesActualcapturado);
				} else {
					contadoresMensual();
					getFlujoDocumentosSupervisor(mesActualcapturado);
				}
			}
		
		public void cargarDerivadosPorMes(){

				if (mesActualcapturado == 0) {
					contadoresEstadoGeneral();
					getFlujoDocumentosSupervisorAtendidos(mesActualcapturado);
				} else {
					contadoresEstadoMensual();
					getFlujoDocumentosSupervisorAtendidos(mesActualcapturado);
				}
			}
		
		public void contadoresEstadoGeneral(){
			numeroAprobadosMes=recaudacionSupervisionService.obtenerNumeroDocumentosMes("APROBADO");
			numeroProcesoMes=recaudacionSupervisionService.obtenerNumeroDocumentosMes("EN PROCESO");
			numeroRechazadosMes=recaudacionSupervisionService.obtenerNumeroDocumentosMes("RECHAZADO");
		}
		
		public void contadoresEstadoMensual(){
			numeroAprobadosMes=recaudacionSupervisionService.obtenerNumeroDocumentosMes("APROBADO",mesActualcapturado);
			numeroProcesoMes=recaudacionSupervisionService.obtenerNumeroDocumentosMes("EN PROCESO",mesActualcapturado);
			numeroRechazadosMes=recaudacionSupervisionService.obtenerNumeroDocumentosMes("RECHAZADO",mesActualcapturado);
		}
		
		public void contadoresGeneral(){
			numeroPendientesMes=recaudacionSupervisionService.obtenerNumeroPendientes();
			numeroDerivadosMes=recaudacionSupervisionService.obtenerNumeroDerivados();
		}
		 
		public void contadoresMensual(){
			numeroPendientesMes=recaudacionSupervisionService.obtenerNumeroPendientesMes(mesActualcapturado);
			numeroDerivadosMes=recaudacionSupervisionService.obtenerNumeroDerivadosMes(mesActualcapturado);
		}
		
		//FILTRADO POR MES
		
		public void filtrarAprobadosMes(){
			listFLujoDocumentosSupervisorAtendido=recaudacionSupervisionService.listaFiltroDocumentosMes("APROBADO");
		}
		public void filtrarRechazadosMes(){
			listFLujoDocumentosSupervisorAtendido=recaudacionSupervisionService.listaFiltroDocumentosMes("RECHAZADO");
		}
		public void filtrarProcesoMes(){
			listFLujoDocumentosSupervisorAtendido=recaudacionSupervisionService.listaFiltroDocumentosMes("EN PROCESO");
		}
		
	 public void verificarItemSeleccionado(){
	 		
	 		
	 		RequestContext contextRequest = RequestContext.getCurrentInstance(); 
	 		FacesContext contextFaces = FacesContext.getCurrentInstance(); 
	 		
	 		if(documentosSeleccionadosDerivacion.size()!=0){
	 			contextRequest.execute("dlgDerivacion.show();");	
	 		}else {
	 			contextFaces.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"Advertencia", "Seleccione al menos un documento "));
	 		}
	 		
	     }
	 
	 public void handleToggle(ToggleEvent event) {  
	        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Fieldset Toggled", "Visibility:" + event.getVisibility());  
	        FacesContext.getCurrentInstance().addMessage(null, msg);  
	    }  
	 
	public void verificarExistenciaSeguimientoFlujoRechazo(){
	    	
			RequestContext contextRequest = RequestContext.getCurrentInstance(); 
			FacesContext contextFaces = FacesContext.getCurrentInstance();  

			if(documentosSeleccionadosAtendidos.size()==1){
				int tamanioFlujoDocumento=recaudacionSupervisionService.obtenertamanioFlujoDocumento(documentosSeleccionadosAtendidos.get(0).getIdflujodocumento());
				Boolean finalizadoDocumento=recaudacionSupervisionService.estaDocumentoFinalizado(documentosSeleccionadosAtendidos.get(0).getIdflujodocumento());
				Boolean finalizadoFlujo=recaudacionSupervisionService.estaFlujoFinalizado(documentosSeleccionadosAtendidos.get(0).getIdflujodocumento());
				
				if(tamanioFlujoDocumento==0 && !finalizadoDocumento){
					contextRequest.execute("dlgRechazar.show();");
				}else{
					if(finalizadoDocumento ){
					contextFaces.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Advertencia", "documento se encuentra ya finalizado "));	
					}else{ 
						if(!finalizadoFlujo){
					contextFaces.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Advertencia", "documento a la espera de respuesta"));		
										   }
						else{
							   contextRequest.execute("dlgRechazar.show();");
						   		}
						
					}
					}
			}
			else{ 
				if(documentosSeleccionadosAtendidos.size()>1){
				contextFaces.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"Advertencia", "Seleccionar solo un documento "));	
				}else{
					contextFaces.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"Advertencia", "Seleccione documento "));	
				}
				}
		}
		
		public void verificarExistenciaSeguimientpoFlujoDespacho(){
			
			RequestContext contextRequest = RequestContext.getCurrentInstance(); 
			FacesContext contextFaces = FacesContext.getCurrentInstance();  
			
			if(documentosSeleccionadosAtendidos.size()!=0){
				int tamanioFlujoDocumento=recaudacionSupervisionService.obtenertamanioFlujoDocumento(documentosSeleccionadosAtendidos.get(0).getIdflujodocumento());
				Boolean finalizado=recaudacionSupervisionService.estaDocumentoFinalizado(documentosSeleccionadosAtendidos.get(0).getIdflujodocumento());
				Boolean finalizadoFlujo=recaudacionSupervisionService.estaFlujoFinalizado(documentosSeleccionadosAtendidos.get(0).getIdflujodocumento());
				
				if(tamanioFlujoDocumento==0 && !finalizado){
					contextRequest.execute("dlgDespachar.show();");
				}else{
					if(finalizado){
					contextFaces.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Advertencia", "Seleccione sólo documentos en proceso"));	
					}else{
						if(!finalizadoFlujo){
					contextFaces.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Advertencia", "Documento a la espera de respuesta"));		
										   }
						else{
							   contextRequest.execute("dlgDespachar.show();");
						   		}
					}
					}
			}
			else{
				contextFaces.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"Advertencia", "Seleccione documento "));	
			}
		}
	
	
		
    public void onRowToggle(ToggleEvent event) {
    	obtenerSeguimientoFlujodeDocumento();	
    }

	
	

	public void enviarNotificaciónPersonalizable(){
		
		recaudacionSupervisionService.enviarNotificaciónPersonalizable(getContenidoMensajePersonalizado(),getIdusuarioNotificacionPersonalizada());
	
			Correo correo =new Correo();
			String msj="Estimado Sr(a). <b> "+obtenerNombresXid(getIdusuarioNotificacionPersonalizada())+"</b> <br /><br />"+
			"Ha recibido el siguiente mensaje :"+"<br /><br />"+
			getContenidoMensajePersonalizado()+"<br /><br /><br /><br />"+
			"Atte <br />"+userMB.getUsuariologueado().getNombres()+" "+userMB.getUsuariologueado().getApellidopat()+
			"<br />"+
			"Oficina de Informática <br />"+
			"<b>Sociedad de Beneficencia de Lima</b>";
			correo.enviarCorreo(obtenerCorreoXid(getIdusuarioNotificacionPersonalizada()),"Mensaje",msj);
		
	}
	
	
	public void getFlujoDocumentosSupervisor(int mesSeleccionado){
		listFLujoDocumentosSupervisorPendiente=recaudacionSupervisionService.getFlujoDocumentosSupervisor(mesSeleccionado);
	}
	
	
	public void getFlujoDocumentosSupervisorAtendidos(int mesSeleccionado){
		listFLujoDocumentosSupervisorAtendido=recaudacionSupervisionService.getFlujoDocumentosSupervisorAtendidos(mesSeleccionado);
	}
	
	public void inicializarNotificaciónPersonalizable(int id){
		setIdusuarioNotificacionPersonalizada(id);
	}
    
	public IRecaudacionSupervisionService getRecaudacionSupervisionService() {
		return recaudacionSupervisionService;
	}

	
	public void setRecaudacionSupervisionService(
			IRecaudacionSupervisionService recaudacionSupervisionService) {
		this.recaudacionSupervisionService = recaudacionSupervisionService;
	}
	

	public List<Usuario> getListadousuariosSeleccionados() {
		return listadousuariosSeleccionados;
	}
	


	public void setListadousuariosSeleccionados(
			List<Usuario> listadousuariosSeleccionados) {
		this.listadousuariosSeleccionados = listadousuariosSeleccionados;
	}



	public List<Flujodocumento> getDocumentosSeleccionadosDerivacion() {
		return documentosSeleccionadosDerivacion;
	}


	public void setDocumentosSeleccionadosDerivacion(
			List<Flujodocumento> documentosSeleccionadosDerivacion) {
		this.documentosSeleccionadosDerivacion = documentosSeleccionadosDerivacion;
	}


	public IDocumentoDgaiService getDocumentodgaiService() {
		return documentodgaiService;
	}


	public void setDocumentodgaiService(IDocumentoDgaiService documentodgaiService) {
		this.documentodgaiService = documentodgaiService;
	}


	public List<Archivodocumento> getListaAdjuntoDocumento() {
		return listaAdjuntoDocumento;
	}


	public void setListaAdjuntoDocumento(
			List<Archivodocumento> listaAdjuntoDocumento) {
		this.listaAdjuntoDocumento = listaAdjuntoDocumento;
	}


	public Flujodocumento getDocumentoSeleccionadoDerivacion() {
		return documentoSeleccionadoDerivacion;
	}


	public void setDocumentoSeleccionadoDerivacion(
			Flujodocumento documentoSeleccionadoDerivacion) {
		this.documentoSeleccionadoDerivacion = documentoSeleccionadoDerivacion;
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

	public List<Flujodocumento> getListFLujoDocumentosSupervisorPendiente() {
		return listFLujoDocumentosSupervisorPendiente;
	}

	public void setListFLujoDocumentosSupervisorPendiente(
			List<Flujodocumento> listFLujoDocumentosSupervisorPendiente) {
		this.listFLujoDocumentosSupervisorPendiente = listFLujoDocumentosSupervisorPendiente;
	}



	public List<Flujodocumento> getListFLujoDocumentosSupervisorAtendido() {
		return listFLujoDocumentosSupervisorAtendido;
	}



	public void setListFLujoDocumentosSupervisorAtendido(
			List<Flujodocumento> listFLujoDocumentosSupervisorAtendido) {
		this.listFLujoDocumentosSupervisorAtendido = listFLujoDocumentosSupervisorAtendido;
	}



	public List<Flujodocumento> getDocumentosSeleccionadosAtendidos() {
		return documentosSeleccionadosAtendidos;
	}



	public void setDocumentosSeleccionadosAtendidos(
			List<Flujodocumento> documentosSeleccionadosAtendidos) {
		this.documentosSeleccionadosAtendidos = documentosSeleccionadosAtendidos;
	}



	public List<Seguimientoflujo> getSeguimientoFlujoDocumento() {
		return seguimientoFlujoDocumento;
	}



	public void setSeguimientoFlujoDocumento(
			List<Seguimientoflujo> seguimientoFlujoDocumento) {
		this.seguimientoFlujoDocumento = seguimientoFlujoDocumento;
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


	public Flujodocumento getDocumentoflujoseleccionado() {
		return documentoflujoseleccionado;
	}


	public void setDocumentoflujoseleccionado(
			Flujodocumento documentoflujoseleccionado) {
		this.documentoflujoseleccionado = documentoflujoseleccionado;
	}

	public List<Perfilusuario> getPerfilesusuario() {
		return perfilesusuario;
	}

	public void setPerfilesusuario(List<Perfilusuario> perfilesusuario) {
		this.perfilesusuario = perfilesusuario;
	}



	public List<Usuario> getTodosUsuarios() {
		return todosUsuarios;
	}

	public void setTodosUsuarios(List<Usuario> todosUsuarios) {
		this.todosUsuarios = todosUsuarios;
	}


	public int getUsuarioGeneral() {
		return usuarioGeneral;
	}

	public void setUsuarioGeneral(int usuarioGeneral) {
		this.usuarioGeneral = usuarioGeneral;
	}

	public List<Usuario> getListaUsuariosEnvio() {
		return listaUsuariosEnvio;
	}

	public void setListaUsuariosEnvio(List<Usuario> listaUsuariosEnvio) {
		this.listaUsuariosEnvio = listaUsuariosEnvio;
	}

	public int getIdPerfilSeleccionado() {
		return idPerfilSeleccionado;
	}

	public void setIdPerfilSeleccionado(int idPerfilSeleccionado) {
		this.idPerfilSeleccionado = idPerfilSeleccionado;
	}

	public int getIdUsuarioSeleccionado() {
		return idUsuarioSeleccionado;
	}

	public void setIdUsuarioSeleccionado(int idUsuarioSeleccionado) {
		this.idUsuarioSeleccionado = idUsuarioSeleccionado;
	}

	public int getIndicesalvador() {
		return indicesalvador;
	}

	public void setIndicesalvador(int indicesalvador) {
		this.indicesalvador = indicesalvador;
	}

	public void eliminarUsuarioDeLista(ActionEvent event) {
		
		for(int i=0;i<listadousuariosSeleccionados.size();i++){
			
			if(listadousuariosSeleccionados.get(i)==getSelectRegistroUsuario()){
				listadousuariosSeleccionados.remove(i);
			}
		}

		}

	public Usuario getSelectRegistroUsuario() {
		return selectRegistroUsuario;
	}

	public void setSelectRegistroUsuario(Usuario selectRegistroUsuario) {
		this.selectRegistroUsuario = selectRegistroUsuario;
	}

	public UsuarioManagedBean getUserMB() {
		return userMB;
	}

	public void setUserMB(UsuarioManagedBean userMB) {
		this.userMB = userMB;
	}
	
	public String obtenerCorreoXid(int id){
		String correo = "";
		
		for(int i=0;i<todosUsuarios.size();i++){
			if(todosUsuarios.get(i).getIdusuario()==getIdusuarioNotificacionPersonalizada()){
				
			correo=	todosUsuarios.get(i).getEmailusr();
			}
			
		}
		
		return correo;
	}
	
	public String obtenerNombresXid(int id){
		String nomApe = "";
		
		for(int i=0;i<todosUsuarios.size();i++){
			if(todosUsuarios.get(i).getIdusuario()==getIdusuarioNotificacionPersonalizada()){
				
			nomApe=	todosUsuarios.get(i).getNombres()+" "+todosUsuarios.get(i).getApellidopat();
			}
		}
		
		return nomApe;
	}

}

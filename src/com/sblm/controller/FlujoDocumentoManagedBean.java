package com.sblm.controller;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.sblm.model.Archivodocumento;
import com.sblm.model.Area;
import com.sblm.model.Documento;
import com.sblm.model.Flujodocumento;
import com.sblm.model.Perfil;
import com.sblm.model.Perfilusuario;
import com.sblm.model.Usuario;
import com.sblm.service.IDocumentoDgaiService;
import com.sblm.service.IFlujoDocumentoService;
import com.sblm.util.CompDataModelFlujoDocumento;
import com.sblm.util.Correo;
import com.sblm.util.FuncionesHelper;
@ManagedBean(name = "flujodocumentoMB")
@ViewScoped
public class FlujoDocumentoManagedBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{flujodocumentoService}")
	private transient IFlujoDocumentoService flujodocumentoService;
	
	@ManagedProperty(value = "#{documentodgaiService}")
	private transient IDocumentoDgaiService documentodgaiService;
	
	
	private Flujodocumento flujodocumento;
	private List<Flujodocumento> flujodocumentos;
	private Documento documentoenviado;
	
	@ManagedProperty(value = "#{usuarioMB}")
	UsuarioManagedBean userMB;
	
	@ManagedProperty(value = "#{documentodgaiMB}")
	DocumentoDgaiManagedBean documentodgaiMB;
	
	
//	@ManagedProperty(value = "#{cmonitoreomesapartes}")
//	 monitoreoMesaPartesController cmonitoreomesapartes;
	Usuario []listadousuarios;
	int numeroDespachados;
	
	//int numeroRechazados;
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
	
	private int idUsuarioSeleccionado;
	private int indicesalvador;
	
	
	int mesActualcapturado;
//	private String ultimoAsunto;
	private Flujodocumento[] listadocumentosrechazados;
	private Flujodocumento documentocapturado;

	private List<Flujodocumento> flujoDocumentoIndex;
	private List<Flujodocumento> usuarioSeleccionadosfljDoc;
	private Documento  selectDocumento;
	private List<Documento> documentoDerivados;
	private List<Documento> listaDerivadosSeleccionados;
	private CompDataModelFlujoDocumento compDataModelFlujoDocumento;
	private String comentarioRechazo="Escribir motivo de rechazo";
	private List<Archivodocumento> listaAdjuntoDocumentoDerivados;
	
	
	private String  descripcionDocumentoDespacho;
	private int idDescripcionDocumentoDespacho;
	
	private int iddocSeleccionadoDerivados;
	private StreamedContent file;
	private String nombreArchivoSeleccionado;
	
	private List<Usuario> todosUsuarios;
	private List<Perfilusuario> perfilesusuario;
	private Usuario selectRegistroUsuario;
	private List<Usuario> listadousuariosSeleccionados;
	private List<Perfil> listaPerfiles;
	
	@ManagedProperty(value = "#{cmonitoreomesapartes}")
	monitoreoMesaPartesController mesaPartesMB;
	
	
	@PostConstruct
	public void initObjects(){
		
		listarDocumentosDerivados();
		actualizarContadoresDGAI();
		Calendar fecha = Calendar.getInstance();
		int mesactual=fecha.getTime().getMonth()+1;

		mesActualcapturado=mesactual;
			
		numeroAprobadosMes=getFlujodocumentoService().obtenerNumeroDocumentosMes("APROBADO",mesactual);
		numeroProcesoMes=getFlujodocumentoService().obtenerNumeroDocumentosMes("EN PROCESO",mesactual);
		numeroRechazadosMes=getFlujodocumentoService().obtenerNumeroDocumentosMes("RECHAZADO",mesactual);
		perfilesusuario= new ArrayList<Perfilusuario>();
		todosUsuarios=flujodocumentoService.obtenerUsuarios();
		listadousuariosSeleccionados= new ArrayList<Usuario>();
		listaPerfiles= new ArrayList<Perfil>();
		listaPerfiles= flujodocumentoService.obtenerPerfiles();
		
			
	}
	
	public void cargarPendientesPorMes(){
	System.out.println("cargarPendientesPorMes::" + mesActualcapturado);
		
		// carga de la grilla
		if (mesActualcapturado == 0) {
			
			numeroPendientesMes=getFlujodocumentoService().obtenerNumeroPendientes();
			numeroDerivadosMes=getFlujodocumentoService().obtenerNumeroDerivados();
			
			documentodgaiMB.setListaDocumentosSinDerivar(getDocumentodgaiService().listaDocumentosSinDerivar());
		} else {
			numeroPendientesMes=getFlujodocumentoService().obtenerNumeroPendientesMes(mesActualcapturado);
			numeroDerivadosMes=getFlujodocumentoService().obtenerNumeroDerivadosMes(mesActualcapturado);
			documentodgaiMB.setListaDocumentosSinDerivar(getFlujodocumentoService().listaDocumentosSinDerivar(mesActualcapturado));
			
		}
	}
	
	public void cargarDerivadosPorMes(){
			// carga de la grilla
			if (mesActualcapturado == 0) {
				System.out.println(":::cero::");
				numeroAprobadosMes=getFlujodocumentoService().obtenerNumeroDocumentosMes("APROBADO");
				numeroProcesoMes=getFlujodocumentoService().obtenerNumeroDocumentosMes("EN PROCESO");
				numeroRechazadosMes=getFlujodocumentoService().obtenerNumeroDocumentosMes("RECHAZADO");
				
				
				documentoDerivados=getFlujodocumentoService().listarDocumentoDerivados();
			} else {
				System.out.println(":::mes::"+mesActualcapturado);
				numeroAprobadosMes=getFlujodocumentoService().obtenerNumeroDocumentosMes("APROBADO",mesActualcapturado);
				numeroProcesoMes=getFlujodocumentoService().obtenerNumeroDocumentosMes("EN PROCESO",mesActualcapturado);
				numeroRechazadosMes=getFlujodocumentoService().obtenerNumeroDocumentosMes("RECHAZADO",mesActualcapturado);
				documentoDerivados=getFlujodocumentoService().listarDocumentoDerivadosMes(mesActualcapturado);
				
			}
		}
	
	public void filtrarAprobados(){
		documentoDerivados=getDocumentodgaiService().listaFiltroDocumentos("APROBADO");
		
	}
	public void filtrarRechazados(){
		documentoDerivados=getDocumentodgaiService().listaFiltroDocumentos("RECHAZADO");
	}
	public void filtrarProceso(){
		documentoDerivados=getDocumentodgaiService().listaFiltroDocumentos("EN PROCESO");
	}
	
	
	//FILTRADO POR MES
	
	public void filtrarAprobadosMes(){
		documentoDerivados=getDocumentodgaiService().listaFiltroDocumentosMes("APROBADO");
		
	}
	public void filtrarRechazadosMes(){
		documentoDerivados=getDocumentodgaiService().listaFiltroDocumentosMes("RECHAZADO");
	}
	public void filtrarProcesoMes(){
		documentoDerivados=getDocumentodgaiService().listaFiltroDocumentosMes("EN PROCESO");
	}
	
	
	
	public void verificarExistenciaFlujoRechazo(){
    	
		RequestContext contextRequest = RequestContext.getCurrentInstance(); 
		FacesContext contextFaces = FacesContext.getCurrentInstance();  

		if(listaDerivadosSeleccionados.size()==1){
			int tamanioFlujoDocumento=flujodocumentoService.obtenertamanioFlujoDocumento(listaDerivadosSeleccionados.get(0).getIddocumento());
			Boolean finalizadoDocumento=flujodocumentoService.estaDocumentoFinalizado(listaDerivadosSeleccionados.get(0).getIddocumento());
			Boolean finalizadoFlujo=flujodocumentoService.estaFlujoFinalizado(listaDerivadosSeleccionados.get(0).getIddocumento());
			
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
			if(listaDerivadosSeleccionados.size()>1){
			contextFaces.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"Advertencia", "Seleccionar solo un documento "));	
			}else{
				contextFaces.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"Advertencia", "Seleccione documento "));	
			}
		}
	}
	
	public void verificarExistenciaFlujoDespacho(){
		
		
		RequestContext contextRequest = RequestContext.getCurrentInstance(); 
		FacesContext contextFaces = FacesContext.getCurrentInstance();  
		

		if(listaDerivadosSeleccionados.size()!=0){
			int tamanioFlujoDocumento=flujodocumentoService.obtenertamanioFlujoDocumento(listaDerivadosSeleccionados.get(0).getIddocumento());
			Boolean finalizado=flujodocumentoService.estaDocumentoFinalizado(listaDerivadosSeleccionados.get(0).getIddocumento());
			Boolean finalizadoFlujo=flujodocumentoService.estaFlujoFinalizado(listaDerivadosSeleccionados.get(0).getIddocumento());
			
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
	
	public void listarDocumentosDerivados(){
		documentoDerivados = new ArrayList<Documento>();
		documentoDerivados=getFlujodocumentoService().listarDocumentoDerivados();
	}	
		public void retornarTamanioFlujo(int idDoc){
		
		
	}
	public void actualizarContadoresDGAI(){
		
		numeroPendientes=getFlujodocumentoService().obtenerNumeroPendientes();
		numeroDerivados=getFlujodocumentoService().obtenerNumeroDerivados();
		
		numeroDespachados=getFlujodocumentoService().obtenerNumeroDespachados();
		
		numeroPendientesMes=getFlujodocumentoService().obtenerNumeroPendientesMes();
		numeroDerivadosMes=getFlujodocumentoService().obtenerNumeroDerivadosMes();
		System.out.println("num pendientesxxx22:::"+numeroPendientesMes);
		
		
		numeroRechazados=getFlujodocumentoService().obtenerNumeroRechazados();
		//numeroPendientesSalida=getFlujodocumentoService().obtenerNumeroPendientesSalida();
		numeroPendientesSalida=numeroDerivados-numeroDespachados-numeroRechazados;
	//	numeroPendientesSalida=numeroDerivadosSalida-numeroDespachados-numeroRechazados;
	}

	
	public void seleccionIdDocumento(){
		for(int i=0;i<listaDerivadosSeleccionados.size();i++){
			if(listaDerivadosSeleccionados.get(i).getIddocumento()==getIddocSeleccionadoDerivados()){
				setDescripcionDocumentoDespacho(listaDerivadosSeleccionados.get(i).getDescripcion());
				listaAdjuntoDocumentoDerivados=documentodgaiService.cargarArchivosDocumento(listaDerivadosSeleccionados.get(i).getIddocumento());
				System.out.println("---------->"+listaAdjuntoDocumentoDerivados.size());
			}
		}
	}
	
	public void inicializarNotificaciónPersonalizable(int id){
		setIdusuarioNotificacionPersonalizada(id);
		
	}
	
	public void enviarNotificaciónPersonalizable(){
		
		
	getFlujodocumentoService().enviarNotificaciónPersonalizable(getContenidoMensajePersonalizado(),getIdusuarioNotificacionPersonalizada());
	
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
	
	public void notificarRechazo(){
		if(getComentarioRechazo()!=null){
			getFlujodocumentoService().actualizarComentarioDocumento(listaDerivadosSeleccionados.get(0).getIddocumento(), comentarioRechazo,flujoDocumentoIndex);
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Exito","Se Rechazó  expediente correctamente!!!"); 
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		else{
			System.out.println("Mensaje de alerta");
		}
		
		actualizarContadoresDGAI();
		listarDocumentosDerivados();
	}
	
	public void busquedaIntervaloFecha(){
		
	}
	
	
	public void metodo2(){
		System.out.println("Method 2");
	}
	
	public void downloadFile(){
		
			InputStream	stream = ((ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext()).getResourceAsStream("/images/optimusprime.jpg");  
	        file = new DefaultStreamedContent(stream, "nombreArchivoSeleccionado");  
	}
	
	public void registrarFlujoDocumento(ActionEvent event) {
		
		
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Exito","Se derivo expediente correctamente!!!"); 
		FacesContext.getCurrentInstance().addMessage(null, msg);
		
		Documento[] listadocume=documentodgaiMB.getListadocumentos();
		
		documentodgaiService.actualizarDocumentoDgai(listadocume[0].getIddocumento());
		
		for (Documento documento : listadocume) {
			int numerodeflujo=1;
			//envia notif. recib. a mesa partes
			//getFlujodocumentoService().actualizarRespuestaToAtendido(documento.getIddocumento());
				for (Usuario user : listadousuariosSeleccionados) {
					/*Envio de correo*/
					Correo correo =new Correo();
					String msj="Estimado Sr(a). <b> "+user.getNombres()+" "+user.getApellidopat()+"</b> <br /><br />"+
							"Se le ha derivado el <a href='http://192.168.1.10:8081/sistemaSBLM/pages/pgDGAI.jsf'>"+documento.getTitulo()+"</a><br /><br />"+
							"Atte <br />"+
							"Equipo SGI <br />"+
							"Oficina de Informática <br />"+
							"<b>Sociedad de Beneficencia de Lima</b>";
							correo.enviarCorreo(user.getEmailusr(), "Notificacion de Expediente",msj);
				 
//				Area are=new Area();
//			      are.setIdare("00000000");
			      Flujodocumento fludoc=new Flujodocumento();
			      fludoc.setUsuario(user);
			      //fludoc.setA;
			      fludoc.setDocumento(documento);
			      fludoc.setFecharecepcion(new Date());
			      fludoc.setEstadoenvio(false);
			      fludoc.setEstado("NINGUNO");
			      fludoc.setRespuesta("en proceso");
			      fludoc.setEstadosupervisor("NINGUNO");
			      fludoc.setIdusuarioremitente(Integer.parseInt(FuncionesHelper.getUsuario().toString()));
			      fludoc.setNumero(numerodeflujo);
			      fludoc.setIdperfilusuario(retornarIdPerfilPorNombre((user.getUsrmod())));
			      
				getFlujodocumentoService().registrarFlujoDocumento(fludoc);
				numerodeflujo++;
				}
		}
		listadousuariosSeleccionados.clear();
		actualizarContadoresDGAI();
		listarDocumentosDerivados();
		documentodgaiMB.listarDocumentoSinDerivar();
		
		Calendar fecha = Calendar.getInstance();
		 int mesactual=fecha.getTime().getMonth()+1;
	
		 numeroAprobadosMes=getFlujodocumentoService().obtenerNumeroDocumentosMes("APROBADO",mesactual);
			numeroProcesoMes=getFlujodocumentoService().obtenerNumeroDocumentosMes("EN PROCESO",mesactual);
			numeroRechazadosMes=getFlujodocumentoService().obtenerNumeroDocumentosMes("RECHAZADO",mesactual);
	}
	
	public int retornarIdPerfilPorNombre(String nombrePerfil){
			
			for (Perfil perfil : listaPerfiles) {
				
				if (perfil.getNombreperfil().equals(nombrePerfil)) {
					return perfil.getIdperfil();
				}
				
			}
			
			return 0;
		}
	

	
    public void onRowToggle(ToggleEvent event) {
    	flujoDocumentoIndex = new ArrayList<Flujodocumento>();
    	int idDocumento;
    	
    	
    	if(listaDerivadosSeleccionados.size()!=0){
    		idDocumento=listaDerivadosSeleccionados.get(0).getIddocumento();
        	flujoDocumentoIndex=flujodocumentoService.obtenerFlujodeDocumento(idDocumento);
    	}else {
    		//LLenamos un registro sin data
    	//	flujoDocumentoIndex.get(0).getUsuario().setNombres("Sin flujo");
    	}
    	
       } 
    
    
	 public void inicializarRechazo(){
		 
		 flujoDocumentoIndex = new ArrayList<Flujodocumento>();
	    	int idDocumento;
	    	
	    	
	    	if(listaDerivadosSeleccionados.size()!=0){
	    		idDocumento=listaDerivadosSeleccionados.get(0).getIddocumento();
	        	flujoDocumentoIndex=flujodocumentoService.obtenerFlujodeDocumento(idDocumento);
	    	}else {
	    		//LLenamos un registro sin data
	    		//flujoDocumentoIndex.get(0).getUsuario().setNombres("Sin flujo");
	    	}
	    	
		 
	 }
	 
	 public void inicializarDespacho(){
		 
			if(listaDerivadosSeleccionados.size()!=0){
			listaAdjuntoDocumentoDerivados=documentodgaiService.cargarArchivosDocumento(listaDerivadosSeleccionados.get(0).getIddocumento());
			setDescripcionDocumentoDespacho(listaDerivadosSeleccionados.get(0).getDescripcion());
			setIdDescripcionDocumentoDespacho(listaDerivadosSeleccionados.get(0).getIddocumento());
		
			}
//			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Exito","Se Despacho El expediente correctamente!!!"); 
//			FacesContext.getCurrentInstance().addMessage(null, msg);
			
	 }
    
	 
	 public void regresarMesaPartes(){
		 
		 
			for(int i=0;i<listaDerivadosSeleccionados.size();i++){
					flujodocumentoService.regresarMesaPartes(listaDerivadosSeleccionados.get(i).getIddocumento());
			}
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Exito","Se Despacho El expediente correctamente!!!"); 
			FacesContext.getCurrentInstance().addMessage(null, msg);
			listarDocumentosDerivados();
	 }
	 
	 
	 
	 
	 /*Metodos no funcionales*/
	 
		public String obtenerNombresXid(int id){
			String nomApe = "";
			
			for(int i=0;i<mesaPartesMB.getListUsuarios().size();i++){
				if(mesaPartesMB.getListUsuarios().get(i).getIdusuario()==getIdusuarioNotificacionPersonalizada()){
					
				nomApe=	mesaPartesMB.getListUsuarios().get(i).getNombres()+" "+mesaPartesMB.getListUsuarios().get(i).getApellidopat();
				}
			}
			
			return nomApe;
		}
		
		
		public String obtenerCorreoXid(int id){
			String correo = "";
			
			for(int i=0;i<mesaPartesMB.getListUsuarios().size();i++){
				if(mesaPartesMB.getListUsuarios().get(i).getIdusuario()==getIdusuarioNotificacionPersonalizada()){
					
				correo=	mesaPartesMB.getListUsuarios().get(i).getEmailusr();
				}
				
			}
			
			return correo;
		}
	 
		
	public void eliminarUsuarioDeLista(ActionEvent event) {
		
		for(int i=0;i<listadousuariosSeleccionados.size();i++){
			
			if(listadousuariosSeleccionados.get(i)==getSelectRegistroUsuario()){
				listadousuariosSeleccionados.remove(i);
			}
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
	
	public void onCellEdit(CellEditEvent event)  { 
		
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
	
	public void buscarPerfilUsuario(){
		
		perfilesusuario.clear();
		perfilesusuario=flujodocumentoService.getPerfilesUsuario(getIdUsuarioSeleccionado());
		
		if(!perfilesusuario.isEmpty()){
		}else
			System.out.println("No tiene ningun perfil");
		
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
	 
	 /*Getting and Setting*/
	
	public IFlujoDocumentoService getFlujodocumentoService() {
		return flujodocumentoService;
	}

	public void setFlujodocumentoService(
			IFlujoDocumentoService flujodocumentoService) {
		this.flujodocumentoService = flujodocumentoService;
	}

	public Flujodocumento getFlujodocumento() {
		if (flujodocumento == null) {
			flujodocumento = new Flujodocumento();
		}
		return flujodocumento;
	}

	public void setFlujodocumento(Flujodocumento flujodocumento) {
		this.flujodocumento = flujodocumento;
	}

	public List<Flujodocumento> getFlujodocumentos() {
		return flujodocumentos;
	}

	public void setFlujodocumentos(List<Flujodocumento> flujodocumentos) {
		this.flujodocumentos = flujodocumentos;
	}

	public Documento getDocumentoenviado() {
		return documentoenviado;
	}

	public void setDocumentoenviado(Documento documentoenviado) {
		this.documentoenviado = documentoenviado;
	}

	public UsuarioManagedBean getUserMB() {
		return userMB;
	}

	public void setUserMB(UsuarioManagedBean userMB) {
		this.userMB = userMB;
	}

	

	public void setDocumentodgaiMB(DocumentoDgaiManagedBean documentodgaiMB) {
		this.documentodgaiMB = documentodgaiMB;
	}

	public DocumentoDgaiManagedBean getDocumentodgaiMB() {
		return documentodgaiMB;
	}


	public Usuario[] getListadousuarios() {
		return listadousuarios;
	}

	public void setListadousuarios(Usuario[] listadousuarios) {
		this.listadousuarios = listadousuarios;
	}


	
	public int getNumeroDespachados() {
		
		return this.numeroDespachados ;
	}

	public void setNumeroDespachados(int numeroDespachados) {
		this.numeroDespachados = numeroDespachados;
	}

	public Flujodocumento[] getListadocumentosrechazados() {
		return listadocumentosrechazados;
	}
	public void setListadocumentosrechazados(
			Flujodocumento[] listadocumentosrechazados) {
		this.listadocumentosrechazados = listadocumentosrechazados;
	}
	public Flujodocumento getDocumentocapturado() {
		return documentocapturado;
	}
	public void setDocumentocapturado(Flujodocumento documentocapturado) {
		this.documentocapturado = documentocapturado;
	}

	public List<Flujodocumento> getFlujoDocumentoIndex() {
		return flujoDocumentoIndex;
	}

	public void setFlujoDocumentoIndex(List<Flujodocumento> flujoDocumentoIndex) {
		this.flujoDocumentoIndex = flujoDocumentoIndex;
	}

	public Documento getSelectDocumento() {
		return selectDocumento;
	}

	public void setSelectDocumento(Documento selectDocumento) {
		this.selectDocumento = selectDocumento;
	}

	public List<Documento> getDocumentoDerivados() {
		return documentoDerivados;
	}

	public void setDocumentoDerivados(List<Documento> documentoDerivados) {
		this.documentoDerivados = documentoDerivados;
	}

	public List<Documento> getListaDerivadosSeleccionados() {
		
		try {
			if(listaDerivadosSeleccionados.isEmpty()){
				System.out.println("VACIO");
			}else{
				
				
			}
			
			
		} catch (NullPointerException e) {
			System.out.println("Controlado");
		}

		
		return listaDerivadosSeleccionados;
	}

	public void setListaDerivadosSeleccionados(
			List<Documento> listaDerivadosSeleccionados) {
		this.listaDerivadosSeleccionados = listaDerivadosSeleccionados;
	}

	public CompDataModelFlujoDocumento getCompDataModelFlujoDocumento() {
		return compDataModelFlujoDocumento;
	}

	public void setCompDataModelFlujoDocumento(
			CompDataModelFlujoDocumento compDataModelFlujoDocumento) {
		this.compDataModelFlujoDocumento = compDataModelFlujoDocumento;
	}

	public String getComentarioRechazo() {
		return comentarioRechazo;
	}

	public void setComentarioRechazo(String comentarioRechazo) {
		this.comentarioRechazo = comentarioRechazo;
	}

	public List<Flujodocumento> getUsuarioSeleccionadosfljDoc() {
		return usuarioSeleccionadosfljDoc;
	}


	public void setUsuarioSeleccionadosfljDoc(
			List<Flujodocumento> usuarioSeleccionadosfljDoc) {
		this.usuarioSeleccionadosfljDoc = usuarioSeleccionadosfljDoc;
	}


	public List<Archivodocumento> getListaAdjuntoDocumentoDerivados() {
		return listaAdjuntoDocumentoDerivados;
	}

	public void setListaAdjuntoDocumentoDerivados(
			List<Archivodocumento> listaAdjuntoDocumentoDerivados) {
		this.listaAdjuntoDocumentoDerivados = listaAdjuntoDocumentoDerivados;
	}

	public int getIddocSeleccionadoDerivados() {
		return iddocSeleccionadoDerivados;
	}

	public void setIddocSeleccionadoDerivados(int iddocSeleccionadoDerivados) {
		this.iddocSeleccionadoDerivados = iddocSeleccionadoDerivados;
	}

	public IDocumentoDgaiService getDocumentodgaiService() {
		return documentodgaiService;
	}

	public void setDocumentodgaiService(IDocumentoDgaiService documentodgaiService) {
		this.documentodgaiService = documentodgaiService;
	}
	public StreamedContent getFile() {  
        return file;  
    }

	public String getNombreArchivoSeleccionado() {
		return nombreArchivoSeleccionado;
	}

	public void setNombreArchivoSeleccionado(String nombreArchivoSeleccionado) {
		this.nombreArchivoSeleccionado = nombreArchivoSeleccionado;
	}


	public String getDescripcionDocumentoDespacho() {
		return descripcionDocumentoDespacho;
	}


	public void setDescripcionDocumentoDespacho(String descripcionDocumentoDespacho) {
		this.descripcionDocumentoDespacho = descripcionDocumentoDespacho;
	}


	public int getIdDescripcionDocumentoDespacho() {
		return idDescripcionDocumentoDespacho;
	}

	public void setIdDescripcionDocumentoDespacho(int idDescripcionDocumentoDespacho) {
		this.idDescripcionDocumentoDespacho = idDescripcionDocumentoDespacho;
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

	

	public int getNumeroAprobados() {
		numeroAprobados=getFlujodocumentoService().obtenerNumeroDocumentos("APROBADO");
		return numeroAprobados;
	}

	public void setNumeroAprobados(int numeroAprobados) {
		this.numeroAprobados = numeroAprobados;
	}

	public int getNumeroProceso() {
		numeroProceso=getFlujodocumentoService().obtenerNumeroDocumentos("EN PROCESO");
		return numeroProceso;
	}

	public void setNumeroProceso(int numeroProceso) {
		this.numeroProceso = numeroProceso;
	}
	public int getNumeroRechazados() {
		numeroRechazados=getFlujodocumentoService().obtenerNumeroDocumentos("RECHAZADO");
		return this.numeroRechazados ;
	}

	public void setNumeroRechazados(int numeroRechazados) {
		this.numeroRechazados = numeroRechazados;
	}

	public int getNumeroAprobadosMes() {
		//numeroAprobadosMes=getFlujodocumentoService().obtenerNumeroDocumentosMes("APROBADO");
		return numeroAprobadosMes;
	}

	public void setNumeroAprobadosMes(int numeroAprobadosMes) {
		this.numeroAprobadosMes = numeroAprobadosMes;
	}

	public int getNumeroProcesoMes() {
		//numeroProcesoMes=getFlujodocumentoService().obtenerNumeroDocumentosMes("EN PROCESO");
		return numeroProcesoMes;
	}

	public void setNumeroProcesoMes(int numeroProcesoMes) {
		this.numeroProcesoMes = numeroProcesoMes;
	}

	public int getNumeroRechazadosMes() {
		//numeroRechazadosMes=getFlujodocumentoService().obtenerNumeroDocumentosMes("RECHAZADO");
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
		numeroDerivados=getFlujodocumentoService().obtenerNumeroDerivados();
		return numeroDerivados;
	}

	public void setNumeroDerivados(int numeroDerivados) {
		this.numeroDerivados = numeroDerivados;
	}
	public int getNumeroDerivadosMes() {
		return numeroDerivadosMes;
	}

	public void setNumeroDerivadosMes(int numeroDerivadosMes) {
		this.numeroDerivadosMes = numeroDerivadosMes;
	}
	
	public int getIdusuarioNotificacionPersonalizada() {
		return idusuarioNotificacionPersonalizada;
	}

	public void setIdusuarioNotificacionPersonalizada(
			int idusuarioNotificacionPersonalizada) {
		this.idusuarioNotificacionPersonalizada = idusuarioNotificacionPersonalizada;
	}

	public String getContenidoMensajePersonalizado() {
		return contenidoMensajePersonalizado;
	}

	public void setContenidoMensajePersonalizado(
			String contenidoMensajePersonalizado) {
		this.contenidoMensajePersonalizado = contenidoMensajePersonalizado;
	}

	public int getNumeroPendientesMes() {
		return numeroPendientesMes;
	}

	public void setNumeroPendientesMes(int numeroPendientesMes) {
		this.numeroPendientesMes = numeroPendientesMes;
	}

	public int getMesActualcapturado() {
		return mesActualcapturado;
	}

	public void setMesActualcapturado(int mesActualcapturado) {
		this.mesActualcapturado = mesActualcapturado;
	}
	
		public monitoreoMesaPartesController getMesaPartesMB() {
		return mesaPartesMB;
	}

	public void setMesaPartesMB(monitoreoMesaPartesController mesaPartesMB) {
		this.mesaPartesMB = mesaPartesMB;
	}

	public List<Perfilusuario> getPerfilesusuario() {
		return perfilesusuario;
	}

	public void setPerfilesusuario(List<Perfilusuario> perfilesusuario) {
		this.perfilesusuario = perfilesusuario;
	}

	public Usuario getSelectRegistroUsuario() {
		return selectRegistroUsuario;
	}

	public void setSelectRegistroUsuario(Usuario selectRegistroUsuario) {
		this.selectRegistroUsuario = selectRegistroUsuario;
	}

	public List<Usuario> getListadousuariosSeleccionados() {
		return listadousuariosSeleccionados;
	}

	public void setListadousuariosSeleccionados(
			List<Usuario> listadousuariosSeleccionados) {
		this.listadousuariosSeleccionados = listadousuariosSeleccionados;
	}

	public int getIndicesalvador() {
		return indicesalvador;
	}

	public void setIndicesalvador(int indicesalvador) {
		this.indicesalvador = indicesalvador;
	}

	public List<Usuario> getTodosUsuarios() {
		return todosUsuarios;
	}

	public void setTodosUsuarios(List<Usuario> todosUsuarios) {
		this.todosUsuarios = todosUsuarios;
	}

	public int getIdUsuarioSeleccionado() {
		return idUsuarioSeleccionado;
	}

	public void setIdUsuarioSeleccionado(int idUsuarioSeleccionado) {
		this.idUsuarioSeleccionado = idUsuarioSeleccionado;
	}
	

	
//	public String getUltimoAsunto() {
//		return ultimoAsunto;
//	}
//
//	public void setUltimoAsunto(String ultimoAsunto) {
//		this.ultimoAsunto = ultimoAsunto;
//	}
//	
//
//	
}

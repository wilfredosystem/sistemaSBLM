package com.sblm.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.persistence.criteria.CriteriaBuilder.In;

import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.DragDropEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;

import com.sblm.model.Archivodocumento;
import com.sblm.model.Documento;
import com.sblm.model.Extensionarchivo;
import com.sblm.model.Tipodocumento;
import com.sblm.model.Usuario;
import com.sblm.modelMP.Flujodoc;
import com.sblm.service.IAuditoriaService;
import com.sblm.service.IDocumentoDgaiService;
import com.sblm.service.IMonitoreoMesaPartesService;
import com.sblm.util.Almanaque;
import com.sblm.util.CompDataModelDocumento;
import com.sblm.util.Correo;
import com.sblm.util.FuncionesHelper;

@ManagedBean(name = "cmonitoreomesapartes")
@ViewScoped
public class monitoreoMesaPartesController implements Serializable {
	@ManagedProperty(value = "#{panelDocumentoMesaPartesServiceImpl}")
	private transient IMonitoreoMesaPartesService panelDocumentoMesaPartesServiceImpl;
	@ManagedProperty(value = "#{panelAuditoriaServiceImpl}")
	private transient IAuditoriaService panelAuditoriaServiceImpl;
	@ManagedProperty(value = "#{documentodgaiService}")
	private transient IDocumentoDgaiService documentodgaiService;

	private static final long serialVersionUID = 5524190003746598593L;
	private List<Flujodoc> listFLujoDocumentosRegInit;
	private List<Documento> listDocumentosRegInit;
	private List<Documento> listDocumentosRegInitAtendido;
	private Documento[] selectRegistrosDocumentos;
	private Documento selectRegistroDocumento;
	private Usuario selectRegistroUsuario;
	private CompDataModelDocumento compDataModelDocumento;
	private List<Documento> filtroCompDataModelDocumento;
	private Object nroExternal = 0;
	private Object nroInternal = 0;
	private String valCommandLink;
	private Boolean valDirectora = true;
	String nroAtendido;
	String nroPendiente;

	// String nroProceso;
	// String nroAprobado;
	// String nroRechazado;
	private List<Flujodoc> lista_iddoc_remoto;
    private List<Documento> lista_iddoc_local;

	String nroPendienteMes;
	String nroAtendidoMes;

	String NroProcesoMes;
	String NroAprobadoMes;
	String nroRechazadoMes;
	private String mesActual;
	private int iddocumento;
	private String descripcionDocumento;
	private int idDescripcionDocumento;
	private List<Usuario> listaUsuarioSeleccionados;
	private List<Usuario> listUsuarios;
	private int indicesalvador;
	private String nombreCompletoSeleccionado;
	private Extensionarchivo archivoDelete;
	private String ultimodespacho;
	/* Manejo de Ficheros */
	private String rutaDocumento;
	private String directorioDocumentos;
	private List<OutputStream> F;
	private HashMap<String,InputStream> is;
	private List<Extensionarchivo> displayList;
	private List<Extensionarchivo> listaExtensionArchivos;
	private int mesActualcapturado;
	
	private List<Archivodocumento> listaAdjuntoDocumento;

	private Map<Integer, List<InputStream>> list;
	private Map<Integer, Map<String, InputStream>> list2;

	@ManagedProperty(value = "#{usuarioMB}")
	UsuarioManagedBean userMB;

	@PostConstruct
	public void initObjects() {
		
		Calendar fechaact = Calendar.getInstance();
		
		fechaact.getTime().getMonth();
		
		mesActualcapturado=fechaact.getTime().getMonth()+1;
		
		listaExtensionArchivos = new ArrayList<Extensionarchivo>();
		listaExtensionArchivos = panelDocumentoMesaPartesServiceImpl
				.getListaExtensionArhivos();
		 directorioDocumentos =
		 "C:\\Users\\Informatica\\Desktop\\WokspaceGit\\sistemaSBLM\\WebContent\\resources\\documents\\";
//		 directorioDocumentos = "C:\\archivosxxx\\";
		// para servidor
//		String curDir = System.getProperty("user.dir");
//		directorioDocumentos = curDir
//				+ "\\webapps\\sistemaSBLM\\resources\\documents\\";

		inicializarUsuarios();
		listUsuarios = panelAuditoriaServiceImpl.listUsuariobyNom();
		Almanaque almanaque = new Almanaque();
		mesActual = almanaque.obtenerMesActual();
		actualizarBeneficenciadb();
		listarGrillaInit();
		valuePendiente();
		ultimodespacho = getPanelDocumentoMesaPartesServiceImpl()
				.obtenerUltimoDespacho();
		//**carga de grilla y fechas**//
		Calendar fecha = Calendar.getInstance();
		 int mesactual=fecha.getTime().getMonth()+1;
		Object pendMes = panelDocumentoMesaPartesServiceImpl.countPendientesMes(mesactual);
		Object atenMes = panelDocumentoMesaPartesServiceImpl.countAtendidosMes(mesactual);
		setNroPendienteMes(pendMes.toString());
		setNroAtendidoMes(atenMes.toString());
		listDocumentosRegInit = panelDocumentoMesaPartesServiceImpl.listarDocumentosRegistrados(mesactual);
		compDataModelDocumento = new CompDataModelDocumento(
				listDocumentosRegInit);
		
	}

	public void cargarPendientesPorMes() {

		System.out.println("cargarPendientesPorMes::" + mesActualcapturado);
		
		// carga de la grilla
		if (mesActualcapturado == 0) {
			
			Object aten = panelDocumentoMesaPartesServiceImpl.countAtendidos();
			Object pend = panelDocumentoMesaPartesServiceImpl.countPendientes();
			
			nroAtendidoMes=aten.toString();
			nroPendienteMes=pend.toString();
			
			listDocumentosRegInit = panelDocumentoMesaPartesServiceImpl.listarDocumentosRegistrados();
		} else {
			
			Object pendMes = panelDocumentoMesaPartesServiceImpl.countPendientesMes(mesActualcapturado);
			Object atenMes = panelDocumentoMesaPartesServiceImpl.countAtendidosMes(mesActualcapturado);
			setNroPendienteMes(pendMes.toString());
			setNroAtendidoMes(atenMes.toString());
			
			listDocumentosRegInit = panelDocumentoMesaPartesServiceImpl.listarDocumentosRegistrados(mesActualcapturado);
		
		}
		compDataModelDocumento = new CompDataModelDocumento(listDocumentosRegInit);
	}

	public void cargarAtendidosPorMes() {

		System.out.println("cargarDatosPorMes::" + mesActualcapturado);
		
		valuePendiente();
		// carga de la grilla
		if (mesActualcapturado == 0) {
			listDocumentosRegInitAtendido = panelDocumentoMesaPartesServiceImpl
					.listarDocumentosAtendidos();
		} else {
			listDocumentosRegInitAtendido = panelDocumentoMesaPartesServiceImpl
					.listarDocumentosAtendidos(mesActualcapturado);
		}
		compDataModelDocumento = new CompDataModelDocumento(
				listDocumentosRegInitAtendido);
	}


	public void inicializarUsuarios(){
		listaUsuarioSeleccionados= new ArrayList<Usuario>();
		listaUsuarioSeleccionados.add(panelDocumentoMesaPartesServiceImpl.getDirectorDGAI());
		listaUsuarioSeleccionados.get(0).setEmailusr(listaUsuarioSeleccionados.get(0).getNombres()+" "+listaUsuarioSeleccionados.get(0).getApellidopat()+" "+listaUsuarioSeleccionados.get(0).getApellidomat());	
		listaUsuarioSeleccionados.get(0).setContrasenausr("icono-blanco.jpg");
	}

	public void filtrarAprobadosMes() {
		listDocumentosRegInit = panelDocumentoMesaPartesServiceImpl
				.listaFiltroDocumentosMes("APROBADO");
		compDataModelDocumento = new CompDataModelDocumento(
				listDocumentosRegInit);
	}

	public void filtrarProcesoMes() {
		listDocumentosRegInit = panelDocumentoMesaPartesServiceImpl
				.listaFiltroDocumentosMes("EN PROCESO");
		compDataModelDocumento = new CompDataModelDocumento(
				listDocumentosRegInit);
	}

	public void filtrarRechazadosMes() {
		listDocumentosRegInit = panelDocumentoMesaPartesServiceImpl
				.listaFiltroDocumentosMes("RECHAZADO");
		compDataModelDocumento = new CompDataModelDocumento(
				listDocumentosRegInit);
	}

	public void listarGrillaInit() {

		listDocumentosRegInit = new ArrayList<Documento>();

		try {
			listDocumentosRegInit = panelDocumentoMesaPartesServiceImpl
					.listarDocumentosRegistrados();
			compDataModelDocumento = new CompDataModelDocumento(
					listDocumentosRegInit);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void listarGrillaInitAtendido() {

		listDocumentosRegInitAtendido = new ArrayList<Documento>();

		try {
			listDocumentosRegInitAtendido = panelDocumentoMesaPartesServiceImpl
					.listarDocumentosAtendidos();
			compDataModelDocumento = new CompDataModelDocumento(
					listDocumentosRegInitAtendido);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void metodo() {
		listarGrillaInit();
	}

  public String mensajeEnvio(){
    	
    	String cadenaUsuarios="";
    	
    	for(int i=0;i<listaUsuarioSeleccionados.size();i++){
    		
    		cadenaUsuarios=cadenaUsuarios+ new String("\n"+listaUsuarioSeleccionados.get(i).getEmailusr()+"");
    		
    	}
    	
    	
    	
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito",  new String("Notificación Exitosa para: " +cadenaUsuarios+"                     Número de expedientes Enviados:"+selectRegistrosDocumentos.length));  
          
        FacesContext.getCurrentInstance().addMessage(null, message);  
        return null;
    } 
    
    
    
public void verificarItemSeleccionado(){
		
		
		RequestContext contextRequest = RequestContext.getCurrentInstance(); 
		FacesContext contextFaces = FacesContext.getCurrentInstance(); 
		
		
		
		if(selectRegistrosDocumentos.length!=0){
			contextRequest.execute("dialogSeleccionDocumento.show();");	
		}else {
			contextFaces.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"Advertencia", "Seleccione al menos un documento "));
		}

		ultimodespacho = getPanelDocumentoMesaPartesServiceImpl()
				.obtenerUltimoDespacho();
	}

	public void valuePendiente() {
		

		Object proc = panelDocumentoMesaPartesServiceImpl.countDocumentosFiltrados("EN PROCESO");
		Object aprob = panelDocumentoMesaPartesServiceImpl.countDocumentosFiltrados("APROBADO");
		Object recha = panelDocumentoMesaPartesServiceImpl.countDocumentosFiltrados("RECHAZADO");

		
//		Object pendMes = panelDocumentoMesaPartesServiceImpl.countPendientesMes();
//		Object atenMes = panelDocumentoMesaPartesServiceImpl.countAtendidosMes();

		Object procMes = panelDocumentoMesaPartesServiceImpl
				.countDocumentosFiltradosMes("EN PROCESO", mesActualcapturado);
		Object aprobMes = panelDocumentoMesaPartesServiceImpl
				.countDocumentosFiltradosMes("APROBADO", mesActualcapturado);
		Object rechaMes = panelDocumentoMesaPartesServiceImpl
				.countDocumentosFiltradosMes("RECHAZADO", mesActualcapturado);
		
		Object aten = panelDocumentoMesaPartesServiceImpl.countAtendidos();
		Object pend = panelDocumentoMesaPartesServiceImpl.countPendientes();
		setNroAtendido(aten.toString());
		setNroPendiente(pend.toString());
		// setNroProceso(proc.toString());
		// setNroAprobado(aprob.toString());
		// setNroRechazado(recha.toString());
		// por mes actual
		Object pendMes = panelDocumentoMesaPartesServiceImpl.countPendientesMes(mesActualcapturado);
		Object atenMes = panelDocumentoMesaPartesServiceImpl.countAtendidosMes(mesActualcapturado);
		setNroPendienteMes(pendMes.toString());
		setNroAtendidoMes(atenMes.toString());

		setNroProcesoMes(procMes.toString());
		setNroAprobadoMes(aprobMes.toString());
		setNroRechazadoMes(rechaMes.toString());

	}

	public void actualizarBeneficenciadb(){
		lista_iddoc_remoto = new ArrayList<Flujodoc>();
		lista_iddoc_local= new ArrayList<Documento>();
		try {
			lista_iddoc_remoto=panelDocumentoMesaPartesServiceImpl.getNewInserts(0);
			lista_iddoc_local=panelDocumentoMesaPartesServiceImpl.getListLocal();
		} catch (Exception e) {
			System.out.println("capturado erroro::"+e.getMessage());
		}
		
		
		
		
        Boolean flag=false;

        /*Buscamos si se realizo una eliminacion*/
        for(int i=0;i<lista_iddoc_local.size();i++){
        	flag=false;
            for(int k=0;k<lista_iddoc_remoto.size();k++){
               
                if(lista_iddoc_remoto.get(k).getDocumento().getIddoc().equals(lista_iddoc_local.get(i).getCodigodocumento())){
                    flag=true; 
                    //Ok!
                }
                       }
                if(!flag){
                
                panelDocumentoMesaPartesServiceImpl.eliminarDocumentoNoEncontrado(lista_iddoc_local.get(i).getCodigodocumento());
                lista_iddoc_local.remove(i);
                 //eliminamos registro en base de datos de servidor, previamente configurada en cascada
                }
        
        }
        
        
        for(int i=0;i<lista_iddoc_remoto.size();i++){
        	flag=false;
            for(int k=0;k<lista_iddoc_local.size();k++){
                
                if(lista_iddoc_remoto.get(i).getDocumento().getIddoc().equals(lista_iddoc_local.get(k).getCodigodocumento())){
                    flag=true;
                 //Ok!
                }
                       }

            if(!flag){
            	
            	Documento doc= new Documento();
				Tipodocumento tipdoc= new Tipodocumento();
				
				tipdoc.setIdtipodoc(lista_iddoc_remoto.get(i).getDocumento().getIdtipdoc());
				doc.setCodigodocumento(lista_iddoc_remoto.get(i).getDocumento().getIddoc());
				doc.setTitulo(lista_iddoc_remoto.get(i).getDocumento().getTitdoc());
				doc.setDescripcion(lista_iddoc_remoto.get(i).getDocumento().getDesdoc());
				doc.setFechadocumento(lista_iddoc_remoto.get(i).getDocumento().getFecdoc());
				doc.setAsunto(lista_iddoc_remoto.get(i).getDocumento().getDesasn());
				doc.setNombreremitente(lista_iddoc_remoto.get(i).getDocumento().getDesrmt());
				doc.setNumerofolio(lista_iddoc_remoto.get(i).getNumfol());
				doc.setEstado("NINGUNO");
				doc.setRespuesta("EN PROCESO");
				doc.setFecharegistro(new Date());
				doc.setTipodocumento(tipdoc);
				
				panelDocumentoMesaPartesServiceImpl.save(doc);
				
          
                 //agregamos registro en base de datos de servidor
            }
        
        }    
		
		
//		
//				for(int i=listFLujoDocumentosRegInit.size();i>0;i--){
//					Documento doc= new Documento();
//					Tipodocumento tipdoc= new Tipodocumento();
//					
//					tipdoc.setIdtipodoc(listFLujoDocumentosRegInit.get(i-1).getDocumento().getIdtipdoc());
//					doc.setCodigodocumento(listFLujoDocumentosRegInit.get(i-1).getDocumento().getIddoc());
//					doc.setTitulo(listFLujoDocumentosRegInit.get(i-1).getDocumento().getTitdoc());
//					doc.setDescripcion(listFLujoDocumentosRegInit.get(i-1).getDocumento().getDesdoc());
//					doc.setFechadocumento(listFLujoDocumentosRegInit.get(i-1).getDocumento().getFecdoc());
//					doc.setAsunto(listFLujoDocumentosRegInit.get(i-1).getDocumento().getDesasn());
//					doc.setNombreremitente(listFLujoDocumentosRegInit.get(i-1).getDocumento().getDesrmt());
//					doc.setNumerofolio(listFLujoDocumentosRegInit.get(i-1).getNumfol());
//					doc.setEstado("NINGUNO");
//					doc.setRespuesta("EN PROCESO");
//					doc.setFecharegistro(new Date());
//					doc.setTipodocumento(tipdoc);
//					
//					panelDocumentoMesaPartesServiceImpl.save(doc);
//				
//
//		}
		
	}
	
	public void agregarUsuarioLista(){
		boolean usuarioVacio = false;

		if (!usuarioVacio) {
		}
			Usuario Usu = new Usuario();
			Usu.setEmailusr("Escriba Nombre Usuario");
			Usu.setRutaimgusr("default.jpg");
			Usu.setContrasenausr("deleteUsuario.png");
			listaUsuarioSeleccionados.add(Usu);
			setIndicesalvador(listaUsuarioSeleccionados.size()-1);
			
		}
	
	public void onCellEdit(CellEditEvent event) {  
        Object oldValue = event.getOldValue();  
        Object newValue = event.getNewValue(); 
        FacesContext contextFaces = FacesContext.getCurrentInstance();  

       listUsuarios = panelAuditoriaServiceImpl.listUsuariobyNom();
       
       //buscamos id usuario
       int id = 0;
       for(int k=0;k<listUsuarios.size();k++){	
			if((listUsuarios.get(k).getNombres()+" "+listUsuarios.get(k).getApellidopat() +" "+listUsuarios.get(k).getApellidomat()).equals(newValue)){
				id=k;
			}

		}

		boolean flag = true;
	

		for (int i = 0; i < listaUsuarioSeleccionados.size() - 1; i++) {
			System.out.println("i=" + i);
			if ((listaUsuarioSeleccionados.get(i).getEmailusr())
					.equals(newValue)) {
				
				contextFaces.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_WARN, "Advertencia",
						"usuario ya se encuentra en la lista"));
				listaUsuarioSeleccionados.remove(getIndicesalvador());
				flag = false;

			}
		}

		if (flag) {
			System.out.println("nuevo");
			listaUsuarioSeleccionados.get(getIndicesalvador()).setRutaimgusr(listUsuarios.get(id).getRutaimgusr());
			listaUsuarioSeleccionados.get(getIndicesalvador()).setCargo(listUsuarios.get(id).getCargo());
			listaUsuarioSeleccionados.get(getIndicesalvador()).setIdusuario(listUsuarios.get(id).getIdusuario());
			
		}
		
    }

	public void eliminarUsuarioDeLista(ActionEvent event) {
			
			for(int i=1;i<listaUsuarioSeleccionados.size();i++){
				
				if(listaUsuarioSeleccionados.get(i)==getSelectRegistroUsuario()){
					listaUsuarioSeleccionados.remove(i);
				}
			}

			}
	
	
	public void subirDocumento(FileUploadEvent event) {
		
		
		try {
			copyFile(event.getFile().getFileName(), event.getFile().getInputstream());
			
				 setRutaDocumento(event.getFile().getFileName());
				
				 FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito",  "Se cargo documento  correctamente");  
			     FacesContext.getCurrentInstance().addMessage(null, message);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
}
	public void setearDescripcion(){
		

		
		
		if(selectRegistrosDocumentos.length!=0){
			inicializarUsuarios();
			setDescripcionDocumento(selectRegistrosDocumentos[0].getDescripcion());
			setIdDescripcionDocumento(selectRegistrosDocumentos[0].getIddocumento());//aca
			
			
			/*Inicializando*/
			is= new HashMap<String, InputStream>();
			F= new ArrayList<OutputStream>();
			list = new HashMap<Integer, List<InputStream>>();
			list2= new HashMap<Integer, Map<String,InputStream>>();
			displayList =new ArrayList<Extensionarchivo>();
			
		}
		
		
}
	
	@SuppressWarnings("rawtypes")
	public void seleccionIdDocumento(){
		
		for(int i=0;i<listDocumentosRegInit.size();i++){
			if(listDocumentosRegInit.get(i).getIddocumento()==getIddocumento()){
				setDescripcionDocumento(listDocumentosRegInit.get(i).getDescripcion());
				setIdDescripcionDocumento(listDocumentosRegInit.get(i).getIddocumento());
				
			}
		}
		
		displayList =new ArrayList<Extensionarchivo>();
		
		is= new HashMap<String, InputStream>();
		//Buscamos el documento y calculamos su tamaño
		for(int i=0;i<selectRegistrosDocumentos.length;i++){
			if(selectRegistrosDocumentos[i].getIddocumento()==getIdDescripcionDocumento()){
				Map docClick=new HashMap<String, InputStream>();
				docClick=list2.get(selectRegistrosDocumentos[i].getIddocumento());
				if(docClick!=null){
					String key;
					InputStream value;
					
					Iterator iterator = docClick.keySet().iterator();
					while (iterator.hasNext()) {
					    key = (String) iterator.next();
					    value = (InputStream) docClick.get(key);
					    is.put(key, value);
					    
					    Extensionarchivo extFile= new Extensionarchivo();
						extFile.setRutaIcono(obtenerRutaImagen(obtenerExtension(key)));
						extFile.setDescExt(key);
						
						displayList.add(extFile);
						
						
					}
					}
				else{}
				}
			}
	}
	
public void actualizarEstado(){
	
		System.out.println("selectRegistrosDocumentos:::"+selectRegistrosDocumentos.length);
		for(int i=0;i<selectRegistrosDocumentos.length;i++){
			
				panelDocumentoMesaPartesServiceImpl.actualizarEstadoToAtendido(selectRegistrosDocumentos[i].getIddocumento(),listaUsuarioSeleccionados);
				
				for (int j = 0; j < listaUsuarioSeleccionados.size(); j++) {

					Correo correo =new Correo();
					String msj="Estimado Sr(a). <b> "+listaUsuarioSeleccionados.get(j).getEmailusr()+"</b> <br /><br />"+
							"Ha recibido el siguiente documento :"+"<br /><br />"+
							selectRegistrosDocumentos[i].getTitulo()+"<br /><br /><br /><br />"+
							"Atte <br />"+userMB.getUsuariologueado().getNombres()+" "+userMB.getUsuariologueado().getApellidopat()+
							"<br />"+
							"Oficina de Informática <br />"+
							"<b>Sociedad de Beneficencia de Lima</b>";
							
							String cadena=listaUsuarioSeleccionados.get(j).getEmailusr();
							correo.enviarCorreo(obtenerCorreoXid(cadena),"Mensaje",msj);
					}
				
				
				
			System.out.println("______________________________________");
			Map tmp=new HashMap<String, InputStream>();
			
			
			tmp=list2.get(selectRegistrosDocumentos[i].getIddocumento());
			
			
			if(tmp!=null){
				String key;
				InputStream value;
			@SuppressWarnings("rawtypes")
			Iterator iterator = tmp.keySet().iterator();
			while (iterator.hasNext()) {
			    key = (String) iterator.next();
			    value = (InputStream) tmp.get(key);
			    System.out.println(key + ":" + value);
			    escribirDocumentosServidor(key,value);
			    Documento docu= new Documento();
			    docu.setIddocumento(selectRegistrosDocumentos[i].getIddocumento());
			    
			    Extensionarchivo extarchivo = new Extensionarchivo();
			    extarchivo.setIdExtensionArchivo(obtenerIdExtensionArchivo(obtenerExtension(key)));
			    
			    try {
				} catch (Exception e) {
				e.getMessage();				}
			    
			    
			    Archivodocumento archivodocu= new Archivodocumento();
			    archivodocu.setDocumento(docu);
			    archivodocu.setRuta(key);
			    archivodocu.setFechaCreacion(new Date());
			    archivodocu.setAutorArchivo((Integer)(FuncionesHelper.getUsuario()));
			    archivodocu.setUsuarioCreador(userMB.getUsuariologueado().getNombres()+" "+userMB.getUsuariologueado().getApellidopat());
			    archivodocu.setExtensionarchivo(extarchivo);
			    
			    panelDocumentoMesaPartesServiceImpl.agregarArchivosDocumento(archivodocu);
			}
			}
		}
		

		listarGrillaInit();
		selectRegistroDocumento=null;
		valuePendiente();
		ultimodespacho=getPanelDocumentoMesaPartesServiceImpl().obtenerUltimoDespacho();
		
	}

	public String obtenerRutaImagen(String extension){
		String rutaimagen = "";
		
		for(int i=0;i<listaExtensionArchivos.size();i++){
			if(listaExtensionArchivos.get(i).getExt().equals(extension)){
				rutaimagen=listaExtensionArchivos.get(i).getRutaIcono();
			}
			
		}
		
		return rutaimagen;
	}
	
	public int obtenerIdExtensionArchivo(String extension){
		int idExtension = 0;
		
		for(int i=0;i<listaExtensionArchivos.size();i++){
			if(listaExtensionArchivos.get(i).getExt().equals(extension)){
				idExtension=listaExtensionArchivos.get(i).getIdExtensionArchivo();
			}
			
		}
		
		return idExtension;
	}
	
	public String obtenerExtension(String nombreArchivo){
		int tamanioCadena,posicionCaracter;
		String extension;
		
		
		tamanioCadena=nombreArchivo.length();
		posicionCaracter=nombreArchivo.lastIndexOf(".");
		extension=nombreArchivo.substring(posicionCaracter, tamanioCadena);
		
		return extension.toLowerCase();
	}	
	
	
	

	
	
	public void copyFile(String fileName, InputStream in) {
		
		is.put(fileName, in);
		
		
		Extensionarchivo extFile= new Extensionarchivo();
		extFile.setRutaIcono(obtenerRutaImagen(obtenerExtension(fileName)));
		extFile.setDescExt(fileName);
		
		displayList.add(extFile);
		
		System.out.println("El flujo del fichero se agrego al hashmap");
		
		list2.put(getIdDescripcionDocumento(), is);
		
		
	}
	
	public  void escribirDocumentosServidor(String fileName, InputStream in){
		
		try {
			System.out.println("entroxdireccion:::"+directorioDocumentos+fileName);
			OutputStream out = new FileOutputStream(new File(directorioDocumentos+fileName));
			F.add(out);
			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = in.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}

			in.close();
			out.flush();
			out.close();

			
		} catch (IOException e) {
			System.out.println("errorxx:::"+e.getMessage());
		}
	}
	
	
	public void onCarDrop(Extensionarchivo E) {
		this.archivoDelete=E;
		System.out.println(archivoDelete.getDescExt());
		
        displayList.remove(getArchivoDelete()); 
        is.remove(getArchivoDelete().getDescExt());
    }
	
	

	
	public void cargarArchivosDocumento(int iddoc){
		
		listaAdjuntoDocumento =new ArrayList<Archivodocumento>();
		
		listaAdjuntoDocumento=documentodgaiService.cargarArchivosDocumento(iddoc);
		
		
	}
	
	
	
	
	
	
	public String obtenerCorreoXid(String cadena){
		String correo = "";
		
		for(int i=0;i<getListUsuarios().size();i++){
			String cadena2=getListUsuarios().get(i).getNombres()+" "+getListUsuarios().get(i).getApellidopat()+" "+getListUsuarios().get(i).getApellidomat();
			System.out.println("---------->"+cadena2);
			
			if(cadena2.equals(cadena)){
				System.out.println("Entro if");
				
			correo=	getListUsuarios().get(i).getEmailusr();
			}
			
		}
		
		return correo;
	}
	

	
	public IMonitoreoMesaPartesService getPanelDocumentoMesaPartesServiceImpl() {
		return panelDocumentoMesaPartesServiceImpl;
	}


	public void setPanelDocumentoMesaPartesServiceImpl(
			IMonitoreoMesaPartesService panelDocumentoMesaPartesServiceImpl) {
		this.panelDocumentoMesaPartesServiceImpl = panelDocumentoMesaPartesServiceImpl;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	public Object getNroExternal() {
		return nroExternal;
	}


	public void setNroExternal(Object nroExternal) {
		this.nroExternal = nroExternal;
	}


	public Object getNroInternal() {
		return nroInternal;
	}


	public void setNroInternal(Object nroInternal) {
		this.nroInternal = nroInternal;
	}


	public List<Flujodoc> getListFLujoDocumentosRegInit() {
		return listFLujoDocumentosRegInit;
	}


	public void setListFLujoDocumentosRegInit(
			List<Flujodoc> listFLujoDocumentosRegInit) {
		this.listFLujoDocumentosRegInit = listFLujoDocumentosRegInit;
	}


	public List<Documento> getListDocumentosRegInit() {
		return listDocumentosRegInit;
	}


	public void setListDocumentosRegInit(List<Documento> listDocumentosRegInit) {
		this.listDocumentosRegInit = listDocumentosRegInit;
	}


	public Documento[] getSelectRegistrosDocumentos() {
		return selectRegistrosDocumentos;
	}


	public void setSelectRegistrosDocumentos(Documento[] selectRegistrosDocumentos) {
		this.selectRegistrosDocumentos = selectRegistrosDocumentos;
	}


	public Documento getSelectRegistroDocumento() {
		return selectRegistroDocumento;
	}


	public void setSelectRegistroDocumento(Documento selectRegistroDocumento) {
		this.selectRegistroDocumento = selectRegistroDocumento;
	}


	public CompDataModelDocumento getCompDataModelDocumento() {
		return compDataModelDocumento;
	}


	public void setCompDataModelDocumento(
			CompDataModelDocumento compDataModelDocumento) {
		this.compDataModelDocumento = compDataModelDocumento;
	}


	public List<Documento> getListDocumentosRegInitAtendido() {
		return listDocumentosRegInitAtendido;
	}


	public void setListDocumentosRegInitAtendido(
			List<Documento> listDocumentosRegInitAtendido) {
		this.listDocumentosRegInitAtendido = listDocumentosRegInitAtendido;
	}


	public String getValCommandLink() {
		return valCommandLink;
	}
	

	public Boolean getValDirectora() {
		return valDirectora;
	}


	public void setValDirectora(Boolean valDirectora) {
		this.valDirectora = valDirectora;
	}


	public void setValCommandLink(String valCommandLink) {
		this.valCommandLink = valCommandLink;
	}


	public String getNroPendiente() {
		return nroPendiente;
	}


	public void setNroPendiente(String nroPendiente) {
		this.nroPendiente = nroPendiente;
	}


	public String getNroAtendido() {
		return nroAtendido;
	}


	public void setNroAtendido(String nroAtendido) {
		this.nroAtendido = nroAtendido;
	}

	// public String getNroRechazado() {
	// return nroRechazado;
	// }
	//
	//
	// public void setNroRechazado(String nroRechazado) {
	// this.nroRechazado = nroRechazado;
	// }


	public String getMesActual() {
		return mesActual;
	}


	public void setMesActual(String mesActual) {
		this.mesActual = mesActual;
	}


	public int getIddocumento() {
		return iddocumento;
	}


	public void setIddocumento(int iddocumento) {
		this.iddocumento = iddocumento;
	}


	public String getDescripcionDocumento() {
		return descripcionDocumento;
	}

	public void setDescripcionDocumento(String descripcionDocumento) {
		this.descripcionDocumento = descripcionDocumento;
	}

	public List<Usuario> getListaUsuarioSeleccionados() {
		return listaUsuarioSeleccionados;
	}


	public void setListaUsuarioSeleccionados(List<Usuario> listaUsuarioSeleccionados) {
		this.listaUsuarioSeleccionados = listaUsuarioSeleccionados;
	}

	public List<Usuario> getListUsuarios() {
		return listUsuarios;
	}

	public void setListUsuarios(List<Usuario> listUsuarios) {
		this.listUsuarios = listUsuarios;
	}

	public IAuditoriaService getPanelAuditoriaServiceImpl() {
		return panelAuditoriaServiceImpl;
	}

	public void setPanelAuditoriaServiceImpl(
			IAuditoriaService panelAuditoriaServiceImpl) {
		this.panelAuditoriaServiceImpl = panelAuditoriaServiceImpl;
	}

	public int getIndicesalvador() {
		return indicesalvador;
	}

	public void setIndicesalvador(int indicesalvador) {
		this.indicesalvador = indicesalvador;
	}

	public String getNombreCompletoSeleccionado() {
		return nombreCompletoSeleccionado;
	}

	public void setNombreCompletoSeleccionado(String nombreCompletoSeleccionado) {
		this.nombreCompletoSeleccionado = nombreCompletoSeleccionado;
	}

	public Usuario getSelectRegistroUsuario() {
		return selectRegistroUsuario;
	}

	public void setSelectRegistroUsuario(Usuario selectRegistroUsuario) {
		this.selectRegistroUsuario = selectRegistroUsuario;
	}

	public String getRutaDocumento() {
		return rutaDocumento;
	}

	public void setRutaDocumento(String rutaDocumento) {
		this.rutaDocumento = rutaDocumento;
	}

	public String getDirectorioDocumentos() {
		return directorioDocumentos;
	}

	public void setDirectorioDocumentos(String directorioDocumentos) {
		this.directorioDocumentos = directorioDocumentos;
	}

	public List<OutputStream> getF() {
		return F;
	}

	public void setF(List<OutputStream> f) {
		F = f;
	}

	public int getIdDescripcionDocumento() {
		return idDescripcionDocumento;
	}

	public void setIdDescripcionDocumento(int idDescripcionDocumento) {
		this.idDescripcionDocumento = idDescripcionDocumento;
	}

	public List<Extensionarchivo> getListaExtensionArchivos() {
		return listaExtensionArchivos;
	}

	public void setListaExtensionArchivos(
			List<Extensionarchivo> listaExtensionArchivos) {
		this.listaExtensionArchivos = listaExtensionArchivos;
	}

	public List<Extensionarchivo> getDisplayList() {
		return displayList;
	}

	public void setDisplayList(List<Extensionarchivo> displayList) {
		this.displayList = displayList;
	}

	public Extensionarchivo getArchivoDelete() {
		return archivoDelete;
	}

	public void setArchivoDelete(Extensionarchivo archivoDelete) {
		this.archivoDelete = archivoDelete;
	}

	public String getUltimodespacho() {
		return ultimodespacho;
	}

	public void setUltimodespacho(String ultimodespacho) {
		this.ultimodespacho = ultimodespacho;
	}

	public UsuarioManagedBean getUserMB() {
		return userMB;
	}

	public void setUserMB(UsuarioManagedBean userMB) {
		this.userMB = userMB;
	}

	public String getNroPendienteMes() {
		return nroPendienteMes;
	}

	public void setNroPendienteMes(String nroPendienteMes) {
		this.nroPendienteMes = nroPendienteMes;
	}

	public String getNroAtendidoMes() {
		return nroAtendidoMes;
	}

	public void setNroAtendidoMes(String nroAtendidoMes) {
		this.nroAtendidoMes = nroAtendidoMes;
	}

	public String getNroRechazadoMes() {
		return nroRechazadoMes;
	}

	public void setNroRechazadoMes(String nroRechazadoMes) {
		this.nroRechazadoMes = nroRechazadoMes;
	}

	// public String getNroAprobado() {
	// return nroAprobado;
	// }
	//
	// public void setNroAprobado(String nroAprobado) {
	// this.nroAprobado = nroAprobado;
	// }
	//
	// public String getNroProceso() {
	// return nroProceso;
	// }
	//
	// public void setNroProceso(String nroProceso) {
	// this.nroProceso = nroProceso;
	// }

	public String getNroProcesoMes() {
		return NroProcesoMes;
	}

	public void setNroProcesoMes(String nroProcesoMes) {
		NroProcesoMes = nroProcesoMes;
	}

	public String getNroAprobadoMes() {
		return NroAprobadoMes;
	}

	public void setNroAprobadoMes(String nroAprobadoMes) {
		NroAprobadoMes = nroAprobadoMes;
	}

	public int getMesActualcapturado() {
		return mesActualcapturado;
	}

	public void setMesActualcapturado(int mesActualcapturado) {
		this.mesActualcapturado = mesActualcapturado;
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

	public List<Documento> getFiltroCompDataModelDocumento() {
		return filtroCompDataModelDocumento;
	}

	public void setFiltroCompDataModelDocumento(
			List<Documento> filtroCompDataModelDocumento) {
		this.filtroCompDataModelDocumento = filtroCompDataModelDocumento;
	}



}

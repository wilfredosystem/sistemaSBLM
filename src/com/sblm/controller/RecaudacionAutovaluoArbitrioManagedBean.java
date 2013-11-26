package com.sblm.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import com.sblm.model.Arbitrio;
import com.sblm.model.Archivodocumento;
import com.sblm.model.Contrato;
import com.sblm.model.Detallearbitrio;
import com.sblm.model.Extensionarchivo;
import com.sblm.model.Inquilino;
import com.sblm.model.Uso;
import com.sblm.model.Usuario;
import com.sblm.service.IMonitoreoMesaPartesService;
import com.sblm.service.IRecaudacionAutovaluoArbitrioService;
import com.sblm.util.Almanaque;

import com.sblm.util.FuncionesHelper;

@ManagedBean(name = "recaudacionautovaluoarbitrioMB")
@ViewScoped
public class RecaudacionAutovaluoArbitrioManagedBean implements Serializable {
	private static final long serialVersionUID = 5524190003746598593L;
	@ManagedProperty(value = "#{recaudacionautovaluoarbitrioService}")
	private transient IRecaudacionAutovaluoArbitrioService recaudacionautovaluoarbitrioService;
	private List<Detallearbitrio> listadetalleArbitrio;
	private List<Arbitrio> listaArbitriosConsulta;
	private List<Arbitrio> listafiltroArbitriosConsulta;	
	
	private List<Contrato> listaContratos;
	private Contrato contratoSeleccionado;
	
	private Arbitrio arbitrioSeleccionadoConsulta;
	private Arbitrio arbitrio;
	private Usuario usuarioCreador;
	
	private Detallearbitrio detalleArbitrio;
	private int idarbitrioGlobal=0;
	
	private List<String> listameses= new ArrayList<String>();
	private List<String> listAnio= new ArrayList<String>();
	private List<Usuario> listaTodosUsuarios;
	
	
	/******cargado de archivos****/
	Map<String, InputStream> carousel;
	private List<Archivodocumento> displayList;
	
	private List<Archivodocumento> displayListTmp;
	private List<Extensionarchivo> listaExtensionArchivos;
	
	 List<String> fileList;
	 private static final String OUTPUT_ZIP_FILE = "C:\\MyFile.zip";
	 private static final String SOURCE_FOLDER = FuncionesHelper.directorioPrincipalLibreria;
	
	@ManagedProperty(value = "#{panelDocumentoMesaPartesServiceImpl}")
	private transient IMonitoreoMesaPartesService panelDocumentoMesaPartesServiceImpl;
	private Archivodocumento archivoDelete;

	
	@ManagedProperty(value = "#{usuarioMB}")
	UsuarioManagedBean usuarioMB;
	
	@PostConstruct
	public void init(){
		listaContratos= new ArrayList<Contrato>();
		listaContratos=recaudacionautovaluoarbitrioService.listaContratos();
		
		obtenerDetalleArbitrio(0);
		listarArbitrioConsulta();
		inicializarCarousel();
		listaTodosUsuarios = new ArrayList<Usuario>();
		listaTodosUsuarios=usuarioMB.getUsuariosdgi();
		displayBlank();
		arbitrio = new Arbitrio();
		listafiltroArbitriosConsulta=listaArbitriosConsulta;
	}
	
	
	public RecaudacionAutovaluoArbitrioManagedBean(){
		listameses=Almanaque.listaMesesAlmanaque();
		listAnio=Almanaque.listaAniosAlmanaque();
		
	}

	
	
	public void obtenerArbitrio(int idarbitrio){
		if(idarbitrio!=0){
			
			for (int i = 0; i < listaArbitriosConsulta.size(); i++) {
				if(idarbitrio==listaArbitriosConsulta.get(i).getIdarbitrio()){
					
					arbitrio=listaArbitriosConsulta.get(i);
					contratoSeleccionado=listaArbitriosConsulta.get(i).getContrato();
					obtenerDetalleArbitrio(arbitrio.getIdarbitrio());
					buscandoUsuarioCreador(listaArbitriosConsulta.get(i).getIdusuariocreador());
				}
			}
					setIdarbitrioGlobal(arbitrio.getIdarbitrio());
		}
		
	}
	
	public void obtenerDetalleArbitrio(int idarbitrio){
		
		
			
			if(idarbitrio!=0){
				listadetalleArbitrio= new ArrayList<Detallearbitrio>();
				listadetalleArbitrio=recaudacionautovaluoarbitrioService.obtenerDetalleArbitrio(idarbitrio);
				
				
				displayList= new ArrayList<Archivodocumento>();
				displayList=recaudacionautovaluoarbitrioService.obtenerArchivosArbitrio(idarbitrio);
				
				displayListTmp= new ArrayList<Archivodocumento>();
				
				for (Archivodocumento A : displayList) {
					displayListTmp.add(A);
				}
				
				if(displayList.size()==0){
					displayBlank();
				}
				
			}
		}

	
	public void grabarCabeceraArbitrio(){
		
		//GRABAR ACTUALIZAR
		if(arbitrio.getIdarbitrio()==0){
			
			arbitrio.setIdusuariocreador((Integer)FuncionesHelper.getUsuario());
			arbitrio.setFechacreacion(new Date());
			arbitrio.setTotal(0.0);
			arbitrio.setContrato(contratoSeleccionado);
			
			recaudacionautovaluoarbitrioService.grabarCabeceraArbitrio(arbitrio);
			
			
			messageFacesPrederminado("Éxito", "Se agregó un nuevo arbitrio para el inquilino:"+contratoSeleccionado.getInquilino().getNombrescompletos(), FacesMessage.SEVERITY_INFO);
			
			
			listarArbitrioConsulta();
			
			arbitrio=recaudacionautovaluoarbitrioService.getUltimaCabeceraGrabada();
			
			escribirArchivoAdjuntosBaseDatosyServidor();
			//grabarDetalleArbitrio();
			arbitrio=null;
			contratoSeleccionado=null;
			displayBlank();
		}else{
			
			arbitrio.setIdusuariocreador((Integer)FuncionesHelper.getUsuario());
			arbitrio.setFechacreacion(new Date());
			
			try {
				recaudacionautovaluoarbitrioService.actualizarCabeceraArbitrio(arbitrio);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			
			escribirArchivoAdjuntosBaseDatosyServidorACTUALIZAR();
			
			messageFacesPrederminado("Éxito", "Se actualizó el arbitrio para el inquilino:"+contratoSeleccionado.getInquilino().getNombrescompletos(), FacesMessage.SEVERITY_INFO);
			
			arbitrio=null;
			contratoSeleccionado=null;
			
			displayBlank();
			listarArbitrioConsulta();
		}
		
	}
	
	
	public void escribirArchivoAdjuntosBaseDatosyServidorACTUALIZAR(){
		
        Boolean flag=false;
		 /*Buscamos si se realizo una eliminacion*/
        for(int i=0;i<displayListTmp.size();i++){
        	flag=false;
            for(int k=0;k<displayList.size();k++){
               
                if(displayList.get(k).getRuta().equals(displayListTmp.get(i).getRuta())){
                    flag=true; 
                    //OK!
                }
                       }
                if(!flag){
                 recaudacionautovaluoarbitrioService.eliminarArchivoDocumentoNoEncontrado(displayListTmp.get(i).getIdarchivodocumento());
                 
                 File file=new File(FuncionesHelper.directorioPrincipalLibreria+displayListTmp.get(i).getRuta());
                 file.delete();
                 //eliminamos registro en base de datos de servidor, previamente configurada en cascada
                }
        
        }
        
        escribirArchivoAdjuntosBaseDatosyServidor();
		
	}
	
	public void escribirArchivoAdjuntosBaseDatosyServidor(){
		
		String key;
		InputStream value;
		
		Iterator iterator = carousel.keySet().iterator();
		while (iterator.hasNext()) {
		    key = (String) iterator.next();
		    value = (InputStream) carousel.get(key);
		    System.out.println(key + ":" + value);
		    escribirArchivo(key,value);
		    
		    Extensionarchivo extarchivo = new Extensionarchivo();
		    extarchivo.setIdExtensionArchivo(obtenerIdExtensionArchivo(obtenerExtension(key)));
		    
		    Archivodocumento archivodocu= new Archivodocumento();
		    archivodocu.setArbitrio(arbitrio);
		    archivodocu.setRuta(key);
		    archivodocu.setTipodocumento("arbitrio");
		    archivodocu.setFechaCreacion(new Date());
		    archivodocu.setAutorArchivo((Integer)(FuncionesHelper.getUsuario()));
		    archivodocu.setUsuarioCreador(usuarioMB.getUsuariologueado().getNombres()+" "+usuarioMB.getUsuariologueado().getApellidopat());
		    archivodocu.setExtensionarchivo(extarchivo);
		    
		    recaudacionautovaluoarbitrioService.grabarArchivosAdjuntos(archivodocu);
		}
		
		inicializarCarousel();
	}
	
	public void grabarDetalleArbitrio(){
		System.out.println("EWNTRO");
		
		
		//obtener suma actual
		Double totalActual=recaudacionautovaluoarbitrioService.obtenerTotalActual(arbitrio.getIdarbitrio())+detalleArbitrio.getMontopagado();
		
		detalleArbitrio.setArbitrio(arbitrio);
		detalleArbitrio.setPeriodo(detalleArbitrio.getPeriodoinicial()+" al "+detalleArbitrio.getPeriodoinicial()+" del "+detalleArbitrio.getPeriodoanio());
		recaudacionautovaluoarbitrioService.grabarDetalleArbitrio(detalleArbitrio);
		recaudacionautovaluoarbitrioService.actualizarSumaTotalDetalleArbitrio(arbitrio.getIdarbitrio(),totalActual);
		listarArbitrioConsulta();
		messageFacesPrederminado("Éxito", "Se agregó nuevo pago al arbitrio", FacesMessage.SEVERITY_INFO);
		
		obtenerDetalleArbitrio(getIdarbitrioGlobal());
		detalleArbitrio=null;
		
	}
	
	
	public void displayBlank(){
		displayList= new ArrayList<Archivodocumento>();
		
		Extensionarchivo ea= new Extensionarchivo();
		ea.setRutaIcono("blank.jpg");
		
		Archivodocumento extFile= new Archivodocumento();
		extFile.setRuta("");
		extFile.setExtensionarchivo(ea);
		
		displayList.add(extFile);
	}
	
	
	
	public void buscandoUsuarioCreador(int idUsuarioCreador){
		for(int i=0;i<listaTodosUsuarios.size();i++){
			if(listaTodosUsuarios.get(i).getIdusuario()==idUsuarioCreador){
				usuarioCreador.setApellidopat(listaTodosUsuarios.get(i).getApellidopat());
				usuarioCreador.setNombres(listaTodosUsuarios.get(i).getNombres());
				usuarioCreador.setRutaimgusr(listaTodosUsuarios.get(i).getRutaimgusr());
			}
		}
		
	}
	
	public void listarArbitrioConsulta(){
		System.out.println("Paso listado");
		listaArbitriosConsulta= new ArrayList<Arbitrio>();
		listaArbitriosConsulta=recaudacionautovaluoarbitrioService.listarArbitrioConsulta();
		
		
	}
	
	public void messageFacesPrederminado(String titulo, String mensaje, Severity severityFatal){
		FacesContext contextFaces = FacesContext.getCurrentInstance(); 
		contextFaces.addMessage(null, new FacesMessage(severityFatal,titulo,mensaje));
	}
	
	public void validarIdArbitrioSeleccionado(){
		
		RequestContext contextRequest = RequestContext.getCurrentInstance(); 
		
		if(getIdarbitrioGlobal()!=0){
			
			contextRequest.execute("dlgDetallePago.show()");
		}else {
			messageFacesPrederminado("Advertencia",  "Elija un arbitrio de la pestaña Consulta", FacesMessage.SEVERITY_FATAL);
			
		}
		
	}
	


	
	public  void escribirArchivo(String fileName, InputStream in){
		
		try {
			System.out.println("entroxdireccion:::"+FuncionesHelper.directorioPrincipalLibreria+fileName);
			OutputStream out = new FileOutputStream(new File(FuncionesHelper.directorioPrincipalLibreria+fileName));
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
	
	public void cargarArchivo(FileUploadEvent event) throws IOException {
		Boolean flag=false;
		
		
		for(int i=0;i<displayList.size();i++){
			if(displayList.get(i).getRuta().equals("") && displayList.get(i).getExtensionarchivo().getRutaIcono().equals("blank.jpg")){
				displayList.remove(i);
			}
		}
		
		
		
		for(int i=0;i<displayList.size();i++){
			if(displayList.get(i).getRuta().equals(event.getFile().getFileName()) ){
				messageFacesPrederminado("Advertencia", "El arhivo "+event.getFile().getFileName()+" ya se encuentra en la lista", FacesMessage.SEVERITY_WARN);
				flag=true;
			}
			
		}
		
		
		if(!flag){
			
			carousel.put(event.getFile().getFileName(), event.getFile().getInputstream());
			
		    Extensionarchivo ea= new Extensionarchivo();
		    ea.setRutaIcono(obtenerRutaImagen(obtenerExtension(event.getFile().getFileName())));
		    
		    Archivodocumento extFile= new Archivodocumento();
		    extFile.setRuta(event.getFile().getFileName());
		    extFile.setExtensionarchivo(ea);
		    
		    displayList.add(extFile);
		    
		}
		
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

	
	public String obtenerRutaImagen(String extension){
		String rutaimagen = "";
		
		for(int i=0;i<listaExtensionArchivos.size();i++){
			if(listaExtensionArchivos.get(i).getExt().equals(extension)){
				rutaimagen=listaExtensionArchivos.get(i).getRutaIcono();
			}
			
		}
		
		return rutaimagen;
	}
	
	public String obtenerExtension(String nombreArchivo){
		int tamanioCadena,posicionCaracter;
		String extension;
		
		
		tamanioCadena=nombreArchivo.length();
		posicionCaracter=nombreArchivo.lastIndexOf(".");
		extension=nombreArchivo.substring(posicionCaracter, tamanioCadena);
		
		return extension.toLowerCase();
	}
	
	public void eliminarArchivodeLista() {
        displayList.remove(getArchivoDelete());
        carousel.remove(getArchivoDelete().getRuta());
    }
	
	public void inicializarCarousel(){
		
		displayList= new ArrayList<Archivodocumento>();
		listaExtensionArchivos = new ArrayList<Extensionarchivo>();
		listaExtensionArchivos = panelDocumentoMesaPartesServiceImpl.getListaExtensionArhivos();
		carousel = new HashMap<String, InputStream>();
		displayListTmp= new ArrayList<Archivodocumento>();
	}
	
	public IRecaudacionAutovaluoArbitrioService getRecaudacionautovaluoarbitrioService() {
		return recaudacionautovaluoarbitrioService;
	}

	public void setRecaudacionautovaluoarbitrioService(
			IRecaudacionAutovaluoArbitrioService recaudacionautovaluoarbitrioService) {
		this.recaudacionautovaluoarbitrioService = recaudacionautovaluoarbitrioService;
	}

	public Arbitrio getArbitrio() {
//		if (arbitrio == null) {
//			arbitrio = new Arbitrio();
//		}
		return arbitrio;
	}

	public void setArbitrio(Arbitrio arbitrio) {
		this.arbitrio = arbitrio;
	}

	public List<Detallearbitrio> getListadetalleArbitrio() {
		return listadetalleArbitrio;
	}

	public void setListadetalleArbitrio(List<Detallearbitrio> listadetalleArbitrio) {
		this.listadetalleArbitrio = listadetalleArbitrio;
	}



	public List<Arbitrio> getListaArbitriosConsulta() {
		listaArbitriosConsulta=recaudacionautovaluoarbitrioService.listarArbitrioConsulta();
		return listaArbitriosConsulta;
	}

	public void setListaArbitriosConsulta(List<Arbitrio> listaArbitriosConsulta) {
		this.listaArbitriosConsulta = listaArbitriosConsulta;
	}

	public Arbitrio getArbitrioSeleccionadoConsulta() {
		return arbitrioSeleccionadoConsulta;
	}

	public void setArbitrioSeleccionadoConsulta(
			Arbitrio arbitrioSeleccionadoConsulta) {
		this.arbitrioSeleccionadoConsulta = arbitrioSeleccionadoConsulta;
	}

	public int getIdarbitrioGlobal() {
		return idarbitrioGlobal;
	}

	public void setIdarbitrioGlobal(int idarbitrioGlobal) {
		this.idarbitrioGlobal = idarbitrioGlobal;
	}

	public Detallearbitrio getDetalleArbitrio() {
		if (detalleArbitrio == null) {
			detalleArbitrio = new Detallearbitrio();
		}
		return detalleArbitrio;
	}

	public void setDetalleArbitrio(Detallearbitrio detalleArbitrio) {
		this.detalleArbitrio = detalleArbitrio;
	}

	public List<Arbitrio> getListafiltroArbitriosConsulta() {
		return listafiltroArbitriosConsulta;
	}

	public void setListafiltroArbitriosConsulta(
			List<Arbitrio> listafiltroArbitriosConsulta) {
		this.listafiltroArbitriosConsulta = listafiltroArbitriosConsulta;
	}

	public Usuario getUsuarioCreador() {
		if (usuarioCreador == null) {
			usuarioCreador = new Usuario();
		}
		return usuarioCreador;
	}

	public void setUsuarioCreador(Usuario usuarioCreador) {
		this.usuarioCreador = usuarioCreador;
	}

	public UsuarioManagedBean getUsuarioMB() {
		return usuarioMB;
	}

	public void setUsuarioMB(UsuarioManagedBean usuarioMB) {
		this.usuarioMB = usuarioMB;
	}

	public List<String> getListameses() {
		return listameses;
	}

	public void setListameses(List<String> listameses) {
		this.listameses = listameses;
	}

	public List<String> getListAnio() {
		return listAnio;
	}

	public void setListAnio(List<String> listAnio) {
		this.listAnio = listAnio;
	}



	public Map<String, InputStream> getCarousel() {
		return carousel;
	}

	public void setCarousel(Map<String, InputStream> carousel) {
		this.carousel = carousel;
	}

	public List<Extensionarchivo> getListaExtensionArchivos() {
		return listaExtensionArchivos;
	}

	public void setListaExtensionArchivos(
			List<Extensionarchivo> listaExtensionArchivos) {
		this.listaExtensionArchivos = listaExtensionArchivos;
	}

	public IMonitoreoMesaPartesService getPanelDocumentoMesaPartesServiceImpl() {
		return panelDocumentoMesaPartesServiceImpl;
	}

	public void setPanelDocumentoMesaPartesServiceImpl(
			IMonitoreoMesaPartesService panelDocumentoMesaPartesServiceImpl) {
		this.panelDocumentoMesaPartesServiceImpl = panelDocumentoMesaPartesServiceImpl;
	}

	public Archivodocumento getArchivoDelete() {
		return archivoDelete;
	}


	public void setArchivoDelete(Archivodocumento archivoDelete) {
		this.archivoDelete = archivoDelete;
	}


	public List<Usuario> getListaTodosUsuarios() {
		return listaTodosUsuarios;
	}


	public void setListaTodosUsuarios(List<Usuario> listaTodosUsuarios) {
		this.listaTodosUsuarios = listaTodosUsuarios;
	}


	public List<Archivodocumento> getDisplayList() {
		return displayList;
	}


	public void setDisplayList(List<Archivodocumento> displayList) {
		this.displayList = displayList;
	}


	public List<Archivodocumento> getDisplayListTmp() {
		return displayListTmp;
	}


	public void setDisplayListTmp(List<Archivodocumento> displayListTmp) {
		this.displayListTmp = displayListTmp;
	}
	
	
    public List<Contrato> getListaContratos() {
		return listaContratos;
	}


	public void setListaContratos(List<Contrato> listaContratos) {
		this.listaContratos = listaContratos;
	}


	public Contrato getContratoSeleccionado() {
		return contratoSeleccionado;
	}


	public void setContratoSeleccionado(Contrato contratoSeleccionado) {
		this.contratoSeleccionado = contratoSeleccionado;
	}


	/**
     * Zip it
     * @param zipFile output ZIP file location
     */
    public void zipIt(String zipFile){
 
     byte[] buffer = new byte[1024];
 
     try{
 
    	FileOutputStream fos = new FileOutputStream(zipFile);
    	ZipOutputStream zos = new ZipOutputStream(fos);
 
    	System.out.println("Output to Zip : " + zipFile);
 
    	for(String file : this.fileList){
 
    		System.out.println("File Added : " + file);
    		ZipEntry ze= new ZipEntry(file);
        	zos.putNextEntry(ze);
 
        	FileInputStream in = 
                       new FileInputStream(SOURCE_FOLDER + File.separator + file);
 
        	int len;
        	while ((len = in.read(buffer)) > 0) {
        		zos.write(buffer, 0, len);
        	}
 
        	in.close();
    	}
 
    	zos.closeEntry();
    	//remember close it
    	zos.close();
 
    	System.out.println("Done");
    }catch(IOException ex){
       ex.printStackTrace();   
    }
   }
 
    /**
     * Traverse a directory and get all files,
     * and add the file into fileList  
     * @param node file or directory
     */
    public  void generateFileList(File node){
 
    	//add file only
	if(node.isFile()){
		fileList.add(generateZipEntry(node.getAbsoluteFile().toString()));
	}
 
	if(node.isDirectory()){
		String[] subNote = node.list();
		for(String filename : subNote){
			generateFileList(new File(node, filename));
		}
	}
 
    }
 
    /**
     * Format the file path for zip
     * @param file file path
     * @return Formatted file path
     */
    private String generateZipEntry(String file){
    	return file.substring(SOURCE_FOLDER.length(), file.length());
    }
    
	JasperPrint jasperPrint;
    public void init2() throws JRException{
        JRBeanCollectionDataSource beanCollectionDataSource=new JRBeanCollectionDataSource(listafiltroArbitriosConsulta);
		String  reportPath=  "C://reportesjasper//reporteSimpleArbitrio.jasper";
		jasperPrint=JasperFillManager.fillReport(reportPath, new HashMap(),beanCollectionDataSource);
    }
    
    
   public void PDF(ActionEvent actionEvent) throws JRException, IOException{
       init2();
      FacesContext context = FacesContext.getCurrentInstance();
       try {
    	   
    	   ByteArrayOutputStream baos = new ByteArrayOutputStream();
    	   JRPdfExporter exporter = new JRPdfExporter();
    	   
    	   exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);

    	   exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);

    	   exporter.exportReport();
    	   
    	   byte[] bytes = baos.toByteArray();
    	   
    	   if (bytes != null && bytes.length > 0) {
        	   HttpServletResponse httpServletResponse=(HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
        	   httpServletResponse.setContentType("application/pdf"); 
        	   httpServletResponse.addHeader("Content-disposition", "attachment; filename=report.pdf");

        	   ServletOutputStream servletOutputStream=httpServletResponse.getOutputStream();  
    	       JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
    	       
    	       context.getApplication().getStateManager().saveView(context);
    	       
    	       context.responseComplete();
    	   }else {
			System.out.println("vacio");
		}
    	   
    	   

    	       
	} catch (Exception e) {
		System.out.println(e.getMessage());
	}
       
       
   }
   
   public void Excel(ActionEvent actionEvent) throws JRException, IOException {
		init2();
		HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext
				.getCurrentInstance().getExternalContext().getResponse();

		
		httpServletResponse.addHeader(
				"Content-disposition",
				"attachment; filename=reporte.xlsx");
		ServletOutputStream servletOutputStream = httpServletResponse
				.getOutputStream();
		JRXlsxExporter docxExporter = new JRXlsxExporter();
		docxExporter
				.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		docxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
				servletOutputStream);
		docxExporter.exportReport();
		FacesContext.getCurrentInstance().responseComplete();
	}
	
}

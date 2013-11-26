package com.sblm.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.sblm.model.Archivodocumento;
import com.sblm.model.Documento;
import com.sblm.model.Extensionarchivo;
import com.sblm.model.Usuario;
import com.sblm.service.IDocumentoDgaiService;
import com.sblm.util.Almanaque;
import com.sblm.util.CompDataModelDocumento;

@ManagedBean(name = "documentodgaiMB")
@ViewScoped
public class DocumentoDgaiManagedBean implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{documentodgaiService}")
	private transient IDocumentoDgaiService documentodgaiService;
	private Documento documento;
	private List<Documento> documentos;
	
	
	private List<Documento> documentoscapturados;
	
	private Documento[] listadocumentos;
	private Documento documentocapturado;
	int totalpendientes;
	private String mesActual;
	private Documento[] listadocumentosrechazados;
	private String ultimoExpedienteDerivado;
	private String ultimoExpedienteDespachado;
	
	private int iddocumento;
	private String descripcionDocumento;
	private int idDescripcionDocumento;
	private List<Documento> listAllDocumentos;
	private Documento documentoseleccionado;
	private Date fechaInicio;
	private Date fechaFin;
	
	private List<Archivodocumento> listaAdjuntoDocumento;
	private List<Documento> listaDocumentosSinDerivar;
	/****/
	private StreamedContent filedownload; 
	
	@PostConstruct
	public void initObjects(){
		listarDocumentoSinDerivar();
		totalPendientesDerivacion();
		Almanaque almanaque= new Almanaque();
		mesActual=almanaque.obtenerMesActual();
		

		
		
		if(getDocumentodgaiService().obtenerUltimodocumento()==null){
			ultimoExpedienteDerivado="no existen expedientes derivados...";
		}else{
			ultimoExpedienteDerivado= getDocumentodgaiService().obtenerUltimodocumento().getTitulo();
		}
		
		if(getDocumentodgaiService().obtenerUltimodocumentoDespachado()==null){
			ultimoExpedienteDespachado="no existen expedientes despachados...";
		}else{
			ultimoExpedienteDespachado= getDocumentodgaiService().obtenerUltimodocumentoDespachado().getTitulo();
		}
		
		listarAllDocumentos();
		
		
		
	}
	
	
	public void listarDocumentoSinDerivar(){
		listaDocumentosSinDerivar = new ArrayList<Documento>();
		listaDocumentosSinDerivar=getDocumentodgaiService().listaDocumentosSinDerivar();
	}
	
	
	public void descargarArchivo(Archivodocumento archivoCapturado){
		
		System.out.println(":::::::::...rutax::"+archivoCapturado.getRuta());
		String curDir = System.getProperty("user.dir");
		String direccion = curDir+"\\webapps\\sistemaSBLM\\resources\\documents\\"+archivoCapturado.getRuta();
		
		
//		 String direccion= "C:\\archivosxxx\\"+archivoCapturado.getRuta();
		
		direccion =
		 "C:\\Users\\Informatica\\Desktop\\WokspaceGit\\sistemaSBLM\\WebContent\\resources\\documents\\"+archivoCapturado.getRuta();
		
		 
		 String extension = "";
		 int i = direccion.lastIndexOf('.');
		    if (i > 0) {
		        extension = direccion.substring(i+1);
		    }
		   
		    System.out.println(":::::::::...extension::"+extension);
		    System.out.println(":::::::::...direccion::"+direccion);
		    InputStream stream;
			try {
				stream = new FileInputStream(direccion);
				filedownload = new DefaultStreamedContent(stream, "application/"+extension+"",archivoCapturado.getRuta());  
			//	filedownload = new DefaultStreamedContent(stream, "application/"+extension+"", "descargar."+extension+"");  
			} catch (FileNotFoundException e) {
				
				System.out.println("errorxxxx::"+e.getMessage());
				 FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia",  new String("No existe el archivo en el servidor"));  
		          
			        FacesContext.getCurrentInstance().addMessage(null, message);  
			} 
	}
	public void pasarParametros(){
		//documentoscapturados=documentos;
		FacesContext facesContext = FacesContext.getCurrentInstance(); 
		Map params = facesContext.getExternalContext().getRequestParameterMap(); 
		 documentocapturado= (Documento) params.get("nombreParametro"); 
	}
	
	public void listarAllDocumentos(){
		listAllDocumentos= new ArrayList<Documento>();
		listAllDocumentos=documentodgaiService.getAllDocumentos();
		
	}
	
	public void cargarArchivosDocumento(){
		
				listaAdjuntoDocumento =new ArrayList<Archivodocumento>();
		
			//	setDescripcionDocumento(documentos.get(i).getDescripcion());
				listaAdjuntoDocumento=documentodgaiService.cargarArchivosDocumento(documentoseleccionado.getIddocumento());
		
		
	}
	
	public void cargarArchivosDocumento(int iddoc){
		
		RequestContext context = RequestContext.getCurrentInstance();  
		
		listaAdjuntoDocumento =new ArrayList<Archivodocumento>();
		
		listaAdjuntoDocumento=documentodgaiService.cargarArchivosDocumento(iddoc);
		
		
	}
	
	public void seleccionIdDocumento(){
		for(int i=0;i<listaDocumentosSinDerivar.size();i++){
			if(listaDocumentosSinDerivar.get(i).getIddocumento()==getIddocumento()){
				setDescripcionDocumento(listaDocumentosSinDerivar.get(i).getDescripcion());
				listaAdjuntoDocumento=documentodgaiService.cargarArchivosDocumento(listaDocumentosSinDerivar.get(i).getIddocumento());
			}
		}
	}

	public void busquedaIntervaloFecha(){
		listAllDocumentos=documentodgaiService.busquedaIntervaloFecha(getFechaFin(),getFechaInicio());
		System.out.println("Buscando ...");
		System.out.println(listAllDocumentos.size());
	}
	
	 public void actualizarDocumentoCapturado() { 
		 
		getDocumentocapturado();
		System.out.println("documentocapturado::"+documentocapturado.getIddocumento());
	 }
	 
	 public void totalPendientesDerivacion() { 
		 
		 totalpendientes= getDocumentodgaiService().totalPendientesDerivacion();
	 }
	 
	public Documento getDocumento() {
		if (documento == null) {
			documento = new Documento();
		}

		return documento;
	}
	

	
	public void setearDescripcion(){
		
		if(listadocumentos.length!=0){
			for(int i=0;i<listadocumentos.length;i++){
					
					setDescripcionDocumento(listadocumentos[0].getDescripcion());
					//setDescripcionDocumentoDerivados(listaDerivadosSeleccionados.get(i).getDescripcion());
			}
			
			listaAdjuntoDocumento =new ArrayList<Archivodocumento>();
			listaAdjuntoDocumento=documentodgaiService.cargarArchivosDocumento(listadocumentos[0].getIddocumento());
			
			
			
			}
		


}

	
public void verificarItemSeleccionado(){
		
		
		RequestContext contextRequest = RequestContext.getCurrentInstance(); 
		FacesContext contextFaces = FacesContext.getCurrentInstance(); 
		
		
		
		if(listadocumentos.length!=0){
			contextRequest.execute("dlgDerivacion.show();");	
		}else {
			contextFaces.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"Advertencia", "Seleccione al menos un documento "));
		}
		
    }
	

	
	public void setDocumento(Documento documento) {
		this.documento = documento;
	}
	public List<Documento> getDocumentos() {
		documentos = getDocumentodgaiService().listarDocumentosDgai();
		return documentos;
	}
	public void setDocumentos(List<Documento> documentos) {
		this.documentos = documentos;
	}
	public IDocumentoDgaiService getDocumentodgaiService() {
		return documentodgaiService;
	}
	public void setDocumentodgaiService(IDocumentoDgaiService documentodgaiService) {
		this.documentodgaiService = documentodgaiService;
	}
	public int getTotalpendientes() {
		return totalpendientes;
	}
	public void setTotalpendientes(int totalpendientes) {
		this.totalpendientes = totalpendientes;
	}
	public Documento getDocumentoseleccionado() {
		return documentoseleccionado;
	}
	public void setDocumentoseleccionado(Documento documentoseleccionado) {
		this.documentoseleccionado = documentoseleccionado;
		
	}
	public List<Documento> getDocumentoscapturados() {
		return documentoscapturados;
	}
	public void setDocumentoscapturados(List<Documento> documentoscapturados) {
		this.documentoscapturados = documentoscapturados;
	}
	public Documento[] getListadocumentos() {
		return listadocumentos;
	}
	public void setListadocumentos(Documento[] listadocumentos) {
		this.listadocumentos = listadocumentos;
	}
	public Documento getDocumentocapturado() {
		return documentocapturado;
	}
	public void setDocumentocapturado(Documento documentocapturado) {
		this.documentocapturado = documentocapturado;
	}
	public Documento[] getListadocumentosrechazados() {
		return listadocumentosrechazados;
	}
	public void setListadocumentosrechazados(Documento[] listadocumentosrechazados) {
		this.listadocumentosrechazados = listadocumentosrechazados;
	}
	public String getMesActual() {
		return mesActual;
	}
	public void setMesActual(String mesActual) {
		this.mesActual = mesActual;
	}

	public String getUltimoExpedienteDerivado() {
		return ultimoExpedienteDerivado;
	}

	public void setUltimoExpedienteDerivado(String ultimoExpedienteDerivado) {
		this.ultimoExpedienteDerivado = ultimoExpedienteDerivado;
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
	public List<Documento> getListAllDocumentos() {
		return listAllDocumentos;
	}
	public void setListAllDocumentos(List<Documento> listAllDocumentos) {
		this.listAllDocumentos = listAllDocumentos;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}


	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public List<Archivodocumento> getListaAdjuntoDocumento() {
		return listaAdjuntoDocumento;
	}
	public void setListaAdjuntoDocumento(
			List<Archivodocumento> listaAdjuntoDocumento) {
		this.listaAdjuntoDocumento = listaAdjuntoDocumento;
	}
	public StreamedContent getFiledownload() {
		return filedownload;
	}
	public void setFiledownload(StreamedContent filedownload) {
		this.filedownload = filedownload;
	}

	public int getIdDescripcionDocumento() {
		return idDescripcionDocumento;
	}

	public void setIdDescripcionDocumento(int idDescripcionDocumento) {
		this.idDescripcionDocumento = idDescripcionDocumento;
	}

	public List<Documento> getListaDocumentosSinDerivar() {
		return listaDocumentosSinDerivar;
	}

	public void setListaDocumentosSinDerivar(
			List<Documento> listaDocumentosSinDerivar) {
		this.listaDocumentosSinDerivar = listaDocumentosSinDerivar;
	}

	public String getUltimoExpedienteDespachado() {
		return ultimoExpedienteDespachado;
	}

	public void setUltimoExpedienteDespachado(String ultimoExpedienteDespachado) {
		this.ultimoExpedienteDespachado = ultimoExpedienteDespachado;
	}

	
}

package com.sblm.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.sblm.model.Archivodocumento;
import com.sblm.model.Carta;
import com.sblm.model.Contrato;
import com.sblm.model.Extensionarchivo;
import com.sblm.model.Inquilino;
import com.sblm.model.Usuario;
import com.sblm.service.IMonitoreoMesaPartesService;
import com.sblm.service.IRecaudacionAutovaluoArbitrioService;
import com.sblm.service.IRecaudacionAutovaluoCartaService;
import com.sblm.util.FuncionesHelper;

@ManagedBean(name = "recaudacionautovaluocartaMB")
@ViewScoped
public class RecaudacionAutovaluoCartaManagedBean implements Serializable {
	private static final long serialVersionUID = 5524190003746598593L;

	@ManagedProperty(value = "#{recaudacionautovaluocartaService}")
	private transient IRecaudacionAutovaluoCartaService recaudacionautovaluocartaService;

	@ManagedProperty(value = "#{recaudacionautovaluoarbitrioService}")
	private transient IRecaudacionAutovaluoArbitrioService recaudacionautovaluoarbitrioService;

	private Contrato contratoSeleccionado;
	
	String jravcalle;
	private Carta carta;
	private List<Usuario> listaTodosUsuarios;
	private Usuario usuarioCreador;

	private List<Carta> listaCartaConsultaTOP;
	private List<Carta> listaCartaConsulta;
	private List<Carta> listafiltroCartaConsulta;

	private int idcartaGlobal = 0;

	/****** cargado de archivos ****/
	Map<String, InputStream> carousel;
	private List<Archivodocumento> displayList;

	private List<Archivodocumento> displayListTmp;
	private List<Extensionarchivo> listaExtensionArchivos;
	private Archivodocumento archivoDelete;
	private StreamedContent filedownload;

	List<String> fileList;
	private static final String OUTPUT_ZIP_FILE = FuncionesHelper.directorioPrincipalLibreria
			+ "documentos.zip";
	private static final String SOURCE_FOLDER = FuncionesHelper.directorioPrincipalLibreria;



	@ManagedProperty(value = "#{usuarioMB}")
	UsuarioManagedBean usuarioMB;

	@ManagedProperty(value = "#{panelDocumentoMesaPartesServiceImpl}")
	private transient IMonitoreoMesaPartesService panelDocumentoMesaPartesServiceImpl;

	@PostConstruct
	public void init() {

		carta = new Carta();
		listarCartaConsulta();

		listaTodosUsuarios = new ArrayList<Usuario>();
		listaTodosUsuarios = usuarioMB.getUsuariosdgi();
		displayBlank();
		inicializarCarousel();
	}


	public void grabarCabeceraCarta() {

		// GRABAR ACTUALIZAR
		if (carta.getIdcarta() == 0) {
			carta.setIdusuariocreador((Integer) FuncionesHelper.getUsuario());
			carta.setFechacreacion(new Date());
			carta.setContrato(contratoSeleccionado);
			
			carta.setNroarchivos(carousel.size());
			recaudacionautovaluocartaService.grabarCabeceraCarta(carta);

			System.out.println("Nro de Archivos :" + carta.getNroarchivos());

			messageFacesPrederminado("Éxito",
					"Se agregó una nueva carta para el inquilino:"
							+ contratoSeleccionado.getInquilino().getNombrescompletos(),
					FacesMessage.SEVERITY_INFO);

			listarCartaConsulta();

			carta = recaudacionautovaluocartaService.getUltimaCabeceraGrabada();

			escribirArchivoAdjuntosBaseDatosyServidor();
			// grabarDetalleArbitrio();
			carta = null;
			contratoSeleccionado=null;
			displayBlank();
		}else {
			carta.setIdusuariocreador((Integer) FuncionesHelper.getUsuario());
			carta.setFechacreacion(new Date());

			
			carta.setNroarchivos(carousel.size());
			
			try {
				recaudacionautovaluocartaService.actualizarCabeceraCarta(carta);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			
			escribirArchivoAdjuntosBaseDatosyServidorACTUALIZAR();
			messageFacesPrederminado("Éxito", "Se actualizó el Carta para el inquilino:"+contratoSeleccionado.getInquilino().getNombrescompletos(), FacesMessage.SEVERITY_INFO);
			contratoSeleccionado=null;
			carta=null;
			displayBlank();
			listarCartaConsulta();
			
		}
	}

	public void obtenerCarta(int idcarta) {
		if (idcarta != 0) {

			for (int i = 0; i < listaCartaConsulta.size(); i++) {
				if (idcarta == listaCartaConsulta.get(i).getIdcarta()) {

					carta = listaCartaConsulta.get(i);
					contratoSeleccionado=listaCartaConsulta.get(i).getContrato();
					
					obtenerArchivosCarta(carta.getIdcarta());
					buscandoUsuarioCreador(listaCartaConsulta.get(i)
							.getIdusuariocreador());
				}
			}
			setIdcartaGlobal(carta.getIdcarta());
		}

	}

	public void obtenerArchivosCarta(int idcarta) {

		if (idcarta != 0) {

			displayList = new ArrayList<Archivodocumento>();
			displayList = recaudacionautovaluocartaService.obtenerArchivosCarta(idcarta);

			displayListTmp = new ArrayList<Archivodocumento>();

			for (Archivodocumento A : displayList) {
				displayListTmp.add(A);
			}

			if (displayList.size() == 0) {
				displayBlank();
			}

		}
	}

	public void zipiarArchivoCarta(int idcarta) {
		fileList = new ArrayList<String>();

		if (idcarta != 0) {

			List<Archivodocumento> lista = new ArrayList<Archivodocumento>();
			lista = recaudacionautovaluocartaService.obtenerArchivosCarta(idcarta);

			for (int i = 0; i < lista.size(); i++) {
				generateFileList(new File(FuncionesHelper.directorioPrincipalLibreria+ lista.get(i).getRuta()));
			}

			zipIt(OUTPUT_ZIP_FILE);

		}
		descargarArchivo(OUTPUT_ZIP_FILE);

	}

	public void listarCartaConsulta() {
		listaCartaConsulta = new ArrayList<Carta>();
		listaCartaConsulta = recaudacionautovaluocartaService.listarArbitrioConsulta();
		


		listaCartaConsultaTOP = new ArrayList<Carta>();
		if (listaCartaConsulta.size() > 4) {
			listaCartaConsultaTOP = listaCartaConsulta.subList(0, 4);
		} else {
			listaCartaConsultaTOP = listaCartaConsulta;
		}

	}

	public void escribirArchivoAdjuntosBaseDatosyServidor() {

		String key;
		InputStream value;

		Iterator iterator = carousel.keySet().iterator();
		while (iterator.hasNext()) {
			key = (String) iterator.next();
			value = (InputStream) carousel.get(key);
			System.out.println(key + ":" + value);
			escribirArchivo(key, value);

			Extensionarchivo extarchivo = new Extensionarchivo();
			extarchivo.setIdExtensionArchivo(obtenerIdExtensionArchivo(obtenerExtension(key)));

			Archivodocumento archivodocu = new Archivodocumento();
			archivodocu.setCarta(carta);
			archivodocu.setRuta(key);
			archivodocu.setTipodocumento("carta");
			archivodocu.setFechaCreacion(new Date());
			archivodocu.setAutorArchivo((Integer) (FuncionesHelper.getUsuario()));
			archivodocu.setUsuarioCreador(usuarioMB.getUsuariologueado().getNombres()+ " "+ usuarioMB.getUsuariologueado().getApellidopat());
			archivodocu.setExtensionarchivo(extarchivo);

			recaudacionautovaluoarbitrioService.grabarArchivosAdjuntos(archivodocu);
		}

		inicializarCarousel();
	}
	
public void escribirArchivoAdjuntosBaseDatosyServidorACTUALIZAR(){
	
		//Actualizar al agrega
	if(carousel.size()>0){
		recaudacionautovaluoarbitrioService.actualizarvalorNroArchivos(carousel.size(),carta.getIdcarta());
		System.out.println("------------->se agrego");
	}
	
	if(displayListTmp.size()>displayList.size()){
		System.out.println("------------->se elimino");
		
	}

		
	
		
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

	public void escribirArchivo(String fileName, InputStream in) {

		try {
			System.out.println("entroxdireccion:::"
					+ FuncionesHelper.directorioPrincipalLibreria + fileName);
			OutputStream out = new FileOutputStream(new File(
					FuncionesHelper.directorioPrincipalLibreria + fileName));
			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = in.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}

			in.close();
			out.flush();
			out.close();

		} catch (IOException e) {
			System.out.println("errorxx:::" + e.getMessage());
		}
	}

	public int obtenerIdExtensionArchivo(String extension) {
		int idExtension = 0;

		for (int i = 0; i < listaExtensionArchivos.size(); i++) {
			if (listaExtensionArchivos.get(i).getExt().equals(extension)) {
				idExtension = listaExtensionArchivos.get(i)
						.getIdExtensionArchivo();
			}

		}

		return idExtension;
	}

	public void cargarArchivo(FileUploadEvent event) throws IOException {
		Boolean flag = false;

		for (int i = 0; i < displayList.size(); i++) {
			if (displayList.get(i).getRuta().equals("")
					&& displayList.get(i).getExtensionarchivo().getRutaIcono()
							.equals("blank.jpg")) {
				displayList.remove(i);
			}
		}

		for (int i = 0; i < displayList.size(); i++) {
			if (displayList.get(i).getRuta()
					.equals(event.getFile().getFileName())) {
				messageFacesPrederminado("Advertencia", "El arhivo "
						+ event.getFile().getFileName()
						+ " ya se encuentra en la lista",
						FacesMessage.SEVERITY_WARN);
				flag = true;
			}

		}

		if (!flag) {

			carousel.put(event.getFile().getFileName(), event.getFile()
					.getInputstream());

			Extensionarchivo ea = new Extensionarchivo();
			ea.setRutaIcono(obtenerRutaImagen(obtenerExtension(event.getFile()
					.getFileName())));

			Archivodocumento extFile = new Archivodocumento();
			extFile.setRuta(event.getFile().getFileName());
			extFile.setExtensionarchivo(ea);

			displayList.add(extFile);

		}

	}

	public String obtenerExtension(String nombreArchivo) {
		int tamanioCadena, posicionCaracter;
		String extension;

		tamanioCadena = nombreArchivo.length();
		posicionCaracter = nombreArchivo.lastIndexOf(".");
		extension = nombreArchivo.substring(posicionCaracter, tamanioCadena);

		return extension.toLowerCase();
	}

	public String obtenerRutaImagen(String extension) {
		String rutaimagen = "";

		for (int i = 0; i < listaExtensionArchivos.size(); i++) {
			if (listaExtensionArchivos.get(i).getExt().equals(extension)) {
				rutaimagen = listaExtensionArchivos.get(i).getRutaIcono();
			}

		}

		return rutaimagen;
	}

	public void eliminarArchivodeLista() {
		displayList.remove(getArchivoDelete());
		carousel.remove(getArchivoDelete().getRuta());
	}

	

	public void buscandoUsuarioCreador(int idUsuarioCreador) {
		usuarioCreador = new Usuario();
		for (int i = 0; i < listaTodosUsuarios.size(); i++) {
			if (listaTodosUsuarios.get(i).getIdusuario() == idUsuarioCreador) {

				usuarioCreador.setApellidopat(listaTodosUsuarios.get(i)
						.getApellidopat());
				usuarioCreador.setNombres(listaTodosUsuarios.get(i)
						.getNombres());
				usuarioCreador.setRutaimgusr(listaTodosUsuarios.get(i)
						.getRutaimgusr());
			}
		}

	}

	public void displayBlank() {
		displayList = new ArrayList<Archivodocumento>();

		Extensionarchivo ea = new Extensionarchivo();
		ea.setRutaIcono("blank.jpg");

		Archivodocumento extFile = new Archivodocumento();
		extFile.setRuta("");
		extFile.setExtensionarchivo(ea);

		displayList.add(extFile);
	}

	public void inicializarCarousel() {

		displayList = new ArrayList<Archivodocumento>();
		listaExtensionArchivos = new ArrayList<Extensionarchivo>();
		listaExtensionArchivos = panelDocumentoMesaPartesServiceImpl
				.getListaExtensionArhivos();
		carousel = new HashMap<String, InputStream>();
		displayListTmp = new ArrayList<Archivodocumento>();
	}

	public void messageFacesPrederminado(String titulo, String mensaje,
			Severity severityFatal) {
		FacesContext contextFaces = FacesContext.getCurrentInstance();
		contextFaces.addMessage(null, new FacesMessage(severityFatal, titulo,
				mensaje));
	}

	public IRecaudacionAutovaluoCartaService getRecaudacionautovaluocartaService() {
		return recaudacionautovaluocartaService;
	}

	public void setRecaudacionautovaluocartaService(
			IRecaudacionAutovaluoCartaService recaudacionautovaluocartaService) {
		this.recaudacionautovaluocartaService = recaudacionautovaluocartaService;
	}

	public Carta getCarta() {
		return carta;
	}

	public void setCarta(Carta carta) {
		this.carta = carta;
	}


	public List<Usuario> getListaTodosUsuarios() {
		return listaTodosUsuarios;
	}

	public void setListaTodosUsuarios(List<Usuario> listaTodosUsuarios) {
		this.listaTodosUsuarios = listaTodosUsuarios;
	}

	public IRecaudacionAutovaluoArbitrioService getRecaudacionautovaluoarbitrioService() {
		return recaudacionautovaluoarbitrioService;
	}

	public void setRecaudacionautovaluoarbitrioService(
			IRecaudacionAutovaluoArbitrioService recaudacionautovaluoarbitrioService) {
		this.recaudacionautovaluoarbitrioService = recaudacionautovaluoarbitrioService;
	}

	public Usuario getUsuarioCreador() {
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

	public List<Carta> getListaCartaConsulta() {
		return listaCartaConsulta;
	}

	public void setListaCartaConsulta(List<Carta> listaCartaConsulta) {
		this.listaCartaConsulta = listaCartaConsulta;
	}

	public List<Carta> getListafiltroCartaConsulta() {
		return listafiltroCartaConsulta;
	}

	public void setListafiltroCartaConsulta(List<Carta> listafiltroCartaConsulta) {
		this.listafiltroCartaConsulta = listafiltroCartaConsulta;
	}

	public int getIdcartaGlobal() {
		return idcartaGlobal;
	}

	public void setIdcartaGlobal(int idcartaGlobal) {
		this.idcartaGlobal = idcartaGlobal;
	}

	public Map<String, InputStream> getCarousel() {
		return carousel;
	}

	public void setCarousel(Map<String, InputStream> carousel) {
		this.carousel = carousel;
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

	public List<Extensionarchivo> getListaExtensionArchivos() {
		return listaExtensionArchivos;
	}

	public void setListaExtensionArchivos(
			List<Extensionarchivo> listaExtensionArchivos) {
		this.listaExtensionArchivos = listaExtensionArchivos;
	}

	public Archivodocumento getArchivoDelete() {
		return archivoDelete;
	}

	public void setArchivoDelete(Archivodocumento archivoDelete) {
		this.archivoDelete = archivoDelete;
	}

	public IMonitoreoMesaPartesService getPanelDocumentoMesaPartesServiceImpl() {
		return panelDocumentoMesaPartesServiceImpl;
	}

	public void setPanelDocumentoMesaPartesServiceImpl(
			IMonitoreoMesaPartesService panelDocumentoMesaPartesServiceImpl) {
		this.panelDocumentoMesaPartesServiceImpl = panelDocumentoMesaPartesServiceImpl;
	}

	public List<Carta> getListaCartaConsultaTOP() {
		return listaCartaConsultaTOP;
	}

	public void setListaCartaConsultaTOP(List<Carta> listaCartaConsultaTOP) {
		this.listaCartaConsultaTOP = listaCartaConsultaTOP;
	}
	
	

	public Contrato getContratoSeleccionado() {
		return contratoSeleccionado;
	}


	public void setContratoSeleccionado(Contrato contratoSeleccionado) {
		this.contratoSeleccionado = contratoSeleccionado;
	}


	/**
	 * Zip it
	 * 
	 * @param zipFile
	 *            output ZIP file location
	 */
	public void zipIt(String zipFile) {

		byte[] buffer = new byte[1024];

		try {

			FileOutputStream fos = new FileOutputStream(zipFile);
			ZipOutputStream zos = new ZipOutputStream(fos);

			System.out.println("Output to Zip : " + zipFile);

			for (String file : this.fileList) {

				System.out.println("File Added : " + file);
				ZipEntry ze = new ZipEntry(file);
				zos.putNextEntry(ze);

				FileInputStream in = new FileInputStream(SOURCE_FOLDER
						+ File.separator + file);

				int len;
				while ((len = in.read(buffer)) > 0) {
					zos.write(buffer, 0, len);
				}

				in.close();
			}

			zos.closeEntry();
			// remember close it
			zos.close();

			System.out.println("Done");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Traverse a directory and get all files, and add the file into fileList
	 * 
	 * @param node
	 *            file or directory
	 */
	public void generateFileList(File node) {

		// add file only
		if (node.isFile()) {
			fileList.add(generateZipEntry(node.getAbsoluteFile().toString()));
		}

		if (node.isDirectory()) {
			String[] subNote = node.list();
			for (String filename : subNote) {
				generateFileList(new File(node, filename));
			}
		}

	}

	/**
	 * Format the file path for zip
	 * 
	 * @param file
	 *            file path
	 * @return Formatted file path
	 */
	private String generateZipEntry(String file) {
		return file.substring(SOURCE_FOLDER.length(), file.length());
	}

	public void descargarArchivo(String archivoCapturado) {

		System.out.println(":::::::::...rutax::" + archivoCapturado);
		String curDir = System.getProperty("user.dir");
		String direccion = curDir
				+ "\\webapps\\sistemaSBLM\\resources\\documents\\"
				+ archivoCapturado;

		// String direccion= "C:\\archivosxxx\\"+archivoCapturado.getRuta();

		direccion = archivoCapturado;

		String extension = "";
		int i = direccion.lastIndexOf('.');
		if (i > 0) {
			extension = direccion.substring(i + 1);
		}

		System.out.println(":::::::::...extension::" + extension);
		System.out.println(":::::::::...direccion::" + direccion);
		InputStream stream;
		try {
			stream = new FileInputStream(direccion);
			filedownload = new DefaultStreamedContent(stream, "application/"
					+ extension + "", archivoCapturado);
		} catch (FileNotFoundException e) {

			System.out.println("ERROR:" + e.getMessage());
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Advertencia", new String(
							"No existe el archivo en el servidor"));

			FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}

	public StreamedContent getFiledownload() {
		return filedownload;
	}

	public void setFiledownload(StreamedContent filedownload) {
		this.filedownload = filedownload;
	}

	public String getJravcalle() {
		return jravcalle;
	}

	public void setJravcalle(String jravcalle) {
		this.jravcalle = jravcalle;
	}
	

}
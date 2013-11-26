package com.sblm.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.sblm.model.Archivodocumento;
import com.sblm.model.Extensionarchivo;
import com.sblm.model.Inmueble;
import com.sblm.service.IArchivoAdjuntoService;
import com.sblm.util.FuncionesHelper;

@ManagedBean(name = "archivoadjuntoMB")
@ViewScoped
public class ArchivoAdjuntoManagedBean implements Serializable {
	private static final long serialVersionUID = 5524190003746598593L;
	@ManagedProperty(value = "#{archivoadjuntoService}")
	private transient IArchivoAdjuntoService archivoadjuntoService;

	Map<String, InputStream> carousel;
	private List<Archivodocumento> displayList;
	private List<Extensionarchivo> listaExtensionArchivos;
	private Archivodocumento archivoDelete;
	private boolean entrovacio = false;
	private boolean valorinicio = false;
	private boolean existedocumento = false;
	private boolean vacio = true;
	public String rutarepetido;
	//String destination = "C:\\compartido\\";
	
	String curDir = System.getProperty("user.dir");
	String destination = curDir+"\\webapps\\sistemaSBLM\\resources\\documents\\";
//	String destination = curDir+"\\sistemaSBLM\\resources\\documents\\";
	// variable para descargar el archivo
	private StreamedContent filedownload;

	@PostConstruct
	public void initObjects() {

		displayList = new ArrayList<Archivodocumento>();

		listaExtensionArchivos = new ArrayList<Extensionarchivo>();
		listaExtensionArchivos = getArchivoadjuntoService()
				.getListaExtensionArhivos();

		carousel = new HashMap<String, InputStream>();

	}

	public void escribirEnServidor() {
		String key;
		InputStream value;

		Iterator iterator = carousel.keySet().iterator();
		while (iterator.hasNext()) {
			key = (String) iterator.next();
			value = (InputStream) carousel.get(key);
			System.out.println("$$$$$::" + key + ":" + value);
			escribirArchivo(key, value);

		}
	}

	public void escribirArchivo(String fileName, InputStream in) {

		File archivo = new File(destination + fileName);
		if (archivo.exists()) {
			System.out
					.println("El fichero ya existe, Desea Sobre Escribir.... ");
			existedocumento = true;
			rutarepetido = fileName;
			Date ahora = new Date();
			SimpleDateFormat formateador = new SimpleDateFormat(
					"dd-MM-yyyy-hhmmss_");
			try {
				OutputStream out = new FileOutputStream(new File(destination
						+ formateador.format(ahora) + fileName));
				int read = 0;
				byte[] bytes = new byte[1024];
				while ((read = in.read(bytes)) != -1) {
					out.write(bytes, 0, read);
				}
				in.close();
				out.flush();
				out.close();
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}

		} else {
			System.out.println("El fichero ha sido Creado!!!! ");
			try {
				OutputStream out = new FileOutputStream(new File(destination
						+ fileName));
				int read = 0;
				byte[] bytes = new byte[1024];
				while ((read = in.read(bytes)) != -1) {
					out.write(bytes, 0, read);
				}
				in.close();
				out.flush();
				out.close();
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	public void cargarArchivo(FileUploadEvent event) {
		try {
			System.out.println("###nomcrearchivo::"
					+ event.getFile().getFileName());
			carousel.put(event.getFile().getFileName(), event.getFile()
					.getInputstream());
			
			System.out.println("tamanioo::"+carousel.size());
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (entrovacio == true || valorinicio == true) {// verificamos si es
														// lista vacia
			displayList.clear();
		}

		Archivodocumento archidoc = new Archivodocumento();
		Extensionarchivo exar = new Extensionarchivo();
		exar.setRutaIcono(obtenerRutaImagen(obtenerExtension(event.getFile()
				.getFileName())));
		archidoc.setExtensionarchivo(exar);
		archidoc.setRuta(event.getFile().getFileName());

		displayList.add(archidoc);
		entrovacio = false;
		valorinicio = false;
	}

	public void limpiarCampos() {
		System.out.println("paso limpieza!!");
		displayList = new ArrayList<Archivodocumento>();

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

	public String obtenerExtension(String nombreArchivo) {
		int tamanioCadena, posicionCaracter;
		String extension;

		tamanioCadena = nombreArchivo.length();
		posicionCaracter = nombreArchivo.lastIndexOf(".");
		extension = nombreArchivo.substring(posicionCaracter, tamanioCadena);

		return extension.toLowerCase();
	}

	public void nuevaListaVaciaInicial() {
		valorinicio = true;
		try {
			System.out.println("vaciooxxxx:::");
			displayList = new ArrayList<Archivodocumento>();
			Extensionarchivo exarchi = new Extensionarchivo();
			exarchi.setRutaIcono("vacio.png");

			Archivodocumento archivo = new Archivodocumento();
			archivo.setRuta("vacio.png");
			archivo.setExtensionarchivo(exarchi);
			displayList.add(archivo);

		} catch (Exception e) {
			System.out.println("error nuevaListaVacia::" + e.getMessage());
		}

	}

	public void nuevaListaVacia() {

		try {
			System.out.println("vaciooxxxx:::");
			displayList = new ArrayList<Archivodocumento>();
			Extensionarchivo exarchi = new Extensionarchivo();
			exarchi.setRutaIcono("vacio.png");

			Archivodocumento archivo = new Archivodocumento();
			archivo.setRuta("vacio.png");
			archivo.setExtensionarchivo(exarchi);
			displayList.add(archivo);
			entrovacio = true;
			valorinicio = false;
		} catch (Exception e) {
			System.out.println("error nuevaListaVacia::" + e.getMessage());
		}

	}

	public void eliminarArchivodeLista() {

		displayList.remove(getArchivoDelete());
		System.out.println("contenidoooo::" + displayList.size());

		if (displayList.isEmpty()) {
			nuevaListaVacia();
			valorinicio = true;
		}
	}

	public void descargarArchivo(Archivodocumento archivoCapturado) {
		String direccion = destination + archivoCapturado.getRuta();
		String extension = "";
		int i = direccion.lastIndexOf('.');
		if (i > 0) {
			extension = direccion.substring(i + 1);
		}
		InputStream stream;
		try {
			stream = new FileInputStream(direccion);
			filedownload = new DefaultStreamedContent(stream, "application/"
					+ extension + "", archivoCapturado.getRuta());
		} catch (FileNotFoundException e) {
			System.out.println("errorxxxx::" + e.getMessage());
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Advertencia", new String(
							"No existe el archivo en el servidor"));
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}

	public List<Archivodocumento> getDisplayList() {

		return displayList;
	}

	public void setDisplayList(List<Archivodocumento> displayList) {

		this.displayList = displayList;
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

	public IArchivoAdjuntoService getArchivoadjuntoService() {
		return archivoadjuntoService;
	}

	public void setArchivoadjuntoService(
			IArchivoAdjuntoService archivoadjuntoService) {
		this.archivoadjuntoService = archivoadjuntoService;
	}

	public boolean isEntrovacio() {
		return entrovacio;
	}

	public void setEntrovacio(boolean entrovacio) {
		this.entrovacio = entrovacio;
	}

	public boolean isValorinicio() {
		return valorinicio;
	}

	public void setValorinicio(boolean valorinicio) {
		this.valorinicio = valorinicio;
	}

	public StreamedContent getFiledownload() {
		return filedownload;
	}

	public void setFiledownload(StreamedContent filedownload) {
		this.filedownload = filedownload;
	}

	public boolean isExistedocumento() {
		return existedocumento;
	}

	public void setExistedocumento(boolean existedocumento) {
		this.existedocumento = existedocumento;
	}

	public String getRutarepetido() {
		return rutarepetido;
	}

	public void setRutarepetido(String rutarepetido) {
		this.rutarepetido = rutarepetido;
	}

	public boolean isVacio() {
		return vacio;
	}

	public void setVacio(boolean vacio) {
		this.vacio = vacio;
	}

}

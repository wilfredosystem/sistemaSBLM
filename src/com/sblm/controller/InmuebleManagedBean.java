package com.sblm.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;

import com.sblm.model.Archivodocumento;
import com.sblm.model.Extensionarchivo;
import com.sblm.model.Inmueble;
import com.sblm.model.Materialpredominante;
import com.sblm.model.Tipointerior;
import com.sblm.model.Tipotitularidad;
import com.sblm.model.Tipovia;
import com.sblm.model.Titularidad;
import com.sblm.service.IInmuebleService;
import com.sblm.service.IMonitoreoMesaPartesService;

@ManagedBean(name = "inmuebleMB")
@ViewScoped
public class InmuebleManagedBean implements Serializable {
	private static final long serialVersionUID = 5524190003746598593L;
	@ManagedProperty(value = "#{inmuebleService}")
	private transient IInmuebleService inmuebleService;
	@ManagedProperty(value = "#{usuarioMB}")
	UsuarioManagedBean userMB;

	private List<Inmueble> inmuebles;

	private Inmueble inmueble;
	private Inmueble inmueblecapturado;
	boolean actualizado = false;
	private Inmueble resultadoinmueble;
	private int numinmuebles;
	private List<Tipovia> listaTipoVia;
	
	private List<Titularidad> listaTitularidad;
	private List<Tipotitularidad> listaTipoTitularidad;
	private List<Materialpredominante> listaMaterialPredominante;
	private List<Tipointerior> listaTipoInterior;

	private List<String> listaDptos;
	private List<String> listaProvincias;
	private List<String> listaDistritos;
	private List<String> listaUbigeos;

	private String valorDpto;
	private String valorProvincia;
	private String valorDistrito;
	
	private int tipourbanizacion;
	private boolean direccionnumero=false;
	private boolean repetido = false;
	/****** cargado de archivos ****/
	@ManagedProperty(value = "#{archivoadjuntoMB}")
	ArchivoAdjuntoManagedBean aaMB;

	/*******************************/
	@PostConstruct
	public void initObjects() {
		/****** cargado de archivos ****/
		// if (actualizado == true) {
		// System.out.println("no limpia lista:::");
		// }else{
		aaMB.nuevaListaVaciaInicial();
		// }

		/*******************************/
		try {
			listaUbigeos = getInmuebleService().listarUbigeos();

			listaDptos = getInmuebleService().listarDepartamentos();
			ActualizarMensajes();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void activarNumero() {
		System.out.println("direccionnumero::"+inmueble.getNumeroprincipal());
		
		if(inmueble.getNumeroprincipal()==""){
			System.out.println("vacio");
			direccionnumero=true;//x direccion
		}else{
			System.out.println("contenido");
			direccionnumero=false;//x manzana
		}
		
		
	}
	public void activarManzana() {
		if(inmueble.getManzana()==""){
			System.out.println("vaciomanzana");
			direccionnumero=false;//x direccion
		}else{
			System.out.println("vaciomanzana");
			direccionnumero=true;//x manzana
		}
	}
	public void cargarUltimoregistro() {
		actualizado=true;
		inmueble=getInmuebleService().obtenerUltimoInmueble();
	}
	public void validarRepetido(String numreg) {
		if (getInmuebleService().validarRepetido(numreg) != null) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Notificacion", "Ya existe El Numero de SBN!!!");
			FacesContext.getCurrentInstance().addMessage(null, msg);

			repetido = true;
		}

	}

	/****** cargado de archivos ****/
	public void cargadoArchivo(FileUploadEvent event) throws IOException {
		aaMB.cargarArchivo(event);
	}

	public void registrarArchivo() {
		if (aaMB.isValorinicio() == true) {
			aaMB.getDisplayList().clear();
		}
		System.out.println("registrarArchivo,, tamanooo DisplayList:::"
				+ aaMB.getDisplayList().size());
		if (aaMB.getDisplayList().size() == 0) {
			System.out.println("##########no adjunto  registros!!!");
			aaMB.nuevaListaVacia();
		} else {

			for (Archivodocumento adocu : aaMB.getDisplayList()) {
				String extension = "";
				int i = adocu.getRuta().lastIndexOf('.');
				if (i > 0) {
					extension = adocu.getRuta().substring(i + 1);
				}
				String newextension = "." + extension;
				System.out.println(":::::::::...extensionx::" + newextension);

				Extensionarchivo extenAr = new Extensionarchivo();
				extenAr = aaMB.getArchivoadjuntoService()
						.obtenerExtensionArhivoPorExtension(newextension);
				if (actualizado == true) {
					adocu.setInmueble(inmueble);
				} else {
					adocu.setInmueble(aaMB.getArchivoadjuntoService()
							.obtenerUltimoInmueble());

				}
				adocu.setExtensionarchivo(extenAr);
				if (aaMB.isExistedocumento() == true
						&& aaMB.getRutarepetido().equals(adocu.getRuta())) {
					Date ahora = new Date();
					SimpleDateFormat formateador = new SimpleDateFormat(
							"dd-MM-yyyy-hhmmss_");

					adocu.setRuta(formateador.format(ahora) + adocu.getRuta());
					// actualizamos el estado
					aaMB.setExistedocumento(false);
				}
				getAaMB().getArchivoadjuntoService()
						.registrarArchivosDocumento(adocu);
			}
			aaMB.escribirEnServidor();
		}
	}

	/*******************************/

	public void registrarInmueble() {
System.out.println("clave::"+inmueble.getClave());
		Date ahora = new Date();
		if (repetido == false) {
			if (actualizado == true) {
				System.out.println("::::::entro actualizado");

				/****** cargado de archivos ****/
				// modificamos de acuerdo al mantenimiento
				aaMB.getArchivoadjuntoService().eliminarArhivoDocumento(
						inmueble);
				registrarArchivo();
				/******  ****/
				String usermodificador = userMB.getUsuariologueado()
						.getNombres()
						+ " "
						+ userMB.getUsuariologueado().getApellidopat();
				inmueble.setFechmod(ahora);
				inmueble.setUsrmod(usermodificador);
				getInmuebleService().registrarInmueble(inmueble);

				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Exito", "Se Actualizo correctamente el Inmueble.");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			} else {
				System.out.println("::::::entro registro");

				Titularidad titu = new Titularidad();
				titu.setIdtitularidad(1);
				Tipotitularidad tiptitu = new Tipotitularidad();
				tiptitu.setIdtipotitularidad(1);
				Materialpredominante matpre = new Materialpredominante();
				matpre.setIdmaterialpredominante(1);
				inmueble.getUbigeo().setDepa(valorDpto);
				inmueble.getUbigeo().setProv(valorProvincia);
				inmueble.getUbigeo().setDist(valorDistrito);
				inmueble.getTipovia().setDescripcion("tipoviaxxx");

				inmueble.setTitularidad(titu);
				inmueble.setTipotitularidad(tiptitu);
				inmueble.setMaterialpredominante(matpre);
				String usercreador = userMB.getUsuariologueado().getNombres()
						+ " " + userMB.getUsuariologueado().getApellidopat();
				inmueble.setUsrcre(usercreador);
				inmueble.setFechcre(ahora);

				getInmuebleService().registrarInmueble(inmueble);
				/****** cargado de archivos ****/
				registrarArchivo();
				/****** ****/
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Exito", "Se registro correctamente el Inmueble.");

				FacesContext.getCurrentInstance().addMessage(null, msg);
			}
		} else {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Error", "El Numero SBN ya se encuentra registrado en la Base de datos.");

			FacesContext.getCurrentInstance().addMessage(null, msg);
			repetido = false;
		}
		// para refrescar el valor en el panel
		ActualizarMensajes();

	}

	public void onRowSelect(SelectEvent event) {
		actualizado = true;
		listaDptos = getInmuebleService().listarDepartamentos();
		listaProvincias = getInmuebleService().listarProvincias(inmueble.getUbigeo().getDepa());
		listaDistritos = getInmuebleService().listarDistritos(inmueble.getUbigeo().getProv());
		System.out.println("id inmueble cap:::" + inmueble.getIdinmueble());
		/****** cargado de archivos ****/
		if (aaMB.getArchivoadjuntoService()
				.obtenerArchivodocumentosPorIdInmueble(inmueble.getIdinmueble())
				.isEmpty()) {
			System.out.println("lsita vaciaaa:::");
			aaMB.nuevaListaVacia();
		} else {
			System.out.println("lista con data:::");
			// seteamos la lista de archivos obtenidos, perteneciente a un
			// inmueble

			aaMB.setDisplayList(aaMB.getArchivoadjuntoService()
					.obtenerArchivodocumentosPorIdInmueble(
							inmueble.getIdinmueble()));
			// para k no limpie la lista
			aaMB.setValorinicio(false);
		}
		/**********/
	}

	public void eliminarInmueble() {
		System.out.println("controlador eliminar inmueble::");
		getInmuebleService().eliminarInmueble(inmueblecapturado);

		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Exito", "Se eliminó el Inmueble "
						+ inmueblecapturado.getNumregistrosbn()
						+ " correctamente.");

		FacesContext.getCurrentInstance().addMessage(null, msg);
		limpiarCampos();
		ActualizarMensajes();
	}

	public void ActualizarMensajes() {
		// resultadoinmueble = getInmuebleService().obtenerUltimoInmueble();
		// numinmuebles = getInmuebleService().obtenerNumeroRegistros();
	}

	public void cargarComboProvincias() {
		System.out.println("dpto:::" + inmueble.getUbigeo().getDepa());
		//System.out.println("dpto:::" + valorDpto);
		listaProvincias = getInmuebleService().listarProvincias(inmueble.getUbigeo().getDepa());
	}

	public void cargarComboDistritos() {
		System.out.println("prov:::" + inmueble.getUbigeo().getProv());
		listaDistritos = getInmuebleService().listarDistritos(inmueble.getUbigeo().getProv());
	}

	public void cargarUbigeo() {
		System.out.println(":::-----------------------:::");
		System.out.println("Provincia:::" +  inmueble.getUbigeo().getProv());
		System.out.println("valorDistrito:::" +  inmueble.getUbigeo().getDist());
		listaUbigeos = getInmuebleService().listarUbigeoPorDistrito(
				inmueble.getUbigeo().getProv(), inmueble.getUbigeo().getDist());
	}

	public void cargarDdtoProvDist() {
		System.out.println("Ubigeo:::" + inmueble.getUbigeo().getUbigeo());
		listaDptos = getInmuebleService().listarDptoPorUbigeo(
				inmueble.getUbigeo().getUbigeo());
		listaProvincias = getInmuebleService().listarProvinciaPorUbigeo(
				inmueble.getUbigeo().getUbigeo());
		listaDistritos = getInmuebleService().listarDistritoPorUbigeo(
				inmueble.getUbigeo().getUbigeo());
	}

	public void limpiarCampos() {
		System.out.println("paso limpieza!!");
		inmueble = new Inmueble();
		aaMB.nuevaListaVaciaInicial();
		actualizado = false;
	}

	public IInmuebleService getInmuebleService() {
		return inmuebleService;
	}

	public void setInmuebleService(IInmuebleService inmuebleService) {
		this.inmuebleService = inmuebleService;
	}

	public UsuarioManagedBean getUserMB() {
		return userMB;
	}

	public void setUserMB(UsuarioManagedBean userMB) {
		this.userMB = userMB;
	}

	public List<Inmueble> getInmuebles() {
		inmuebles = getInmuebleService().listarInmuebles();
		return inmuebles;
	}

	public void setInmuebles(List<Inmueble> inmuebles) {
		this.inmuebles = inmuebles;
	}

	public Inmueble getInmueble() {
		if (inmueble == null) {
			inmueble = new Inmueble();
		}
		return inmueble;
	}

	public void setInmueble(Inmueble inmueble) {
		this.inmueble = inmueble;
	}

	public Inmueble getInmueblecapturado() {
		return inmueblecapturado;
	}

	public void setInmueblecapturado(Inmueble inmueblecapturado) {
		this.inmueblecapturado = inmueblecapturado;
	}

	public Inmueble getResultadoinmueble() {
		return resultadoinmueble;
	}

	public void setResultadoinmueble(Inmueble resultadoinmueble) {
		this.resultadoinmueble = resultadoinmueble;
	}

	public int getNuminmuebles() {
		return numinmuebles;
	}

	public void setNuminmuebles(int numinmuebles) {
		this.numinmuebles = numinmuebles;
	}

	public List<String> getListaDptos() {

		return listaDptos;
	}

	public void setListaDptos(List<String> listaDptos) {
		this.listaDptos = listaDptos;
	}

	public List<String> getListaProvincias() {
		return listaProvincias;
	}

	public void setListaProvincias(List<String> listaProvincias) {
		this.listaProvincias = listaProvincias;
	}

	public List<String> getListaDistritos() {
		return listaDistritos;
	}

	public void setListaDistritos(List<String> listaDistritos) {
		this.listaDistritos = listaDistritos;
	}

	public String getValorDpto() {
		return valorDpto;
	}

	public void setValorDpto(String valorDpto) {
		this.valorDpto = valorDpto;
	}

	public String getValorProvincia() {
		return valorProvincia;
	}

	public void setValorProvincia(String valorProvincia) {
		this.valorProvincia = valorProvincia;
	}

	public String getValorDistrito() {
		return valorDistrito;
	}

	public void setValorDistrito(String valorDistrito) {
		this.valorDistrito = valorDistrito;
	}


	public List<Tipovia> getListaTipoVia() {
		listaTipoVia = getInmuebleService().listarTipoVia();
		return listaTipoVia;
	}

	public void setListaTipoVia(List<Tipovia> listaTipoVia) {
		this.listaTipoVia = listaTipoVia;
	}

	public List<String> getListaUbigeos() {

		return listaUbigeos;
	}

	public void setListaUbigeos(List<String> listaUbigeos) {
		this.listaUbigeos = listaUbigeos;
	}

	// public String getValorUbigeo() {
	// return valorUbigeo;
	// }
	//
	// public void setValorUbigeo(String valorUbigeo) {
	// this.valorUbigeo = valorUbigeo;
	// }


	public List<Titularidad> getListaTitularidad() {
		listaTitularidad = getInmuebleService().listarTitularidad();
		return listaTitularidad;
	}

	public void setListaTitularidad(List<Titularidad> listaTitularidad) {
		this.listaTitularidad = listaTitularidad;
	}

	public List<Tipotitularidad> getListaTipoTitularidad() {
		listaTipoTitularidad = getInmuebleService().listarTipoTitularidad();
		return listaTipoTitularidad;
	}

	public void setListaTipoTitularidad(
			List<Tipotitularidad> listaTipoTitularidad) {
		this.listaTipoTitularidad = listaTipoTitularidad;
	}

	public List<Materialpredominante> getListaMaterialPredominante() {
		listaMaterialPredominante = getInmuebleService()
				.listarMaterialPredominante();
		return listaMaterialPredominante;
	}

	public void setListaMaterialPredominante(
			List<Materialpredominante> listaMaterialPredominante) {
		this.listaMaterialPredominante = listaMaterialPredominante;
	}

	public List<Tipointerior> getListaTipoInterior() {
		listaTipoInterior = getInmuebleService().listarTipoInterior();
		return listaTipoInterior;
	}

	public void setListaTipoInterior(List<Tipointerior> listaTipoInterior) {
		this.listaTipoInterior = listaTipoInterior;
	}

	public ArchivoAdjuntoManagedBean getAaMB() {
		return aaMB;
	}

	public void setAaMB(ArchivoAdjuntoManagedBean aaMB) {
		this.aaMB = aaMB;
	}


	public boolean isDireccionnumero() {
		return direccionnumero;
	}


	public void setDireccionnumero(boolean direccionnumero) {
		this.direccionnumero = direccionnumero;
	}


	public int getTipourbanizacion() {
		return tipourbanizacion;
	}


	public void setTipourbanizacion(int tipourbanizacion) {
		this.tipourbanizacion = tipourbanizacion;
	}

}

package com.sblm.controller;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.sblm.model.Mep;

import com.sblm.model.Tipointerior;
import com.sblm.model.Tipotitularidad;
import com.sblm.model.Tipovia;
import com.sblm.model.Titularidad;
import com.sblm.model.Upa;
import com.sblm.model.Uso;
import com.sblm.model.Valuacion;
import com.sblm.service.IUpaService;

@ManagedBean(name = "upaMB")
@ViewScoped
public class UpaManagedBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{upaService}")
	private transient IUpaService upaService;
	@ManagedProperty(value = "#{usuarioMB}")
	UsuarioManagedBean userMB;

	private List<Upa> upas;

	private Upa upa;
	private Upa upacapturado;
	boolean actualizado = false;
	private Upa resultadoupa;
	private int numupas;
	private Inmueble inmueble;
	private List<Tipovia> listaTipoVia;
	private List<Titularidad> listaTitularidad;
	private List<Tipotitularidad> listaTipoTitularidad;
	private List<Materialpredominante> listaMaterialPredominante;
	private List<Tipointerior> listaTipoInterior;
	private List<Valuacion> listaValuacion;
	
	private List<Uso> listaUso;
	private List<Mep> listaMep;

	private List<String> listaDptos;
	private List<String> listaProvincias;
	private List<String> listaDistritos;
	private List<String> listaUbigeos;

	private String valorDpto;
	private String valorProvincia;
	private String valorDistrito;
	private boolean repetido = false;
	/****** cargado de archivos ****/
	@ManagedProperty(value = "#{archivoadjuntoMB}")
	ArchivoAdjuntoManagedBean aaMB;
	/*******************************/
	@PostConstruct
	public void initObjects() {
		/****** cargado de archivos ****/
		aaMB.nuevaListaVaciaInicial();
		/*******************************/
		try {
			listaUbigeos = getUpaService().listarUbigeos();

			listaDptos = getUpaService().listarDepartamentos();
			ActualizarMensajes();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void cargarUltimoregistro() {
		System.out.println("obetenr ultima upaaa");
		upa=getUpaService().obtenerUltimoUpa();
		actualizado=true;
	}
	public void validarRepetido(String numreg) {
		if (getUpaService().validarRepetido(numreg) != null) {
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
					adocu.setUpa(upa);//modificamos de acuerdo al mantenimiento
				} else {
					//modificamos de acuerdo al mantenimiento
					adocu.setUpa(aaMB.getArchivoadjuntoService()
							.obtenerUltimoUpa());

				}
				adocu.setExtensionarchivo(extenAr);
				if(aaMB.isExistedocumento()==true && aaMB.getRutarepetido().equals(adocu.getRuta())){
					Date ahora = new Date();
					SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy-hhmmss_");
					
				adocu.setRuta(formateador.format(ahora)+adocu.getRuta());
				//actualizamos el estado
				aaMB.setExistedocumento(false);
				}
				getAaMB().getArchivoadjuntoService().registrarArchivosDocumento(adocu);
			}
			aaMB.escribirEnServidor();
		}
	}

	/*******************************/
	public void filaInmuebleSeleccionado() {
		System.out.println("SBN::::" + inmueble.getNumregistrosbn());
		System.out.println("SBN::::" + inmueble.getDireccion());
		upa.getInmueble().setNumregistrosbn(inmueble.getNumregistrosbn());
	}

	public List<String> autocompletar(String query) {

		List<String> listado = new ArrayList<String>();
		List<String> listaInmuebles = new ArrayList<String>();
		listaInmuebles = getUpaService().listaInmuebles();

		for (String inmu : listaInmuebles) {
			if (!inmu.toString().equals(null)) {

				if (inmu.toLowerCase().contains(query.toLowerCase())) {
					try {
						listado.add(inmu);

					} catch (Exception e) {
						System.out.println("error autocompletado::"
								+ e.getMessage());
					}

				}
			}

		}

		return listado;

	}

	public void ActualizarMensajes() {
//		resultadoupa = getUpaService().obtenerUltimoUpa();
//		numupas = getUpaService().obtenerNumeroRegistros();
	}

	public void registrarUpa() {

		Date ahora = new Date();

		if (actualizado == true) {
			System.out.println("::::::entro actualizado Upa");

			/****** cargado de archivos ****/
			//modificar de acuerdo al mantenimiento
			aaMB.getArchivoadjuntoService().eliminarArhivoDocumentoUpa(upa);
			registrarArchivo();
			/******  ****/

			String usermodificador = userMB.getUsuariologueado().getNombres()
					+ " " + userMB.getUsuariologueado().getApellidopat();
			upa.setFecmod(ahora);
			upa.setUsrmod(usermodificador);
			getUpaService().registrarUpa(upa);

			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Exito", "Se Actualizo correctamente el Upa.");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} else {

			Valuacion val = new Valuacion();
			val.setIdvaluacion(1);
			upa.setValuacion(val);

			Mep mep = new Mep();
			mep.setIdmep(1);
			upa.setMep(mep);

			Uso uso = new Uso();
			uso.setIduso(1);
			upa.setUso(uso);

			Inmueble inmueb = new Inmueble();

			inmueb = getUpaService().obtenerInmueblePorParametro(
					upa.getInmueble().getNumregistrosbn());
			if (inmueb == null) {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN,
						"Notificacion", "No existe el inmueble ingresado.");

				FacesContext.getCurrentInstance().addMessage(null, msg);
			} else {
				inmueb.setNumregistrosbn(upa.getInmueble().getNumregistrosbn());
				upa.setInmueble(inmueb);

				

				String usercreador = userMB.getUsuariologueado().getNombres()
						+ " " + userMB.getUsuariologueado().getApellidopat();
				upa.setUsrcre(usercreador);
				upa.setFeccre(ahora);
				
				getUpaService().registrarUpa(upa);
				/****** cargado de archivos ****/
				registrarArchivo();
				/****** ****/
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Exito", "Se registro correctamente la UPA.");

				FacesContext.getCurrentInstance().addMessage(null, msg);
			}

		}
		// para refrescar el valor en el panel
		ActualizarMensajes();

	}

	public void onRowSelect(SelectEvent event) {
		System.out.println("id Upa cap:::" + upa.getIdupa());

		actualizado = true;
		/****** cargado de archivos ****/
		if (aaMB.getArchivoadjuntoService()	.obtenerArchivodocumentosPorIdUpa(upa.getIdupa()).isEmpty()) {
			
			aaMB.nuevaListaVacia();
		} else {

			// seteamos la lista de archivos obtenidos, perteneciente a una upa
			aaMB.setDisplayList(aaMB.getArchivoadjuntoService()
					.obtenerArchivodocumentosPorIdUpa(upa.getIdupa()));
			//para k no limpie la lista
			aaMB.setValorinicio(false);
		}
		/**********/
	}

	public void eliminarUpa() {
		System.out.println("controlador eliminar Upa::");
		getUpaService().eliminarUpa(upacapturado);

		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Exito", "Se eliminó el Upa  correctamente.");

		FacesContext.getCurrentInstance().addMessage(null, msg);
		limpiarCampos();
		ActualizarMensajes();
	}

	




	public void limpiarCampos() {
		
		System.out.println("paso limpieza!!");
		upa = new Upa();
		aaMB.nuevaListaVaciaInicial();
		actualizado = false;
	}

	public void cargarArchivo(FileUploadEvent event) {
		System.out.println("ccccc");
	}

	public IUpaService getUpaService() {
		return upaService;
	}

	public void setUpaService(IUpaService upaService) {
		this.upaService = upaService;
	}

	public UsuarioManagedBean getUserMB() {
		return userMB;
	}

	public void setUserMB(UsuarioManagedBean userMB) {
		this.userMB = userMB;
	}

	public List<Upa> getUpas() {
		upas = getUpaService().listarUpas();
		return upas;
	}

	public void setUpas(List<Upa> upas) {
		this.upas = upas;
	}

	public Upa getUpa() {
		if (upa == null) {
			upa = new Upa();
		}
		return upa;
	}

	public void setUpa(Upa upa) {
		this.upa = upa;
	}

	public Upa getUpacapturado() {
		return upacapturado;
	}

	public void setUpacapturado(Upa upacapturado) {
		this.upacapturado = upacapturado;
	}

	public Upa getResultadoupa() {
		return resultadoupa;
	}

	public void setResultadoupa(Upa resultadoupa) {
		this.resultadoupa = resultadoupa;
	}

	public int getNumupas() {
		return numupas;
	}

	public void setNumupas(int numupas) {
		this.numupas = numupas;
	}

	public List<Tipovia> getListaTipoVia() {
		listaTipoVia = getUpaService().listarTipoVia();
		return listaTipoVia;
	}

	public void setListaTipoVia(List<Tipovia> listaTipoVia) {
		this.listaTipoVia = listaTipoVia;
	}

	

	public List<Titularidad> getListaTitularidad() {
		return listaTitularidad;
	}

	public void setListaTitularidad(List<Titularidad> listaTitularidad) {
		this.listaTitularidad = listaTitularidad;
	}

	public List<Tipotitularidad> getListaTipoTitularidad() {
		return listaTipoTitularidad;
	}

	public void setListaTipoTitularidad(
			List<Tipotitularidad> listaTipoTitularidad) {
		this.listaTipoTitularidad = listaTipoTitularidad;
	}

	public List<Materialpredominante> getListaMaterialPredominante() {
		return listaMaterialPredominante;
	}

	public void setListaMaterialPredominante(
			List<Materialpredominante> listaMaterialPredominante) {
		this.listaMaterialPredominante = listaMaterialPredominante;
	}

	public List<Tipointerior> getListaTipoInterior() {
		listaTipoInterior = getUpaService().listarTipoInterior();
		return listaTipoInterior;
	}

	public void setListaTipoInterior(List<Tipointerior> listaTipoInterior) {
		this.listaTipoInterior = listaTipoInterior;
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

	public List<String> getListaUbigeos() {
		return listaUbigeos;
	}

	public void setListaUbigeos(List<String> listaUbigeos) {
		this.listaUbigeos = listaUbigeos;
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

	

	public List<Valuacion> getListaValuacion() {
		listaValuacion = getUpaService().listarValuacion();
		return listaValuacion;
	}

	public void setListaValuacion(List<Valuacion> listaValuacion) {
		this.listaValuacion = listaValuacion;
	}

	

	public List<Uso> getListaUso() {
		listaUso = getUpaService().listarUso();
		return listaUso;
	}

	public List<Mep> getListaMep() {
		listaMep = getUpaService().listarMep();
		return listaMep;
	}

	public void setListaMep(List<Mep> listaMep) {
		this.listaMep = listaMep;
	}

	public Inmueble getInmueble() {
		return inmueble;
	}

	public void setInmueble(Inmueble inmueble) {
		this.inmueble = inmueble;
	}

	public void setListaUso(List<Uso> listaUso) {
		this.listaUso = listaUso;
	}

	public ArchivoAdjuntoManagedBean getAaMB() {
		return aaMB;
	}

	public void setAaMB(ArchivoAdjuntoManagedBean aaMB) {
		this.aaMB = aaMB;
	}

}

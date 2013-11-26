package com.sblm.controller;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;

import com.sblm.model.Arbitrio;
import com.sblm.model.Archivodocumento;
import com.sblm.model.Califica;
import com.sblm.model.Extensionarchivo;
import com.sblm.model.Inquilino;
import com.sblm.model.Tipoentidad;
import com.sblm.model.Tipopersona;
import com.sblm.service.IInquilinoService;
import com.sblm.util.PerfilModuloPermiso;

@ManagedBean(name = "inquilinoMB")
@ViewScoped
public class InquilinoManagedBean implements Serializable {
	private static final long serialVersionUID = 5524190003746598593L;
	@ManagedProperty(value = "#{inquilinoService}")
	private transient IInquilinoService inquilinoService;
	@ManagedProperty(value = "#{usuarioMB}")
	UsuarioManagedBean userMB;
	@ManagedProperty(value = "#{inmuebleMB}")
	InmuebleManagedBean inmuebleMB;
	private List<Inquilino> inquilinos;

	private Inquilino inquilino;
	private Inquilino inquilinocapturado;
	boolean actualizado = false;
	private Inquilino resultadoinquilino;
	private int numinquilinos;
	private List<Tipopersona> listaTipoPersona;
	private List<Tipoentidad> listaTipoEntidad;
	private boolean repetido = false;
	private boolean mostrarTablaCalifica = false;
	private List<Califica> listacalifica;
	private Califica califica;
	private boolean estadoagregar = false;
	private Califica calificacionquitado;
	Califica califi;
	/****** cargado de archivos ****/
	@ManagedProperty(value = "#{archivoadjuntoMB}")
	ArchivoAdjuntoManagedBean aaMB;

	/*******************************/
	@PostConstruct
	public void initObjects() {
		/****** cargado de archivos ****/
		aaMB.nuevaListaVaciaInicial();
		inicializarCalifica();

		/*******************************/
		try {
			inmuebleMB.getListaDptos();
			ActualizarMensajes();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void inicializarCalifica(){
		listacalifica = new ArrayList<Califica>();
	}


	public void agregarCalifica() {
			califi = new Califica();
			
			califi.setCalifica(califica.getCalifica());
			califi.setConcepto(califica.getConcepto());
			califi.setInquilino(inquilino);
			
			listacalifica.add(califi);
			mostrarTablaCalifica = true;
			estadoagregar = true;
			
	}

	public void quitarCalifica() {
		actualizado = true;
		System.out.println("aaaa:::" + califica.getConcepto());

		for (Iterator<Califica> iter = listacalifica.iterator(); iter.hasNext();) {
			Califica algo = iter.next();
			// hacer algo con algo
			if (algo.equals(calificacionquitado)) {
				iter.remove(); // Esto quita el elemento actual de la lista, SIN
				// causar problemas
			}
		}
	

	}

	public void cargarUltimoregistro() {
		actualizado = true;
		inquilino = getInquilinoService().obtenerUltimoInquilino();
	}

	public void validarRepetido(String dni) {
		if (getInquilinoService().validarRepetido(dni) != null) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Notificacion", "Ya existe El Numero de DNI!!!");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			repetido = true;
		}else{
			repetido = false;
		}
	}

	/****** cargado de archivos ****/
	public void cargadoArchivo(FileUploadEvent event) throws IOException {
		aaMB.cargarArchivo(event);
	}

	public void registrarCalificacion() {
		for (Califica pm : listacalifica) {
			
//			Inquilino in = new Inquilino();
//			in.setIdinquilino(4);
//			pm.setInquilino(in);
			Califica ca=new Califica();
			ca.setCalifica(pm.getCalifica()); 
			ca.setConcepto(pm.getConcepto());
			ca.setInquilino(inquilino);
			getInquilinoService().registrarCalificacion(ca);
		}
		
		inicializarCalifica();
		
	}

	public void registrarArchivo() {
		if (aaMB.isValorinicio() == true) {
			aaMB.getDisplayList().clear();
		}

		if (aaMB.getDisplayList().size() == 0) {
			System.out.println("##########no se adjuntaron registros!!!");
			aaMB.nuevaListaVacia();
		} else {
			// escribimos fisicamente el archivo
			aaMB.escribirEnServidor();
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
					// modificamos de acuerdo al mantenimiento
					adocu.setInquilino(inquilino);
				} else {
					// modificamos de acuerdo al mantenimiento
					adocu.setInquilino(aaMB.getArchivoadjuntoService()
							.obtenerUltimoInquilino());
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
//				Arbitrio ar=new Arbitrio();
//				ar.setIdarbitrio(1);
//				adocu.setArbitrio(ar);
				getAaMB().getArchivoadjuntoService().registrarArchivosDocumento(adocu);
			}

		}
	}

	/*******************************/
	public void calificacionAceptado() {
		System.out.println("calificacionAceptado");

	}

	public void calificacionCancelado() {
		System.out.println("calificacionCancelado");

	}

	public void cargarComboProvincias() {
		System.out.println("dpto:::" + inquilino.getDpto());
		inmuebleMB.setListaProvincias(inmuebleMB.getInmuebleService()
				.listarProvincias(inquilino.getDpto()));
	}

	public void cargarComboDistritos() {
		System.out.println("provincia:::" + inquilino.getProvincia());
		inmuebleMB.setListaDistritos(inmuebleMB.getInmuebleService()
				.listarDistritos(inquilino.getProvincia()));
	}

	public void ActualizarMensajes() {
		resultadoinquilino = getInquilinoService().obtenerUltimoInquilino();
		numinquilinos = getInquilinoService().obtenerNumeroRegistros();
	}

	public void registrarInquilino() {
		
		Date ahora = new Date();
		
			if (actualizado == true) {
				System.out.println("::::::entro actualizado");
				//eliminamos califica k pertenece a un inquilino
				getInquilinoService().eliminarCalificacion(inquilino);
				//registro de las calificaciones
				registrarCalificacion();
				
				/****** cargado de archivos ****/
				// modificar de acuerdo al mantenimiento
				aaMB.getArchivoadjuntoService()
						.eliminarArhivoDocumentoInquilino(inquilino);
				registrarArchivo();
				/******  ****/

				String usermodificador = userMB.getUsuariologueado()
						.getNombres()
						+ " "
						+ userMB.getUsuariologueado().getApellidopat();
				inquilino.setFecmod(ahora);
				inquilino.setUsrmod(usermodificador);

				getInquilinoService().registrarInquilino(inquilino);
				
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Exito", "Se Actualizo correctamente el Inquilino.");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			} else {
				validarRepetido(inquilino.getDni());
				if (repetido == false) {
				System.out.println("::::::entro registro");

				String usercreador = userMB.getUsuariologueado().getNombres()
						+ " " + userMB.getUsuariologueado().getApellidopat();
				inquilino.setUsrcre(usercreador);
				inquilino.setFeccre(ahora);
				inquilino.setNombrescompletos(inquilino.getNombre()+ " "+inquilino.getApellidopat()+" "+inquilino.getApellidomat());
				getInquilinoService().registrarInquilino(inquilino);

				/****** cargado de archivos ****/
				registrarArchivo();
				/****** ****/

				
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Exito", "Se registro correctamente el Inquilino.");

				FacesContext.getCurrentInstance().addMessage(null, msg);
			//	repetido = false;
				} else {
					FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error",
							"El Numero DNI ya se encuentra registrado en la Base de datos.");

					FacesContext.getCurrentInstance().addMessage(null, msg);
					//repetido = false;
				}
			}
		
		// para refrescar el valor en el panel
		ActualizarMensajes();

	}

	public void eliminarInquilino() {
		System.out.println("controlador eliminar inquilino::");
		getInquilinoService().eliminarInquilino(inquilinocapturado);

		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Exito", "Se eliminó el Inquilino "
						+ inquilinocapturado.getNombre() + " "
						+ inquilinocapturado.getApellidopat() + " "
						+ inquilinocapturado.getApellidomat()
						+ " correctamente.");

		FacesContext.getCurrentInstance().addMessage(null, msg);
		limpiarCampos();
		ActualizarMensajes();
	}

	public void limpiarCampos() {
		System.out.println("paso limpieza!!");
		inquilino = new Inquilino();

		aaMB.nuevaListaVaciaInicial();
		actualizado = false;
	}

	public void onRowSelect(SelectEvent event) {
		System.out.println("id inquilino cap:::" + inquilino.getIdinquilino());
		mostrarTablaCalifica=true;
		listacalifica =getInquilinoService().listarCalificacion(inquilino);
		actualizado = true;
		/****** cargado de archivos ****/
		// modificar de acuerdo al mantenimiento
		if (aaMB.getArchivoadjuntoService()
				.obtenerArchivodocumentosPorIdInquilino(
						inquilino.getIdinquilino()).isEmpty()) {

			aaMB.nuevaListaVacia();
		} else {

			// seteamos la lista de archivos obtenidos, perteneciente a una upa
			// modificar de acuerdo al mantenimiento
			aaMB.setDisplayList(aaMB.getArchivoadjuntoService()
					.obtenerArchivodocumentosPorIdInquilino(
							inquilino.getIdinquilino()));
			// para k no limpie la lista
			aaMB.setValorinicio(false);
		}
		/**********/

	}

	public IInquilinoService getInquilinoService() {
		return inquilinoService;
	}

	public void setInquilinoService(IInquilinoService inquilinoService) {
		this.inquilinoService = inquilinoService;
	}

	public UsuarioManagedBean getUserMB() {
		return userMB;
	}

	public void setUserMB(UsuarioManagedBean userMB) {
		this.userMB = userMB;
	}

	public List<Inquilino> getInquilinos() {
		inquilinos = getInquilinoService().listarInquilinos();
		return inquilinos;
	}

	public void setInquilinos(List<Inquilino> inquilinos) {
		this.inquilinos = inquilinos;
	}

	public Inquilino getInquilino() {
		if (inquilino == null) {
			inquilino = new Inquilino();
		}
		return inquilino;
	}

	public void setInquilino(Inquilino inquilino) {
		this.inquilino = inquilino;
	}

	public Inquilino getInquilinocapturado() {
		return inquilinocapturado;
	}

	public void setInquilinocapturado(Inquilino inquilinocapturado) {
		this.inquilinocapturado = inquilinocapturado;
	}

	public Inquilino getResultadoinquilino() {
		return resultadoinquilino;
	}

	public void setResultadoinquilino(Inquilino resultadoinquilino) {
		this.resultadoinquilino = resultadoinquilino;
	}

	public int getNuminquilinos() {
		return numinquilinos;
	}

	public void setNuminquilinos(int numinquilinos) {
		this.numinquilinos = numinquilinos;
	}

	public List<Tipopersona> getListaTipoPersona() {
		listaTipoPersona = getInquilinoService().listarTipoPersona();
		return listaTipoPersona;
	}

	public void setListaTipoPersona(List<Tipopersona> listaTipoPersona) {
		this.listaTipoPersona = listaTipoPersona;
	}

	public List<Tipoentidad> getListaTipoEntidad() {
		listaTipoEntidad = getInquilinoService().listarTipoEntidad();
		return listaTipoEntidad;
	}

	public void setListaTipoEntidad(List<Tipoentidad> listaTipoEntidad) {
		this.listaTipoEntidad = listaTipoEntidad;
	}

	public InmuebleManagedBean getInmuebleMB() {
		return inmuebleMB;
	}

	public void setInmuebleMB(InmuebleManagedBean inmuebleMB) {
		this.inmuebleMB = inmuebleMB;
	}

	public ArchivoAdjuntoManagedBean getAaMB() {
		return aaMB;
	}

	public void setAaMB(ArchivoAdjuntoManagedBean aaMB) {
		this.aaMB = aaMB;
	}

	public boolean isMostrarTablaCalifica() {
		return mostrarTablaCalifica;
	}

	public void setMostrarTablaCalifica(boolean mostrarTablaCalifica) {
		this.mostrarTablaCalifica = mostrarTablaCalifica;
	}

	public List<Califica> getListacalifica() {

		// listacalifica = getInquilinoService().listarCalificacion();
		return listacalifica;
	}

	public void setListacalifica(List<Califica> listacalifica) {
		this.listacalifica = listacalifica;
	}

	public Califica getCalifica() {
		if (califica == null) {
			califica = new Califica();
		}
		return califica;
	}

	public void setCalifica(Califica califica) {
		this.califica = califica;
	}

	public Califica getCalificacionquitado() {
		return calificacionquitado;
	}

	public void setCalificacionquitado(Califica calificacionquitado) {
		this.calificacionquitado = calificacionquitado;
	}

}

package com.sblm.controller;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import com.sblm.model.Institucion;
import com.sblm.service.IInstitucionService;
import com.sblm.util.FuncionesHelper;

@ManagedBean(name = "institucionMB")
@ViewScoped
public class InstitucionManagedBean implements Serializable {

	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{institucionService}")
	private transient IInstitucionService institucionService;
	Institucion institucion;
	List<Institucion> listaInstituciones;
	private int nroInstituciones;
	private Institucion ultimaInstitucion;
	
	@ManagedProperty(value = "#{usuarioMB}")
	UsuarioManagedBean userMB;
	

	@PostConstruct
	public void initObjects() {
		institucion = new Institucion();
		obtenerInstituciones();
		obtenerUltimaInstitucionCreada();
		nroInstituciones();
	}
	
	public void obtenerUltimaInstitucionCreada() {
		ultimaInstitucion=listaInstituciones.get(0);
	}
	
	public void obtenerInstituciones(){
		listaInstituciones=institucionService.obtenerTodosInstituciones();
	}

	public void limpiarRegistro() {
		institucion=new Institucion();
		institucion.setIdinstitucion(0);
	}
	
	public void grabarRepresentante(){
		if (institucion.getIdinstitucion()==0) {
			institucion.setFechacreacion(new Date());
			institucion.setUsuariocreador(userMB.getUsuariologueado().getNombrescompletos());
			institucionService.grabarInstitucion(institucion);
			limpiarRegistro();
			obtenerInstituciones();
			nroInstituciones();
			obtenerUltimaInstitucionCreada();
		} else {
			institucion.setFechamodificacion(new Date());
			institucion.setUsuariomodificador(userMB.getUsuariologueado().getNombrescompletos());
			institucionService.grabarInstitucion(institucion);
			limpiarRegistro();
			obtenerInstituciones();
			nroInstituciones();
		}
		
	}
	
	public void nroInstituciones(){
		nroInstituciones=institucionService.nroInstituciones();
		
	}
	
	public void eliminarRepresentante() {
		institucionService.eliminarInstitucion(institucion);
		obtenerInstituciones();
		nroInstituciones();
		obtenerUltimaInstitucionCreada();
		limpiarRegistro();
	}

	public IInstitucionService getInstitucionService() {
		return institucionService;
	}

	public void setInstitucionService(IInstitucionService institucionService) {
		this.institucionService = institucionService;
	}

	public Institucion getInstitucion() {
		return institucion;
	}

	public void setInstitucion(Institucion institucion) {
		this.institucion = institucion;
	}

	public List<Institucion> getListaInstituciones() {
		return listaInstituciones;
	}

	public void setListaInstituciones(List<Institucion> listaInstituciones) {
		this.listaInstituciones = listaInstituciones;
	}
	public int getNroInstituciones() {
		return nroInstituciones;
	}
	public void setNroInstituciones(int nroInstituciones) {
		this.nroInstituciones = nroInstituciones;
	}
	public UsuarioManagedBean getUserMB() {
		return userMB;
	}
	public void setUserMB(UsuarioManagedBean userMB) {
		this.userMB = userMB;
	}
	public Institucion getUltimaInstitucion() {
		return ultimaInstitucion;
	}
	public void setUltimaInstitucion(Institucion ultimaInstitucion) {
		this.ultimaInstitucion = ultimaInstitucion;
	}
	
}

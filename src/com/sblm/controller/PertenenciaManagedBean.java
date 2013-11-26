package com.sblm.controller;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;

import com.sblm.model.Pertenencia;
import com.sblm.model.Uso;
import com.sblm.service.IPertenenciaService;

@ManagedBean(name = "pertenenciaMB")
@ViewScoped
public class PertenenciaManagedBean implements Serializable {

	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{pertenenciaService}")
	private transient IPertenenciaService pertenenciaService;
	@ManagedProperty(value = "#{usuarioMB}")
	UsuarioManagedBean userMB;
	
	private List<Pertenencia> pertenencias;
	
	private Pertenencia pertenencia;
	private Pertenencia pertenenciacapturado;
	boolean actualizado = false;
	private Pertenencia resultadopertenencia;
	private int numpertenencias;
	@PostConstruct
	public void initObjects() {

		try {

			ActualizarMensajes();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void ActualizarMensajes() {
		resultadopertenencia=getPertenenciaService().obtenerUltimoPertenencia();
		numpertenencias=getPertenenciaService().obtenerNumeroRegistros();
	}
public void registrarPertenencia() {
		
		Date ahora = new Date();
		
		
		if (actualizado == true) {
			System.out.println("::::::entro actualizado");
			String usermodificador =userMB.getUsuariologueado().getNombres()+" "+userMB.getUsuariologueado().getApellidopat();
			pertenencia.setFecmod(ahora);
			pertenencia.setUsrmod(usermodificador);
			getPertenenciaService().registrarPertenencia(pertenencia);
			FacesMessage msg = new FacesMessage(
					"Se Actualizo correctamente el registro de  Pertenencia.");

			FacesContext.getCurrentInstance().addMessage(null, msg);
		} else {
			System.out.println("::::::entro registro");
			String usercreador =userMB.getUsuariologueado().getNombres()+" "+userMB.getUsuariologueado().getApellidopat();
			pertenencia.setUsrcre(usercreador);
			pertenencia.setFeccre(ahora);
			getPertenenciaService().registrarPertenencia(pertenencia);
			FacesMessage msg = new FacesMessage(
					"Se Registro correctamente el registro de Pertenencia.");

			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		// para refrescar el valor en el panel
		ActualizarMensajes();

	}


public void eliminarPertenencia() {
	getPertenenciaService().eliminarPertenencia(pertenenciacapturado);
	
	
	FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
			"Exito", "Se eliminó el Uso "
					+ pertenenciacapturado.getDescripcion()
					+ " correctamente.");

	FacesContext.getCurrentInstance().addMessage(null, msg);
	limpiarCampos();
	ActualizarMensajes();
}
public void limpiarCampos() {

	pertenencia = null;
}

public void onRowSelect(SelectEvent event) {
	actualizado = true;
	
}
public IPertenenciaService getPertenenciaService() {
	return pertenenciaService;
}
public void setPertenenciaService(IPertenenciaService pertenenciaService) {
	this.pertenenciaService = pertenenciaService;
}
public UsuarioManagedBean getUserMB() {
	return userMB;
}
public void setUserMB(UsuarioManagedBean userMB) {
	this.userMB = userMB;
}
public List<Pertenencia> getPertenencias() {
	return getPertenenciaService().listarPertenencias();
}
public void setPertenencias(List<Pertenencia> pertenencias) {
	this.pertenencias = pertenencias;
}
public Pertenencia getPertenencia() {
	if (pertenencia == null) {
		pertenencia = new Pertenencia();
	}
	return pertenencia;
}
public void setPertenencia(Pertenencia pertenencia) {
	this.pertenencia = pertenencia;
}
public Pertenencia getPertenenciacapturado() {
	return pertenenciacapturado;
}
public void setPertenenciacapturado(Pertenencia pertenenciacapturado) {
	this.pertenenciacapturado = pertenenciacapturado;
}
public Pertenencia getResultadopertenencia() {
	return resultadopertenencia;
}
public void setResultadopertenencia(Pertenencia resultadopertenencia) {
	this.resultadopertenencia = resultadopertenencia;
}
public int getNumpertenencias() {
	return numpertenencias;
}
public void setNumpertenencias(int numpertenencias) {
	this.numpertenencias = numpertenencias;
}

}

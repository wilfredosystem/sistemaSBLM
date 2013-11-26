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

import com.sblm.model.Igv;
import com.sblm.model.Uso;
import com.sblm.service.IIgvService;

@ManagedBean(name = "igvMB")
@ViewScoped
public class IgvManagedBean implements Serializable {

	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{igvService}")
	private transient IIgvService igvService;
	@ManagedProperty(value = "#{usuarioMB}")
	UsuarioManagedBean userMB;
	
	private List<Igv> igvs;
	
	private Igv igv;
	private Igv igvcapturado;
	boolean actualizado = false;
	private Igv resultadoigv;
	private int numigvs;
	@PostConstruct
	public void initObjects() {

		try {

			ActualizarMensajes();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void ActualizarMensajes() {
		resultadoigv=getIgvService().obtenerUltimoIgv();
		numigvs=getIgvService().obtenerNumeroRegistros();
	}
public void registrarIgv() {
		
		Date ahora = new Date();
		
		
		if (actualizado == true) {
			System.out.println("::::::entro actualizado");
			String usermodificador =userMB.getUsuariologueado().getNombres()+" "+userMB.getUsuariologueado().getApellidopat();
			igv.setFecmod(ahora);
			igv.setUsrmod(usermodificador);
			getIgvService().registrarIgv(igv);
			FacesMessage msg = new FacesMessage(
					"Se Actualizo correctamente el Igv.");

			FacesContext.getCurrentInstance().addMessage(null, msg);
		} else {
			System.out.println("::::::entro registro");
			String usercreador =userMB.getUsuariologueado().getNombres()+" "+userMB.getUsuariologueado().getApellidopat();
			igv.setUsrcre(usercreador);
			igv.setFeccre(ahora);
			getIgvService().registrarIgv(igv);
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Exito", "Se Registro correctamente el IGV.");
			

			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		// para refrescar el valor en el panel
		ActualizarMensajes();

	}


public void eliminarIgv() {
	getIgvService().eliminarIgv(igvcapturado);
	
	
	FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
			"Exito", "Se eliminó el Igv "
					+ igvcapturado.getValorigv()
					+ " correctamente.");

	FacesContext.getCurrentInstance().addMessage(null, msg);
	limpiarCampos();
	ActualizarMensajes();
}
public void limpiarCampos() {

	igv = null;
}

public void onRowSelect(SelectEvent event) {
	actualizado = true;
	
}
public IIgvService getIgvService() {
	return igvService;
}
public void setIgvService(IIgvService igvService) {
	this.igvService = igvService;
}
public UsuarioManagedBean getUserMB() {
	return userMB;
}
public void setUserMB(UsuarioManagedBean userMB) {
	this.userMB = userMB;
}

public Igv getIgv() {
	if (igv == null) {
		igv = new Igv();
	}
	return igv;
}
public void setIgv(Igv igv) {
	this.igv = igv;
}
public Igv getIgvcapturado() {
	return igvcapturado;
}
public void setIgvcapturado(Igv igvcapturado) {
	this.igvcapturado = igvcapturado;
}
public Igv getResultadoigv() {
	return resultadoigv;
}
public void setResultadoigv(Igv resultadoigv) {
	this.resultadoigv = resultadoigv;
}
public int getNumigvs() {
	return numigvs;
}
public void setNumigvs(int numigvs) {
	this.numigvs = numigvs;
}
public List<Igv> getIgvs() {
	return getIgvService().listarIgvs();
	
}
public void setIgvs(List<Igv> igvs) {
	this.igvs = igvs;
}
}

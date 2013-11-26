package com.sblm.controller;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;

import com.sblm.model.Modulo;
import com.sblm.model.Tipocambio;
import com.sblm.model.Usuario;
import com.sblm.service.ITipoCambioService;
import com.sblm.service.IUsuarioService;

@ManagedBean(name = "tipocambioMB")
@ViewScoped
public class TipoCambioManagedBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{tipocambioService}")
	private transient ITipoCambioService tipocambioService;
	@ManagedProperty(value = "#{usuarioMB}")
	UsuarioManagedBean userMB;

	private List<Tipocambio> tipocambios;

	private Tipocambio tipoCam;
	private Double resultadoTipoCambio;
	private String mes;
	private Usuario usuario;

	boolean actualizado = false;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@PostConstruct
	public void initObjects() {

		try {
			Tipocambio tipocambio = getTipocambioService().obtenerTipoCambio();
			
			if(tipocambio==null){
				resultadoTipoCambio =0.0;
			}else{
				resultadoTipoCambio = tipocambio.getTipocambio();
			}
			mes = getTipocambioService().obtenerMes();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
public String  irtipocambio(){
		
		return "pgTipoCambio?faces-redirect=true";
	}
	

	public void onRowSelect(SelectEvent event) {
		actualizado = true;
		// tipoCam=tipocambio;
		FacesMessage msg = new FacesMessage("tipo cambio :"
				+ tipoCam.getTipocambio());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void limpiarCampos() {
		tipoCam = null;
	}

	public void registrarTipoCambio() throws ParseException {
		
		Date ahora = new Date();
		
		
		if (actualizado == true) {
			String usermodificador =userMB.getUsuariologueado().getNombres()+" "+userMB.getUsuariologueado().getApellidopat();
			tipoCam.setFecha(ahora);
			tipoCam.setUsrmod(usermodificador);
			getTipocambioService().registrarTipoCambio(tipoCam);
			FacesMessage msg = new FacesMessage(
					"Se Actualizo correctamente el tipo de cambio.");

			FacesContext.getCurrentInstance().addMessage(null, msg);
		} else {
			String usercreador =userMB.getUsuariologueado().getNombres()+" "+userMB.getUsuariologueado().getApellidopat();
			tipoCam.setUsrcre(usercreador);
			tipoCam.setFeccre(ahora);
			getTipocambioService().registrarTipoCambio(tipoCam);
			FacesMessage msg = new FacesMessage(
					"Se Registro correctamente el tipo de cambio.");

			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		// para refrescar el valor en el panel
		Tipocambio tipocamb = getTipocambioService().obtenerTipoCambio();
		resultadoTipoCambio = tipocamb.getTipocambio();

	}

	public ITipoCambioService getTipocambioService() {
		return tipocambioService;
	}

	public void setTipocambioService(ITipoCambioService tipocambioService) {
		this.tipocambioService = tipocambioService;
	}

	public Tipocambio getTipoCam() {
		if (tipoCam == null) {
			tipoCam = new Tipocambio();
		}
		return tipoCam;
	}

	public void setTipoCam(Tipocambio tipoCam) {
		this.tipoCam = tipoCam;
	}

	public Double getResultadoTipoCambio() {
		return resultadoTipoCambio;
	}

	public void setResultadoTipoCambio(Double resultadoTipoCambio) {
		this.resultadoTipoCambio = resultadoTipoCambio;
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public List<Tipocambio> getTipocambios() {
		tipocambios = getTipocambioService().listarTipoCambios();
		return tipocambios;
	}

	public void setTipocambios(List<Tipocambio> tipocambios) {
		this.tipocambios = tipocambios;
	}

	public UsuarioManagedBean getUserMB() {
		return userMB;
	}

	public void setUserMB(UsuarioManagedBean userMB) {
		this.userMB = userMB;
	}

}

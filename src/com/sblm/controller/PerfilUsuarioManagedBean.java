package com.sblm.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.sblm.model.Modulo;
import com.sblm.model.Perfil;
import com.sblm.model.Perfilusuario;
import com.sblm.service.IPerfilService;
import com.sblm.service.IPerfilUsuarioService;
import com.sblm.util.FuncionesHelper;

@ManagedBean(name = "perfilusuarioMB")
@ViewScoped
public class PerfilUsuarioManagedBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{perfilusuarioService}")
	private transient IPerfilUsuarioService perfilusuarioService;
	private Perfilusuario perfilusuario;
	private List<Perfilusuario> perfilusuarios;
	
	private List<Perfilusuario> perfilusuariosporid;
	@ManagedProperty(value = "#{perfilService}")
	private transient IPerfilService perfilService;
	private Perfil perfil;
	private Perfilusuario [] listadoperfilusuarios;
	
	@ManagedProperty(value = "#{usuarioMB}")
	UsuarioManagedBean userMB;
	 private int idperfilseleccionado;
	
	
	public void cambiarPerfil() {
//		userMB.getUsuariologueado().setIdusuario(userMB.getUsuariologueado().getIdusuario(););
//		userMB.obtenerMenu();
		System.out.println("ultimo idperfilseleccionado:"+idperfilseleccionado);
		int idusu=userMB.getUsuariologueado().getIdusuario();
		List <Perfilusuario> listaperusu= new ArrayList <Perfilusuario>();
		listaperusu=getPerfilusuarioService().listarPerfilUsuariosPorId(idusu);
		for (Perfilusuario perfilus : listaperusu) {
			System.out.println("###############");
			System.out.println("perfil estado:::"+perfilus.getPerfil().getIdperfil());
			System.out.println("perfil:::"+perfilus.getPerfil().getNombreperfil());
			System.out.println("perfil estado:::"+perfilus.getActivo());
			if(idperfilseleccionado==perfilus.getPerfil().getIdperfil()){
				System.out.println("###verdada"+idperfilseleccionado);
				perfilus.setActivo(true);
				getPerfilusuarioService().actualizarPerfilUsuario(perfilus);
			}else{
				System.out.println("###falso"+idperfilseleccionado);
				perfilus.setActivo(false);
				getPerfilusuarioService().actualizarPerfilUsuario(perfilus);
			}
		}
		userMB.getUsuariologueado().setIdusuario(idusu);
		userMB.obtenerMenu();
		FacesMessage msg = new FacesMessage("Id :" + "mensajillo");
		FacesContext.getCurrentInstance().addMessage(null, msg);
		FuncionesHelper.idperfilseleccionado=idperfilseleccionado;
		userMB.obtenerPerfil();
	}
	public void registrarPerfilUsuario() {
		registrarPerfil();
		getPerfilService().obtenerUltimoIdPerfil();
		System.out.println("paso controller perfilusuario");
		System.out.println("ultimo id de perfil:"+getPerfilService().obtenerUltimoIdPerfil());

		FacesMessage msg = new FacesMessage("Id :" + perfilusuario.getIdperfilusuario());
		getPerfilusuarioService().registrarPerfilUsuario(perfilusuario);
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	public void registrarPerfil( ) {
		System.out.println(":::::registrar  Perfil  MB:::::");
		System.out.println("id perfil:"+perfil.getIdperfil());
		System.out.println("nombre perfil:"+perfil.getNombreperfil());
		FacesMessage msg = new FacesMessage("Id :" + perfil.getIdperfil());
		getPerfilService().registrarPerfil(perfil);
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	public IPerfilUsuarioService getPerfilusuarioService() {
		return perfilusuarioService;
	}
	public void setPerfilusuarioService(IPerfilUsuarioService perfilusuarioService) {
		this.perfilusuarioService = perfilusuarioService;
	}
	public Perfilusuario getPerfilusuario() {
		if (perfilusuario == null) {
			perfilusuario = new Perfilusuario	();
		}
		return perfilusuario;
	}
	public void setPerfilusuario(Perfilusuario perfilusuario) {
		this.perfilusuario = perfilusuario;
	}
	public List<Perfilusuario> getPerfilusuarios() {
		perfilusuarios = getPerfilusuarioService().listarPerfilUsuarios();
		return perfilusuarios;
	}
	public void setPerfilusuarios(List<Perfilusuario> perfilusuarios) {
		this.perfilusuarios = perfilusuarios;
	}
	public IPerfilService getPerfilService() {
		return perfilService;
	}
	public void setPerfilService(IPerfilService perfilService) {
		this.perfilService = perfilService;
	}
	public Perfil getPerfil() {
		if (perfil == null) {
			perfil = new Perfil	();
		}
		return perfil;
	}
	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}
	public Perfilusuario[] getListadoperfilusuarios() {
		return listadoperfilusuarios;
	}
	public void setListadoperfilusuarios(Perfilusuario[] listadoperfilusuarios) {
		this.listadoperfilusuarios = listadoperfilusuarios;
	}
	public List<Perfilusuario> getPerfilusuariosporid() {
		int idusuariolog=userMB.getUsuariologueado().getIdusuario();
		perfilusuariosporid = getPerfilusuarioService().listarPerfilUsuariosPorId(idusuariolog);
		return perfilusuariosporid;
	}
	public void setPerfilusuariosporid(List<Perfilusuario> perfilusuariosporid) {
		this.perfilusuariosporid = perfilusuariosporid;
	}
	public UsuarioManagedBean getUserMB() {
		return userMB;
	}
	public void setUserMB(UsuarioManagedBean userMB) {
		this.userMB = userMB;
	}
	public int getIdperfilseleccionado() {
		return idperfilseleccionado;
	}
	public void setIdperfilseleccionado(int idperfilseleccionado) {
		this.idperfilseleccionado = idperfilseleccionado;
	}


//	public int getIdusuario() {
//		return idusuario;
//	}
//
//	public void setIdusuario(int idusuario) {
//		this.idusuario = idusuario;
//	}
	
	
}

package com.sblm.controller;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.primefaces.component.column.Column;
import org.primefaces.component.menuitem.MenuItem;
import org.primefaces.component.submenu.Submenu;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultMenuModel;
import org.primefaces.model.MenuModel;
import org.springframework.dao.DataAccessException;

import com.sblm.model.Pagina;
import com.sblm.model.Paginamodulo;
import com.sblm.model.Perfil;
import com.sblm.model.Perfilmodulo;
import com.sblm.model.Usuario;
import com.sblm.service.IModuloService;
import com.sblm.service.IPaginaService;
import com.sblm.service.IPerfilModuloService;
import com.sblm.service.IPerfilService;
import com.sblm.service.IUsuarioService;
import com.sblm.util.Correo;
import com.sblm.util.FuncionesHelper;

@ManagedBean(name = "usuarioMB")
@SessionScoped
public class UsuarioManagedBean implements Serializable {
	private static final long serialVersionUID = 5524190003746598593L;

	@ManagedProperty(value = "#{usuarioService}")
	private transient IUsuarioService usuarioService;

	private Usuario usuario;
	private List<Usuario> usuarios;
	List<Paginamodulo> page_module_ALL; 
	private String nombrecompleto;
	// @ManagedProperty(value = "#{usuarioService}")
	// private transient IUsuarioService usuarioService;

	private transient FacesContext context = FacesContext.getCurrentInstance();
	private String nombreusr;
	private String contrasenausr;
	private boolean loggedIn;

	private Object sesionUserName;

	private boolean modadministracion;
	private boolean modmantenimiento;
	private boolean modmodulos;
	private String horaconexion;

	HttpServletRequest request = (HttpServletRequest) FacesContext
			.getCurrentInstance().getExternalContext().getRequest();
	HttpSession varSesion = request.getSession();

	private int idperfil;
	// para obtener los atributos del user en las paginas
	private Usuario usuariologueado;
	// para menu dinamicos
	@ManagedProperty(value = "#{moduloService}")
	private transient IModuloService moduloService;
	private MenuModel model;

	@ManagedProperty(value = "#{paginaService}")
	private transient IPaginaService paginaService;

	@ManagedProperty(value = "#{perfilService}")
	private transient IPerfilService perfilService;
	private Perfil perfil;

	@ManagedProperty(value = "#{perfilmoduloService}")
	private transient IPerfilModuloService perfilmoduloService;
	
	private List<Usuario> usuariosdgi;
	
	private Boolean flag=false;
	
	@PostConstruct
	public void initObjects() {
		
	}
	
	public void actualizarDatosUsuario(){
		
		usuariologueado = getUsuarioService().buscarUsuarioxId(Integer.parseInt(FuncionesHelper.getUsuario().toString()));
		nombrecompleto =usuariologueado.getNombres()+" "+usuariologueado.getApellidopat();
		
	}

	public void cerrarSesion() {
		FacesContext context = FacesContext.getCurrentInstance();

		ExternalContext externalContext = context.getExternalContext();

		Object session = externalContext.getSession(false);

		HttpSession httpSession = (HttpSession) session;

		httpSession.invalidate();
	}

	
	public void loguear() {
		System.out.println("paso metodo login xxxxxxxxxxxxxx");
		RequestContext context = RequestContext.getCurrentInstance();
		
		loggedIn = false;

		Usuario usuario = new Usuario();
		usuario.setNombreusr(nombreusr);
		usuario.setContrasenausr(contrasenausr);
		// List <String> listauser= getUsuarioService().loguear(usuario);
		usuario = getUsuarioService().buscarUsuario(usuario);
		usuariologueado = usuario;
		
		Date ahora = new Date();
		SimpleDateFormat formateador = new SimpleDateFormat(
				"dd/MM/yyyy hh:mm:ss");
		formateador.format(ahora);
		horaconexion = formateador.format(ahora);
		if (usuario==null) {
			System.out.println("paso metodo login INCORRECTO");
			loggedIn = false;
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","Usuario o Contraseña incorrecto!!!"); 
			FacesContext.getCurrentInstance().addMessage(null, msg);

		} else {
			nombrecompleto =usuariologueado.getNombres()+" "+usuariologueado.getApellidopat();
			System.out.println("paso metodo login CORRECTO");
			page_module_ALL= new ArrayList<Paginamodulo>(getPaginaService().listarPaginasModulos());
			loggedIn = true;
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Mensaje","Login correcto"); 
			FacesContext.getCurrentInstance().addMessage(null, msg);
			
			varSesion.setAttribute("usuario", usuario.getIdusuario());
			Object sesionUserName = (Object) varSesion.getAttribute("usuario");
			getUsuarioService().grabarLogueo();
		}

		
		context.addCallbackParam("loggedIn", loggedIn);

		// ***********para manejar los estado del modulo*******//
		// ///////visualizacion de modulos dinamicos////////
		
			obtenerMenu();
			try {
			idperfil = getUsuarioService().obtenerIdPerfil(usuario);
			FuncionesHelper.idperfilseleccionado=idperfil;
			FuncionesHelper.nombreperfilSeleccionado=getUsuarioService().obtenerNombrePerfilSeleccionado(usuario);
			perfil = getPerfilService().listarPerfilPorId(idperfil);
			


		} catch (Exception e) {
			System.out.println("mi  error controller:" + e.getMessage());
		}

	}
	
	public void obtenerPerfil(){
		
		
		Usuario U= new Usuario();
		U.setIdusuario((Integer)FuncionesHelper.getUsuario());
		
		idperfil = getUsuarioService().obtenerIdPerfil(U);
		FuncionesHelper.idperfilseleccionado=idperfil;
		FuncionesHelper.nombreperfilSeleccionado=getUsuarioService().obtenerNombrePerfilSeleccionado(U);
		perfil = getPerfilService().listarPerfilPorId(idperfil);
		
		
	}
	
	public List<Pagina> paginasModulos(int idmodulo){
		List<Pagina> tmp= new ArrayList<Pagina>();
		for (Paginamodulo pm : page_module_ALL) {
			if (pm.getModulo().getIdmodulo()==idmodulo) {
				tmp.add(pm.getPagina());
			}
		}
		System.out.println("----------->"+tmp.size());
		
		return tmp;
	}

	public void obtenerMenu() {
		List<Pagina> lstpagina= new ArrayList<Pagina>();
		try {
			model = new DefaultMenuModel();
			
			List<Perfilmodulo> lstperfilmodulo = getPerfilmoduloService().listarPerfilModuloPorIdPerfil(getUsuarioService().obtenerIdPerfil(usuariologueado));

			for (Perfilmodulo modu : lstperfilmodulo) {
				Submenu submenup = new Submenu();
				System.out.println("modulocre#######:::"+modu.getModulo().getNombremodulo());
				System.out.println("moduloesatdo#######:::"+modu.getEstado());
				submenup.setLabel(modu.getModulo().getNombremodulo()); // menus
				submenup.setRendered(modu.getEstado());
				Column colum = new Column();
				Submenu submenu = new Submenu();

				//List<Pagina> lstpagina = getPaginaService().listarPaginasModulos(modu.getModulo().getIdmodulo());
				lstpagina = paginasModulos(modu.getModulo().getIdmodulo());
				for (Pagina pag : lstpagina) {
					System.out.println("hola mundo");
					
					MenuItem item = new MenuItem(); // items
					item.setValue(pag.getDescripcionpagina());
					item.setUrl(pag.getNombrepagina() + ".jsf");
					submenu.getChildren().add(item);
					
				}
				colum.getChildren().add(submenu);
				submenup.getChildren().add(colum);
				model.addSubmenu(submenup);
			}

		} catch (DataAccessException e) {
			System.out.println("error obtener menu: " + e.getMessage());
		}
	}
	public void adicionarUsuario() {
		try {
			getUsuarioService().crearUsuario(usuario);
		} catch (DataAccessException e) {
			System.out.println("error: " + e.getMessage());
		}
	}

	public void recuperarContrasenha() {

		String passRecuperado = getUsuarioService().obtenerContrasenha(
				usuario.getEmailusr());

		System.out.println("passxx: " + passRecuperado);
		if (passRecuperado == null) {
System.out.println("cambiar::::");
			// enviamos el mensaje en el growl
		
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Mensaje","no existe el correo en la BD."); 
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} else {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Mensaje","Se envio su contraseña a su correo electronico..."); 
			FacesContext.getCurrentInstance().addMessage(null, msg);
			try {
				
				context.addMessage(null, new FacesMessage(
						"Se envio su contraseña a su correo electronico..."));
				
				Correo correo=new Correo();
				String msj="Estimado(a) "+ " " + ", \n" + " su contraseña es: " + passRecuperado + "";
				
				correo.enviarCorreo(usuario.getEmailusr(), "Recuperacion de Contraseña", msj);
		
				
			} catch (Exception e) {
				System.out.println("ERROR AL ENVIAR CORREO!!");
				System.out.println(">> Error = " + e);
				// enviamos el mensaje de conformidad
				// context.addMessage(null, new FacesMessage(
				// "Error del servidor, no se pudo enviar su pass."));
			}
		}

	}

	public String navega() {
		System.out.println("error whr");
		return "/pages/login.jsf";

	}

	public IUsuarioService getUsuarioService() {
		return usuarioService;
	}

	public void setUsuarioService(IUsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	public Usuario getUsuario() {
		if (usuario == null) {
			usuario = new Usuario();
		}
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getUsuarios() {
		usuarios = getUsuarioService().listarUsuarios();
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public String getNombreusr() {
		return nombreusr;
	}

	public void setNombreusr(String nombreusr) {
		this.nombreusr = nombreusr;
	}

	public String getContrasenausr() {
		return contrasenausr;
	}

	public void setContrasenausr(String contrasenausr) {
		this.contrasenausr = contrasenausr;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	// public Perfilmodulo getPerfilmod() {
	// return perfilmod;
	// }
	//
	// public void setPerfilmod(Perfilmodulo perfilmod) {
	// this.perfilmod = perfilmod;
	// }

	public boolean isModadministracion() {
		return modadministracion;
	}

	public void setModadministracion(boolean modadministracion) {
		this.modadministracion = modadministracion;
	}

	public boolean isModmantenimiento() {
		return modmantenimiento;
	}

	public void setModmantenimiento(boolean modmantenimiento) {
		this.modmantenimiento = modmantenimiento;
	}

	public boolean isModmodulos() {
		return modmodulos;
	}

	public void setModmodulos(boolean modmodulos) {
		this.modmodulos = modmodulos;
	}

	public Usuario getUsuariologueado() {
		return usuariologueado;
	}

	public void setUsuariologueado(Usuario usuariologueado) {
		this.usuariologueado = usuariologueado;
	}

	public IModuloService getModuloService() {
		return moduloService;
	}

	public void setModuloService(IModuloService moduloService) {
		this.moduloService = moduloService;
	}

	public IPaginaService getPaginaService() {
		return paginaService;
	}

	public void setPaginaService(IPaginaService paginaService) {
		this.paginaService = paginaService;
	}

	public MenuModel getModel() {
		return model;
	}

	public void setModel(MenuModel model) {
		this.model = model;
	}

	public int getIdperfil() {
		return idperfil;
	}

	public void setIdperfil(int idperfil) {
		this.idperfil = idperfil;
	}

	public IPerfilService getPerfilService() {
		return perfilService;
	}

	public void setPerfilService(IPerfilService perfilService) {
		this.perfilService = perfilService;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public String getHoraconexion() {
		return horaconexion;
	}

	public void setHoraconexion(String horaconexion) {
		this.horaconexion = horaconexion;
	}

	public IPerfilModuloService getPerfilmoduloService() {
		return perfilmoduloService;
	}

	public void setPerfilmoduloService(IPerfilModuloService perfilmoduloService) {
		this.perfilmoduloService = perfilmoduloService;
	}

	public List<Usuario> getUsuariosdgi() {
		usuariosdgi = getUsuarioService().listarUsuarios(usuariologueado.getIdusuario());
		return usuariosdgi;
	}

	public void setUsuariosdgi(List<Usuario> usuariosdgi) {
		this.usuariosdgi = usuariosdgi;
	}

	public String getNombrecompleto() {
		return nombrecompleto;
	}

	public void setNombrecompleto(String nombrecompleto) {
		this.nombrecompleto = nombrecompleto;
	}

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}
	
}

package com.sblm.controller;

import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.StreamedContent;

import com.sblm.model.Archivodocumento;
import com.sblm.model.Area;
import com.sblm.model.Cliente;
import com.sblm.model.Contrato;
import com.sblm.model.Cuentabancaria;
import com.sblm.model.Extensionarchivo;
import com.sblm.model.Inmueble;
import com.sblm.model.Inquilino;
import com.sblm.model.Institucion;
import com.sblm.model.Representante;
import com.sblm.model.Upa;
import com.sblm.model.Usuario;
import com.sblm.service.IContratoService;
import com.sblm.util.Correo;
import com.sblm.util.FuncionesHelper;

@ManagedBean(name = "ccMB")
@ViewScoped
public class CContratoManagedBean implements Serializable {

	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{contratoService}")
	private transient IContratoService contratoService;
	private Usuario selectRegistroUsuario;
	private List<Integer> listaAnho;
	
	//Contrato
	private int idcontratoGlobal=0;
	
	//Objetos

	private Contrato contrato;
	private Upa selectUpa,upa;
	private Inmueble inmueble;
	private Inquilino inquilino;
	private Institucion institucion;
	private Representante representante;
	private Representante representanteaval;
	private Cliente cliente;
	private Cuentabancaria ctabancaria;
	
	//Lista
	private List<Inquilino> listInquilino,listInquilinoFiltro;
	private List<Inmueble> listInmueble, listInmuebleFiltro;
	private List<Upa> listUpa,listUpaFiltro;
	private List<Upa> upasInmueble;
	private List<Contrato> listContrato,listContratoFiltro;
	private List<Cuentabancaria> listCtasBancarias,listCtasBancariasFiltro;
	
	private List<Institucion> listInstitucion;
	private List<Representante> listRepresentante;
	private List<Cliente> listCliente;
	
	private boolean mostrarUpaSimpleMultiple=false;
	private boolean cambio=false;
	private boolean siResolucion;
	private boolean sidolar;
	private boolean sisoles;
	
	private List<Usuario> listadousuariosSeleccionados;
	private int indicesalvador;
	List<Usuario> todosUsuarios;
	
	private String contenidoMensajePersonalizado;
	private Double tipoCambio;
	private int nrocoutas;
	
	private int idUsuarioSeleccionado;
	//Id Seleccionados
	private int idupaselecionada;
	
	private String nombreOcupante;
	
	/****** cargado de archivos ****/
	Map<String, InputStream> carousel;
	private List<Archivodocumento> displayList;

	private List<Archivodocumento> displayListTmp;
	private List<Extensionarchivo> listaExtensionArchivos;
	private Archivodocumento archivoDelete;
	private StreamedContent filedownload;

	List<String> fileList;
	private static final String OUTPUT_ZIP_FILE = FuncionesHelper.directorioPrincipalLibreria
			+ "documentos.zip";
	private static final String SOURCE_FOLDER = FuncionesHelper.directorioPrincipalLibreria;
	
	
	@PostConstruct
	public void init(){
		initNewMB();
		initListDAO();
		contrato.setSisuscrito(false);
		contrato.setSiactaentrega(false);
		contrato.setSiocupante(true);
		sidolar=false;
		sisoles=true;
		
	}
	
	public void initListDAO(){
		listUpa= contratoService.getListaUpa();
		listInquilino=contratoService.getListaInquilino();
		listInmueble=contratoService.getListaInmueble();
		listContrato=contratoService.getListaContrato();
		listInstitucion=contratoService.getListaInstitucion();
		listRepresentante=contratoService.getListaRepresentante();
		listCliente=contratoService.getListaCliente();
		todosUsuarios=contratoService.obtenerUsuarios();
		tipoCambio=contratoService.getValorTipoCambio();
		listCtasBancarias= contratoService.getCtasBancarias();
	}
	
	public void initNewMB(){
		inquilino=  new Inquilino();
		contrato = new Contrato();
		inmueble = new Inmueble();
		listUpa= new ArrayList<Upa>();
		upa = new Upa();
		listUpa= new ArrayList<Upa>();
		listInquilino = new ArrayList<Inquilino>();
		listInmueble= new ArrayList<Inmueble>();
		upasInmueble= new ArrayList<Upa>();
		listContrato= new ArrayList<Contrato>();
		cliente = new Cliente();
		representanteaval= new Representante();
		todosUsuarios= new ArrayList<Usuario>();
		listadousuariosSeleccionados= new ArrayList<Usuario>();
		listCtasBancarias= new ArrayList<Cuentabancaria>();
	}
	
	public void seleccionarUpa(SelectEvent event) {
	if (!verificarSiAsociadoUpaContrato()) {
		upa=null;
		inmueble=null;
		upa=selectUpa;
		inmueble=selectUpa.getInmueble();
		mostrarUpaSimpleMultiple=false;
	}	
		
	}
	
	public void buscaUpasXInmueble() {
		mostrarUpaSimpleMultiple=true;
		upasInmueble=contratoService.buscarUpasXInmueble(inmueble.getIdinmueble());
	}
	
	public void setearContratoSeleccionado() {
		idcontratoGlobal=contrato.getIdcontrato();
		upa=contrato.getUpa();
		inmueble=contrato.getUpa().getInmueble();
		inquilino=contrato.getInquilino();
		representante=contrato.getRepresentante();
		institucion=contrato.getInstitucion();
		representanteaval=contrato.getRepresentanteaval();
		cliente=contrato.getCliente();
		if (contrato.getTipomoneda()!=null) {
			if(contrato.getTipomoneda().equals('S')){
				sisoles=true;
				sidolar=false;
			}else{
				contrato.setMontototalsoles(contrato.getMontototalsoles()/tipoCambio);
				sisoles=false;
				sidolar=true;
			}
		}
	}
	
	public void actualizarfechafincontrato(SelectEvent event){
		
		
	}
	
	public void calcularFecha() {
		Calendar c1 = GregorianCalendar.getInstance();
		c1.setTime(contrato.getIniciocontrato());
		c1.add(Calendar.MONTH, nrocoutas);
		
		contrato.setFincontrato(c1.getTime());
	}
	
	public void buscarUpaXid() {
		for (Upa object : listUpa) {
			if(object.getIdupa()==getIdupaselecionada()){
				upa=object;
				inmueble=upa.getInmueble();
				}
		}
	}
	
	 public Boolean verificarSiAsociadoUpaContrato() {
		 
		 for (Contrato contrato: listContrato) {
			if (contrato.getEstado().equals("VIGENTE")) {
				
				if (selectUpa.getIdupa()==contrato.getUpa().getIdupa()) {
					FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN,"Sin Éxito","Upa ya se encuentra relacionado con el contrato:"+contrato.getNrocontrato()); 
					FacesContext.getCurrentInstance().addMessage(null, msg);
					return true;
				}
			} 
			 
		}
		 			return false;
	 }
	 
	 public void verificarExistenciaCodigoContrato(){
		 for (Contrato contratoElementoLista : listContrato) {
			 if (contrato.getNrocontrato().equals(contratoElementoLista.getNrocontrato())) {
				 FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN,"Sin Éxito","Ya existe un contrato con el código ingresado: "+contrato.getNrocontrato()); 
				 FacesContext.getCurrentInstance().addMessage(null, msg);
			}	
		 }
	 }
	
	public void grabarCabeceraContrato(){
		if (idcontratoGlobal==0) {
			contrato.setUpa(upa);
			contrato.setInquilino(inquilino);
			contrato.setEstado("VIGENTE");
			contrato.setFechacreacion(new Date());
			contrato.setNumerocuotas(nrocoutas);
			
			
			if (sisoles) {
			contrato.setTipomoneda('S');
			contrato.setMontototaldolar(contrato.getMontototalsoles()/tipoCambio);
			
			contrato.setMontocuotasoles(contrato.getMontototalsoles()/nrocoutas);
			contrato.setMontototaldolar(contrato.getMontototaldolar()/nrocoutas);
			} else {
			contrato.setTipomoneda('D');
			contrato.setMontototaldolar(contrato.getMontototalsoles());
			contrato.setMontototalsoles(contrato.getMontototaldolar()*tipoCambio);
			
			contrato.setMontocuotadolar(contrato.getMontocuotadolar()/nrocoutas);
			contrato.setMontocuotasoles(contrato.getMontocuotadolar()/nrocoutas);
			}
			
			contratoService.registrarContrato(contrato);
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Éxito","Nuevo contrato:"+contrato.getNrocontrato()+" creado con éxito"); 
			 FacesContext.getCurrentInstance().addMessage(null, msg);//grabado
			limpirRegistroPropiedades();
			contrato = new Contrato();
			nrocoutas=0;
			listContrato=contratoService.getListaContrato();
			
			
			
		} else {
			contrato.setUpa(upa);
			contrato.setInquilino(inquilino);
			contrato.setFechamodificacion(new Date());
			contrato.setNumerocuotas(nrocoutas);
			if (sisoles) {
				contrato.setMontototaldolar(contrato.getMontocuotasoles()/tipoCambio);
				contrato.setTipomoneda('S');
			} else {
				contrato.setTipomoneda('D');
			contrato.setMontocuotadolar(contrato.getMontocuotasoles());
			contrato.setMontocuotasoles(contrato.getMontocuotadolar()*tipoCambio);
			}
			
			contratoService.registrarContrato(contrato);			//actualizado
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Éxito","Se actualizó contrato: "+contrato.getNrocontrato() +" con éxito") ; 
			FacesContext.getCurrentInstance().addMessage(null, msg);
			limpirRegistroPropiedades();
			contrato = new Contrato();
			nrocoutas=0;
			listContrato=contratoService.getListaContrato();
			
			
		}
	}
	
	public void actualizarInstitucionContrato(){
		if (idcontratoGlobal!=0) {
			contrato.setRepresentante(representante);
			contrato.setInstitucion(institucion);
			contratoService.registrarContrato(contrato);			//actualizado
			limpirRegistroPropiedades();
		} 
	}
	
	public void actualizarAvalContrato(){
		if (idcontratoGlobal!=0) {
			contrato.setCliente(cliente);
			contrato.setRepresentanteaval(representanteaval);
			contratoService.registrarContrato(contrato);			//actualizado
			limpirRegistroPropiedades();
		} 
	}
	
	public void actualizarGarantiasContrato(){
		if (idcontratoGlobal!=0) {
			contrato.setCuentabancaria(ctabancaria);
			contratoService.registrarContrato(contrato);			//actualizado
			limpirRegistroPropiedades();
		} 
	}
	
	public void actualizarPeriodoContrato(){
		if (idcontratoGlobal!=0) {
			contrato.setCuentabancaria(ctabancaria);
			contratoService.registrarContrato(contrato);			//actualizado
			limpirRegistroPropiedades();
		} 
	}
	
	

	public String obtenerCorreoXid(int id){
		String correo = "";
		
		for(int i=0;i<todosUsuarios.size();i++){
			if(todosUsuarios.get(i).getIdusuario()==id){
			correo=	todosUsuarios.get(i).getEmailusr();
			}
		}
		return correo;
	}
	
	 
	public String obtenerNombresXid(int id){
			String nomApe = "";
			
			for(int i=0;i<todosUsuarios.size();i++){
				if(todosUsuarios.get(i).getIdusuario()==id){
				nomApe=	todosUsuarios.get(i).getNombrescompletos();
				}
			}
			return nomApe;
		}
	
	public void enviarNotificaciónPersonalizable(int idusuariodestino){
		
	contratoService.enviarNotificaciónPersonalizable(getContenidoMensajePersonalizado(),idusuariodestino);
	
	Correo correo =new Correo();
	String msj="Estimado Sr(a). <b> "+obtenerNombresXid(idusuariodestino)+"</b> <br /><br />"+
			"Ha recibido el siguiente mensaje :"+"<br /><br />"+
			getContenidoMensajePersonalizado()+"<br /><br /><br /><br />"+
			"Atte <br />"+obtenerNombresXid(Integer.parseInt(FuncionesHelper.getUsuario().toString()))+
			"<br />"+
			"Oficina de Informática <br />"+
			"<b>Sociedad de Beneficencia de Lima</b>";
			correo.enviarCorreo(obtenerCorreoXid(idusuariodestino),"Mensaje",msj);
		
	}
	
	
	public void cancelarContrato(){
		
		if (idcontratoGlobal!=0) {
			contratoService.cancelarContrato(idcontratoGlobal);
			 FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN,"Éxito","Se canceló contrato: "+contrato.getNrocontrato()+" éxitosamente"); 
			 FacesContext.getCurrentInstance().addMessage(null, msg);
			 limpirRegistroPropiedades();
			 listContrato=contratoService.getListaContrato();
			 
			 for (int i = 0; i < listadousuariosSeleccionados.size(); i++) {
				 enviarNotificaciónPersonalizable(listadousuariosSeleccionados.get(i).getIdusuario());
			 }
		} 
	}
	
	
	public void limpirRegistroPropiedades(){
		contrato=null;
		upa=null;
		inmueble=null;
		institucion=null;
		representante=null;
		inquilino=new Inquilino();
		cliente=null;
		representanteaval=null;
		ctabancaria=null;
		
	}
	
	public void ActivarDesactivarSiOcupante() {
		if (!contrato.getSiocupante()) {
			contrato.setSiocupante(false);
		} else {
			contrato.setSiocupante(true);
		}
	}
	
	public void ActivarDesactivarSifechaSuscrito() {
		if (contrato.getSisuscrito()) {
			contrato.setSisuscrito(true);
		} else {
			contrato.setSisuscrito(false);
		}
	}
	
	public void ActivarDesactivarSiactaEntrega() {
		if (contrato.getSiactaentrega()) {
			contrato.setSiactaentrega(true);
		} else {
			contrato.setSiactaentrega(false);
		}
	}
	
	public void cambiarSolesDolar(){
		if (sisoles) {
			sidolar=true;
			sisoles=false;
		} else {
			sisoles=true;
			sidolar=false;
		}
	}
	
	public void cambiarDolarSoles(){
		if (sidolar) {
			sisoles=true;
			sidolar=false;
		} else {
			sidolar=false;
		}
	}
	
	public void eliminarUsuarioDeLista(ActionEvent event) {
		
		for(int i=0;i<listadousuariosSeleccionados.size();i++){
			
			if(listadousuariosSeleccionados.get(i)==getSelectRegistroUsuario()){
				listadousuariosSeleccionados.remove(i);
			}
		}

		}
	
	public List<String> autoCompleteUsuario(String query){
		
		List<String> result = new ArrayList<String>();
		
		for(Usuario usu : (List<Usuario>)todosUsuarios){
			
			String nomComplusu=usu.getNombrescompletos();
			
			if(nomComplusu.toLowerCase().contains(query.toLowerCase())){
				result.add(nomComplusu);
			}
		}
		
		return result;
	}
	

	
	public void agregarUsuarioLista(){
		boolean usuarioVacio = false;

		if (!usuarioVacio) {
		}
			Usuario Usu = new Usuario();
			Usu.setEmailusr("Escriba Nombre Usuario");
			Usu.setRutaimgusr("default.jpg");
			Usu.setContrasenausr("deleteUsuario.png");
			listadousuariosSeleccionados.add(Usu);
			setIndicesalvador(listadousuariosSeleccionados.size()-1);
			
		
	 }
	
	
 
	
	public void onCellEdit(CellEditEvent event) { 
		
		
		if(Integer.parseInt(event.getColumn().getWidth())==99){
	        Object newValue = event.getNewValue();
	        
	        
	        FacesContext contextFaces = FacesContext.getCurrentInstance();  

	       
	       //buscamos id usuario
	       int id = 0;
	       for(int k=0;k<todosUsuarios.size();k++){	
				if((todosUsuarios.get(k).getNombrescompletos()).equals(newValue)){
					id=k;
				}

			}

			boolean flag = true;
		

			for (int i = 0; i < listadousuariosSeleccionados.size() - 1; i++) {
				System.out.println("i=" + i);
				if ((listadousuariosSeleccionados.get(i).getEmailusr())
						.equals(newValue)) { 
					
					contextFaces.addMessage(null, new FacesMessage(
							FacesMessage.SEVERITY_WARN, "Advertencia",
							"usuario ya se encuentra en la lista"));
					listadousuariosSeleccionados.remove(getIndicesalvador());
					flag = false;

				}
			}

			if (flag) {
				System.out.println("nuevo");
				listadousuariosSeleccionados.get(getIndicesalvador()).setRutaimgusr(todosUsuarios.get(id).getRutaimgusr());
				listadousuariosSeleccionados.get(getIndicesalvador()).setCargo(todosUsuarios.get(id).getCargo());
				listadousuariosSeleccionados.get(getIndicesalvador()).setIdusuario(todosUsuarios.get(id).getIdusuario());
				listadousuariosSeleccionados.get(getIndicesalvador()).setUsrmod("Ingrese Perfil");
				
				Area A= new Area();
				A.setDesare(todosUsuarios.get(id).getArea().getDesare());
				listadousuariosSeleccionados.get(getIndicesalvador()).setArea(A);
				setIdUsuarioSeleccionado(todosUsuarios.get(id).getIdusuario());
				
			}
			
		}else{
	        Object newValue = event.getNewValue();
			
			listadousuariosSeleccionados.get(getIndicesalvador()).setUsrmod(newValue.toString());
			
		}
    }
	
	public void validarCancelacionContrato() {
		RequestContext contextRequest = RequestContext.getCurrentInstance(); 
		FacesContext contextFaces = FacesContext.getCurrentInstance(); 
		
		if (idcontratoGlobal!=0) {
			contextRequest.execute("dlgCancelarContrato.show();");
		} else {
			contextFaces.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Advertencia", "Seleccione el contrato a cancelar"));	
		}
		
	}
	
	public IContratoService getContratoService() {
		return contratoService;
	}

	public void setContratoService(IContratoService contratoService) {
		this.contratoService = contratoService;
	}

	public Upa getSelectUpa() {
		return selectUpa;
	}

	public void setSelectUpa(Upa selectUpa) {
		this.selectUpa = selectUpa;
	}

	public List<Upa> getListUpa() {
		return listUpa;
	}

	public void setListUpa(List<Upa> listUpa) {
		this.listUpa = listUpa;
	}

	public List<Upa> getListUpaFiltro() {
		return listUpaFiltro;
	}

	public void setListUpaFiltro(List<Upa> listUpaFiltro) {
		this.listUpaFiltro = listUpaFiltro;
	}

	public Contrato getContrato() {
		return contrato;
	}

	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
	}

	public Upa getUpa() {
		return upa;
	}

	public void setUpa(Upa upa) {
		this.upa = upa;
	}

	public Inmueble getInmueble() {
		return inmueble;
	}

	public void setInmueble(Inmueble inmueble) {
		this.inmueble = inmueble;
	}

	public List<Inquilino> getListInquilino() {
		return listInquilino;
	}
	public void setListInquilino(List<Inquilino> listInquilino) {
		this.listInquilino = listInquilino;
	}
	public List<Inmueble> getListInmueble() {
		return listInmueble;
	}
	public void setListInmueble(List<Inmueble> listInmueble) {
		this.listInmueble = listInmueble;
	}

	public List<Inmueble> getListInmuebleFiltro() {
		return listInmuebleFiltro;
	}

	public void setListInmuebleFiltro(List<Inmueble> listInmuebleFiltro) {
		this.listInmuebleFiltro = listInmuebleFiltro;
	}

	public Inquilino getInquilino() {
		return inquilino;
	}
	public void setInquilino(Inquilino inquilino) {
		this.inquilino = inquilino;
	}
	public List<Inquilino> getListInquilinoFiltro() {
		return listInquilinoFiltro;
	}
	public void setListInquilinoFiltro(List<Inquilino> listInquilinoFiltro) {
		this.listInquilinoFiltro = listInquilinoFiltro;
	}
	public List<Upa> getUpasInmueble() {
		return upasInmueble;
	}
	public void setUpasInmueble(List<Upa> upasInmueble) {
		this.upasInmueble = upasInmueble;
	}
	public boolean isMostrarUpaSimpleMultiple() {
		return mostrarUpaSimpleMultiple;
	}
	public void setMostrarUpaSimpleMultiple(boolean mostrarUpaSimpleMultiple) {
		this.mostrarUpaSimpleMultiple = mostrarUpaSimpleMultiple;
	}
	public int getIdupaselecionada() {
		return idupaselecionada;
	}
	public void setIdupaselecionada(int idupaselecionada) {
		this.idupaselecionada = idupaselecionada;
	}
	public boolean isSiResolucion() {
		return siResolucion;
	}
	public void setSiResolucion(boolean siResolucion) {
		this.siResolucion = siResolucion;
	}
	public String getNombreOcupante() {
		return nombreOcupante;
	}
	public void setNombreOcupante(String nombreOcupante) {
		this.nombreOcupante = nombreOcupante;
	}
	public List<Contrato> getListContrato() {
		return listContrato;
	}
	public void setListContrato(List<Contrato> listContrato) {
		this.listContrato = listContrato;
	}
	public List<Contrato> getListContratoFiltro() {
		return listContratoFiltro;
	}
	public void setListContratoFiltro(List<Contrato> listContratoFiltro) {
		this.listContratoFiltro = listContratoFiltro;
	}
	public List<Institucion> getListInstitucion() {
		return listInstitucion;
	}
	public void setListInstitucion(List<Institucion> listInstitucion) {
		this.listInstitucion = listInstitucion;
	}
	public Institucion getInstitucion() {
		return institucion;
	}
	public void setInstitucion(Institucion institucion) {
		this.institucion = institucion;
	}
	public Representante getRepresentante() {
		return representante;
	}
	public void setRepresentante(Representante representante) {
		this.representante = representante;
	}
	public List<Representante> getListRepresentante() {
		return listRepresentante;
	}
	public void setListRepresentante(List<Representante> listRepresentante) {
		this.listRepresentante = listRepresentante;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public List<Cliente> getListCliente() {
		return listCliente;
	}
	public void setListCliente(List<Cliente> listCliente) {
		this.listCliente = listCliente;
	}

	public Representante getRepresentanteaval() {
		return representanteaval;
	}

	public void setRepresentanteaval(Representante representanteaval) {
		this.representanteaval = representanteaval;
	}

	public Usuario getSelectRegistroUsuario() {
		return selectRegistroUsuario;
	}

	public void setSelectRegistroUsuario(Usuario selectRegistroUsuario) {
		this.selectRegistroUsuario = selectRegistroUsuario;
	}

	public List<Usuario> getListadousuariosSeleccionados() {
		return listadousuariosSeleccionados;
	}
	public void setListadousuariosSeleccionados(
			List<Usuario> listadousuariosSeleccionados) {
		this.listadousuariosSeleccionados = listadousuariosSeleccionados;
	}
	public int getIndicesalvador() {
		return indicesalvador;
	}
	public void setIndicesalvador(int indicesalvador) {
		this.indicesalvador = indicesalvador;
	}
	public int getIdUsuarioSeleccionado() {
		return idUsuarioSeleccionado;
	}
	public void setIdUsuarioSeleccionado(int idUsuarioSeleccionado) {
		this.idUsuarioSeleccionado = idUsuarioSeleccionado;
	}
	public String getContenidoMensajePersonalizado() {
		return contenidoMensajePersonalizado;
	}
	public void setContenidoMensajePersonalizado(
			String contenidoMensajePersonalizado) {
		this.contenidoMensajePersonalizado = contenidoMensajePersonalizado;
	}
	public boolean isSidolar() {
		return sidolar;
	}
	public void setSidolar(boolean sidolar) {
		this.sidolar = sidolar;
	}
	public boolean isSisoles() {
		return sisoles;
	}
	public void setSisoles(boolean sisoles) {
		this.sisoles = sisoles;
	}

	public int getNrocoutas() {
		return nrocoutas;
	}

	public void setNrocoutas(int nrocoutas) {
		this.nrocoutas = nrocoutas;
	}

	public List<Cuentabancaria> getListCtasBancarias() {
		return listCtasBancarias;
	}

	public void setListCtasBancarias(List<Cuentabancaria> listCtasBancarias) {
		this.listCtasBancarias = listCtasBancarias;
	}

	public Cuentabancaria getCtabancaria() {
		return ctabancaria;
	}

	public void setCtabancaria(Cuentabancaria ctabancaria) {
		this.ctabancaria = ctabancaria;
	}
	public List<Integer> getListaAnho() {
		
		listaAnho = new ArrayList<Integer>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
		int anhoActual=Integer.parseInt(dateFormat.format( new Date()));
		
		for(int i=anhoActual;i>=1950;i--){
			//for(int i=1950;i<=anhoActual;i++){
			listaAnho.add(i);
		}
		
		return listaAnho;
	}
	
	public void setListaAnho(List<Integer> listaAnho) {
		this.listaAnho = listaAnho;
	}
	
	
	
}

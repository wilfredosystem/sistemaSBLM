package com.sblm.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;

import com.sblm.model.Contrato;
import com.sblm.model.Inmueble;
import com.sblm.model.Inquilino;
import com.sblm.model.Tipoentidad;
import com.sblm.model.Upa;
import com.sblm.model.Uso;
import com.sblm.service.IContratoService;
import com.sblm.service.IInquilinoService;

@ManagedBean(name = "contratoMB")
@ViewScoped
public class ContratoManagedBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{contratoService}")
	private transient IContratoService contratoService;
	
	//Objetos
	private Contrato contrato;
	private Upa selectUpa,upa;
	private Inmueble inmueble;
	
	//Lista
	private List<Uso> listUso;
	private List<Inquilino> listInquilino;
	private List<Inmueble> listInmuebles;
	private List<Upa> listUpa,listUpaFiltro;
	
	
//	private Contrato contratocapturado;
//	boolean actualizado = false;
//	private Contrato resultadocontrato;
//	private int numcontratos;
	private List<Integer> listaAnho;
	
	
	@PostConstruct
	public void init(){
		
		contrato = new Contrato();
		inmueble = new Inmueble();
		listUso= new ArrayList<Uso>();
		listUpa= new ArrayList<Upa>();
		inicializandoValoresEstaticos();
		upa = new Upa();
	}
	
	public void inicializandoValoresEstaticos(){
		listUso = contratoService.getListaUsos();
		listUpa= contratoService.getListaUpa(); System.out.println("Tamanio lista de Upas: "+listUpa.size());
		listInmuebles=contratoService.getListaInmueble();
	//	listI
	}
	
	
	public void seleccionarUpa(SelectEvent event) {
		upa=selectUpa;
		inmueble=selectUpa.getInmueble();
		
		
		
		
		
		System.out.println(selectUpa.getInmueble().getAreaconstruida());
	
		
		
//		inmueble= new Inmueble();
//		inmueble=selectUpa.getInmueble();
//		
//		if (inmueble.getAreaconstruida()!=null) {
//			System.out.println("Antes de parsear:"+inmueble.getAreaconstruida());
//		} else {
//			System.out.println("Es nulo");
//		}
//		
//		
//		
		
		
		if (selectUpa.getInmueble().getAreaterreno()==null) {
			selectUpa.getInmueble().setAreaterreno(0.0);
			
		}
		if (selectUpa.getInmueble().getAreaconstruida()==null) {
			selectUpa.getInmueble().setAreaconstruida(0.0);			
		}
		
		System.out.println("Despues de parsear :"+ selectUpa.getInmueble().getAreaconstruida());
		
		
		//upa.setInmueble(inmueble);
		
		
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
	
	public IContratoService getContratoService() {
		return contratoService;
	}
	
	public void setContratoService(IContratoService contratoService) {
		this.contratoService = contratoService;
	}
	
	public Contrato getContrato() {
		return contrato;
	}
	
	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
	}


	public Inmueble getInmueble() {
		return inmueble;
	}

	public void setInmueble(Inmueble inmueble) {
		this.inmueble = inmueble;
	}

	public List<Uso> getListUso() {
		return listUso;
	}

	public void setListUso(List<Uso> listUso) {
		this.listUso = listUso;
	}

	public List<Inquilino> getListInquilino() {
		return listInquilino;
	}

	public void setListInquilino(List<Inquilino> listInquilino) {
		this.listInquilino = listInquilino;
	}

	public List<Inmueble> getListInmuebles() {
		return listInmuebles;
	}

	public void setListInmuebles(List<Inmueble> listInmuebles) {
		this.listInmuebles = listInmuebles;
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

	public Upa getSelectUpa() {
		return selectUpa;
	}

	public void setSelectUpa(Upa selectUpa) {
		this.selectUpa = selectUpa;
	}

	public Upa getUpa() {
		return upa;
	}

	public void setUpa(Upa upa) {
		this.upa = upa;
	}

	
}

package com.sblm.model;

// Generated 30-oct-2013 12:22:39 by Hibernate Tools 4.0.0

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Cuota generated by hbm2java
 */
@Entity
@Table(name = "CUOTA", schema = "dbo", catalog = "beneficenciadba")
public class Cuota implements java.io.Serializable {

	private int idcuota;
	
	private Contrato contrato;
	private Double montoacumuladosoles;
	private Double montoacumuladodolar;
	private Double mora;
	private Date fechaliquidacion;
	private Boolean regularizacion;
	private Boolean acho;
	private Boolean cancelado;
	private Double garantia;
	private Integer cuotanumero;
	private String observacion;
	private String usrcre;
	private String usrmod;
	private Date feccre;
	private Date fecmod;
	private Double montodolar;
	private Double montosoles;
	private List<Detallecuota> detallecuotas = new ArrayList<Detallecuota>(0);

	public Cuota() {
	}

	public Cuota(int idcuota) {
		this.idcuota = idcuota;
	}

	public Cuota(int idcuota, 
			Contrato contrato, Double montoacumuladosoles,
			Double montoacumuladodolar, Double mora, Date fechaliquidacion,
			Boolean regularizacion, Boolean acho, Boolean cancelado, Double garantia,Integer cuotanumero,
			String observacion, String usrcre, String usrmod, Date feccre,
			Date fecmod, Double montodolar, Double montosoles,
			List<Detallecuota> detallecuotas) {
		this.idcuota = idcuota;
		this.contrato = contrato;
		this.montoacumuladosoles = montoacumuladosoles;
		this.montoacumuladodolar = montoacumuladodolar;
		this.mora = mora;
		this.fechaliquidacion = fechaliquidacion;
		this.regularizacion = regularizacion;
		this.acho = acho;
		this.cancelado = cancelado;
		this.garantia = garantia;
		this.cuotanumero = cuotanumero;
		this.observacion = observacion;
		this.usrcre = usrcre;
		this.usrmod = usrmod;
		this.feccre = feccre;
		this.fecmod = fecmod;
		this.montodolar = montodolar;
		this.montosoles = montosoles;
		this.detallecuotas = detallecuotas;
	}

	@Id @GeneratedValue
	@Column(name = "IDCUOTA", unique = true, nullable = false)
	public int getIdcuota() {
		return this.idcuota;
	}

	public void setIdcuota(int idcuota) {
		this.idcuota = idcuota;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IDCONTRATO")
	public Contrato getContrato() {
		if(contrato==null){
			contrato=new Contrato();
			}
		return this.contrato;
	}

	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
	}

	@Column(name = "MONTOACUMULADOSOLES", precision = 53, scale = 0)
	public Double getMontoacumuladosoles() {
		return this.montoacumuladosoles;
	}

	public void setMontoacumuladosoles(Double montoacumuladosoles) {
		this.montoacumuladosoles = montoacumuladosoles;
	}

	@Column(name = "MONTOACUMULADODOLAR", precision = 53, scale = 0)
	public Double getMontoacumuladodolar() {
		return this.montoacumuladodolar;
	}

	public void setMontoacumuladodolar(Double montoacumuladodolar) {
		this.montoacumuladodolar = montoacumuladodolar;
	}

	@Column(name = "MORA", precision = 53, scale = 0)
	public Double getMora() {
		return this.mora;
	}

	public void setMora(Double mora) {
		this.mora = mora;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHALIQUIDACION", length = 23)
	public Date getFechaliquidacion() {
		return this.fechaliquidacion;
	}

	public void setFechaliquidacion(Date fechaliquidacion) {
		this.fechaliquidacion = fechaliquidacion;
	}

	@Column(name = "REGULARIZACION")
	public Boolean getRegularizacion() {
		return this.regularizacion;
	}

	public void setRegularizacion(Boolean regularizacion) {
		this.regularizacion = regularizacion;
	}

	@Column(name = "ACHO")
	public Boolean getAcho() {
		return this.acho;
	}

	public void setAcho(Boolean acho) {
		this.acho = acho;
	}
	@Column(name = "CANCELADO")
	public Boolean getCancelado() {
		return this.cancelado;
	}

	public void setCancelado(Boolean cancelado) {
		this.cancelado = cancelado;
	}
	@Column(name = "GARANTIA", precision = 53, scale = 0)
	public Double getGarantia() {
		return this.garantia;
	}

	public void setGarantia(Double garantia) {
		this.garantia = garantia;
	}
	@Column(name = "CUOTANUMERO")
	public Integer getCuotanumero() {
		return this.cuotanumero;
	}

	public void setCuotanumero(Integer cuotanumero) {
		this.cuotanumero = cuotanumero;
	}
	
	@Column(name = "OBSERVACION", length = 200)
	public String getObservacion() {
		return this.observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	@Column(name = "USRCRE", length = 50)
	public String getUsrcre() {
		return this.usrcre;
	}

	public void setUsrcre(String usrcre) {
		this.usrcre = usrcre;
	}

	@Column(name = "USRMOD", length = 50)
	public String getUsrmod() {
		return this.usrmod;
	}

	public void setUsrmod(String usrmod) {
		this.usrmod = usrmod;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECCRE", length = 23)
	public Date getFeccre() {
		return this.feccre;
	}

	public void setFeccre(Date feccre) {
		this.feccre = feccre;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECMOD", length = 23)
	public Date getFecmod() {
		return this.fecmod;
	}

	public void setFecmod(Date fecmod) {
		this.fecmod = fecmod;
	}

	@Column(name = "MONTODOLAR", precision = 53, scale = 0)
	public Double getMontodolar() {
		return this.montodolar;
	}

	public void setMontodolar(Double montodolar) {
		this.montodolar = montodolar;
	}

	@Column(name = "MONTOSOLES", precision = 53, scale = 0)
	public Double getMontosoles() {
		return this.montosoles;
	}

	public void setMontosoles(Double montosoles) {
		this.montosoles = montosoles;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cuota")
	public List<Detallecuota> getDetallecuotas() {
		return this.detallecuotas;
	}

	public void setDetallecuotas(List<Detallecuota> detallecuotas) {
		this.detallecuotas = detallecuotas;
	}

}

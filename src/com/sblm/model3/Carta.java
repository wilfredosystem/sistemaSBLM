package com.sblm.model3;

// Generated 25/11/2013 12:19:12 PM by Hibernate Tools 4.0.0

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Carta generated by hbm2java
 */
@Entity
@Table(name = "CARTA", schema = "dbo", catalog = "beneficenciadba")
public class Carta implements java.io.Serializable {

	private int idcarta;
	private Inquilino inquilino;
	private Contrato contrato;
	private String jravcalle;
	private String nro;
	private String intdtostand;
	private Integer idusuariocreador;
	private Date fechacreacion;
	private Integer nroarchivos;
	private String clave;
	private String departamento;
	private String provincia;
	private String distrito;
	private Set<Archivodocumento> archivodocumentos = new HashSet<Archivodocumento>(
			0);

	public Carta() {
	}

	public Carta(int idcarta) {
		this.idcarta = idcarta;
	}

	public Carta(int idcarta, Inquilino inquilino, Contrato contrato,
			String jravcalle, String nro, String intdtostand,
			Integer idusuariocreador, Date fechacreacion, Integer nroarchivos,
			String clave, String departamento, String provincia,
			String distrito, Set<Archivodocumento> archivodocumentos) {
		this.idcarta = idcarta;
		this.inquilino = inquilino;
		this.contrato = contrato;
		this.jravcalle = jravcalle;
		this.nro = nro;
		this.intdtostand = intdtostand;
		this.idusuariocreador = idusuariocreador;
		this.fechacreacion = fechacreacion;
		this.nroarchivos = nroarchivos;
		this.clave = clave;
		this.departamento = departamento;
		this.provincia = provincia;
		this.distrito = distrito;
		this.archivodocumentos = archivodocumentos;
	}

	@Id
	@Column(name = "IDCARTA", unique = true, nullable = false)
	public int getIdcarta() {
		return this.idcarta;
	}

	public void setIdcarta(int idcarta) {
		this.idcarta = idcarta;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IDINQUILINO")
	public Inquilino getInquilino() {
		return this.inquilino;
	}

	public void setInquilino(Inquilino inquilino) {
		this.inquilino = inquilino;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IDCONTRATO")
	public Contrato getContrato() {
		return this.contrato;
	}

	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
	}

	@Column(name = "JRAVCALLE", length = 50)
	public String getJravcalle() {
		return this.jravcalle;
	}

	public void setJravcalle(String jravcalle) {
		this.jravcalle = jravcalle;
	}

	@Column(name = "NRO", length = 10)
	public String getNro() {
		return this.nro;
	}

	public void setNro(String nro) {
		this.nro = nro;
	}

	@Column(name = "INTDTOSTAND", length = 50)
	public String getIntdtostand() {
		return this.intdtostand;
	}

	public void setIntdtostand(String intdtostand) {
		this.intdtostand = intdtostand;
	}

	@Column(name = "IDUSUARIOCREADOR")
	public Integer getIdusuariocreador() {
		return this.idusuariocreador;
	}

	public void setIdusuariocreador(Integer idusuariocreador) {
		this.idusuariocreador = idusuariocreador;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHACREACION", length = 23)
	public Date getFechacreacion() {
		return this.fechacreacion;
	}

	public void setFechacreacion(Date fechacreacion) {
		this.fechacreacion = fechacreacion;
	}

	@Column(name = "NROARCHIVOS")
	public Integer getNroarchivos() {
		return this.nroarchivos;
	}

	public void setNroarchivos(Integer nroarchivos) {
		this.nroarchivos = nroarchivos;
	}

	@Column(name = "CLAVE", length = 50)
	public String getClave() {
		return this.clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	@Column(name = "DEPARTAMENTO", length = 50)
	public String getDepartamento() {
		return this.departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	@Column(name = "PROVINCIA", length = 50)
	public String getProvincia() {
		return this.provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	@Column(name = "DISTRITO", length = 50)
	public String getDistrito() {
		return this.distrito;
	}

	public void setDistrito(String distrito) {
		this.distrito = distrito;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "carta")
	public Set<Archivodocumento> getArchivodocumentos() {
		return this.archivodocumentos;
	}

	public void setArchivodocumentos(Set<Archivodocumento> archivodocumentos) {
		this.archivodocumentos = archivodocumentos;
	}

}

package com.sblm.model;

// Generated 14-oct-2013 14:58:03 by Hibernate Tools 4.0.0

import java.util.Date;
import java.util.HashSet;
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
 * Flujodocumento generated by hbm2java
 */
@Entity
@Table(name = "FLUJODOCUMENTO", schema = "dbo", catalog = "beneficenciadba")
public class Flujodocumento implements java.io.Serializable {

	private int idflujodocumento;
	private Documento documento;
	private Usuario usuario;
	private Integer idarea;
	private Integer numero;
	private Integer idusuarioremitente;
	private Date fechamodificacion;
	private Date fechaenvio;
	private Date fecharecepcion;
	private String respuesta;
	private Boolean estadoenvio;
	private Boolean estadorecepcion;
	private String estado;
	private String comentario;
	private String estadosupervisor;
	private Date fechaderivacionsupervisor;
	private Date fechaaprobacionrechazo;
	private Integer idperfilusuario;
	private Set<Seguimientoflujo> seguimientoflujos = new HashSet<Seguimientoflujo>(
			0);

	public Flujodocumento() {
	}

	public Flujodocumento(int idflujodocumento) {
		this.idflujodocumento = idflujodocumento;
	}

	public Flujodocumento(int idflujodocumento,
			Documento documento, Usuario usuario, Integer idarea,
			Integer numero, Integer idusuarioremitente, Date fechamodificacion,
			Date fechaenvio, Date fecharecepcion, String respuesta,
			Boolean estadoenvio, Boolean estadorecepcion, String estado,
			String comentario, String estadosupervisor,
			Date fechaderivacionsupervisor, Date fechaaprobacionrechazo,
			Set<Seguimientoflujo> seguimientoflujos,Integer idperfilusuario) {
		this.idflujodocumento = idflujodocumento;
		this.documento = documento;
		this.usuario = usuario;
		this.idarea = idarea;
		this.numero = numero;
		this.idusuarioremitente = idusuarioremitente;
		this.fechamodificacion = fechamodificacion;
		this.fechaenvio = fechaenvio;
		this.fecharecepcion = fecharecepcion;
		this.respuesta = respuesta;
		this.estadoenvio = estadoenvio;
		this.estadorecepcion = estadorecepcion;
		this.estado = estado;
		this.comentario = comentario;
		this.estadosupervisor = estadosupervisor;
		this.fechaderivacionsupervisor = fechaderivacionsupervisor;
		this.fechaaprobacionrechazo = fechaaprobacionrechazo;
		this.idperfilusuario=idperfilusuario;
		this.seguimientoflujos = seguimientoflujos;
	}

	@Id @GeneratedValue
	@Column(name = "IDFLUJODOCUMENTO", unique = true, nullable = false)
	public int getIdflujodocumento() {
		return this.idflujodocumento;
	}

	public void setIdflujodocumento(int idflujodocumento) {
		this.idflujodocumento = idflujodocumento;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IDDOCUMENTO")
	public Documento getDocumento() {
		if(documento==null){
			documento=new Documento();
			}
		return this.documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IDUSUARIODESTINO")
	public Usuario getUsuario() {
		if(usuario==null){
			usuario=new Usuario();
			}
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Column(name = "IDAREA")
	public Integer getIdarea() {
		return this.idarea;
	}

	public void setIdarea(Integer idarea) {
		this.idarea = idarea;
	}

	@Column(name = "NUMERO")
	public Integer getNumero() {
		return this.numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	@Column(name = "IDUSUARIOREMITENTE")
	public Integer getIdusuarioremitente() {
		return this.idusuarioremitente;
	}

	public void setIdusuarioremitente(Integer idusuarioremitente) {
		this.idusuarioremitente = idusuarioremitente;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHAMODIFICACION", length = 23)
	public Date getFechamodificacion() {
		return this.fechamodificacion;
	}

	public void setFechamodificacion(Date fechamodificacion) {
		this.fechamodificacion = fechamodificacion;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHAENVIO", length = 23)
	public Date getFechaenvio() {
		return this.fechaenvio;
	}

	public void setFechaenvio(Date fechaenvio) {
		this.fechaenvio = fechaenvio;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHARECEPCION", length = 23)
	public Date getFecharecepcion() {
		return this.fecharecepcion;
	}

	public void setFecharecepcion(Date fecharecepcion) {
		this.fecharecepcion = fecharecepcion;
	}

	@Column(name = "RESPUESTA", length = 15)
	public String getRespuesta() {
		return this.respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	@Column(name = "ESTADOENVIO")
	public Boolean getEstadoenvio() {
		return this.estadoenvio;
	}

	public void setEstadoenvio(Boolean estadoenvio) {
		this.estadoenvio = estadoenvio;
	}

	@Column(name = "ESTADORECEPCION")
	public Boolean getEstadorecepcion() {
		return this.estadorecepcion;
	}

	public void setEstadorecepcion(Boolean estadorecepcion) {
		this.estadorecepcion = estadorecepcion;
	}

	@Column(name = "ESTADO", length = 10)
	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	@Column(name = "COMENTARIO", length = 500)
	public String getComentario() {
		return this.comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	@Column(name = "ESTADOSUPERVISOR", length = 10)
	public String getEstadosupervisor() {
		return this.estadosupervisor;
	}

	public void setEstadosupervisor(String estadosupervisor) {
		this.estadosupervisor = estadosupervisor;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHADERIVACIONSUPERVISOR", length = 23)
	public Date getFechaderivacionsupervisor() {
		return this.fechaderivacionsupervisor;
	}

	public void setFechaderivacionsupervisor(Date fechaderivacionsupervisor) {
		this.fechaderivacionsupervisor = fechaderivacionsupervisor;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHAAPROBACIONRECHAZO", length = 23)
	public Date getFechaaprobacionrechazo() {
		return this.fechaaprobacionrechazo;
	}

	public void setFechaaprobacionrechazo(Date fechaaprobacionrechazo) {
		this.fechaaprobacionrechazo = fechaaprobacionrechazo;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "flujodocumento")
	public Set<Seguimientoflujo> getSeguimientoflujos() {
		return this.seguimientoflujos;
	}

	public void setSeguimientoflujos(Set<Seguimientoflujo> seguimientoflujos) {
		this.seguimientoflujos = seguimientoflujos;
	}
	
	@Column(name = "IDPERFILUSUARIO")
	public Integer getIdperfilusuario() {
		return this.idperfilusuario;
	}

	public void setIdperfilusuario(Integer idperfilusuario) {
		this.idperfilusuario = idperfilusuario;
	}

}

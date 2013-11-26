package com.sblm.model3;

// Generated 25/11/2013 12:19:12 PM by Hibernate Tools 4.0.0

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Auditoria generated by hbm2java
 */
@Entity
@Table(name = "AUDITORIA", schema = "dbo", catalog = "beneficenciadba")
public class Auditoria implements java.io.Serializable {

	private int idauditoria;
	private Perfil perfil;
	private Modulo modulo;
	private Estadoauditoria estadoauditoria;
	private Eventoauditoria eventoauditoria;
	private Usuario usuarioByIdusuario;
	private Usuario usuarioByIdusuariodestino;
	private Date fecentrada;
	private String horaentrada;
	private String nompantalla;
	private String url;
	private Boolean estado;
	private Integer codauditoria;
	private String ip;
	private String mensajepersonalizado;

	public Auditoria() {
	}

	public Auditoria(int idauditoria) {
		this.idauditoria = idauditoria;
	}

	public Auditoria(int idauditoria, Perfil perfil, Modulo modulo,
			Estadoauditoria estadoauditoria, Eventoauditoria eventoauditoria,
			Usuario usuarioByIdusuario, Usuario usuarioByIdusuariodestino,
			Date fecentrada, String horaentrada, String nompantalla,
			String url, Boolean estado, Integer codauditoria, String ip,
			String mensajepersonalizado) {
		this.idauditoria = idauditoria;
		this.perfil = perfil;
		this.modulo = modulo;
		this.estadoauditoria = estadoauditoria;
		this.eventoauditoria = eventoauditoria;
		this.usuarioByIdusuario = usuarioByIdusuario;
		this.usuarioByIdusuariodestino = usuarioByIdusuariodestino;
		this.fecentrada = fecentrada;
		this.horaentrada = horaentrada;
		this.nompantalla = nompantalla;
		this.url = url;
		this.estado = estado;
		this.codauditoria = codauditoria;
		this.ip = ip;
		this.mensajepersonalizado = mensajepersonalizado;
	}

	@Id
	@Column(name = "IDAUDITORIA", unique = true, nullable = false)
	public int getIdauditoria() {
		return this.idauditoria;
	}

	public void setIdauditoria(int idauditoria) {
		this.idauditoria = idauditoria;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IDPERFIL")
	public Perfil getPerfil() {
		return this.perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IDMODULO")
	public Modulo getModulo() {
		return this.modulo;
	}

	public void setModulo(Modulo modulo) {
		this.modulo = modulo;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IDESTADOAUDITORIA")
	public Estadoauditoria getEstadoauditoria() {
		return this.estadoauditoria;
	}

	public void setEstadoauditoria(Estadoauditoria estadoauditoria) {
		this.estadoauditoria = estadoauditoria;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IDEVENTOAUDITORIA")
	public Eventoauditoria getEventoauditoria() {
		return this.eventoauditoria;
	}

	public void setEventoauditoria(Eventoauditoria eventoauditoria) {
		this.eventoauditoria = eventoauditoria;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IDUSUARIO")
	public Usuario getUsuarioByIdusuario() {
		return this.usuarioByIdusuario;
	}

	public void setUsuarioByIdusuario(Usuario usuarioByIdusuario) {
		this.usuarioByIdusuario = usuarioByIdusuario;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IDUSUARIODESTINO")
	public Usuario getUsuarioByIdusuariodestino() {
		return this.usuarioByIdusuariodestino;
	}

	public void setUsuarioByIdusuariodestino(Usuario usuarioByIdusuariodestino) {
		this.usuarioByIdusuariodestino = usuarioByIdusuariodestino;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECENTRADA", length = 23)
	public Date getFecentrada() {
		return this.fecentrada;
	}

	public void setFecentrada(Date fecentrada) {
		this.fecentrada = fecentrada;
	}

	@Column(name = "HORAENTRADA", length = 50)
	public String getHoraentrada() {
		return this.horaentrada;
	}

	public void setHoraentrada(String horaentrada) {
		this.horaentrada = horaentrada;
	}

	@Column(name = "NOMPANTALLA", length = 50)
	public String getNompantalla() {
		return this.nompantalla;
	}

	public void setNompantalla(String nompantalla) {
		this.nompantalla = nompantalla;
	}

	@Column(name = "URL", length = 200)
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "ESTADO")
	public Boolean getEstado() {
		return this.estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	@Column(name = "CODAUDITORIA")
	public Integer getCodauditoria() {
		return this.codauditoria;
	}

	public void setCodauditoria(Integer codauditoria) {
		this.codauditoria = codauditoria;
	}

	@Column(name = "IP", length = 20)
	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Column(name = "MENSAJEPERSONALIZADO", length = 100)
	public String getMensajepersonalizado() {
		return this.mensajepersonalizado;
	}

	public void setMensajepersonalizado(String mensajepersonalizado) {
		this.mensajepersonalizado = mensajepersonalizado;
	}

}

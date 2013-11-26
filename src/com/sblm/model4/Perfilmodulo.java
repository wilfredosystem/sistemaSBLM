package com.sblm.model4;

// Generated 06-nov-2013 11:43:21 by Hibernate Tools 4.0.0

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
 * Perfilmodulo generated by hbm2java
 */
@Entity
@Table(name = "PERFILMODULO", schema = "dbo", catalog = "beneficenciadba")
public class Perfilmodulo implements java.io.Serializable {

	private int idmodulopermiso;
	private Perfil perfil;
	private Modulo modulo;
	private Permiso permiso;
	private Boolean estado;
	private String usrcre;
	private String usrmod;
	private Date feccre;
	private Date fecmod;

	public Perfilmodulo() {
	}

	public Perfilmodulo(int idmodulopermiso) {
		this.idmodulopermiso = idmodulopermiso;
	}

	public Perfilmodulo(int idmodulopermiso, Perfil perfil, Modulo modulo,
			Permiso permiso, Boolean estado, String usrcre, String usrmod,
			Date feccre, Date fecmod) {
		this.idmodulopermiso = idmodulopermiso;
		this.perfil = perfil;
		this.modulo = modulo;
		this.permiso = permiso;
		this.estado = estado;
		this.usrcre = usrcre;
		this.usrmod = usrmod;
		this.feccre = feccre;
		this.fecmod = fecmod;
	}

	@Id
	@Column(name = "IDMODULOPERMISO", unique = true, nullable = false)
	public int getIdmodulopermiso() {
		return this.idmodulopermiso;
	}

	public void setIdmodulopermiso(int idmodulopermiso) {
		this.idmodulopermiso = idmodulopermiso;
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
	@JoinColumn(name = "IDPERMISO")
	public Permiso getPermiso() {
		return this.permiso;
	}

	public void setPermiso(Permiso permiso) {
		this.permiso = permiso;
	}

	@Column(name = "ESTADO")
	public Boolean getEstado() {
		return this.estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
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

}

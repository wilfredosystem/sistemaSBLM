package com.sblm.model;

// Generated 14-oct-2013 14:58:03 by Hibernate Tools 4.0.0

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Estadoauditoria generated by hbm2java
 */
@Entity
@Table(name = "ESTADOAUDITORIA", schema = "dbo", catalog = "beneficenciadba")
public class Estadoauditoria implements java.io.Serializable {

	private int idestadoauditoria;
	private String nombreestado;
	private Set<Auditoria> auditorias = new HashSet<Auditoria>(0);

	public Estadoauditoria() {
	}

	public Estadoauditoria(int idestadoauditoria) {
		this.idestadoauditoria = idestadoauditoria;
	}

	public Estadoauditoria(int idestadoauditoria, String nombreestado,
			Set<Auditoria> auditorias) {
		this.idestadoauditoria = idestadoauditoria;
		this.nombreestado = nombreestado;
		this.auditorias = auditorias;
	}

	@Id @GeneratedValue
	@Column(name = "IDESTADOAUDITORIA", unique = true, nullable = false)
	public int getIdestadoauditoria() {
		return this.idestadoauditoria;
	}

	public void setIdestadoauditoria(int idestadoauditoria) {
		this.idestadoauditoria = idestadoauditoria;
	}

	@Column(name = "NOMBREESTADO", length = 50)
	public String getNombreestado() {
		return this.nombreestado;
	}

	public void setNombreestado(String nombreestado) {
		this.nombreestado = nombreestado;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "estadoauditoria")
	public Set<Auditoria> getAuditorias() {
		return this.auditorias;
	}

	public void setAuditorias(Set<Auditoria> auditorias) {
		this.auditorias = auditorias;
	}

}

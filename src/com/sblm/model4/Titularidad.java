package com.sblm.model4;

// Generated 06-nov-2013 11:43:21 by Hibernate Tools 4.0.0

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Titularidad generated by hbm2java
 */
@Entity
@Table(name = "TITULARIDAD", schema = "dbo", catalog = "beneficenciadba")
public class Titularidad implements java.io.Serializable {

	private int idtitularidad;
	private String nombre;
	private String descripcion;
	private Boolean estado;
	private Set<Inmueble> inmuebles = new HashSet<Inmueble>(0);

	public Titularidad() {
	}

	public Titularidad(int idtitularidad) {
		this.idtitularidad = idtitularidad;
	}

	public Titularidad(int idtitularidad, String nombre, String descripcion,
			Boolean estado, Set<Inmueble> inmuebles) {
		this.idtitularidad = idtitularidad;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.estado = estado;
		this.inmuebles = inmuebles;
	}

	@Id
	@Column(name = "IDTITULARIDAD", unique = true, nullable = false)
	public int getIdtitularidad() {
		return this.idtitularidad;
	}

	public void setIdtitularidad(int idtitularidad) {
		this.idtitularidad = idtitularidad;
	}

	@Column(name = "NOMBRE", length = 100)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "DESCRIPCION", length = 100)
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Column(name = "ESTADO")
	public Boolean getEstado() {
		return this.estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "titularidad")
	public Set<Inmueble> getInmuebles() {
		return this.inmuebles;
	}

	public void setInmuebles(Set<Inmueble> inmuebles) {
		this.inmuebles = inmuebles;
	}

}

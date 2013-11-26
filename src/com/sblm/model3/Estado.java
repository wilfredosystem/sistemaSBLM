package com.sblm.model3;

// Generated 25/11/2013 12:19:12 PM by Hibernate Tools 4.0.0

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Estado generated by hbm2java
 */
@Entity
@Table(name = "ESTADO", schema = "dbo", catalog = "beneficenciadba")
public class Estado implements java.io.Serializable {

	private int idestado;
	private String codigoestado;
	private String descripcion;
	private Asunto asunto;

	public Estado() {
	}

	public Estado(int idestado) {
		this.idestado = idestado;
	}

	public Estado(int idestado, String codigoestado, String descripcion,
			Asunto asunto) {
		this.idestado = idestado;
		this.codigoestado = codigoestado;
		this.descripcion = descripcion;
		this.asunto = asunto;
	}

	@Id
	@Column(name = "IDESTADO", unique = true, nullable = false)
	public int getIdestado() {
		return this.idestado;
	}

	public void setIdestado(int idestado) {
		this.idestado = idestado;
	}

	@Column(name = "CODIGOESTADO", length = 4)
	public String getCodigoestado() {
		return this.codigoestado;
	}

	public void setCodigoestado(String codigoestado) {
		this.codigoestado = codigoestado;
	}

	@Column(name = "DESCRIPCION", length = 50)
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "estado")
	public Asunto getAsunto() {
		return this.asunto;
	}

	public void setAsunto(Asunto asunto) {
		this.asunto = asunto;
	}

}

package com.sblm.model3;

// Generated 25/11/2013 12:19:12 PM by Hibernate Tools 4.0.0

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * Asunto generated by hbm2java
 */
@Entity
@Table(name = "ASUNTO", schema = "dbo", catalog = "beneficenciadba")
public class Asunto implements java.io.Serializable {

	private int idasunto;
	private Estado estado;
	private String codigoasunto;
	private String descripcionasunto;

	public Asunto() {
	}

	public Asunto(Estado estado) {
		this.estado = estado;
	}

	public Asunto(Estado estado, String codigoasunto, String descripcionasunto) {
		this.estado = estado;
		this.codigoasunto = codigoasunto;
		this.descripcionasunto = descripcionasunto;
	}

	@GenericGenerator(name = "generator", strategy = "foreign", parameters = @Parameter(name = "property", value = "estado"))
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "IDASUNTO", unique = true, nullable = false)
	public int getIdasunto() {
		return this.idasunto;
	}

	public void setIdasunto(int idasunto) {
		this.idasunto = idasunto;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	public Estado getEstado() {
		return this.estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	@Column(name = "CODIGOASUNTO", length = 4)
	public String getCodigoasunto() {
		return this.codigoasunto;
	}

	public void setCodigoasunto(String codigoasunto) {
		this.codigoasunto = codigoasunto;
	}

	@Column(name = "DESCRIPCIONASUNTO", length = 100)
	public String getDescripcionasunto() {
		return this.descripcionasunto;
	}

	public void setDescripcionasunto(String descripcionasunto) {
		this.descripcionasunto = descripcionasunto;
	}

}

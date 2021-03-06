package com.sblm.model3;

// Generated 25/11/2013 12:19:12 PM by Hibernate Tools 4.0.0

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Igv generated by hbm2java
 */
@Entity
@Table(name = "IGV", schema = "dbo", catalog = "beneficenciadba")
public class Igv implements java.io.Serializable {

	private int idigv;
	private String valorigv;
	private String usrcre;
	private String usrmod;
	private Date feccre;
	private Date fecmod;

	public Igv() {
	}

	public Igv(int idigv) {
		this.idigv = idigv;
	}

	public Igv(int idigv, String valorigv, String usrcre, String usrmod,
			Date feccre, Date fecmod) {
		this.idigv = idigv;
		this.valorigv = valorigv;
		this.usrcre = usrcre;
		this.usrmod = usrmod;
		this.feccre = feccre;
		this.fecmod = fecmod;
	}

	@Id
	@Column(name = "IDIGV", unique = true, nullable = false)
	public int getIdigv() {
		return this.idigv;
	}

	public void setIdigv(int idigv) {
		this.idigv = idigv;
	}

	@Column(name = "VALORIGV", length = 10)
	public String getValorigv() {
		return this.valorigv;
	}

	public void setValorigv(String valorigv) {
		this.valorigv = valorigv;
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

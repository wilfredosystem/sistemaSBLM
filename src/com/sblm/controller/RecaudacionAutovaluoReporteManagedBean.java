package com.sblm.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.JRLoader;

import com.sblm.bean.Columna;
import com.sblm.bean.UpaLista;
import com.sblm.model.Contrato;
import com.sblm.model.Detallecartera;
import com.sblm.model.Ubigeo;
import com.sblm.model.Upa;
import com.sblm.service.IRecaudacionAutovaluoReporteService;

@ManagedBean(name = "autovaluoReporteMB")
@ViewScoped
public class RecaudacionAutovaluoReporteManagedBean implements Serializable {

	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{autovaluoReporteService}")
	private transient IRecaudacionAutovaluoReporteService autovaluoReporteService;

	@ManagedProperty(value = "#{usuarioMB}")
	UsuarioManagedBean userMB;

	@ManagedProperty(value = "#{tipocambioMB}")
	TipoCambioManagedBean tipocambioMB;

	JasperPrint jasperPrint;

	Ubigeo ubigeo = new Ubigeo();
	List<Ubigeo> ubigeos = new ArrayList<Ubigeo>();

	// lista para upas
	List listaupas = new ArrayList();

	/******* PATRON GENERAL DE UPAS *******/
	private String mora;
	List listaupaspadron = new ArrayList();
	
	List<Detallecartera> lstDetallecarteras;
	
	@PostConstruct
	public void initObjects() {

		ubigeos = getAutovaluoReporteService().listarUbigeos();
	}

	

	

	/************ REPORTE UPA POR DISTRITO *************/
	public void mostrarReporteUpa() {
		List<Upa> lstUpas = new ArrayList<Upa>();
		lstUpas = getAutovaluoReporteService().listarUpasXDistrito(
				ubigeo.getUbigeo());

		if (lstUpas.isEmpty()) {
			listaupas = null;
		}
		ArrayList<UpaLista> lstUpa = new ArrayList<UpaLista>();
		ArrayList<Columna> lstImportes = new ArrayList<Columna>();
		for (Upa upa : lstUpas) {

			Columna importes = new Columna();
			importes.setRep_clave(upa.getClave());
			importes.setRep_direccion(upa.getDireccion());
			importes.setRep_mazlote(upa.getManzana() + "-" + upa.getLote());
			importes.setRep_stand(upa.getUbicacionpiso());
			importes.setRep_numero(upa.getNumprincipal());
			importes.setRep_distrito(upa.getInmueble().getUbigeo().getDist());
			// para enviar al parametro del reporte
			ubigeo.setDist(upa.getInmueble().getUbigeo().getDist());

			Contrato contra = getAutovaluoReporteService().obtenerContratoXUpa(
					upa.getIdupa());

			if (contra == null) {
				listaupas = null;
				System.out.println("no existe contrato");
			} else {
				double montoacumulado = 0;
				for (int anho = 1999; anho <= 2014; anho++) {
					double monto = getAutovaluoReporteService()
							.obtenerMontoPorAnho(contra.getIdcontrato(),
									anho + "");

					switch (anho) {
					case 1999:
						importes.setImporteupa01(monto);
						break;
					case 2000:
						importes.setImporteupa02(monto);
						break;
					case 2001:
						importes.setImporteupa03(monto);
						break;
					case 2002:
						importes.setImporteupa04(monto);
						break;
					case 2003:
						importes.setImporteupa05(monto);
						break;
					case 2004:
						importes.setImporteupa06(monto);
						break;
					case 2005:
						importes.setImporteupa07(monto);
						break;
					case 2006:
						importes.setImporteupa08(monto);
						break;
					case 2007:
						importes.setImporteupa09(monto);
						break;
					case 2008:
						importes.setImporteupa10(monto);
						break;
					case 2009:
						importes.setImporteupa11(monto);
						break;
					case 2010:
						importes.setImporteupa12(monto);
						break;
					case 2011:
						importes.setImporteupa13(monto);
						break;
					case 2012:
						importes.setImporteupa14(monto);
						break;
					case 2013:
						importes.setImporteupa15(monto);
						break;
					case 2014:
						importes.setImporteupa16(monto);
						break;
					default:
						break;
					}
					montoacumulado = montoacumulado + monto;
				}
				importes.setImporteupatotal(montoacumulado);
				importes.setRep_cbeneficio("-");
				importes.setRep_observacion(upa.getObsupa());

				lstImportes.add(importes);

				// asignamos las listas
				listaupas = lstUpa;

				for (UpaLista upaLista : lstUpa) {
					System.out
							.println("val::"
									+ upaLista.getLstDetalle().get(0)
											.getImporteupa15());
				}
			}
		}
		UpaLista upacompleta = new UpaLista();
		upacompleta.setLstDetalle(lstImportes);
		lstUpa.add(upacompleta);
	}
	public void initReporteUpa() throws JRException {
		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(
				listaupas);
		String reportPath = "D:\\reporteupa\\reporteAutovaluoUpa.jasper";

		Map<String, Object> mapa = new HashMap<String, Object>();

		mapa.put("usuariocreador", "WILFREDO HUAIRA");
		mapa.put("tipocambio", "2.5");

		String hijo01 = "d:/reporteupa/reporteAutovaluoUpa_subreport1.jasper";
		mapa.put("subreporte01", hijo01);

		// PARAMETRO USUARIO CREADOR
		mapa.put("usuariocreador", userMB.getNombrecompleto());
		// PARAMETRO TIPO CAMBIO
		mapa.put("tipocambio", tipocambioMB.getTipocambioService()
				.obtenerUltimoTipocambio().getTipocambio());
		// PARAMETRO DISTRITO

		System.out.println("distrito:::" + ubigeo.getDist());
		mapa.put("distrito", ubigeo.getDist());
		jasperPrint = JasperFillManager.fillReport(reportPath, mapa,
				beanCollectionDataSource);
	}

	public void ExcelReporteUpa(ActionEvent actionEvent) throws JRException,
			IOException {
		initReporteUpa();
		HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext
				.getCurrentInstance().getExternalContext().getResponse();

		Date ahora = new Date();
		SimpleDateFormat formateador = new SimpleDateFormat("dd_MM_yyyy");

		httpServletResponse.addHeader("Content-disposition",
				"attachment; filename=ReporteUpa_" + formateador.format(ahora)
						+ ".xlsx");

		ServletOutputStream servletOutputStream = httpServletResponse
				.getOutputStream();
		JRXlsxExporter docxExporter = new JRXlsxExporter();
		docxExporter
				.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		docxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
				servletOutputStream);
		docxExporter.exportReport();
		FacesContext.getCurrentInstance().responseComplete();
	}

	public void PDFReporteUpa(ActionEvent actionEvent) throws JRException,
			IOException {

		initReporteUpa();
		FacesContext context = FacesContext.getCurrentInstance();
		try {

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			JRPdfExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
			exporter.exportReport();

			byte[] bytes = baos.toByteArray();

			if (bytes != null && bytes.length > 0) {
				HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext
						.getCurrentInstance().getExternalContext()
						.getResponse();
				httpServletResponse.setContentType("application/pdf");

				Date ahora = new Date();
				SimpleDateFormat formateador = new SimpleDateFormat(
						"dd_MM_yyyy");

				httpServletResponse.addHeader(
						"Content-disposition",
						"attachment; filename=ReporteUpa_"
								+ formateador.format(ahora) + ".pdf");

				ServletOutputStream servletOutputStream = httpServletResponse
						.getOutputStream();
				JasperExportManager.exportReportToPdfStream(jasperPrint,
						servletOutputStream);

				context.getApplication().getStateManager().saveView(context);

				context.responseComplete();
			} else {
				System.out.println("vacio");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	/************ REPORTE PADRON GENERAL DE UPAS *************/
	public void mostrarReporteUpaPadronxxxxxxxxxx() {
		List<Upa> lstUpas = new ArrayList<Upa>();
		
		lstUpas = getAutovaluoReporteService().listarUpasXDistrito(ubigeo.getUbigeo());

		if (lstUpas.isEmpty()) {
			listaupas = null;
		}
		ArrayList<UpaLista> listaUpas = new ArrayList<UpaLista>();
		ArrayList<Columna> lstImportes = new ArrayList<Columna>();
		for (Upa upa : lstUpas) {

			Columna importes = new Columna();
			importes.setRep_clave(upa.getClave());
			importes.setRep_direccion(upa.getDireccion());
			importes.setRep_mazlote(upa.getManzana() + "-" + upa.getLote());
			importes.setRep_stand(upa.getUbicacionpiso());
			importes.setRep_numero(upa.getNumprincipal());
			importes.setRep_distrito(upa.getInmueble().getUbigeo().getDist());
			// para enviar al parametro del reporte
			ubigeo.setDist(upa.getInmueble().getUbigeo().getDist());

			Contrato contra = getAutovaluoReporteService().obtenerContratoXUpa(
					upa.getIdupa());

			if (contra == null) {
				listaupas = null;
				System.out.println("no existe contrato 2");
			} else {
				double montoacumulado = 0;
				for (int anho = 1999; anho <= 2014; anho++) {
					double monto = getAutovaluoReporteService()
							.obtenerMontoPorAnho(contra.getIdcontrato(),
									anho + "");

					switch (anho) {
					case 1999:
						importes.setImporteupa01(monto);
						break;
					case 2000:
						importes.setImporteupa02(monto);
						break;
					case 2001:
						importes.setImporteupa03(monto);
						break;
					case 2002:
						importes.setImporteupa04(monto);
						break;
					case 2003:
						importes.setImporteupa05(monto);
						break;
					case 2004:
						importes.setImporteupa06(monto);
						break;
					case 2005:
						importes.setImporteupa07(monto);
						break;
					case 2006:
						importes.setImporteupa08(monto);
						break;
					case 2007:
						importes.setImporteupa09(monto);
						break;
					case 2008:
						importes.setImporteupa10(monto);
						break;
					case 2009:
						importes.setImporteupa11(monto);
						break;
					case 2010:
						importes.setImporteupa12(monto);
						break;
					case 2011:
						importes.setImporteupa13(monto);
						break;
					case 2012:
						importes.setImporteupa14(monto);
						break;
					case 2013:
						importes.setImporteupa15(monto);
						break;
					case 2014:
						importes.setImporteupa16(monto);
						break;
					default:
						break;
					}
					montoacumulado = montoacumulado + monto;
				}
				importes.setImporteupatotal(montoacumulado);
				importes.setRep_cbeneficio("-");
				importes.setRep_observacion(upa.getObsupa());

				lstImportes.add(importes);

				UpaLista upacompleta = new UpaLista();
				upacompleta.setLstDetalle(lstImportes);
				listaUpas.add(upacompleta);
				// asignamos las listas
				listaupas = listaUpas;

				for (UpaLista upaLista : listaUpas) {
					System.out
							.println("val::"
									+ upaLista.getLstDetalle().get(0)
											.getImporteupa15());
				}
			}
		}
	}
	public void mostrarReporteUpaPadron() {
		List<Upa> lstUpas = new ArrayList<Upa>();
		//Listamos todos los distritos de lima
		System.out.println("xxxxxxxxxxxxxxxxxx");
		
		lstUpas = getAutovaluoReporteService().listarUpasXInmueble();
		
		
		lstDetallecarteras = getAutovaluoReporteService().listarDetallescarteras();

		if (lstUpas.isEmpty()) {
			listaupas = null;
		}
		ArrayList<UpaLista> listaUpas = new ArrayList<UpaLista>();
		ArrayList<Columna> lstImportes = new ArrayList<Columna>();
		for (Upa upa : lstUpas) {

			Columna importes = new Columna();
			importes.setRep_clave(upa.getClave());
			importes.setRep_direccion(upa.getDireccion());
			importes.setRep_mazlote(upa.getManzana() + "-" + upa.getLote());
			importes.setRep_stand(upa.getUbicacionpiso());
			importes.setRep_numero(upa.getNumprincipal());
			importes.setRep_distrito(upa.getInmueble().getUbigeo().getDist());
			// para enviar al parametro del reporte
			ubigeo.setDist(upa.getInmueble().getUbigeo().getDist());

			Contrato contra = getAutovaluoReporteService().obtenerContratoXUpa(
					upa.getIdupa());

			if (contra == null) {
				listaupas = null;
				System.out.println("no existe contrato3");
			} else {
				double montoacumulado = 0;
				for (int anho = 1999; anho <= 2014; anho++) {
					double monto = getAutovaluoReporteService()
							.obtenerMontoPorAnho(contra.getIdcontrato(),
									anho + "");

					switch (anho) {
					case 1999:
						importes.setImporteupa01(monto);
						break;
					case 2000:
						importes.setImporteupa02(monto);
						break;
					case 2001:
						importes.setImporteupa03(monto);
						break;
					case 2002:
						importes.setImporteupa04(monto);
						break;
					case 2003:
						importes.setImporteupa05(monto);
						break;
					case 2004:
						importes.setImporteupa06(monto);
						break;
					case 2005:
						importes.setImporteupa07(monto);
						break;
					case 2006:
						importes.setImporteupa08(monto);
						break;
					case 2007:
						importes.setImporteupa09(monto);
						break;
					case 2008:
						importes.setImporteupa10(monto);
						break;
					case 2009:
						importes.setImporteupa11(monto);
						break;
					case 2010:
						importes.setImporteupa12(monto);
						break;
					case 2011:
						importes.setImporteupa13(monto);
						break;
					case 2012:
						importes.setImporteupa14(monto);
						break;
					case 2013:
						importes.setImporteupa15(monto);
						break;
					case 2014:
						importes.setImporteupa16(monto);
						break;
					default:
						break;
					}
					montoacumulado = montoacumulado + monto;
				}
				importes.setImporteupatotal(montoacumulado);
				importes.setRep_cbeneficio("-");
				importes.setRep_observacion(upa.getObsupa());

				lstImportes.add(importes);

				
				// asignamos las listas
				listaupas = listaUpas;

				for (UpaLista upaLista : listaUpas) {
					System.out
							.println("val::"
									+ upaLista.getLstDetalle().get(0)
											.getImporteupa15());
				}
			}
		}
		UpaLista upacompleta = new UpaLista();
		upacompleta.setLstDetalle(lstImportes);
		listaUpas.add(upacompleta);
	}
	public void initReporteUpaPadron() throws JRException {
		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(
				lstDetallecarteras);
		String reportPath = "D:\\reporteupa\\reporteAutovaluoUpaPadronx.jasper";

		Map<String, Object> mapa = new HashMap<String, Object>();

//		String hijo01 = "d:/reporteupa/reporteAutovaluoUpaPadron_subreport1.jasper";
//		mapa.put("subreporte01", hijo01);

		// PARAMETRO USUARIO CREADOR
		mapa.put("usuariocreador", userMB.getNombrecompleto());
		// PARAMETRO TIPO CAMBIO
		mapa.put("tipocambio", tipocambioMB.getTipocambioService()
				.obtenerUltimoTipocambio().getTipocambio());
		// PARAMETRO DISTRITO
 
		System.out.println("distrito:::" + ubigeo.getDist());
		mapa.put("distrito", ubigeo.getDist());
		jasperPrint = JasperFillManager.fillReport(reportPath, mapa,
				beanCollectionDataSource);
	}

	public void ExcelReporteUpaPadron(ActionEvent actionEvent) throws JRException,
			IOException {
		initReporteUpaPadron();
		HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext
				.getCurrentInstance().getExternalContext().getResponse();

		Date ahora = new Date();
		SimpleDateFormat formateador = new SimpleDateFormat("dd_MM_yyyy");

		httpServletResponse.addHeader("Content-disposition",
				"attachment; filename=ReporteUpaPadron_" + formateador.format(ahora)
						+ ".xlsx");

		ServletOutputStream servletOutputStream = httpServletResponse
				.getOutputStream();
		JRXlsxExporter docxExporter = new JRXlsxExporter();
		docxExporter
				.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		docxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
				servletOutputStream);
		docxExporter.exportReport();
		FacesContext.getCurrentInstance().responseComplete();
	}

	public void PDFReporteUpaPadron(ActionEvent actionEvent) throws JRException,
			IOException {

		initReporteUpaPadron();
		FacesContext context = FacesContext.getCurrentInstance();
		try {

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			JRPdfExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
			exporter.exportReport();

			byte[] bytes = baos.toByteArray();

			if (bytes != null && bytes.length > 0) {
				HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext
						.getCurrentInstance().getExternalContext()
						.getResponse();
				httpServletResponse.setContentType("application/pdf");

				Date ahora = new Date();
				SimpleDateFormat formateador = new SimpleDateFormat(
						"dd_MM_yyyy");

				httpServletResponse.addHeader(
						"Content-disposition",
						"attachment; filename=ReporteUpaPadron_"
								+ formateador.format(ahora) + ".pdf");

				ServletOutputStream servletOutputStream = httpServletResponse
						.getOutputStream();
				JasperExportManager.exportReportToPdfStream(jasperPrint,
						servletOutputStream);

				context.getApplication().getStateManager().saveView(context);

				context.responseComplete();
			} else {
				System.out.println("vacio");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	/*******************************************/
	public IRecaudacionAutovaluoReporteService getAutovaluoReporteService() {
		return autovaluoReporteService;
	}

	public void setAutovaluoReporteService(
			IRecaudacionAutovaluoReporteService autovaluoReporteService) {
		this.autovaluoReporteService = autovaluoReporteService;
	}

	public UsuarioManagedBean getUserMB() {
		return userMB;
	}

	public void setUserMB(UsuarioManagedBean userMB) {
		this.userMB = userMB;
	}

	public TipoCambioManagedBean getTipocambioMB() {
		return tipocambioMB;
	}

	public void setTipocambioMB(TipoCambioManagedBean tipocambioMB) {
		this.tipocambioMB = tipocambioMB;
	}

	public List<Ubigeo> getUbigeos() {
		return ubigeos;
	}

	public void setUbigeos(List<Ubigeo> ubigeos) {
		this.ubigeos = ubigeos;
	}

	public Ubigeo getUbigeo() {
		return ubigeo;
	}

	public void setUbigeo(Ubigeo ubigeo) {
		this.ubigeo = ubigeo;
	}

	public List getListaupas() {
		return listaupas;
	}

	public void setListaupas(List listaupas) {
		this.listaupas = listaupas;
	}

	public String getMora() {
		return mora;
	}

	public void setMora(String mora) {
		this.mora = mora;
	}





	public List getListaupaspadron() {
		return listaupaspadron;
	}





	public void setListaupaspadron(List listaupaspadron) {
		this.listaupaspadron = listaupaspadron;
	}





	public List<Detallecartera> getLstDetallecarteras() {
		return lstDetallecarteras;
	}





	public void setLstDetallecarteras(List<Detallecartera> lstDetallecarteras) {
		this.lstDetallecarteras = lstDetallecarteras;
	}

}

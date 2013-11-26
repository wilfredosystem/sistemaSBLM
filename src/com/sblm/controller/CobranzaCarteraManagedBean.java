package com.sblm.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.data.FilterEvent;

import com.sblm.model.Cartera;
import com.sblm.model.Contrato;
import com.sblm.model.Cuota;
import com.sblm.model.Detallecartera;
import com.sblm.model.Detallecuota;
import com.sblm.model.Upa;
import com.sblm.model.Uso;
import com.sblm.model.Usuario;
import com.sblm.service.ICarteraService;

@ManagedBean(name = "carteraMB")
@ViewScoped
public class CobranzaCarteraManagedBean implements Serializable {

	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{carteraService}")
	private transient ICarteraService carteraService;

	@ManagedProperty(value = "#{usuarioMB}")
	UsuarioManagedBean userMB;

	private List<Cartera> carteras;

	/** Cabecera cartera **/
	private Cartera cartera;
	private Cartera carteracapturado;
	boolean actualizado = false;
	private Cartera resultadocartera;
	private int numCarteras;
	private List<Usuario> usuarios;
	// para activar campos de carteradetalle
	private boolean desactivado = true;

	/** Detalle cartera **/
	boolean actualizadodetalle = false;
	private Detallecartera detallecartera;
	private Detallecartera detallecarteracapturado;
	private List<Detallecartera> detallecarteras;
	private List<Detallecartera> filtrodetallecarteras;
	private Upa upa;
	private Contrato contrato;
	private List<Contrato> contratos;

	private List<Contrato> contratosdisponibles;
	private List<Contrato> contratosxcartera;
	
	private List<Detallecuota> detallecuotaxcontrato;
	@ManagedProperty(value = "#{usoMB}")
	UsoManagedBean usoMB;

	private String usuariocreador;
	private String cargo;
	private Date fecha;
	private boolean desactivadoEdicion = false;// mostrar cabecera cartera
	private List<Uso> usos;

	/** cuota **/
	private Cuota cuota;
	private Cuota cuotacapturado;
	private List<Cuota> cuotas;
	private List<Cuota> filtrocuotas;
	private String nombreInquilinoFiltro = null;
	private String nombreInmuebleFiltro = null;
	/** consulta cartera ***/
	private boolean vertabla = false;
	/** detalle cuota **/
	private Detallecuota detallecuota;
	private List<Detallecuota> detallecuotas;
	private List<Detallecuota> detallecuotasxcontrato;
	private List<Cuota> cuotasxcontrato;
	// boolean actualizadodetallecuota = false;
	boolean actualizadopagocuota = false;
	
	boolean actualizadopagodetallecuota = false;
	private int numerocuotaspagadas;
	private int numerocuotasrestantes;

	private double montocuotaspagadas;
	private double montocuotaspagadasdolar;

	private boolean verpanel = false;

	int valornumcuotaspagadas;
	int valornumcuotasrestantes;
	double valorultimacuotaacumulada;
	double valorultimacuotarestante;
	@ManagedProperty(value = "#{tipocambioMB}")
	TipoCambioManagedBean tipocambioMB;

	@PostConstruct
	public void initObjects() {
		numerocuotaspagadas = getCarteraService().obtenerNumeroRegistros();
		// numerocuotaspagadas=getCarteraService().obtenerNumerocuotaspagadas();
		numerocuotasrestantes = getCarteraService().obtenerNumeroRegistros();
		usuariocreador = userMB.getUsuariologueado().getNombres() + " "
				+ userMB.getUsuariologueado().getApellidopat();
		cargo = userMB.getUsuariologueado().getCargo();
		// fecha=userMB.getUsuariologueado().getFeccre();
		try {
			fecha = getCarteraService().obtenerUltimoCartera().getFeccre();
		} catch (Exception e) {
			System.out.println(":::consulta vacia:::");
		}
		usos = getCarteraService().listarUsos();
		// filtrocuotas = getCarteraService().listarCuotas();
		// cuotas = getCarteraService().listarCuotas();
		filtrodetallecarteras = getCarteraService().listarDetalleCarteras();
		// detallecarteras=getCarteraService().listarDetalleCarteras();

		try {
			ActualizarMensajes();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void mostrarMontoCuota() {

		if (cuota.getContrato().getIdcontrato() == 0) {
			verpanel = false;
			System.out.println("estamos en mostrarMontoCuota");
			detallecuotasxcontrato = detallecuotas;
			// cuotasxcontrato= cuotas;
		} else {
			System.out.println("----------------calculo----------------::");

			try {
				detallecuotasxcontrato = getCarteraService().listardetallecuotasxcontratoycuota(cuota.getContrato().getIdcontrato(),cuota.getIdcuota());
			System.out.println("cuota id::"+cuota.getIdcuota());
		
			 cuota = getCarteraService().obtenerCuotaPorId(cuota.getIdcuota());
			
			System.out.println("cmonto contrato >>desde cuota::"+cuota.getContrato().getMontototalsoles());
			
			
			System.out.println("cmonto de cuota fija >>desde cuota::"+cuota.getContrato().getMontocuotasoles());
			System.out.println("total de cuotas ::"+cuota.getContrato().getNumerocuotas());
			System.out.println("numero de cuotas pagadas::"+cuota.getCuotanumero());
			System.out.println("acumulado de cuota::"+cuota.getMontoacumuladosoles());

//				cuotasxcontrato = getCarteraService().listarcuotasxcontrato(
//						cuota.getContrato().getIdcontrato());
//
//				montocuotaspagadas = getCarteraService()
//						.obtenerMontocuotaspagadas(
//								cuota.getContrato().getIdcontrato());
//
//				montocuotaspagadasdolar = (double) Math
//						.round(montocuotaspagadas
//								/ tipocambioMB.getTipocambioService()
//										.obtenerUltimoTipocambio()
//										.getTipocambio() * 1000) / 1000;
//				System.out
//						.println("montocuotaspagadas:::" + montocuotaspagadas);
//				Double montocuotasoles = getCarteraService()
//						.obtenerContratoPorId(
//								cuota.getContrato().getIdcontrato())
//						.getMontocuotasoles();
//				valornumcuotaspagadas = (int) (montocuotaspagadas / montocuotasoles) + 1;
//				valornumcuotasrestantes = getCarteraService()
//						.obtenerContratoPorId(
//								cuota.getContrato().getIdcontrato())
//						.getNumerocuotas()
//						- valornumcuotaspagadas;
//				
//				valorultimacuotaacumulada = (double) (montocuotaspagadas - valornumcuotaspagadas
//						* montocuotasoles);
//				valorultimacuotarestante = montocuotasoles
//						- valorultimacuotaacumulada;
//
//				System.out.println("montocuotaspagadas::" + montocuotaspagadas);
//				System.out.println("montocuotasoles::" + montocuotasoles);
//				System.out.println("valornumcuotaspagadas::"
//						+ valornumcuotaspagadas);
//				System.out.println("valorultimacuotaacumulada::"
//						+ valorultimacuotaacumulada);
//
//				System.out.println("--------------------------------::");
//				cuota.getContrato().setMontocuotasoles(
//						getCarteraService().obtenerContratoPorId(
//								cuota.getContrato().getIdcontrato())
//								.getMontocuotasoles());
//				cuota.getContrato().setMontocuotadolar(
//						getCarteraService().obtenerContratoPorId(
//								cuota.getContrato().getIdcontrato())
//								.getMontocuotadolar());
//				cuota.getContrato().setMontototalsoles(
//						getCarteraService().obtenerContratoPorId(
//								cuota.getContrato().getIdcontrato())
//								.getMontototalsoles());
//				cuota.getContrato().setMontototaldolar(
//						getCarteraService().obtenerContratoPorId(
//								cuota.getContrato().getIdcontrato())
//								.getMontototaldolar());
//				cuota.getContrato().setNumerocuotas(
//						getCarteraService().obtenerContratoPorId(
//								cuota.getContrato().getIdcontrato())
//								.getNumerocuotas());
//				cuota.getContrato().setInquilino(
//						getCarteraService().obtenerContratoPorId(
//								cuota.getContrato().getIdcontrato())
//								.getInquilino());
				verpanel = true;
			} catch (Exception e) {
				System.out.println("error mostrar panel cuotas::"
						+ e.getMessage());
			}
		}
	}

	public void cambiarMonedaSolesaDolar() {
		System.out.println("soles:::" + detallecuota.getMontosoles());
		Double valSoles = detallecuota.getMontosoles();
		Double valDolar = valSoles
				/ tipocambioMB.getTipocambioService().obtenerUltimoTipocambio()
						.getTipocambio();
		detallecuota.setMontodolar((double) Math.round(valDolar * 1000) / 1000);
	}

	public void cambiarMonedaDolaraSoles() {
		System.out.println("dolar:::" + detallecuota.getMontodolar());
		Double valDolar = detallecuota.getMontodolar();
		Double valSoles = valDolar
				* tipocambioMB.getTipocambioService().obtenerUltimoTipocambio()
						.getTipocambio();
		detallecuota.setMontosoles((double) Math.round(valSoles * 1000) / 1000);
	}

	public void cargarbusquedaDetalleCartera() {
		System.out.println("entor busquedaaaa");
		detallecarteras = getCarteraService().listarDetalleCarteras();

		cuotas = getCarteraService().listarCuotasPorIdCartera(
				cartera.getIdcartera());

	}

	public void mostrarPagosCartera() {
		System.out.println("entro mostrarPagosCartera::"
				+ cartera.getIdcartera());
		vertabla = true;
		cuotas = getCarteraService().listarCuotasPorIdCartera(
				cartera.getIdcartera());

		cuotasxcontrato = getCarteraService().listarcuotasxcontrato(
				cuota.getContrato().getIdcontrato());

	}

	public void mostrarNumeroCuotaxContrato() {
	System.out.println("id contratozzz:::"+cuota.getContrato().getIdcontrato());
	
	int tamlistacuota =getCarteraService().listarcuotasxcontrato(cuota.getContrato().getIdcontrato()).size();
	
	if(tamlistacuota==0){
		System.out.println("nuevoooofff");
		Cuota cuot=new Cuota();
		cuot.setCuotanumero(1);//creamos la 1ra cuota
		cuot.setMontoacumuladosoles(0.0);
		cuot.setMontoacumuladodolar(0.0);
		
		cuot.setCancelado(false);
		Contrato contra=new Contrato();
		contra.setIdcontrato(cuota.getContrato().getIdcontrato());
		cuot.setContrato(contra);
		getCarteraService().registrarCuota(cuot);
		
		cuota=cuot;
		actualizadopagocuota=true;
	}
	
		cuotasxcontrato = getCarteraService().listarcuotasxcontrato(cuota.getContrato().getIdcontrato());
		
	}

	public void mostrarDetallecuotaxContrato() {
		System.out.println("mostrarPagosContrato2:: Cartera id>>::" + cartera.getIdcartera());
		System.out.println("mostrarPagosContrato2:: contrato id>>::"+contrato.getIdcontrato());
		if (contrato.getIdcontrato() != 0) {
			detallecuotaxcontrato=getCarteraService().listardetallecuotaxcontrato(cartera.getIdcartera(),contrato.getIdcontrato());
			
		} else {
			System.out.println("id==0");
			contratosxcartera = getCarteraService().listarContratosxcartera(
					cartera.getIdcartera());

		}
	}
	public void mostrarPagosContrato() {
		
		// cuotasxcontrato =
		// getCarteraService().listarcuotasxcontrato(cuota.getContrato().getIdcontrato());
		System.out.println(" Cartera id>>::" + cartera.getIdcartera());
		System.out.println(" contrato id>>::"+ cuota.getContrato().getIdcontrato());
		if (cuota.getContrato().getIdcontrato() != 0) {
			System.out.println("id !=  0::diferente");
			contratosxcartera = getCarteraService().listarContratosdecartera(cartera.getIdcartera(),cuota.getContrato().getIdcontrato());

		} else {
			System.out.println("id==0:::igual");
			contratosxcartera = getCarteraService().listarContratosxcartera(
					cartera.getIdcartera());

		}

	}
	public void cargarContratosxcartera2() {
		System.out.println("cargarContratosxcartera2### Cartera id>>::" + cartera.getIdcartera());
		contratosxcartera = getCarteraService().listarContratosxcartera(cartera.getIdcartera());
	}
	public void cargarContratosxcartera() {
		System.out.println(" Cartera id>>::" + cartera.getIdcartera());
		System.out.println(" contrato id>>::"+ cuota.getContrato().getIdcontrato());

		try {
			// muestra todas los contratos con sus cuotas
			contratosxcartera = getCarteraService().listarContratosxcartera(cartera.getIdcartera());
			// calculamos la suma de las cuotas,obteniendo por contrato
			for (Contrato contra : contratosxcartera) {
				List<Cuota> lstcuota = (List<Cuota>) contra.getCuotas();
				float totalcuotasoles = 0;
				float totalcuotadolares = 0;
				for (Cuota cuo : lstcuota) {
					System.out.println("cuo.getMontoacumuladosoles():::"+cuo.getMontoacumuladosoles());
					totalcuotasoles = totalcuotasoles+ cuo.getMontoacumuladosoles().floatValue();
					totalcuotadolares = totalcuotadolares+ cuo.getMontoacumuladodolar().floatValue();
					System.out.println("totalcuotasoles:::"+totalcuotasoles);
				}
				// guardamos el acumulado en la variable para mostrar en la
				// vista
				//OJO:cambiar el atributo
				contra.setMontosolesgarantia(new Double((double) Math
						.round(totalcuotasoles * 1000) / 1000));
				contra.setMontodolargarantia(new Double((double) Math
						.round(totalcuotadolares * 1000) / 1000));
			}

			vertabla = true;
		} catch (Exception e) {
			System.out.println("error cargarContratosxcartera:: "
					+ e.getMessage());
		}

	}

	public void mostrarReporteCartera() {
		System.out.println("entro mostrarPagosCartera::"
				+ cartera.getIdcartera());
		if (cartera.getIdcartera() == 0) {

			// cuotas = getCarteraService().listarCuotas();
			// filtrocuotas = cuotas;
			detallecarteras = getCarteraService().listarDetalleCarteras();
			filtrodetallecarteras = detallecarteras;
		} else {
			// cuotas = getCarteraService().listarCuotasPorIdCartera(
			// cartera.getIdcartera());
			// filtrocuotas = getCarteraService().listarCuotasPorIdCartera(
			// cartera.getIdcartera());

			detallecarteras = getCarteraService()
					.listarDetallecarteraPorIdCartera(cartera.getIdcartera());
			filtrodetallecarteras = getCarteraService()
					.listarDetallecarteraPorIdCartera(cartera.getIdcartera());

		}

	}

	public void capturarFiltro(FilterEvent event) {

		Map<String, String> tempString = event.getFilters();

		System.out.println("size filter: " + tempString.size());
		for (String key : tempString.keySet()) {
			System.out.println("key: " + key + " \t values: "
					+ tempString.get(key));
		}

		if (tempString.size() != 0) {
			nombreInquilinoFiltro = filtrocuotas.get(0).getContrato()
					.getInquilino().getNombrescompletos();
			nombreInmuebleFiltro = filtrocuotas.get(0).getContrato().getUpa()
					.getInmueble().getNumregistrosbn();
		}

	}

	JasperPrint jasperPrint;

	public void init2() throws JRException {
		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(
				filtrodetallecarteras);
		String reportPath = "C:\\reportesjasper\\reportePagoCartera.jasper";
		Map mapa = new HashMap();
		// PARAMETRO CARTERA
		if (cartera.getIdcartera() == 0) {
			mapa.put("numerocartera", "Todos");
		} else {
			String numCart = getCarteraService().obtenerCarteraPorId(
					cartera.getIdcartera()).getNumero();
			System.out.println("num cartera::" + numCart);
			mapa.put("numerocartera", numCart);
		}

		// PARAMETRO INQUILINO
		if (nombreInquilinoFiltro == null) {
			mapa.put("nombreinquilino", "Todos");

			mapa.put("montototalsoles", "14850");
			mapa.put("montototaldolar", "55000");
			mapa.put("numerocuotaspagadas", "No calculado");
			mapa.put("totalcuotas", "No calculado");
		} else {
			System.out.println("inquilinofiltrado::" + nombreInquilinoFiltro);
			mapa.put("nombreinquilino", nombreInquilinoFiltro);
		}
		// PARAMETRO INMUEBLE
		if (nombreInmuebleFiltro == null) {
			mapa.put("inmueblereporte", "Todos");
		} else {
			mapa.put("inmueblereporte", nombreInmuebleFiltro);
		}
		// PARAMETRO USUARIO CREADOR
		mapa.put("usuariocreador", userMB.getNombrecompleto());
		// PARAMETRO TIPO CAMBIO
		;
		mapa.put("tipocambio", tipocambioMB.getTipocambioService()
				.obtenerUltimoTipocambio().getTipocambio());

		jasperPrint = JasperFillManager.fillReport(reportPath, mapa,
				beanCollectionDataSource);
	}

	public void Excel(ActionEvent actionEvent) throws JRException, IOException {
		init2();
		HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext
				.getCurrentInstance().getExternalContext().getResponse();

		Date ahora = new Date();
		SimpleDateFormat formateador = new SimpleDateFormat("dd_MM_yyyy");

		httpServletResponse.addHeader("Content-disposition",
				"attachment; filename=ReportePago_" + formateador.format(ahora)
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

	public void PDF(ActionEvent actionEvent) throws JRException, IOException {

		init2();
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
						"attachment; filename=ReportePago_"
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

	/************ REPORTE PAGO CUOTA *************/

	public void initReporteCuota() throws JRException {
		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(
				contratosxcartera);
		String reportPath = "C:\\reportesjasper\\reporteCuotaPorContrato.jasper";
		Map mapa = new HashMap();
		// PARAMETRO PARA SUBREPORTE
		// C:\reportesjasper\reportePagoCuota_subreporte01.jasperreportePagoCuota_subreport1.jasper
		mapa.put("SUBREPORT_DIR", "C:\\reportesjasper\\");
		// PARAMETRO USUARIO CREADOR
		mapa.put("usuariocreador", userMB.getNombrecompleto());
		// PARAMETRO TIPO CAMBIO
		mapa.put("tipocambio", tipocambioMB.getTipocambioService()
				.obtenerUltimoTipocambio().getTipocambio());
		// PARAMETRO NUMERO CARTERA
		mapa.put("numerocartera", cartera.getIdcartera());
		jasperPrint = JasperFillManager.fillReport(reportPath, mapa,
				beanCollectionDataSource);
	}

	public void ExcelReporteCuota(ActionEvent actionEvent) throws JRException,
			IOException {
		initReporteCuota();
		HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext
				.getCurrentInstance().getExternalContext().getResponse();

		Date ahora = new Date();
		SimpleDateFormat formateador = new SimpleDateFormat("dd_MM_yyyy");

		httpServletResponse.addHeader("Content-disposition",
				"attachment; filename=ReporteCuota_" + formateador.format(ahora)
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

	public void PDFReporteCuota(ActionEvent actionEvent) throws JRException,
			IOException {

		initReporteCuota();
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
						"attachment; filename=ReporteCuota_"
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

	/************ REPORTE DETALLE CUOTA *************/

	public void initReporteDetalleCuotaPorContrato() throws JRException {
		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(
				detallecuotaxcontrato);
		String reportPath = "C:\\reportesjasper\\reportePagosPorContrato.jasper";
		Map mapa = new HashMap();
		
		// PARAMETRO USUARIO CREADOR
		mapa.put("usuariocreador", userMB.getNombrecompleto());
		// PARAMETRO TIPO CAMBIO
		mapa.put("tipocambio", tipocambioMB.getTipocambioService()
				.obtenerUltimoTipocambio().getTipocambio());
		// PARAMETRO NUMERO CARTERA
		mapa.put("numerocontrato", contrato.getIdcontrato());
		
		Contrato con =getCarteraService().obtenerContratoPorId(contrato.getIdcontrato());
		System.out.println("idcontrato##"+contrato.getIdcontrato());
		System.out.println("getMontototalsoles##"+contrato.getMontototalsoles());
		System.out.println("getMontototaldolar##"+contrato.getMontototaldolar());
		System.out.println("con.getMontototalsoles()####"+con.getMontototalsoles());
		System.out.println("con.getMontototalsoles()####"+con.getMontototaldolar());
		mapa.put("montodecontratoSoles", con.getMontototalsoles());
		mapa.put("montodecontratoDolares", con.getMontototaldolar());
		
		jasperPrint = JasperFillManager.fillReport(reportPath, mapa,
				beanCollectionDataSource);
	}

	public void ExcelReporteDetalleCuotaPorContrato(ActionEvent actionEvent) throws JRException,
			IOException {
		initReporteDetalleCuotaPorContrato();
		HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext
				.getCurrentInstance().getExternalContext().getResponse();

		Date ahora = new Date();
		SimpleDateFormat formateador = new SimpleDateFormat("dd_MM_yyyy");

		httpServletResponse.addHeader("Content-disposition",
				"attachment; filename=ReportePago_" + formateador.format(ahora)
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

	public void PDFReporteDetalleCuotaPorContrato(ActionEvent actionEvent) throws JRException,
			IOException {

		initReporteDetalleCuotaPorContrato();
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
						"attachment; filename=ReportePago_"
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
	/****** Detalle cartera ******/
	public void registrarDetalleCartera() {

		Date ahora = new Date();
		if (actualizadodetalle == true) {
			System.out.println("::::::entro actualizado detalle");
			// if (getCarteraService().listarDetalleCarterasPorNroContrato(
			// detallecartera.getContrato().getNrocontrato()) == null) {
			// } else {
			// FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
			// "Error",
			// "El numero de contrato ya se encuentra registrado.");
			// }
			//

			String usermodificador = userMB.getUsuariologueado().getNombres()
					+ " " + userMB.getUsuariologueado().getApellidopat();
			detallecartera.setFecmod(ahora);
			detallecartera.setUsrmod(usermodificador);

			System.out.println("detallecartera id:::"
					+ detallecartera.getIddetallecartera());
			getCarteraService().registrarDetalleCartera(detallecartera);
			// mostrado de tabla
			detallecarteras = getCarteraService()
					.listarDetalleCarterasPorIdCartera(cartera.getIdcartera());
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Exito",
					"Se Actualizo correctamente el detalle de la  cartera.");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} else {
			System.out.println("::::::entro registro detalle");
			System.out.println("::::::Nro contrato>>>>"
					+ contrato.getNrocontrato());
			System.out.println("::::::nro contrato::"
					+ getCarteraService().listarDetalleCarterasPorNroContrato(
							contrato.getNrocontrato()));
			if (getCarteraService().listarDetalleCarterasPorNroContrato(
					detallecartera.getContrato().getNrocontrato()).isEmpty()) {
				String usercreador = userMB.getUsuariologueado().getNombres()
						+ " " + userMB.getUsuariologueado().getApellidopat();
				detallecartera.setUsrcre(usercreador);
				detallecartera.setFeccre(ahora);
				detallecartera.setCartera(cartera);

				getCarteraService().registrarDetalleCartera(detallecartera);
				// mostrado de tabla
				System.out.println("cartera idx::" + cartera.getIdcartera());
				detallecarteras = getCarteraService()
						.listarDetalleCarterasPorIdCartera(
								getCarteraService().obtenerUltimoCartera()
										.getIdcartera());

				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Exito",
						"Se Registro correctamente el detalle de la cartera.");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			} else {
				System.out.println("repetidooo");
				FacesMessage msg = new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Error",
						"El numero de contrato ya se encuentra registrado.");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}

		}
		// para refrescar el valor en el panel
		// ActualizarMensajes();

	}

	public void editarCartera() {
		desactivadoEdicion = true;
		desactivado = false;
		// actualizadodetalle=true;
		System.out.println("onRowSelect entrooo: idcaretra::::"
				+ cartera.getIdcartera());
		detallecarteras = getCarteraService()
				.listarDetalleCarterasPorIdCartera(cartera.getIdcartera());

	}

	public void onRowSelectDetalleCartera(SelectEvent event) {
		actualizado = true;
		System.out.println("contrao:::" + contrato.getIdcontrato());
		System.out.println("upa:::" + contrato.getUpa().getIdupa());
		System.out.println("inmueble:::"
				+ contrato.getUpa().getInmueble().getIdinmueble());
		// detallecartera.setUpa(upa);

		detallecartera.getUpa().setIdupa(upa.getIdupa());
		detallecartera.getInmueble().setIdinmueble(
				upa.getInmueble().getIdinmueble());

	}

	public void onRowBuscarCartera(SelectEvent event) {
		System.out.println("cartera id:::" + cartera.getIdcartera());
		System.out.println("cartera num:::" + cartera.getNumero());
		contrato = detallecartera.getContrato();
	}

	public void pasoObjeto() {
		System.out.println("contrato:::" + contrato.getIdcontrato());
		System.out.println("uPA:::" + contrato.getUpa().getIdupa());
		System.out.println("inmueble:::"
				+ contrato.getUpa().getInmueble().getIdinmueble());
	}

	public void onRowSelectDetalleContrato(SelectEvent event) {
		actualizado = true;
		System.out.println("contrato:::" + contrato.getIdcontrato());
		System.out.println("uPA:::" + contrato.getUpa().getIdupa());
		System.out.println("inmueble:::"
				+ contrato.getUpa().getInmueble().getIdinmueble());
		detallecartera.setContrato(contrato);
		detallecartera.getUpa().setIdupa(contrato.getUpa().getIdupa());
		detallecartera.getInmueble().setIdinmueble(
				contrato.getUpa().getInmueble().getIdinmueble());

	}

	/****** Cabecera cartera *******/
	public void ActualizarMensajes() {
		System.out
				.println("entro metodo ActualizarMensajes::::::::::::::::::::");
		// resultadocartera = getCarteraService().obtenerUltimoCartera();
		// numCarteras = getCarteraService().obtenerNumeroRegistros();

	}

	public void registrarCartera() {
		desactivado = false;
		desactivadoEdicion = true;
		Date ahora = new Date();
		if (actualizado == true) {
			System.out.println("::::::entro actualizado");
			String usermodificador = userMB.getUsuariologueado().getNombres()
					+ " " + userMB.getUsuariologueado().getApellidopat();
			cartera.setFecmod(ahora);
			cartera.setUsrmod(usermodificador);
			getCarteraService().registrarCartera(cartera);
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Exito", "Se Actualizo correctamente el cartera.");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} else {
			System.out.println("::::::entro registro");
			String usercreador = userMB.getUsuariologueado().getNombres() + " "
					+ userMB.getUsuariologueado().getApellidopat();
			cartera.setUsrcre(usercreador);
			cartera.setFeccre(ahora);
			getCarteraService().registrarCartera(cartera);

			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Exito", "Se Registro correctamente el cartera.");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			System.out.println("okkkkkkkkkkkkkxxxx!!!");
		}
		cartera = getCarteraService().obtenerUltimoCartera();
		// para refrescar el valor en el panel
		ActualizarMensajes();
	}

	public void eliminarCartera() {
		getCarteraService().eliminarCartera(carteracapturado);

		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Exito", "Se eliminó la cartera correctamente.");

		FacesContext.getCurrentInstance().addMessage(null, msg);
		// limpiarCampos();
		ActualizarMensajes();
	}

	public void eliminarCarteraDetalle() {
		getCarteraService().eliminarCarteraDetalle(detallecarteracapturado);

		System.out.println("elimnoado de detalle acrtera::");
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Exito", "Se eliminó la detalle de  cartera correctamente.");

		FacesContext.getCurrentInstance().addMessage(null, msg);
		// limpiarCampos();
		detallecarteras = getCarteraService()
				.listarDetalleCarterasPorIdCartera(cartera.getIdcartera());
	}

	/********* REGISTRO PAGO *********/

	public void registrarPagoCuota() {

		Date ahora = new Date();
		
		if (actualizadopagocuota == true) {
			System.out.println("::::::entro actualizado pago cuota cabecera");
			String usermodificador = userMB.getUsuariologueado().getNombres()+ " " + userMB.getUsuariologueado().getApellidopat();
			cuota.setFecmod(ahora);
			cuota.setUsrmod(usermodificador);

			getCarteraService().registrarCuota(cuota);
			registrarPagoDetalleCuota();
			// para actualizar tabla
			cuotasxcontrato = getCarteraService().listarcuotasxcontrato(
					cuota.getContrato().getIdcontrato());
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Exito", "Se Actualizo correctamente el Pago.");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			actualizadopagocuota = false;
			
		} else {
			System.out.println("::::::entro registro pago cuota cabecera");
			System.out.println("id cuota suma::"+cuota.getIdcuota());
			System.out.println("###########################");
			
			System.out.println("monto cuota1 ::"+cuota.getContrato().getMontocuotasoles());
			double sumasoles;
			double sumadolar;
			double sumacalculadosoles=getCarteraService().obtenerMontoAcumuladoDetallecuota(cuota.getIdcuota());
			double sumacalculadodolar=getCarteraService().obtenerMontoAcumuladoDetallecuotadolar(cuota.getIdcuota());

			sumasoles =sumacalculadosoles + detallecuota.getMontosoles();
			sumadolar =sumacalculadodolar + detallecuota.getMontodolar();
			double montocuota =getCarteraService().obtenerContratoPorId(cuota.getContrato().getIdcontrato()).getMontocuotasoles();
			System.out.println("montocuota::"+montocuota);
			System.out.println("suma detallecuota::"+sumasoles);
			String usercreador = userMB.getUsuariologueado().getNombres() + " "	+ userMB.getUsuariologueado().getApellidopat();
			cuota.setUsrcre(usercreador);
			cuota.setFeccre(ahora);
			cuota.setMontoacumuladosoles(sumasoles);
			cuota.setMontoacumuladodolar(sumadolar);
			
//			if(sumasoles<=montocuota){
			System.out.println("######################");
			System.out.println("acumulado::"+cuota.getMontoacumuladosoles());
			System.out.println("numero cuota::"+cuota.getCuotanumero());
			//if(sumasoles<=montocuota){
				if(cuota.getMontoacumuladosoles()<=montocuota){
				System.out.println("Nooo cubre la cuota es menor");
				//getCarteraService().registrarCuota(cuota);
				if(sumasoles==montocuota){
					System.out.println("completo la cuota");
					cuota.setCancelado(true);
					getCarteraService().registrarCuota(cuota);
					registrarPagoDetalleCuota();
					//creacion de nueva cuota
					System.out.println("creacion nueva cuota");
					Cuota cuo=new Cuota();
					int numerodecuota= (int) (sumasoles/montocuota);
					cuo.setCuotanumero(numerodecuota);//creamos la 1ra cuota
						Contrato contra=new Contrato();
						contra.setIdcontrato(cuota.getContrato().getIdcontrato());
					cuo.setContrato(contra);
					cuo.setUsrcre(usercreador);
					cuo.setFeccre(ahora);
					cuo.setMontoacumuladosoles(0.0);
					cuo.setMontoacumuladodolar(0.0);
					cuo.setCuotanumero(cuota.getCuotanumero()+1);
					cuo.setCancelado(false);
					getCarteraService().registrarCuota(cuo);
					
					cuota=getCarteraService().obtenerUltimoCuota();//para mantenr actualizado la cuota
					cuotasxcontrato = getCarteraService().listarcuotasxcontrato(cuota.getContrato().getIdcontrato());
				}else{
					System.out.println("registramos nuevo detallecuota");
					cuota.setCancelado(false);
					getCarteraService().registrarCuota(cuota);
					registrarPagoDetalleCuota();
				}
				
				
			}else{
				
				System.out.println("sobrepasa en monto de la cuota");
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN,
						"Notificacion", "El monto ingresado Es mayor a la diferencia d ela cuota, ingrese el monto correcto!!!");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}
			
			// para actualizar tabla
			// cuotasxcontrato =
			// getCarteraService().listarcuotasxcontrato(cuota.getContrato().getIdcontrato());

			// FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
			// "Exito", "Se Registro correctamente el Pago de la cuota.");
			// FacesContext.getCurrentInstance().addMessage(null, msg);
			

		}
		// }
	}

	public void registrarPagoDetalleCuota() {

		Date ahora = new Date();
		if (actualizadopagodetallecuota == true) {
			System.out.println("::::::entro actualizadodetallecuota");
			String usermodificador = userMB.getUsuariologueado().getNombres()+ " " + userMB.getUsuariologueado().getApellidopat();
			detallecuota.setFecmod(ahora);
			detallecuota.setUsrmod(usermodificador);

			getCarteraService().registrarDetalleCuota(detallecuota);

			// para actualizar la tabla
			System.out.println("estamos en registrarPagoDetalleCuota");
			
//			detallecuotasxcontrato = getCarteraService().listardetallecuotasxcontrato(cuota.getContrato().getIdcontrato());
			detallecuotasxcontrato = getCarteraService().listardetallecuotasxcontratoycuota(cuota.getContrato().getIdcontrato(),cuota.getIdcuota());
			// cuotasxcontrato =
			// getCarteraService().listarcuotasxcontrato(cuota.getContrato().getIdcontrato());
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Exito", "Se Actualizo correctamente el Pago.");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} else {
			System.out.println("::::::entro registrodetallecuotaxx ");

			String usercreador = userMB.getUsuariologueado().getNombres() + " "	+ userMB.getUsuariologueado().getApellidopat();
			detallecuota.setUsrcre(usercreador);
			detallecuota.setFeccre(ahora);
			detallecuota.setCuota(cuota);
			getCarteraService().registrarDetalleCuota(detallecuota);
			
			
			
			// para actualizar la tabla
			System.out.println("estamos en registrarPagoDetalleCuota registro");
			detallecuotasxcontrato = getCarteraService().listardetallecuotasxcontrato(cuota.getContrato().getIdcontrato());
			// cuotasxcontrato =
			// getCarteraService().listarcuotasxcontrato(cuota.getContrato().getIdcontrato());
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Exito", "Se Registro correctamente el Pago de la cuota.");
			FacesContext.getCurrentInstance().addMessage(null, msg);

		}
	}

	public void onRowSelectRegistroCuotasPagos(SelectEvent event) {
		// actualizadodetallecuota = true;
		actualizadopagocuota = true;

	}

	public void listarComboContrato() {
		System.out.println("aca::");
		System.out.println("id carterax::" + cartera.getIdcartera());
		contratosxcartera = getCarteraService().listarContratosxcartera(
				cartera.getIdcartera());

	}

	public void cambioActualizado(SelectEvent event) {

		actualizado = true;
	}

	public void limpiarCampos() {
		desactivadoEdicion = false;
		cartera = null;
		detallecartera = null;
		detallecarteras.clear();
		desactivado = true;
		actualizado = false;
	}

	public void limpiarCamposDetalleCartera() {
		desactivadoEdicion = true;
		// cartera = null;
		detallecartera = null;
		actualizadodetalle = false;// nuevo registro
		// detallecarteras.clear();
		desactivado = false;
	}

	public void onRowSelect(SelectEvent event) {
		actualizadodetalle = true;

	}

	public void onRowSelectLista(SelectEvent event) {

		actualizado = true;

	}

	public ICarteraService getCarteraService() {
		return carteraService;
	}

	public void setCarteraService(ICarteraService carteraService) {
		this.carteraService = carteraService;
	}

	public UsuarioManagedBean getUserMB() {
		return userMB;
	}

	public void setUserMB(UsuarioManagedBean userMB) {
		this.userMB = userMB;
	}

	public List<Cartera> getCarteras() {
		carteras = getCarteraService().listarCarteras();
		return carteras;
	}

	public void setCarteras(List<Cartera> carteras) {
		this.carteras = carteras;
	}

	public Cartera getCartera() {
		if (cartera == null) {
			cartera = new Cartera();
		}
		return cartera;
	}

	public void setCartera(Cartera cartera) {
		this.cartera = cartera;
	}

	public Cartera getCarteracapturado() {
		return carteracapturado;
	}

	public void setCarteracapturado(Cartera carteracapturado) {
		this.carteracapturado = carteracapturado;
	}

	public Cartera getResultadocartera() {
		return resultadocartera;
	}

	public void setResultadocartera(Cartera resultadocartera) {
		this.resultadocartera = resultadocartera;
	}

	public int getNumCarteras() {
		return numCarteras;
	}

	public void setNumCarteras(int numCarteras) {
		this.numCarteras = numCarteras;
	}

	public boolean isDesactivado() {
		return desactivado;
	}

	public void setDesactivado(boolean desactivado) {
		this.desactivado = desactivado;
	}

	public List<Usuario> getUsuarios() {

		usuarios = getCarteraService().listarUsuarios();
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public Detallecartera getDetallecartera() {
		if (detallecartera == null) {
			detallecartera = new Detallecartera();
		}
		return detallecartera;
	}

	public void setDetallecartera(Detallecartera detallecartera) {
		this.detallecartera = detallecartera;
	}

	public Upa getUpa() {
		if (upa == null) {
			upa = new Upa();
		}
		return upa;
	}

	public void setUpa(Upa upa) {
		this.upa = upa;
	}

	public UsoManagedBean getUsoMB() {
		return usoMB;
	}

	public void setUsoMB(UsoManagedBean usoMB) {
		this.usoMB = usoMB;
	}

	public List<Detallecartera> getDetallecarteras() {

		return detallecarteras;
	}

	public void setDetallecarteras(List<Detallecartera> detallecarteras) {
		this.detallecarteras = detallecarteras;
	}

	public Contrato getContrato() {
		if (contrato == null) {
			contrato = new Contrato();
		}
		return contrato;
	}

	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
	}

	public String getUsuariocreador() {
		return usuariocreador;
	}

	public void setUsuariocreador(String usuariocreador) {
		this.usuariocreador = usuariocreador;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public List<Contrato> getContratos() {
		contratos = getCarteraService().listarContratos();
		return contratos;
	}

	public void setContratos(List<Contrato> contratos) {
		this.contratos = contratos;
	}

	public List<Uso> getUsos() {
		usos = getCarteraService().listarUsos();

		return usos;
	}

	public void setUsos(List<Uso> usos) {
		this.usos = usos;
	}

	public Detallecartera getDetallecarteracapturado() {
		return detallecarteracapturado;
	}

	public void setDetallecarteracapturado(
			Detallecartera detallecarteracapturado) {
		this.detallecarteracapturado = detallecarteracapturado;
	}

	public boolean isDesactivadoEdicion() {
		return desactivadoEdicion;
	}

	public void setDesactivadoEdicion(boolean desactivadoEdicion) {
		this.desactivadoEdicion = desactivadoEdicion;
	}

	public Cuota getCuota() {
		if (cuota == null) {
			System.out.println("se creo  cuota");
			cuota = new Cuota();
		}
		return cuota;
	}

	public void setCuota(Cuota cuota) {
		this.cuota = cuota;
	}

	public Cuota getCuotacapturado() {
		return cuotacapturado;
	}

	public void setCuotacapturado(Cuota cuotacapturado) {
		this.cuotacapturado = cuotacapturado;
	}

	public List<Cuota> getCuotas() {

		return cuotas;
	}

	public void setCuotas(List<Cuota> cuotas) {
		this.cuotas = cuotas;
	}

	public List<Cuota> getFiltrocuotas() {
		return filtrocuotas;
	}

	public void setFiltrocuotas(List<Cuota> filtrocuotas) {
		this.filtrocuotas = filtrocuotas;
	}

	public boolean isVertabla() {
		return vertabla;
	}

	public void setVertabla(boolean vertabla) {
		this.vertabla = vertabla;
	}

	public TipoCambioManagedBean getTipocambioMB() {
		return tipocambioMB;
	}

	public void setTipocambioMB(TipoCambioManagedBean tipocambioMB) {
		this.tipocambioMB = tipocambioMB;
	}

	public List<Detallecartera> getFiltrodetallecarteras() {
		return filtrodetallecarteras;
	}

	public void setFiltrodetallecarteras(
			List<Detallecartera> filtrodetallecarteras) {
		this.filtrodetallecarteras = filtrodetallecarteras;
	}

	public List<Contrato> getContratosdisponibles() {

		contratosdisponibles = getCarteraService().listarContratosdisponibles();
		return contratosdisponibles;
	}

	public void setContratosdisponibles(List<Contrato> contratosdisponibles) {
		this.contratosdisponibles = contratosdisponibles;
	}

	public List<Contrato> getContratosxcartera() {
		// System.out.println("aca::");
		// System.out.println("id carterax::"+cartera.getIdcartera());
		// contratosxcartera=
		// getCarteraService().listarContratosxcartera(cartera.getIdcartera());
		return contratosxcartera;
	}

	public void setContratosxcartera(List<Contrato> contratosxcartera) {
		this.contratosxcartera = contratosxcartera;
	}

	public List<Detallecuota> getDetallecuotas() {

		detallecuotas = getCarteraService().listarDetallecuotas();
		return detallecuotas;
	}

	public void setDetallecuotas(List<Detallecuota> detallecuotas) {
		this.detallecuotas = detallecuotas;
	}

	public Detallecuota getDetallecuota() {
		if (detallecuota == null) {
			detallecuota = new Detallecuota();
		}
		return detallecuota;
	}

	public void setDetallecuota(Detallecuota detallecuota) {
		this.detallecuota = detallecuota;
	}

	public boolean isVerpanel() {
		return verpanel;
	}

	public void setVerpanel(boolean verpanel) {
		this.verpanel = verpanel;
	}

	public List<Detallecuota> getDetallecuotasxcontrato() {
		return detallecuotasxcontrato;
	}

	public void setDetallecuotasxcontrato(
			List<Detallecuota> detallecuotasxcontrato) {
		this.detallecuotasxcontrato = detallecuotasxcontrato;
	}

	public List<Cuota> getCuotasxcontrato() {
		return cuotasxcontrato;
	}

	public void setCuotasxcontrato(List<Cuota> cuotasxcontrato) {
		this.cuotasxcontrato = cuotasxcontrato;
	}

	public int getNumerocuotaspagadas() {
		return numerocuotaspagadas;
	}

	public void setNumerocuotaspagadas(int numerocuotaspagadas) {
		this.numerocuotaspagadas = numerocuotaspagadas;
	}

	public int getNumerocuotasrestantes() {
		return numerocuotasrestantes;
	}

	public void setNumerocuotasrestantes(int numerocuotasrestantes) {
		this.numerocuotasrestantes = numerocuotasrestantes;
	}

	public double getMontocuotaspagadas() {
		return montocuotaspagadas;
	}

	public void setMontocuotaspagadas(double montocuotaspagadas) {
		this.montocuotaspagadas = montocuotaspagadas;
	}

	public int getValornumcuotaspagadas() {
		return valornumcuotaspagadas;
	}

	public void setValornumcuotaspagadas(int valornumcuotaspagadas) {
		this.valornumcuotaspagadas = valornumcuotaspagadas;
	}

	public double getValorultimacuotaacumulada() {
		return valorultimacuotaacumulada;
	}

	public void setValorultimacuotaacumulada(double valorultimacuotaacumulada) {
		this.valorultimacuotaacumulada = valorultimacuotaacumulada;
	}

	public int getValornumcuotasrestantes() {
		return valornumcuotasrestantes;
	}

	public void setValornumcuotasrestantes(int valornumcuotasrestantes) {
		this.valornumcuotasrestantes = valornumcuotasrestantes;
	}

	public double getValorultimacuotarestante() {
		return valorultimacuotarestante;
	}

	public void setValorultimacuotarestante(double valorultimacuotarestante) {
		this.valorultimacuotarestante = valorultimacuotarestante;
	}

	public double getMontocuotaspagadasdolar() {
		return montocuotaspagadasdolar;
	}

	public void setMontocuotaspagadasdolar(double montocuotaspagadasdolar) {
		this.montocuotaspagadasdolar = montocuotaspagadasdolar;
	}

	public List<Detallecuota> getDetallecuotaxcontrato() {
		return detallecuotaxcontrato;
	}

	public void setDetallecuotaxcontrato(List<Detallecuota> detallecuotaxcontrato) {
		this.detallecuotaxcontrato = detallecuotaxcontrato;
	}

	// public String getInquilinofiltrado() {
	// return inquilinofiltrado;
	// }
	//
	// public void setInquilinofiltrado(String inquilinofiltrado) {
	// this.inquilinofiltrado = inquilinofiltrado;
	// }

	// public SelectItem[] getFiltrousuarios() {
	//
	//
	// return filtrousuarios;
	// }
	//
	// public void setFiltrousuarios(SelectItem[] filtrousuarios) {
	// this.filtrousuarios = filtrousuarios;
	// }

}

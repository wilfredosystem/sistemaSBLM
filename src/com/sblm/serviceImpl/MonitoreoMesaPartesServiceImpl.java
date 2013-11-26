package com.sblm.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sblm.dao.IAuditoriaDAO;
import com.sblm.dao.IMonitoreoMesaPartesDAO;
import com.sblm.dao.INotificacionesDAO;
import com.sblm.dao.IUsuarioDAO;
import com.sblm.model.Archivodocumento;
import com.sblm.model.Auditoria;
import com.sblm.model.Extensionarchivo;
import com.sblm.model.Modulo;
import com.sblm.model.Pagina;
import com.sblm.model.Perfilusuario;
import com.sblm.model.Tipocambio;
import com.sblm.model.Usuario;
import com.sblm.modelMP.Documento;
import com.sblm.modelMP.Flujodoc;
import com.sblm.service.IAuditoriaService;
import com.sblm.service.IMonitoreoMesaPartesService;
import com.sblm.service.INotificacionesService;
import com.sblm.service.IUsuarioService;

@Transactional(readOnly = true)
@Service(value="panelDocumentoMesaPartesServiceImpl")
public class MonitoreoMesaPartesServiceImpl implements IMonitoreoMesaPartesService{

	@Autowired
	private IMonitoreoMesaPartesDAO monitoreoMesaPartesDAO;

	@Override
	public List<com.sblm.model.Documento> listarDocumentosRegistrados(int mes) { 
		// TODO Auto-generated method stub
		return monitoreoMesaPartesDAO.listarDocumentosRegistrados(mes);
	}
	@Override
	public List<com.sblm.model.Documento> listaFiltroDocumentos(String resp) {
		// TODO Auto-generated method stub
		return monitoreoMesaPartesDAO.listaFiltroDocumentos(resp);
	}
	
	@Override
	public List<com.sblm.model.Documento> listaFiltroDocumentosMes(String resp) {
		// TODO Auto-generated method stub
		return monitoreoMesaPartesDAO.listaFiltroDocumentosMes(resp);
	}
	
//	@Override
//	public List<com.sblm.model.Documento> listaDocumentosDespachados() {
//		// TODO Auto-generated method stub
//		return monitoreoMesaPartesDAO.listaDocumentosDespachados();
//	}
//	
//	@Override
//	public List<com.sblm.model.Documento> listaDocumentosRechazados() {
//		// TODO Auto-generated method stub
//		return monitoreoMesaPartesDAO.listaDocumentosRechazados();
//	}
//	@Override
//	public List<com.sblm.model.Documento> listaDocumentosPendientes() {
//		// TODO Auto-generated method stub
//		return monitoreoMesaPartesDAO.listaDocumentosPendientes();
//	}
	@Override
	public Object countExternalDB() {
		// TODO Auto-generated method stub
		return monitoreoMesaPartesDAO.countExternalDB();
	}

	@Override
	public Object countInternalDB() {
		// TODO Auto-generated method stub
		return monitoreoMesaPartesDAO.countInternalDB();
	}

	@Override
	public List<Flujodoc> getNewInserts(int val) {
		// TODO Auto-generated method stub
		return monitoreoMesaPartesDAO.getNewInserts(val);
	}

	@Override
	public void save(com.sblm.model.Documento doc) {
		// TODO Auto-generated method stub
			
		monitoreoMesaPartesDAO.save(doc);
	}
	
	@Override
	public Object countDocumentosFiltrados(String resp) {
		return monitoreoMesaPartesDAO.countDocumentosFiltrados(resp);
	}
	@Override
	public Object countDocumentosFiltradosMes(String resp,int mes) {
		return monitoreoMesaPartesDAO.countDocumentosFiltradosMes(resp, mes);
	}
	
	@Override
	public Object countPendientes() {
		return monitoreoMesaPartesDAO.countPendientes();
	}
	@Override
	public Object countAtendidos() {
		// TODO Auto-generated method stub
		return monitoreoMesaPartesDAO.countAtendidos();	}
//	@Override
//	public Object countRechazados() {
//		// TODO Auto-generated method stub
//		return monitoreoMesaPartesDAO.countRechazados();	}
//	
	@Override
	public Object countPendientesMes(int mes) {
		return monitoreoMesaPartesDAO.countPendientesMes(mes);
	}
	@Override
	public Object countAtendidosMes(int mes) {
		return monitoreoMesaPartesDAO.countAtendidosMes(mes);
	}
	@Override
	public Object countRechazadosMes() {
		return monitoreoMesaPartesDAO.countRechazadosMes();
	}
	
	@Override
	public void actualizarEstadoToAtendido(int iddoc, List<Usuario> listaUsuarioSeleccionados) {
		monitoreoMesaPartesDAO.actualizarEstadoToAtendido(iddoc,listaUsuarioSeleccionados);
		
	}

	@Override
	public List<com.sblm.model.Documento> listarDocumentosAtendidos(int mes) {
		return monitoreoMesaPartesDAO.listarDocumentosAtendidos(mes);
	}
	@Override
	public List<com.sblm.model.Documento> listarDocumentosAtendidos() {
		return monitoreoMesaPartesDAO.listarDocumentosAtendidos();
	}
	@Override
	public List<com.sblm.model.Documento> listarDocumentosAtendidosPorMes(int mes) {
		return monitoreoMesaPartesDAO.listarDocumentosAtendidosPorMes(mes);
	}
	
	@Override
	public String obtenerUltimoDespacho() {
		return monitoreoMesaPartesDAO.obtenerUltimoDespacho();
	}
	
	@Override
	public Usuario getDirectorDGAI() {
		return monitoreoMesaPartesDAO.getDirectorDGAI();
	}

	@Override
	public List<Extensionarchivo> getListaExtensionArhivos() {
		return monitoreoMesaPartesDAO.getListaExtensionArhivos();
	}

	@Override
	public void agregarArchivosDocumento(Archivodocumento archivodocu) {
		
		monitoreoMesaPartesDAO.agregarArchivosDocumento(archivodocu);
		
	}
	@Override
	public List<com.sblm.model.Documento> listarDocumentosRegistrados() {
		return monitoreoMesaPartesDAO.listarDocumentosRegistrados();
	}
	@Override
	public Object countPendientesMes() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Object countAtendidosMes() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<com.sblm.model.Documento> getListLocal() {
		// TODO Auto-generated method stub
		return monitoreoMesaPartesDAO.getListLocal();
	}
	@Override
	public void eliminarDocumentoNoEncontrado(String codigodocumento) {
		monitoreoMesaPartesDAO.eliminarDocumentoNoEncontrado(codigodocumento);
		
	}
	



}

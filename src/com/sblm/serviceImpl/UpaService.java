package com.sblm.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sblm.dao.IUpaDAO;
import com.sblm.model.Inmueble;
import com.sblm.model.Materialpredominante;
import com.sblm.model.Mep;
import com.sblm.model.Tipointerior;
import com.sblm.model.Tipotitularidad;
import com.sblm.model.Tipovia;
import com.sblm.model.Titularidad;
import com.sblm.model.Upa;
import com.sblm.model.Uso;
import com.sblm.model.Valuacion;
import com.sblm.service.IUpaService;
@Transactional(readOnly = true)
@Service(value="upaService")
public class UpaService  implements IUpaService{
	@Autowired
	private IUpaDAO upaDAO;

	@Transactional(readOnly = false)
	@Override
	public void registrarUpa(Upa upa) {
		getUpaDAO().registrarUpa(upa); 
	}
	@Transactional(readOnly = false)
	@Override
	public void actualizarUpa(Upa upa) {
		getUpaDAO().actualizarUpa(upa); 
		
	}
	@Transactional(readOnly = false)
	@Override
	public void eliminarUpa(Upa upa) {
		getUpaDAO().eliminarUpa(upa); 
		
	}

	@Override
	public List<Upa> listarUpas() {
		return getUpaDAO().listarUpas();
	}

	@Override
	public Upa obtenerUltimoUpa() {
		return getUpaDAO().obtenerUltimoUpa();
	}
	@Override
	public Upa validarRepetido(String numreg) {
		return getUpaDAO().validarRepetido(numreg);
	}
	@Override
	public Upa obtenerUpaPorId(int id) {
		return getUpaDAO().obtenerUpaPorId(id);
	}
	@Override
	public Inmueble obtenerInmueblePorParametro(String valor) {
		return getUpaDAO().obtenerInmueblePorParametro(valor);
	}
	
	@Override
	public int obtenerNumeroRegistros() {
		return getUpaDAO().obtenerNumeroRegistros();
	}

	@Override
	public List<Tipovia> listarTipoVia() {
		return getUpaDAO().listarTipoVia();
	}

	
	@Override
	public List<Valuacion> listarValuacion() {
		return getUpaDAO().listarValuacion();
	}

	@Override
	public List<Uso> listarUso() {
		return getUpaDAO().listarUso();
	}
	@Override
	public List<Mep> listarMep() {
		return getUpaDAO().listarMep();
	}
	
	@Override
	public List<Titularidad> listarTitularidad() {
		return getUpaDAO().listarTitularidad();
	}

	@Override
	public List<Tipotitularidad> listarTipoTitularidad() {
		return getUpaDAO().listarTipoTitularidad();
	}

	@Override
	public List<Materialpredominante> listarMaterialPredominante() {
		return getUpaDAO().listarMaterialPredominante();
	}

	@Override
	public List<Tipointerior> listarTipoInterior() {
		return getUpaDAO().listarTipoInterior();
	}

	@Override
	public List<String> listarDepartamentos() {
		// TODO Auto-generated method stub
		return getUpaDAO().listarDepartamentos();
	}
	@Override
	public List<String> listaInmuebles() {
		// TODO Auto-generated method stub
		return getUpaDAO().listaInmuebles();
	}
	
	@Override
	public List<String> listarProvincias(String dpto) {
		// TODO Auto-generated method stub
		return getUpaDAO().listarProvincias(dpto);
	}

	@Override
	public List<String> listarDistritos(String prov) {
		// TODO Auto-generated method stub
		return getUpaDAO().listarDistritos(prov);
	}

	@Override
	public List<String> listarUbigeoPorDistrito(String prov, String dis) {
		// TODO Auto-generated method stub
		return getUpaDAO().listarUbigeoPorDistrito(prov, dis);
	}

	@Override
	public List<String> listarUbigeos() {
		// TODO Auto-generated method stub
		return getUpaDAO().listarUbigeos();
	}

	@Override
	public List<String> listarDptoPorUbigeo(String Ubigeo) {
		// TODO Auto-generated method stub
		return getUpaDAO().listarDptoPorUbigeo(Ubigeo);
	}

	@Override
	public List<String> listarProvinciaPorUbigeo(String Ubigeo) {
		// TODO Auto-generated method stub
		return getUpaDAO().listarProvinciaPorUbigeo(Ubigeo);
	}

	@Override
	public List<String> listarDistritoPorUbigeo(String Ubigeo) {
		// TODO Auto-generated method stub
		return getUpaDAO().listarDistritoPorUbigeo(Ubigeo);
	}
	public IUpaDAO getUpaDAO() {
		return upaDAO;
	}
	public void setUpaDAO(IUpaDAO upaDAO) {
		this.upaDAO = upaDAO;
	}

}

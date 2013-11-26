package com.sblm.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sblm.dao.IInmuebleDAO;
import com.sblm.model.Inmueble;
import com.sblm.model.Materialpredominante;
import com.sblm.model.Tipointerior;
import com.sblm.model.Tipotitularidad;
import com.sblm.model.Tipovia;
import com.sblm.model.Titularidad;
import com.sblm.model.Ubigeo;
import com.sblm.service.IInmuebleService;
@Transactional(readOnly = true)
@Service(value="inmuebleService")
public class InmuebleService implements IInmuebleService{
	@Autowired
	private IInmuebleDAO inmuebleDAO;

	@Transactional(readOnly = false)
	@Override
	public void registrarInmueble(Inmueble inmueble) {
		getInmuebleDAO().registrarInmueble(inmueble);
		
	}
	@Transactional(readOnly = false)
	@Override
	public void actualizarInmueble(Inmueble inmueble) {
		getInmuebleDAO().actualizarInmueble(inmueble);
		
	}
	@Transactional(readOnly = false)
	@Override
	public void eliminarInmueble(Inmueble inmueble) {
		getInmuebleDAO().eliminarInmueble(inmueble);
		
	}

	@Override
	public List<Inmueble> listarInmuebles() {
		return getInmuebleDAO().listarInmuebles();
	}

	@Override
	public Inmueble obtenerUltimoInmueble() {
		return getInmuebleDAO().obtenerUltimoInmueble();
	}

	@Override
	public Inmueble obtenerInmueblePorId(int id) {
		return getInmuebleDAO().obtenerInmueblePorId(id);
	}

	@Override
	public int obtenerNumeroRegistros() {
		return getInmuebleDAO().obtenerNumeroRegistros();
	}
	@Override
	public Inmueble validarRepetido(String numreg) {
		return getInmuebleDAO().validarRepetido(numreg);
	}
	public IInmuebleDAO getInmuebleDAO() {
		return inmuebleDAO;
	}
	public void setInmuebleDAO(IInmuebleDAO inmuebleDAO) {
		this.inmuebleDAO = inmuebleDAO;
	}

	
	/**tipo via**/
	@Override
	public List<Tipovia> listarTipoVia() {
		return getInmuebleDAO().listarTipoVia();
	}
	
	/** listarTitularidad**/
	@Override
	public List<Titularidad> listarTitularidad() {
		return getInmuebleDAO().listarTitularidad();
	}
	/** listarTipo Titularidad**/
	@Override
	public List<Tipotitularidad> listarTipoTitularidad() {
		return getInmuebleDAO().listarTipoTitularidad();
	}
	/** listar Materialpredominante**/
	@Override
	public List<Materialpredominante> listarMaterialPredominante() {
		return getInmuebleDAO().listarMaterialPredominante();
	}
	/** listar TipoInterior**/
	@Override
	public List<Tipointerior> listarTipoInterior() {
		return getInmuebleDAO().listarTipoInterior();
	}
	
	/**listado departamentos*/
	@Override
	public List<String> listarDepartamentos() {
		return getInmuebleDAO().listarDepartamentos();
	}
	/**listado provincias*/
	@Override
	public List<String> listarProvincias(String dpto) {
		return getInmuebleDAO().listarProvincias(dpto);
	}
	/**listado distritos*/
	@Override
	public List<String> listarDistritos(String prov) {
		return getInmuebleDAO().listarDistritos(prov);
	}
	/**listado ubigeo por distrito**/
	@Override
	public List<String> listarUbigeoPorDistrito(String prov, String dis) {
		return getInmuebleDAO().listarUbigeoPorDistrito(prov, dis);
	}
	/**listado ubigeos*/
	@Override
	public List<String> listarUbigeos() {
		return getInmuebleDAO().listarUbigeos();
	}
	/**listado DptoPorUbigeo*/
	@Override
	public List<String> listarDptoPorUbigeo(String Ubigeo) {
		return getInmuebleDAO().listarDptoPorUbigeo(Ubigeo);
	}
	/**listado ProvinciaPorUbigeo*/
	@Override
	public List<String> listarProvinciaPorUbigeo(String Ubigeo) {
		return getInmuebleDAO().listarProvinciaPorUbigeo(Ubigeo);
	}
	/**listado DistritoPorUbigeo*/
	@Override
	public List<String> listarDistritoPorUbigeo(String Ubigeo) {
		return getInmuebleDAO().listarDistritoPorUbigeo(Ubigeo);
	}
}

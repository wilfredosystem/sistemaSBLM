package com.sblm.service;

import java.util.List;

import com.sblm.model.Inmueble;
import com.sblm.model.Materialpredominante;
import com.sblm.model.Tipointerior;
import com.sblm.model.Tipotitularidad;
import com.sblm.model.Tipovia;
import com.sblm.model.Titularidad;
import com.sblm.model.Ubigeo;

public interface IInmuebleService {

	public void registrarInmueble(Inmueble inmueble);
	public void actualizarInmueble(Inmueble inmueble);

	public void eliminarInmueble(Inmueble inmueble);
	public List<Inmueble> listarInmuebles();
	
	public Inmueble obtenerUltimoInmueble();

	public Inmueble obtenerInmueblePorId(int id);
	int obtenerNumeroRegistros();
	/**lista tipo via**/
	List<Tipovia> listarTipoVia();
	
	/**lista Titularidad**/
	List<Titularidad> listarTitularidad();
	/**lista tipo Titularidad**/
	List<Tipotitularidad> listarTipoTitularidad();
	/**lista tipo Materialpredominante**/
	List<Materialpredominante> listarMaterialPredominante();
	/**lista Tipointerior**/
	List<Tipointerior> listarTipoInterior();
	/**listado departamento**/
	List<String> listarDepartamentos();
	/**listado provincias**/
	List<String> listarProvincias(String dpto);
	/**listado distritos**/
	List<String> listarDistritos(String prov);
	/**listado ubigeo por distrito**/
	List<String> listarUbigeoPorDistrito(String prov,String dis);
	/**listado ubigeos**/
	List<String> listarUbigeos();
	/**listado Valores Por Ubigeo**/
	List<String> listarDptoPorUbigeo(String Ubigeo);
	List<String> listarProvinciaPorUbigeo(String Ubigeo);
	List<String> listarDistritoPorUbigeo(String Ubigeo);
	Inmueble validarRepetido(String numreg);
	
	
	
	
	
	
}

package com.sblm.dao;

import java.util.List;

import com.sblm.model.Inmueble;
import com.sblm.model.Materialpredominante;
import com.sblm.model.Tipointerior;
import com.sblm.model.Tipotitularidad;
import com.sblm.model.Tipovia;
import com.sblm.model.Titularidad;
import com.sblm.model.Ubigeo;

public interface IInmuebleDAO {
	public void registrarInmueble(Inmueble inmueble);
	public void actualizarInmueble(Inmueble inmueble);

	public void eliminarInmueble(Inmueble inmueble);
	public List<Inmueble> listarInmuebles();
	
	public Inmueble obtenerUltimoInmueble();

	public Inmueble obtenerInmueblePorId(int id);
	int obtenerNumeroRegistros();
	/**listar tipo via**/
	List<Tipovia> listarTipoVia();
	
	/**listar Titularidad**/
	List<Titularidad> listarTitularidad();
	/**listar TipoTitularidad**/
	List<Tipotitularidad> listarTipoTitularidad();
	/**listar Materialpredominante**/
	List<Materialpredominante> listarMaterialPredominante();
	/**listar Tipointerior**/
	List<Tipointerior> listarTipoInterior();
	/**listado departamentos**/
	List<String> listarDepartamentos();
	/**listado provincias**/
	List<String> listarProvincias(String dpto);
	/**listado Distritos**/
	List<String> listarDistritos(String prov);
	/**listado ubigeo por distrito**/
	List<String> listarUbigeoPorDistrito(String prov,String dist);
	/**listado ubigeos**/
	List<String> listarUbigeos();
	/**listado datos por ubigeos**/
	List<String> listarDistritoPorUbigeo(String ubigeo);
	List<String> listarProvinciaPorUbigeo(String ubigeo);
	List<String> listarDptoPorUbigeo(String ubigeo);
	Inmueble validarRepetido(String numreg);
	
	
	
	
}

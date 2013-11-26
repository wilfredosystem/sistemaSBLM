package com.sblm.dao;

import java.util.List;

import com.sblm.model.Califica;
import com.sblm.model.Inquilino;
import com.sblm.model.Tipoentidad;
import com.sblm.model.Tipopersona;

public interface IInquilinoDAO {
	public void registrarInquilino(Inquilino inquilino);
	public void actualizarInquilino(Inquilino inquilino);

	public void eliminarInquilino(Inquilino inquilino);
	public List<Inquilino> listarInquilinos();
	
	public Inquilino obtenerUltimoInquilino();

	public Inquilino obtenerInquilinoPorId(int id);
	int obtenerNumeroRegistros();
	/**lista tipoentidad**/
	List<Tipoentidad> listarTipoEntidad();
	/**lista tipoentidad**/
	List<Tipopersona> listarTipoPersona();
	Inquilino validarRepetido(String numreg);
	List<Califica> listarCalificacion();
	void registrarCalificacion(Califica calificacion);
	List<Califica> listarCalificacion(Inquilino inqui);
	void eliminarCalificacion(Inquilino inquilino);
}

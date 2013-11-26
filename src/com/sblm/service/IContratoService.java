package com.sblm.service;

import java.util.List;

import com.sblm.model.Cliente;
import com.sblm.model.Contrato;
import com.sblm.model.Cuentabancaria;
import com.sblm.model.Inmueble;
import com.sblm.model.Inquilino;
import com.sblm.model.Institucion;
import com.sblm.model.Representante;
import com.sblm.model.Upa;
import com.sblm.model.Uso;
import com.sblm.model.Usuario;

public interface IContratoService {

	List<Uso> getListaUsos();
	public void registrarContrato(Contrato contrato);
	List<Upa> getListaUpa();
	List<Inmueble> getListaInmueble();
	List<Inquilino> getListaInquilino();
	List<Upa> buscarUpasXInmueble(int idinmueble);
	List<Contrato> getListaContrato();
	List<Institucion> getListaInstitucion();
	List<Representante> getListaRepresentante();
	void cancelarContrato(int idcontratoGlobal);
	List<Cliente> getListaCliente();
	List<Usuario> obtenerUsuarios();
	void enviarNotificaciónPersonalizable(String contenidoMensajePersonalizado,
			int idusuariodestino);
	Double getValorTipoCambio();
	List<Cuentabancaria> getCtasBancarias();

}

package com.sblm.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sblm.dao.IContratoDAO;
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
import com.sblm.service.IContratoService;

@Transactional(readOnly = true)
@Service(value="contratoService")
public class ContratoService implements IContratoService{
	@Autowired
	private IContratoDAO contratoDAO;
	
	@Transactional(readOnly = false)
	@Override
	public void registrarContrato(Contrato contrato) {
		getContratoDAO().registrarContrato(contrato);
	}

	public IContratoDAO getContratoDAO() {
		return contratoDAO;
	}

	public void setContratoDAO(IContratoDAO contratoDAO) {
		this.contratoDAO = contratoDAO;
	}

	@Override
	public List<Uso> getListaUsos() {
		return contratoDAO.getListaUsos();
	}

	@Override
	public List<Upa> getListaUpa() {
		return contratoDAO.getListaUpa();
	}

	@Override
	public List<Inmueble> getListaInmueble() {
		return contratoDAO.getListaInmueble();
	}

	@Override
	public List<Inquilino> getListaInquilino() {
		return contratoDAO.getListaInquilino();
	}

	@Override
	public List<Upa> buscarUpasXInmueble(int idinmueble) {
		return contratoDAO.buscarUpasXInmueble(idinmueble);
	}

	@Override
	public List<Contrato> getListaContrato() {
		return contratoDAO.getListaContrato();
	}

	@Override
	public List<Institucion> getListaInstitucion() {
		return contratoDAO.getListaInstitucion();
	}


	@Override
	public List<Representante> getListaRepresentante() {
		// TODO Auto-generated method stub
		return contratoDAO.getListaRepresentante();
	}

	@Override
	public void cancelarContrato(int idcontratoGlobal) {
		contratoDAO.cancelarContrato(idcontratoGlobal);
		
	}

	@Override
	public List<Cliente> getListaCliente() {
		// TODO Auto-generated method stub
		return contratoDAO.getListaCliente();
	}

	@Override
	public List<Usuario> obtenerUsuarios() {
		// TODO Auto-generated method stub
		return contratoDAO.obtenerUsuarios();
	}

	@Override
	public void enviarNotificaciónPersonalizable(
			String contenidoMensajePersonalizado, int idusuariodestino) {
		contratoDAO.enviarNotificaciónPersonalizable(contenidoMensajePersonalizado,idusuariodestino);
		
	}

	@Override
	public Double getValorTipoCambio() {
		// TODO Auto-generated method stub
		return contratoDAO.getValorTipoCambio();
	}

	@Override
	public List<Cuentabancaria> getCtasBancarias() {
		// TODO Auto-generated method stub
		return contratoDAO.getCtasBancarias();
	}

	

}

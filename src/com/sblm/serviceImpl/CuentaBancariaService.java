package com.sblm.serviceImpl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sblm.dao.ICuentaBancariaDAO;
import com.sblm.model.Banco;
import com.sblm.model.Cuentabancaria;
import com.sblm.service.ICuentaBancariaService;

@Transactional(readOnly = true)
@Service(value="cuentabancariaService")
public class CuentaBancariaService implements ICuentaBancariaService{
	@Autowired
	private ICuentaBancariaDAO cuentabancariaDAO;
	


	@Transactional(readOnly = false)
	@Override
	public void grabarCuentaBancaria(Cuentabancaria cuentabancaria) {
		cuentabancariaDAO.grabarCuentaBancaria(cuentabancaria);
		
	}

	@Override
	public List<Cuentabancaria> obtenerTodasCuentasBancarias() {
		
		return cuentabancariaDAO.obtenerTodasCuentasBancarias();
	}

	@Transactional(readOnly = false)
	@Override
	public void eliminarCuentabancaria(Cuentabancaria cuentabancaria) {
		cuentabancariaDAO.eliminarCuentabancaria(cuentabancaria);
		
	}

	@Override
	public int nroCuentasBancarias() {
		
		return cuentabancariaDAO.nroCuentasBancarias();
	}

	@Override
	public List<Banco> listBancos() {
		// TODO Auto-generated method stub
		return cuentabancariaDAO.listBancos();
	}



}

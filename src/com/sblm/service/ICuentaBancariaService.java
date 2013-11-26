package com.sblm.service;

import java.util.List;

import com.sblm.model.Banco;

public interface ICuentaBancariaService {

	List<com.sblm.model.Cuentabancaria> obtenerTodasCuentasBancarias();

	void grabarCuentaBancaria(com.sblm.model.Cuentabancaria cuentabancaria);

	int nroCuentasBancarias();

	void eliminarCuentabancaria(com.sblm.model.Cuentabancaria cuentabancaria);

	List<Banco> listBancos();

}

package com.sblm.dao;

import java.util.List;

import com.sblm.model.Banco;
import com.sblm.model.Cuentabancaria;

public interface ICuentaBancariaDAO {

	void grabarCuentaBancaria(Cuentabancaria banco);

	List<Cuentabancaria> obtenerTodasCuentasBancarias();

	void eliminarCuentabancaria(Cuentabancaria banco);

	int nroCuentasBancarias();

	List<Banco> listBancos();

}

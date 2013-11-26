package com.sblm.dao;

import java.util.List;

import com.sblm.model.Banco;
import com.sblm.model.Cuentabancaria;

public interface IBancoServiceDAO {

	void grabarbanco(Banco banco);

	List<Banco> obtenerTodosBancos();

	void eliminarBanco(Banco banco);

	int nroBancos();




}

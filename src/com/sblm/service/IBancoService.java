package com.sblm.service;

import java.util.List;

import com.sblm.model.Banco;

public interface IBancoService {

	List<Banco> obtenerTodosBancos();

	void grabarBanco(Banco banco);

	int nroBancos();

	void eliminarBanco(Banco banco);

}

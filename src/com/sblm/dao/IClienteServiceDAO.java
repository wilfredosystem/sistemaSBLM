package com.sblm.dao;

import java.util.List;
import com.sblm.model.Cliente;

public interface IClienteServiceDAO {

	void grabarCliente(Cliente cliente);

	List<Cliente> obtenerTodosClientes();

	void eliminarCliente(Cliente cliente);

	int nroClientes();

}

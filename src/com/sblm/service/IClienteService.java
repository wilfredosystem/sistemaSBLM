package com.sblm.service;

import java.util.List;
import com.sblm.model.Cliente;

public interface IClienteService {

	public void grabarCliente(Cliente cliente);

	public List<Cliente> obtenerTodosClientes();

	public void eliminarCliente(Cliente cliente);

	public int nroClientes();

}

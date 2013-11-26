package com.sblm.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sblm.dao.IClienteServiceDAO;
import com.sblm.model.Cliente;
import com.sblm.service.IClienteService;

@Transactional(readOnly = true)
@Service(value="clienteService")
public class ClienteService implements IClienteService{
	@Autowired
	private IClienteServiceDAO clienteDAO;
	


	@Transactional(readOnly = false)
	@Override
	public void grabarCliente(Cliente cliente) {
		clienteDAO.grabarCliente(cliente);
		
	}

	@Override
	public List<Cliente> obtenerTodosClientes() {
		
		return clienteDAO.obtenerTodosClientes();
	}

	@Transactional(readOnly = false)
	@Override
	public void eliminarCliente(Cliente cliente) {
		clienteDAO.eliminarCliente(cliente);
		
	}

	@Override
	public int nroClientes() {
		
		return clienteDAO.nroClientes();
	}



}

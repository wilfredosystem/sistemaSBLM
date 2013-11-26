package com.sblm.serviceImpl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sblm.dao.IBancoServiceDAO;
import com.sblm.model.Banco;
import com.sblm.service.IBancoService;

@Transactional(readOnly = true)
@Service(value="bancoService")
public class BancoService implements IBancoService{
	@Autowired
	private IBancoServiceDAO bancoDAO;
	


	@Transactional(readOnly = false)
	@Override
	public void grabarBanco(Banco banco) {
		bancoDAO.grabarbanco(banco);
		
	}

	@Override
	public List<Banco> obtenerTodosBancos() {
		
		return bancoDAO.obtenerTodosBancos();
	}

	@Transactional(readOnly = false)
	@Override
	public void eliminarBanco(Banco banco) {
		bancoDAO.eliminarBanco(banco);
		
	}

	@Override
	public int nroBancos() {
		
		return bancoDAO.nroBancos();
	}



}

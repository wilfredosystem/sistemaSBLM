package com.sblm.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sblm.dao.IUsoDAO;
import com.sblm.model.Uso;
import com.sblm.service.IUsoService;
@Transactional(readOnly = true)
@Service(value="usoService")
public class UsoService implements IUsoService{
	@Autowired
	private IUsoDAO usoDAO;
	
	@Transactional(readOnly = false)
	@Override
	public void registrarUso(Uso uso) {
		getUsoDAO().registrarUso(uso);
			
	}
	@Transactional(readOnly = false)
	@Override
	public void actualizarUso(Uso uso) {
		// TODO Auto-generated method stub
		
	}
	@Transactional(readOnly = false)
	@Override
	public void eliminarUso(Uso uso) {
		 getUsoDAO().eliminarUso(uso);
		
	}

	@Override
	public List<Uso> listarUsos() {
		return getUsoDAO().listarUsos();
	}

	@Override
	public Uso obtenerUltimoUso() {
		return getUsoDAO().obtenerUltimoUso();
	}
	@Override
	public int obtenerNumeroRegistros() {
		return getUsoDAO().obtenerNumeroRegistros();
	}
	
	@Override
	public Uso obtenerUsoPorId(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	public IUsoDAO getUsoDAO() {
		return usoDAO;
	}
	public void setUsoDAO(IUsoDAO usoDAO) {
		this.usoDAO = usoDAO;
	}

}

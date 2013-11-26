package com.sblm.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sblm.dao.IIgvDAO;
import com.sblm.dao.IRepresentanteServiceDAO;
import com.sblm.dao.IUsoDAO;
import com.sblm.model.Igv;
import com.sblm.model.Representante;
import com.sblm.service.IIgvService;
import com.sblm.service.IRepresentanteService;

@Transactional(readOnly = true)
@Service(value="representanteService")
public class RepresentanteService implements IRepresentanteService{
	@Autowired
	private IRepresentanteServiceDAO representanteDAO;
	


	@Transactional(readOnly = false)
	@Override
	public void grabarRepresentante(Representante representante) {
		representanteDAO.grabarRepresentante(representante);
		
	}



	@Override
	public List<Representante> obtenerTodosRepresentantes() {
		
		return representanteDAO.obtenerTodosRepresentantes();
	}



	@Override
	public void eliminarRepresentante(Representante representante) {
		representanteDAO.eliminarRepresentante(representante);
		
	}



	@Override
	public int nrorepresentantes() {
		// TODO Auto-generated method stub
		return representanteDAO.nrorepresentantes();
	}



}

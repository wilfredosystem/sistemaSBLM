package com.sblm.serviceImpl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sblm.dao.IInstitucionServiceDAO;
import com.sblm.model.Institucion;
import com.sblm.service.IInstitucionService;

@Transactional(readOnly = true)
@Service(value="institucionService")
public class InstitucionService implements IInstitucionService{
	@Autowired
	private IInstitucionServiceDAO institucionDAO;
	


	@Transactional(readOnly = false)
	@Override
	public void grabarInstitucion(Institucion institucion) {
		institucionDAO.grabarInstitucion(institucion);
		
	}

	@Override
	public List<Institucion> obtenerTodosInstituciones() {
		
		return institucionDAO.obtenerTodosInstituciones();
	}

	@Transactional(readOnly = false)
	@Override
	public void eliminarInstitucion(Institucion institucion) {
		institucionDAO.eliminarInstitucion(institucion);
		
	}

	@Override
	public int nroInstituciones() {
		
		return institucionDAO.nroInstituciones();
	}



}

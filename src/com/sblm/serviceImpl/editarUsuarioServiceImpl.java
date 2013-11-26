package com.sblm.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sblm.dao.IEditarUsuarioDAO;
import com.sblm.model.Usuario;
import com.sblm.service.IEditarUsuarioService;

@Transactional(readOnly = true)
@Service(value="panelEditarUsuarioServiceImpl")
public class editarUsuarioServiceImpl implements IEditarUsuarioService{

	@Autowired
	private IEditarUsuarioDAO editarusuarioDAO;



	@Override
	public List<Usuario> getUsuarioEditSinPerfil(String selectIdRegistroUsuario) {

		return editarusuarioDAO.getUsuarioEditSinPerfil(selectIdRegistroUsuario);
	}



	@Override
	public void actualizarUsuarioMaestro(String nombreUsuario,String nombreRegistro, String pat,
			String mat, String cargoRegistro, String emailRegistro,
			String passRegistro, String formatearFechaString,String ruta) {

		editarusuarioDAO.actualizarUsuario(nombreUsuario,nombreRegistro,pat,mat,cargoRegistro,emailRegistro,passRegistro,formatearFechaString,ruta);
		
	}


	
	
	
}

package com.sblm.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sblm.dao.IUsuarioDAO;
import com.sblm.model.Perfilmodulo;
import com.sblm.model.Tipocambio;
import com.sblm.model.Usuario;
import com.sblm.service.IUsuarioService;

@Transactional(readOnly = true)
@Service(value="usuarioService")
public class UsuarioService implements IUsuarioService {

	@Autowired
	private IUsuarioDAO usuarioDAO;
	
	public IUsuarioDAO getUsuarioDAO() {
		return usuarioDAO;
	}

	@Transactional(readOnly = false)
	@Override
	public void crearUsuario(Usuario usuario) {
		getUsuarioDAO().crearUsuario(usuario);
		
	}

	@Transactional(readOnly = false)
	@Override
	public void actualizarUsuario(Usuario usuario) {
		// TODO Auto-generated method stub
		
	}

	@Transactional(readOnly = false)
	@Override
	public void eliminarUsuario(Usuario usuario) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Usuario listarUsuarioPorId(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Usuario> listarUsuarios() {
		return getUsuarioDAO().listarUsuarios();
	}
	@Override
	public List<Usuario> listarUsuarios(int iduser) {
		return getUsuarioDAO().listarUsuarios(iduser);
	}

	@Override
	public String obtenerContrasenha(String mail) {
		return getUsuarioDAO().obtenerPass(mail);
	}
	
	public Usuario buscarUsuario(Usuario usuario) {
		return getUsuarioDAO().buscarUsuario(usuario);
	}

	public void cambiarContrasenha(int id, String pass) {
		getUsuarioDAO().cambiarContrasenha(id, pass);
	}

	@Override
	public List<Perfilmodulo> obtenerEstadoModulo(int idperfil) {
		return getUsuarioDAO().obtenerEstadoModulo(idperfil);
	}

	@Override
	public int obtenerIdPerfil(Usuario usuario) {
		return getUsuarioDAO(). obtenerIdPerfil( usuario);
		
	}

	@Override
	public List<String> loguear(Usuario usuario) {
		return getUsuarioDAO().loguear(usuario);
		
	}

	@Override
	public Perfilmodulo obtenerEstadoModulo(int idperfil, int idmodulo) {
		return getUsuarioDAO().obtenerEstadoModulo(idperfil, idmodulo);
	}

	@Override
	public void grabarLogueo() {
		getUsuarioDAO().grabarLogueo();		
	}

	@Override
	public Usuario buscarUsuarioxId(int parseInt) {
		// TODO Auto-generated method stub
		return getUsuarioDAO().buscarUsuarioxId(parseInt);	}

	@Override
	public String obtenerNombrePerfilSeleccionado(Usuario u) {
		// TODO Auto-generated method stub
		return getUsuarioDAO().obtenerNombrePerfilSeleccionado(u);
	}


	


}

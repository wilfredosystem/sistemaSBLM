package com.sblm.serviceImpl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sblm.dao.IAuditoriaDAO;
import com.sblm.model.Auditoria;
import com.sblm.model.Modulo;
import com.sblm.model.Pagina;
import com.sblm.model.Perfilusuario;
import com.sblm.model.Usuario;
import com.sblm.service.IAuditoriaService;

@Transactional(readOnly = true)
@Service(value="panelAuditoriaServiceImpl")
public class AuditoriaServiceImpl implements IAuditoriaService{

	@Autowired
	private IAuditoriaDAO auditoriaDAO;

	
	
	@Override
	public List<Auditoria> listarAuditoria() {
		return auditoriaDAO.listarAuditoriaSingle();
	}



	@Override
	public List<Usuario> listUsuariobyNom() {
		return auditoriaDAO.listUsuariobyNom();
	}



	@Override
	public List<Perfilusuario> listPerfilbyNom() {
		// TODO Auto-generated method stub
		 return auditoriaDAO.listPerfilbyNom();
	}



	@Override
	public List<Modulo> listModulobyNom() {
		// TODO Auto-generated method stub
		return auditoriaDAO.listmodulobyNom();
	}

	@Override
	public List<Pagina> listRecursobyNom() {
		// TODO Auto-generated method stub
		return auditoriaDAO.listRecursobyNom();
	}
	
	


	@Override
	public String nroConectadosDelDia() {
		// TODO Auto-generated method stub
		return auditoriaDAO.nroConectadosDelDia();
	}

	

	@Override
	public List listAuditoriaFiltro(Date fechaInicio, Date fechaFin,
			String nombreUsuario, String nomPantalla, String nomPerfil,
			String nomModulo) {

		return auditoriaDAO.listAuditoriaFiltro(fechaInicio, fechaFin,
				nombreUsuario,nomPantalla,nomPerfil,
				nomModulo);
	}
	
	@Override
	public List listAuditoriaFiltroPerfil(Date fechaInicio, Date fechaFin,
			String nombrePerfil, String recursoBusqueda, String nomPerfil,
			String moduloBusqueda) {
		// TODO Auto-generated method stub
		return auditoriaDAO.listAuditoriaFiltroPerfil(fechaInicio, fechaFin,
				nombrePerfil,recursoBusqueda,nomPerfil,
				moduloBusqueda);
	}
	


	@Override
	public Usuario listUsuarioTop() { 
		// TODO Auto-generated method stub
		return auditoriaDAO.listUsuarioTop();
	}



	@Override
	public Object ultimoModuloVisitado() {
		// TODO Auto-generated method stub
		return auditoriaDAO.ultimoModuloVisitado();	}



	@Override
	public Object ultimaPaginaVisitado() {
		// TODO Auto-generated method stub
		return  auditoriaDAO.ultimaPaginaVisitado();
	}




//
//	@Override
//	public List<Usuario> listUserTop(String string) {
//		// TODO Auto-generated method stub
//		return auditoriaDAO.listUserTop(string);
//	}









	



	

}

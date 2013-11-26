package com.sblm.serviceImpl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sblm.dao.INotificacionesDAO;
import com.sblm.service.INotificacionesService;

@Transactional(readOnly = true)
@Service(value="panelNotificacionesServiceImpl")
public class NotificacionesServiceImpl implements INotificacionesService{

	@Autowired
	private INotificacionesDAO notificacionesDAO;

	@Override
	public List listarNotificaciones(int i, String mesSeleccionado,
			String anioSeleccionado) {
		// TODO Auto-generated method stub
		return notificacionesDAO.listarNotificaciones(i,mesSeleccionado,anioSeleccionado);
	}

	@Override
	public void actualizarPendienteToRevisado(int idauditoria) {
		notificacionesDAO.actualizarPendienteToRevisado(idauditoria);
	}
	
	@Override
	public void actualizarPendienteToCancelado(int idauditoria) {
		notificacionesDAO.actualizarPendienteToCancelado(idauditoria);
		
	}
	@Override
	public Object nroNotificacionesRevisado() {
		// TODO Auto-generated method stub
		return notificacionesDAO.nroNotificacionesRevisado();
	}

	@Override
	public Object nroNotificacionesPendiente() {
		// TODO Auto-generated method stub
		return notificacionesDAO.nroNotificacionesPendiente();
	}

	@Override
	public Object nroNotificacionesCancelado() {
		// TODO Auto-generated method stub
		return notificacionesDAO.nroNotificacionesCancelado();
	}

	@Override
	public Object nroNotificacionesDelMes() {
		// TODO Auto-generated method stub
		return notificacionesDAO.nroNotificacionesDelMes();
	}

	@Override
	public Object nroNotificacionesTotal() {
		// TODO Auto-generated method stub
		return notificacionesDAO.nroNotificacionesTotal();
	}



	


}

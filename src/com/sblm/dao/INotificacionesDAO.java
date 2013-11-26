package com.sblm.dao;

import java.util.List;

public interface INotificacionesDAO {


	void actualizarPendienteToRevisado(int idauditoria);


	Object nroNotificacionesRevisado();

	Object nroNotificacionesPendiente();

	Object nroNotificacionesCancelado();

	Object nroNotificacionesDelMes();

	List listarNotificaciones(int i, String mesSeleccionado, String anioSeleccionado);


	Object nroNotificacionesTotal();


	void actualizarPendienteToCancelado(int idauditoria);


	

}

package com.sblm.service;

import java.util.List;

import com.sblm.model.Auditoria;

public interface INotificacionesService {


	void actualizarPendienteToRevisado(int idauditoria);


	Object nroNotificacionesRevisado();

	Object nroNotificacionesPendiente();

	Object nroNotificacionesCancelado();

	Object nroNotificacionesDelMes();

	List listarNotificaciones(int i, String mesSeleccionado, String anioSeleccionado);


	Object nroNotificacionesTotal();


	void actualizarPendienteToCancelado(int idauditoria);

}

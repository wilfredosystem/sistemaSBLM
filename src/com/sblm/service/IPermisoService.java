package com.sblm.service;

import java.util.List;

import com.sblm.model.Permiso;

public interface IPermisoService {

	public void registrarPermiso(Permiso permiso);

	public void actualizarPermiso(Permiso permiso);

	public void eliminarPermiso(Permiso permiso);

	public Permiso listarPermisoPorId(int id);

	public List<Permiso> listarPermisos();
}

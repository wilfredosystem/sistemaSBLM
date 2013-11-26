package com.sblm.dao;

import java.util.List;

import com.sblm.model.Archivodocumento;
import com.sblm.model.Carta;

public interface IRecaudacionAutovaluoCartaDAO {

	void grabarCabeceraArbitrio(Carta carta);

	List<Carta> listarArbitrioConsulta();

	Carta getUltimaCabeceraGrabada();

	List<Archivodocumento> obtenerArchivosCarta(int idcarta);

	void actualizarCabeceraCarta(Carta carta);

}

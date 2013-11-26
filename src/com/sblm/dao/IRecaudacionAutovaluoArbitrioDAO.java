package com.sblm.dao;

import java.util.List;
import com.sblm.model.Arbitrio;
import com.sblm.model.Archivodocumento;
import com.sblm.model.Contrato;
import com.sblm.model.Detallearbitrio;
import com.sblm.model.Extensionarchivo;
import com.sblm.model.Inquilino;
import com.sblm.model.Uso;

public interface IRecaudacionAutovaluoArbitrioDAO {

	void grabarCabeceraArbitrio(Arbitrio arbitrio);

	List<Detallearbitrio> obtenerDetalleArbitrio(int idarbitrio);

	List<Arbitrio> listarArbitrioConsulta();

	void grabarDetalleArbitrio(Detallearbitrio detalleArbitrio);

	List<Uso> getListaUso();

	List<Inquilino> getListaInquilino();

	void grabarArchivosAdjuntos(Archivodocumento archivodocu);

	List<Archivodocumento> obtenerArchivosArbitrio(int idarbitrio);

	void actualizarCabeceraArbitrio(Arbitrio arbitrio);

	void eliminarArchivoDocumentoNoEncontrado(int idarchivodocumento);

	Arbitrio getUltimaCabeceraGrabada();

	void actualizarvalorNroArchivos(int size, int idcarta);

	Double obtenerTotalActual(int idarbitrio);

	void actualizarSumaTotalDetalleArbitrio(int idarbitrio, Double totalActual);

	List<Contrato> listaContratos();

}

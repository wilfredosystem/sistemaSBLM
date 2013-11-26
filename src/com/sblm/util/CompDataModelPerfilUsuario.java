package com.sblm.util;

import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;
import com.sblm.model.Perfilusuario;



public class CompDataModelPerfilUsuario extends ListDataModel<Perfilusuario> implements SelectableDataModel<Perfilusuario>{

	public CompDataModelPerfilUsuario() {
		
	}
	
	public CompDataModelPerfilUsuario(List<Perfilusuario> data) {
		super(data);
	}
	
	@Override
	public Perfilusuario getRowData(String rowKey) {
		List<Perfilusuario> listCamp = (List<Perfilusuario>) getWrappedData();
		
		for (Perfilusuario ctmaeCampania : listCamp) {
			if ((ctmaeCampania.getIdperfilusuario()+"").equals(rowKey)) {
				return ctmaeCampania;
			}
		}
		return null;
	}

	@Override
	public Object getRowKey(Perfilusuario ctmaeCampania) {
		return ctmaeCampania.getIdperfilusuario();
	}
}
package com.sblm.util;

import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;
import com.sblm.model.Usuario;;



public class CompDataModelUsuario extends ListDataModel<Usuario> implements SelectableDataModel<Usuario>{

	public CompDataModelUsuario() {
		
	}
	
	public CompDataModelUsuario(List<Usuario> data) {
		super(data);
	}
	
	@Override
	public Usuario getRowData(String rowKey) {
		List<Usuario> listCamp = (List<Usuario>) getWrappedData();
		
		for (Usuario ctmaeCampania : listCamp) {
			if ((ctmaeCampania.getIdusuario()+"").equals(rowKey)) {
				return ctmaeCampania;
			}
		}
		return null;
	}

	@Override
	public Object getRowKey(Usuario ctmaeCampania) {
		return ctmaeCampania.getIdusuario();
	}
}
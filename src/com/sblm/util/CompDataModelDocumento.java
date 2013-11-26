package com.sblm.util;

import java.io.Serializable;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;
import com.sblm.model.Documento;



public class CompDataModelDocumento extends ListDataModel<Documento> implements SelectableDataModel<Documento>,Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -889031709539421232L;

	public CompDataModelDocumento() {
		
	}
	
	public CompDataModelDocumento(List<Documento> data) {
		super(data);
	}
	
	@Override
	public Documento getRowData(String rowKey) {
		List<Documento> listCamp = (List<Documento>) getWrappedData();
		
		for (Documento ctmaeCampania : listCamp) {
			if ((ctmaeCampania.getIddocumento()+"").equals(rowKey)) {
				return ctmaeCampania;
			}
		}
		return null;
	}

	@Override
	public Object getRowKey(Documento ctmaeCampania) {
		return ctmaeCampania.getIddocumento();
	}
}
package com.sblm.util;

import java.io.Serializable;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;
import com.sblm.model.Flujodocumento;



public class CompDataModelFlujoDocumento extends ListDataModel<Flujodocumento> implements SelectableDataModel<Flujodocumento>,Serializable{

	private static final long serialVersionUID = -889031709539421232L;

	public CompDataModelFlujoDocumento() {
		
	}
	
	public CompDataModelFlujoDocumento(List<Flujodocumento> data) {
		super(data);
	}
	
	@Override
	public Flujodocumento getRowData(String rowKey) {
		List<Flujodocumento> listCamp = (List<Flujodocumento>) getWrappedData();
		
		for (Flujodocumento ctmaeCampania : listCamp) {
			if ((ctmaeCampania.getIdflujodocumento()+"").equals(rowKey)) {
				return ctmaeCampania;
			}
		}
		return null;
	}

	@Override
	public Object getRowKey(Flujodocumento ctmaeCampania) {
		return ctmaeCampania.getIdflujodocumento();
	}
}
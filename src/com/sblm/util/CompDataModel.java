package com.sblm.util;

import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;
import com.sblm.model.Auditoria;;



public class CompDataModel extends ListDataModel<Auditoria> implements SelectableDataModel<Auditoria>{

	public CompDataModel() {
		
	}
	
	public CompDataModel(List<Auditoria> data) {
		super(data);
	}
	
	@Override
	public Auditoria getRowData(String rowKey) {
		List<Auditoria> listCamp = (List<Auditoria>) getWrappedData();
		
		for (Auditoria ctmaeCampania : listCamp) {
			if ((ctmaeCampania.getIdauditoria()+"").equals(rowKey)) {
				return ctmaeCampania;
			}
		}
		return null;
	}

	@Override
	public Object getRowKey(Auditoria ctmaeCampania) {
		return ctmaeCampania.getIdauditoria();
	}
}
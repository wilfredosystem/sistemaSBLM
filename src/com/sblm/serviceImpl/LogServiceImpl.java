package com.sblm.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sblm.dao.ILogDAO;
import com.sblm.model.Auditoria;
import com.sblm.service.ILogService;

@Transactional(readOnly = true)
@Service(value="panelLogServiceImpl")
public class LogServiceImpl implements ILogService{

	@Autowired
	private ILogDAO logDAO;

	@Override
	public void save(String adt) {
		//System.out.println("entroservice"+adt.getUrl());
		logDAO.save(adt);		
	}


}

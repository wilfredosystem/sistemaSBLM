package com.sblm.util;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.*;
import org.hibernate.type.Type;

import com.sblm.model.Auditoria;
import com.sblm.model.Usuario;

public class AuditLogInterceptor extends EmptyInterceptor{
	
	private static final long serialVersionUID = 5645525943162111518L;

	private static final Logger logger = Logger
	            .getLogger(AuditLogInterceptor.class);

	    private Set<Usuario> savedEntities = new HashSet<Usuario>();
	    private  Session session;
	    
	    public void setSession(Session session) {
	        this.session = session;
	    }

	    @Override
	    public boolean onSave(Object object, Serializable id, Object[] state,
	            String[] propertyNames, Type[] types) {
	        if (object instanceof Usuario) {
	        	Usuario entity = (Usuario) object;
	            logger.info("Saving new Entity with data " + entity.getNombreusr());
	            savedEntities.add(entity);
	        }        
	        return super.onSave(object, id, state, propertyNames, types);
	    }
	    
	    @Override
	    public void postFlush(@SuppressWarnings("rawtypes") Iterator entities) {        
	        super.postFlush(entities);
	        for (Usuario entity : savedEntities) {
	            logger.info("Logging about saved entity  " + entity.getNombreusr());
	            Auditoria audit = new Auditoria();
	            
	            audit.setUrl("Saved entity with data " + entity.getNombreusr() 
	                + " for id " + entity.getIdusuario() + " at time " + new Date());
	            session.save(audit);
	        }
	        savedEntities.clear();
	    }
	
}

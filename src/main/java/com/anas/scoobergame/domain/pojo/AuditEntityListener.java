package com.anas.scoobergame.domain.pojo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Calendar;

public class AuditEntityListener {
	
	private static final Logger log = (Logger) LoggerFactory.getLogger(AuditEntityListener.class);

	@PrePersist
	public void prePersist(AuditableEntity e) {
		if(log.isDebugEnabled())
			log.debug("PrePersist ... set auditable fields on entity");
		try{
			e.setCreatedDate(Calendar.getInstance());
			e.setLastModifiedDate(Calendar.getInstance());
		} catch(Exception ex) {
			log.error("Exception in AuditableEntityListener", ex);
		}
		
	}

	@PreUpdate
	public void preUpdate(AuditableEntity e) {
		if (log.isDebugEnabled())
			log.debug("preUpdate ... set auditable fields on entity");
		try{
			e.setLastModifiedDate(Calendar.getInstance());
		} catch(Exception ex) {
			log.error("Exception in AuditableEntityListener", ex);
		}
		
	
	}

}

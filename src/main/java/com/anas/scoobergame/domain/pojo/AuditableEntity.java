package com.anas.scoobergame.domain.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Calendar;

@MappedSuperclass
@EntityListeners(value = { AuditEntityListener.class })
public abstract class AuditableEntity  {
	
	private static final long serialVersionUID = 561410088865740668L;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date")
	private Calendar createdDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_modified_date")
	@JsonIgnore
	private Calendar lastModifiedDate;

	public Calendar getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Calendar createdDate) {
		this.createdDate = createdDate;
	}

	public Calendar getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Calendar lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

}

package com.bas.admin.dao.entity;


import java.sql.Timestamp;

/**
 * 
 * @author Amogh
 * 
 */

public class DesignationEntity {
	private int designationId;
	private String designationName;
	private String description;
	private Timestamp doe;
	private Timestamp dom;
	private String entryBy;

	public int getDesignationId() {
		return designationId;
	}

	public void setDesignationId(int designationId) {
		this.designationId = designationId;
	}

	public String getDesignationName() {
		return designationName;
	}

	public void setDesignationName(String designationName) {
		this.designationName = designationName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	

	public Timestamp getDoe() {
		return doe;
	}

	public void setDoe(Timestamp doe) {
		this.doe = doe;
	}

	public Timestamp getDom() {
		return dom;
	}

	public void setDom(Timestamp dom) {
		this.dom = dom;
	}

	public String getEntryBy() {
		return entryBy;
	}

	public void setEntryBy(String entryBy) {
		this.entryBy = entryBy;
	}

	@Override
	public String toString() {
		return "DesignationForm [designationId=" + designationId
				+ ", designationName=" + designationName + ", description="
				+ description + ", doe=" + doe + ", dom=" + dom + ", entryBy="
				+ entryBy + "]";
	}

}

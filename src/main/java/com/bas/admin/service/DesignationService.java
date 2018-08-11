package com.bas.admin.service;

import java.util.List;

import com.bas.admin.web.controller.form.DesignationForm;

/**
 * 
 * @author Amogh
 *
 */
public interface DesignationService {
	
	public String addDesignation(DesignationForm designationform);
	public String editDesignation(DesignationForm designationForm);
	public String deleteDesignation(int designationId);
	public List<DesignationForm> findDesignations();
	public DesignationForm findDesignationById(int desigId);
	public String updateDesignation(DesignationForm designationForm);

}

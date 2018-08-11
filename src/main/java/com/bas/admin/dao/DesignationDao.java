package com.bas.admin.dao;

import java.util.List;

import com.bas.admin.dao.entity.DesignationEntity;
/**
 * 
 * @author Amogh
 *
 */
public interface DesignationDao {
	public String addDesignation(DesignationEntity designationEntity);
	public String editDesignation(DesignationEntity designationEntity);
	public String deleteDesignation(int designationId);
	public List<DesignationEntity> findDesignations();
	public DesignationEntity findDesignationById(int desigId);
	public String updateDesignation(DesignationEntity designationEntity);


}

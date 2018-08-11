package com.bas.admin.dao;

import java.util.List;

import com.bas.admin.dao.entity.OrganizationTimeEntity;

public interface OrgTimeDao {
	
	public String editOrgTime(OrganizationTimeEntity organizationTimeEntity);
	public String deleteOrgTime(int sno);
	public List<OrganizationTimeEntity> findOrgTimes();
	public String addOrgTime(OrganizationTimeEntity organizationTimeEntity);
	public OrganizationTimeEntity findOrgTimes(int sno);

}

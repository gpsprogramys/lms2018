package com.bas.admin.service;

import java.util.List;

import com.bas.admin.web.controller.form.OrganisationTimeForm;
import com.bas.admin.web.controller.form.OrganizationTimeVO;

/**
 * @author Sid
*
 */
public interface OrgTimeService {	

	public String editOrgTime(OrganizationTimeVO organizationTimeVO);
	public String deleteOrgTime(int sno);
	public List<OrganisationTimeForm> findOrgTimes();
	public OrganizationTimeVO findOrgTimesbySno(int sno);
	public String addOrgTime(OrganizationTimeVO organizationTimeVO2);

}

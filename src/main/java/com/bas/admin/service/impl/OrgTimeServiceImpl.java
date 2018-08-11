package com.bas.admin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bas.admin.dao.OrgTimeDao;
import com.bas.admin.dao.entity.OrganizationTimeEntity;
import com.bas.admin.service.OrgTimeService;
import com.bas.admin.web.controller.form.OrganisationTimeForm;
import com.bas.admin.web.controller.form.OrganizationTimeVO;

@Service("orgTimeServiceImpl")
@Transactional
public class OrgTimeServiceImpl implements OrgTimeService {
	
	@Autowired
	@Qualifier("OrgTimeDaoImpl")
	private OrgTimeDao orgTimeDao;

	@Override
	public String editOrgTime(OrganizationTimeVO organizationTimeVO) {
		OrganizationTimeEntity organizationTimeEntity = new OrganizationTimeEntity();
		BeanUtils.copyProperties(organizationTimeVO, organizationTimeEntity);
		String res = orgTimeDao.editOrgTime(organizationTimeEntity);
		return "__done__";
	}

	@Override
	public String deleteOrgTime(int sno) {
	    orgTimeDao.deleteOrgTime(sno);
		return "done";
	}

	@Override
	public List<OrganisationTimeForm> findOrgTimes() {		
		List<OrganizationTimeEntity> organizationTimeEntitieslist=orgTimeDao.findOrgTimes();
		List<OrganisationTimeForm> organizationTimeVOlist=new ArrayList<OrganisationTimeForm>();
		for(OrganizationTimeEntity fs: organizationTimeEntitieslist){
			OrganisationTimeForm organizationTime=new OrganisationTimeForm();
			BeanUtils.copyProperties(fs,organizationTime);
			organizationTimeVOlist.add(organizationTime);
		}
		return organizationTimeVOlist;
	}

	@Override
	public String addOrgTime(OrganizationTimeVO organizationTimeVO2) {
		OrganizationTimeEntity organizationTimeEntity = new OrganizationTimeEntity();
		BeanUtils.copyProperties(organizationTimeVO2, organizationTimeEntity);
		String result = orgTimeDao.addOrgTime(organizationTimeEntity);
		return result;
		
	}

	@Override
	public OrganizationTimeVO findOrgTimesbySno(int sno) {		
		OrganizationTimeVO organizationTimeVO=new OrganizationTimeVO();	
		OrganizationTimeEntity organizationTimeEntity = orgTimeDao.findOrgTimes(sno);		
		BeanUtils.copyProperties(organizationTimeEntity,organizationTimeVO);	
		return organizationTimeVO;
	}
	
}

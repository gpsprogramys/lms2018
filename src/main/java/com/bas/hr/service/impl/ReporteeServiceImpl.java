package com.bas.hr.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bas.hr.dao.ReporteeDao;
import com.bas.hr.service.ReporteeService;
import com.bas.hr.web.controller.model.ManagerEmployeeRelationEntity;
import com.bas.hr.web.controller.model.ManagerEmployeeRelationVO;

/**
 * 
 * @author xxxx
 * 
 *
 */
@Service("ReporteeServiceImpl")
@Transactional(propagation=Propagation.REQUIRED)
public class ReporteeServiceImpl implements ReporteeService{
	
	@Autowired
	@Qualifier("ReporteeDaoImpl")
	private ReporteeDao reporteeDao;

	@Override
	public String addEmployeeToManagerRelation(ManagerEmployeeRelationVO managerEmployeeRelationVO) {
		ManagerEmployeeRelationEntity managerEmployeeRelationEntity= new ManagerEmployeeRelationEntity();
		BeanUtils.copyProperties(managerEmployeeRelationVO, managerEmployeeRelationEntity);
		String result = reporteeDao.addEmployeToManagerRelation(managerEmployeeRelationEntity);
		return result;
	}
	
}

package com.bas.employee.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.bas.common.web.controller.form.SubjectArrangementVO;
import com.bas.common.web.controller.form.SubjectNameCodeVO;
import com.bas.employee.dao.FacultySubjectAssignmentDao;
import com.bas.employee.service.FacultySubjectAssignmentService;

@Service("FacultySubjectAssignmentServiceImpl")
@Scope("singleton")
public class FacultySubjectAssignmentServiceImpl implements FacultySubjectAssignmentService {
	
	@Autowired
	@Qualifier("FacultySubjectAssignmentDaoImpl")
	private FacultySubjectAssignmentDao facultySubjectAssignmentDao;
	
	@Override
	public List<String> findBranchSemSection(String empid){
		return facultySubjectAssignmentDao.findBranchSemSection(empid);
	}

	@Override
	public List<SubjectArrangementVO> findFacultyByBranchPeriod(String branchSemSec,String dayPeriod,int empid) {
		return facultySubjectAssignmentDao.findFacultyByBranchPeriod(branchSemSec,dayPeriod,empid);
	}	
	
	public List<SubjectNameCodeVO> findSubjectsByFacultyBranchPeriod(String branchSemSec,	String dayPeriod, int empid){
		return facultySubjectAssignmentDao.findSubjectsByFacultyBranchPeriod(branchSemSec,dayPeriod,empid);
	}
	
	
}

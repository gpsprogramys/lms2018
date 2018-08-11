package com.bas.employee.dao;

import java.util.List;

import com.bas.common.web.controller.form.SubjectArrangementVO;
import com.bas.common.web.controller.form.SubjectNameCodeVO;

public interface FacultySubjectAssignmentDao {
	public List<SubjectArrangementVO> findFacultyByBranchPeriod(String branchSemSec,String dayPeriod,int empid);
	public List<String> findBranchSemSection(String empid);
	public List<SubjectNameCodeVO> findSubjectsByFacultyBranchPeriod(String branchSemSec,	String dayPeriod, int empid);
}

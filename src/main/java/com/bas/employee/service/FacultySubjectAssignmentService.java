package com.bas.employee.service;

import java.util.List;

import com.bas.common.web.controller.form.SubjectArrangementVO;
import com.bas.common.web.controller.form.SubjectNameCodeVO;

public interface FacultySubjectAssignmentService {

	public List<String> findBranchSemSection(String empid);

	public List<SubjectArrangementVO> findFacultyByBranchPeriod(String branchSemSec,	String dayPeriod, int empid);
	public  List<SubjectNameCodeVO> findSubjectsByFacultyBranchPeriod(String branchSemSec,	String dayPeriod, int empid);

}

package com.bas.employee.service;

import java.util.List;

import com.bas.employee.web.controller.form.FaculityTimeForm;

public interface BasFacultyTimeService {
	public String addFacultyTime(FaculityTimeForm faculityTimeForm);
	public String editFacultyTime(FaculityTimeForm faculityTimeForm);
	public String deleteFacultyTime(int fid);
	public List<FaculityTimeForm> findFacultyTime();
	public FaculityTimeForm findFacultyTimeById(int facId);
	public String updateFacultyTime(FaculityTimeForm faculityTimeForm); 
	public FaculityTimeForm findFacultyTimeByParams(int facId, String dayName, String classRoom, String subjectCode);

}

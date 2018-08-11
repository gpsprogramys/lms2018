package com.bas.employee.dao;

import java.util.List;

import com.bas.employee.dao.entity.FaculityTimeEntity;

public interface BasFacultyTimeDao {
	public String addFacultyTime(FaculityTimeEntity faculityTimeEntity);
	public String editFacultyTime(FaculityTimeEntity faculityTimeEntity);
	public String deleteFacultyTime(int fid);
	public List<FaculityTimeEntity> findFacultyTime();
	public FaculityTimeEntity findFacultyTimeById(int facId);
	public String updateFacultyTime(FaculityTimeEntity faculityTimeEntity);
	public FaculityTimeEntity findFacultyTimeByParams(int facId, String dayName,
			String classRoom, String subjectCode);
}

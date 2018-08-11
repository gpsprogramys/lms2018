/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bas.employee.web.controller.form;


import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.bas.admin.web.controller.form.AttendanceHeaderVO;


/**
 * * @author Nagendra
 * 
 */
public class AttendanceHeaderBeanRowMapper implements RowMapper<AttendanceHeaderVO> {
    @Override
	public AttendanceHeaderVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		AttendanceHeaderVO attendanceHeaderVO = new AttendanceHeaderVO();
                attendanceHeaderVO.setEmpid(rs.getInt("id"));
		attendanceHeaderVO.setName(rs.getString("name"));
                attendanceHeaderVO.setDesignation(rs.getString("designation"));
		attendanceHeaderVO.setDepartment(rs.getString("department"));
		Blob blob=rs.getBlob("image");
		int n1=(int) blob.length();
		byte[] imageByte=blob.getBytes(1,n1);
		attendanceHeaderVO.setPhoto(imageByte);
		return attendanceHeaderVO;
	}
}

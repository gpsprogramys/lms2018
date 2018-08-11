package com.bas.employee.report.excel;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.bas.admin.web.controller.form.FaculityDailyAttendanceReportVO;


/**
 * 
 * @author Nahid
 *
 */
public class EmployeeOnLeaveExcelBuilder extends AbstractExcelView {
	
	 @Override
	    protected void buildExcelDocument(Map<String, Object> model,
	            HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response)
	            throws Exception {
	        // get data model which is passed by the Spring container
		     List<FaculityDailyAttendanceReportVO> employeeLeaveHistory = (List<FaculityDailyAttendanceReportVO>) model.get("empLeaveDataList");
	         
	        // create a new Excel sheet
	        HSSFSheet sheet = workbook.createSheet("Employee On Leave");
	        sheet.setDefaultColumnWidth(30);
	         
	        // create style for header cells
	        CellStyle style = workbook.createCellStyle();
	        Font font = workbook.createFont();
	        font.setFontName("Arial");
	        style.setFillForegroundColor(HSSFColor.BLUE.index);
	        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
	        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
	        font.setColor(HSSFColor.WHITE.index);
	        style.setFont(font);
	         
	        // create header row
	        HSSFRow header = sheet.createRow(0);
	         
	        header.createCell(0).setCellValue("No");
	        header.getCell(0).setCellStyle(style);
	         
	        header.createCell(1).setCellValue("Request ID");
	        header.getCell(1).setCellStyle(style);
	         
	        header.createCell(2).setCellValue("Name");
	        header.getCell(2).setCellStyle(style);
	         
	        header.createCell(3).setCellValue("Entry Date");
	        header.getCell(3).setCellStyle(style);
	         
	        header.createCell(4).setCellValue("Leave Type");
	        header.getCell(4).setCellStyle(style);
	        
	        header.createCell(5).setCellValue("Leave From");
	        header.getCell(5).setCellStyle(style);
	        
	        
	        header.createCell(6).setCellValue("Leave To");
	        header.getCell(6).setCellStyle(style);
	        
	        
	        header.createCell(7).setCellValue("Leave Taken");
	        header.getCell(7).setCellStyle(style);
	        
	        header.createCell(8).setCellValue("Reason");
	        header.getCell(8).setCellStyle(style);
	         
	        // create data rows
	        int rowCount = 1;
	         
	        for (FaculityDailyAttendanceReportVO emFaculityLeaveMasterVO : employeeLeaveHistory) {
	            HSSFRow aRow = sheet.createRow(rowCount);
	            aRow.createCell(0).setCellValue(rowCount);
	            aRow.createCell(1).setCellValue(emFaculityLeaveMasterVO.getFid());
	            aRow.createCell(2).setCellValue(emFaculityLeaveMasterVO.getName());
	            aRow.createCell(3).setCellValue(emFaculityLeaveMasterVO.getDesignation());
	            aRow.createCell(4).setCellValue(emFaculityLeaveMasterVO.getDepartment());
	            aRow.createCell(5).setCellValue(emFaculityLeaveMasterVO.getDetail());
	            aRow.createCell(6).setCellValue(emFaculityLeaveMasterVO.getIntimestatus());
	            aRow.createCell(7).setCellValue(emFaculityLeaveMasterVO.getOuttimestatus());
	            aRow.createCell(8).setCellValue(emFaculityLeaveMasterVO.getFatherName());
	            rowCount++;
	        }
	    }

}

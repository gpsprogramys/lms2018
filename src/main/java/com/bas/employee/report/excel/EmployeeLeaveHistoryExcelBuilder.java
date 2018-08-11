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

import com.bas.employee.web.controller.form.FaculityLeaveMasterVO;


/**
 * 
 * @author aaaaaaa
 *
 */
public class EmployeeLeaveHistoryExcelBuilder extends AbstractExcelView {
	
	 @Override
	    protected void buildExcelDocument(Map<String, Object> model,
	            HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response)
	            throws Exception {
	        // get data model which is passed by the Spring container
		     List<FaculityLeaveMasterVO> employeeLeaveHistory = (List<FaculityLeaveMasterVO>) model.get("faculityLeaveMasterVOslist");
	         
	        // create a new Excel sheet
	        HSSFSheet sheet = workbook.createSheet("Employee Leave History");
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
	         
	        header.createCell(0).setCellValue("SNO");
	        header.getCell(0).setCellStyle(style);
	         
	        header.createCell(1).setCellValue("Date From");
	        header.getCell(1).setCellStyle(style);
	         
	        header.createCell(2).setCellValue("Date To");
	        header.getCell(2).setCellStyle(style);
	         
	        header.createCell(3).setCellValue("Total day(s)");
	        header.getCell(3).setCellStyle(style);
	         
	        header.createCell(4).setCellValue("Leave Type");
	        header.getCell(4).setCellStyle(style);
	        
	        header.createCell(5).setCellValue("In Account");
	        header.getCell(5).setCellStyle(style);
	        
	        
	        header.createCell(6).setCellValue("Leave Dates");
	        header.getCell(6).setCellStyle(style);
	        
	        
	        header.createCell(7).setCellValue("Lwp");
	        header.getCell(7).setCellStyle(style);
	        
	        header.createCell(8).setCellValue("Description");
	        header.getCell(8).setCellStyle(style);
	         
	        // create data rows
	        int rowCount = 1;
	         
	        for (FaculityLeaveMasterVO emFaculityLeaveMasterVO : employeeLeaveHistory) {
	            HSSFRow aRow = sheet.createRow(rowCount);
	            aRow.createCell(0).setCellValue(rowCount);
	            aRow.createCell(1).setCellValue(emFaculityLeaveMasterVO.getLeaveFrom());
	            aRow.createCell(2).setCellValue(emFaculityLeaveMasterVO.getLeaveTo());
	            aRow.createCell(3).setCellValue(emFaculityLeaveMasterVO.getTotalDays());
	            aRow.createCell(4).setCellValue(emFaculityLeaveMasterVO.getLeaveType());
	            aRow.createCell(5).setCellValue(emFaculityLeaveMasterVO.getInAccount());
	            aRow.createCell(6).setCellValue(emFaculityLeaveMasterVO.getLeaveDates());
	            aRow.createCell(7).setCellValue(emFaculityLeaveMasterVO.getLwp());
	            aRow.createCell(8).setCellValue(emFaculityLeaveMasterVO.getDescription());
	            rowCount++;
	        }
	    }

}

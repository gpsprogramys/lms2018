package com.bas.employee.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.bas.admin.aop.logger.Loggable;
import com.bas.common.constant.BASApplicationConstants;
import com.bas.common.dao.entity.EmployeeLeaveDetailEntity;
import com.bas.common.dao.entity.MessageStatusEntity;
import com.bas.common.dao.entity.SuggestionManagerOptionEntity;
import com.bas.common.dao.entity.SuggestionOptionEntity;
import com.bas.common.util.DateUtils;
import com.bas.common.web.controller.form.AllEmployeeDetailsEntity;
import com.bas.employee.dao.IEmployeeLMSDao;
import com.bas.employee.dao.entity.EmployeeLeaveRequestEntity;
import com.bas.employee.dao.entity.LeaveBalanceEntity;
import com.bas.employee.dao.entity.SubjectAlternativeArrangementsEntity;
import com.bas.employee.web.controller.form.EmployeeDetailsForm;
import com.synergy.bank.employee.dao.entity.EmployeeDetailsEntity;
import com.synergy.bank.employee.dao.entity.EmployeeLeaveBalanceEntity;

/**
 * @author xxxxxxx
 *
 */
@Repository("EmployeeLMSDao")
@Scope("singleton")
public class EmployeeLMSDao implements IEmployeeLMSDao {
	
	@Autowired
	@Qualifier("sdatasource")
	private DataSource dataSource;

	private JdbcTemplate JdbcTemplate;

	@PostConstruct
	public void instantiateJdbcTemplate() {
		JdbcTemplate = new JdbcTemplate(dataSource);
	}

		
	@Override
	@Loggable
	public EmployeeDetailsEntity findEmployeeById(EmployeeDetailsEntity employeeDetailsEntity) {
		EmployeeDetailsEntity employeeDetails = new EmployeeDetailsEntity();
		String query = "SELECT * from emp_db where id = ";
		employeeDetails = JdbcTemplate.queryForObject(query + employeeDetailsEntity.getId(),
				new BeanPropertyRowMapper<EmployeeDetailsEntity>(EmployeeDetailsEntity.class));
		return employeeDetails;
	}

	@Override
	public EmployeeLeaveBalanceEntity getLeaveBalanceByEmpId(EmployeeLeaveBalanceEntity employeeLeaveBalanceEntity) {
		int empNo = employeeLeaveBalanceEntity.getEmpNo();
		String query = "SELECT * FROM emp_leave_balance where empNo = ";
		EmployeeLeaveBalanceEntity balanceEntity = JdbcTemplate.queryForObject(query + empNo,
				new BeanPropertyRowMapper<EmployeeLeaveBalanceEntity>(EmployeeLeaveBalanceEntity.class));
		return balanceEntity;
	}
	
	/**
	 * 
	 * @param entity
	 * @return
	 */
	@Override
	public String isLeaveAlreadyApplied(EmployeeLeaveRequestEntity entity) {
		//String checkLeaveQuery="select leaveDates from emp_leave_history where empid="+entity.getEmployeeId()+" and  leaveDates  like  '%?%'";
		String leaveDates=entity.getLeaveDates();
		//Kepp the Dates in an array to match with the leaves
		String leaveDatesToken[]=leaveDates.split(",");
		String message="";
		for(String cdate:leaveDatesToken) {
			  try {
				  String checkLeaveQuery="select leaveDates from emp_leave_requests_tbl where empid="+entity.getEmployeeId()+" and  leaveDates  like  '%"+cdate+"%'";
				  JdbcTemplate.queryForObject(checkLeaveQuery,String.class);
				  message=message+cdate+",";
			  }catch(Exception ex){
				  ex.printStackTrace();
			  }
		}
		if(message.length()>0) {
			message="Sorry you have already applied leaves for these dates ("+message+") which are peding for approval.";
			return message;
		}
		for(String cdate:leaveDatesToken) {
			  try {
				  String checkLeaveQuery="select leaveDates from emp_leave_history where empid="+entity.getEmployeeId()+" and  leaveDates  like  '%"+cdate+"%'";
				  JdbcTemplate.queryForObject(checkLeaveQuery,String.class);
				  message=message+cdate+",";
			  }catch(Exception ex){
				  ex.printStackTrace();
			  }
		}
		if(message.length()>0) {
			message="Sorry you have already applied leaves for these dates ("+message+")";
			return message;
		}
		return "no";
	}
	
	/**
	 *  This method updates the number of leave on the basis 
	 *  of input and return remaining leaves in leave balance 
	 *  table........
	 *  
	 */
	private float updateLeaveBalance(String leaveType,float numberOfLeavs,String empid) {
		 String currentMonth=DateUtils.getCurrentDateSQLDB();
		 currentMonth=currentMonth.substring(0,currentMonth.length()-2);
		 String lsql="select * from emp_leave_balance where empid="+empid+" and leaveMonth like '%"+currentMonth+"%'";
		 LeaveBalanceEntity leaveBalance=(LeaveBalanceEntity)JdbcTemplate.queryForObject(lsql, new BeanPropertyRowMapper(LeaveBalanceEntity.class));
		 float remainingBalance=0;
		 if(leaveType.equals(BASApplicationConstants.CL_LEAVE)) {
			 float currentBalance=leaveBalance.getCl();
			 remainingBalance=currentBalance-numberOfLeavs;
			 float noflupdate=numberOfLeavs;
			 if(remainingBalance<=0){
				 noflupdate=0;
			 }else{
				 noflupdate=remainingBalance;
			 }
			 String usql="update emp_leave_balance set cl=? where empid="+empid+" and leaveMonth like '%"+currentMonth+"%'";
			 int status=JdbcTemplate.update(usql,new Object[]{noflupdate});
			 if(status==0){
				 throw new RuntimeException("Sorry leave balance "+ BASApplicationConstants.EL_LEAVE+" could not be updated....");
			 }
		 }else if(leaveType.equals(BASApplicationConstants.OD_LEAVE)) { 
			 float currentodBalance=leaveBalance.getOd();
			 remainingBalance=currentodBalance-numberOfLeavs;
			 float noflupdate=numberOfLeavs;
			 if(remainingBalance<=0){
				 noflupdate=0;
			 }else{
				 noflupdate=remainingBalance;
			 }
			 String usql="update emp_leave_balance set od=? where empid="+empid+" and leaveMonth like '%"+currentMonth+"%'";
			 int status=JdbcTemplate.update(usql,new Object[]{noflupdate});
			 if(status==0){
				 throw new RuntimeException("Sorry leave balance "+ BASApplicationConstants.OD_LEAVE+"  could not be updated....");
			 }
		 }
		 else if(leaveType.equals(BASApplicationConstants.EL_LEAVE)) { 
			 float currentBalance=leaveBalance.getEl();
			 remainingBalance=currentBalance-numberOfLeavs;
			 float noflupdate=numberOfLeavs;
			 if(remainingBalance<=0){
				 noflupdate=0;
			 }else{
				 noflupdate=remainingBalance;
			 }
			 String usql="update emp_leave_balance set el=? where empid="+empid+" and leaveMonth like '%"+currentMonth+"%'";
			 int status=JdbcTemplate.update(usql,new Object[]{noflupdate});
			 if(status==0){
				 throw new RuntimeException("Sorry leave balance "+ BASApplicationConstants.EL_LEAVE+"  could not be updated....");
			 }
		 }else if(leaveType.equals(BASApplicationConstants.SL_LEAVE)) { 
			 String usql="update emp_leave_balance set sl=? where empid="+empid+" and leaveMonth like '%"+currentMonth+"%'";
			 int status=JdbcTemplate.update(usql,new Object[]{0});
			 if(status==0){
				 throw new RuntimeException("Sorry leave balance "+ BASApplicationConstants.EL_LEAVE+"  could not be updated....");
			 }
			 return 0;
		 }
		return remainingBalance;
	}
	
	
	private boolean isTakenShortLeaveInThisMonth(String empid){
		 String currentMonth=DateUtils.getCurrentDateSQLDB();
		 currentMonth=currentMonth.substring(0,currentMonth.length()-2);
		 String sql="select requestId from emp_leave_history where leaveDates like '%"+currentMonth+"%' and leaveType=? and  empid="+empid;
		 try {
			 	JdbcTemplate.queryForObject(sql,new Object[]{BASApplicationConstants.SL_LEAVE},String.class);
			 	return true;
		 }catch(Exception ex){
			 System.out.println(ex.getMessage());
		 }
		 return false;
	}
	
	private String findtakenShortLeaveDateInThisMonth(String empid){
		 String userMessage="";
		 String currentMonth=DateUtils.getCurrentDateSQLDB();
		 currentMonth=currentMonth.substring(0,currentMonth.length()-2);
		 String sql="select leaveDates from emp_leave_requests_tbl where leaveDates  like '%"+currentMonth+"%' and leaveType=? and  empid="+empid;
		 List<String> leaveDates=new ArrayList<String>();
		 String leaveDate="Not Known";
		 try {
			 	leaveDates=JdbcTemplate.queryForList(sql,new Object[]{BASApplicationConstants.SL_LEAVE},String.class);
			 	leaveDate=leaveDates.get(0);
			 	userMessage="Sorry , you have already applied SL on "+leaveDate+" but it is not approved so far.";
		 }catch(Exception ex){
			 System.out.println(ex.getMessage());
			 sql="select leaveDates from emp_leave_history where leaveDates  like '%"+currentMonth+"%' and leaveType=? and  empid="+empid;
			 try {
				 	leaveDates=JdbcTemplate.queryForList(sql,new Object[]{BASApplicationConstants.SL_LEAVE},String.class);
				 	leaveDate=leaveDates.get(0);
				 	userMessage="Sorry , you have already applied SL on "+leaveDate+" of this month "+currentMonth;
			 }catch(Exception xx){
				 System.out.println(xx.getMessage());
			 }
		 }
		 return userMessage;
	}

	@Override
	public MessageStatusEntity saveLeaveRequest(EmployeeLeaveRequestEntity entity) {
		boolean b=TransactionSynchronizationManager.isActualTransactionActive();
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		if(b){
			System.out.println("@@)((((((((((_________________________________________@@@@@@@@@@@@@@@@@@");
		}
	     //	System.out.println("saving leave request for no of days" + entity.getTotalDays());
		MessageStatusEntity messageStatusEntity=new MessageStatusEntity();
		/* String leaveResult=isLeaveAlreadyApplied(entity);
		 if(!"no".equalsIgnoreCase(leaveResult)){
			 messageStatusEntity.setStatus("fail");
			 messageStatusEntity.setMessage(leaveResult);
			 return messageStatusEntity;
		 }*/
		 String lsql = "insert into emp_leave_requests_tbl(requestId,empid,empName,deptt,leaveType,leaveTypeDescription,leaveCategory,leaveMeeting,lstatus,purpose,leaveFrom,leaveTo,leaveDates,lwp,cel,ccl,inAccount,totalDays,lwpDays,elDays,leaveDays,sandwitch,addressTelNoLeave,mobile,doapplication,hrid,hrApproval,reportingManager,managerApproval,managementid,manegementApproval,changeLeaveType,ccto,doe,dom,description,approvedBy) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		 String lsqlb = "insert into emp_applied_leave_requests_tbl(requestId,empid,empName,deptt,leaveType,leaveTypeDescription,leaveCategory,leaveMeeting,lstatus,purpose,leaveFrom,leaveTo,leaveDates,lwp,cel,ccl,inAccount,totalDays,lwpDays,elDays,leaveDays,sandwitch,addressTelNoLeave,mobile,doapplication,hrid,hrApproval,reportingManager,managerApproval,managementid,manegementApproval,changeLeaveType,ccto,doe,dom,description,approvedBy) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		 long pid=0;
		 String nextRequestId=JdbcTemplate.queryForObject("select requestId from leave_request_id_gen where sno=1", String.class);
		   if(nextRequestId!=null && nextRequestId.length()>0) {
			   pid=Long.parseLong(nextRequestId);
			   pid++;
			   nextRequestId="L"+pid;
			   entity.setRequestID(nextRequestId);
		   }
		    //In case of LWP do not update the leave balance
			if(entity.getLeaveType().equalsIgnoreCase(BASApplicationConstants.LWP_LEAVE)){
				setComputedDatesForLeaveTypes(entity,entity.getTotalDays(),BASApplicationConstants.LWP_LEAVE);
		   }else if(entity.getLeaveType().equalsIgnoreCase(BASApplicationConstants.OD_LEAVE)){
				setComputedDatesForLeaveTypes(entity,0,BASApplicationConstants.OD_LEAVE);
				//We have to work on this since it is not clear so far................
				//////////////////////TODO
			    float   lwp=updateLeaveBalance(BASApplicationConstants.OD_LEAVE,entity.getTotalDays(),entity.getEmployeeId());
		   }else if(entity.getLeaveType().equalsIgnoreCase(BASApplicationConstants.COMPENSATORY_LEAVE)){
				setComputedDatesForLeaveTypes(entity,0,BASApplicationConstants.COMPENSATORY_LEAVE);
		   }
			else if(entity.getLeaveType().equalsIgnoreCase(BASApplicationConstants.SL_LEAVE)) {
			   boolean status=isTakenShortLeaveInThisMonth(entity.getEmployeeId());
			   if(status){
				   float   lwp=updateLeaveBalance(entity.getLeaveType(),BASApplicationConstants.SL_LEAVE_DAY,entity.getEmployeeId());
				   if(lwp<0){
					   lwp=-lwp;
				   }else{
					   lwp=0;
				   }
				   entity.setLwp(lwp);
				   messageStatusEntity.setMessage("He has already taken SL for this month so this leave will adusted as per CL!.");
				   entity.setLeaveDays("");
				   String leaveDates=entity.getLeaveDates();
				   String leaveDatesTokens[]=leaveDates.split(",");
				   String cdateTokens[]=leaveDatesTokens[0].split("-");
				   entity.setLeaveDays(cdateTokens[2]+"("+BASApplicationConstants.SL_LEAVE_DAY+")");
			   }else{
				   //first time leave would be not considered as lwp since one sl is allowed in a month
				   updateLeaveBalance(entity.getLeaveType(),BASApplicationConstants.SL_LEAVE_DAY,entity.getEmployeeId());
				   entity.setLwpDays("");
				   String leaveDates=entity.getLeaveDates();
				   String leaveDatesTokens[]=leaveDates.split(",");
				   String cdateTokens[]=leaveDatesTokens[0].split("-");
				   entity.setLeaveDays(cdateTokens[2]+"(.5)");
			   }
		   } else{
			   float   lwp=updateLeaveBalance(entity.getLeaveType(),entity.getTotalDays(),entity.getEmployeeId());
			   if(lwp<0){
				   lwp=-lwp;
			   }else{
				   lwp=0;
			   }
			   setComputedDatesForLeaveTypes(entity,lwp,entity.getLeaveType());
			   /////////////////////////
			   entity.setLwp(lwp);
		   }
			
			Object acDate=null;
			if(entity.getInAccount()!=null)
			acDate=DateUtils.getCurrentCalendarDate(entity.getInAccount());
			
		   Object[] data = new Object[] { entity.getRequestID(), entity.getEmployeeId(), entity.getEmpName(),
					entity.getDepartment(), entity.getLeaveType(), entity.getOtherLeaveDescription(),
					entity.getLeaveCategory(), entity.getLeaveMeeting(), entity.getLstatus(), entity.getPurpose(),
					entity.getLeaveFrom(), entity.getLeaveTo(), entity.getLeaveDates(), entity.getLwp(),entity.getCel(),entity.getTotalDays()-entity.getLwp()-entity.getCel(),acDate,
					entity.getTotalDays(),entity.getLwpDays(),entity.getElDays(),entity.getLeaveDays(),entity.getSandwitch(), entity.getAddress(), entity.getMobile(),
					entity.getDoapplication(), entity.getHrManagerId(),entity.getHrApproval(), entity.getManagerId(),
					entity.getManagerApproval(),entity.getManagementid(),entity.getManagementApproval(),"NA",entity.getCCTo(), entity.getDoapplication(), entity.getDom(), "NA", entity.getUserid() };
		   try {
			   	JdbcTemplate.update(lsql, data);
			 	JdbcTemplate.update(lsqlb, data);
			    JdbcTemplate.update("update leave_request_id_gen set requestId=? where sno=1", new Object[]{ pid});
		   }catch(DuplicateKeyException duplicateKeyException){
			   messageStatusEntity.setStatus("fail");
			   messageStatusEntity.setMessage("Sorry you have already applied leaves for these dates ("+entity.getLeaveFrom()+")");
			   return messageStatusEntity;
		   }
		   messageStatusEntity.setStatus("success");
		   messageStatusEntity.setMessage("Your leave application have been submitted successfully , Thank you!");
		   return messageStatusEntity;
	}
	
	/**
	 *   Method which is used to compute number of leave dd and lwp dd 
	 *   which is specifically needed for one page leave report for the
	 *   employee.... 
	 *   
	 * @param entity
	 * @param lwp
	 */
	private void setComputedDatesForLeaveTypes(EmployeeLeaveRequestEntity entity,float lwp,String leaveType) {
		 //computing dates for one page leave history report
		   String leaveDates=entity.getLeaveDates();
		   String leaveDatesToken[]=leaveDates.split(",");
		   //handle dates for Leaves first.
		   float totaLeavesType=entity.getTotalDays()-lwp;//3.5 and lwp=1.5
		   int totalLeaveCount=(int)Math.ceil(totaLeavesType);
		   int totalLeaveLessCount=(int)Math.floor(totaLeavesType);
		   StringBuilder compDateBuilder=new StringBuilder();
		   for(int x=0;x<totalLeaveCount;x++) {
			   String compDate=leaveDatesToken[x];
			    String dd=compDate.split("-")[2];
			    if((x+1)==totalLeaveCount) { //last date
			    	   if(totalLeaveCount>totalLeaveLessCount){
			    	    	//float tempLeave=totalLeaveCount-totaLeavesType;
			    		   float tempLeave=(float)(totaLeavesType - Math.floor(totaLeavesType)); 
			    		   compDateBuilder.append(dd+"("+tempLeave+"),");
			    	     } else{
			    	    	 compDateBuilder.append(dd+"(1),");
			    	     }
			    }else {
			    	compDateBuilder.append(dd+"(1),");
			    }
		   }
		   //handle dates for LWP.
		   int totalLwpCount=(int)Math.ceil(lwp);
		   int totalLwpLessCount=(int)Math.floor(lwp);
		   //float 
		   //5->>2
		   StringBuilder compLwpDateBuilder=new StringBuilder();
		   for(int x=leaveDatesToken.length-totalLwpCount;x<leaveDatesToken.length;x++) {
			     String compDate=leaveDatesToken[x];
			     String dd=compDate.split("-")[2];
			     if(compDateBuilder.toString().length()==0 && ((x+1)==leaveDatesToken.length)) {
			    	  if(totalLwpCount>totalLwpLessCount){
			    	    	 float templwp=totalLwpCount-lwp;
			    	    	 compLwpDateBuilder.append(dd+"("+templwp+"),");
			    	     }else{
			    	    	 compLwpDateBuilder.append(dd+",");
			    	     } 
			     }
			     else if(compDateBuilder.toString().length()>0 && x==(leaveDatesToken.length-totalLwpCount)) { //first date
			    	     if(totalLwpCount>totalLwpLessCount){
			    	    	 //float templwp=totalLwpCount-lwp;
			    	    	   float templwp=(float)(lwp - Math.floor(lwp)); 
			    	    	 compLwpDateBuilder.append(dd+"("+templwp+"),");
			    	     }else{
			    	    	 compLwpDateBuilder.append(dd+"(1),");
			    	     }
			     }else {
			    	 compLwpDateBuilder.append(dd+"(1),");
			     }
		   }

		   if(leaveType.equalsIgnoreCase(BASApplicationConstants.LWP_LEAVE)) {
			   entity.setLwpDays(compLwpDateBuilder.toString());
			   entity.setLwp(lwp);
			   if(totaLeavesType==0){
				   entity.setLeaveDays("");
			   	}else {
			   		entity.setLeaveDays(compDateBuilder.toString());
			   	}
			 	entity.setCcl(totaLeavesType);
		   }
		   else if(leaveType.equalsIgnoreCase(BASApplicationConstants.EL_LEAVE)) {
			   entity.setLwpDays(compLwpDateBuilder.toString());
			   entity.setLwp(lwp);
			   if(totaLeavesType==0){
				   entity.setElDays("");
			   	}else {
			   	  entity.setElDays(compDateBuilder.toString());
			   	}
			   entity.setLeaveDays("");
			   entity.setCel(totaLeavesType);
		   }else {
			   if(totaLeavesType==0){
				   entity.setLeaveDays("");
			   	}else {
			   		entity.setLeaveDays(compDateBuilder.toString());
			   	}
			   entity.setElDays("");
			   //When CL is applied more that available CL the some leaves would be converted into LWP
			   if(lwp>0) {
				   entity.setLwpDays(compLwpDateBuilder.toString());
				   entity.setLwp(lwp);
			   }else {
				   		entity.setLwpDays("");
			   }
			   entity.setCcl(totaLeavesType);
		   }
}

	@Override
	public EmployeeDetailsEntity getReportingManagerForEmployee(int managerId) {
		// String sql = "SELECT manager_id from emp_manager_relation_tb where
		// employee_id = ?";
		// @SuppressWarnings("deprecation")
		// int managerId = JdbcTemplate.queryForInt(sql, new Object[]{empId});
		// System.out.println("employee id is "+empId+" and manager id is
		// "+managerId);
		String sql1 = "SELECT * FROM emp_db where id = ?";
		return JdbcTemplate.queryForObject(sql1, new Object[] { managerId },
				new BeanPropertyRowMapper<EmployeeDetailsEntity>(EmployeeDetailsEntity.class));
	}

	@Override
	public int findNoOfHolidays(Date leaveFrom, Date leaveTo) {
		System.out.println("getting holiday data from the database");
		int noOfHolidays = 0;
		String sql = "SELECT COUNT(*) from holiday_entry_tbl WHERE cdate BETWEEN ? AND ?";
		noOfHolidays = JdbcTemplate.queryForInt(sql, new Object[] { leaveFrom, leaveTo });
		System.out.println("No of holidays between the start and end date is: " + noOfHolidays);
		return noOfHolidays;
	}
	
	@Override
	public List<String> findfHolidaysDatesInBetween(Date leaveFrom, Date leaveTo) {
		System.out.println("getting holiday data from the database");
		int noOfHolidays = 0;
		String sql = "SELECT cdate from holiday_entry_tbl WHERE cdate BETWEEN ? AND ?";
		List<String> holidaysDateList=new ArrayList<String>();
		try {
			 holidaysDateList = JdbcTemplate.queryForList(sql, new Object[] { leaveFrom, leaveTo },String.class);
		}catch(Exception ex){
		}
		System.out.println("No of holidays between the start and end date is: " + noOfHolidays);
		return holidaysDateList;
	}

	@Override
	public int getReportingManagerId(int empId) {
		final String FIND_MANAGER_ID = "SELECT manager_id from emp_manager_relation_tb where employee_id = ?";
		return JdbcTemplate.queryForInt(FIND_MANAGER_ID, new Object[] { empId });
	}

	@Override
	public String getEmployeeName(int empId) {
		String sql = "SELECT name from emp_db where id = ?";
		EmployeeDetailsForm detailsForm = new EmployeeDetailsForm();
		detailsForm = JdbcTemplate.queryForObject(sql, new Object[] { empId },
				new BeanPropertyRowMapper<EmployeeDetailsForm>(EmployeeDetailsForm.class));
		return detailsForm.getName();
	}

	@Override
	public List<SuggestionOptionEntity> findEmployeeSuggestionOption(String searchword,String empid) {
		String query = "select ed.id as empid,ed.name,ed.designation,ed.department from emp_db as ed,emp_manager_relation_tb as em where ed.id=em.employee_id and em.hr_id=? and  (ed.name like '%" + searchword
				+ "%' or ed.id like '%" + searchword + "%' or ed.department like '%" + searchword + "%') order by ed.id LIMIT 5";
		List<SuggestionOptionEntity> suggestionOptionList = JdbcTemplate.query(query,new Object[]{empid}, new BeanPropertyRowMapper<SuggestionOptionEntity>(SuggestionOptionEntity.class));
		return suggestionOptionList;
	}
	
	@Override
	public String deleteEmployeeLwpByDate(String lwpDate,String empid) {
		String query = "delete from emp_leave_requests_tbl where empid=? and leaveFrom=? and leaveTo=? and leaveType='LWP'";
		//We have to delete the data from the  emp_applied_leave_requests_tbl
		int p=JdbcTemplate.update(query,new Object[]{empid,DateUtils.dataConversionStrToDbFormat(lwpDate),DateUtils.dataConversionStrToDbFormat(lwpDate)});
		return p==1?"success":"failed";
	}
	
	@Override
	public List<SuggestionOptionEntity> findEmplyeeSuggestionOptionForLeaveHistory(String searchword) {
		String query = "select id as empid,name,designation,department,email,phoneNumber from emp_db where name like '%" + searchword
				+ "%' or id like '%" + searchword + "%' or department like '%" + searchword + "%' order by id LIMIT 5";
		/*if(searchword==null || searchword.trim().length()==0){
			query="select id as empid,name,designation,department,email,phoneNumber from emp_db";
		}*/
		List<SuggestionOptionEntity> suggestionOptionList = JdbcTemplate.query(query,
				new BeanPropertyRowMapper<SuggestionOptionEntity>(SuggestionOptionEntity.class));
		return suggestionOptionList;
	}
	
	@Override
	public List<SuggestionManagerOptionEntity> findEmplyeeSuggestionManager(String searchword, String manager_id) {
		// Select only those employees which the manager is supposed to see
		String query = "select e.id as empid, e.name, e.designation, e.department from emp_db e "
				+ "where e.id in (select m.employee_id from emp_manager_relation_tb m where m.manager_id = "+ manager_id +") "
						+ "AND (e.name like '%" + searchword
						+ "%' or e.id like '%" + searchword 
						+ "%' or e.department like '%" + searchword 
						+ "%') order by e.id";
		List<SuggestionManagerOptionEntity> suggestionOptionList = JdbcTemplate.query(query,
				new BeanPropertyRowMapper<SuggestionManagerOptionEntity>(SuggestionManagerOptionEntity.class));
		return suggestionOptionList;
	}


	@Override
	@Loggable
	public EmployeeLeaveDetailEntity findEmployeeLeaveDetail(String eid) {
		String sql = "select e.id as eid,e.name,e.type,e.designation,e.doj,e.department,e.paddress,e.phoneNumber as mobile,l.CL,l.EL,l.SL,l.OD,m.manager_id as managerId,hr_id as hrid from emp_db as e,emp_leave_balance as l,emp_manager_relation_tb as m  where   m.employee_id=e.id and l.empid=e.id and e.id=?";
		EmployeeLeaveDetailEntity employeeLeaveDetail =null;
		try {
		 employeeLeaveDetail = JdbcTemplate.queryForObject(sql, new Object[] { eid },
				new BeanPropertyRowMapper<EmployeeLeaveDetailEntity>(EmployeeLeaveDetailEntity.class));
		String query = "select name from emp_db where id=" + employeeLeaveDetail.getManagerId();
		String managerName = JdbcTemplate.queryForObject(query, String.class);
		employeeLeaveDetail.setManagerName(managerName);
		String takenLeaveDate=findtakenShortLeaveDateInThisMonth(eid);
		employeeLeaveDetail.setSlDate(takenLeaveDate);
		String pel = JdbcTemplate.queryForObject("select el from emp_pre_leave_balance_tbl where syear like '%"+DateUtils.getPreviousYear()+"%' and empid="+eid,String.class);
		employeeLeaveDetail.setPel(pel);
		employeeLeaveDetail.setEl(Float.parseFloat(employeeLeaveDetail.getEl())+(Float.parseFloat(pel))+"");
		}catch(EmptyResultDataAccessException exe){
			System.out.println(exe.getMessage());
			if(employeeLeaveDetail!=null)
			employeeLeaveDetail.setPel("0");
		}
		return employeeLeaveDetail;
	}
	
	
	@Override
	@Loggable
	public AllEmployeeDetailsEntity findEmployeeDetails(String eid) {
		String sql = "select * from emp_db where id=?";
		AllEmployeeDetailsEntity employeeDetail =null;
		
		 employeeDetail = JdbcTemplate.queryForObject(sql, new Object[] { eid },
				new BeanPropertyRowMapper<AllEmployeeDetailsEntity>(AllEmployeeDetailsEntity.class));
		
		
		return employeeDetail;
	}
	
	/**
	 * Returning the details of a selected employee for a manager intending to apply for leave of his employees behalf
	 * 
	 * @param Employee id
	 * @return Leave Details
	 */
	@Override
	@Loggable
	public EmployeeLeaveDetailEntity findEmployeeLeaveDetailForManager(String eid) {
		
		// The eid is already filtered by the manager 
		String sql = "select e.id as eid,e.name,e.type,e.designation,e.doj,e.department,e.paddress,e.phoneNumber as mobile,l.CL,l.EL,l.SL,l.OD from emp_db as e,emp_leave_balance as l where l.empid=e.id and e.id=?";
		EmployeeLeaveDetailEntity employeeLeaveDetail =null;
		try {
		 employeeLeaveDetail = JdbcTemplate.queryForObject(sql, new Object[] { eid },
				new BeanPropertyRowMapper<EmployeeLeaveDetailEntity>(EmployeeLeaveDetailEntity.class));
		 
		 //String query = "select name from emp_db where id=" + employeeLeaveDetail.getManagerId();
		 //String managerName = JdbcTemplate.queryForObject(query, String.class);
		 //employeeLeaveDetail.setManagerName(managerName);
		
		String takenLeaveDate=findtakenShortLeaveDateInThisMonth(eid);
		employeeLeaveDetail.setSlDate(takenLeaveDate);
		}catch(EmptyResultDataAccessException exe){
			
		}
		return employeeLeaveDetail;
	}


	@Override
	public int findTotaPendingLeaveCount(String role,String empid) {
		String sql="";
		if("managerApproval".equalsIgnoreCase(role)){
			 sql = "SELECT COUNT(*) FROM emp_leave_requests_tbl where "+role+" ='PENDING' and empid!="+Long.parseLong(empid)+" and reportingManager='"+empid+"'";
		}else if("hrApproval".equalsIgnoreCase(role)){
			 sql = "SELECT COUNT(*) FROM emp_leave_requests_tbl where "+role+" ='PENDING' and empid!="+Long.parseLong(empid)+" and hrid='"+empid+"'";
		}else if("manegementApproval".equalsIgnoreCase(role)){
			 sql = "SELECT COUNT(*) FROM emp_leave_requests_tbl where "+role+" ='PENDING' and empid!="+Long.parseLong(empid)+" and managementid='"+empid+"' and hrApproval='APPROVED'";
		}
		int total = JdbcTemplate.queryForInt(sql);
		return total;
	}


	@Override
	public void addAlternativeArrangementsFaculty(String[] name, String[] branch, String[] period, String[] subject,
			String[] date,String userid,Date doe,Date dom,String fid) {
		String sql="insert into faculty_Alternative_arrangements(name,doa,branch,subject,submitedby,period,fid,doe,dom,status) values(?,?,?,?,?,?,?,?,?,?)";
		for(int i=0;i<name.length;i++){
			Object data[]=new Object[]{name[i],date[i],branch[i],subject[i],userid,period[i],name[i],doe,dom,"Not Approved"}; 
			JdbcTemplate.update(sql,data);
		}
	}


	@Override
	public List<SubjectAlternativeArrangementsEntity> findAlternateArrangementPeriodFarFaculty(String username) {
		String sql="select *from faculty_alternative_arrangements where fid=? AND status=?";
		List<SubjectAlternativeArrangementsEntity> alternativeArrangementsEntities=JdbcTemplate.query(sql,new Object[]{username,"Not Approved"},new BeanPropertyRowMapper<SubjectAlternativeArrangementsEntity>(SubjectAlternativeArrangementsEntity.class));
		return alternativeArrangementsEntities;
	}


	@Override
	public String findFacultyById(String submitedby) {
		String sql="select name from emp_db where id=?";
		Integer id=Integer.parseInt(submitedby);
		String name=JdbcTemplate.queryForObject(sql,new Object[]{id}, String.class);
		return name;
	}


	@Override
	public String updateStatusForAlternateClass(String id, String status) {
		String sql=null;
		if(status=="Approved"){
			sql="update faculty_alternative_arrangements set status=? where fid=?";
			JdbcTemplate.update(sql, new Object[]{status,id});
		}
		else{
			sql="delete from faculty_alternative_arrangements where fid=?";
			JdbcTemplate.update(sql, new Object[]{id});
		}
		return "Succesfully Status "+status;
	}


	@Override
	public List<SubjectAlternativeArrangementsEntity> findAllAlternativeClassDetailsForHrHod(String empid) {
		String sql="select *from faculty_alternative_arrangements where submitedby=?";
		List<SubjectAlternativeArrangementsEntity> alternativeArrangementsEntities=JdbcTemplate.query(sql,new Object[]{empid},new BeanPropertyRowMapper<SubjectAlternativeArrangementsEntity>(SubjectAlternativeArrangementsEntity.class));
		return alternativeArrangementsEntities;
	}
 
	@Override
	public String updateBasicProfile(String eid, String dob,String mobile,String password,String email) {
			String updateProfile="update emp_db set email=?,dob=?,phoneNumber=? where id=?";
			JdbcTemplate.update(updateProfile, new Object[]{email,dob,mobile,eid});
			String updateLogin="update faculity_login_tbl set email=?,password=? where eid=?";
			JdbcTemplate.update(updateLogin, new Object[]{email,password,eid});
			return "updated";
	}



	
}
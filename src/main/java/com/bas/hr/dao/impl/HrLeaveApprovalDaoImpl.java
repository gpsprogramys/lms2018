package com.bas.hr.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.bas.common.constant.BASApplicationConstants;
import com.bas.common.constant.BaoConstants;
import com.bas.common.constant.LeaveStatus;
import com.bas.common.util.DateUtils;
import com.bas.employee.dao.entity.FaculityLeaveMasterEntity;
import com.bas.employee.dao.entity.LeaveBalanceEntity;
import com.bas.hr.dao.HrLeaveApprovalDao;
import com.bas.hr.entity.LeavesModifiedEntity;


@Repository("HrLeaveApprovalDaoImpl")
@Scope("singleton")
public class HrLeaveApprovalDaoImpl extends JdbcDaoSupport implements HrLeaveApprovalDao{
	
	
	@Autowired
	@Qualifier("sdatasource")
	public void setDataSourceInSuper(DataSource dataSource) {
		super.setDataSource(dataSource);
	}


	@Override
	public String rejectLeaveByLeaveRequestId(String leaveRequestId, String leaveRejectReason,String role) {
		String moveRowIntoHistory="INSERT INTO emp_leave_history select * from emp_leave_requests_tbl where requestId = '"+leaveRequestId+"'";
		String deleteRowByEmpid="delete from emp_leave_requests_tbl where requestId='"+leaveRequestId+"'";
		super.getJdbcTemplate().execute(moveRowIntoHistory);
		super.getJdbcTemplate().execute(deleteRowByEmpid);
		String squery ="select lh.empid,lh.leaveType,lh.leaveCategory,lh.totalDays,lh.lwpDays,lh.lwp,lh.inAccount,lh.leaveDays from emp_leave_history as lh where lh.requestId=?";
		FaculityLeaveMasterEntity faculityLeaveMasterEntity = super.getJdbcTemplate().queryForObject(squery,new Object[] {leaveRequestId},new BeanPropertyRowMapper<FaculityLeaveMasterEntity>(FaculityLeaveMasterEntity.class));
		if(BASApplicationConstants.CL_LEAVE.equalsIgnoreCase(faculityLeaveMasterEntity.getLeaveType())){
			float recoverBalance=faculityLeaveMasterEntity.getTotalDays()-faculityLeaveMasterEntity.getLwp();
		    String recoverBalanceQuery="update emp_leave_balance set CL=CL+"+recoverBalance+" where empid="+faculityLeaveMasterEntity.getEmpid();
		    super.getJdbcTemplate().update(recoverBalanceQuery);
		}else if(BASApplicationConstants.SL_LEAVE.equalsIgnoreCase(faculityLeaveMasterEntity.getLeaveType())){
			if(faculityLeaveMasterEntity.getLwp()==0){
				String recoverBalanceQuery="update emp_leave_balance set SL=1 where empid="+faculityLeaveMasterEntity.getEmpid();
			    super.getJdbcTemplate().update(recoverBalanceQuery);
			}
			//TODO ->>>>IF SL Converts into EL
		}else if(BASApplicationConstants.EL_LEAVE.equalsIgnoreCase(faculityLeaveMasterEntity.getLeaveType())){
			float recoverBalance=faculityLeaveMasterEntity.getTotalDays()-faculityLeaveMasterEntity.getLwp();
		    String recoverBalanceQuery="update emp_leave_balance set EL=EL+"+recoverBalance+" where empid="+faculityLeaveMasterEntity.getEmpid();
		    super.getJdbcTemplate().update(recoverBalanceQuery);
		}
		String updateLeaveStatus="";
		if(role.equalsIgnoreCase(BASApplicationConstants.MANAGER)) {
		 updateLeaveStatus="update emp_leave_history set description=?,managerApproval=?,dom=? where requestId='"+leaveRequestId+"'";
		}else if(role.equalsIgnoreCase(BASApplicationConstants.HR)) {
			updateLeaveStatus="update emp_leave_history set description=?,hrApproval=?,dom=? where requestId='"+leaveRequestId+"'";
		}else if(role.equalsIgnoreCase(BASApplicationConstants.MANAGEMENT)) {
			updateLeaveStatus="update emp_leave_history set description=?,manegementApproval=?,dom=? where requestId='"+leaveRequestId+"'";
		}
		
		super.getJdbcTemplate().update(updateLeaveStatus,new Object[]{leaveRejectReason,LeaveStatus.REJECT_STATUS,DateUtils.getCurrentTimestatmp()});
		return BaoConstants.SUCCESS;
	}
	
	/**
	 *  This method return the all the leave balance information of an employee 
	 * @param empid id for current employee
	 * @return
	 */
	private LeaveBalanceEntity findLeaveCurrentLeaveBalanceByEmpId(String empid) {
		 String currentMonth=DateUtils.getCurrentDateSQLDB();
		 currentMonth=currentMonth.substring(0,currentMonth.length()-2);
		 String lsql="select * from emp_leave_balance where empid="+empid+" and leaveMonth like '%"+currentMonth+"%'";
		 LeaveBalanceEntity leaveBalance=(LeaveBalanceEntity)super.getJdbcTemplate().queryForObject(lsql, new BeanPropertyRowMapper(LeaveBalanceEntity.class));
		 return leaveBalance;
	}
	
	private void updateEmployeeEL(String empid,float el) {
		 String currentMonth=DateUtils.getCurrentDateSQLDB();
		 currentMonth=currentMonth.substring(0,currentMonth.length()-2);
		 String lsql="update emp_leave_balance set EL=EL-"+el+"  where empid="+empid+" and leaveMonth like '%"+currentMonth+"%'";
		 super.getJdbcTemplate().update(lsql);
	}
	
	private void updateEmployeeCL(String empid,float cl) {
		 String currentMonth=DateUtils.getCurrentDateSQLDB();
		 currentMonth=currentMonth.substring(0,currentMonth.length()-2);
		 String lsql="update emp_leave_balance set CL=CL-"+cl+"  where empid="+empid+" and leaveMonth like '%"+currentMonth+"%'";
		 super.getJdbcTemplate().update(lsql);
	}
	
	@Override
	public  LeavesModifiedEntity findModifiedLeaveByRequestId(String requestId) {
		 String lsql="select * from leaves_modified_tbl where requestid=? LIMIT 1";
		 LeavesModifiedEntity leavesModifiedEntity=new LeavesModifiedEntity();
		 try {
			 leavesModifiedEntity=super.getJdbcTemplate().queryForObject(lsql,new Object[]{requestId},new BeanPropertyRowMapper<LeavesModifiedEntity>(LeavesModifiedEntity.class));
		 }catch(Exception ex){
			 	System.out.println(ex.getMessage());
		 }
		 return  leavesModifiedEntity;
	}
	
	@Override
	public String approveLeaveByManagerRequestId(String leaveRequestId, String leaveApproveReason){
		String updateLeaveStatus="update emp_leave_requests_tbl set description=?,managerApproval=?,dom=? where requestId='"+leaveRequestId+"'";
		super.getJdbcTemplate().update(updateLeaveStatus,new Object[]{leaveApproveReason,LeaveStatus.APPROVED_STATUS,DateUtils.getCurrentTimestatmp()});
		return BaoConstants.SUCCESS;
	}
	
	@Override
	public String approveLeaveByManagementRequestId(String leaveRequestId, String leaveApproveReason){
		String updateLeaveStatus="update emp_leave_requests_tbl set mcomment=?,manegementApproval=?,dom=? where requestId='"+leaveRequestId+"'";
		super.getJdbcTemplate().update(updateLeaveStatus,new Object[]{leaveApproveReason,LeaveStatus.APPROVED_STATUS,DateUtils.getCurrentTimestatmp()});
		//Here management is final approver for the leave!!!
		String moveRowIntoHistory="INSERT INTO emp_leave_history select * from emp_leave_requests_tbl where requestId = '"+leaveRequestId+"'";
		super.getJdbcTemplate().execute(moveRowIntoHistory);
		String deleteRowByEmpid="delete from emp_leave_requests_tbl where requestId='"+leaveRequestId+"'";
		super.getJdbcTemplate().execute(deleteRowByEmpid);
		return BaoConstants.SUCCESS;
	}
	
	
	@Override
	public String approveLeaveWithLeaveRequestIdByHRManagement(String leaveRequestId, String leaveApproveReason,
			String spChangeLeaveType,String changeLeaveType,final float cl,final float el,final float plwp,String approveAthority){
		leaveRequestId=leaveRequestId.trim();
		String insertModifyLeavesQuery="insert into leaves_modified_tbl(requestid,cl,el,lwp,changeType,description,dom,doe,adjustedBy) values(?,?,?,?,?,?,?,?,?)";
		Object data[]=new Object[]{leaveRequestId,cl,el,plwp,changeLeaveType,leaveApproveReason,DateUtils.getCurrentTimestatmp(),DateUtils.getCurrentTimestatmp(),approveAthority,};
		super.getJdbcTemplate().update(insertModifyLeavesQuery,data);
		//String modifyModifyLeavesQuery="update  leaves_modified_tbl set cl=?,el,lwp,changeType,description,dom,doe,adjustedBy)";
		//Logic to update the status of the leave request by HR & MANAGEMENT
		String updateLeaveStatus="update emp_leave_requests_tbl set description=?,hrApproval=?,acomment=?,managerApproval=?,dom=? where requestId='"+leaveRequestId+"'";
		if("management".equalsIgnoreCase(approveAthority)){
			 updateLeaveStatus="update emp_leave_requests_tbl set description=?,manegementApproval=?,dom=? where requestId='"+leaveRequestId+"'";
			 super.getJdbcTemplate().update(updateLeaveStatus,new Object[]{leaveApproveReason,LeaveStatus.APPROVED_STATUS,DateUtils.getCurrentTimestatmp()});
		}else {
				String managerApprovalStatus="select managerApproval from emp_leave_requests_tbl where requestId='"+leaveRequestId+"'";
				if(managerApprovalStatus==null || !managerApprovalStatus.equalsIgnoreCase(LeaveStatus.APPROVED_STATUS)) {
					managerApprovalStatus=BASApplicationConstants.DIRECTLY_APPROVED_BY_HR;
				}
			    super.getJdbcTemplate().update(updateLeaveStatus,new Object[]{leaveApproveReason,LeaveStatus.APPROVED_STATUS,managerApprovalStatus,LeaveStatus.APPROVED_STATUS,DateUtils.getCurrentTimestatmp()});
		}
		String squery ="select lh.empid,lh.ccl,lh.leaveType from emp_leave_requests_tbl as lh where lh.requestId=?";
	    FaculityLeaveMasterEntity faculityLeaveMasterEntity = super.getJdbcTemplate().queryForObject(squery,new Object[] {leaveRequestId},new BeanPropertyRowMapper<FaculityLeaveMasterEntity>(FaculityLeaveMasterEntity.class));
		//special case when employee apply CL and OD and it;s has to convert into SL
		if(spChangeLeaveType!=null && !spChangeLeaveType.equalsIgnoreCase("NONE")) {
			       String updateLeaveType="update emp_leave_requests_tbl set leaveType=?,dom=? where requestId='"+leaveRequestId+"'";
					if(BASApplicationConstants.SL_LEAVE.equals(spChangeLeaveType)){
						String updateLeaveBalance="update emp_leave_balance set CL=CL+.5,SL=0 where empid="+faculityLeaveMasterEntity.getEmpid();
						super.getJdbcTemplate().update(updateLeaveBalance);
					}
					super.getJdbcTemplate().update(updateLeaveType,new Object[]{spChangeLeaveType,DateUtils.getCurrentTimestatmp()});
					 return BaoConstants.SUCCESS;
		}
		 
	    float balanceDiff=0;
	    float balanceDiffCel=0;
	    float ccl=faculityLeaveMasterEntity.getCcl();
	    if(!faculityLeaveMasterEntity.getLeaveType().equals(BASApplicationConstants.CL_LEAVE) && ccl>0){
	    	   if(cl==0 && el==0){
	    		   return BaoConstants.SUCCESS;
	    	   }else{
	    		   balanceDiffCel=-cl;
	    		    balanceDiff =-el;
	    	   }
	    }else {
				   balanceDiff=ccl-cl;
				if(ccl==0)
					balanceDiff=0;
				
				 float cel=faculityLeaveMasterEntity.getCel();
				 balanceDiffCel=cel-el;
				if(cel==0)
					balanceDiffCel=0;
	    }
		if(balanceDiffCel>0 && balanceDiff>0){	
			String updateLeaveBalance="update emp_leave_balance set CL=CL+"+balanceDiff+", EL=EL+"+balanceDiffCel+"  where empid="+faculityLeaveMasterEntity.getEmpid();
			super.getJdbcTemplate().update(updateLeaveBalance);
		}else  if(balanceDiffCel<=0 && balanceDiff<=0){	
			balanceDiffCel=-balanceDiffCel;
			balanceDiff=-balanceDiff;
			String updateLeaveBalance="update emp_leave_balance set CL=CL-"+balanceDiff+", EL=EL-"+balanceDiffCel+"  where empid="+faculityLeaveMasterEntity.getEmpid();
			super.getJdbcTemplate().update(updateLeaveBalance);
		}else  if(balanceDiffCel<=0 && balanceDiff>0){	
			balanceDiffCel=-balanceDiffCel;
			String updateLeaveBalance="update emp_leave_balance set CL=CL+"+balanceDiff+", EL=EL-"+balanceDiffCel+"  where empid="+faculityLeaveMasterEntity.getEmpid();
			super.getJdbcTemplate().update(updateLeaveBalance);
		}		
		else  if(balanceDiffCel>0 && balanceDiff<=0){	
			balanceDiff=-balanceDiff;
			String updateLeaveBalance="update emp_leave_balance set CL=CL-"+balanceDiff+", EL=EL+"+balanceDiffCel+"  where empid="+faculityLeaveMasterEntity.getEmpid();
			super.getJdbcTemplate().update(updateLeaveBalance);
		}		
		return BaoConstants.SUCCESS;
	}
	
	/**
	 *   Method used to compute leaves as per final adjusted  formula as per 
	 *   user selections.
	 *   
	 */
	@Override
	public String approveLeaveByLeaveRequestId(String leaveRequestId, String leaveApproveReason,
			String changeLeaveType,final float cl,final float el,final float plwp) {
		//String moveRowIntoHistory="INSERT INTO emp_leave_history select * from emp_leave_requests_tbl where requestId = '"+leaveRequestId+"'";
		//super.getJdbcTemplate().execute(moveRowIntoHistory);
		//String deleteRowByEmpid="delete from emp_leave_requests_tbl where requestId='"+leaveRequestId+"'";
		//super.getJdbcTemplate().execute(deleteRowByEmpid);
		//String squery ="select lh.empid,lh.leaveType,lh.leaveCategory,lh.totalDays,lh.lwpDays,lh.lwp,lh.inAccount,lh.leaveDays from emp_leave_history as lh where lh.requestId=?";
		String squery ="select lh.empid,lh.leaveType,lh.leaveCategory,lh.totalDays,lh.lwpDays,lh.lwp,lh.inAccount,lh.leaveDays from emp_leave_requests_tbl as lh where lh.requestId=?";
		if(!BASApplicationConstants.NO_CHANGE.equalsIgnoreCase(changeLeaveType)) {
			    FaculityLeaveMasterEntity faculityLeaveMasterEntity = super.getJdbcTemplate().queryForObject(squery,new Object[] {leaveRequestId},new BeanPropertyRowMapper<FaculityLeaveMasterEntity>(FaculityLeaveMasterEntity.class));
				LeaveBalanceEntity leaveBalanceEntity=findLeaveCurrentLeaveBalanceByEmpId(faculityLeaveMasterEntity.getEmpid()+"");
				if(BASApplicationConstants.CL_LEAVE.equalsIgnoreCase(faculityLeaveMasterEntity.getLeaveType())) {
					if(BASApplicationConstants.EL_LEAVE.equalsIgnoreCase(changeLeaveType)) {
							float cel=0;
						    String elDays=null;
							String lwpdays=faculityLeaveMasterEntity.getLwpDays();
						    float lwp=faculityLeaveMasterEntity.getLwp();//2.5 , el=1.5
						    float tel=leaveBalanceEntity.getEl();  //1
						    float remel=tel-lwp;  //remel =>>remainning el /5 //5.5
						    if(remel>0) {
						    	lwp=0;
						    	elDays=lwpdays;
						    	lwpdays=null;
						    	cel=faculityLeaveMasterEntity.getLwp();
						    }else{
						    	lwp=-remel; //when lwp>=total el
						    	cel=faculityLeaveMasterEntity.getLwp();
						    	///2
						    	int lwpCount=(int)Math.ceil(lwp);
						    	int lwpLessCount=(int)Math.floor(lwp);
						    	String lwpdaysTokens[]=lwpdays.split(",");
						    	 elDays="";
						        for(int x=0;x<lwp;x++) { //
						        	elDays=elDays+lwpdaysTokens[lwpdaysTokens.length-1-x]+",";
						        }
						        lwpdays="";
						        for(int x=0;x<lwpdaysTokens.length-lwp;x++) { //
						        	lwpdays=lwpdays+lwpdaysTokens[x]+",";
						        }
						    }
						    String updateLeaveStatus="update emp_leave_requests_tbl set elDays=?,cel=?,lwp=?,lwpDays=?,changeLeaveType=? where requestId='"+leaveRequestId+"'";
						    super.getJdbcTemplate().update(updateLeaveStatus,new Object[]{elDays,cel,lwp,lwpdays,changeLeaveType});
						    updateEmployeeEL(leaveBalanceEntity.getEmpNo(),0F);
					}
				}else if(BASApplicationConstants.EL_LEAVE.equalsIgnoreCase(faculityLeaveMasterEntity.getLeaveType())) {
					 if(BASApplicationConstants.LWP_LEAVE.equalsIgnoreCase(changeLeaveType)) {
						    float totalDays=faculityLeaveMasterEntity.getTotalDays();
						    String totalLeaveDays=faculityLeaveMasterEntity.getLeaveDays();
							String lwpDays=faculityLeaveMasterEntity.getLwpDays();
							lwpDays=totalLeaveDays+lwpDays;
							float lwp=totalDays;
							updateEmployeeEL(faculityLeaveMasterEntity.getEmpid()+"",totalDays-faculityLeaveMasterEntity.getLwp()); 
							String updateLeaveStatus="update emp_leave_requests_tbl set leaveDays=?,lwp=?,lwpDays=?,changeLeaveType=? where requestId='"+leaveRequestId+"'";
							super.getJdbcTemplate().update(updateLeaveStatus,new Object[]{null,lwp,lwpDays,changeLeaveType});
					}
				}else{
					if(BASApplicationConstants.LWP_LEAVE.equalsIgnoreCase(changeLeaveType)){
					    float totalDays=faculityLeaveMasterEntity.getTotalDays();
					    String totalLeaveDays=faculityLeaveMasterEntity.getLeaveDays();
						String lwpDays=faculityLeaveMasterEntity.getLwpDays();
						lwpDays=totalLeaveDays+(lwpDays!=null?lwpDays:"");
						float lwp=totalDays;
						//updateEmployeeEL(faculityLeaveMasterEntity.getEmpid()+"",totalDays-faculityLeaveMasterEntity.getLwp()); 
						String updateLeaveStatus="update emp_leave_requests_tbl set leaveDays=?,lwp=?,lwpDays=?,changeLeaveType=? where requestId='"+leaveRequestId+"'";
						super.getJdbcTemplate().update(updateLeaveStatus,new Object[]{null,lwp,lwpDays,changeLeaveType});
				 }else if(BASApplicationConstants.CL_LEAVE.equalsIgnoreCase(changeLeaveType)){
					    float totalDays=faculityLeaveMasterEntity.getTotalDays();
						updateEmployeeCL(faculityLeaveMasterEntity.getEmpid()+"",totalDays); 
						String updateLeaveStatus="update emp_leave_requests_tbl set changeLeaveType=? where requestId='"+leaveRequestId+"'";
						super.getJdbcTemplate().update(updateLeaveStatus,new Object[]{changeLeaveType});
				 }else if(BASApplicationConstants.EL_LEAVE.equalsIgnoreCase(changeLeaveType)){
					 float totalDays=faculityLeaveMasterEntity.getTotalDays();
						updateEmployeeEL(faculityLeaveMasterEntity.getEmpid()+"",totalDays); 
						String updateLeaveStatus="update emp_leave_requests_tbl set changeLeaveType=? where requestId='"+leaveRequestId+"'";
						super.getJdbcTemplate().update(updateLeaveStatus,new Object[]{changeLeaveType});
				 }
					
			}
		}//end of no change
		
		//Approving the status of Management and moving record from leave_request_tbl into leave_history_tbl
		approveLeaveByManagementRequestId(leaveRequestId,leaveApproveReason);
	/*	String updateLeaveStatus="update emp_leave_requests_tbl set description=?,hrApproval=?,dom=? where requestId='"+leaveRequestId+"'";
		super.getJdbcTemplate().update(updateLeaveStatus,new Object[]{leaveApproveReason,LeaveStatus.APPROVED_STATUS,DateUtils.getCurrentTimestatmp()});*/
		return BaoConstants.SUCCESS;
	}
	
	
	/**
	 *   Method used to compute leaves as per final adjusted  formula as per 
	 *   user selections.
	 *   
	 */
	@Override
	public String approveLeaveByLeaveRequestIdTemp(String leaveRequestId, String leaveApproveReason,
			String changeLeaveType,final float mpcl,final float mpel,final float mplwp) {
		//String moveRowIntoHistory="INSERT INTO emp_leave_history select * from emp_leave_requests_tbl where requestId = '"+leaveRequestId+"'";
		//super.getJdbcTemplate().execute(moveRowIntoHistory);
		//String deleteRowByEmpid="delete from emp_leave_requests_tbl where requestId='"+leaveRequestId+"'";
		//super.getJdbcTemplate().execute(deleteRowByEmpid);
		//String squery ="select lh.empid,lh.leaveType,lh.leaveCategory,lh.totalDays,lh.lwpDays,lh.lwp,lh.inAccount,lh.leaveDays from emp_leave_history as lh where lh.requestId=?";
		String squery ="select lh.empid,lh.leaveType,lh.leaveCategory,lh.totalDays,lh.lwpDays,lh.lwp,lh.cel,lh.ccl,lh.inAccount,lh.leaveDays,lh.leaveDates from emp_leave_requests_tbl as lh where lh.requestId=?";
	     FaculityLeaveMasterEntity faculityLeaveMasterEntity = super.getJdbcTemplate().queryForObject(squery,new Object[] {leaveRequestId},new BeanPropertyRowMapper<FaculityLeaveMasterEntity>(FaculityLeaveMasterEntity.class));
		
	     List<String> leaveTypes=new ArrayList<String>();
	     leaveTypes.add("CL");
	     leaveTypes.add("EL");
	     leaveTypes.add("LWP");
	     
		if(!BASApplicationConstants.NO_CHANGE.equalsIgnoreCase(changeLeaveType) && !leaveTypes.contains(faculityLeaveMasterEntity.getLeaveType()) && mplwp>0) {
			String leaveDates=faculityLeaveMasterEntity.getLeaveDates();	
			List<String> dateTokensList=new ArrayList<String>();
			setComputedDatesForLeaveTypes(leaveDates, faculityLeaveMasterEntity.getTotalDays(), mplwp, dateTokensList);
			String updateLeaveComputatedDates="update emp_leave_requests_tbl set ccl=?,leaveDays=?,lwp=?,lwpDays=? where requestId='"+leaveRequestId+"'";
			Object[] pdata=new Object[]{mpcl,dateTokensList.get(0),mplwp,dateTokensList.get(1)};
			super.getJdbcTemplate().update(updateLeaveComputatedDates,pdata);
		}
		else if(!BASApplicationConstants.NO_CHANGE.equalsIgnoreCase(changeLeaveType)) {
			  LeaveBalanceEntity leaveBalanceEntity=findLeaveCurrentLeaveBalanceByEmpId(faculityLeaveMasterEntity.getEmpid()+"");
				//finding total leaves applied and checking whether mcl is less than available cls
				float totalAvailableCls=leaveBalanceEntity.getCl();
				String totalClLeavesDays=faculityLeaveMasterEntity.getLeaveDays();
				int totalClLeavesCountDays=0;
				if(totalClLeavesDays!=null && totalClLeavesDays.length()>0) {
					String totalDaysTokens[]=totalClLeavesDays.split(",");
					totalClLeavesCountDays=totalDaysTokens.length;
						for(String str:totalDaysTokens) {
							float cp=Float.parseFloat(str.substring(str.indexOf("(")+1,str.indexOf(")")));
							totalAvailableCls=totalAvailableCls+cp;
						}
				}
				//if modified cl is less than available CL of the employee
				//04(1),05(0.75),
				String totalDbClDays="";
				if(mpcl<totalAvailableCls) {
					  //float remainingCl=totalAvailableCls -mpcl; //1.75 ->>.25
					  String totalDaysTokens[]=totalClLeavesDays.split(",");
					  if(totalClLeavesCountDays==1) {
						  	//current computated cl into applied leaves request tables
						  	float ccl=Float.parseFloat(totalDaysTokens[0].substring(totalDaysTokens[0].indexOf("(")+1,totalDaysTokens[0].indexOf(")")));
						  	if((ccl-mpcl) >0) {
						  		 totalDbClDays=totalDaysTokens[0].substring(0,2)+"("+(ccl-mpcl)+")";
						  	}
					  }else{
						     int mpclcount=(int) Math.ceil(mpcl);
						     float tmpcl=mpcl;
						     for(int i=0;i<mpclcount;i++) {
						    	 if(tmpcl>=1) {
						    		 totalDbClDays=totalDbClDays+","+totalDaysTokens[i];  
						    	 }	 
						    	 else {
						    		 totalDbClDays=totalDbClDays+","+ totalDaysTokens[i].substring(0, 2)+"("+tmpcl+")";  
						    	 }		 
						    	 tmpcl=tmpcl-1;
						     }
					  }
				} //end of CL computation
				
				//compute total leaves dd from dates
				//2016-07-04,2016-07-05,2016-07-06
				String leaveDates=faculityLeaveMasterEntity.getLeaveDates();
				String ddDates="";
				String leaveDatesTokens[]=leaveDates.split(",");
				for(String dddate:leaveDatesTokens) {
						String dddateTokens[]=dddate.split("-");
						String dd=dddateTokens[2];
						ddDates=ddDates+dd+",";
				}
				
				//Case 1 when all CL ,EL,LWP is not zero
				if(mpcl>0 && mpel>0 && mplwp>0) {
								String cldates=ddDates+"-> ("+mpcl+")";
								String eldates=ddDates+"-> ("+mpel+")";
								String lwpdates=ddDates+"-> ("+mplwp+")";
								String updateLeaveComputatedDates="update emp_leave_requests_tbl set ccl=?,leaveDays=?,lwp=?,lwpDays=?,cel=?,elDays=? where requestId='"+leaveRequestId+"'";
								Object[] pdata=new Object[]{mpcl,cldates,mplwp,lwpdates,mpel,eldates};
								super.getJdbcTemplate().update(updateLeaveComputatedDates,pdata);
				}else if(mpcl>0  && mplwp>0 && mpel==0) {
						List<String> dateTokensList=new ArrayList<String>();
						setComputedDatesForLeaveTypes(leaveDates, faculityLeaveMasterEntity.getTotalDays(), mplwp, dateTokensList);
						String updateLeaveComputatedDates="update emp_leave_requests_tbl set ccl=?,leaveDays=?,lwp=?,lwpDays=?,cel=?,elDays=? where requestId='"+leaveRequestId+"'";
						Object[] pdata=new Object[]{mpcl,dateTokensList.get(0),mplwp,dateTokensList.get(1),0,""};
						super.getJdbcTemplate().update(updateLeaveComputatedDates,pdata);
				}else if(mpcl>0  && mpel>0 && mplwp==0) {
					List<String> dateTokensList=new ArrayList<String>();
					setComputedDatesForLeaveTypes(leaveDates, faculityLeaveMasterEntity.getTotalDays(), mpel, dateTokensList);
					String updateLeaveComputatedDates="update emp_leave_requests_tbl set ccl=?,leaveDays=?,lwp=?,lwpDays=?,cel=?,elDays=? where requestId='"+leaveRequestId+"'";
					Object[] pdata=new Object[]{mpcl,dateTokensList.get(0),0,"",mpel,dateTokensList.get(1)};
					super.getJdbcTemplate().update(updateLeaveComputatedDates,pdata);
				}else if(mpcl==0 && mpel>0 && mplwp>0) {
					List<String> dateTokensList=new ArrayList<String>();
					setComputedDatesForLeaveTypes(leaveDates, faculityLeaveMasterEntity.getTotalDays(), mplwp, dateTokensList);
					String updateLeaveComputatedDates="update emp_leave_requests_tbl set ccl=?,leaveDays=?,lwp=?,lwpDays=?,cel=?,elDays=? where requestId='"+leaveRequestId+"'";
					Object[] pdata=new Object[]{0,"",mplwp,dateTokensList.get(1),mpel,dateTokensList.get(0)};
					super.getJdbcTemplate().update(updateLeaveComputatedDates,pdata);
				}
				
				LeavesModifiedEntity leavesModifiedEntity=findModifiedLeaveByRequestId(leaveRequestId);
				float clbalanceDiff=leavesModifiedEntity.getCl()-mpcl;
				float elbalanceDiff=leavesModifiedEntity.getEl()-mpel;
				String updateLeaveBalance="";
				if(clbalanceDiff>0 && elbalanceDiff>0){
					 updateLeaveBalance="update emp_leave_balance set CL=CL+"+clbalanceDiff+", EL=EL+"+elbalanceDiff+"  where empid="+faculityLeaveMasterEntity.getEmpid();
				}else if(clbalanceDiff<=0 && elbalanceDiff<=0){
					 updateLeaveBalance="update emp_leave_balance set CL=CL-"+clbalanceDiff+", EL=EL-"+elbalanceDiff+"  where empid="+faculityLeaveMasterEntity.getEmpid();
				}else if(clbalanceDiff<=0 && elbalanceDiff>0){
					 updateLeaveBalance="update emp_leave_balance set CL=CL-"+clbalanceDiff+", EL=EL+"+elbalanceDiff+"  where empid="+faculityLeaveMasterEntity.getEmpid();
				} if(clbalanceDiff>0 && elbalanceDiff<=0){
					 updateLeaveBalance="update emp_leave_balance set CL=CL+"+clbalanceDiff+", EL=EL-"+elbalanceDiff+"  where empid="+faculityLeaveMasterEntity.getEmpid();
				}
				super.getJdbcTemplate().update(updateLeaveBalance);
	}
		
		//end of no change
		//Approving the status of Management and moving record from leave_request_tbl into leave_history_tbl
		approveLeaveByManagementRequestId(leaveRequestId,leaveApproveReason);
		//update Leave Balance Table
		
	/*	String updateLeaveStatus="update emp_leave_requests_tbl set description=?,hrApproval=?,dom=? where requestId='"+leaveRequestId+"'";
		super.getJdbcTemplate().update(updateLeaveStatus,new Object[]{leaveApproveReason,LeaveStatus.APPROVED_STATUS,DateUtils.getCurrentTimestatmp()});*/
		return BaoConstants.SUCCESS;
	}
	
	/**
	 *   Method which is used to compute number of leave dd and lwp dd 
	 *   which is specifically needed for one page leave report for the
	 *   employee.... 
	 *   
	 * @param leaveDates total leaves dates
	 * @totalDays totalDays leaves days 
	 * @param lwp  ->>> this is second leaves
	 */
	private void setComputedDatesForLeaveTypes(String leaveDates,float totalDays, float lwp,List<String> dateTokensList) {
		  //computing dates for one page leave history report
		  // String leaveDates=entity.getLeaveDates();
		   String leaveDatesToken[]=leaveDates.split(",");
		   //handle dates for Leaves first.
		   float totaLeavesType=totalDays-lwp;//3.5 and lwp=1.5
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
		   dateTokensList.add(compDateBuilder.toString());
		   dateTokensList.add(compLwpDateBuilder.toString());
	}
}

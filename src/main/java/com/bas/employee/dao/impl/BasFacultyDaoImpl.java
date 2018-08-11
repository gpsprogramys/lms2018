package com.bas.employee.dao.impl;

import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.bas.admin.dao.entity.DepartmentEntity;
import com.bas.admin.dao.entity.EmployeeMonthAttendanceEntity;
import com.bas.admin.dao.entity.FaculityDailyAttendanceReportEntity;
import com.bas.admin.dao.entity.FacultyManualAttendanceEntity;
import com.bas.admin.dao.entity.HolidayEntryEntity;
import com.bas.admin.dao.entity.OrganizationTimeEntity;
import com.bas.admin.dao.entity.PaginationFaculityDailyAttendanceReportEntity;
import com.bas.admin.web.controller.form.InOutTimeVO;
import com.bas.common.constant.BaoConstants;
import com.bas.common.constant.FaculityQuery;
import com.bas.common.constant.LeaveStatus;
import com.bas.common.dao.entity.EmployeeHeader;
import com.bas.common.util.DateUtils;
import com.bas.employee.dao.BasFacultyDao;
import com.bas.employee.dao.entity.EmployeeShowFormEntity;
import com.bas.employee.dao.entity.FaculityLeaveMasterEntity;
import com.bas.employee.dao.entity.FaculityLeaveMasterEntity1;
import com.bas.employee.dao.entity.FacultyAttendStatusEntity;
import com.bas.employee.dao.entity.FacultyEntity;
import com.bas.employee.dao.entity.FacultyEntity1;
import com.bas.employee.dao.entity.FacultyLeaveApprovalEntity;
import com.bas.employee.dao.entity.FacultyLeaveBalanceEntity1;
import com.bas.employee.dao.entity.FacultyLeaveTypeEntity;
import com.bas.employee.dao.entity.FacultySalaryMasterEntity;
import com.bas.employee.dao.entity.FacultyWorkingDaysEntity;
import com.bas.employee.dao.entity.LeaveBalanceEntity;
import com.bas.employee.dao.entity.ManualAttendanceEntity;
import com.bas.employee.dao.entity.ReportingManagerEntity;
import com.bas.employee.dao.entity.SubjectAlternativeArrangementsEntity;
import com.bas.employee.dao.query.EmployeeQuery;
import com.bas.employee.web.controller.form.FaculityLeaveMasterVO;
import com.bas.employee.web.controller.form.FacultyForm;
import com.bas.hr.dao.query.HRQuery;
import com.bas.hr.dao.query.ManagementQuery;

@Repository("BasFacultyDaoImpl")
@Scope("singleton")
/**
 *
 * @author xxxxdshgshgfsdhfghdsfxxxxxxxxxxx
 *
 */
@Transactional(propagation=Propagation.REQUIRED)
public class BasFacultyDaoImpl extends JdbcDaoSupport implements BasFacultyDao {

	@Autowired
	@Qualifier("sdatasource")
	public void setDataSourceInSuper(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	public void saveToFacultyWorkingTable(FacultyWorkingDaysEntity fwdEntity) {
		String query = "insert into faculty_working_days_tbl (empid,noOfDaysWorked,noOfApprovedLeaves,noOfHolidays,totalworkingdays,department,month,noOfLwp) values (?,?,?,?,?,?,?,?)";
		super.getJdbcTemplate().update(query, new Object[] {fwdEntity.getEmpid(),fwdEntity.getNoOfDaysWorked(),fwdEntity.getNoOfApprovedLeaves(),fwdEntity.getNoOfHolidays(),fwdEntity.getTotalworkingdays(),fwdEntity.getDepartment(),fwdEntity.getMonth(),fwdEntity.getNoOfLwp()});
	}

	@Override
	public List<FaculityLeaveMasterEntity> LeaveHistoryByDate(String date1, String date2, String empid) {
		// String squery = "select lh.empNo,e.`name`, e.designation,e.department, e.doj, e.image, lh.leaveType,lh.leaveCategory,lh.lstatus,lh.purpose,lh.ldateFrom as leaveFrom,lh.ldateTo as leaveTo,lh.totalDays from emp_leave_history as lh,emp_db as e where lh.empNo=e.id and lh.empNo=? and ldateFrom >= ? and ldateFrom <= ?";
		String squery ="select lh.empid,e.name, e.designation,e.department, e.doj, e.image, lh.leaveType,lh.leaveCategory,lh.purpose,lh. leaveFrom,lh.leaveTo,lh.totalDays,lh.lwpDays,lh.leaveDays,lh.elDays,lh.lwp,lh.ccl,cel,lh.inAccount from emp_leave_history as lh,emp_db as e where lh.empid=e.id and lh.empid=? and leaveFrom >= ? and leaveFrom <=? and hrApproval=?";
		List<FaculityLeaveMasterEntity> faculityLeaveMasterEntitieslist=null;
		try {
			 faculityLeaveMasterEntitieslist = super.getJdbcTemplate().query(squery,new Object[] { Integer.parseInt(empid), date1, date2,LeaveStatus.APPROVED_STATUS},new BeanPropertyRowMapper<FaculityLeaveMasterEntity>(FaculityLeaveMasterEntity.class));
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	//System.out.println(squery);
		return faculityLeaveMasterEntitieslist;
	}

	@Override
	public List<HolidayEntryEntity> getHoliday(int month, int year){
		//String query = "select DATE_FORMAT(cdate,'%Y-%m-%d') as holidayDate,holidayType, description, comment,image from holiday_entry_tbl where month(cdate) = ? ";
		String query = "select DATE_FORMAT(cdate,'%Y-%m-%d') as holidayDate,holidayType, description, comment,image from holiday_entry_tbl where month(cdate) = ? and year(cdate) = ? ";
		List<HolidayEntryEntity> calenderEntities = super.getJdbcTemplate().query(query,
				new Object[]{month,year}, new BeanPropertyRowMapper<HolidayEntryEntity>(HolidayEntryEntity.class));
		return calenderEntities;
	}

	@Override
	public List<FaculityDailyAttendanceReportEntity> getAttendanceStatusByDate(String d1, String d2, String eid) {
		System.out.println("coold1== "+d1+" d2== "+d2+" eid== "+eid);
		String sql = "select att.fid, DATE_FORMAT(att.cdate,'%Y-%m-%d') as cdate, e.name, e.fatherName, e.department, att.intime, att.outtime, att.status, att.intimestatus, att.outtimestatus, att.present from faculity_att_tab as att,emp_db as e where (att.fid=e.id and att.fid=? and cdate>=? and cdate<=?) order by att.cdate desc,intime desc,outtime asc";
		List<FaculityDailyAttendanceReportEntity> fDARE = super.getJdbcTemplate().query(sql,new Object[] {eid,d1,d2}, new BeanPropertyRowMapper<FaculityDailyAttendanceReportEntity>(FaculityDailyAttendanceReportEntity.class));
		System.out.println(sql);
		return fDARE;
	}

	@Override
	public List<FacultyLeaveApprovalEntity> getLeaveApprovalForAdmin(String hrid) {
		String query =HRQuery.FIND_PENDING_LEAVE_REQUESTS;
		List<FacultyLeaveApprovalEntity> facultyLeaveData = super.getJdbcTemplate().query(query,new Object[]{hrid},
				new BeanPropertyRowMapper<FacultyLeaveApprovalEntity>(FacultyLeaveApprovalEntity.class));
		return facultyLeaveData;
	}
	
	@Override
	public List<FacultyLeaveApprovalEntity> getLeaveApprovalForManagement(String managementId) {
		String query =ManagementQuery.FIND_PENDING_LEAVE_REQUESTS;
		List<FacultyLeaveApprovalEntity> facultyLeaveData = super.getJdbcTemplate().query(query,new Object[]{managementId},
				new BeanPropertyRowMapper<FacultyLeaveApprovalEntity>(FacultyLeaveApprovalEntity.class));
		return facultyLeaveData;
	}
	
	@Override
	public List<FaculityDailyAttendanceReportEntity> findEmployeesOnLeaveOnDate(String ondate) {
		String query =HRQuery.FIND_EMPLOYEE_ON_LEAVE_ON_DATE;
		List<FaculityDailyAttendanceReportEntity> facultyLeaveData = super.getJdbcTemplate().query(query,new Object[]{ondate},
				new BeanPropertyRowMapper<FaculityDailyAttendanceReportEntity>(FaculityDailyAttendanceReportEntity.class));
		return facultyLeaveData;
	}

	
	@Override
	public List<FacultyLeaveApprovalEntity> getPendingLeaveRequestByEmpid(String empid) {
		String query =EmployeeQuery.FIND_PENDING_LEAVE_REQUESTS_BY_EMPID;
		List<FacultyLeaveApprovalEntity> facultyLeaveData = super.getJdbcTemplate().query(query,new Object[]{empid},
				new BeanPropertyRowMapper<FacultyLeaveApprovalEntity>(FacultyLeaveApprovalEntity.class));
		return facultyLeaveData;
	}
	
	@Override
	public String updateLeaveApprovalForAdmin(int requestID, String hrApproval){
		String query = "update emp_leave_requests_tbl set hrApproval = ? where requestID = ?";
		Object employeeLeaveApproval[] = new Object[]{hrApproval, requestID};
		super.getJdbcTemplate().update(query,employeeLeaveApproval);
		return"Admin approval persisted";
	}

	@Override
	public FacultyEntity showEmployee(String id) {
		//dont use * just use column name because column might be added or deleted
		String query = "SELECT id,name,fatherName,motherName,spouseName,guardianName,email,paddress,phoneNumber,dob,sex,maritalStatus,"
				+ "bloodGroup,designation,department,type,category,doj,diploma,batchlourDegree,mastersDegree,postMastersDegree,"
				+ "otherQualification,description,reporting_manager FROM emp_db where id=?";
		FacultyEntity facultyEntity = (FacultyEntity)super.getJdbcTemplate().queryForObject(query,new Object[]{id} ,new BeanPropertyRowMapper(FacultyEntity.class));

		return facultyEntity;
	}


	@Override
	public List<String> selectReportingManager() {
		String sql = "SELECT CONCAT(A.NAME, '-', A.ID, '-', A.DEPARTMENT) FROM emp_db A WHERE A.REPORTING_MANAGER='YES'";
		List<String> reportmList = super.getJdbcTemplate().queryForList(sql, String.class);
		return reportmList;

	}


	@Override
	public List<String> selectEmployeeType() {
		String sql = "SELECT employeeTypeName from employee_type_tbl";
		List<String> employeeTypeList = super.getJdbcTemplate().queryForList(sql, String.class);
		return employeeTypeList;
	}


	@Override
	public List<String> selectDesignations(){
		String sql = "Select DesignationName from designations_tbl";
		List<String> desigList = super.getJdbcTemplate().queryForList(sql, String.class);
		return desigList;

	}


	@Override
	public List<String> selectBloodGroups(){
		String sql = "Select bloodGroup from blood_group_tbl";
		List<String> bloodGroupList = super.getJdbcTemplate().queryForList(sql, String.class);
		return bloodGroupList;

	}


	@Override
	public String updateEmployee(FaculityDailyAttendanceReportEntity facultyAttendStatusEntity, String fid, String newdate){
		String sql = "UPDATE faculity_att_tab SET intime=?,outtime=?,intimestatus=?,outtimestatus=?,detail=?,present=? WHERE fid=? and Date(cdate)=?";
		Object[] object = new Object[] {facultyAttendStatusEntity.getIntime(),facultyAttendStatusEntity.getOuttime(),
				facultyAttendStatusEntity.getIntimestatus(),facultyAttendStatusEntity.getOuttimestatus(),
				facultyAttendStatusEntity.getDetail(),facultyAttendStatusEntity.getPresent(),fid,newdate};
		super.getJdbcTemplate().update(sql, object);
		return "Attendus Updated Success";
	}



	@Override
	public String deleteAttendus(String employeeId, String attndDate){
		String sql = "Delete from faculity_att_tab where fid=? and Date(cdate)=?";
		super.getJdbcTemplate().update(sql,new Object[]{employeeId,attndDate});
		return "Attendus Deleted Successfuly";
	}

	@Override
	public List<String> selectDepartments(){
		String sql = "Select departmentName from departments_tbl";
		List<String> depList = super.getJdbcTemplate().queryForList(sql, String.class);
		return depList;
	}
	
	@Override
	public Map<String,String> selectDepartmentsAsMap(){
		String sql = "Select departmentShortName,departmentName from departments_tbl";
		List<DepartmentEntity> depList =(List<DepartmentEntity>) super.getJdbcTemplate().query(sql, new BeanPropertyRowMapper(DepartmentEntity.class));
		 Map<String,String> departmentMap=new LinkedHashMap<String, String>();
		 for(DepartmentEntity departmentEntity:depList){
			 departmentMap.put(departmentEntity.getDepartmentShortName(), departmentEntity.getDepartmentName());
		 }
		return departmentMap;
	}

	@Override
	public String createEmployeeAccount(FacultyEntity facultyEntity) {
		
		String checkUserQuery="select phoneNumber from emp_db where phoneNumber="+facultyEntity.getPhoneNumber();
		try{
				String mob=getJdbcTemplate().queryForObject(checkUserQuery, String.class);
				if((facultyEntity.getPhoneNumber()).equals(mob))
					return "0";
		}catch(Exception e){
			e.printStackTrace();
		}
		//Generate the emp id;
		String tokens[]=facultyEntity.getReportingManager().split("-");
		String reportingManageEId=tokens[1];
		long newempid=facultyEntity.getId();
		if(newempid==0) {
			String sql="select cempid from current_gen_emp_id";
			String empid=getJdbcTemplate().queryForObject(sql, String.class);
			newempid=Long.parseLong(empid)+1;
			sql="update current_gen_emp_id set cempid='"+newempid+"'";
			getJdbcTemplate().update(sql);
		}
		String query = "insert into emp_db(id,name,fatherName,motherName,spouseName,guardianName,email,paddress,phoneNumber,dob,doj,diploma,batchlourDegree,mastersDegree,postMastersDegree,otherQualification,sex,maritalStatus,bloodGroup,designation,department,type,category,reporting_manager,manager,doe,dom,description) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object data[]=new Object[]{newempid,facultyEntity.getName(),"NA","NA","NA","NA",facultyEntity.getEmail(),"NA",facultyEntity.getPhoneNumber(),facultyEntity.getDob(),facultyEntity.getDoj(),"NA","NA","NA","NA","NA",facultyEntity.getSex(),"married","NA",facultyEntity.getDesignation(),facultyEntity.getDepartment(),facultyEntity.getType(),facultyEntity.getCategory(),reportingManageEId,facultyEntity.getManager(),facultyEntity.getDoe(),facultyEntity.getDom(),"NA"};
		getJdbcTemplate().update(query,data);
		Timestamp time = new Timestamp(new Date().getTime());
		String dom = time.toString();
		String inserIntoRelation="insert into emp_manager_relation_tb(manager_id,employee_id,hr_id,dom,doe,entryBy) values(?,?,?,?,?,?)";
		getJdbcTemplate().update(inserIntoRelation,new Object[]{reportingManageEId,newempid+"",LeaveStatus.HR_ID,time,time,facultyEntity.getDiploma()});
		//Generating credential
		String loginDetailQuery="insert into faculity_login_tbl(userid,eid,password,email,locked,role,doe,dom) values(?,?,?,?,?,?,?,?)";
		Object loginDetailData[]=new Object[]{newempid+"",newempid+"",facultyEntity.getPassword(),facultyEntity.getEmail(),"no",facultyEntity.getRole(),facultyEntity.getDoe(),facultyEntity.getDom()};
		getJdbcTemplate().update(loginDetailQuery,loginDetailData);
		//insert record into "emp_leave_balance_tbl"
		
		String insertEmpLeaveBalance = "INSERT INTO emp_leave_balance(empid,leaveMonth,CL,EL,SL,CO,Study,OD,WV,SV,dom,doe,description,entryType,modifiedBy) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object insertEmpLeaveBalanceData[] = new Object[]{newempid,facultyEntity.getDom(),"1.75","0","1","0","0","0","0","0",dom,dom,"kaya","AOTO",facultyEntity.getDiploma()};
		getJdbcTemplate().update(insertEmpLeaveBalance, insertEmpLeaveBalanceData);
		return newempid+"";
	}
	
	@Override
	public String updateProfilePic(FacultyEntity facultyEntity) {
		if(facultyEntity.getImage()==null || facultyEntity.getImage().length==0 ){
			return "success";
		}
		LobHandler lobHandler = new DefaultLobHandler();
		SqlLobValue image = new SqlLobValue(facultyEntity.getImage(),
				lobHandler);
		String updateProfilePic="update  emp_db set image=?,dom=? where id=?";
		int dataTye[] = new int[] { Types.BLOB, Types.DATE, Types.INTEGER};
		super.getJdbcTemplate().update(updateProfilePic, new Object[]{image,DateUtils.getCurrentCalendarDate(),facultyEntity.getId()}, dataTye);
		return "success";
	}

	//put all the queries in the sql file
	@Override
	public String persistFaculty(FacultyEntity facultyEntity) {
		LobHandler lobHandler = new DefaultLobHandler();
		SqlLobValue image = new SqlLobValue(facultyEntity.getImage(),
				lobHandler);
		SqlLobValue rthumbimage = new SqlLobValue(facultyEntity.getRightThumb(),
				lobHandler);
		SqlLobValue lthumbimage = new SqlLobValue(facultyEntity.getLeftThumb(),
				lobHandler);
		Object data[] = new Object[] { facultyEntity.getId(),facultyEntity.getName(),facultyEntity.getFatherName(),facultyEntity.getMotherName(),
				facultyEntity.getSpouseName(),facultyEntity.getGuardianName(),facultyEntity.getEmail(),facultyEntity.getPaddress(),
				facultyEntity.getPhoneNumber(),facultyEntity.getDob(),facultyEntity.getSex(),facultyEntity.getMaritalStatus(),
				facultyEntity.getBloodGroup(),facultyEntity.getDesignation(),facultyEntity.getDepartment(), facultyEntity.getType(),
				facultyEntity.getCategory(),facultyEntity.getDoj(),facultyEntity.getDiploma(),facultyEntity.getBatchlourDegree(),
				facultyEntity.getMastersDegree(),facultyEntity.getPostMastersDegree(),facultyEntity.getOtherQualification(),
				image,lthumbimage,rthumbimage,facultyEntity.getDescription(),facultyEntity.getDom(),facultyEntity.getDoe()};
		String query = "insert into  emp_db values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		int dataTye[] = new int[] { Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
				Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
				Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
				Types.BLOB,Types.BLOB,Types.BLOB, Types.VARCHAR, Types.DATE, Types.DATE};
		super.getJdbcTemplate().update(query, data, dataTye);
		return "ahahha";
	}

	@Override
	public String updateFaculty(FacultyEntity facultyEntity) {
		if(facultyEntity.getImage()==null || facultyEntity.getImage().length==0 ){
			byte[] dimage=findPhotoByEmpId(facultyEntity.getId()+"");
			facultyEntity.setImage(dimage);
		}
		LobHandler lobHandler = new DefaultLobHandler();
		SqlLobValue image = new SqlLobValue(facultyEntity.getImage(),
				lobHandler);
		Object data[] = new Object[] { facultyEntity.getName(),facultyEntity.getFatherName(),facultyEntity.getMotherName(),
				facultyEntity.getSpouseName(),facultyEntity.getEmail(),facultyEntity.getPaddress(),
				facultyEntity.getPhoneNumber(),facultyEntity.getDob(),facultyEntity.getSex(),facultyEntity.getMaritalStatus(),
				facultyEntity.getBloodGroup(),facultyEntity.getDesignation(),facultyEntity.getDepartment(), facultyEntity.getType(),
				facultyEntity.getCategory(),facultyEntity.getDoj(),facultyEntity.getDiploma(),facultyEntity.getBatchlourDegree(),
				facultyEntity.getMastersDegree(),facultyEntity.getPostMastersDegree(),facultyEntity.getOtherQualification(),
				image,facultyEntity.getDescription(),facultyEntity.getDom(),facultyEntity.getId()};
		String query = "update emp_db set name=?,fatherName=?,motherName=?,spouseName=?,email=?,paddress=?,phoneNumber=?,dob=?,sex=?,maritalStatus=?,bloodGroup=?,designation=?,department=?,type=?,category=?,doj=?,diploma=?,batchlourDegree=?,mastersDegree=?,postMastersDegree=?,otherQualification=?,image=?,description=?,dom=? where id=?";
		int dataTye[] = new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
				Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
				Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,Types.BLOB, Types.VARCHAR,Types.DATE,Types.INTEGER};
		super.getJdbcTemplate().update(query, data, dataTye);
		String updateManager="update emp_manager_relation_tb set manager_id=? where employee_id=?";
		super.getJdbcTemplate().update(updateManager,new Object[]{facultyEntity.getReportingManager(),facultyEntity.getId()});
		String updateEmailIntoLoginTable="update faculity_login_tbl set email=? where eid=?";
		super.getJdbcTemplate().update(updateEmailIntoLoginTable,new Object[]{facultyEntity.getEmail(),facultyEntity.getId()});
		return "updated";
	}

	@Override
	public String deletetFaculty(String name) {
		String query = "delete from faculty_tbl where name=?";
		super.getJdbcTemplate().update(query, name);
		return "deleted";
	}

	@Override
	public List<FacultyEntity> findAllFaculty() {
		//dont use * just use column name because column might be added or deleted
		String query = "SELECT id,name,email,phoneNumber,designation,department,doj FROM emp_db where active='YES'";
		List<FacultyEntity> facultyEntities = super.getJdbcTemplate().query(
				query, new BeanPropertyRowMapper(FacultyEntity.class));
		return facultyEntities;
	}
	
	@Override
	public List<FacultyEntity> findAllFacultyByStatus(String employeeStatus) {
		//dont use * just use column name because column might be added or deleted
		String query = "SELECT id,name,email,phoneNumber,designation,department,doj,active FROM emp_db where active=?";
		List<FacultyEntity> facultyEntities = super.getJdbcTemplate().query(
				query, new Object[]{employeeStatus},new BeanPropertyRowMapper(FacultyEntity.class));
		return facultyEntities;
	}
	
	@Override
	public List<FacultyEntity> findFacultyWithBirthday() {
		//dont use * just use column name because column might be added or deleted
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		int month = now.get(Calendar.MONTH) + 1; // Note: zero based!
		String smonth="";
		String sday="";
		int day = now.get(Calendar.DAY_OF_MONTH);
		if((month+"").length()==1){
			smonth="0"+month;
		}
		if((day+"").length()==1){
			sday="0"+day;
		}
		String cdate=sday+"/"+smonth;
		String query = "SELECT id,name,email,phoneNumber,designation,department,doj,active FROM emp_db where dob like '"+cdate+"/%'";
		List<FacultyEntity> facultyEntities = super.getJdbcTemplate().query(
				query,new BeanPropertyRowMapper(FacultyEntity.class));
		return facultyEntities;
	}
	@Override
	//dont fetch the whole data when u need only the image
	public byte[] findPhotoByEmpId(String empid) {
		String query = "select * from emp_db where id="+Long.parseLong(empid);
		FacultyEntity facultyEntity = null;
		try {
			facultyEntity = (FacultyEntity) super.getJdbcTemplate()
					.queryForObject(query,
							new BeanPropertyRowMapper(FacultyEntity.class));
		} catch (Exception exception) {
			//exception.printStackTrace();
		}
		return facultyEntity==null? new byte[]{}:facultyEntity.getImage();
	}

	@Override
	public FacultyEntity findFacultyByName(String name) {
		String query = "select * from faculty_tbl where name='" + name + "'";
		FacultyEntity facultyEntitie = null;
		try {
			facultyEntitie = (FacultyEntity) super.getJdbcTemplate()
					.queryForObject(query,
							new BeanPropertyRowMapper(FacultyEntity.class));
		} catch (EmptyResultDataAccessException exception) {
			exception.printStackTrace();
		}
		return facultyEntitie;
	}

	@Override
	public EmployeeShowFormEntity findFacultyByNamespecific(String name) {
		String query = "select * from employeeShow_tbl where name='" + name + "'";
		EmployeeShowFormEntity specific = null;
		try {
			specific = (EmployeeShowFormEntity) super.getJdbcTemplate()
					.queryForObject(query,
							new BeanPropertyRowMapper(EmployeeShowFormEntity.class));
		} catch (EmptyResultDataAccessException exception) {
			exception.printStackTrace();
		}
		return specific;
	}
	
	
	/**
	 *  
	 * @param requestId
	 */
	@Override
	public FacultyLeaveApprovalEntity findCurrentClByRequestId(String requestId) {
		String squery = HRQuery.FIND_EMP_CURRENT_CL_BALANCE_BY_REQUEST_ID;
		FacultyLeaveApprovalEntity facultyLeaveApprovalEntity = null;
		float totalAvailableCls=0;
		try {
			facultyLeaveApprovalEntity = super.getJdbcTemplate().queryForObject(
							squery,
							new Object[] { requestId },
							new BeanPropertyRowMapper<FacultyLeaveApprovalEntity>(
									FacultyLeaveApprovalEntity.class));
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
		}
		return facultyLeaveApprovalEntity;
	}

	@Override
	public LeaveBalanceEntity findLeaveBalance(String empid) {
		String squery = "select lb.empid,e.name, e.designation,e.department ,lb.leaveMonth,lb.cl,lb.SL,lb.EL,lb.OD,lb.CO,lb.Study,lb.SV,lb.WV,lb.dom from emp_leave_balance as lb,emp_db as e where lb.empid=e.id and lb.empid=?";
		LeaveBalanceEntity leaveBalanceEntity = null;
		try {
			leaveBalanceEntity = super
					.getJdbcTemplate()
					.queryForObject(
							squery,
							new Object[] { Integer.parseInt(empid) },
							new BeanPropertyRowMapper<LeaveBalanceEntity>(
									LeaveBalanceEntity.class));
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
		}
		return leaveBalanceEntity;
	}

	@Override
	public List<FaculityLeaveMasterEntity> findLeaveHistory(String empid) {
		//String squery = "select lh.empNo,e.`name`, e.designation,e.department, e.doj, e.image, lh.leaveType,lh.leaveCategory,lh.lstatus,lh.purpose,lh.ldateFrom as leaveFrom,lh.ldateTo as leaveTo,lh.totalDays from emp_leave_history as lh,emp_db as e where lh.empNo=e.id and lh.empNo=?";
		String squery ="select lh.requestId, lh.empid,e.name, e.designation,e.department, e.doj, e.image, lh.leaveType,lh.inAccount,lh.leaveDates,lh.lwp,lh.leaveCategory,lh.purpose,lh. leaveFrom,lh.leaveTo,lh.totalDays from emp_leave_history as lh,emp_db as e where lh.empid=e.id and lh.empid=?";
		List<FaculityLeaveMasterEntity> faculityLeaveMasterEntitieslist = super.getJdbcTemplate().query(squery,new Object[] { Integer.parseInt(empid) },new BeanPropertyRowMapper<FaculityLeaveMasterEntity>(FaculityLeaveMasterEntity.class));
		return faculityLeaveMasterEntitieslist;
	}


	//Changes by PK
	@Override
	public List<FaculityLeaveMasterEntity> sortLeaveHistoryByDate(String date1, String date2, String empid) {
		//String squery = "select lh.empNo,e.`name`, e.designation,e.department, e.doj, e.image, lh.leaveType,lh.leaveCategory,lh.lstatus,lh.purpose,lh.ldateFrom as leaveFrom,lh.ldateTo as leaveTo,lh.totalDays from emp_leave_history as lh,emp_db as e where lh.empNo=e.id and lh.empNo=? and ldateFrom >= ? and ldateTo <= ?";
		// String squery ="select lh.employee_id,e.name, e.designation,e.department, e.doj, e.image, lh.leaveType,lh.leaveCategory,lh.purpose,lh. leaveFrom,lh.leaveTo,lh.totalDays from emp_leave_history as lh,emp_db as e where lh.employee_id=e.id and lh.employee_id=? and leaveFrom >= ? and leaveFrom <= ?";
		String squery="select lh.empid,e.name, e.designation,e.department, e.doj, lh.leaveType,lh.leaveCategory,lh.purpose,lh. leaveFrom,lh.leaveTo,lh.totalDays,lh.inAccount,lh.leaveDates,lh.lwp from emp_leave_history as lh,emp_db as e where lh.empid=e.id and lh.empid= ? and leaveFrom >= ? and leaveFrom <= ?";
		List<FaculityLeaveMasterEntity> faculityLeaveMasterEntitieslist = super.getJdbcTemplate().query(squery,new Object[] { empid, date1, date2 },new BeanPropertyRowMapper<FaculityLeaveMasterEntity>(FaculityLeaveMasterEntity.class));
		//System.out.println(squery);
		return faculityLeaveMasterEntitieslist;
	}

	@Override
	public byte[] findImageById(String empid) {
		String squery = "select image from emp_db where id=?";
		FaculityLeaveMasterEntity imgForEmp = (FaculityLeaveMasterEntity) super.getJdbcTemplate().queryForObject(squery,new Object[] { Integer.parseInt(empid) },new BeanPropertyRowMapper(FaculityLeaveMasterEntity.class));
		return imgForEmp.getImage();
	}

	//End

	@Override
	public List<FaculityDailyAttendanceReportEntity> findAttendStatus(String fid,int monthValue) {
		String dquery = "select s.cdate,s.intime,s.intimestatus,s.outtime,s.outtimestatus,s.status,s.detail,s.present  from faculity_att_tab as s   where s.fid="
				+ fid + "  and month(s.cdate) = "+ monthValue +" order by s.cdate asc";
		System.out.println(dquery);
		List<FaculityDailyAttendanceReportEntity> facultyAttendStatusEntitieslist = getJdbcTemplate()
				.query(dquery,
						new BeanPropertyRowMapper<FaculityDailyAttendanceReportEntity>(
								FaculityDailyAttendanceReportEntity.class));
		return facultyAttendStatusEntitieslist;
	}


	@Override
	public List<FacultySalaryMasterEntity> findSalaryHistory(String empid) {
		boolean bbb = TransactionSynchronizationManager
				.isActualTransactionActive();
		if (bbb) {
			if(logger.isDebugEnabled()){
				logger.debug("____Ahahahah Spring tx is working and discuss later______");
			}
		}
		/*
		 * java.util.Date date = new java.util.Date(); String dtformat= "MMM";
		 * SimpleDateFormat sdf = new SimpleDateFormat(dtformat); Calendar c =
		 * Calendar.getInstance(); int monthMaxDays =
		 * c.getActualMaximum(Calendar.DAY_OF_MONTH);
		 *
		 * int monthMaxDays = 30; String cdate =
		 * DateUtils.getCurrentDateSQLDB();
		 *
		 * String cntquery =
		 * "SELECT COUNT(*) FROM holiday_entry_tbl WHERE cdate<'" + cdate + "'";
		 * int numHol = super.getJdbcTemplate().queryForObject(cntquery,
		 * Integer.class); int totworkdays = monthMaxDays - numHol; int
		 * leavetotal = 0;
		 *
		 * String leavetknqry =
		 * "SELECT el.totalDays FROM emp_leave_history AS el WHERE ldateFrom<'"
		 * + cdate + "'"; List<Integer> leavetotallist =
		 * super.getJdbcTemplate().queryForList( leavetknqry, Integer.class);
		 *
		 * for (Integer i : leavetotallist) { leavetotal += i; }
		 *
		 * int daysworked = totworkdays - leavetotal;
		 *
		 * String insquery =
		 * "insert into faculty_salary_table values(?,?,?,?,?,?,?,?,?,?,?)";
		 * Object[] data = new Object[] { Integer.parseInt(empid), cdate,
		 * daysworked, leavetotal, totworkdays, 1000, "CS", "paid in Full",
		 * cdate, cdate, "null" }; super.getJdbcTemplate().update(insquery,
		 * data);
		 */

		int eidfin = Integer.parseInt(empid);
		String query = "SELECT sal.empid as eid,sal.month,sal.daysworked,sal.noleaves,sal.totworkdays,sal.salpaid,sal.comment FROM admin_salary_table AS sal WHERE sal.empid="
				+ eidfin + "";
		List<FacultySalaryMasterEntity> facultySalaryMasterEntitieslist = super
				.getJdbcTemplate().query(
						query,
						new BeanPropertyRowMapper<FacultySalaryMasterEntity>(
								FacultySalaryMasterEntity.class));
		return facultySalaryMasterEntitieslist;
	}

	@Override
	public ManualAttendanceEntity findEmployeeDataForAttendance(String empid) {
		String lquery = "select e.id as eid,e.name,e.designation,e.department,e.type from emp_db as e where e.id=?";
		ManualAttendanceEntity manualAttendanceEntity = (ManualAttendanceEntity) super
				.getJdbcTemplate()
				.queryForObject(lquery, new Object[] { empid },
						new BeanPropertyRowMapper(ManualAttendanceEntity.class));
		return manualAttendanceEntity;
	}

	@Override
	public FaculityLeaveMasterEntity findLeaveAppData(String empid/*,String leaveMonth*/) {
		//	String lquery = "select e.id as empNo,e.name,e.designation,e.department,e.type,eb.totalCL,eb.totalSL,eb.totalEL,eb.od,e.paddress as address from emp_db as e,emp_leave_balance as eb where (eb.empNo=e.id and e.id=?) and MONTH(eb.leaveMonth)=?";
		String lquery = "select e.id as empNo,e.name,e.designation,e.department,e.type,eb.totalCL,eb.totalSL,eb.totalEL,eb.od,e.paddress as address from emp_db as e,emp_leave_balance as eb where (eb.empNo=e.id and e.id=?) ";

		FaculityLeaveMasterEntity faculityLeaveMasterEntity = (FaculityLeaveMasterEntity) super
				.getJdbcTemplate().queryForObject(
						lquery,
						new Object[] { empid/*,leaveMonth*/ },
						new BeanPropertyRowMapper(
								FaculityLeaveMasterEntity.class));
		//logger
		System.out.println(faculityLeaveMasterEntity.getTotalCL());
		return faculityLeaveMasterEntity;
	}

	@Override
	public void addLeaveEntry(
			FaculityLeaveMasterEntity faculityLeaveMasterEntity) {
		System.out.println("before insert query" + faculityLeaveMasterEntity.getLeaveFrom());
		System.out.println("before insert query" + faculityLeaveMasterEntity.getMobile());
		String sql = "insert into emp_leave_requests_tbl(leaveFrom,leaveTo,totalDays,leaveType,description,purpose,reason,leaveCategory,reportingManager,cCTo,address,leaveMeeting,mobile) "
				+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] data = new Object[] {
				faculityLeaveMasterEntity.getLeaveFrom(),
				faculityLeaveMasterEntity.getLeaveTo(),
				faculityLeaveMasterEntity.getTotalDays(),
				faculityLeaveMasterEntity.getLeaveType(),
				faculityLeaveMasterEntity.getDescription(),
				faculityLeaveMasterEntity.getPurpose(),
				faculityLeaveMasterEntity.getReason(),
				faculityLeaveMasterEntity.getLeaveCategory(),
				faculityLeaveMasterEntity.getReportingManager(),
				faculityLeaveMasterEntity.getCcTo(),
				faculityLeaveMasterEntity.getAddress(),
				faculityLeaveMasterEntity.getLeaveMeeting(),
				faculityLeaveMasterEntity.getMobile()
		};
		// firing the query
		super.getJdbcTemplate().update(sql, data);
	}
	
	
	@Override
	public boolean checkEmployeeProfileByNameAndMobile(String mobile,String name,String email) {
		List<String> employee=new ArrayList<String>();
		String query = "select name from emp_db where name=? and phoneNumber=?";
		if(email!=null && email.length()>0){
		   query = "select name from emp_db where name=? and phoneNumber=? and email=?";
		   employee = super.getJdbcTemplate().queryForList(query,new Object[]{name,mobile,email},String.class);
		}else{
	    	employee = super.getJdbcTemplate().queryForList(query,new Object[]{name,mobile},String.class);
	    }
		if(employee.size()>0){
			return true;
		}
		return false;
	}

	@Override
	public List<String> findRelation(String manager) {
		String query = "select name from emp_db where id in (select employee_id from emp_manager_relation_tb where manager_id = ?)";
		List<String> employee = super.getJdbcTemplate().queryForList(query,new Object[]{manager},String.class);
		System.out.println(employee);
		return employee;
	}

	@Override
	public String applyAddendumLeave(FaculityLeaveMasterEntity al) {
		String query = "insert into emp_leave_requests_tbl (mobile, name, leaveFrom, leaveTo, totalDays,leaveType,purpose,"
				+ "leaveCategory,leaveMeeting,reportingManager, address, reason,description,cCTo) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object data[] = new Object[] {al.getMobile(),al.getName(),al.getLeaveFrom(),al.getLeaveTo(),
				al.getTotalDays(),al.getLeaveType(),al.getPurpose(),al.getLeaveCategory(),al.getLeaveMeeting(),
				al.getReportingManager(), al.getAddress(),al.getReason(),al.getDescription(),al.getCcTo()};
		super.getJdbcTemplate().update(query,data);
		return "success";
	}

	@Override
	public byte[] findPhotoByEmpName(String name) {
		String query = "select * from emp_db where name='" + name + "'";
		FaculityLeaveMasterEntity faculityLeaveMasterEntity = new FaculityLeaveMasterEntity();
		try {
			faculityLeaveMasterEntity = (FaculityLeaveMasterEntity) super
					.getJdbcTemplate().queryForObject(
							query,
							new BeanPropertyRowMapper(
									FaculityLeaveMasterEntity.class));
		} catch (EmptyResultDataAccessException exception) {
			exception.printStackTrace();
		}
		return faculityLeaveMasterEntity.getImage();

	}

	@Override
	public String enterLeaveHistory(FaculityLeaveMasterVO fl) {

		String query = "insert into emp_leave_history (empNo,deptt,leaveType,ldateFrom,ldateTo,totalDays,purpose,addressTelNoLeave,empName,doe,dom,description,approvedBy,leaveMeeting,leaveCategory,lstatus) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object data[] = new Object[] { fl.getEmpid(), fl.getDepartment(),
				fl.getLeaveType(), fl.getLeaveFrom(), fl.getLeaveTo(),
				fl.getTotalDays(), fl.getPurpose(), fl.getAddress()+"  mobile:"+fl.getMobile(),
				fl.getName(), new Date(), new Date(), fl.getDescription(),"NA",
				fl.getLeaveMeeting(), fl.getLeaveCategory(),"PENDING"};
		super.getJdbcTemplate().update(query, data);
		return "success";

	}

	@Override
	public List<FaculityLeaveMasterEntity> findAllLeaveHistory() {
		String query = "select e.empNo,e.ldateFrom as leaveFrom,e.ldateTo as leaveTo,e.empName as name,e.deptt as department,e.lstatus,e.leaveType,e.totalDays,e.doe, em.designation from emp_leave_history as e,emp_db as em where e.empNo=em.id order by e.doe desc";

		List<FaculityLeaveMasterEntity> facultyLeaveHistories = super
				.getJdbcTemplate().query(
						query,
						new BeanPropertyRowMapper(
								FaculityLeaveMasterEntity.class));
		return facultyLeaveHistories;
	}

	@Override
	public List<FaculityLeaveMasterEntity> findAllEmpDb() {
		String query = "select e.id as empNo,e.name,e.designation,e.department,e.type,e.doe,e.dom,e.description,e.type as leaveType,e.category as leaveCategory,e.paddress as address from emp_db as e";
		List<FaculityLeaveMasterEntity> allEmpDb = super.getJdbcTemplate()
				.query(query,
						new BeanPropertyRowMapper(
								FaculityLeaveMasterEntity.class));
		return allEmpDb;
	}

	@Override
	public String deleteLeaveHistory(String name, String date) {
		String query = "delete from emp_leave_history where empNo=? and ldateFrom=?";
		Object data[] = new Object[] { name, date };
		super.getJdbcTemplate().update(query, data);
		return "deleted";
	}

	@Override
	public String addEmployeeManulAttendance(
			ManualAttendanceEntity manualAttendanceEntity) {

		/*String intime = manualAttendanceEntity.getIntime();
		String intimeTokens[] = intime.split(":");
		Time wintime = new Time(Integer.parseInt(intimeTokens[0]),
				Integer.parseInt(intimeTokens[1]), 0);
		String outtime=manualAttendanceEntity.getOuttime();
		String outtimeTokens[] = outtime.split(":");
		Time wouttime = new Time(Integer.parseInt(outtimeTokens[0]),
				Integer.parseInt(outtimeTokens[1]), 0);
		System.out.println(manualAttendanceEntity);
		// match this fid is valid or not
		Time curretTime = DateUtils.getCurrentTime();
		// Computing in status for employee.
		InOutTimeEntity inOutTimeVO = getJdbcTemplate().queryForObject(
				FaculityQuery.SELECT_CURRENT_ORGANIZATION_TIME,
				new BeanPropertyRowMapper<InOutTimeEntity>(
						InOutTimeEntity.class));
		String inStatus = BaoConstants.NORMAL_STATUS;
		if (wintime.getTime() > inOutTimeVO.getLatein().getTime()) {
			inStatus = BaoConstants.LATE_IN_STATUS;
		}
		String outStatus = BaoConstants.NORMAL_STATUS;
		if (wouttime.getTime() <= inOutTimeVO.getLatein().getTime()) {
			outStatus = BaoConstants.EARLY_OUT_STATUS;
		}
		String status=BaoConstants.NORMAL_STATUS;
		//computing the final status
		if(inStatus.equals(BaoConstants.LATE_IN_STATUS) && outStatus.equals(BaoConstants.EARLY_OUT_STATUS)){
			status=BaoConstants.LATE_IN_STATUS+"-"+BaoConstants.EARLY_OUT_STATUS;
		}else if(inStatus.equals(BaoConstants.NORMAL_STATUS) && outStatus.equals(BaoConstants.EARLY_OUT_STATUS)){
			status=BaoConstants.EARLY_OUT_STATUS;
		} else if(inStatus.equals(BaoConstants.LATE_IN_STATUS) && outStatus.equals(BaoConstants.NORMAL_STATUS)){
			status=BaoConstants.LATE_IN_STATUS;
		}
		try {
		// Here we have two use cases
		// this is entry first entry for today for the employee
		Object[] qdata = new Object[] { manualAttendanceEntity.getEid(),
				manualAttendanceEntity.getCdate(),
				wintime,wouttime,status,
				DateUtils.getCurrentDateInSQLFormat(), inStatus,outStatus,"MANUAL",
				"FULLDAY", "YES", "Welcome sir", "NotSent", "admin" };
			getJdbcTemplate()
					.update("insert into faculity_att_tab (fid,cdate,intime,outtime,status,dom,intimestatus,outtimestatus,attmode,detail,present,description,alert,entryby) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
							qdata);
		}catch(Exception ex){
			return "NOTDONE";
		}*/
		return "DONE";
	}

	@Override
	public List<FaculityLeaveMasterEntity> findAllPendingLeaveHistory() {
		String query = "select e.empNo,e.ldateFrom as leaveFrom,e.ldateTo as leaveTo,e.empName as name,e.deptt as department,e.lstatus,e.leaveType,e.totalDays,e.doe, em.designation from emp_leave_history as e,emp_db as em where e.empNo=em.id and e.lstatus='RM-APPROVED' order by e.doe desc";
		List<FaculityLeaveMasterEntity> facultyLeaveHistories = super
				.getJdbcTemplate().query(
						query,
						new BeanPropertyRowMapper(
								FaculityLeaveMasterEntity.class));
		return facultyLeaveHistories;
	}

	@Override
	public void updateLeaveHistory(String empNo, String date, String lstatus) {
		String query="update emp_leave_history set lstatus=? where empNo=? and ldateFrom=?";
		Object data[] = new Object[] {lstatus,empNo,date};
		super.getJdbcTemplate().update(query, data);
	}

	@Override
	public List<FaculityLeaveMasterEntity> getReportingManagerList() {
		String query="select id,name,designation,department from emp_db where id in(select manager_id from emp_manager_relation_tb)";
		//        String query="select elt.eid as empNo,elt.role,edb.name, edb.designation from faculity_login_tbl as elt,emp_db as edb where elt.role='user' and id=elt.eid";
		List<FaculityLeaveMasterEntity> entities=super.getJdbcTemplate().query(
				query,
				new BeanPropertyRowMapper(
						FaculityLeaveMasterEntity.class));
		return entities;
	}

	@Override
	public List<FaculityLeaveMasterEntity> getCCToList() {
		String query="select elt.eid as empNo,elt.role,edb.name from faculity_login_tbl as elt,emp_db as edb where elt.role='user' and id=elt.eid";
		List<FaculityLeaveMasterEntity> ccToList=super.getJdbcTemplate().query(
				query,
				new BeanPropertyRowMapper(
						FaculityLeaveMasterEntity.class));
		return ccToList;
	}

	@Override
	public String enterRmLeaveHistory(
			FaculityLeaveMasterVO fl) {
		String query = "insert into emp_leave_history (empNo,deptt,leaveType,ldateFrom,ldateTo,totalDays,purpose,addressTelNoLeave,empName,doe,dom,description,approvedBy,leaveMeeting,leaveCategory,lstatus) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object data[] = new Object[] { fl.getEmpid(), fl.getDepartment(),
				fl.getLeaveType(), fl.getLeaveFrom(), fl.getLeaveTo(),
				fl.getTotalDays(), fl.getPurpose(), fl.getAddress()+"  mobile:"+fl.getMobile(),
				fl.getName(), new Date(), new Date(), fl.getDescription(),"NA",
				fl.getLeaveMeeting(), fl.getLeaveCategory(),"RM-PENDING"};
		super.getJdbcTemplate().update(query, data);
		return "success";

	}

	@Override
	public List<FaculityLeaveMasterEntity> findAllRmPendingLeaveHistory() {
		String query = "select e.empNo,e.ldateFrom as leaveFrom,e.ldateTo as leaveTo,e.empName as name,e.deptt as department,e.lstatus,e.leaveType,e.totalDays,e.doe, em.designation from emp_leave_history as e,emp_db as em where e.empNo=em.id and e.lstatus='RM-PENDING' order by e.doe desc";
		List<FaculityLeaveMasterEntity> facultyLeaveHistories = super
				.getJdbcTemplate().query(
						query,
						new BeanPropertyRowMapper(
								FaculityLeaveMasterEntity.class));
		return facultyLeaveHistories;
	}

	@Override
	public List<FaculityDailyAttendanceReportEntity> showAttendusReport(String date){
		String sql = "select att.fid, att.cdate, e.name, e.fatherName, e.department, att.intime, att.outtime, att.status, att.intimestatus, att.outtimestatus, att.present from faculity_att_tab as att,emp_db as e where (att.fid=e.id and cdate=?)";
		System.out.println("I am showing Data");
		List<FaculityDailyAttendanceReportEntity> fDARE = super.getJdbcTemplate().query(sql,new Object[]{date}, new BeanPropertyRowMapper<FaculityDailyAttendanceReportEntity>(FaculityDailyAttendanceReportEntity.class));
		return fDARE;
	}

	@Override
	public List<FaculityDailyAttendanceReportEntity> showAttendusReportByDep(String date, String dep){
		String sql = "select att.fid, att.cdate, e.name,e.designation, e.fatherName, e.department, att.intime, att.outtime, att.status, att.intimestatus, att.outtimestatus, att.present from faculity_att_tab as att,emp_db as e where (att.fid=e.id and cdate=? and e.department=?) ORDER BY att.fid asc,outtime ASC,intime ASC";
		List<FaculityDailyAttendanceReportEntity> fDARE = super.getJdbcTemplate().query(sql,new Object[]{date, dep}, new BeanPropertyRowMapper<FaculityDailyAttendanceReportEntity>(FaculityDailyAttendanceReportEntity.class));
		return fDARE;
	}

	@Override
	public List<String> searchEmployee(String employeeName) {
		String sQuery = "select name as 'full_name' from emp_db where name like '%"+employeeName+"%'";
		//		String sQuery = "select name from employeeShow_tbl where name like '%"+employeeName+"%'";
		List<String> queryf = super.getJdbcTemplate().queryForList(sQuery, String.class);
		return queryf;
	}

	@Override
	public List<String> searchEmployeeByManId(String employeeName) {
		String sQuery = "select concat(name, '-', id, '-', designation)  from emp_db where name like '%"+employeeName+"%'";
		//		String sQuery = "select name from employeeShow_tbl where name like '%"+employeeName+"%'";
		List<String> queryf = super.getJdbcTemplate().queryForList(sQuery, String.class);
		return queryf;
	}

	@Override
	public List<String> searchId(String employeeId) {
		String sQuery = "select concat(name, '-', id, '-', designation)  from emp_db where id like '%"+employeeId+"%'";
		//		String sQuery = "select name from employeeShow_tbl where name like '%"+employeeName+"%'";
		List<String> queryf = super.getJdbcTemplate().queryForList(sQuery, String.class);
		return queryf;
	}


	@Override
	public List<FacultyLeaveApprovalEntity> getLeaveApprovalForManager(int managerId){
		String query = HRQuery.FIND_PENDING_LEAVE_REQUESTS_MANAGER;
		/*String query = "select requestID, name, leaveFrom, leaveTo, totalDays, leaveType"
				+ " from emp_leave_requests_tbl where manager_id = "+managerId+" and managerApproval = 'Pending' ";*/
		List <FacultyLeaveApprovalEntity> facultyLeaveData = super.getJdbcTemplate().query(query,new Object[]{managerId},
				new BeanPropertyRowMapper<FacultyLeaveApprovalEntity>(FacultyLeaveApprovalEntity.class));
		System.out.println("Faculty Leave Data" + facultyLeaveData);
		return facultyLeaveData;

	}
	@Override
	public String updateLeaveApprovalForManager(int requestId, String managerApproval)
	{
		String query = "update emp_leave_requests_tbl set managerApproval = ? where requestId = ?";
		Object employeeLeaveApproval[]= new Object[]{managerApproval,requestId};
		System.out.println("DAO "+requestId+" "+managerApproval);
		
		super.getJdbcTemplate().update(query, employeeLeaveApproval);
		return"Manager apprvoal persisted";
	}



	@Override
	public List<ReportingManagerEntity> findEmployeeListByManager(String managerId) {
		String query="select e.id,e.name,e.designation,e.department from emp_db as e,emp_manager_relation_tb as em where em.manager_id="+managerId+" and e.id=em.employee_id";
		List<ReportingManagerEntity> employeeListForManager=new ArrayList<ReportingManagerEntity>();
		try {
			employeeListForManager = super
					.getJdbcTemplate().query(query,new BeanPropertyRowMapper(ReportingManagerEntity.class));
		} catch (EmptyResultDataAccessException exception) {
		}
		return employeeListForManager;
	}

	
	@Override
	public String deleteEmployeeManagerByEmpId(String empid) {
		String query="delete from emp_manager_relation_tb  where employee_id=?";
		super.getJdbcTemplate().update(query,new Object[]{empid});
		return "success";
	}
	
	@Override
	public ReportingManagerEntity findReportingManagerByEmpId(String employeeId) {
		String query="select id,name,designation,department from emp_db where id in(select manager_id from emp_manager_relation_tb where employee_id='"+employeeId+"')";
		ReportingManagerEntity faculityLeaveMasterEntity=null;
		try {
			faculityLeaveMasterEntity = (ReportingManagerEntity) super
					.getJdbcTemplate().queryForObject(query,new BeanPropertyRowMapper(ReportingManagerEntity.class));
		} catch (EmptyResultDataAccessException exception) {
			exception.printStackTrace();
			faculityLeaveMasterEntity=new ReportingManagerEntity();
			faculityLeaveMasterEntity.setId(123456);
			faculityLeaveMasterEntity.setDepartment("administrator");
			faculityLeaveMasterEntity.setDesignation("Admin");
			faculityLeaveMasterEntity.setName("Admin Bas");
		}
		return faculityLeaveMasterEntity;
	}

	@Override
	public List<FaculityDailyAttendanceReportEntity> showAttendenceforTodayAllDepts(){
		System.out.println("Hello from DAO Implementation");
		String currentDate=DateUtils.getCurrentCalendarDate();
		System.out.println(currentDate+"@@@@@@@@@@@22");
		String sql = "select att.fid, att.cdate, e.name, e.fatherName, e.department,e.designation, att.intime, att.outtime, att.status, att.intimestatus, att.outtimestatus, att.present from faculity_att_tab as att,emp_db as e  where att.fid=e.id and att.cdate='"+currentDate+"'";
		List<FaculityDailyAttendanceReportEntity> attTodayAllDept = super.getJdbcTemplate().query(sql, new BeanPropertyRowMapper<FaculityDailyAttendanceReportEntity>(FaculityDailyAttendanceReportEntity.class));
		//System.out.println(attTodayAllDept);
		return attTodayAllDept;
	}

	@Override
	public PaginationFaculityDailyAttendanceReportEntity TodaysAttendanceWithPagination(int start,
			int noOfRecords) {
		System.out.println("Welcome from pagination Dao ");
		String currentDate=DateUtils.getCurrentCalendarDate();
		System.out.println(currentDate);

		String sql = "select att.fid, att.cdate, e.name, e.fatherName, e.department,e.designation, att.intime, att.outtime, att.status, att.intimestatus, att.outtimestatus, att.present from faculity_att_tab as att,emp_db as e where (att.fid=e.id and cdate='"+currentDate+"') order by att.intime limit " + start + ", " + noOfRecords +"";
		List<FaculityDailyAttendanceReportEntity>  faculityDailyAttendanceReportEntityList= super.getJdbcTemplate().query(sql, new BeanPropertyRowMapper<FaculityDailyAttendanceReportEntity>(FaculityDailyAttendanceReportEntity.class));
		PaginationFaculityDailyAttendanceReportEntity paginationFaculityDailyAttendanceReportEntity= new PaginationFaculityDailyAttendanceReportEntity();
		int tnoOfRecords = getJdbcTemplate().queryForInt("select count(*)from faculity_att_tab  where cdate='"+currentDate+"'");
		System.out.println(tnoOfRecords);
		paginationFaculityDailyAttendanceReportEntity.setNoOfRecords(tnoOfRecords);
		paginationFaculityDailyAttendanceReportEntity.setFaculityDailyAttendanceReportEntityList(faculityDailyAttendanceReportEntityList);
		System.out.println("Welcome end from pagination Dao ");
		System.out.println(tnoOfRecords);
		return paginationFaculityDailyAttendanceReportEntity;
	}

	@Override
	public List<FaculityDailyAttendanceReportEntity> showAttendenceForTodayForSelectedDept(String dep){
		String currentDate=DateUtils.getCurrentCalendarDate();
		String sql = "select att.fid, att.cdate, e.name, e.fatherName, e.department, att.intime, att.outtime, att.status, att.intimestatus, att.outtimestatus, att.present from faculity_att_tab as att,emp_db as e where (att.fid=e.id and cdate='"+currentDate+"' and e.department='"+dep+"')";
		List<FaculityDailyAttendanceReportEntity> attTodaySelDept = super.getJdbcTemplate().query(sql, new BeanPropertyRowMapper<FaculityDailyAttendanceReportEntity>(FaculityDailyAttendanceReportEntity.class));
		return attTodaySelDept;
	}

	@Override
	public List<FaculityDailyAttendanceReportEntity> updateAttendenceForToday(List<String> checkedIds){
		String query="";
		int rowsAffected=0;
		String norm = "Normal";
		String currentDate=DateUtils.getCurrentCalendarDate();
		for(String id : checkedIds){
			System.out.println("-curr Date----"+currentDate);
			query = "update faculity_att_tab as att set att.outtimestatus=?, att.outtime=(select outtime from organisation_time order by sno desc LIMIT 1) where att.fid=? and att.cdate=?";
			rowsAffected = super.getJdbcTemplate().update(query,new Object[]{norm,Integer.parseInt(id),currentDate});
		}
		//System.out.println("rowsAffected = ==============="+rowsAffected);
		//System.out.println("Query string =================="+query);
		List<FaculityDailyAttendanceReportEntity> attTodayAllDept = showAttendenceforTodayAllDepts();
		return attTodayAllDept;
	}
	//End


	@Override
	public FacultyLeaveBalanceEntity1 findFacultyLeaveBalanceById(String employeeId) {
		System.out.println("Welcome from leave balance Dao 3");
		String firstQuery = "SELECT CL,SL,EL,OD,CO,Study,SV,WV FROM emp_leave_balance WHERE empid=" + employeeId;
		System.out.println(firstQuery);
		FaculityLeaveMasterEntity1 faculityLeaveMasterEntity = (FaculityLeaveMasterEntity1) super.getJdbcTemplate().queryForObject(firstQuery, new BeanPropertyRowMapper(FaculityLeaveMasterEntity1.class));
		String secondQuery = "SELECT id, name, image, department, designation,doj FROM emp_db WHERE id=" + employeeId;
		FacultyEntity1 facultyEntity = (FacultyEntity1) super.getJdbcTemplate().queryForObject(secondQuery, new BeanPropertyRowMapper(FacultyEntity1.class));
		FacultyLeaveBalanceEntity1 facultyLeaveBanalceEntity = new FacultyLeaveBalanceEntity1(faculityLeaveMasterEntity, facultyEntity);
		System.out.println("leave balance Dao 4");
		System.out.println(facultyLeaveBanalceEntity);
		return facultyLeaveBanalceEntity;
	}

	@Override
	public FacultyLeaveBalanceEntity1 updateLeaveBalance(FacultyLeaveBalanceEntity1 flb) {
		System.out.println("Welcome from Updateleavebalance Dao 3");
		String queryUpdate = "UPDATE emp_leave_balance SET CL=?, SL=?, EL=?,CO=?,Study=?,OD=?,WV=?,SV=?,dom=? WHERE empid=?";
		Object facultyLeaveBalanceData[] = new Object[]{flb.getCl(), flb.getSl(), flb.getEl(),flb.getCo(),flb.getStudy(),flb.getOd(),flb.getWv(),flb.getSv(),DateUtils.getCurrentTimestatmp(),flb.getEmpid()};
		super.getJdbcTemplate().update(queryUpdate, facultyLeaveBalanceData);

		FacultyLeaveBalanceEntity1 facultyLeaveBalanceEntityUpdated = findFacultyLeaveBalanceById(String.valueOf(flb.getEmpid()));
		System.out.println("Welcome from Updateleavebalance Dao 4");
		System.out.println(facultyLeaveBalanceEntityUpdated);
		return facultyLeaveBalanceEntityUpdated;
	}

	@Override
	public List<EmployeeMonthAttendanceEntity> getEmployeeMonthlyAttendanceInfo(String eid, String month,
			String year){

		String query = "SELECT emp_db.name, emp_db.id as eid, emp_db.department,emp_db.designation, faculity_att_tab.cdate FROM faculity_att_tab, emp_db WHERE emp_db.id= faculity_att_tab.fid AND emp_db.id=? AND faculity_att_tab.cdate LIKE '"
				+ year + "-" + month + "-%' ORDER BY  emp_db.id,faculity_att_tab.cdate";

		List<EmployeeMonthAttendanceEntity> employeeMonthAttendanceList = super.getJdbcTemplate().query(query,
				new Object[] { eid },new BeanPropertyRowMapper<EmployeeMonthAttendanceEntity>(EmployeeMonthAttendanceEntity.class));
		return employeeMonthAttendanceList;
	}

	@Override
	public List<EmployeeMonthAttendanceEntity> getDeptMonthlyAttendanceInfo(String department, String month,
			String year){

		String query = "SELECT emp_db.name, emp_db.id AS eid, emp_db.department,emp_db.designation, faculity_att_tab.cdate "
				+ "FROM faculity_att_tab, emp_db WHERE emp_db.id= faculity_att_tab.fid  AND  emp_db.department = ? AND faculity_att_tab.cdate LIKE '"
				+ year + "-" + month + "-%'  ORDER BY  emp_db.id,faculity_att_tab.cdate";

		List<EmployeeMonthAttendanceEntity> employeeMonthAttendanceList = super.getJdbcTemplate().query(query,
				new Object[] { department },
				new BeanPropertyRowMapper<EmployeeMonthAttendanceEntity>(EmployeeMonthAttendanceEntity.class));
		
		List<EmployeeMonthAttendanceEntity> filteredEmployeeMonthAttendanceList=new ArrayList<EmployeeMonthAttendanceEntity>();
		for(EmployeeMonthAttendanceEntity employeeMonthAttendanceEntity:employeeMonthAttendanceList){
			  if(!filteredEmployeeMonthAttendanceList.contains(employeeMonthAttendanceEntity)){
				  filteredEmployeeMonthAttendanceList.add(employeeMonthAttendanceEntity);
			  }
		}
		return filteredEmployeeMonthAttendanceList;
	}

	@Override
	public List<HolidayEntryEntity>  getHolidayCategoryList(String month, String year,
			Map<EmployeeHeader, List<String>> employeeMonthlyLeaveStatus) {

		String query = "SELECT DATE_FORMAT(holiday_entry_tbl.cdate,'%Y-%m-%d') AS cdate,weekend,holiday,working,holidayType FROM holiday_entry_tbl WHERE holiday_entry_tbl.cdate LIKE '"
				+ year + "-" + month + "-%'";
		List<HolidayEntryEntity> holidayCategoryList = super.getJdbcTemplate().query(query,
				new BeanPropertyRowMapper<HolidayEntryEntity>(HolidayEntryEntity.class));

		return holidayCategoryList;
	}
	
	@Override
	public boolean isEmployeePresentOnHoliday(String cdate,String empid) {
		String query = "select  DISTINCT(att.fid)  from faculity_att_tab as att,holiday_entry_tbl as he where he.cdate=att.cdate and he.cdate=? and att.fid=?;";
        try {
        	super.getJdbcTemplate().queryForObject(query, new Object[]{cdate,empid},String.class);
        	return true;	
        }catch(Exception ex){
        	System.out.println("Empty result ... "+ex.getMessage());
        }
		return false;
	}

	@Override
	public List<FacultyLeaveTypeEntity> getLeaveCategoryList(String eid, String month, String year,
			Map<EmployeeHeader, List<String>> employeeMonthlyLeaveStatus, int maxDate) {

		String selectedMonthYear = year + "-" + month + "-%";
		System.out.println("#######################################selectedMonthYear   =  " + selectedMonthYear);

		String query = "SELECT emp_leave_history.empId,emp_leave_history.leaveType, emp_leave_history.lstatus, emp_leave_history.deptt, DATE_FORMAT(emp_leave_history.leaveFrom,'%Y-%m-%d') AS leaveFrom, DATE_FORMAT(emp_leave_history.leaveTo,'%Y-%m-%d') AS leaveTo, emp_leave_history.leaveDates, emp_leave_history.lwp, emp_leave_history.inAccount, emp_leave_history.totalDays FROM emp_leave_history WHERE emp_leave_history.empId=? AND (emp_leave_history.leaveFrom LIKE '"
				+ selectedMonthYear + "' OR emp_leave_history.leaveTo LIKE '" + selectedMonthYear
				+ "') ORDER BY emp_leave_history.empId";

		List<FacultyLeaveTypeEntity> facultyLeaveCategoryList = super.getJdbcTemplate().query(query, new Object[]{ eid },
				new BeanPropertyRowMapper<FacultyLeaveTypeEntity>(FacultyLeaveTypeEntity.class));
		return facultyLeaveCategoryList;
	}

	@Override
	public List<FacultyLeaveTypeEntity> getDeptLeaveCategoryList(String department, String month, String year,
			Map<EmployeeHeader, List<String>> employeeMonthlyLeaveStatus, int maxDate) {

		String selectedMonthYear = year + "-" + month + "-%";
		System.out.println("#######################################selectedMonthYear   =  " + selectedMonthYear);

		String query = "SELECT emp_leave_history.empId,emp_leave_history.leaveType,emp_leave_history.lstatus,emp_leave_history.deptt, DATE_FORMAT(emp_leave_history.leaveFrom,'%Y-%m-%d') AS leaveFrom, DATE_FORMAT(emp_leave_history.leaveTo,'%Y-%m-%d') AS leaveTo, emp_leave_history.leaveDates,emp_leave_history.lwp,emp_leave_history.inAccount,emp_leave_history.totalDays FROM emp_leave_history WHERE emp_leave_history.deptt='"
				+ department + "' AND (emp_leave_history.leaveFrom LIKE '" + selectedMonthYear
				+ "' OR emp_leave_history.leaveTo LIKE '" + selectedMonthYear
				+ "') ORDER BY emp_leave_history.empId";

		List<FacultyLeaveTypeEntity> facultyLeaveCategoryList = super.getJdbcTemplate().query(query,
				new BeanPropertyRowMapper<FacultyLeaveTypeEntity>(FacultyLeaveTypeEntity.class));
		return facultyLeaveCategoryList;
	}

	@Override
	public String markLwpInDb(int id,String description){
		String currDate=DateUtils.getCurrentCalendarDate();
		String q1 = "select name,department from emp_db where id="+id;
		FaculityDailyAttendanceReportEntity attTodayAllDept = super.getJdbcTemplate().queryForObject(q1,new BeanPropertyRowMapper<FaculityDailyAttendanceReportEntity>(FaculityDailyAttendanceReportEntity.class));
		String name = attTodayAllDept.getName();    String lwp = "LWP";  String lCat = "FULLDAY";
		String department = attTodayAllDept.getDepartment(); String lstatus = "Not Approved";
		String sql = "insert into emp_leave_history (empNo,empName,deptt,leaveType,leaveCategory,lstatus,purpose,ldateFrom,ldateTo,totalDays) values (?,?,?,?,?,?,?,?,?,?)";
		try{
			super.getJdbcTemplate().update(sql,new Object[]{id,name,department,lwp,lCat,lstatus,description,currDate,currDate,1});
		}
		catch(Exception e){
			return "LWP has already been marked for this employee";
		}
		return "success";
	}
    //select lh.empid from emp_leave_history as lh,emp_db as e where lh.empid=e.id and lh.leaveType='LWP' and lh.leaveFrom = '2016-02-08';

	@Override
	public List<String> findEmpoyeeIdsForLwpByDate(String lwpDate){
		String sql = "select CONCAT(lh.empid,'-',lh.purpose) from emp_leave_requests_tbl as lh,emp_db as e where lh.empid=e.id and lh.leaveType='LWP' and lh.leaveFrom = '"+lwpDate+"'";
		List<String> employeeLwpIds = super.getJdbcTemplate().queryForList(sql,String.class);
		return employeeLwpIds;
	}

	@Override
	public List<FaculityDailyAttendanceReportEntity> findAllEmployeeForByDepartmentAndType(String departmentName,String employeeType){
		String sql = "select e.id as fid, e.name, e.designation, e.department,e.doj from emp_db e where e.department=? and type=? order by department asc,name asc";
		Object data[]=new Object[]{departmentName,employeeType};
		if("All".equalsIgnoreCase(departmentName)) {
			sql= "select e.id as fid, e.name, e.designation, e.department,e.doj from emp_db e where  type=? order by department asc,name asc";
			 data=new Object[]{employeeType};
		}
		String findOrganizationTime = "select intime, outtime from organisation_time order by date desc LIMIT 1";
		List<FaculityDailyAttendanceReportEntity> attTodayAllDept = super.getJdbcTemplate().query(sql,data,new BeanPropertyRowMapper<FaculityDailyAttendanceReportEntity>(FaculityDailyAttendanceReportEntity.class));
		try{
			OrganizationTimeEntity orgTime2 = (OrganizationTimeEntity) getJdbcTemplate().queryForObject(findOrganizationTime, new BeanPropertyRowMapper(OrganizationTimeEntity.class));
			for (FaculityDailyAttendanceReportEntity faculityDailyAttendanceReportEntity: attTodayAllDept){
				faculityDailyAttendanceReportEntity.setIntime(orgTime2.getIntime());
				faculityDailyAttendanceReportEntity.setOuttime(orgTime2.getOuttime());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return attTodayAllDept;
	}

	@Override
	public List<FaculityDailyAttendanceReportEntity> findAllEmployeeForAttendance(){
		String currentDate=DateUtils.getCurrentCalendarDate();
		/*String d1 = "2015-08-25";
		        String d2 = "2015-08-31";*/
		String sql = "select e.id as fid, e.name,e.doj, e.designation, e.department from emp_db e order by department asc,name asc";
		List<FaculityDailyAttendanceReportEntity> attTodayAllDept = super.getJdbcTemplate().query(sql, new BeanPropertyRowMapper<FaculityDailyAttendanceReportEntity>(FaculityDailyAttendanceReportEntity.class));
		return attTodayAllDept;
	}

	@Override
	public String addOrganisationTime(OrganizationTimeEntity organisationTimeEntity) {
		System.out.println(organisationTimeEntity.getDom());
		System.out.println(organisationTimeEntity.getDoe());
		String query = "";
		Object[] data = {};
		int[] dataType = {};
		query = "insert into organisation_time2(intime, outtime, latein, earlyout,doe,dom,enteredby,active) values (?,?,?,?,?,?,?,?)";
		/*
		 * query=
		 * "insert into fruits_tbl1 (name,price,taste,quantity,city,image,doe) values(?,?,?,?,?,?,?)"
		 * ;
		 */
		data = new Object[] { DateUtils.getCurrentTime(organisationTimeEntity.getIntime()),
				DateUtils.getCurrentTime(organisationTimeEntity.getLatein()), DateUtils.getCurrentTime(organisationTimeEntity.getOuttime()),
				DateUtils.getCurrentTime(organisationTimeEntity.getEarlyout()), organisationTimeEntity.getDoe(), organisationTimeEntity.getDom(),
				organisationTimeEntity.getEnteredby(),organisationTimeEntity.getActive()};
		dataType = new int[] { Types.TIME, Types.TIME, Types.TIME, Types.TIME, Types.DATE, Types.DATE,
				Types.VARCHAR,Types.INTEGER };
		super.getJdbcTemplate().update(query, data, dataType);
		return "success";

	}


	@Override
	public List<OrganizationTimeEntity> findOrgTimes() {
		String query = "select sno,intime,outtime,latein,earlyout,doe,dom,enteredby,active from organisation_time2";
		List<OrganizationTimeEntity> organizationTimeEntitieslist = super.getJdbcTemplate().query(query,new BeanPropertyRowMapper<OrganizationTimeEntity>(OrganizationTimeEntity.class));
		return organizationTimeEntitieslist;
	}

	@Override
	public String activateOrganisationTime(String sno) {
		System.out.println("Welcome Dao 3");
		String query="update organisation_time2 set active=0";
		String query1="update organisation_time2 set active=1 where sno="+sno;
		super.getJdbcTemplate().update(query);
		int q=super.getJdbcTemplate().update(query1);
		System.out.println("Welcome Dao 4 ");
		return q==0?"fail":"updated";
	}
	
	@Override
	public List<FacultyLeaveApprovalEntity> findEmployeesLeaveAppOnDate(String ondate){
		String query="select eh.empid, eh.requestId,doj,ed.designation,eh.empName,eh.leaveTo,eh.leaveType,eh.leaveFrom,eh.totalDays,eh.deptt,eh.purpose from emp_leave_requests_tbl as eh,emp_db as ed where eh.empid=ed.id and  leaveFrom=?";
		String squery="select eh.empid, eh.requestId,doj,ed.designation,eh.empName,eh.leaveTo,eh.leaveType,eh.leaveFrom,eh.totalDays,eh.deptt,eh.purpose from emp_leave_history as eh,emp_db as ed where eh.empid=ed.id and  leaveFrom=?";
		//String query="select *from emp_leave_requests_tbl";
		List<FacultyLeaveApprovalEntity> empOnLeaveList=super.getJdbcTemplate().query(query,new Object[]{ondate},new BeanPropertyRowMapper<FacultyLeaveApprovalEntity>(FacultyLeaveApprovalEntity.class));
		List<FacultyLeaveApprovalEntity> empOnLeaveFromHistoryTableList=super.getJdbcTemplate().query(squery,new Object[]{ondate},new BeanPropertyRowMapper<FacultyLeaveApprovalEntity>(FacultyLeaveApprovalEntity.class));
		List<FacultyLeaveApprovalEntity> finalFacultyLeaveApprovalEntityList=new ArrayList<FacultyLeaveApprovalEntity>();
		finalFacultyLeaveApprovalEntityList.addAll(empOnLeaveList);
		finalFacultyLeaveApprovalEntityList.addAll(empOnLeaveFromHistoryTableList);
		return finalFacultyLeaveApprovalEntityList;
	}
	

	@Override
	public FacultyLeaveApprovalEntity employeeOnLeaveDetailByAjax(String pempRequestId,String pempid) {
		int emp_id=Integer.parseInt(pempid);
		System.out.println("hello-----------------------------------------------------------------------------------------------");
		String query="select eh.empid, eh.requestId,ed.designation,eh.empName,eh.leaveTo,eh.leaveType,eh.leaveFrom,eh.totalDays,eh.deptt,eh.purpose from emp_leave_requests_tbl as eh,emp_db as ed where ed.id=? and  eh.requestId=?";
		FacultyLeaveApprovalEntity empOnLeave = super.getJdbcTemplate().queryForObject(query,new Object[]{emp_id,pempRequestId},new BeanPropertyRowMapper<FacultyLeaveApprovalEntity>(FacultyLeaveApprovalEntity.class));
		return empOnLeave;
	}
	
	
	@Override
	public String findEmpNameByEmpId(String empid) {
		 String name=(String)super.getJdbcTemplate().queryForObject("select name from emp_db where id="+empid,String.class);
		 return name;
	}
	@Override
	public FacultyLeaveApprovalEntity empAppliedLeaveDetail(String empRequestId) {
		String query="select requestId,leaveType,purpose,leaveFrom,leaveTo,leaveDates,lwp,inAccount,totalDays,hrApproval,managerApproval,manegementApproval,reportingManager as manager_id from emp_applied_leave_requests_tbl where requestId='"+empRequestId+"'";
		FacultyLeaveApprovalEntity empAppliedLeave = super.getJdbcTemplate().queryForObject(query,new BeanPropertyRowMapper<FacultyLeaveApprovalEntity>(FacultyLeaveApprovalEntity.class));
		return empAppliedLeave;
	}

	@Override
	public FacultyLeaveApprovalEntity empApprovedLeaveDetail(String empRequestId) {
		String query="select requestId,leaveType,purpose,leaveFrom,leaveTo,leaveDates,lwp,inAccount,totalDays,hrApproval,managerApproval,manegementApproval from emp_leave_history where requestId='"+empRequestId+"'";
		FacultyLeaveApprovalEntity empApprovedLeave = super.getJdbcTemplate().queryForObject(query,new BeanPropertyRowMapper<FacultyLeaveApprovalEntity>(FacultyLeaveApprovalEntity.class));
		
		return empApprovedLeave;
	}

	@Override
	public String deletetEmployeeById(String empid) {
		boolean transactionStatus=TransactionSynchronizationManager.isActualTransactionActive();
		System.out.println(")@@@@@@@@@@@@@@@@@@@@@@@@@");
		System.out.println("________"+transactionStatus+"___________");
		System.out.println(")@@@@@@@@@@@@@@@@@@@@@@@@@");
		
		//String updateActive="update emp_db set active='NO' where id="+Long.parseLong(empid) ;
		String moveRowIntoEmpDbHistory="INSERT INTO emp_db_history_tbl select * from emp_db where id="+Long.parseLong(empid) ;
		String deleteRowFromEmpDb="delete from emp_db where id="+Long.parseLong(empid) ;
		super.getJdbcTemplate().execute(moveRowIntoEmpDbHistory);
		super.getJdbcTemplate().execute(deleteRowFromEmpDb);		
		String moveRowIntoFaculityLoginHistoryTbl="INSERT INTO faculity_login_history_tbl select * from faculity_login_tbl where eid='"+empid+"'";
		String deleteRowFromFaculityLoginTbl="delete from faculity_login_tbl where eid='"+empid+"'";
		super.getJdbcTemplate().execute(moveRowIntoFaculityLoginHistoryTbl);
		super.getJdbcTemplate().execute(deleteRowFromFaculityLoginTbl);		
		return "success";
	}

	@Override
	public String activeEmployeeByEmpId(String empid) {
		//String updateActive="update emp_db set active='NO' where id="+Long.parseLong(empid) ;
		String moveRowIntoEmpDb="INSERT INTO emp_db select * from emp_db_history_tbl where id="+Long.parseLong(empid) ;
		String deleteRowFromEmpHistoryDb="delete from emp_db_history_tbl where id="+Long.parseLong(empid) ;
		super.getJdbcTemplate().execute(moveRowIntoEmpDb);
		super.getJdbcTemplate().execute(deleteRowFromEmpHistoryDb);		
		String moveRowIntoFaculityLoginTbl="INSERT INTO faculity_login_tbl select * from faculity_login_history_tbl where eid='"+empid+"'";
		String deleteRowFromFaculityLoginHistoryTbl="delete from faculity_login_history_tbl where eid='"+empid+"'";
		super.getJdbcTemplate().execute(moveRowIntoFaculityLoginTbl);
		super.getJdbcTemplate().execute(deleteRowFromFaculityLoginHistoryTbl);		
		return "success";
	}

	@Override
	public FacultyEntity findEmplyeeByAjax(String empid) {
		int id=Integer.parseInt(empid);
		String sql="select name,sex,email,phoneNumber,type,category,dob,doj,designation,department,reporting_manager from emp_db where id="+id;
		FacultyEntity facultyEntity=super.getJdbcTemplate().queryForObject(sql,new BeanPropertyRowMapper<FacultyEntity>(FacultyEntity.class));
		return facultyEntity;
	}

	@Override
	public String updateEmplyeeByAjax(FacultyForm facultyForm) {
		String msg="true";
		String sql="update emp_db set name=?,sex=?,email=?,phoneNumber=?,type=?,category=?,dob=?,doj=?,designation=?,department=?,reporting_manager=? where id=?";
		int value=super.getJdbcTemplate().update(sql,new Object[]{facultyForm.getName(),facultyForm.getSex(),facultyForm.getEmail(),facultyForm.getPhoneNumber(),facultyForm.getType(),facultyForm.getCategory(),facultyForm.getDob(),facultyForm.getDoj(),facultyForm.getDesignation(),facultyForm.getDepartment(),facultyForm.getReportingManager(),facultyForm.getId()});
		if(value==0)
			msg="false";
		return msg;
	}
	
	@Override
	public FacultyAttendStatusEntity findEmployeeAttendanceByDate(String eid,String cdate) {
		FacultyAttendStatusEntity facultyAttendStatusEntity=new FacultyAttendStatusEntity();
		facultyAttendStatusEntity.setIntime("8:45:00");
		facultyAttendStatusEntity.setOuttime("16:45:00");
			try{
				String sql="select * from faculity_att_tab where fid=? and cdate=?";
				facultyAttendStatusEntity=(FacultyAttendStatusEntity)super.getJdbcTemplate().queryForObject(sql,new Object[]{eid,cdate},new BeanPropertyRowMapper(FacultyAttendStatusEntity.class));
			}catch(Exception ex){
				ex.printStackTrace();
			}
			return facultyAttendStatusEntity;
	}

	@Override
	public String employeeManualAttendancePost(String eid,String cdate, String detail, Time intime, Time outtime) {
		String  status=BaoConstants.SUCCESS;
		try{
			String sql="select * from faculity_att_req_tab where fid=? and cdate=?";
			FacultyAttendStatusEntity facultyAttendStatusEntity=(FacultyAttendStatusEntity)super.getJdbcTemplate().queryForObject(sql,new Object[]{eid,cdate},new BeanPropertyRowMapper(FacultyAttendStatusEntity.class));
		    return BaoConstants.RECORD_ALREADY_EXIST;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		 InOutTimeVO inOutTimeVO =(InOutTimeVO) getJdbcTemplate().queryForObject(
		 FaculityQuery.SELECT_CURRENT_ORGANIZATION_TIME,new BeanPropertyRowMapper(InOutTimeVO.class));
		  String inStatus=BaoConstants.NORMAL_STATUS;
		  Time ointime=inOutTimeVO.getIntime();
		  long dtime=ointime.getTime()-intime.getTime();
		  if(dtime<0){
			  inStatus=BaoConstants.LATE_IN_STATUS;
		  }
		  String outStatus=BaoConstants.NORMAL_STATUS;
		  Time orgouttime=inOutTimeVO.getOuttime();
		  long outime=outtime.getTime()-orgouttime.getTime();
		  if(outime<0){
			  outStatus=BaoConstants.EARLY_OUT_STATUS;
		  }
		   int id=Integer.parseInt(eid);
			String sql="insert into faculity_att_req_tab(fid,cdate,intime,outtime,dom,intimestatus,outtimestatus,attmode,detail,present,description,entryby) values(?,?,?,?,?,?,?,?,?,?,?,?)";
			try{
				super.getJdbcTemplate().update(sql,new Object[]{id,cdate,intime,outtime,cdate,inStatus,outStatus,"Manual",detail,"No","Welcome sir",id});
			}catch(Exception e){
				e.printStackTrace();
				return BaoConstants.RECORD_ALREADY_EXIST;
			}
			return status;
	}

	@Override
	public List<FacultyManualAttendanceEntity> empManualRequestAtt() {
	//String sql="select fart.cdate,fart.intime,fart.outtime,fart.intimestatus,fart.outtimestatus,fart.detail,ed.name,ed.department from faculity_att_req_tab as fart,emp_db as ed where ed.id=fart.fid and fart.fid="+eid;
		String sql="select  fat.fid,fat.cdate,fat.intime,fat.outtime,fat.intimestatus,fat.outtimestatus,fat.detail,ed.`name`,ed.department,ed.designation from faculity_att_req_tab as fat,emp_db as ed where fat.fid=ed.id";
		List<FacultyManualAttendanceEntity> facultymanualAttendanceEntity= super.getJdbcTemplate().query(sql, new BeanPropertyRowMapper<FacultyManualAttendanceEntity>(FacultyManualAttendanceEntity.class));
		return facultymanualAttendanceEntity;
	}

	@Override
	public String rejectEmpAttRequestById(String empid,String cdate) {
		int id=Integer.parseInt(empid);
		String sql="delete from faculity_att_req_tab where fid="+empid;
		int value=super.getJdbcTemplate().update(sql);
		return "record has bee delete sucessfully from database";
	}

	@Override
	public String approveEmpAttRequestById(String empid,String cdate) {
		String selectAttendanceRecord="select  * from faculity_att_req_tab where fid="+empid+" and cdate=?";
		String moveRowIntoHistory="INSERT INTO faculity_att_tab  "+selectAttendanceRecord;
		super.getJdbcTemplate().update(moveRowIntoHistory,new Object[]{cdate});
		//rejectEmpAttRequestById(empid,cdate);
		String deleteRecord="delete  from faculity_att_req_tab where fid="+empid+" and cdate=?";
		super.getJdbcTemplate().update(deleteRecord,new Object[]{cdate});
		return "Attendance Request has been Sucessfully approved ";
	}

	@Override
	public List<SubjectAlternativeArrangementsEntity> findAllemployeeAlternateClassStatus() {
		String sql="select *from faculty_alternative_arrangements";
		List<SubjectAlternativeArrangementsEntity> alternativeArrangementsEntitiesList=super.getJdbcTemplate().query(sql, new BeanPropertyRowMapper<SubjectAlternativeArrangementsEntity>(SubjectAlternativeArrangementsEntity.class));
		return alternativeArrangementsEntitiesList;
	}

	@Override
	public List<String> selectEmployeeCategory() {
		String sql = "SELECT categoryName from category_tbl_copy";
		List<String> emplyeeCategoryList=super.getJdbcTemplate().queryForList(sql, String.class);
		return emplyeeCategoryList;
	}
	

}
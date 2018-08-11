package com.bas.employee.service.impl;

import java.sql.Time;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.bas.admin.dao.entity.EmployeeMonthAttendanceEntity;
import com.bas.admin.dao.entity.FaculityDailyAttendanceReportEntity;
import com.bas.admin.dao.entity.FacultyManualAttendanceEntity;
import com.bas.admin.dao.entity.HolidayEntryEntity;
import com.bas.admin.dao.entity.OrganizationTimeEntity;
import com.bas.admin.dao.entity.PaginationFaculityDailyAttendanceReportEntity;
import com.bas.admin.web.controller.form.EmployeeShowForm;
import com.bas.admin.web.controller.form.FaculityDailyAttendanceReportVO;
import com.bas.admin.web.controller.form.FacultyManualAttendance;
import com.bas.admin.web.controller.form.HolidayEntryForm;
import com.bas.admin.web.controller.form.OrganisationTimeForm;
import com.bas.admin.web.controller.form.PaginationFaculityDailyAttendanceReportVO;
import com.bas.common.constant.BASApplicationConstants;
import com.bas.common.dao.entity.EmployeeHeader;
import com.bas.common.util.DateUtils;
import com.bas.employee.dao.BasFacultyDao;
import com.bas.employee.dao.IEmployeeLMSDao;
import com.bas.employee.dao.entity.EmployeeShowFormEntity;
import com.bas.employee.dao.entity.FaculityLeaveMasterEntity;
import com.bas.employee.dao.entity.FacultyAttendStatusEntity;
import com.bas.employee.dao.entity.FacultyEntity;
import com.bas.employee.dao.entity.FacultyLeaveApprovalEntity;
import com.bas.employee.dao.entity.FacultyLeaveBalanceEntity1;
import com.bas.employee.dao.entity.FacultyLeaveTypeEntity;
import com.bas.employee.dao.entity.FacultySalaryMasterEntity;
import com.bas.employee.dao.entity.FacultyWorkingDaysEntity;
import com.bas.employee.dao.entity.LeaveBalanceEntity;
import com.bas.employee.dao.entity.ManualAttendanceEntity;
import com.bas.employee.dao.entity.ReportingManagerEntity;
import com.bas.employee.dao.entity.SubjectAlternativeArrangementsEntity;
import com.bas.employee.service.BasFacultyService;
import com.bas.employee.web.controller.form.FaculityLeaveMasterVO;
import com.bas.employee.web.controller.form.FacultyForm;
import com.bas.employee.web.controller.form.FacultyLeaveApprovalVO;
import com.bas.employee.web.controller.form.FacultyLeaveBalanceVO1;
import com.bas.employee.web.controller.form.FacultySalaryMasterVO;
import com.bas.employee.web.controller.form.FacultyWorkingDaysVO;
import com.bas.employee.web.controller.form.LeaveBalanceForm;
import com.bas.employee.web.controller.form.ManualAttendanceVO;
import com.bas.employee.web.controller.form.ReportingManagerVO;
import com.bas.employee.web.controller.form.SubjectAlternativeArrangementsVO;
import com.bas.hr.web.controller.model.EmployeeLeave;
import com.fasterxml.jackson.databind.util.BeanUtil;


/*
 * \\\\\
 */
@Service("BasFacultyServiceImpl")
@Transactional
public class BasFacultyServiceImpl implements BasFacultyService {

	@Autowired
	@Qualifier("BasFacultyDaoImpl")
	private BasFacultyDao basFacultyDao;
	
	@Autowired
	@Qualifier("EmployeeLMSDao")
	private IEmployeeLMSDao iEmployeeLMSDao;
	
	@Override
	public EmployeeLeave findEmployeeLeaveBalance(String empid,String requestid){
		requestid=requestid.trim();
		EmployeeLeave employeeLeave=new EmployeeLeave();
		LeaveBalanceEntity balanceEntity=basFacultyDao.findLeaveBalance(empid);
		FacultyLeaveApprovalEntity facultyLeaveApprovalEntity=basFacultyDao.findCurrentClByRequestId(requestid);
		float totalAvailableCls=0;
		//04(1),05(0.75),
		String totalDays=facultyLeaveApprovalEntity.getLeaveDays();

		if(totalDays!=null && totalDays.trim().length()>0) {
				String totalDaysTokens[]=totalDays.split(",");
				for(String str:totalDaysTokens){
						float cp=Float.parseFloat(str.substring(str.indexOf("(")+1,str.indexOf(")")));
						totalAvailableCls=totalAvailableCls+cp;
				}
		}
		employeeLeave.setMel(facultyLeaveApprovalEntity.getEl());
		employeeLeave.setMcl(facultyLeaveApprovalEntity.getCl());
		employeeLeave.setMlwp(facultyLeaveApprovalEntity.getLwp());
		
		float totalAvailableEls=0;
		String cElDays=facultyLeaveApprovalEntity.getElDays();
		if(cElDays!=null && cElDays.trim().length()>0) {
				String cElDaysTokens[]=cElDays.split(",");
				for(String str:cElDaysTokens){
						float cel=Float.parseFloat(str.substring(str.indexOf("(")+1,str.indexOf(")")));
						totalAvailableEls=totalAvailableEls+cel;
				}
		}
		if(facultyLeaveApprovalEntity.getLeaveType().equalsIgnoreCase(BASApplicationConstants.CL_LEAVE)){
			float totalCurrentCL=totalAvailableCls+balanceEntity.getCl();
			employeeLeave.setCl(totalCurrentCL);
		}else{
			employeeLeave.setCl(balanceEntity.getCl());
		}
		employeeLeave.setEl(balanceEntity.getEl()+totalAvailableEls);
		employeeLeave.setLwp(facultyLeaveApprovalEntity.getLwp());
		return employeeLeave;
    }

	@Override
	public List<FaculityLeaveMasterVO> LeaveHistory(String d1, String d2, String eid){
        List<FaculityLeaveMasterEntity> faculityLeaveMasterEntitieslist = basFacultyDao.LeaveHistoryByDate(d1,d2,eid);
        List<FaculityLeaveMasterVO> faculityLeaveMasterVOslist = new ArrayList<FaculityLeaveMasterVO>();
        for (FaculityLeaveMasterEntity flh : faculityLeaveMasterEntitieslist) {
            FaculityLeaveMasterVO faculityLeaveMasterVO = new FaculityLeaveMasterVO();
            BeanUtils.copyProperties(flh, faculityLeaveMasterVO);
            faculityLeaveMasterVOslist.add(faculityLeaveMasterVO);
        }
        return faculityLeaveMasterVOslist;
    }

	@Override
	public List<HolidayEntryForm> getHolidays(int m,int y) {
		List<HolidayEntryEntity> facList = basFacultyDao.getHoliday(m,y);
		List<HolidayEntryForm> fDAV = new ArrayList<HolidayEntryForm>();
		for (HolidayEntryEntity faculityDailyAttendanceReportEntity : facList) {
			HolidayEntryForm facDailyAttRep = new HolidayEntryForm();
			BeanUtils.copyProperties(faculityDailyAttendanceReportEntity, facDailyAttRep);
			fDAV.add(facDailyAttRep);
		}
		return fDAV;
	}

	@Override
	public List<FaculityDailyAttendanceReportVO> getAttStatusByDate(String d1, String d2, String eid) {
		List<FaculityDailyAttendanceReportEntity> facList = basFacultyDao.getAttendanceStatusByDate(d1,d2,eid);
		List<FaculityDailyAttendanceReportVO> fDAV = new ArrayList<FaculityDailyAttendanceReportVO>();
		for (FaculityDailyAttendanceReportEntity faculityDailyAttendanceReportEntity : facList) {
			FaculityDailyAttendanceReportVO facDailyAttRep = new FaculityDailyAttendanceReportVO();
			BeanUtils.copyProperties(faculityDailyAttendanceReportEntity, facDailyAttRep);
			fDAV.add(facDailyAttRep);
		}
		return fDAV;
	}
	

	@Override
	public List<FacultyLeaveApprovalVO> getLeaveApprovalForAdmin(String hrid) 	{
		List<FacultyLeaveApprovalEntity> empLeaveApprovalEntityList = basFacultyDao.getLeaveApprovalForAdmin(hrid);
		List<FacultyLeaveApprovalVO> employeeLeaveApprovalList = new ArrayList<FacultyLeaveApprovalVO>();
		for(FacultyLeaveApprovalEntity eLA: empLeaveApprovalEntityList){
			FacultyLeaveApprovalVO empLeaveApprovalVO = new FacultyLeaveApprovalVO();
			BeanUtils.copyProperties(eLA, empLeaveApprovalVO);
			employeeLeaveApprovalList.add(empLeaveApprovalVO);
		}
		return employeeLeaveApprovalList;
	}
	
	@Override
	public List<FacultyLeaveApprovalVO> getLeaveApprovalForManagement(String managementId) 	{
		List<FacultyLeaveApprovalEntity> empLeaveApprovalEntityList = basFacultyDao.getLeaveApprovalForManagement(managementId);
		List<FacultyLeaveApprovalVO> employeeLeaveApprovalList = new ArrayList<FacultyLeaveApprovalVO>();
		for(FacultyLeaveApprovalEntity eLA: empLeaveApprovalEntityList){
			FacultyLeaveApprovalVO empLeaveApprovalVO = new FacultyLeaveApprovalVO();
			BeanUtils.copyProperties(eLA, empLeaveApprovalVO);
			employeeLeaveApprovalList.add(empLeaveApprovalVO);
		}
		return employeeLeaveApprovalList;
	}
	
	@Override
	public List<FaculityDailyAttendanceReportVO> findEmployeesOnLeaveOnDate(String ondate) {
		List<FaculityDailyAttendanceReportEntity> empLeaveApprovalEntityList = basFacultyDao.findEmployeesOnLeaveOnDate(ondate);
		List<FaculityDailyAttendanceReportVO> employeeOnLeaveList = new ArrayList<FaculityDailyAttendanceReportVO>();
		for(FaculityDailyAttendanceReportEntity eLA: empLeaveApprovalEntityList) {
			FaculityDailyAttendanceReportVO empLeaveApprovalVO = new FaculityDailyAttendanceReportVO();
				BeanUtils.copyProperties(eLA, empLeaveApprovalVO);
				employeeOnLeaveList.add(empLeaveApprovalVO);
		}
		return employeeOnLeaveList;
	}

	@Override
	public List<FacultyLeaveApprovalVO> getPendingLeaveRequestByEmpid(String empid)
	{
		List<FacultyLeaveApprovalEntity> empLeaveApprovalEntityList = basFacultyDao.getPendingLeaveRequestByEmpid(empid);
		List<FacultyLeaveApprovalVO> employeeLeaveApprovalList = new ArrayList<FacultyLeaveApprovalVO>();
		for(FacultyLeaveApprovalEntity eLA: empLeaveApprovalEntityList){
			FacultyLeaveApprovalVO empLeaveApprovalVO = new FacultyLeaveApprovalVO();
			BeanUtils.copyProperties(eLA, empLeaveApprovalVO);
			employeeLeaveApprovalList.add(empLeaveApprovalVO);
		}
		return employeeLeaveApprovalList;
	}


	@Override
	public String addOrganisationTime(OrganisationTimeForm organisationTimeForm) {
		System.out.println(organisationTimeForm.getDom());
		System.out.println(organisationTimeForm.getDoe());
		OrganizationTimeEntity organisationTimeEntity = new OrganizationTimeEntity();
		BeanUtils.copyProperties(organisationTimeForm, organisationTimeEntity);
		String result = basFacultyDao.addOrganisationTime(organisationTimeEntity);
		return result;

	}
	@Override
	public List<OrganisationTimeForm> findOrgTimes() {
		List<OrganizationTimeEntity> organizationTimeEntitieslist=basFacultyDao.findOrgTimes();
		List<OrganisationTimeForm> organizationTimeVOlist=new ArrayList<OrganisationTimeForm>();
		for(OrganizationTimeEntity fs: organizationTimeEntitieslist){
			OrganisationTimeForm organizationTime=new OrganisationTimeForm();
			BeanUtils.copyProperties(fs,organizationTime);
			organizationTimeVOlist.add(organizationTime);
		}
		return organizationTimeVOlist;
	}


	@Override
	public String updateLeaveApprovalForAdmin(int requestID, String hrApproval){
		return basFacultyDao.updateLeaveApprovalForAdmin(requestID, hrApproval);
	}

	@Override
	public FacultyForm showEmployee(String id) {
		FacultyEntity form = basFacultyDao.showEmployee(id);
		FacultyForm formOrg = new FacultyForm();
		BeanUtils.copyProperties(form, formOrg);
		return formOrg;
	}


	@Override
	public byte[] findImgById(String empid) {
		byte[] img = basFacultyDao.findImageById(empid);
		return img;
	}

	@Override
	public List<String> selectDesignations() {
		return basFacultyDao.selectDesignations();
	}

	@Override
	public List<String> selectBloodGroups(){
		return basFacultyDao.selectBloodGroups();
	}


	@Override
	public List<String> selectReportingManagers() {
		return basFacultyDao.selectReportingManager();
	}

	@Override
	public boolean checkEmployeeProfileByNameAndMobile(String mobile,String name,String email) {
		return basFacultyDao.checkEmployeeProfileByNameAndMobile(mobile,name,email);
	}
	@Override
	public List<String> findRelation(String manager) {
		List<String> employees = basFacultyDao.findRelation(manager);
		return employees;
	}

	@Override
	public String applyAddendumLeave(FaculityLeaveMasterVO faculityLeaveMasterVO)	{
		FaculityLeaveMasterEntity facultyLeaveEntity = new FaculityLeaveMasterEntity();
		BeanUtils.copyProperties(faculityLeaveMasterVO, facultyLeaveEntity);
		return basFacultyDao.applyAddendumLeave(facultyLeaveEntity);
	}


	@Override
	public List<FaculityDailyAttendanceReportVO> showAttendusReportByDep(String date, String dep){
		List<FaculityDailyAttendanceReportEntity> facList = basFacultyDao.showAttendusReportByDep(date, dep);
		List<FaculityDailyAttendanceReportVO> fDAV = new ArrayList<FaculityDailyAttendanceReportVO>();
		for (FaculityDailyAttendanceReportEntity faculityDailyAttendanceReportEntity : facList) {
			FaculityDailyAttendanceReportVO facDailyAttRep = new FaculityDailyAttendanceReportVO();
			BeanUtils.copyProperties(faculityDailyAttendanceReportEntity, facDailyAttRep);
			fDAV.add(facDailyAttRep);
		}
		return fDAV;
	}


	@Override
	public List<FaculityDailyAttendanceReportVO> showAttendusReport(String date){
		List<FaculityDailyAttendanceReportEntity> facList = basFacultyDao.showAttendusReport(date);
		List<FaculityDailyAttendanceReportVO> fDAV = new ArrayList<FaculityDailyAttendanceReportVO>();
		for (FaculityDailyAttendanceReportEntity faculityDailyAttendanceReportEntity : facList) {
			FaculityDailyAttendanceReportVO facDailyAttRep = new FaculityDailyAttendanceReportVO();
			BeanUtils.copyProperties(faculityDailyAttendanceReportEntity, facDailyAttRep);
			fDAV.add(facDailyAttRep);
		}
		return fDAV;
	}


	@Override
	public String createEmployeeAccount(FacultyForm facultyForm) {
		boolean b=TransactionSynchronizationManager.isActualTransactionActive();
		System.out.println("______________)@++++++++++++++++++++++TX = "+b);
		FacultyEntity facultyEntity = new FacultyEntity();
		BeanUtils.copyProperties(facultyForm, facultyEntity);
		return basFacultyDao.createEmployeeAccount(facultyEntity);
	}


	@Override
	public String persistFaculty(FacultyForm facultyForm) {
		FacultyEntity facultyEntity = new FacultyEntity();
		BeanUtils.copyProperties(facultyForm, facultyEntity);
		return basFacultyDao.persistFaculty(facultyEntity);
	}

	@Override
	public String updateFaculty(FacultyForm facultyForm) {
		FacultyEntity facultyEntity = new FacultyEntity();
		BeanUtils.copyProperties(facultyForm, facultyEntity);
		return basFacultyDao.updateFaculty(facultyEntity);
	}
	
	@Override
	public String updateProfilePic(FacultyForm facultyForm) {
		FacultyEntity facultyEntity = new FacultyEntity();
		BeanUtils.copyProperties(facultyForm, facultyEntity);
		return basFacultyDao.updateProfilePic(facultyEntity);
	}

	@Override
	public String deletetFaculty(String name) {
		return basFacultyDao.deletetFaculty(name);
	}
	
	@Override
	public String deletetEmployeeById(String empid) {
		return basFacultyDao.deletetEmployeeById(empid);
	}

	@Override
	public FacultyForm findFacultyByName(String name) {
		FacultyEntity facultyEntity = basFacultyDao.findFacultyByName(name);
		FacultyForm facultyForm = new FacultyForm();
		BeanUtils.copyProperties(facultyEntity, facultyForm);
		return facultyForm;
	}

	@Override
	public EmployeeShowForm findFacultyByNamespeci(String name) {
		EmployeeShowFormEntity employentity= basFacultyDao.findFacultyByNamespecific(name);

		EmployeeShowForm employeeform=  new EmployeeShowForm();


		BeanUtils.copyProperties(employentity, employeeform);
		return employeeform;
	}

	@Override
	public List<FacultyForm> findAllFaculty() {
		List<FacultyEntity> facultyEntities = basFacultyDao.findAllFaculty();
		List<FacultyForm> facultyForms = new ArrayList<FacultyForm>();
		for (FacultyEntity fe : facultyEntities) {
			FacultyForm facultyForm = new FacultyForm();
			BeanUtils.copyProperties(fe, facultyForm);
			facultyForms.add(facultyForm);
		}
		return facultyForms;
	}
	
	@Override
	public List<FacultyForm> findAllFacultyByStatus(String employeeStatus) {
		List<FacultyEntity> facultyEntities = basFacultyDao.findAllFacultyByStatus(employeeStatus);
		List<FacultyForm> facultyForms = new ArrayList<FacultyForm>();
		for (FacultyEntity fe : facultyEntities) {
			FacultyForm facultyForm = new FacultyForm();
			BeanUtils.copyProperties(fe, facultyForm);
			facultyForms.add(facultyForm);
		}
		return facultyForms;
	}
	
	
	@Override
	public List<FacultyForm> findFacultyWithBirthday() {
		List<FacultyEntity> facultyEntities = basFacultyDao.findFacultyWithBirthday();
		List<FacultyForm> facultyForms = new ArrayList<FacultyForm>();
		for (FacultyEntity fe : facultyEntities) {
			FacultyForm facultyForm = new FacultyForm();
			BeanUtils.copyProperties(fe, facultyForm);
			facultyForms.add(facultyForm);
		}
		return facultyForms;
	}

	@Override
	public byte[] findPhotoByEmpId(String empid) {
		return basFacultyDao.findPhotoByEmpId(empid);
	}

	@Override
	public LeaveBalanceForm findLeaveBalance(String empid) {
		LeaveBalanceEntity leaveBalanceEntity = basFacultyDao
				.findLeaveBalance(empid);
		LeaveBalanceForm leaveBalanceForm = new LeaveBalanceForm();
		BeanUtils.copyProperties(leaveBalanceEntity,
				leaveBalanceForm);
		return leaveBalanceForm;
	}

	@Override
	public List<FaculityLeaveMasterVO> findLeaveHistory(String empid)  {
		List<FaculityLeaveMasterEntity> faculityLeaveMasterEntitieslist = basFacultyDao
				.findLeaveHistory(empid);
		List<FaculityLeaveMasterVO> faculityLeaveMasterVOslist = new ArrayList<FaculityLeaveMasterVO>();
		for (FaculityLeaveMasterEntity flh : faculityLeaveMasterEntitieslist) {
			FaculityLeaveMasterVO faculityLeaveMasterVO = new FaculityLeaveMasterVO();
			BeanUtils.copyProperties(flh, faculityLeaveMasterVO);
			faculityLeaveMasterVOslist.add(faculityLeaveMasterVO);
		}
		return faculityLeaveMasterVOslist;
	}

	@Override
	public List<FaculityDailyAttendanceReportVO> findAttendStatus(String fid, int monthValue) {
		List<FaculityDailyAttendanceReportEntity> attendStatusEntities = basFacultyDao
				.findAttendStatus(fid,monthValue);
		List<FaculityDailyAttendanceReportVO> facultyAttendStatusVOList = new ArrayList<FaculityDailyAttendanceReportVO>();
		for (FaculityDailyAttendanceReportEntity fe : attendStatusEntities) {
			FaculityDailyAttendanceReportVO facultyAttendStatusVO = new FaculityDailyAttendanceReportVO();
			BeanUtils.copyProperties(fe, facultyAttendStatusVO);
			facultyAttendStatusVOList.add(facultyAttendStatusVO);
		}
		return facultyAttendStatusVOList;
	}

	@Override
	public List<FacultySalaryMasterVO> findSalaryHistory(String empid) {
		List<FacultySalaryMasterEntity> facultySalaryMasterEntitieslist = basFacultyDao
				.findSalaryHistory(empid);
		List<FacultySalaryMasterVO> facultySalaryMasterVOslist = new ArrayList<FacultySalaryMasterVO>();
		for (FacultySalaryMasterEntity fs : facultySalaryMasterEntitieslist) {
			FacultySalaryMasterVO facultySalaryMasterVO = new FacultySalaryMasterVO();
			BeanUtils.copyProperties(fs, facultySalaryMasterVO);
			facultySalaryMasterVOslist.add(facultySalaryMasterVO);
		}
		return facultySalaryMasterVOslist;
	}

	@Override
	public FaculityLeaveMasterVO findLeaveAppData(String empid/*,String leaveMonth*/) {
		FaculityLeaveMasterEntity faculityLeaveMasterEntity = basFacultyDao
				.findLeaveAppData(empid/*,leaveMonth*/);
		FaculityLeaveMasterVO faculityLeaveMasterVO = new FaculityLeaveMasterVO();
		BeanUtils.copyProperties(faculityLeaveMasterEntity,
				faculityLeaveMasterVO);
		return faculityLeaveMasterVO;
	}

	@Override
	public void addLeaveEntry(FaculityLeaveMasterVO faculityLeaveMasterVO) {
		System.out.println("before Dao" + faculityLeaveMasterVO.getLeaveFrom());
		FaculityLeaveMasterEntity faculityLeaveMasterEntity=new FaculityLeaveMasterEntity();
		BeanUtils.copyProperties(faculityLeaveMasterVO, faculityLeaveMasterEntity);
		basFacultyDao.addLeaveEntry(faculityLeaveMasterEntity);
	}

	@Override
	public byte[] findEmpPhotoByName(String name) {
		return basFacultyDao.findPhotoByEmpName(name);
	}

	@Override
	public String enterLeaveHistory(FaculityLeaveMasterVO faculityLeaveMasterVO) {
		return basFacultyDao.enterLeaveHistory(faculityLeaveMasterVO);
	}

	@Override
	public List<FaculityLeaveMasterVO> findAllLeaveHistory() {
		List<FaculityLeaveMasterEntity> faculityLeaveMasterEntitieslist = basFacultyDao
				.findAllLeaveHistory();
		List<FaculityLeaveMasterVO> faculityLeaveMasterVOslist = new ArrayList<FaculityLeaveMasterVO>();
		for (FaculityLeaveMasterEntity flh : faculityLeaveMasterEntitieslist) {
			FaculityLeaveMasterVO faculityLeaveMasterVO = new FaculityLeaveMasterVO();
			BeanUtils.copyProperties(flh, faculityLeaveMasterVO);
			faculityLeaveMasterVOslist.add(faculityLeaveMasterVO);
		}
		return faculityLeaveMasterVOslist;

	}

	@Override
	public List<FaculityLeaveMasterVO> findAllEmpDb() {
		List<FaculityLeaveMasterEntity> faculityLeaveMasterEntitieslist = basFacultyDao
				.findAllEmpDb();
		List<FaculityLeaveMasterVO> faculityLeaveMasterVOslist = new ArrayList<FaculityLeaveMasterVO>();
		for (FaculityLeaveMasterEntity flh : faculityLeaveMasterEntitieslist) {
			FaculityLeaveMasterVO faculityLeaveMasterVO = new FaculityLeaveMasterVO();
			BeanUtils.copyProperties(flh, faculityLeaveMasterVO);
			faculityLeaveMasterVOslist.add(faculityLeaveMasterVO);
		}
		return faculityLeaveMasterVOslist;
	}

	@Override
	public String deleteLeaveHistory(String name, String date) {
		return basFacultyDao.deleteLeaveHistory(name, date);

	}

	@Override
	public ManualAttendanceVO findEmployeeDataForAttendance(String empid) {
		ManualAttendanceEntity manualAttendanceEntity = basFacultyDao
				.findEmployeeDataForAttendance(empid);
		ManualAttendanceVO manualAttendanceVO = new ManualAttendanceVO();
		BeanUtils.copyProperties(manualAttendanceEntity,manualAttendanceVO);
		return manualAttendanceVO;
	}

	@Override
	public String addEmployeeManulAttendance(
			ManualAttendanceVO manualAttendanceVO) {
		ManualAttendanceEntity manualAttendanceEntity = new ManualAttendanceEntity();
		BeanUtils.copyProperties(manualAttendanceVO, manualAttendanceEntity);
		return basFacultyDao.addEmployeeManulAttendance(manualAttendanceEntity);
	}

	@Override
	public List<FaculityLeaveMasterVO> findAllPendingLeaveHistory() {
		List<FaculityLeaveMasterEntity> faculityLeaveMasterEntitieslist = basFacultyDao
				.findAllPendingLeaveHistory();
		List<FaculityLeaveMasterVO> faculityLeaveMasterVOslist = new ArrayList<FaculityLeaveMasterVO>();
		for (FaculityLeaveMasterEntity flh : faculityLeaveMasterEntitieslist) {
			FaculityLeaveMasterVO faculityLeaveMasterVO = new FaculityLeaveMasterVO();
			BeanUtils.copyProperties(flh, faculityLeaveMasterVO);
			faculityLeaveMasterVOslist.add(faculityLeaveMasterVO);
		}
		return faculityLeaveMasterVOslist;
	}

	@Override
	public void updateLeaveHistory(String empNo, String date, String lstatus) {
		basFacultyDao.updateLeaveHistory(empNo, date,lstatus);
	}
	
	@Override
	public String deleteEmployeeManagerByEmpId(String empid) {
		return basFacultyDao.deleteEmployeeManagerByEmpId(empid);
	}
	@Override
	public List<ReportingManagerVO> findEmployeeListByManager(String managerId) {
		List<ReportingManagerEntity> employeeListForManager=basFacultyDao.findEmployeeListByManager(managerId);
		List<ReportingManagerVO> employeeListManagerVo = new ArrayList<ReportingManagerVO>();
		for (ReportingManagerEntity reportingManagerEntity : employeeListForManager) {
			ReportingManagerVO reportingManagerVO = new ReportingManagerVO();
			BeanUtils.copyProperties(reportingManagerEntity, reportingManagerVO);
			employeeListManagerVo.add(reportingManagerVO);
		}
		return employeeListManagerVo;
	}

	@Override
	public List<FaculityLeaveMasterVO> getReportingManagerList() {
		List<FaculityLeaveMasterEntity> faculityLeaveMasterEntities=basFacultyDao.getReportingManagerList();
		List<FaculityLeaveMasterVO> faculityLeaveMasterVOs=new ArrayList<FaculityLeaveMasterVO>();
		for (FaculityLeaveMasterEntity faculityLeaveMasterEntity : faculityLeaveMasterEntities) {
			FaculityLeaveMasterVO faculityLeaveMasterVO=new FaculityLeaveMasterVO();
			BeanUtils.copyProperties(faculityLeaveMasterEntity, faculityLeaveMasterVO);
			faculityLeaveMasterVOs.add(faculityLeaveMasterVO);
		}
		return faculityLeaveMasterVOs;
	}

	@Override
	public List<FaculityLeaveMasterVO> getCCToList() {
		List<FaculityLeaveMasterEntity> faculityLeaveMasterEntities=basFacultyDao.getCCToList();
		List<FaculityLeaveMasterVO> faculityLeaveMasterVOs=new ArrayList<FaculityLeaveMasterVO>();
		for (FaculityLeaveMasterEntity faculityLeaveMasterEntity : faculityLeaveMasterEntities) {
			FaculityLeaveMasterVO faculityLeaveMasterVO=new FaculityLeaveMasterVO();
			BeanUtils.copyProperties(faculityLeaveMasterEntity, faculityLeaveMasterVO);
			faculityLeaveMasterVOs.add(faculityLeaveMasterVO);
		}
		return faculityLeaveMasterVOs;
	}

	@Override
	public String enterRmLeaveHistory(
			FaculityLeaveMasterVO faculityLeaveMasterVO) {
		return basFacultyDao.enterRmLeaveHistory(faculityLeaveMasterVO);

	}

	@Override
	public List<FaculityLeaveMasterVO> findAllRmPendingLeaveHistory() {
		List<FaculityLeaveMasterEntity> faculityLeaveMasterEntitieslist = basFacultyDao
				.findAllRmPendingLeaveHistory();
		List<FaculityLeaveMasterVO> faculityLeaveMasterVOslist = new ArrayList<FaculityLeaveMasterVO>();
		for (FaculityLeaveMasterEntity flh : faculityLeaveMasterEntitieslist) {
			FaculityLeaveMasterVO faculityLeaveMasterVO = new FaculityLeaveMasterVO();
			BeanUtils.copyProperties(flh, faculityLeaveMasterVO);
			faculityLeaveMasterVOslist.add(faculityLeaveMasterVO);
		}
		return faculityLeaveMasterVOslist;
	}

	@Override
	public String updateEmployee(FaculityDailyAttendanceReportVO dfaAttendStatusVO, String fid, String newdate){
		FaculityDailyAttendanceReportEntity facultyAttendStatusEntity = new FaculityDailyAttendanceReportEntity();
		BeanUtils.copyProperties(dfaAttendStatusVO, facultyAttendStatusEntity);
		return basFacultyDao.updateEmployee(facultyAttendStatusEntity, fid, newdate);
	}



	@Override
	public String deleteAttendus(String employeeId, String attndDate) {
		return basFacultyDao.deleteAttendus(employeeId,attndDate);
	}


	@Override
	public List<String> selectDepartments(){
		return basFacultyDao.selectDepartments();
	}

	@Override
	public List<String> selectEmployeeType() {
		return basFacultyDao.selectEmployeeType();
	}

	@Override
	public List<String> searchEmployee(String employeeName){
		List<String> query = basFacultyDao.searchEmployee(employeeName);
		return query;
	}


	@Override
	public List<EmployeeShowForm> findAllEmployee() {
		List<FacultyEntity> employeeEntities = basFacultyDao.findAllFaculty();
		List<EmployeeShowForm> employeeForms = new ArrayList<EmployeeShowForm>();
//		List<FacultyForm> facultyForms = new ArrayList<FacultyForm>();
		for (FacultyEntity fe :employeeEntities) {
			EmployeeShowForm ef= new EmployeeShowForm();
			BeanUtils.copyProperties(fe, ef);
			employeeForms.add(ef);
		}
		return employeeForms;
	}

	@Override
	public List<FacultyLeaveApprovalVO> getLeaveApprovalForManager(int managerId)
	{
		List<FacultyLeaveApprovalEntity> empLeaveApprovalEntityList = basFacultyDao.getLeaveApprovalForManager(managerId);
		List<FacultyLeaveApprovalVO> employeeLeaveApprovalList = new ArrayList<FacultyLeaveApprovalVO>();
		for (FacultyLeaveApprovalEntity eLA: empLeaveApprovalEntityList)
		{
			FacultyLeaveApprovalVO empLeaveApprovalVO = new FacultyLeaveApprovalVO();
			BeanUtils.copyProperties(eLA, empLeaveApprovalVO);
			employeeLeaveApprovalList.add(empLeaveApprovalVO);
		}
		System.out.println("Dao" + employeeLeaveApprovalList);
		return employeeLeaveApprovalList;

	}

	@Override
	public String updateLeaveApprovalForManager(int requestId, String managerApproval) 	{
		return basFacultyDao.updateLeaveApprovalForManager(requestId, managerApproval);
	}

	@Override
	public ReportingManagerVO findReportingManagerByEmpId(String employeeId) {
		ReportingManagerVO reportingManagerVO = new ReportingManagerVO();
		ReportingManagerEntity	reportingManagerEntity=basFacultyDao.findReportingManagerByEmpId(employeeId);
		BeanUtils.copyProperties(reportingManagerEntity, reportingManagerVO);
		return reportingManagerVO;
	}


	@Override
	public List<String> searchEmployeeByManId(String employeeName){
		List<String> query = basFacultyDao.searchEmployeeByManId(employeeName);
		return query;
	}

	@Override
	public List<String> searchId(String employeeId){
		List<String> query = basFacultyDao.searchId(employeeId);
		return query;
	}

	/*
	 * PK
	 *
	 */
	@Override
	public List<FaculityDailyAttendanceReportVO> showAttendenceReportByDepForToday(String dep){
		List<FaculityDailyAttendanceReportEntity> facList = basFacultyDao.showAttendenceForTodayForSelectedDept(dep);
		List<FaculityDailyAttendanceReportVO> fDAV = new ArrayList<FaculityDailyAttendanceReportVO>();
		for (FaculityDailyAttendanceReportEntity faculityDailyAttendanceReportEntity : facList) {
			FaculityDailyAttendanceReportVO facDailyAttRep = new FaculityDailyAttendanceReportVO();
			BeanUtils.copyProperties(faculityDailyAttendanceReportEntity, facDailyAttRep);
			fDAV.add(facDailyAttRep);
		}
		return fDAV;
	}

	@Override
	public List<FaculityDailyAttendanceReportVO> showAttendenceReportForToday(){
		System.out.println("Hello from Service Implementation");
		List<FaculityDailyAttendanceReportEntity> facList = basFacultyDao.showAttendenceforTodayAllDepts();
		System.out.println("Bye from Service Implementation");
		List<FaculityDailyAttendanceReportVO> fDAV = new ArrayList<FaculityDailyAttendanceReportVO>();
		for (FaculityDailyAttendanceReportEntity faculityDailyAttendanceReportEntity : facList) {
			FaculityDailyAttendanceReportVO facDailyAttRep = new FaculityDailyAttendanceReportVO();
			BeanUtils.copyProperties(faculityDailyAttendanceReportEntity, facDailyAttRep);
			fDAV.add(facDailyAttRep);
		}
		//System.out.println(fDAV);
		return fDAV;
	}



	@Override
	public PaginationFaculityDailyAttendanceReportVO TodaysAttendanceWithPagination(int start, int noOfRecords) {
		System.out.println("Welcome from pagination service ");
		PaginationFaculityDailyAttendanceReportEntity paginationFaculityDailyAttendanceReportEntity=basFacultyDao.TodaysAttendanceWithPagination(start, noOfRecords);
		System.out.println("Welcome end from pagination service ");
		PaginationFaculityDailyAttendanceReportVO faculityDailyAttendanceReportVO=new PaginationFaculityDailyAttendanceReportVO();
		BeanUtils.copyProperties(paginationFaculityDailyAttendanceReportEntity, faculityDailyAttendanceReportVO);
		List<FaculityDailyAttendanceReportVO> faculityDailyAttendanceReportVOList=new  ArrayList<FaculityDailyAttendanceReportVO>();
		List<FaculityDailyAttendanceReportEntity> faculityDailyAttendanceReportEntities=paginationFaculityDailyAttendanceReportEntity.getFaculityDailyAttendanceReportEntityList();
		for(FaculityDailyAttendanceReportEntity fe:faculityDailyAttendanceReportEntities){
			FaculityDailyAttendanceReportVO form=new FaculityDailyAttendanceReportVO();
			  BeanUtils.copyProperties(fe, form);
			  faculityDailyAttendanceReportVOList.add(form);
		}
		faculityDailyAttendanceReportVO.setFaculityDailyAttendanceReportVOList(faculityDailyAttendanceReportVOList);
		return faculityDailyAttendanceReportVO;

	}

	@Override
	public List<String> findEmpoyeeIdsForLwpByDate(String lwpDate){
		return basFacultyDao.findEmpoyeeIdsForLwpByDate(lwpDate);
	}
	
	@Override
	public List<FaculityDailyAttendanceReportVO> findAllEmployeeForByDepartment(String departmentName,String employeeType){
		List<FaculityDailyAttendanceReportEntity> facList = basFacultyDao.findAllEmployeeForByDepartmentAndType(departmentName,employeeType);
		List<FaculityDailyAttendanceReportVO> fDAV = new ArrayList<FaculityDailyAttendanceReportVO>();
		for (FaculityDailyAttendanceReportEntity faculityDailyAttendanceReportEntity : facList) {
			FaculityDailyAttendanceReportVO facDailyAttRep = new FaculityDailyAttendanceReportVO();
			BeanUtils.copyProperties(faculityDailyAttendanceReportEntity, facDailyAttRep);
			fDAV.add(facDailyAttRep);
		}
		return fDAV;
	}

	@Override
    public List<FaculityDailyAttendanceReportVO> findAllEmployeeForAttendance(){
		List<FaculityDailyAttendanceReportEntity> facList = basFacultyDao.findAllEmployeeForAttendance();
		List<FaculityDailyAttendanceReportVO> fDAV = new ArrayList<FaculityDailyAttendanceReportVO>();
		for (FaculityDailyAttendanceReportEntity faculityDailyAttendanceReportEntity : facList) {
			FaculityDailyAttendanceReportVO facDailyAttRep = new FaculityDailyAttendanceReportVO();
			BeanUtils.copyProperties(faculityDailyAttendanceReportEntity, facDailyAttRep);
			fDAV.add(facDailyAttRep);
		}
		return fDAV;
    }

	@Override
	public List<FaculityDailyAttendanceReportVO> updateAttendenceForToday(List<String> checkedIds){

		List<FaculityDailyAttendanceReportEntity> facList = basFacultyDao.updateAttendenceForToday(checkedIds);

		List<FaculityDailyAttendanceReportVO> fDAV = new ArrayList<FaculityDailyAttendanceReportVO>();
		for (FaculityDailyAttendanceReportEntity faculityDailyAttendanceReportEntity : facList) {
			FaculityDailyAttendanceReportVO facDailyAttRep = new FaculityDailyAttendanceReportVO();
			BeanUtils.copyProperties(faculityDailyAttendanceReportEntity, facDailyAttRep);
			fDAV.add(facDailyAttRep);
		}
		return fDAV;

	}
	//End

	@Override
	public List<FaculityLeaveMasterVO> sortLeaveByDate(String d1, String d2, String eid){
		List<FaculityLeaveMasterEntity> faculityLeaveMasterEntitieslist = basFacultyDao.sortLeaveHistoryByDate(d1,d2,eid);
		List<FaculityLeaveMasterVO> faculityLeaveMasterVOslist = new ArrayList<FaculityLeaveMasterVO>();
		for (FaculityLeaveMasterEntity flh : faculityLeaveMasterEntitieslist) {
			FaculityLeaveMasterVO faculityLeaveMasterVO = new FaculityLeaveMasterVO();
			BeanUtils.copyProperties(flh, faculityLeaveMasterVO);
			faculityLeaveMasterVOslist.add(faculityLeaveMasterVO);
		}
		return faculityLeaveMasterVOslist;
	}


	@Override
	public FacultyLeaveBalanceVO1 findFacultyLeaveBalanceById(String empId) {
		System.out.println("Welcome from leave balance service 2");
		FacultyLeaveBalanceVO1 facultyLeaveBalanceVO = new FacultyLeaveBalanceVO1();
		FacultyLeaveBalanceEntity1 facultyLeaveBalanceEntity = basFacultyDao.findFacultyLeaveBalanceById(empId);
		BeanUtils.copyProperties(facultyLeaveBalanceEntity, facultyLeaveBalanceVO);
		System.out.println("leave balance service 5");
		return facultyLeaveBalanceVO;
	}

	@Override
	public FacultyLeaveBalanceVO1 updateLeaveBalance(FacultyLeaveBalanceVO1 facultyLeaveBalanceVOCopy) {
		FacultyLeaveBalanceEntity1 facultyLeaveBalanceEntity = new FacultyLeaveBalanceEntity1();
		BeanUtils.copyProperties(facultyLeaveBalanceVOCopy, facultyLeaveBalanceEntity);
		FacultyLeaveBalanceEntity1 facultyLeaveBalanceEntityCopy =  basFacultyDao.updateLeaveBalance(facultyLeaveBalanceEntity);
		FacultyLeaveBalanceVO1 facultyLeaveBalanceVO = new FacultyLeaveBalanceVO1();
		BeanUtils.copyProperties(facultyLeaveBalanceEntityCopy, facultyLeaveBalanceVO);
		return facultyLeaveBalanceVO;
	}

	@Override
	public void saveToFacultyWorkingTable(FacultyWorkingDaysVO fwd){
		FacultyWorkingDaysEntity fwdEntity = new FacultyWorkingDaysEntity();
		BeanUtils.copyProperties(fwd,fwdEntity);
		basFacultyDao.saveToFacultyWorkingTable(fwdEntity);
	}

    @Override
    public String markLwpService(int id,String desc) {
        return basFacultyDao.markLwpInDb(id,desc);
    }

	@Override
	public String activateOrganisationTime(String sno) {
		 return basFacultyDao.activateOrganisationTime(sno);
	}

	@Override
	 public Map<EmployeeHeader,List<String>> getDeptMonthlyAttendance(String department, String selectedMonth, String year) {
		String month = DateUtils.getCurrentMonth(selectedMonth);
		List<EmployeeMonthAttendanceEntity> empMonthAttendanceList = basFacultyDao.getDeptMonthlyAttendanceInfo(department, month, year);
		int maxDate = 0;
		// Logic for no of Days in a Month(For January = 31 , for
		// February=28/29 , and June=30 etc.. )
		maxDate = getTotalDaysInMonth(month, year);
		Map<EmployeeHeader, List<String>> updatedEmployeeMonthlyLeaveStatus = getProcessedAttendanceRecord(empMonthAttendanceList, month, year, maxDate);
		decideDeptLeaveCategory(department, month, year, updatedEmployeeMonthlyLeaveStatus, maxDate);
		return updatedEmployeeMonthlyLeaveStatus;
	}
	
	@Override
	public boolean isEmployeePresentOnHoliday(String cdate, String empid){
		return basFacultyDao.isEmployeePresentOnHoliday(cdate, empid);
	}

	@Override
	public Map<EmployeeHeader, List<String>> getEmployeeMonthlyAttendanceStatus(String eid, String selectedMonth,
			String year) {
		String month = DateUtils.getCurrentMonth(selectedMonth);
		List<EmployeeMonthAttendanceEntity> empMonthAttendanceList = basFacultyDao.getEmployeeMonthlyAttendanceInfo(eid, month, year);
		int maxDate = 0;
		maxDate = getTotalDaysInMonth(month, year);
		Map<EmployeeHeader, List<String>> updatedEmployeeMonthlyLeaveStatus = getProcessedAttendanceRecord(empMonthAttendanceList, month, year, maxDate);
		decideLeaveCategory(eid, month, year, updatedEmployeeMonthlyLeaveStatus, maxDate);
		return updatedEmployeeMonthlyLeaveStatus;
	}

	private Map<EmployeeHeader, List<String>> getProcessedAttendanceRecord(List<EmployeeMonthAttendanceEntity> empMonthAttendanceList, String month, String year, int maxDate){
		Map<EmployeeHeader, List<String>> employeeMonthlyLeaveStatus = new LinkedHashMap<EmployeeHeader, List<String>>();
		int nodays = 31;
		boolean firstTime = true;
		String pempId = "";
		String cempId = "";
		int totalWorkedDays = 0;
		EmployeeHeader employeeHeader = null;

		for (EmployeeMonthAttendanceEntity emae : empMonthAttendanceList) {
			cempId = emae.getEid();
			if (cempId == null) {
				continue;
			}
			if (!cempId.equals(pempId)) {
				firstTime = true;
				if (employeeHeader != null) {
					employeeHeader.setTotalDaysWorked(totalWorkedDays);
				}
				totalWorkedDays = 0;
			}
			if (firstTime) {
				employeeHeader = new EmployeeHeader();
				employeeHeader.setEid(emae.getEid());
				employeeHeader.setName(emae.getName());
				employeeHeader.setDepartment(emae.getDepartment());
				employeeHeader.setDesignation(emae.getDesignation());

				List<String> monthLeaveStatusList = new ArrayList<String>(nodays);
				if (maxDate == nodays) {
					// if a given month has exactly 31 days in it, fill the
					// monthLeaveStatusList with value 'A'.
					for (int i = 0; i < nodays; i++) {
						monthLeaveStatusList.add(BASApplicationConstants.ABSENT);
					}
				} else if (maxDate < nodays) {
					int dayCount;
					for (dayCount = 0; dayCount < maxDate; dayCount++) {
						monthLeaveStatusList.add(BASApplicationConstants.ABSENT);
					}
					for (int temp = dayCount; temp < nodays; temp++) {
						monthLeaveStatusList.add(BASApplicationConstants.BLANK);
					}
				}
				String cdate = emae.getCdate();
				if (cdate != null) {
					String day = cdate.split("-")[2];
					int intDay = Integer.parseInt(day);
					// Replace 'A' by 'P' depending upon the number of days in a
					// month
					if (!(monthLeaveStatusList.get(intDay - 1)).equals("-")) {
						boolean poh=isEmployeePresentOnHoliday(cdate, emae.getEid());
						if(!poh){
								monthLeaveStatusList.set(intDay - 1, BASApplicationConstants.PRESENT);
								totalWorkedDays++;
						}else{
							monthLeaveStatusList.set(intDay - 1, BASApplicationConstants.PRESENT_ON_HOLIDAY);
							//not counting this since this date was organization holiday
							//totalWorkedDays++; 
						}
						// employeeHeader.setTotalDaysWorked(totalWorkedDays);
					}
				}
				employeeMonthlyLeaveStatus.put(employeeHeader, monthLeaveStatusList);
				firstTime = false;
			} else {
				// the Employee entry in the employeeHeader already exists, so
				// just append value 'P' in the monthLeaveStatusList
				String cdate = emae.getCdate();
				if (cdate != null) {
					String day = cdate.split("-")[2];
					int intDay = Integer.parseInt(day);
					List<String> monthLeaveStatusList = employeeMonthlyLeaveStatus.get(employeeHeader);
					
					boolean poh=isEmployeePresentOnHoliday(cdate, emae.getEid());
					if(!poh){
						monthLeaveStatusList.set(intDay - 1, BASApplicationConstants.PRESENT);
						totalWorkedDays++;
					}else{
						monthLeaveStatusList.set(intDay - 1, BASApplicationConstants.PRESENT_ON_HOLIDAY);
						//not counting this since this date was organization holiday
						//totalWorkedDays++; 
					}
					// employeeHeader.setTotalDaysWorked(totalWorkedDays);
				}
			}
			pempId = emae.getEid();
		} // for()
		// This is special case for last record
		if(employeeHeader!=null)
		employeeHeader.setTotalDaysWorked(totalWorkedDays);

		decideHolidayCategory(month, year, employeeMonthlyLeaveStatus);
		return employeeMonthlyLeaveStatus;
	}

	private int getTotalDaysInMonth(String month, String year){
		int monthNumber = Integer.parseInt(month);
		int yearNumber = Integer.parseInt(year);
		int maxDate=0;
		if(monthNumber == 1 || monthNumber == 3 || monthNumber == 5 || monthNumber == 7 || monthNumber == 8
				|| monthNumber == 10 || monthNumber == 12){
			maxDate = 31;
		}else if (monthNumber == 2){
			if(yearNumber %4 != 0)
				maxDate = 28;
			else
				maxDate = 29;
		}else{
			maxDate = 30;
		}
		return maxDate;
	}

	private void decideHolidayCategory(String month, String year,
			Map<EmployeeHeader, List<String>> employeeMonthlyLeaveStatus) {
		List<HolidayEntryEntity> holidayCategoryList = basFacultyDao.getHolidayCategoryList(month, year, employeeMonthlyLeaveStatus);
		Set<Entry<EmployeeHeader, List<String>>> allEmployeesKeyAndValues = employeeMonthlyLeaveStatus.entrySet();
		for (Entry<EmployeeHeader, List<String>> employeesKeyAndValues : allEmployeesKeyAndValues) {
			EmployeeHeader empHeader = employeesKeyAndValues.getKey();
			float totalWorkedDays = empHeader.getTotalDaysWorked();
			// fetch leave Date for employees....by id
			// List<String> leaveDates
			List<String> monthLeaveStatusList = employeesKeyAndValues.getValue();
			for (HolidayEntryEntity hee : holidayCategoryList) {
				String day = (hee.getCdate()).split("-")[2];
				int intDay = Integer.parseInt(day);
				if (!hee.getWorking().equalsIgnoreCase(BASApplicationConstants.YES)) {
					if(monthLeaveStatusList.get(intDay - 1).equalsIgnoreCase("A")) {
							if (hee.getWeekend().equalsIgnoreCase(BASApplicationConstants.YES)) {
								if (hee.getHolidayType().equalsIgnoreCase("Sunday")){
									monthLeaveStatusList.set(intDay - 1, BASApplicationConstants.SUNDAY);
								}else{
									monthLeaveStatusList.set(intDay - 1, BASApplicationConstants.SATURDAY);
								}
							}else{
								monthLeaveStatusList.set(intDay - 1, BASApplicationConstants.HOLIDAY);
							}
					}
					totalWorkedDays++;
				} else {
					monthLeaveStatusList.set(intDay - 1, BASApplicationConstants.DEMO_ON_HOLIDAY);
					totalWorkedDays++;
				}//if else
			}//for
			empHeader.setTotalDaysWorked(totalWorkedDays);
		}//for(Entry)
	}

	private void decideDeptLeaveCategory(String department, String month, String year,
			Map<EmployeeHeader, List<String>> employeeMonthlyLeaveStatus, int maxDate) {

		List<FacultyLeaveTypeEntity> facultyLeaveCategoryList = basFacultyDao.getDeptLeaveCategoryList(department, month, year, employeeMonthlyLeaveStatus, maxDate);
		fillLeaveTypeInfo(employeeMonthlyLeaveStatus, facultyLeaveCategoryList, month, maxDate);
	}//decideDeptLeaveCategory()

	private void decideLeaveCategory(String eid, String month, String year,
			Map<EmployeeHeader, List<String>> employeeMonthlyLeaveStatus, int maxDate) {

		List<FacultyLeaveTypeEntity> facultyLeaveCategoryList = basFacultyDao.getLeaveCategoryList(eid, month, year, employeeMonthlyLeaveStatus, maxDate);
		fillLeaveTypeInfo(employeeMonthlyLeaveStatus, facultyLeaveCategoryList, month, maxDate);
	}//decideLeaveCategory()


	private Map<EmployeeHeader, List<String>> fillLeaveTypeInfo(Map<EmployeeHeader, List<String>> employeeMonthlyLeaveStatus, List<FacultyLeaveTypeEntity> facultyLeaveCategoryList, String month, int maxDate){

		System.out.println(facultyLeaveCategoryList.toString());
		int monthNumber = Integer.parseInt(month);

		Set<Entry<EmployeeHeader, List<String>>> allEmployeesKeyAndValues = employeeMonthlyLeaveStatus.entrySet();
		for (Entry<EmployeeHeader, List<String>> employeesKeyAndValues : allEmployeesKeyAndValues){

			EmployeeHeader empHeader = employeesKeyAndValues.getKey();
			List<String> monthLeaveStatusList = employeesKeyAndValues.getValue();
			float totalWorkedDays = empHeader.getTotalDaysWorked();
			String tableEid = empHeader.getEid();

			for(FacultyLeaveTypeEntity fLeaveEntity : facultyLeaveCategoryList){
				String empId = String.valueOf(fLeaveEntity.getEmpId());
				if(tableEid.equals(empId)){
					System.out.println(empId);
					String leaveType = fLeaveEntity.getLeaveType();
					String leaveDates = fLeaveEntity.getLeaveDates();

					if(leaveDates != null && leaveDates.length() > 0){
						String leaveDatesHolder[] = leaveDates.split(",");
						int datesholderSize = leaveDatesHolder.length;

						List<String>specificLeaveDates = new ArrayList<String>();
						for(int i=0; i< datesholderSize; i++ ){
							int mm = Integer.parseInt(leaveDatesHolder[i].split("-")[1]);
							if(mm == monthNumber){
								specificLeaveDates.add(leaveDatesHolder[i]);
							}
						}
						String selectedDatesHolder[]= specificLeaveDates.toArray(new String[specificLeaveDates.size()]);
						float lwpInAccount = fLeaveEntity.getLwp();
						int lwpCount = (int) lwpInAccount;
						int dateCounter = selectedDatesHolder.length - lwpCount;

						String startDate = selectedDatesHolder[0];
						String endDate = selectedDatesHolder[selectedDatesHolder.length - 1];
						int startDD = Integer.parseInt(startDate.split("-")[2]);
						int endDD = Integer.parseInt(endDate.split("-")[2]);

						for(String filterDates : selectedDatesHolder){
							if(filterDates != null){
								int dd = Integer.parseInt(filterDates.split("-")[2]);
								boolean holiday = checkForHolidayOnLeaveApplication(monthLeaveStatusList, dd - 1);
								boolean leaveOnDemoDay = monthLeaveStatusList.get(dd-1).equalsIgnoreCase(BASApplicationConstants.DEMO_ON_HOLIDAY);
								if(holiday != true){
									if (dd >= startDD && dd <= maxDate) {
										totalWorkedDays = getLeaveType(leaveType, monthLeaveStatusList, dateCounter, leaveOnDemoDay, dd, fLeaveEntity, totalWorkedDays);
									}else if(dd <= endDD && dd <= maxDate){
										totalWorkedDays = getLeaveType(leaveType, monthLeaveStatusList, dateCounter, leaveOnDemoDay, dd, fLeaveEntity, totalWorkedDays);
									}
								}
							}
							dateCounter--;
						}
					}
				}
			}
			empHeader.setTotalDaysWorked(totalWorkedDays);
		}
		return employeeMonthlyLeaveStatus;
	}// fillLeaveTypeInfo()

	private boolean checkForHolidayOnLeaveApplication(List<String> monthLeaveStatusList, int dayOfMonth){
		String holidayOrNot = monthLeaveStatusList.get(dayOfMonth);
		if(holidayOrNot.equalsIgnoreCase(BASApplicationConstants.SUNDAY) || holidayOrNot.equalsIgnoreCase(BASApplicationConstants.SATURDAY) || holidayOrNot.equalsIgnoreCase(BASApplicationConstants.HOLIDAY))
			return true;
		return false;
	}

	private float getLeaveType(String leaveType, List<String> monthLeaveStatusList, int dateCounter, boolean leaveOnDemoDay, int dd, FacultyLeaveTypeEntity fLeaveEntity, float totalWorkedDays){
		if(!leaveType.equalsIgnoreCase(BASApplicationConstants.LWP_LEAVE) && dateCounter>0){
			if(leaveOnDemoDay == true){
				monthLeaveStatusList.set(dd - 1, BASApplicationConstants.LWP_LEAVE);
				totalWorkedDays--;
			}else{
				if(monthLeaveStatusList.get(dd-1).equalsIgnoreCase(BASApplicationConstants.ABSENT)){
					if(fLeaveEntity.getInAccount()!=null){
						int inAccount= Integer.parseInt(fLeaveEntity.getInAccount().split("-")[2]);
						if(inAccount == dd){
							monthLeaveStatusList.set(dd - 1, BASApplicationConstants.IN_ACCOUNT_LEAVE);
							totalWorkedDays++;
						}else{
							monthLeaveStatusList.set(dd - 1, BASApplicationConstants.LEAVE);
							totalWorkedDays++;
						}
					}
					else{
						monthLeaveStatusList.set(dd - 1, BASApplicationConstants.LEAVE);
						totalWorkedDays++;
					}
				}
			}
		}else{
			if(monthLeaveStatusList.get(dd-1).equalsIgnoreCase(BASApplicationConstants.ABSENT)){
				monthLeaveStatusList.set(dd - 1, BASApplicationConstants.LWP_LEAVE);
			}
		}
		return totalWorkedDays;
	}//getLeaveType()
	
	
	
	@Override
	public List<FacultyLeaveApprovalVO> findEmployeesLeaveAppOnDate(String ondate)
	{
		List<FacultyLeaveApprovalEntity> empOnLeavList=basFacultyDao.findEmployeesLeaveAppOnDate(ondate);
		List<FacultyLeaveApprovalVO> empLeaveList=new ArrayList<FacultyLeaveApprovalVO>();
		for(FacultyLeaveApprovalEntity facultyLeaveApprovalEntity: empOnLeavList){
			FacultyLeaveApprovalVO facultyLeaveApprovalVO=new FacultyLeaveApprovalVO();
		 	BeanUtils.copyProperties(facultyLeaveApprovalEntity,facultyLeaveApprovalVO);
			empLeaveList.add(facultyLeaveApprovalVO);
	   }
		return empLeaveList;
	}

	@Override
	public FacultyLeaveApprovalVO employeeOnLeaveDetailByAjax(String pempRequestId,String pempid) {
		
		FacultyLeaveApprovalEntity empOnLeavDetail=basFacultyDao.employeeOnLeaveDetailByAjax(pempRequestId,pempid);
		FacultyLeaveApprovalVO empOnLeaveD=new FacultyLeaveApprovalVO();
		BeanUtils.copyProperties(empOnLeavDetail,empOnLeaveD);
		return empOnLeaveD;
	}

	@Override
	public FacultyLeaveApprovalVO empAppliedLeaveDetail(String empRequestId) {
		FacultyLeaveApprovalEntity facultyAppliedLeaveDetail=basFacultyDao.empAppliedLeaveDetail(empRequestId);
		FacultyLeaveApprovalVO facultyAppliedLeave=new FacultyLeaveApprovalVO();
		BeanUtils.copyProperties(facultyAppliedLeaveDetail,facultyAppliedLeave);
		return facultyAppliedLeave;
	}
	
	@Override
	public String findEmpNameByEmpId(String empid){
		return basFacultyDao.findEmpNameByEmpId(empid);
	}

	@Override
	public FacultyLeaveApprovalVO empApprovedLeaveDetail(String empRequestId) {
		FacultyLeaveApprovalEntity facultyApprovedLeaveDetail=basFacultyDao.empApprovedLeaveDetail(empRequestId);
		FacultyLeaveApprovalVO facultyApprovedLeave=new FacultyLeaveApprovalVO();
		BeanUtils.copyProperties(facultyApprovedLeaveDetail,facultyApprovedLeave);
		return facultyApprovedLeave;
	}


	
	@Override
	public String activeEmployeeByEmpId(String empid) {
		return basFacultyDao.activeEmployeeByEmpId(empid);
	}

	@Override
	public FacultyForm findEmplyeeByAjax(String empid) {
		FacultyEntity facultyEntity=basFacultyDao.findEmplyeeByAjax(empid);
		FacultyForm facultyForm=new FacultyForm();
		BeanUtils.copyProperties(facultyEntity,facultyForm);
		return facultyForm;
	}

	@Override
	public String updateEmplyeeByAjax(FacultyForm facultyForm) {
		String msg=basFacultyDao.updateEmplyeeByAjax(facultyForm);
		return msg;
	}
	
	@Override 
	public FacultyManualAttendance findEmployeeAttendanceByDate(String eid, String cdate){
		FacultyAttendStatusEntity facultyAttendStatusEntity=basFacultyDao.findEmployeeAttendanceByDate(eid,cdate);
		FacultyManualAttendance facultyManualAttendance=new FacultyManualAttendance();
		BeanUtils.copyProperties(facultyAttendStatusEntity,facultyManualAttendance,new String[]{"cdate","dom"});
		return facultyManualAttendance;
	}

	@Override
	public String employeeManualAttendancePost(String eid,String cdate, String detail, Time intime, Time outtime) {
		String value=basFacultyDao.employeeManualAttendancePost(eid,cdate,detail,intime,outtime);
		return value;
	}

	@Override
	public List<FacultyManualAttendance> empManualRequestAtt() {
	
		List<FacultyManualAttendanceEntity> facultyManualAttendanceList=basFacultyDao.empManualRequestAtt();
		List<FacultyManualAttendance> facultyManualAttList=new ArrayList<FacultyManualAttendance>();
		for(FacultyManualAttendanceEntity facultyManualAttendanceEntity:facultyManualAttendanceList)
		{
			FacultyManualAttendance facultyManualAttendance=new FacultyManualAttendance();
			BeanUtils.copyProperties(facultyManualAttendanceEntity, facultyManualAttendance);
			facultyManualAttList.add(facultyManualAttendance);
		}
		return facultyManualAttList;
	}

	@Override
	public String rejectEmpAttRequestById(String empid,String cdate) {
		String value=basFacultyDao.rejectEmpAttRequestById(empid,cdate);
		return value;
	}

	@Override
	public String approveEmpAttRequestById(String empid,String cdate) {
		String value=basFacultyDao.approveEmpAttRequestById(empid,cdate);
		return value;
	}

	@Override
	public List<SubjectAlternativeArrangementsVO> findAllemployeeAlternateClassStatus() {
		List<SubjectAlternativeArrangementsEntity> alternativeArrangementsEntityList=basFacultyDao.findAllemployeeAlternateClassStatus();
		List<SubjectAlternativeArrangementsVO> alternativeArrangementsVOList=new ArrayList<SubjectAlternativeArrangementsVO>();
		for(SubjectAlternativeArrangementsEntity alternativeArrangementsEntity:alternativeArrangementsEntityList){
			SubjectAlternativeArrangementsVO alternativeArrangementsVO=new SubjectAlternativeArrangementsVO();
			BeanUtils.copyProperties(alternativeArrangementsEntity, alternativeArrangementsVO);
			alternativeArrangementsVOList.add(alternativeArrangementsVO);
		}
		return alternativeArrangementsVOList;
	}

	@Override
	public List<String> selectEmployeeCategory() {
		 return basFacultyDao.selectEmployeeCategory();
	}

//	@Override
//	public List<FacultyFormForComboBox> findEmployeeDataByAjax() {
//		
//		List<FacultyEntityForComboBox> facultyforComboBoxList=basFacultyDao.findEmployeeDataByAjax();
//		List <FacultyFormForComboBox> facultyComboBoxList=new ArrayList<>();
//		for(FacultyEntityForComboBox facultyEntityList:facultyforComboBoxList)
//		{
//			FacultyFormForComboBox facultydataComboBoxList=new FacultyFormForComboBox();
//			BeanUtils.copyProperties(facultyEntityList,facultydataComboBoxList);
//			facultyComboBoxList.add(facultydataComboBoxList);
//		}
//		return facultyComboBoxList;
//	}
	
	@Override
	public Map<String, String> selectDepartmentsAsMap(){
		return basFacultyDao.selectDepartmentsAsMap();
	}
	
}

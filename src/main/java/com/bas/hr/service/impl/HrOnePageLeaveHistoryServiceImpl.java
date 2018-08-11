package com.bas.hr.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bas.admin.aop.logger.Loggable;
import com.bas.common.constant.BASApplicationConstants;
import com.bas.common.dao.entity.EmployeeLeaveDetailEntity;
import com.bas.common.util.DateUtils;
import com.bas.employee.dao.BasFacultyDao;
import com.bas.employee.dao.IEmployeeLMSDao;
import com.bas.employee.dao.entity.FaculityLeaveMasterEntity;
import com.bas.hr.service.HrOnePageLeaveHistoryService;
import com.bas.hr.web.controller.model.EmployeeOnePageLeaveHistoryVO;

@Service("HrOnePageLeaveHistoryServiceImpl")
@Transactional
public class HrOnePageLeaveHistoryServiceImpl implements
		HrOnePageLeaveHistoryService {

	@Autowired
	@Qualifier("BasFacultyDaoImpl")
	private BasFacultyDao basFacultyDao;
	
	@Autowired
	@Qualifier("EmployeeLMSDao")
	private IEmployeeLMSDao iEmployeeLMSDao;

	@Loggable
	@Override
	public List<EmployeeOnePageLeaveHistoryVO> findEmployeeOnePageLeaveHistory(
			String empid, String sDate, String eDate) {
		EmployeeLeaveDetailEntity employeeLeaveDetailEntity=iEmployeeLMSDao.findEmployeeLeaveDetail(empid);
		
		List<EmployeeOnePageLeaveHistoryVO> employeeOnePageLeaveHistoryList = new ArrayList<EmployeeOnePageLeaveHistoryVO>();
		// String startDate="01-07-2015";
		// String endDate="30-06-2016";
		// LOOP
		List<String> endDateList = DateUtils.getEndDateMonthList();
		List<String> startDateList = DateUtils.getStartMonthDateList();
		float clinaccount=1.75F;
		
		float elinaccount=Float.parseFloat(employeeLeaveDetailEntity!=null?employeeLeaveDetailEntity.getPel():"0");
		for (int x = 0; x < 12; x++) {
			List<FaculityLeaveMasterEntity> faculityLeaveHistoryList = basFacultyDao
					.LeaveHistoryByDate(startDateList.get(x), endDateList.get(x),empid);
			// all the FEB Leaves only
			EmployeeOnePageLeaveHistoryVO employeeOnePageLeaveHistoryVO = new EmployeeOnePageLeaveHistoryVO();
			if(faculityLeaveHistoryList!=null) {
			for (FaculityLeaveMasterEntity entity : faculityLeaveHistoryList) {
				if (entity.getLeaveType().equalsIgnoreCase(
						BASApplicationConstants.CL_LEAVE)) {
					String leaveDates = entity.getLeaveDays();
					if (leaveDates != null && leaveDates.length() > 0) {
						String clDates = employeeOnePageLeaveHistoryVO
								.getCldates();
						if (clDates == null) {
							clDates = "";
						}
						clDates = clDates + leaveDates;
						employeeOnePageLeaveHistoryVO.setCldates(clDates);
						float clnoleaves = employeeOnePageLeaveHistoryVO
								.getClnoleaves();
						clnoleaves = clnoleaves+entity.getCcl();
						employeeOnePageLeaveHistoryVO.setClnoleaves(clnoleaves);
					}
					if (entity.getLwp() > 0) {
						String lwpDays = entity.getLwpDays();
						String lwpdates = employeeOnePageLeaveHistoryVO
								.getLwpdates();
						if (lwpdates == null) {
							lwpdates = "";
						}
						lwpdates = lwpdates + lwpDays;
						employeeOnePageLeaveHistoryVO.setLwpdates(lwpdates);
						float lwpleaves = employeeOnePageLeaveHistoryVO
								.getLwpleaves();
						lwpleaves = lwpleaves + entity.getLwp();
						employeeOnePageLeaveHistoryVO.setLwpleaves(lwpleaves);
					}
					 if (entity.getCel() > 0) {
						String eleaveDates = entity.getElDays();
						String elDates = employeeOnePageLeaveHistoryVO
								.getEldates();
						if (elDates == null) {
							elDates = "";
						}
						elDates = elDates + eleaveDates;
						employeeOnePageLeaveHistoryVO.setEldates(elDates);
						float elnoleaves = employeeOnePageLeaveHistoryVO
								.getElnoleaves();
						elnoleaves = elnoleaves + entity.getCel();
						employeeOnePageLeaveHistoryVO.setElnoleaves(elnoleaves);
					}
				} else if (entity.getLeaveType().equalsIgnoreCase(
						BASApplicationConstants.EL_LEAVE)) {
					String leaveDates = entity.getLeaveDays();
					if (leaveDates != null && leaveDates.length() > 0) {
						String clDates = employeeOnePageLeaveHistoryVO
								.getCldates();
						if (clDates == null) {
							clDates = "";
						}
						clDates = clDates + leaveDates;
						employeeOnePageLeaveHistoryVO.setCldates(clDates);
						float clnoleaves = employeeOnePageLeaveHistoryVO
								.getClnoleaves();
						clnoleaves =clnoleaves+ entity.getCcl();
						employeeOnePageLeaveHistoryVO.setClnoleaves(clnoleaves);
					}
					if (entity.getLwp() > 0) {
						String lwpDays = entity.getLwpDays();
						String lwpdates = employeeOnePageLeaveHistoryVO
								.getLwpdates();
						if (lwpdates == null) {
							lwpdates = "";
						}
						lwpdates = lwpdates + lwpDays;
						employeeOnePageLeaveHistoryVO.setLwpdates(lwpdates);
						float lwpleaves = employeeOnePageLeaveHistoryVO
								.getLwpleaves();
						lwpleaves = lwpleaves + entity.getLwp();
						employeeOnePageLeaveHistoryVO.setLwpleaves(lwpleaves);
					}
					 if (entity.getCel() > 0) {
						String eleaveDates = entity.getElDays();
						String elDates = employeeOnePageLeaveHistoryVO
								.getEldates();
						if (elDates == null) {
							elDates = "";
						}
						elDates = elDates + eleaveDates;
						employeeOnePageLeaveHistoryVO.setEldates(elDates);
						float elnoleaves = employeeOnePageLeaveHistoryVO
								.getElnoleaves();
						elnoleaves = elnoleaves + entity.getCel();
						employeeOnePageLeaveHistoryVO.setElnoleaves(elnoleaves);
					}

				} else if (entity.getLeaveType().equalsIgnoreCase(
						BASApplicationConstants.SL_LEAVE)) {
					String leaveDates = entity.getLeaveDays();
					if (leaveDates != null && leaveDates.length() > 0) {
						String slDates = employeeOnePageLeaveHistoryVO
								.getSldates();
						if (slDates == null) {
							slDates = "";
						}
						slDates = slDates + leaveDates;
						employeeOnePageLeaveHistoryVO.setSldates(slDates);
						float clnoleaves = employeeOnePageLeaveHistoryVO
								.getSlnoleaves();
						clnoleaves = entity.getCcl();
						employeeOnePageLeaveHistoryVO.setSlnoleaves(clnoleaves);
					}
					if (entity.getLwp() > 0) {
						String lwpDays = entity.getLwpDays();
						String lwpdates = employeeOnePageLeaveHistoryVO
								.getLwpdates();
						if (lwpdates == null) {
							lwpdates = "";
						}
						lwpdates = lwpdates + lwpDays;
						employeeOnePageLeaveHistoryVO.setLwpdates(lwpdates);
						float lwpleaves = employeeOnePageLeaveHistoryVO
								.getLwpleaves();
						lwpleaves = lwpleaves + entity.getLwp();
						employeeOnePageLeaveHistoryVO.setLwpleaves(lwpleaves);
					}
					 if (entity.getCel() > 0) {
						String eleaveDates = entity.getElDays();
						String elDates = employeeOnePageLeaveHistoryVO
								.getEldates();
						if (elDates == null) {
							elDates = "";
						}
						elDates = elDates + eleaveDates;
						employeeOnePageLeaveHistoryVO.setEldates(elDates);
						float elnoleaves = employeeOnePageLeaveHistoryVO
								.getElnoleaves();
						elnoleaves = elnoleaves + entity.getCel();
						employeeOnePageLeaveHistoryVO.setElnoleaves(elnoleaves);
					}
				} else if (entity.getLeaveType().equalsIgnoreCase(
						BASApplicationConstants.COMPENSATORY_LEAVE)) {
					String leaveDates = entity.getLeaveDays();
					if (leaveDates != null && leaveDates.length() > 0) {
						String coDates = employeeOnePageLeaveHistoryVO
								.getCodates();
						if (coDates == null) {
							coDates = "";
						}
						coDates = coDates + leaveDates;
						employeeOnePageLeaveHistoryVO.setCodates(coDates);
						float conoleaves = employeeOnePageLeaveHistoryVO
								.getConoleaves();
						conoleaves = conoleaves + entity.getTotalDays()
								- entity.getLwp();
						employeeOnePageLeaveHistoryVO.setConoleaves(conoleaves);
						String coinaccountDates = employeeOnePageLeaveHistoryVO
								.getCoinaccount();
						if (coinaccountDates == null) {
							coinaccountDates = "";
						}
						//2016-01-13
						String inAccountTokens[]=entity.getInAccount().split("-");
						String cinAccountDate=inAccountTokens[2]+"/"+inAccountTokens[1]+",";
						coinaccountDates = coinaccountDates + cinAccountDate;
						employeeOnePageLeaveHistoryVO.setCoinaccount(coinaccountDates);
					}
					if (entity.getLwp() > 0) {
						String lwpDays = entity.getLwpDays();
						String lwpdates = employeeOnePageLeaveHistoryVO
								.getLwpdates();
						if (lwpdates == null) {
							lwpdates = "";
						}
						lwpdates = lwpdates + lwpDays;
						employeeOnePageLeaveHistoryVO.setLwpdates(lwpdates);
						float lwpleaves = employeeOnePageLeaveHistoryVO
								.getLwpleaves();
						lwpleaves = lwpleaves + entity.getLwp();
						employeeOnePageLeaveHistoryVO.setLwpleaves(lwpleaves);
					}
				} else if (entity.getLeaveType().equalsIgnoreCase(
						BASApplicationConstants.STUDY_LEAVE)) {
					String leaveDates = entity.getLeaveDays();
					if (leaveDates != null && leaveDates.length() > 0) {
						String studyDates = employeeOnePageLeaveHistoryVO
								.getStudydates();
						if (studyDates == null) {
							studyDates = "";
						}
						studyDates = studyDates + leaveDates;
						employeeOnePageLeaveHistoryVO.setStudydates(studyDates);
						float studynoleaves = employeeOnePageLeaveHistoryVO
								.getStudyoleaves();
						studynoleaves = studynoleaves + entity.getTotalDays()
								- entity.getLwp();
						employeeOnePageLeaveHistoryVO
								.setStudyoleaves(studynoleaves);
					}
					if (entity.getLwp() > 0) {
						String lwpDays = entity.getLwpDays();
						String lwpdates = employeeOnePageLeaveHistoryVO
								.getLwpdates();
						if (lwpdates == null) {
							lwpdates = "";
						}
						lwpdates = lwpdates + lwpDays;
						employeeOnePageLeaveHistoryVO.setLwpdates(lwpdates);
						float lwpleaves = employeeOnePageLeaveHistoryVO
								.getLwpleaves();
						lwpleaves = lwpleaves + entity.getLwp();
						employeeOnePageLeaveHistoryVO.setLwpleaves(lwpleaves);
					}
				} else if (entity.getLeaveType().equalsIgnoreCase(
						BASApplicationConstants.OD_LEAVE)) {
					String leaveDates = entity.getLeaveDays();
					if (leaveDates != null && leaveDates.length() > 0) {
						String odDates = employeeOnePageLeaveHistoryVO
								.getOddates();
						if (odDates == null) {
							odDates = "";
						}
						odDates = odDates + leaveDates;
						employeeOnePageLeaveHistoryVO.setOddates(odDates);
						float odnoleaves = employeeOnePageLeaveHistoryVO
								.getOdnoleaves();
						odnoleaves = odnoleaves + entity.getTotalDays()
								- entity.getLwp();
						employeeOnePageLeaveHistoryVO.setOdnoleaves(odnoleaves);
					}
					if (entity.getLwp() > 0) {
						String lwpDays = entity.getLwpDays();
						String lwpdates = employeeOnePageLeaveHistoryVO
								.getLwpdates();
						if (lwpdates == null) {
							lwpdates = "";
						}
						lwpdates = lwpdates + lwpDays;
						employeeOnePageLeaveHistoryVO.setLwpdates(lwpdates);
						float lwpleaves = employeeOnePageLeaveHistoryVO
								.getLwpleaves();
						lwpleaves = lwpleaves + entity.getLwp();
						employeeOnePageLeaveHistoryVO.setLwpleaves(lwpleaves);
					}
				} else if (entity.getLeaveType().equalsIgnoreCase(
						BASApplicationConstants.SV_WV_LEAVE)) {
					String leaveDates = entity.getLeaveDays();
					if (leaveDates != null && leaveDates.length() > 0) {
						String svwvDates = employeeOnePageLeaveHistoryVO
								.getSvwvdates();
						if (svwvDates == null) {
							svwvDates = "";
						}
						svwvDates = svwvDates + leaveDates;
						employeeOnePageLeaveHistoryVO.setSvwvdates(svwvDates);
						float svwvnoleaves = employeeOnePageLeaveHistoryVO
								.getSvwvnoleaves();
						svwvnoleaves = svwvnoleaves + entity.getTotalDays()
								- entity.getLwp();
						employeeOnePageLeaveHistoryVO
								.setSvwvnoleaves(svwvnoleaves);
					}
					if (entity.getLwp() > 0) {
						String lwpDays = entity.getLwpDays();
						String lwpdates = employeeOnePageLeaveHistoryVO
								.getLwpdates();
						if (lwpdates == null) {
							lwpdates = "";
						}
						lwpdates = lwpdates + lwpDays;
						employeeOnePageLeaveHistoryVO.setLwpdates(lwpdates);
						float lwpleaves = employeeOnePageLeaveHistoryVO
								.getLwpleaves();
						lwpleaves = lwpleaves + entity.getLwp();
						employeeOnePageLeaveHistoryVO.setLwpleaves(lwpleaves);
					}
				}
				else if (entity.getLeaveType().equalsIgnoreCase(
						BASApplicationConstants.LWP_LEAVE)) {
					if (entity.getLwp() > 0) {
						String lwpDays = entity.getLwpDays();
						String lwpdates = employeeOnePageLeaveHistoryVO
								.getLwpdates();
						if (lwpdates == null) {
							lwpdates = "";
						}
						lwpdates = lwpdates + lwpDays;
						employeeOnePageLeaveHistoryVO.setLwpdates(lwpdates);
						float lwpleaves = employeeOnePageLeaveHistoryVO
								.getLwpleaves();
						lwpleaves = lwpleaves + entity.getLwp();
						employeeOnePageLeaveHistoryVO.setLwpleaves(lwpleaves);
					}
				}
			}// end Of for loop
			}
			//
			if((x+1)%3==0){
				elinaccount=elinaccount+2.0F;
			}
			
			//calculating no of days for OD leaves
			float totalNoOds=0;
			//04(1),05(0.75),
			String oddates=employeeOnePageLeaveHistoryVO.getOddates();
			if(oddates!=null && oddates.trim().length()>0) {
					String totalDaysTokens[]=oddates.split(",");
					for(String str:totalDaysTokens){
							float cp=Float.parseFloat(str.substring(str.indexOf("(")+1,str.indexOf(")")));
							totalNoOds=totalNoOds+cp;
					}
					employeeOnePageLeaveHistoryVO.setOddates(oddates+"  /Total->"+totalNoOds);
			}
			
			elinaccount=elinaccount-employeeOnePageLeaveHistoryVO.getElnoleaves();
			employeeOnePageLeaveHistoryVO.setElinaccount(elinaccount);
			
			clinaccount=clinaccount-employeeOnePageLeaveHistoryVO.getClnoleaves();
			employeeOnePageLeaveHistoryVO.setClinaccount(clinaccount);
			clinaccount=clinaccount+1.75F;
			employeeOnePageLeaveHistoryList.add(employeeOnePageLeaveHistoryVO);
		}
		return employeeOnePageLeaveHistoryList;

	}
}

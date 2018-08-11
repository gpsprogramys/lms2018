package com.bas.hr.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bas.hr.dao.HrLeaveApprovalDao;
import com.bas.hr.entity.LeavesModifiedEntity;
import com.bas.hr.service.HrLeaveApprovalService;
import com.bas.hr.web.controller.model.LeavesModifiedForm;

/**
 * 
 * @author nagendra
 *
 */
@Service("HrLeaveApprovalServiceImpl")
@Scope("singleton")
@Transactional(propagation=Propagation.REQUIRED)
public class HrLeaveApprovalServiceImpl implements HrLeaveApprovalService{
	
	@Autowired
	@Qualifier("HrLeaveApprovalDaoImpl")
	private HrLeaveApprovalDao hrLeaveApprovalDao;
	
	@Override
	public String approveLeaveByManagerRequestId(String leaveRequestId, String leaveApproveReason){
		return hrLeaveApprovalDao.approveLeaveByManagerRequestId(leaveRequestId, leaveApproveReason);
	}
	
	@Override
	public String approveLeaveByManagementRequestId(String leaveRequestId, String leaveApproveReason){
	return hrLeaveApprovalDao.approveLeaveByManagementRequestId(leaveRequestId, leaveApproveReason);
	}	

	@Override
	public String rejectLeaveByLeaveRequestId(String leaveRequestId, String leaveRejectReason,String role) {
		return hrLeaveApprovalDao.rejectLeaveByLeaveRequestId(leaveRequestId, leaveRejectReason,role);
	}

	@Override
	public String approveLeaveByLeaveRequestId(String leaveRequestId, String leaveApproveReason,
			String changeLeaveType,float cl,float el,float lwp) {
		return hrLeaveApprovalDao.approveLeaveByLeaveRequestIdTemp(leaveRequestId, leaveApproveReason,changeLeaveType,lwp,cl,el);
	}

	@Override
	public String approveLeaveWithLeaveRequestIdByHRManagement(String leaveRequestId, String leaveApproveReason,
String spChangeLeaveType, String changeLeaveType, float cl, float el, float plwp, String approveAuthority) {
		return hrLeaveApprovalDao.approveLeaveWithLeaveRequestIdByHRManagement(leaveRequestId, leaveApproveReason, spChangeLeaveType,changeLeaveType, cl, el, plwp, approveAuthority);
	}

	@Override
	public LeavesModifiedForm findModifiedLeaveByRequestId(String requestId) {
		LeavesModifiedEntity leavesModifiedEntity=hrLeaveApprovalDao.findModifiedLeaveByRequestId(requestId);
		LeavesModifiedForm leavesModifiedForm=new LeavesModifiedForm();
		BeanUtils.copyProperties(leavesModifiedEntity, leavesModifiedForm);
		return leavesModifiedForm;
	}

	@Override
	public String approveLeaveByLeaveRequestIdTemp(String leaveRequestId, String leaveApproveReason,
			String changeLeaveType, float mpcl, float mpel, float mplwp) {
		return hrLeaveApprovalDao.approveLeaveByLeaveRequestIdTemp(leaveRequestId, leaveApproveReason, changeLeaveType, mpcl, mpel, mplwp);
	}

}

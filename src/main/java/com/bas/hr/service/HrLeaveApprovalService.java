package com.bas.hr.service;

import com.bas.hr.web.controller.model.LeavesModifiedForm;

/**
 * 
 * @author xxx
 *
 */
public interface HrLeaveApprovalService {
	public String rejectLeaveByLeaveRequestId(String leaveRequestId,String leaveRejectReason,String role);
	public String approveLeaveByLeaveRequestId(String leaveRequestId, String leaveApproveReason,
			String changeLeaveType,float cl,float el,float lwp) ;
	public String approveLeaveByManagerRequestId(String leaveRequestId, String leaveApproveReason);
	public String approveLeaveByManagementRequestId(String leaveRequestId, String leaveApproveReason);
	
	public String approveLeaveWithLeaveRequestIdByHRManagement(String leaveRequestId, String leaveApproveReason,
			String spchangeLeaveType,String changeLeaveType,final float cl,final float el,final float plwp,String approveAthority);
	
	public LeavesModifiedForm findModifiedLeaveByRequestId(String requestId);
	
	public String approveLeaveByLeaveRequestIdTemp(String leaveRequestId, String leaveApproveReason, String changeLeaveType,
			float mpcl, float mpel, float mplwp);
	
}

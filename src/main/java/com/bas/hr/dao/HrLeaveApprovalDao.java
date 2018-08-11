package com.bas.hr.dao;

import com.bas.hr.entity.LeavesModifiedEntity;

/**
 * 
 * @author nagendra.yadav
 *
 */
public interface HrLeaveApprovalDao {
	public String rejectLeaveByLeaveRequestId(String leaveRequestId,String leaveRejectReason,String role);
	public String approveLeaveByLeaveRequestId(String leaveRequestId, String leaveApproveReason,
			String changeLeaveType,float cl,float el,float lwp);
	public String approveLeaveByManagerRequestId(String leaveRequestId, String leaveApproveReason);
	public String approveLeaveByManagementRequestId(String leaveRequestId, String leaveApproveReason);
	public String approveLeaveWithLeaveRequestIdByHRManagement(String leaveRequestId, String leaveApproveReason,
			String spChangeLeaveType,String changeLeaveType,final float cl,final float el,final float plwp,String approveAuthority);
	public LeavesModifiedEntity findModifiedLeaveByRequestId(String requestId);
	public String approveLeaveByLeaveRequestIdTemp(String leaveRequestId, String leaveApproveReason, String changeLeaveType,
			float mpcl, float mpel, float mplwp);
}

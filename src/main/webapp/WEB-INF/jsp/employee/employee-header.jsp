 <!-- Header -->
  <header id="header" class="header">
    <div class="header-top bg-theme-colored border-top-theme-colored2-2px sm-text-center">
      <div class="container">
        <div class="row">          
          <div class="col-md-6">
            <div class="widget">
              <ul class="styled-icons icon-sm icon-white">
                <li><a href="#"><i class="fa fa-facebook"></i></a></li>
                <li><a href="#"><i class="fa fa-twitter"></i></a></li>
                <li><a href="#"><i class="fa fa-google-plus"></i></a></li>
                <li><a href="#"><i class="fa fa-instagram"></i></a></li>
                <li><a href="#"><i class="fa fa-linkedin"></i></a></li>
              </ul>
            </div>
          </div>
          <div class="col-md-6">
            <div id="side-panel-trigger" class="side-panel-trigger ml-15 mt-8 pull-right sm-pull-none"><a href="#"><i class="fa fa-bars font-24"></i></a>
            </div>
            <div class="widget">
              <ul class="list-inline text-right flip sm-text-center">
                <li>
                  <a class="text-white" href="#">FAQ</a>
                </li>
                <li class="text-white">|</li>
                <li>
                  <a class="text-white" href="#">Help Desk</a>
                </li>
                <li class="text-white">|</li>
                <li>
                  <a class="text-white" href="#">Support</a>
                </li>
              </ul>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="header-nav">
      <div class="header-nav-wrapper navbar-scrolltofixed bg-white">
        <div class="container">
          <nav id="menuzord-right" class="menuzord default theme-colored"><a class="menuzord-brand pull-left flip mt-20 mt-sm-10 mb-sm-20 pt-5" href="index-mp-layout1.html"><img src="${pageContext.request.contextPath}/images/logo-wide.png" alt=""></a>
            <ul class="menuzord-menu">
              <li class="active"><a href="${pageContext.request.contextPath}/employee/employee-home">Home</a>
                 
                  <li><a href="#">Leave Management</a>
                     <ul class="dropdown">
                       <li><a href="${pageContext.request.contextPath}/employee/leaveBalance">Leave Balance </a></li>
							<li><a
								href="${pageContext.request.contextPath}/employee/employeeLeaveApplication">
									Leave Application</a></li>
							<li>
							 <li><a href="${pageContext.request.contextPath}/employee/employeeLeaveStatus">Leave Request Status</a></li>
							<li class="divider"></li>
							<li><a
								href="${pageContext.request.contextPath}/employee/employeeOnePageLeaveHistory">One Page Leave History</a></li>
							
							<li><a
								href="${pageContext.request.contextPath}/employee/leaveHistory">Leave
									History</a></li>
							
							<c:if test="${sessionScope.user_session_data.reportingManager=='yes'}">
								<li class="divider"></li>
								
								<li>
								
								<a href="${pageContext.request.contextPath}/employee/managerApproveLeave">Approve
										Leave Manager</a></li>
							</c:if>
							</ul>
                      </li>
               
               
                  <li><a href="#">Attendance Status</a>
                  <ul class="dropdown">
                  <li><a href="${pageContext.request.contextPath}/employee/employeeAttendance">Monthly Attendance Detail</a></li>
								<li class="divider"></li>
							<li><a href="${pageContext.request.contextPath}/employee/employeeMonthlyAttendanceStatus">Monthly Attendance Summary</a></li>
							<li><a href="${pageContext.request.contextPath}/employee/employeeMonthlyAttStatusWithChart">Monthly Att Status Chart</a></li>
								<li><a href="${pageContext.request.contextPath}/employee/faculityAttReport">Attendance Report in PDF</a></li>
								<li><a href="${pageContext.request.contextPath}/employee/employeeManualAttendance">Manual Attendance</a></li>
								    
                        </ul>           
                  </li>
                  
                  
                    <li><a href="#">Menu</a>
                <ul class="dropdown">
               <li><a href="${pageContext.request.contextPath}/employee/employeeLeaveStatus">Leave Application Status</a></li>
							<li><a href="${pageContext.request.contextPath}/employee/leaveBalance">Employee Leave Balance</a></li>
						<li><a	href="${pageContext.request.contextPath}/employee/employeeOnePageLeaveHistory">One Page Leave History</a></li>
							<li><a href="${pageContext.request.contextPath}/employee/employeeLeaveApplication">Leave Application Form</a></li>
							<li><a href="${pageContext.request.contextPath}/employee/leaveHistory">Employee Leave History</a></li>
							<li><a href="${pageContext.request.contextPath}/employee/employeeAttendance">Employee Attendance Status</a></li>
                 	<li><a href="${pageContext.request.contextPath}/employee/employeeMonthlyAttendanceStatus"s>Employee Monthly Register</a></li>
                 		<li><a href="${pageContext.request.contextPath}/employee/viewHolidayCalenderEmployee">Holiday Calendar</a></li>
                 </ul>
                  </li>
                  
                  
                  
              <li><a href="#">Setting</a>
                <ul class="dropdown">
               <li><a href="${pageContext.request.contextPath}/common/changePassword">Change Password</a></li>
							<li><a href="${pageContext.request.contextPath}/employee/profile">Profile</a></li>
						<li><a	href="${pageContext.request.contextPath}/employee/viewHolidayCalenderEmployee">Calendar</a></li>
							<li><a href="#">Leave Reminder</a></li>
							<li class="divider"></li>
							<li><a href="#">Attendance Reminder</a></li>
							<li><a href="#">Attendance Summary</a></li>
                 </ul>
                  </li>
          
          
      <li><a href="${pageContext.request.contextPath}/common/logout">Logout</a></li>
          </ul>
          </nav>
        </div>
      </div>
    </div>
  </header>

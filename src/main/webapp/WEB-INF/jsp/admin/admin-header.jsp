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
          <nav id="menuzord-right" class="menuzord default theme-colored"><a class="menuzord-brand pull-left flip mt-20 mt-sm-10 mb-sm-20 pt-5" href="index-mp-layout1.html"><img src="images/logo-wide.png" alt=""></a>
            <ul class="menuzord-menu">
              <li class="active"><a href="${pageContext.request.contextPath}/admin/adminHome">Home</a>
                 
                 
                  <li><a href="#">Attendance</a>
                     <ul class="dropdown">
                          <li><a href="${pageContext.request.contextPath}/admin/adminAttendance">Today's Attendance</a></li>
                            <li><a href="${pageContext.request.contextPath}/admin/manualAttendance">Manual Attendance</a></li>
                        
                                 <li><a href="${pageContext.request.contextPath}/admin/viewOneEmployeeAttendance">View One Employee Attendance</a></li>
                                   <li><a href="${pageContext.request.contextPath}/admin/showDeptMonthlyAttendance">Show Department Wise Monthly Attendance</a></li>
                                    <li><a href="${pageContext.request.contextPath}/admin/adminUpdateAttendance">Update Attendance</a></li>
                                        <li><a href="${pageContext.request.contextPath}/admin/adminDeleteAttendance">Delete Attendance</a></li>
                                        <li><a href="${pageContext.request.contextPath}/admin/empManualRequestAtt">Employee Manual Request Attendance</a></li>
                                   
                            </ul>
                      </li>
               
               
                  <li><a href="#">LMS</a>
                  <ul class="dropdown">
                     <li><a href="${pageContext.request.contextPath}/admin/employeeOnLeave">Employee On Leave</a></li>
                        		<li><a href="${pageContext.request.contextPath}/admin/showLeaveBalance">Show Leave Balance</a></li>
                        		<li><a href="${pageContext.request.contextPath}/admin/employeeByDepartment">Show Admin Leave Balance</a></li>
                        		<li><a href="${pageContext.request.contextPath}/admin/leaveApplication">Leave Application</a></li> 
                        		<li><a href="${pageContext.request.contextPath}/admin/hrApproveLeave">Pending Leave Approval</a></li> 
                        		 <li><a href="${pageContext.request.contextPath}/admin/findEmployeeOnePageLeaveHistory">One Page Leave History</a></li>
                        		      <li><a href="${pageContext.request.contextPath}/admin/showAllFacultyWithleaveHistory">Employee Leave History</a></li>
                        		 <li><a href="${pageContext.request.contextPath}/admin/employeeByDepartment">Employee By Department</a></li>
                        		  <li><a href="${pageContext.request.contextPath}/admin/adminMarkLwp">Mark Employee LWP</a></li>
                        		  <li><a href="${pageContext.request.contextPath}/admin/empWorkingDays">Working Days</a></li>
                        	</ul>
                  </li>
                  
                  
                  
              <li><a href="#">Action</a>
                <ul class="dropdown">
                
                     <li><a href="${pageContext.request.contextPath}/admin/addHolidayCalendar">Add Holiday Calendar</a></li>
                                <li><a href="${pageContext.request.contextPath}/admin/addDepartment">Add Department</a></li>
                                <li><a href="${pageContext.request.contextPath}/admin/designations">Add Designation</a></li>
                                <li><a href="${pageContext.request.contextPath}/admin/addLeaveReason">Add Leave Reason</a></li>
                                <li><a href="${pageContext.request.contextPath}/admin/leaveTypes">Add Leave Type</a></li>
                                <li><a href="${pageContext.request.contextPath}/admin/organizationTime">Organization Time</a></li>
                                <li><a href="${pageContext.request.contextPath}/admin/viewHolidayCalender">View Holiday Calendar</a></li>
                                 <li><a href="${pageContext.request.contextPath}/admin/showAllFaculty">Show Employees</a></li>
                                 <li><a href="${pageContext.request.contextPath}/admin/addEmployees">Add Employees</a></li>
                                 <li><a href="${pageContext.request.contextPath}/admin/showAllFaculty">Employee Status</a></li>
                                  <li><a href="${pageContext.request.contextPath}/admin/reporteeManagement">Reportee Management</a></li>
                            </ul>
                
                </li>
                
                  <li><a href="#">Message Board</a>
                   <ul class="dropdown">
                                <li><a href="${pageContext.request.contextPath}/admin/uploadMessageBoard">Post Message</a></li>
                                   <li class="divider"></li>	
                                 <li><a href="${pageContext.request.contextPath}/admin/messageBoardInbox">Message Inbox</a></li>
                            </ul>
                  </li>                  
                  
                <li><a href="${pageContext.request.contextPath}/admin/changePassword">Change Password</a></li>
                       
                          <li><a href="${pageContext.request.contextPath}/common/logout">Logout</a></li>  
                  
              </ul>    
                  
          </nav>
        </div>
      </div>
    </div>
  </header>

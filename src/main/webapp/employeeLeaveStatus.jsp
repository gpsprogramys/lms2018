<%@ taglib uri="http://www.springframework.org/tags/form" prefix="ff"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html><%@ taglib
	uri="http://www.springframework.org/tags/form" prefix="ff"%>
<%@ page isELIgnored="false"%>

<html dir="ltr" lang="en">

<!-- Mirrored from thememascot.net/demo/personal/j/imfundo/v2.0/demo/events-table.html by HTTrack Website Copier/3.x [XR&CO'2014], Sat, 04 Aug 2018 11:06:51 GMT -->
<head>

<!-- Meta Tags -->
<meta name="viewport" content="width=device-width,initial-scale=1.0"/>
<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
<meta name="description" content="Imfundo - Education University School Kindergarten Learning HTML Template" />
<meta name="keywords" content="education,school,university,educational,learn,learning,teaching,workshop" />
<meta name="author" content="ThemeMascot" />

<!-- Page Title -->
<title>Education & Courses HTML5 Template - Imfundo</title>

<%@include file="css.jsp" %>
 <%@include file="top.jsp" %>

</head>
<body class="">
<div id="wrapper" class="clearfix">
  <!-- preloader -->
  <div id="preloader">
    <div id="spinner">
      <img alt="" src="images/preloaders/5.gif">
    </div>
    <div id="disable-preloader" class="btn btn-default btn-sm">Disable Preloader</div>
  </div>
  
 <%@include file="employee-header.jsp" %>

    <!-- Section: Schedule -->
     <ff:form action="${pageContext.request.contextPath}/employee/adminApproveLeave"
					method="post" commandName="facultyAttendStatusVO">
    <section id="schedule" class="divider parallax layer-overlay overlay-white-8" data-bg-img="images/bg/bg1.jpg">
      <div class="container pt-100 pb-80">
        <div class="section-content">
          <div class="row">
            <div class="col-md-12">
              <table class="table table-striped table-schedule">
                <thead>
                <tr class="bg-theme-colored">
                    <th>No</th>
                    <th>Request ID</th>
                    <th>Entry Date</th>
                    <th>Leave Type</th>
                     <th>Leave Form</th>
                    <th>Leave To</th>
                    <th>Leave Taken</th>
                    <th>Reason</th>
                    <th>Management</th>
                    <th>Manager</th>
                    <th>HR</th>
                  </tr>
                </thead>
                <tbody>
                
                
                 <tbody>
                        <c:forEach var= "empLeaveData" items = "${empLeaveDataList}" varStatus="status">
                            <tr bgcolor="#FFFFFF" style="color: black;" id="rowId${status.count}">
                            	<td id ="no" style="padding: 4px;">${status.count}</td>	
                            	<td id ="requestID" style="padding: 4px;">${empLeaveData.requestID}</td>
                            	<%-- <td style="padding: 4px;">${empLeaveData.empid} - ${empLeaveData.empName}</td> --%>
                            	<td style="padding: 4px;"><fmt:formatDate pattern="dd-MMM-yyyy"
												value="${empLeaveData.doapplication}"/></td>
												 	<td>${empLeaveData.leaveType}</td>
                            	<td style="padding: 4px;"><fmt:formatDate pattern="dd-MMM-yyyy"
												value="${empLeaveData.leaveFrom}"/></td>
								<td style="padding: 4px;"><fmt:formatDate pattern="dd-MMM-yyyy"
												value="${empLeaveData.leaveTo}"/></td>				
                            	<td style="padding: 4px;">${empLeaveData.totalDays}</td>
                            	<td style="padding: 4px;">${empLeaveData.purpose}</td>
                            	
                            	 <c:if test="${empLeaveData.manegementApproval=='PENDING'}">
                            		<td style="padding: 4px;text-align: center;"><img src="${pageContext.request.contextPath}/img/notapproved.png"/></td>
                            	</c:if>
                            	 <c:if test="${empLeaveData.manegementApproval=='APPROVED'}">
                            			<td style="padding: 4px;text-align: center;"><img src="${pageContext.request.contextPath}/img/approved.gif"/></td>
                            	</c:if>
                            	
                            	 <c:if test="${empLeaveData.managerApproval=='PENDING'}">
                            		<td style="padding: 4px;text-align: center;"><img src="${pageContext.request.contextPath}/img/notapproved.png"/></td>
                            	</c:if>
                            	 <c:if test="${empLeaveData.managerApproval=='APPROVED'}">
                            			<td style="padding: 4px;text-align: center;"><img src="${pageContext.request.contextPath}/img/approved.gif"/></td>
                            	</c:if>
                            	<c:if test="${empLeaveData.hrApproval=='PENDING'}">
                            		<td style="padding: 4px;text-align: center;"><img src="${pageContext.request.contextPath}/img/notapproved.png"/></td>
                            	</c:if>
                            	
                            	<c:if test="${empLeaveData.hrApproval=='APPROVED'}">
                            			<td style="padding: 4px;text-align: center;">
                            			<img src="${pageContext.request.contextPath}/img/approved.gif"/></td>
                            	</c:if>
									</tr>
                            </c:forEach>
                
                
                
                
                
                
                
                 <tr>
                            	<td>&nbsp;</td>
                            	<td>&nbsp;</td>
                            	<td>&nbsp;</td>
                            	<td>&nbsp;</td>
                            	<td>&nbsp;</td>
                            	<td>&nbsp;</td>
                            	<td>&nbsp;</td>
                            	<td>&nbsp;</td>
                            	<td>&nbsp;</td>
                            	<td>&nbsp;</td>
                            	<td>&nbsp;</td>
                            </tr>
                              <tr>
                            	<td>&nbsp;</td>
                            	<td>&nbsp;</td>
                            	<td>&nbsp;</td>
                            	<td>&nbsp;</td>
                            	<td>&nbsp;</td>
                            	<td>&nbsp;</td>
                            	<td>&nbsp;</td>
                            	<td>&nbsp;</td>
                            	<td>&nbsp;</td>
                            	<td>&nbsp;</td>
                            	<td>&nbsp;</td>
                            </tr>
                              <tr>
                            	<td>&nbsp;</td>
                            	<td>&nbsp;</td>
                            	<td>&nbsp;</td>
                            	<td>&nbsp;</td>
                            	<td>&nbsp;</td>
                            	<td>&nbsp;</td>
                            	<td>&nbsp;</td>
                            	<td>&nbsp;</td>
                            	<td>&nbsp;</td>
                            	<td>&nbsp;</td>
                            	<td>&nbsp;</td>
                            </tr>
                              <tr>
                            	<td>&nbsp;</td>
                            	<td>&nbsp;</td>
                            	<td>&nbsp;</td>
                            	<td>&nbsp;</td>
                            	<td>&nbsp;</td>
                            	<td>&nbsp;</td>
                            	<td>&nbsp;</td>
                            	<td>&nbsp;</td>
                            	<td>&nbsp;</td>
                            	<td>&nbsp;</td>
                            	<td>&nbsp;</td>
                            </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
    
    
    </section>
  </div>
  </ff:form>
  
  
  
  
  <!-- end main-content -->
  <%@include file="footer.jsp" %>
   <%@include file="common-js.jsp" %>

</body>


</html>
<!-- HR LEAVE APPLICATION FORM - For All Employees -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="ff"%>
<!DOCTYPE html>
<html dir="ltr" lang="en">
<head>

<!-- Meta Tags -->
<meta name="viewport" content="width=device-width,initial-scale=1.0"/>
<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
<meta name="description" content="Imfundo - Education University School Kindergarten Learning HTML Template" />
<meta name="keywords" content="education,school,university,educational,learn,learning,teaching,workshop" />
<meta name="author" content="ThemeMascot" />




<%@include file="css.jsp" %>


<script type="text/javascript">
 contextPath="${pageContext.request.contextPath}";
</script>
<script src="${pageContext.request.contextPath}/js/jquery1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<script src="${pageContext.request.contextPath}/js/fsearch.js"></script>


<script type="text/javascript"
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>

<link
	href="http://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/css/datepicker.css"
	rel="stylesheet" type="text/css" />
<script
	src="http://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/js/bootstrap-datepicker.js"></script>

<script
	src="${pageContext.request.contextPath}/js/vendor/modernizr-2.6.2-respond-1.1.0.min.js"></script>
<script
	src="http://cdnjs.cloudflare.com/ajax/libs/moment.js/2.9.0/moment-with-locales.js"></script>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/prelodr.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/datePickerStyle.css"
	type="text/css" media="all" />

<link href='https://fonts.googleapis.com/css?family=Poiret+One'
	rel='stylesheet' type='text/css' />



<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-confirm.js"></script>

<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.watermarkinput.js"></script>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/applyLeaveStyle.css">

<!-- Page Title -->
<title>Education & Courses HTML5 Template</title>


 <%@include file="top.jsp" %>
 
<style type="text/css">
	.not-active {
  		 pointer-events: none;
  		 cursor: default;
	}
	
	</style>
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

<%@include file="admin-header.jsp" %>
<section style="margin-left: 70px; margin-top: 50px; padding: 0px;">
<div class="container-fluid">
			<br /> <span
				style="color: blue; font-weight: bold; margin-left: -22px;"
				id="applicationMessageId">${applicationMessage}</span> <span
				style="color: red; font-weight: bold; margin-left: -22px;"
				id="applicationMessageId">${messageStatus.warning}</span>
			<div class="row">
				<div class="col-md-8">
					<ff:form
						action="${pageContext.request.contextPath}/admin/employeeLeaveApplication"
						id="leaveForm" commandName="employeeLeaveRequestForm">
						<input type="hidden" name="empName" id="empName" value="${employeeLeaveDetailVO.name}"/>
						<input type="hidden" name="employeeId" id="employeeId" value="${employeeLeaveDetailVO.eid}"/>
						<input type="hidden" name="managerId" id="managerId" value="${employeeLeaveDetailVO.managerId}"/>
						<input type="hidden" name="hrManagerId" id="hrManagerId" value="${employeeLeaveDetailVO.hrid}"/>
						<input type="hidden" name="reportingManager" id="reportingManager" value="${employeeLeaveDetailVO.managerName}"/>
						<input type="hidden" name="department" id="department" value="${employeeLeaveDetailVO.department}"/>
						<input type="hidden" name="slDate" id="slDate"  value="${employeeLeaveDetailVO.slDate}"/>
						<table style="font-size: 13px; margin-left: 7px;">
							<tr>
								<td style="color: black;" align="left"><font
									style="color: black">Available Leaves:</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<font style="color: black">EL: </font><span id="eel"
									style="font-weight: bold;">${employeeLeaveDetailVO.el}</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<font style="color: black">CL: </font> <span id="ecl"
									style="font-weight: bold;">${employeeLeaveDetailVO.cl}</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<font style="color: black">SL: </font> <span id="esl"
									style="font-weight: bold;">${employeeLeaveDetailVO.sl} </span>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<font style="color: black">OD: </font><span id="eod"
									style="font-weight: bold;">${employeeLeaveDetailVO.od}</span>
									
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<font style="color: black">Current Date: </font><span id="cdate"
									style="font-weight: bold;"></span>
									
									</td>
								
								
							</tr>
						</table>


	<table border="0"
							style="width: 100%; margin-top: 10px; font-size: 13px;"
							class="table">
							<tr height="25px">
								<td colspan="1" style="width: 25%; align: left;"><b>Leave
										From</b><font color="red">*</font> <input type="text"
									id="datepicker1" name="leaveFrom"> <br /> <span
									id="datepicker1Error" style="color: red;"></span> </td>

								<td colspan="1" style="width: 25%; align: left;"><b>Leave
										To</b><font color="red">*</font><span style="padding-left: 60px"></span>
									<input type="text" id="datepicker2" name="leaveTo"> <br />
									<span id="datepicker2Error" style="color: red;"></span> </td>

								<td colspan="1" style="width: 25%; align: left;"><b>Total
										day(sa) &nbsp;:</b> <input type="number" id="totalDays"
									name="totalDays" /></td>


								<td style="color: black; margin-left: 80px; width: 30%;"
									align="left" rowspan="3"><font style="color: black">
										Employee Id&nbsp;&nbsp;: &nbsp;&nbsp; </font> <span id="eempid">${employeeLeaveDetailVO.eid}</span>
									<br> <font style="color: black"> 
									<img id="eimage"
										src="${pageContext.request.contextPath}/employee/renderImage?empid=${sessionScope.user_session_data.eid}" class="img-zoom"
										style="border-radius: 50%; width: 60px; height: 60px; border: 0" />
										<br />Name&nbsp;&nbsp;: &nbsp;&nbsp;
								</font> <span id="ename">${employeeLeaveDetailVO.name}</span> <br>
									<font style="color: black"> DOJ&nbsp;&nbsp;:
										&nbsp;&nbsp; </font> <span id="edoj">${employeeLeaveDetailVO.doj}</span>
									<br> <font style="color: black">
										Designation&nbsp;&nbsp;: &nbsp;&nbsp; </font> <span id="edesignation">${employeeLeaveDetailVO.designation}</span>
									<br> <font style="color: black">
										Department&nbsp;&nbsp;: &nbsp;&nbsp; </font> <span id="edepartment">${employeeLeaveDetailVO.department}&nbsp;</span>
									<br> <font style="color: black"> Type&nbsp;&nbsp;:
										&nbsp;&nbsp; </font> <span id="etype">${employeeLeaveDetailVO.type}&nbsp;</span>
	

								</td>
							<tr height="25px">
								<td colspan="1" style="align: left;"><b>Leave Type </b> <font
									color="red">*</font> <!--<ff:select path="leaveType"
										onchange="javascript:checkLeaveType();"
										style="background-color: #FFFFFF" class="empcustom">
										<ff:options items="${leaveTypeList}" />
									</ff:select>-->
									<div id="otherDescriptionId" style="margin-top: -6px;">
										<textarea id="otherDescription" name="otherLeaveDescription"
											style="background-color: white; width: 188px;"
											placeholder="Description" size="65"></textarea>
									</div>

									<div id="inAccId" style="margin-top: -25px;">
										<br /> <b>In A/C <font color="red">*</font>
										</b>&nbsp; <input type="text" id="datepicker3" name="inAccount"
											style="background-color: white; width: 123px;"
											placeholder="Specify date" size="65" />
									</div> <script type="text/javascript">
											$("#inAccId").hide();
											$("#otherDescriptionId").hide();
									</script> 
									<span id="datepicker3Error" style="color: red;"></span></td>

								<td colspan="1" style="width: 10%; align: center;"><b>Purpose</b><font
									color="red">*</font> </br> <ff:select path="purpose"
										style="background-color: white;width: 300px;"
										onchange="javascript:checkPurpose();">
										<ff:options items="${reasonTypeList}" />
									</ff:select> <span style="padding-left: 60px;"></span> 
									<textarea type="text" id="reason" name="reason"
										style="background-color: white; width: 265px; margin-top: -27px;"
										placeholder="Please specify reason."></textarea> <script
										type="text/javascript">
								  $('#reason').hide();
							</script></td>

								<td class="groupwrap container" colspan="1"
									style="width: 10%; align: left;"><b>Leave Category </b> <font
									color="red">*</font> <label style="font-size: 13px"
									class="empcustom"> <input type="radio" value="fullDay"
										name="leaveCategory" checked="checked" id="ffcheck" /> Full
										Day
								</label> <label class="empcustom" style="font-size: 13px"> <input
										type="radio" value="halfDay" name="leaveCategory" id="halfday" />
										Half Day&nbsp;&nbsp;&nbsp; <select id="lvmeeting"
										style="width: 170px; margin-top: -10px; margin-left: -10px;">
											<option>First Meeting</option>
											<option selected="selected">Second Meeting</option>

									</select>
								</label></td>
							</tr>
							<tr height="25px">
								<td colspan="1" style="width: 10%; align: right;"><b>Address:</b>
									</br> <textarea
										placeholder="e.g. 45 Park Street, APT 7J, San Francisco, CA"
										id="" name="address" style="background-color: white;"
										placeholder="(type here)" rows="4">${employeeLeaveDetailVO.paddress}</textarea></td>

								<td colspan="2">
									<table>
										<tr>
											<td><b>Mobile:</b> </br> <input type="text" name="mobile"
												placeholder="(xxx)-xxx-xxxx" id="mobile"
												style="height: 15px; width: 180px;" value="${employeeLeaveDetailVO.mobile}"/></td>
											<td style="width: 180px;"><b>Reporting Manager
													&nbsp; : </b> <input type="text" name="reportingManager"
												placeholder="XXXX" id="reportingManager"
												style="height: 15px; background-color: rgba(243, 234, 32, 0.06);"
												readonly="readonly"  value="${employeeLeaveDetailVO.managerName}"/> <input type="hidden"
												name="reportingManagerId" placeholder="XXXX"
												id="re
												portingManagerId" style="height: 15px" /></td>
											<td align="left" style="margin-left: -20px;display: none"><b>Sandwitch
											</b> <font color="red">*</font> <label style="font-size: 13px"
												class="empcustom"> <input type="radio" value="No"
													name="sandwitch" checked="checked" id="sandwitchNo" /> No
											</label> <label class="empcustom" style="font-size: 13px"> <input
													type="radio" value="Yes" name="sandwitch" id="sandwitchYes" />
													Yes
											</label></td>
										</tr>

										<tr>
											<td colspan="2" id="leaveDatesStatus"></td>
										</tr>

									</table>
								</td>
							</tr>
							
							<tr>
								<td colspan="3" style="width: 10%; align: right;" id="sbm">
									Alternative arrangements :
									<select style="background-color: #FFFFFF;margin-top: 9px;" id="alternativeArrange" name="alternativeArrange">
										<option value="NO">No</option>
										<option value="YES">Yes</option>
									</select>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<a   id="AlternativeArrangements" href="javascript:" > <img src="${pageContext.request.contextPath}/img/add-arrangement.png" style="height: 28px;" /></a>&nbsp;
									<a   id="AlternativeArrangements1" href="javascript:" class="not-active" > <img src="${pageContext.request.contextPath}/img/add-arrangement.png" style="height: 28px;" /></a>&nbsp;
									<div>
										<table style="width: 100%;" class="table table-bordered" id="AlertnativeArrangementTable">
										<thead>
												 <tr>	
												<th>Date</th>
												<th>Branch</th>
												<th>Period</th>
												<th>Subject</th>
												<th>Name</th>
												<th>Action</th>
												</tr>
												</thead>				
										</table>
									</div>
								</td>
							</tr>
							<tr>
								<td colspan="3" style="width: 10%;text-align: right;" id="sbm">
									<input type="button" value="Apply Leave" class="btn btn-primary"
									id="submitbtn" />
								</td>
							</tr>
						</table>
						<br/>
					</ff:form>
				</div>
			</div>
		</div>
	</section>
	

<!-- Footer -->
 <%@include file="footer.jsp" %>
 <script src="${pageContext.request.contextPath}/js/please-wait-loader.js"></script>
   <%@include file="common-js.jsp" %>
<!-- End of footer -->



	<!--
	*********************************************************************************** 
	******************************** SCRIPTS ******************************************
	***********************************************************************************
	***********************************************************************************
	-->


<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/jquery.easy-confirm-dialog.js">
	</script>


<script type="text/javascript">
	function leaveCategoryCheck() {
		if (document.getElementById('lc1').checked) {
			$("#lvmeeting").show();
		} else {
			$("#lvmeeting").hide();
			$('input[name=leaveMeeting]:checked').val("NA");
		}
	}
	</script>

<script type="text/javascript">
	function checkType() {
		$("#description").hide();
		if (document.getElementById("leaveType1").value == "Others") {
			$("#description").show();
		} else {
			$("#description").hide();
		}
	}
	</script>
	
	<script>
	
	 function applySandWich(){

 		///////////////////////////////////////////////////////
 		var pempid=$("input[type='hidden'][id='employeeId']").val();
 		if(document.getElementById("datepicker1").value != '' && document.getElementById("datepicker2").value != '')
 		{
			var sandv=$("input[type='radio'][name='sandwitch']:checked").val();
			$.getJSON("${pageContext.request.contextPath}/employee/calculateNoOfDays", {
				  leaveFrom: document.getElementById("datepicker1").value,
			         leaveTo: document.getElementById("datepicker2").value,
			         empid:pempid,sandwitch:sandv
			     },
				 function(data,status) {
	    	 		 if(data.message!="no") {
	    	 			 $("#applicationMessageId").html(data.message);
	    	 			 disableButton();
	    	 		 }else{
	    	 			$("#applicationMessageId").html("");
	    	 			enableButton();
	    	 		 }
		    	 /* 	if( document.getElementById("datepicker1").value == document.getElementById("datepicker2").value ){
		    	 		//?? WHY EMPTY ??
		    	 	} */
		    	 	 if(data.totalDays < 0){
						$('#totalDays').val(0);
						$("#leaveDatesStatus").html("");
					}
					else
					{
						$('#totalDays').val(data.totalDays);	
						$("#leaveDatesStatus").html(data.leaveDates);
						if(data.lwp=="yes"){
							$("select#leaveType option[value='CL']").remove(); 
							$("select#leaveType option[value='SL']").remove(); 
							$("select#leaveType option[value='EL']").remove(); 
							$("select#leaveType option[value='OD']").remove(); 
							$("select#leaveType option[value='OD']").remove(); 
							$("select#leaveType option[value='Others']").remove(); 
							$("select#leaveType option[value='Compensatory Leave']").remove(); 
						   
						}else{
							
							 var olen=$("#leaveType option").length;
							 if(olen==1){
									 $("#leaveType").append('<option value="CL">CL</option>');
									 $("#leaveType").append('<option value="SL">SL</option>');
									 $("#leaveType").append('<option value="EL">EL</option>');
									 $("#leaveType").append('<option value="LWP">LWP</option>');
									 $("#leaveType").append('<option value="OD">OD</option>');
									 $("#leaveType").append('<option value="Compensatory Leave">Compensatory Leave</option>');
									 $("#leaveType").append('<option value="Others">Others</option>');
									  $('select[name="leaveType"]').val('CL');
							 }
						}
				}});
          }
 		else{
 			//??
 		}		
		 
	 }
	 
	 
	 function enableButton(){
		 $('input[type="button"][id="submitbtn"]').removeAttr('disabled');
	 }
	 
	 function disableButton(){
		 $('input[type="button"][id="submitbtn"]').attr('disabled','disabled');
	 }
	function checkLeaveType(){
		if(document.getElementById("leaveType").value == 'SL'){
		 var esl=$("#esl").html();
		 if(esl==0){
			 $("#applicationMessageId").html($("input[type='hidden'][id='slDate']").val());
			 $("input[type='number'][id='totalDays']").val(.5);
			 disableButton();
			 return;
		 }
		} 
		enableButton();
		if(document.getElementById("leaveType").value == 'EL'){
			$("input[id='sandwitchYes'][value='Yes']").prop("checked",true);
			applySandWich();  
		}else{
			$("input[id='sandwitchNo'][value='No']").prop("checked",true);
		}
		if(document.getElementById("leaveType").value == 'Compensatory Leave'){
			$("#inAccId").show();
			$("#otherDescriptionId").hide();
		}else if(document.getElementById("leaveType").value == 'Others' || document.getElementById("leaveType").value == 'LWP'){
			$("#otherDescriptionId").show();
			$("#inAccId").hide();
		}
		else{
			$("#otherDescriptionId").hide();
			$("#inAccId").hide();
		}
		if(document.getElementById("leaveType").value == 'SL'){
			$("input[id='halfday'][value='halfDay']").prop("checked",true);
			//$('#lvmeeting option[value=Second Meeting]').prop('selected', 'selected');
			$("input[id='ffcheck'][value='fullDay']").prop("disabled",true);
			$("#lvmeeting").show();
	    	$("input[type='number'][id='totalDays']").val(.5);
		}else{
			$("input[id='ffcheck'][value='fullDay']").prop("disabled",false);
			$("input[id='ffcheck'][value='fullDay']").prop("checked",true);
			//$("#ffcheck").attr('checked',true);
			$("#lvmeeting").hide();
			var totalDays=$("input[type='number'][id='totalDays']").val();
	    	//if(totalDays==.5){
	    		$("input[type='number'][id='totalDays']").val(1);
	    		applySandWich();  
	    		//////////////////////////////////////////////////////
	    		//}else{
		}
	}
	
	function checkPurpose(){
		/* alert(document.getElementById("leaveType")); */
		if(document.getElementById("purpose").value == 'Others'){
			$("#reason").show();
		}
		else{
			$("#reason").hide();
		}
	}
	
	
	/*
	  This method will be invoked to validate leave form data
	*/
	function validatePreSubmission(){
		 var datepicker1=$("#datepicker1").val();
		 if(datepicker1.trim().length==0) {
			// alert('Please select "leave from" for leave applied');
			 $("#datepicker1Error").html(" *Leave From cannot be empty.");
			 return;
		 }
		 
		 var datepicker2=$("#datepicker2").val();
		 if(datepicker2.trim().length==0) {
			 $("#datepicker2Error").html(" *Leave To cannot be empty.");
			 return;
		 }
		 
		 
		 //validating the date when Compensatory Leave is selected
		 //if(document.getElementById("leaveType").value == 'Compensatory Leave'){
		 var leaveType=$("#leaveType").val();	 
		 if('Compensatory Leave'==leaveType) {
		 	var datepicker3=$("#datepicker3").val();
		 	if(datepicker3.trim().length==0) {
				 $("#datepicker3Error").html(" In A/C  date cannot be empty.");
				 $("#datepicker3").focus();
				 return;
			 }
		 }	
		 
		 var leaveType=$("#leaveType").val();	 
		 if('SL'==leaveType) {
			 var totalDays=$("input[type='number'][id='totalDays']").val();
			 if(totalDays!=.5){
				 $("#applicationMessageId").html("For SL total number of day cannot be more than .5, select proper date.");
				 disableButton();
				 return;
			 }
			 
			 if(datepicker1!=datepicker2){
				 $("#applicationMessageId").html("From date & To date should be same to apply SL type leave.");
				 disableButton();
				 return;
			 }
		 }	
		 
		 var appmsg=$("#applicationMessageId").html();
		 if(appmsg.trim().length==0){
			 /////////////////////////	
			 $("#leaveForm").submit();
		 }
	}
	</script>
	
	<script type="text/javascript">
		$(function() {
			disableButton();
			$("#datepicker1").datepicker({
		         dateFormat: 'dd-mm-yy',
		         showOn: 'button',
		         buttonImage: "${pageContext.request.contextPath}/images/datePickerPopup.gif",
		         buttonImageOnly: true,
		         showOtherMonths : true,
		         selectOtherMonths : true,
		         changeYear:true,
		         yearRange: "c-50:c+0",
		         showWeek: false
		    }).next('button').text('').button({
		       text: true
		    });
			$('#datepicker1').change(function (){
				$("input[id='ffcheck'][value='fullDay']").prop("checked",true);
				applySandWich();	
			});
	});	
	</script>
	
<script	type="text/javascript">
   var currentServerDate="";
  $(function() {
		$.ajax({
		    type: 'GET',
		    url: '${pageContext.request.contextPath}/common/serverDate',
		    data: {'watch':'aukcia', 'id':100},
		    complete: function(data){
		    	//JSON.stringify(data); 
		         currentServerDate=data.responseText;
		    	 $("#cdate").html(currentServerDate);
		    }
		 });
		
		$("#datepicker2").datepicker({
	         dateFormat: 'yy-mm-dd',
	         showOn: 'button',
	         buttonImage: "${pageContext.request.contextPath}/images/datePickerPopup.gif",
	         buttonImageOnly: true,
	         showOtherMonths : true,
	         selectOtherMonths : true,
	         changeYear:true,
	         yearRange: "c-50:c+0",
	         showWeek: false
	    }).next('button').text('').button({
	       text: true
	    });
		
		$("#doa").datepicker({
	         dateFormat: 'yy-mm-dd',
	         showOn: 'button',
	         buttonImage: "${pageContext.request.contextPath}/images/datePickerPopup.gif",
	         buttonImageOnly: true,
	         showOtherMonths : true,
	         selectOtherMonths : true,
	         changeYear:true,
	         yearRange: "c-50:c+0",
	         showWeek: false
	    }).next('button').text('').button({
	       text: true
	    });
		
		$('#datepicker2').change(function (){
			$("input[id='ffcheck'][value='fullDay']").prop("checked",true);
			applySandWich();
			enableButton();
			});
		});	
	</script>
	
	<script type="text/javascript">
		$(function() {
			$("#datepicker3").datepicker({
		         dateFormat: 'yy-mm-dd',
		         showOn: 'button',
		         buttonImage: "${pageContext.request.contextPath}/images/datePickerPopup.gif",
		         buttonImageOnly: true,
		         showOtherMonths : true,
		         selectOtherMonths : true,
		         changeYear:true,
		         yearRange: "c-50:c+0",
		         showWeek: false
		    }).next('button').text('').button({
		       text: true
		    });
			$('#datepicker3').change(function (){
				 $("#datepicker3Error").html("");
				 $("input[id='ffcheck'][value='fullDay']").prop("checked",true);
			});
			
		});
	</script> 

	<!-- Making the Search Box Work -->
	<script type="text/javascript">
$(document).ready(function(){
	
	$("#AlertnativeArrangementTable").hide();
	$("#AlternativeArrangements").hide();
	$("#alternativeArrange").change(function(){
			var value=$("#alternativeArrange").val();
			if(value=='YES'){
				$("#AlternativeArrangements").show();
				$("#AlternativeArrangements1").hide();
			}
			if(value=='NO'){
				$("#AlternativeArrangements1").show();
				$("#AlternativeArrangements").hide();
			}
		});
	
	
/* 	$("#addAlertnativeArrBt").click(function(){
        $("#AlternativeArrangementsModel").modal('hide');
    });
	$(".icon-remove").click(function(){
        $("#AlternativeArrangementsModel").modal('hide');
    });
	
	$("#AlternativeArrangements").click(function(){
        $("#AlternativeArrangementsModel").modal('show');
    });
	 */
	$("#submitbtn").easyconfirm({locale: { title: 'Confirm?', button: ['No','Yes']}});
	  $("#submitbtn").click(function() {
		  validatePreSubmission();
	}); 
	
	$('.pimg-zoom').hover(function() {
        $(this).addClass('transition');
    }, function() {
    	//alert("img- remove zoom............");
        $(this).removeClass('transition');
    });
	$('.img-zoom').hover(function() {
        $(this).addClass('transition');
    }, function() {
    	//alert("img- remove zoom............");
        $(this).removeClass('transition');
    });
	$("#lvmeeting").hide();
	$("input[type='radio'][name='leaveCategory']").change(function () {
	    if ($(this).val() == "fullDay") {
	    	var leaveType=$("#leaveType").val();	 
			 if('SL'!=leaveType) {
				 var totalDays=$("input[type='number'][id='totalDays']").val();
				 if(totalDays.indexOf('.')!=-1){ 
					 //totalDays=totalDays+.5;
					 totalDays = (parseFloat(totalDays)+parseFloat(.5)).toFixed(1);
				 }
				 $("input[type='number'][id='totalDays']").val(totalDays);
			 }
	    	$("#lvmeeting").hide();
	    	
	    } else {
	    	$("#lvmeeting").show();
	    	var totalDays=$("input[type='number'][id='totalDays']").val();
	    	if(totalDays==.5){
	    	}	
	    	else if(totalDays.indexOf('.')==-1){ 
	    		totalDays=totalDays-.5;
	    	}else if(totalDays.indexOf('.')!=-1 && totalDays>.5){ 
	    		totalDays=totalDays-.5;
	    	}
	    	$("input[type='number'][id='totalDays']").val(totalDays);
	    }
	});
	
	jQuery.fn.extend({
	    live: function (event, callback) {
	       if (this.selector) {            
	            jQuery(document).on(event, this.selector, callback);
	        }
	    }
	});
	
	 $('#datepicker1').change(function(){
		 $('#datepicker1Error').html("");
	 });
	 
	 $('#datepicker2').change(function(){
		 $('#datepicker2Error').html("");
	 });
	 
		$("#icon-remove").click(function(){
				 $("#mocha").hide();
				 $(".modal-backdrop.fade.in").remove();
		});
		
		$("#addAlertnativeArrBt").click(function() {
				alert("_@@)@@");	
				var doa=$("#doa").val();
				var doaTokens=doa.split("/");
				doa=doaTokens[1]+"-"+doaTokens[0]+"-"+doaTokens[2];
				var branchSemSec=$("#branchSemSec").val();
				var period=$("#period").val();
				var facultyNamesList=$("#facultyNamesList").val();
				var facultyNamesListText=$("#facultyNamesList option:selected").text();
				var subjectCode=$("#subjectCodes").val();
				var subjectCodeText=$("#subjectCodes option:selected").text();
				var drows='';
				$("#AlertnativeArrangementTable").show();
				drows=drows+'<tr>';
				drows=drows+'<td><input type="hidden" name="date" value="'+doa+'" />'+doa+'</td>';
				drows=drows+'<td><input type="hidden" name="branch" value="'+branchSemSec+'" />'+branchSemSec+'</td>';
				drows=drows+'<td><input type="hidden" name="period" value="'+period+'" />'+period+'</td>';
				drows=drows+'<td><input type="hidden" name="subject" value="'+subjectCode+'" />'+subjectCodeText+'</td>';
				drows=drows+'<td><input type="hidden" name="name" value="'+facultyNamesList+'" />'+facultyNamesListText+'</td>';
				drows=drows+' <td><a href="javascript:" id="deleteRows"><img src="${pageContext.request.contextPath}/images/delete.png"/></a></td>';
				drows=drows+'</tr>';						
				//alert(drows);
				$("#AlertnativeArrangementTable").append(drows);
				 $("#mocha").hide();
				 $(".modal-backdrop.fade.in").remove();
		});
		
		$("#facultyNamesList").change(function() {
			var branchSemSec=$("#branchSemSec").val();
			
			var doa=$("#doa").val();
			var period=$("#period").val();
			var empid=$(this).val();
			$.getJSON("${pageContext.request.contextPath}/employee/findSubjectsByFacultyBranchPeriod",{currentDate:doa,period:period,branchSemSec:branchSemSec,empid:empid}, function(data,status) {
						$("#subjectCodes").empty();
						 for(var q=0;q<data.length;q++) {
			 			      $("#subjectCodes").append($('<option>', { value :data[q].subCode })
			 			      .text(data[q].subjectShortName )); 
						  }
			});
		});
		
		$("#period").change(function() {
			var branchSemSec=$("#branchSemSec").val();
			var doa=$("#doa").val();
			var period=$(this).val();
			$.getJSON("${pageContext.request.contextPath}/employee/findFacultyByBranchPeriod",{currentDate:doa,period:period,branchSemSec:branchSemSec}, function(data,status) {
				$("#facultyNamesList").empty();
				  if(data.length==0){
					  $("#subjectCodes").empty();
				  }
				  for(var p=0;p<data.length;p++) {
							 value=data[p].name+" ("+data[p].facultyId+")"
			 				 $('#facultyNamesList').append($('<option>', { value :data[p].facultyId }).text(value)); 
							  if(p==0) {
								  $("#subjectCodes").empty();
								  var sdata=data[0].subjectsCode;
								  for(var q=0;q<sdata.length;q++) {
					 			      $("#subjectCodes").append($('<option>', { value :sdata[q].subCode })
					 			      .text(sdata[q].subjectShortName )); 
								  }
							  }
				 }
			});
			
	  });	

	  $("#AlternativeArrangements").click(function(){
			$.getJSON("${pageContext.request.contextPath}/employee/findBranchSemSectionByEmpId", function(data,status) {
				$("#doa").val($("#datepicker1").val());
				$("#branchSemSec").empty();
				 for(var p=0;p<data.length;p++){
		 				 $('#branchSemSec')
		 			      .append($('<option>', { value : data[p] })
		 			      .text(data[p])); 
				 }
				$("#mocha").show();
				//$("#mocha").modal('hide');
				$(".modal-backdrop.in").remove();
				$('body').append('<div class="modal-backdrop fade in" style="height: 777px;"></div>');
			});
			
	 });
	 //$('.modal ').insertAfter($('body'));
	// $("#mocha").modal('hide');
	 $("#mocha").hide();
	 
	 $("a[id$='deleteRows'").click(function(){
			alert("@_@))@@@)");	 
	 });
	 
});	 

function deleteRow(cthis){
		
	$(cthis).hide();
}

</script>
<div class="modal" id="mocha" aria-hidden="false" style="margin-top: 70px;">
	<div class="modal-header">
        <a href="javascript:" id="icon-remove"><i class="icon-remove"  aria-hidden="true" ></i></a>
       <table style="width: 100%;" class="table table-bordered">
												 <tr style="background-color: #FDF100;color:black;">	
												<th colspan="2">Alertnative Arrangement <img src="${pageContext.request.contextPath}/images/add-user.png" style="height: 32px;"/></th>
												</tr>
												   <tr style="height: 20px;">
										            <td>Date Of Arrange</td>
										           <td><input type="text" name="doa" style="width: 150px;margin-top: 10px;" id="doa"/>(MM/dd/yyyy)</td>
										        </tr>
										        
										            <tr style="height: 20px;">
										             <td style="height: 20px;">&nbsp;Branch</td>
										           <td>
										           <select name="branchSemSec" id="branchSemSec">
										           	<option>XXXXXXXXXX</option>
										           </select></td>
										        </tr>	
										        		
										          <tr style="height: 20px;">
										             <td style="height: 20px;">&nbsp;Period</td>
										           <td>
										           <select id="period" name="period">
										           	<option>1</option>
										           	<option>2</option>
										           	<option>3</option>
										           	<option>4</option>
										           	<option>5</option>
										           	<option>6</option>
										           	<option>7</option>
										           	<option>8</option>
										           </select></td>
										        </tr>	
										        
										          <tr style="height: 20px;">
										             <td style="height: 20px;">&nbsp;Name</td>
										           <td>
										           <select style="width: 320px;" id="facultyNamesList" name="facultyName">
										           	<option>XXXXXXX</option>
										           </select></td>
										        </tr>	
										        
										            
										    
										        
										        
										        <tr style="height: 20px;">
										             <td style="height: 20px;">&nbsp;Subject</td>
										           <td>
										           <select name="subjectCode" id="subjectCodes">
										           	<option>XXXXXXXXX</option>
										           </select></td>
										        </tr>	
										        
										           <tr>
										            <td>&nbsp;</td>
										           <td style="text-align: right;">	
										           <input type="button" value="Add Arrangement" class="btn btn-primary"   id="addAlertnativeArrBt" /></td>
										        </tr>						
										</table>
    </div>
</div>

</div>
</body>

</html>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="ff"%>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html dir="ltr" lang="en">

<!-- Mirrored from thememascot.net/demo/personal/j/imfundo/v2.0/demo/form-appointment-style2.html by HTTrack Website Copier/3.x [XR&CO'2014], Sat, 04 Aug 2018 11:06:25 GMT -->
<head>

<!-- Meta Tags -->
<meta name="viewport" content="width=device-width,initial-scale=1.0"/>
<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
<meta name="description" content="Imfundo - Education University School Kindergarten Learning HTML Template" />
<meta name="keywords" content="education,school,university,educational,learn,learning,teaching,workshop" />
<meta name="author" content="ThemeMascot" />

<!-- Page Title -->
<title>Education & Courses HTML5 Template - Imfundo</title>
 <%@ include file="css.jsp" %>
   <%@ include file="top.jsp" %>
   
     <style>
      .error {
   color: red;
   font-size: 12px;
}
      </style>
      
 <script>
   $(document).ready(function(){
	   
	   $('#springForm').validate({ // initialize the plugin
	        rules: {
	        	"email": {
	                required: true,
	                minlength:2,
	            },
	            password: {
	                required: true,
	                
	            }
	        }
	    });
   });
   </script>
   
    <%@ include file="employee-header.jsp" %>

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

  <!-- Start main-content -->
  <div class="main-content"> 
    <!-- Section: home -->
    <section id="home" class="divider parallax layer-overlay overlay-dark-9" data-bg-img="${pageContext.request.contextPath}/images/bg/bg4.jpg">
     	<ff:form action="${pageContext.request.contextPath}/common/changePassword"
					method="post"   id="springForm">
     
     		${ApplicationMessage}
     		
     		<tr  style="color: #000000; font-weight: bold;">
								<td colspan="2"><span style="color: red;font-weight: bold;font-size: 14px;">${error}</span></td>
							</tr>
     
      <div class="display-table">
        <div class="display-table-cell">
          <div class="container pb-100">
            <div class="row" style="height: 550px">
              <div class="col-md-6 col-md-push-3">
                <div class="text-center mb-60"><a href="#" class=""><img alt="" src="${pageContext.request.contextPath}/images/logo-wide-white.png"></a></div>
                <div class="bg-lightest border-1px p-25">
                 
                    <form name="editprofile-form" method="post">
                <div class="icon-box mb-0 p-0">
                  <a href="#" class="icon icon-bordered icon-rounded icon-sm pull-left mb-0 mr-10">
                    <i class="fa fa-key"></i>
                  </a>
                  <h4 class="text-gray pt-10 mt-0 mb-30">Change Password</h4>
                </div>
                <hr>

                <div class="row">
                  <div class="form-group col-md-12">
                    <label>Current Password <span style="color: red;font-weight: bold;font-size: 16px;">*</span></label>
                    <input name="currentPassword" id="currentPassword" style="height: 45px;width: 500px;" class="error" type="password" value="${sessionScope.user_session_data.password}" readonly="readonly">
                  </div>
                  
                  
                  <div class="form-group col-md-12">
                    <label>New Password<span style="color: red;font-weight: bold;font-size: 16px;">*</span></label>
                    <input name="newpassword" id="newPassword"  style="height: 45px;width: 500px;" class="error" type=password">
                  </div>
                </div>
                
                <div class="row">
                  <div class="form-group col-md-12">
                    <label>Confirm Password<span style="color: red;font-weight: bold;font-size: 16px;">*</span></label>
                    <input name="confirmpassword"  id="confirmPassword" style="height: 45px;width: 500px;" class="error" type=password">
                  </div>
                </div>
               
                <div class="form-group">
                  <input type="submit" id="addDesignation" class="btn btn-dark btn-lg mt-15" ></input>
                </div>
              </form>
                
                
                </div>
              </div>
 
            </div>
          </div>
        </div>
      </div>
 </ff:form>
    </section>
 
  </div>
  <!-- end main-content -->
  <!-- Footer -->
  <footer id="footer" class="footer bg-black-111">
    <div class="container p-10">
      <div class="row">
        <div class="col-md-12 text-center">
          <p class="mb-0">Copyright &copy;2018 <span class="text-theme-colored2">ThemeMascot</span>. All Rights Reserved</p>
        </div>
      </div>
    </div>
  </footer>
  <a class="scrollToTop" href="#"><i class="fa fa-angle-up"></i></a>
</div>
<!-- end wrapper -->

<!-- Footer Scripts -->
<!-- JS | Custom script for all pages -->
<script src="js/custom.js"></script>

</body>

</html>
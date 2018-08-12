<%@ taglib uri="http://www.springframework.org/tags/form" prefix="ff"%>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html dir="ltr" lang="en">

<head>

<!-- Meta Tags -->
<meta name="viewport" content="width=device-width,initial-scale=1.0"/>
<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
<meta name="description" content="Imfundo - Education University School Kindergarten Learning HTML Template" />
<meta name="keywords" content="education,school,university,educational,learn,learning,teaching,workshop" />
<meta name="author" content="ThemeMascot" />

<!-- Page Title -->
<title>Login Page</title>
<%@include file="css.jsp" %>
<%@include file="top.jsp" %>

<style>
    .error {
   color: red;
   font-size: 12px;
}
</style>
 
 <script>
	var ccontextPath="${pageContext.request.contextPath}";
   $(document).ready(function(){
	   
		$("input[type='password'][id='password']").keydown(function (e) {
			  if (e.keyCode == 13) {
				  var pusername=$("input[type='text'][id='username']").val();
					 var ppassword=$("input[type='password'][id='password']").val();
					 	$.ajax({url:ccontextPath+"/common/validateUserByAjax",data:{username:pusername,password:ppassword},success:function(data) {
						    if(data=='invalid') {
								// alert(payeeAccount+ " is not valid account number.");
								 $("#errorMessage").html("Sorry!  user id and password are not valid!.");
								 return;
						    }else{
						    	 $("#springForm").submit();
						    	 return;
						    }
					  }
					});   
			 }
});
	 //making ajax call
	 
	 $('#login').click(function(){
		 var pusername=$("input[type='text'][id='username']").val();
		 var ppassword=$("input[type='password'][id='password']").val();
		 	$.ajax({url:ccontextPath+"/common/validateUserByAjax",data:{username:pusername,password:ppassword},success:function(data) {
			    if(data=='invalid') {
					// alert(payeeAccount+ " is not valid account number.");
					 $("#errorMessage").html("Sorry!  user id and password are not valid!.");
					 return;
			    }else{
			    	 $("#springForm").submit();
			    	 return;
			    }
		  }
		});   
	 });
	   
	   $('#springForm').validate({ // initialize the plugin
	        rules: {
	        	username: {
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
      <div class="display-table">
        <div class="display-table-cell">
          <div class="container pb-100">
            <div class="row" style="height: 550px">
              <div class="col-md-6 col-md-push-3">
                <div class="text-center mb-60"><a href="#" class=""><img alt="" src="${pageContext.request.contextPath}/images/logo-wide-white.png"></a></div>
                <div class="bg-lightest border-1px p-25">
                  <h4 class="text-theme-colored text-uppercase m-2"><center>Sign Up Form</center></h4>
                 	<ff:form action="${pageContext.request.contextPath}/common/auth" method="post"   id="springForm">
                  <br/>
                    <div class="row">
                      <div class="col-sm-12">
                        <div class="form-group mb-10">
                          <input name="username"  id="username" class="form-control" type="text" placeholder="Your USER ID" aria-required="true">
                        </div>
                      </div>
                      <div class="col-sm-12">
                        <div class="form-group mb-10">
                          <input name="password" id="password"class="form-control required" type="password" placeholder="Your Password" aria-required="true">
                        </div>
                      </div>
                      </div>
                     
                    <div class="form-group mb-0 mt-20">
                      <input name="form_botcheck" class="form-control" type="hidden" value="">
                      <button type="button" id="login"  class="btn btn-dark btn-theme-colored btn-lg"  data-loading-text="Please wait...">
                      Log IN</button>
                    </div>
                    <br/>
                    	 <a href="${pageContext.request.contextPath}/common/resetPassword">Forgot your password?</a>
                    
                    
                    </ff:form>
                 
               </div>
              </div>
 
            </div>
          </div>
        </div>
      </div>
 
    </section>
 </div>
   <!-- end main-content -->
  
  
  
  
  
  <!-- Footer -->
  <footer id="footer" class="footer bg-black-111">
    <div class="container p-9">
      <div class="row">
        <div class="col-md-12 text-center">
          <p class="mb-0">Copyright &copy;2018 <span class="text-theme-colored2">ThemeMascot</span>. All Rights Reserved</p>
        </div>
      </div>
    </div>
  </footer>
  <a class="scrollToTop" href="#"><i class="fa fa-angle-up"></i></a>
</div>
</body>

<script src="${pageContext.request.contextPath}/js/custom.js"></script>

<!-- Mirrored from thememascot.net/demo/personal/j/imfundo/v2.0/demo/form-appointment-style2.html by HTTrack Website Copier/3.x [XR&CO'2014], Sat, 04 Aug 2018 11:06:25 GMT -->
</html>
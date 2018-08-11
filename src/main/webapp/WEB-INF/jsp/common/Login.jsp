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

<!-- Page Title -->
<title>Education & Courses HTML5 Template - Imfundo</title>

<!-- Favicon and Touch Icons -->
<link href="${pageContext.request.contextPath}/images/favicon.png" rel="shortcut icon" type="image/png">
<link href="${pageContext.request.contextPath}/images/apple-touch-icon.png" rel="apple-touch-icon">
<link href="${pageContext.request.contextPath}/images/apple-touch-icon-72x72.png" rel="apple-touch-icon" sizes="72x72">
<link href="${pageContext.request.contextPath}/images/apple-touch-icon-114x114.png" rel="apple-touch-icon" sizes="114x114">
<link href="${pageContext.request.contextPath}/images/apple-touch-icon-144x144.png" rel="apple-touch-icon" sizes="144x144">

<!-- Stylesheet -->
<link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/css/jquery-ui.min.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/css/animate.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/css/css-plugin-collections.css" rel="stylesheet"/>
<!-- CSS | menuzord megamenu skins -->
<link href="${pageContext.request.contextPath}/css/menuzord-megamenu.css" rel="stylesheet"/>
<link id="menuzord-menu-skins" href="${pageContext.request.contextPath}/css/menuzord-skins/menuzord-boxed.css" rel="stylesheet"/>
<!-- CSS | Main style file -->
<link href="${pageContext.request.contextPath}/css/style-main.css" rel="stylesheet" type="text/css">
<!-- CSS | Preloader Styles -->
<link href="${pageContext.request.contextPath}/css/preloader.css" rel="stylesheet" type="text/css">
<!-- CSS | Custom Margin Padding Collection -->
<link href="${pageContext.request.contextPath}/css/custom-bootstrap-margin-padding.css" rel="stylesheet" type="text/css">
<!-- CSS | Responsive media queries -->
<link href="${pageContext.request.contextPath}/css/responsive.css" rel="stylesheet" type="text/css">
<!-- CSS | Style css. This is the file where you can place your own custom css code. Just uncomment it and use it. -->
<!-- <link href="css/style.css" rel="stylesheet" type="text/css"> -->

<!-- CSS | Theme Color -->
<link href="${pageContext.request.contextPath}/css/colors/theme-skin-color-set1.css" rel="stylesheet" type="text/css">

<!-- external javascripts -->
<script src="${pageContext.request.contextPath}/js/jquery-2.2.4.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery-ui.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<!-- JS | jquery plugin collection for this theme -->
<script src="${pageContext.request.contextPath}/js/jquery-plugin-collection.js"></script>

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
<![endif]-->
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
                  <h4 class="text-theme-colored text-uppercase m-0">Sign Up Form</h4>
              	<ff:form action="${pageContext.request.contextPath}/common/auth"
					method="post"   id="springForm">
                    <div class="row">
                      <div class="col-sm-12">
                        <div class="form-group mb-10">
                          <input name="username" id="username" class="form-control" type="text" required="" placeholder="Your USER ID" aria-required="true">
                        </div>
                      </div>
                      <div class="col-sm-12">
                        <div class="form-group mb-10">
                          <input name="password" class="form-control required" type="password" placeholder="Your Password" aria-required="true">
                        </div>
                      </div>
                      </div>
                     
                    <div class="form-group mb-0 mt-20">
                      <input name="form_botcheck" class="form-control" type="hidden" value="">
                      <button type="submit" id="login" class="btn btn-dark btn-theme-colored" data-loading-text="Please wait...">Log IN</button>
                    </div>
                    	 <a href="${pageContext.request.contextPath}/common/resetPassword">Forgot your password?</a>
                    
                 </ff:form>
                  <!-- Appointment Form Validation-->
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
<script src="${pageContext.request.contextPath}/js/custom.js"></script>

</body>
</html>
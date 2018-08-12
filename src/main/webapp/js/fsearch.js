(function($){
$.fn.fsearch = function(){
  var $searchInput = $(this);
  $searchInput.after('<div id="divResult"></div>');
  $resultDiv = $('#divResult');
  $searchInput.focus();
  $searchInput.addClass('searchi');
  $resultDiv.html("<ul></ul><div id='search-footer' class='searchf'></div>");
  $searchInput.keyup(function(e) {
  var q=$(this).val();
    if(q != '')
    { 
      var current_index = $('.selected').index(),
      $options = $resultDiv.find('.option'),
      items_total = $options.length;

      // When Down Arrow key is pressed
      if (e.keyCode == 40) {
          if (current_index + 1 < items_total) {
              current_index++;
              change_selection($options,current_index);
          }
      } 

      // When Up Arrow is pressed
      else if (e.keyCode == 38) {
          if (current_index > 0) {
              current_index--;
              change_selection($options,current_index);
          }
      }

      // When enter key is pressed
      else if(e.keyCode == 13){
        var id = $resultDiv.find('ul li.selected').attr('id');
        var name = $resultDiv.find('ul li.selected').find('.name').text();
        $searchInput.val(name); 
        $resultDiv.fadeOut();// Here you get the id and name of the element selected. You can change this to redirect to any page too. Just like facebook.   
      }
      else{
      $resultDiv.fadeIn();
      $resultDiv.find('#search-footer').html("<img src="+contextPath+"/img/loader.gif"+" alt='Collecting Data...'/>");
      
      // Query search details from database
      $.getJSON(contextPath+"/admin/emplyeeSuggestionOption",{searchword: q},function(jsonResult)  { 
        var str='';
        for(var i=0; i<jsonResult.length;i++)
          {
            str += '<li id=' + jsonResult[i].empid + ' class="option"><img class="profile_image" src='+contextPath+'/admin/renderImage?empid='+jsonResult[i].empid+' alt="'+jsonResult[i].name+'"/><span class="name">'+ jsonResult[i].empid+' - ' + jsonResult[i].name + '&nbsp;&nbsp; - '+jsonResult[i].department+'</span></li>';
          }
          $resultDiv.find('ul').empty().prepend(str);
          $resultDiv.find('div#search-footer').text(jsonResult.length + " results found...");
          $resultDiv.find('ul li').first().addClass('selected');
      }); 

        $resultDiv.find('ul li').live('mouseover',function(e){
        current_index = $resultDiv.find('ul li').index(this);
        $options = $resultDiv.find('.option');
        change_selection($options,current_index);
      });

      // Change selected element style by adding a css class
      function change_selection($options,current_index){
        $options.removeClass('selected');
        $options.eq(current_index).addClass('selected');
        }
      }
    }
    else{
      //Hide the results if there is no search input
      $resultDiv.hide();
    }
  });    

  // Hide the search result when clicked outside
  jQuery(document).live("click", function(e) { 
    var $clicked = $(e.target);
    if ($clicked.hasClass("searchi") || $clicked.hasClass("searchf")){
    }
    else{
      $resultDiv.fadeOut(); 
    }
  });
  
  // Hide the search result when clicked outside
  //This i have added to give support of jQuery Plugin Higher that 1.8
  jQuery(document).on("click", function(e) { 
    var $clicked = $(e.target);
    if ($clicked.hasClass("searchi") || $clicked.hasClass("searchf")){
    }
    else{
      $resultDiv.fadeOut(); 
    }
  });

  // Show previous search results when the search box is clicked
  $searchInput.click(function(){
    var q=$(this).val();
    if(q != '')
    { 
      $resultDiv.fadeIn();
    }
  });

  // Select the element when it is clicked
  $resultDiv.find('li').live("click",function(e){ 
    var eid = $(this).attr('id');
    //alert("eid = "+eid);
    var name = ($(this).find('.name').text());
    $searchInput.val(name);
    ///////////////////////////////
 // Query search details from database
    $.getJSON(contextPath+"/admin/findEmplyeeLeaveDetail",{eid:eid},function(jsonResult)  { 
    	//alert(jsonResult.mobile);
    	$("#eel").html(jsonResult.el);
    	$("#ecl").html(jsonResult.cl);
    	$("#esl").html(jsonResult.sl);
    	$("#eod").html(jsonResult.od);
    	$("#address").html(jsonResult.paddress);
    	$("#mobile").val(jsonResult.mobile);
    	$("#reportingManager").val(jsonResult.managerName);
    	$("#dreportingManager").val(jsonResult.managerName);
    	$("#reportingManagerId").val(jsonResult.managerId);
    	$("#ename").html(jsonResult.name);
    	$("#eimage").attr("src",contextPath+'/admin/renderImage?empid='+eid);
    	$("#edesignation").html(jsonResult.designation);
    	$("#edepartment").html(jsonResult.department);
    	$("#etype").html(jsonResult.type);
    	$("#eempid").html(eid);
    	$("#edoj").html(jsonResult.doj);
    	//setting the values in hidden fields inside the html forms..
    	$("input[type='hidden'][id='empName']").val(jsonResult.name);
    	$("input[type='hidden'][id='employeeId']").val(eid);
    	$("input[type='hidden'][id='managerId']").val(jsonResult.managerId);
    	$("input[type='hidden'][id='reportingManager']").val(jsonResult.managerName);
    	$("input[type='hidden'][id='hrManagerId']").val(jsonResult.hrid);
    	$("input[type='hidden'][id='department']").val(jsonResult.department);
    	//alert("@_@_"+jsonResult.slDate);
    	$("input[type='hidden'][id='slDate']").val(jsonResult.slDate);
    	//clearing the error message.............
    	$("#applicationMessageId").html("");
    	
    }); 
    ///////////////////////////////////
  });
 };
})(jQuery);
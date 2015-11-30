<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%request.setCharacterEncoding("gb2312");
  request.setCharacterEncoding("UTF-8");
  String userid =(String)request.getParameter("userid");
  //session.getAttribute
  session.setAttribute("userid", userid);%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <title>DashBoard</title>

    <!-- Bootstrap core CSS -->
    <link href="assets/css/bootstrap.css" rel="stylesheet">
    <!--external css-->
    <link href="assets/font-awesome/css/font-awesome.css" rel="stylesheet" />
    <link rel="stylesheet" type="text/css" href="assets/css/zabuto_calendar.css">
    <link rel="stylesheet" type="text/css" href="assets/js/gritter/css/jquery.gritter.css" />
    <link rel="stylesheet" type="text/css" href="assets/lineicons/style.css">    
    
    <!-- Custom styles for this template -->
    <link href="assets/css/style.css" rel="stylesheet">
    <link href="assets/css/style-responsive.css" rel="stylesheet">

    <script src="assets/js/chart-master/Chart.js"></script>
    
  </head>

  <body>

  <section id="container" >
      <header class="header black-bg">
              <div class="sidebar-toggle-box">
                  <div class="fa fa-bars tooltips" data-placement="right" data-original-title="Toggle Navigation"></div>
              </div>
            <!--logo start-->
            <a href="index.html" class="logo"><b>Foot</b></a>

            <div class="top-menu">
            	<ul class="nav pull-right top-menu">
                    <li><a class="logout" href="Index.jsp">Logout</a></li>
            	</ul>
            </div>
        </header>
      <!--header end-->
      
      <!-- **********************************************************************************************************************************************************
      MAIN SIDEBAR MENU
      *********************************************************************************************************************************************************** -->
      <!--sidebar start-->
      <aside>
          <div id="sidebar" >
              <!-- sidebar menu start-->
              <ul class="sidebar-menu" id="nav-accordion">
              
              	  <p class="centered"><a href="#.html"><img src="assets/img/ui-sam.jpg" class="img-circle" width="60"></a></p>
              	  <h5 class="centered">User:<%=userid%></h5>
              	  	
                  <li class="sub-menu">
                      <a id="dash" href="javascript:;">
                          <i class="fa fa-dashboard"></i>
                          <span>Dashboard</span>
                      </a>
                  </li>
                  
                  <li class="sub-menu">
                      <a id="foot" href="javascript:;">
                          <i class="fa fa-dashboard"></i>
                          <span>Foot Print</span>
                      </a>
                  </li>
                  
                  <li class="sub-menu">
                      <a id="check" href="javascript:;" >
                          <i class="fa fa-desktop"></i>
                          <span>Weekly Check</span>
                      </a>
                  </li>

                  <li class="sub-menu">
                      <a id="recplace" href="javascript:;" >
                          <i class="fa fa-cogs"></i>
                          <span>Recommend Place</span>
                      </a>
                  </li>
                  
                  <li class="sub-menu">
                      <a id="recfriend" href="javascript:;" >
                          <i class="fa fa-cogs"></i>
                          <span>Recommend Friend</span>
                      </a>
                  </li>
                  
                  <li class="sub-menu">
                      <a id="hotplace" href="javascript:;" >
                          <i class="fa fa-cogs"></i>
                          <span>Hot Place</span>
                      </a>
                  </li>
                  
                  <li class="sub-menu">
                      <a id="intimacy" href="javascript:;" >
                          <i class="fa fa-cogs"></i>
                          <span>Friend Intimacy</span>
                      </a>
                  </li>
                

              </ul>
              <!-- sidebar menu end-->
          </div>
      </aside>
      <!--sidebar end-->
      
      <!-- **********************************************************************************************************************************************************
      MAIN CONTENT
      *********************************************************************************************************************************************************** -->
      <!--main content start-->
      <section id="main-content">
          <section class="wrapper">
          	<div id="test">
              <div class="row">
                  <div class="col-lg-9 main-chart">
                  
                  	<div class="row mtbox">
                  		<div class="col-md-2 col-sm-2 col-md-offset-1 box0">
                  			<div class="box1">
					  			<span class="li_user"></span>
					  			<h3>933</h3>
                  			</div>
					  			<p>933 People liked your page the last 24hs. Whoohoo!</p>
                  		</div>
                  		<div class="col-md-2 col-sm-2 box0">
                  			<div class="box1">
					  			<span class="li_location"></span>
					  			<h3>+48</h3>
                  			</div>
					  			<p>48 New files were added in your cloud storage.</p>
                  		</div>
                  		<div class="col-md-2 col-sm-2 box0">
                  			<div class="box1">
					  			<span class="li_star"></span>
					  			<h3>23</h3>
                  			</div>
					  			<p>You have 23 unread messages in your inbox.</p>
                  		</div>
                      <div class="col-md-2 col-sm-2 box0">
                        <div class="box1">
                  <span class="li_heart"></span>
                  <h3>23</h3>
                        </div>
                  <p>You have 23 unread messages in your inbox.</p>
                      </div>
                  	</div><!-- /row mt -->	
                  

					<div class="row mt">
                      <!--CUSTOM CHART START -->
                      <div class="border-head">
                          <h3>VISITS</h3>
                      </div>
                      <div class="custom-bar-chart">
                          <ul class="y-axis">
                              <li><span>10.000</span></li>
                              <li><span>8.000</span></li>
                              <li><span>6.000</span></li>
                              <li><span>4.000</span></li>
                              <li><span>2.000</span></li>
                              <li><span>0</span></li>
                          </ul>
                          <div class="bar">
                              <div class="title">JAN</div>
                              <div class="value tooltips" data-original-title="8.500" data-toggle="tooltip" data-placement="top">85%</div>
                          </div>
                          <div class="bar ">
                              <div class="title">FEB</div>
                              <div class="value tooltips" data-original-title="5.000" data-toggle="tooltip" data-placement="top">50%</div>
                          </div>
                          <div class="bar ">
                              <div class="title">MAR</div>
                              <div class="value tooltips" data-original-title="6.000" data-toggle="tooltip" data-placement="top">60%</div>
                          </div>
                          <div class="bar ">
                              <div class="title">APR</div>
                              <div class="value tooltips" data-original-title="4.500" data-toggle="tooltip" data-placement="top">45%</div>
                          </div>
                          <div class="bar">
                              <div class="title">MAY</div>
                              <div class="value tooltips" data-original-title="3.200" data-toggle="tooltip" data-placement="top">32%</div>
                          </div>
                          <div class="bar ">
                              <div class="title">JUN</div>
                              <div class="value tooltips" data-original-title="6.200" data-toggle="tooltip" data-placement="top">62%</div>
                          </div>
                          <div class="bar">
                              <div class="title">JUL</div>
                              <div class="value tooltips" data-original-title="7.500" data-toggle="tooltip" data-placement="top">75%</div>
                          </div>
                      </div>
                      <!--custom chart end-->
					</div><!-- /row -->	
					
                  </div><!-- /col-lg-9 END SECTION MIDDLE -->
                  
              </div><! --/row -->
              </div>
          </section>
      </section>

      <!--main content end-->
     
  </section>




    <!-- js placed at the end of the document so the pages load faster -->
    <script src="assets/js/jquery.js"></script>
    <script src="assets/js/jquery-1.8.3.min.js"></script>
    <script src="assets/js/bootstrap.min.js"></script>
    <script class="include" type="text/javascript" src="assets/js/jquery.dcjqaccordion.2.7.js"></script>
    <script src="assets/js/jquery.scrollTo.min.js"></script>
    <script src="assets/js/jquery.nicescroll.js" type="text/javascript"></script>
    <script src="assets/js/jquery.sparkline.js"></script>


    <!--common script for all pages-->
    <script src="assets/js/common-scripts.js"></script>
    
    <script type="text/javascript" src="assets/js/gritter/js/jquery.gritter.js"></script>
    <script type="text/javascript" src="assets/js/gritter-conf.js"></script>

    <!--script for this page-->
    <script src="assets/js/sparkline-chart.js"></script>    
	<script src="assets/js/zabuto_calendar.js"></script>	
	<script src="http://www.amcharts.com/lib/3/ammap.js"></script>
	<script src="http://www.amcharts.com/lib/3/maps/js/worldLow.js"></script>
	<script src="http://www.amcharts.com/lib/3/themes/dark.js"></script>
	
	<!-- click button -->
	<script type="text/javascript">
			$("#dash").click(function(){
				document.getElementById("test").innerHTML='<object type=\"text/html\" data=\"index.html\" width="100%" height="600px" ></object>';
			});
	</script>
	
	<!-- click button -->
	<script type="text/javascript">
			$("#foot").click(function(){
				var u = "CreateFile?userid=<%=userid%>";
				$.ajax({
					url: u,
					context:  document.body,
					success: function(data) {
						$("#test").html(data);
						$("#test").find("script").each(eval($(this).text()));
					}
				});
			});
	</script>
	
	
	
	<!-- click button -->
	<script type="text/javascript">
			$("#check").click(function(){
				var StartDate = prompt("StartDate", "");
				var EndDate = prompt("EndDate","");
				var u = "CreateChart?startdate=" + StartDate + "&enddate=" + EndDate+"&userid=<%=userid%>";
				if (StartDate != "" && EndDate != ""){
				  x = StartDate;
				  $.ajax({
						url: u,
						context:  document.body,
						success: function(data) {
							$("#test").html(data);
							$("#test").find("script").each(eval($(this).text()));
						}
					});
				}
				else{
					alert("Have to input date");
				}
			});
	</script>
	<!-- click button -->
	
	<!-- click button -->
	<script type="text/javascript">
			$("#recfriend").click(function(){
				var u = "RecFriend?userid=<%=userid%>";
				$.ajax({
					url: u,
					context:  document.body,
					success: function(data) {
						$("#test").html(data);
						$("#test").find("script").each(eval($(this).text()));
					}
				});
			});
	</script>
	
	<!-- click button -->
	<script type="text/javascript">
			$("#recplace").click(function(){
				var u = "RecPlace?userid=<%=userid%>";
				$.ajax({
					url: u,
					context:  document.body,
					success: function(data) {
						$("#test").html(data);
						$("#test").find("script").each(eval($(this).text()));
					}
				});
			});
	</script>
	
	<!-- click button -->
	<script type="text/javascript">
			$("#hotplace").click(function(){
				var u = "HotPlace?userid=<%=userid%>";
				$.ajax({
					
					url: u,
					context:  document.body,
					success: function(data) {
						$("#test").html(data);
						$("#test").find("script").each(eval($(this).text()));
					}
				});
			});
	</script>
	
	<script type="text/javascript">
			$("#intimacy").click(function(){
				var u = "FriendIntimacy?userid=<%=userid%>";
				$.ajax({	
					url: u,
					context:  document.body,
					success: function(data) {
						$("#test").html(data);
						$("#test").find("script").each(eval($(this).text()));
					}
				});
			});
	</script>
	
  </body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.cust.hrms.dao.*" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.text.*" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
	<%
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
	if (session.getAttribute("employeeId") == null || session.getAttribute("email") == null
			|| session.getAttribute("staffId") == null) {
		response.sendRedirect("login.jsp");

	}
	session.setAttribute("parent", "admin");
	session.setAttribute("page", "admin_leave_setup");
	if(session.getAttribute("isSupervisor") != null || session.getAttribute("isSuperAdmin") != null){
		boolean isHrAdmin = (boolean) session.getAttribute("isHrAdmin");
		boolean isSuperAdmin = (boolean) session.getAttribute("isSuperAdmin");
		if(isHrAdmin == false && isSuperAdmin == false){
			response.sendRedirect("index.jsp");
		}
	}
	%>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>HRMS | Supervisor Leave Setup</title>
  <!-- Tell the browser to be responsive to screen width -->
  <meta name="viewport" content="width=device-width, initial-scale=1">
<jsp:include page="css.jsp"></jsp:include>
</head>
<body class="hold-transition sidebar-mini">
<div class="wrapper">
<jsp:include page="topNav.jsp"></jsp:include>
<jsp:include page="sideNav.jsp"></jsp:include>

  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-6">
            <h1>Admin Leave Setup</h1>
          </div>
          <div class="col-sm-6">
            <ol class="breadcrumb float-sm-right">
              <li class="breadcrumb-item"><a href="#">Admin</a></li>
              <li class="breadcrumb-item active">Admin Leave Setup</li>
            </ol>
          </div>
        </div>
      </div><!-- /.container-fluid -->
    </section>

    <!-- Main content -->
    <section class="content">
      <div class="container-fluid">
      	<div class="row">
    
			<div class="col-12">
				        <div class="card card-primary card-outline">
          <div class="card-header">
            <h3 class="card-title">
            </h3>
          </div>
          <div class="card-body">
            <div class="row">
              <div class="col-5 col-sm-3">
                <div class="nav flex-column nav-tabs h-100" id="vert-tabs-tab" role="tablist" aria-orientation="vertical">
                  <a class="nav-link active" id="vert-tabs-leave-with-pay-inquiry-tab" data-toggle="pill" href="#vert-tabs-leave-with-pay-inquiry" role="tab" aria-controls="vert-tabs-leave-with-pay-inquiry" aria-selected="false">Leave With Pay Inquiry</a>
                  <a class="nav-link" id="vert-tabs-leave-without-pay-inquiry-tab" data-toggle="pill" href="#vert-tabs-leave-without-pay-inquiry" role="tab" aria-controls="vert-tabs-leave-without-pay-inquiry" aria-selected="false">Leave Without Pay Inquiry</a>
                  <a class="nav-link" id="vert-tabs-leave-type-inquiry-tab" data-toggle="pill" href="#vert-tabs-leave-type-inquiry" role="tab" aria-controls="vert-tabs-leave-type-inquiry" aria-selected="false">Leave Type Inquiry</a>
                  <a class="nav-link" id="vert-tabs-red-zone-tab" data-toggle="pill" href="#vert-tabs-red-zone" role="tab" aria-controls="vert-tabs-red-zone" aria-selected="false">Red Zone Inquiry</a>
                  <a class="nav-link" id="vert-tabs-holiday-inquiry-tab" data-toggle="pill" href="#vert-tabs-holiday-inquiry" role="tab" aria-controls="vert-tabs-holiday-inquiry" aria-selected="false">Holiday Inquiry</a>
                </div>
              </div>
              <div class="col-7 col-sm-9">
                <div class="tab-content" id="vert-tabs-tabContent">
                  <div class="tab-pane text-left fade show active" id="vert-tabs-leave-with-pay-inquiry" role="tabpanel" aria-labelledby="vert-tabs-leave-with-pay-inquiry-tab">
                     <ul class="chart-legend clearfix">
                      <li><a href="hrLeavePendingApprovalWithPay.jsp"><i class="far fa-circle text-primary"></i> Leave Request Pending Approval </a></li>
                      <li><a href="hrLeaveSentBackForCorrectionWithPay.jsp"><i class="far fa-circle text-primary"></i>Leave Request Sent Back For Correction</a></li>
                      <li><a href="hrLeaveApprovedWithPay.jsp"><i class="far fa-circle text-primary"></i> Approved Leave Request </a></li>
                      <li><a href="hrLeaveDeclinedWithPay.jsp"><i class="far fa-circle text-primary"></i> Declined Leave Request </a></li>
                      <li><a href="hrLeaveRecallBackPendingApproval.jsp"><i class="far fa-circle text-primary"></i> Leave Recall Back Pending Approval </a></li>
                      <li><a href="hrApprovedLeaveRecallBackWithPay.jsp"><i class="far fa-circle text-primary"></i> Approved Leave Recalled Back </a></li>
                      <li><a href="hrLeaveHistoryWithPay.jsp"><i class="far fa-circle text-primary"></i> Leave History </a></li>
                    </ul> 
                  </div>
                  <div class="tab-pane fade" id="vert-tabs-leave-without-pay-inquiry" role="tabpanel" aria-labelledby="vert-tabs-leave-without-pay-inquiry-tab">
                     <ul class="chart-legend clearfix">
                      <li><a href="#"><i class="far fa-circle text-primary"></i> Leave Request Pending Approval </a></li>
                    </ul> 
                  </div>
                  <div class="tab-pane fade" id="vert-tabs-leave-type-inquiry" role="tabpanel" aria-labelledby="vert-tabs-leave-type-inquiry-tab">
                     <ul class="chart-legend clearfix">
                      <li><a href="createLeaveType.jsp"><i class="far fa-circle text-primary"></i> Create Leave Type </a></li>
                      <li><a href="allLeaveTypes.jsp"><i class="far fa-circle text-primary"></i> All Leave Types </a></li>
                    </ul> 
                  </div>
                  <div class="tab-pane fade" id="vert-tabs-red-zone" role="tabpanel" aria-labelledby="vert-tabs-red-zone-tab">
                     <ul class="chart-legend clearfix">
                      <li><a href="createRedZone.jsp"><i class="far fa-circle text-primary"></i> Create Red Zone </a></li>
                      <li><a href="createRedZoneAllDepartment.jsp"><i class="far fa-circle text-primary"></i> Create Red Zone For All Departments </a></li>
                      <li><a href="allRedZone.jsp"><i class="far fa-circle text-primary"></i> All Red Zones </a></li>
                    </ul> 
                  </div>
                  <div class="tab-pane fade" id="vert-tabs-holiday-inquiry" role="tabpanel" aria-labelledby="vert-tabs-holiday-inquiry-tab">
                     <ul class="chart-legend clearfix">
                      <li><a href="createHoliday.jsp"><i class="far fa-circle text-primary"></i> Create Holiday </a></li>
                      <li><a href="allHolidays.jsp"><i class="far fa-circle text-primary"></i> All Holidays </a></li>
                    </ul> 
                  </div>
                </div>
              </div>
            </div>
            
          </div>
          <!-- /.card -->
        </div>
        <!-- /.card -->
				<!-- /.card -->
			</div>
		</div>
      </div>
    </section>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->
<jsp:include page="footer.jsp"></jsp:include>

  <!-- Control Sidebar -->
  <aside class="control-sidebar control-sidebar-dark">
    <!-- Control sidebar content goes here -->
  </aside>
  <!-- /.control-sidebar -->
</div>
<!-- ./wrapper -->

<jsp:include page="javascript.jsp"></jsp:include>
</body>
</html>

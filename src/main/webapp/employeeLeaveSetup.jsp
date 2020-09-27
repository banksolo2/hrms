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
	session.setAttribute("parent", "leave");
	session.setAttribute("page", "employee_leave_setup");
	%>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>HRMS | Employee Leave Setup</title>
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
            <h1>Employee Leave Setup</h1>
          </div>
          <div class="col-sm-6">
            <ol class="breadcrumb float-sm-right">
              <li class="breadcrumb-item"><a href="#">Leave</a></li>
              <li class="breadcrumb-item active">Employee Leave Setup</li>
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
                  <a class="nav-link active" id="vert-tabs-make-request-tab" data-toggle="pill" href="#vert-tabs-make-request" role="tab" aria-controls="vert-tabs-make-request" aria-selected="true">Make Leave Request</a>
                  <a class="nav-link" id="vert-tabs-leave-with-pay-inquiry-tab" data-toggle="pill" href="#vert-tabs-leave-with-pay-inquiry" role="tab" aria-controls="vert-tabs-leave-with-pay-inquiry" aria-selected="false">Leave With Pay Inquiry</a>
                  <a class="nav-link" id="vert-tabs-leave-without-pay-inquiry-tab" data-toggle="pill" href="#vert-tabs-leave-without-pay-inquiry" role="tab" aria-controls="vert-tabs-leave-without-pay-inquiry" aria-selected="false">Leave Without Pay Inquiry</a>
                </div>
              </div>
              <div class="col-7 col-sm-9">
                <div class="tab-content" id="vert-tabs-tabContent">
                  <div class="tab-pane text-left fade show active" id="vert-tabs-make-request" role="tabpanel" aria-labelledby="vert-tabs-make-request-tab">
                  	<ul class="chart-legend clearfix">
                      <li><a href="makeLeaveRequestWithPay.jsp"><i class="far fa-circle text-primary"></i> Leave With Pay </a></li>
                      <li><a href="makeLeaveRequestWithoutPay.jsp"><i class="far fa-circle text-primary"></i> Leave Without Pay </a></li>
                    </ul>
                  </div>
                  <div class="tab-pane fade" id="vert-tabs-leave-with-pay-inquiry" role="tabpanel" aria-labelledby="vert-tabs-leave-with-pay-inquiry-tab">
                     <ul class="chart-legend clearfix">
                      <li><a href="employeeApprovedLeaveRequestWithPay.jsp"><i class="far fa-circle text-primary"></i> Approved Leave Request </a></li>
                      <li><a href="employeeDraftedLeaveRequestWithPay.jsp"><i class="far fa-circle text-primary"></i> Drafted Leave Request </a></li>
                      <li><a href="employeeLeaveRequestSentBackForCorrectionWithPay.jsp"><i class="far fa-circle text-primary"></i> Leave Request Send Back For Correction</a></li>
                      <li><a href="employeePendingLeaveRequestWithPay.jsp"><i class="far fa-circle text-primary"></i> Pending Leave Request</a></li>
                      <li><a href="employeeDeclinedLeaveRequestWithPay.jsp"><i class="far fa-circle text-primary"></i> Declined Leave Request</a></li>
                      <li><a href="employeeLeaveRecallBackPendingApprovalWithPay.jsp"><i class="far fa-circle text-primary"></i> Leave Recall Back Pending Approval</a></li>
                      <li><a href="employeeApprovedLeaveRecalledBackWithPay.jsp"><i class="far fa-circle text-primary"></i> Approved Leave Recalled Back</a></li>
                      <li><a href="employeeLeaveHistoryWithPay.jsp"><i class="far fa-circle text-primary"></i> Leave History</a></li>
                    </ul>
                  </div>
                  <div class="tab-pane fade" id="vert-tabs-leave-without-pay-inquiry" role="tabpanel" aria-labelledby="vert-tabs-leave-without-pay-inquiry-tab">
                     <ul class="chart-legend clearfix">
                      <li><a href="employeeApprovedLeaveRequestWithoutPay.jsp"><i class="far fa-circle text-primary"></i> Approved Leave Request </a></li>
                      <li><a href="employeeDraftedLeaveRequestWithoutPay.jsp"><i class="far fa-circle text-primary"></i> Drafted Leave Request </a></li>
                      <li><a href="#"><i class="far fa-circle text-primary"></i> Leave Request Send Back For Correction</a></li>
                      <li><a href="#"><i class="far fa-circle text-primary"></i> Pending Leave Request</a></li>
                      <li><a href="#"><i class="far fa-circle text-primary"></i> Declined Leave Request</a></li>
                      <li><a href="#"><i class="far fa-circle text-primary"></i> Leave Recall Back Pending Approval</a></li>
                      <li><a href="#"><i class="far fa-circle text-primary"></i> Approved Leave Recalled Back</a></li>
                      <li><a href="#"><i class="far fa-circle text-primary"></i> Leave History</a></li>
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

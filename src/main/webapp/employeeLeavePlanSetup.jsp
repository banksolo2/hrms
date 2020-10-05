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
	session.setAttribute("parent", "leave_plan");
	session.setAttribute("page", "employee_leave_plan_setup");
	%>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>HRMS | Employee Leave Plan Setup</title>
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
            <h1>Employee Leave Plan Setup</h1>
          </div>
          <div class="col-sm-6">
            <ol class="breadcrumb float-sm-right">
              <li class="breadcrumb-item"><a href="#">Leave Plan</a></li>
              <li class="breadcrumb-item active">Employee Leave Plan Setup</li>
            </ol>
          </div>
        </div>
      </div><!-- /.container-fluid -->
    </section>

    <!-- Main content -->
    <section class="content">
      <div class="container-fluid">
      	<div class="row">
      		<%
      		String successMessage = (String)session.getAttribute("success");
      		if(successMessage != null){
      		%>
      		<div class="col-md-12">
	            <div class="card bg-success">
	              <div class="card-header">
	                <h3 class="card-title"><i class="icon fas fa-check"></i> Success Message</h3>
	
	                <div class="card-tools">
	                  <button type="button" class="btn btn-tool" data-card-widget="remove"><i class="fas fa-times"></i>
	                  </button>
	                </div>
	                <!-- /.card-tools -->
	              </div>
	              <!-- /.card-header -->
	              <div class="card-body">
	                <%=successMessage %>
	              </div>
	              <!-- /.card-body -->
	            </div>
	            <!-- /.card -->
	          </div>
	          <!-- /.col -->
	          <%
      		}
      		session.setAttribute("success", null);
	          %>
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
                  <a class="nav-link active" id="vert-tabs-make-leave-plan-tab" data-toggle="pill" href="#vert-tabs-make-leave-plan" role="tab" aria-controls="vert-tabs-make-leave-plan" aria-selected="false">Make Leave Plan</a>
                  <a class="nav-link" id="vert-tabs-leave-plan-inquiry-tab" data-toggle="pill" href="#vert-tabs-leave-plan-inquiry" role="tab" aria-controls="vert-tabs-leave-plan-inquiry" aria-selected="false">Leave Plan Inquiry</a>
                </div>
              </div>
              <div class="col-7 col-sm-9">
                <div class="tab-content" id="vert-tabs-tabContent">
                  <div class="tab-pane text-left fade show active" id="vert-tabs-make-leave-plan" role="tabpanel" aria-labelledby="vert-tabs-make-leave-plan-tab">
                     <h5>Make Leave Plan</h5>
                     <ul class="chart-legend clearfix">
                      <li><a href="createLeavePlan.jsp"><i class="far fa-circle text-primary"></i> Create Leave Plan </a></li>
                    </ul> 
                  </div>
                  <div class="tab-pane fade" id="vert-tabs-leave-plan-inquiry" role="tabpanel" aria-labelledby="vert-tabs-leave-plan-inquiry-tab">
                     <h5>Leave Plan Inquiry</h5>
                     <ul class="chart-legend clearfix">
                      <li><a href="allDraftedLeavePlan.jsp"><i class="far fa-circle text-primary"></i> All Drafted Leave Plans </a></li>
                      <li><a href="allPendingLeavePlan.jsp"><i class="far fa-circle text-primary"></i> All Pending Leave Plans </a></li>
                      <li><a href="allApprovedLeavePlan.jsp"><i class="far fa-circle text-primary"></i> All Approved Leave Plans</a></li>
                      <li><a href="allLeavePlanSentForCorrection.jsp"><i class="far fa-circle text-primary"></i> All leave Plans Sent For Correction</a></li>
                      <li><a href="allDeclinedLeavePlan.jsp"><i class="far fa-circle text-primary"></i> All Declined Leave Plans</a></li>
                      <li><a href="employeeLeavePlanHistory.jsp"><i class="far fa-circle text-primary"></i> Leave Plan History</a></li>
                    </ul> 
                  </div>
                  
                </div>
              </div>
            </div>
            
          </div>
          <!-- /.card -->
        </div>
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

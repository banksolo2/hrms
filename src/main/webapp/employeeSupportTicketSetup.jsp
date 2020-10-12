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
	session.setAttribute("parent", "support_ticket");
	session.setAttribute("page", "employee_support_ticket_setup");
	%>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>HRMS | Support Ticket Setup</title>
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
            <h1>Support Ticket Setup</h1>
          </div>
          <div class="col-sm-6">
            <ol class="breadcrumb float-sm-right">
              <li class="breadcrumb-item"><a href="#">Support Ticket</a></li>
              <li class="breadcrumb-item active">Support Ticket Setup</li>
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
              <div class="col-5 col-sm-5">
                <div class="nav flex-column nav-tabs h-100" id="vert-tabs-tab" role="tablist" aria-orientation="vertical">
                  <a class="nav-link active" id="vert-tabs-create-support-ticket-tab" data-toggle="pill" href="#vert-tabs-create-support-ticket" role="tab" aria-controls="vert-tabs-create-support-ticket" aria-selected="false">Create Support Ticket</a>
                  <a class="nav-link" id="vert-tabs-employee-support-ticket-inquiry-tab" data-toggle="pill" href="#vert-tabs-employee-support-ticket-inquiry" role="tab" aria-controls="vert-tabs-employee-support-ticket-inquiry" aria-selected="false">Personal Support Ticket Inquiry (Employees)</a>
                  <a class="nav-link" id="vert-tabs-department-support-ticket-inquiry-tab" data-toggle="pill" href="#vert-tabs-department-support-ticket-inquiry" role="tab" aria-controls="vert-tabs-department-support-ticket-inquiry" aria-selected="false">Personal Support Ticket Inquiry (Departments)</a>
                  <a class="nav-link" id="vert-tabs-support-ticket-inquiry-tab" data-toggle="pill" href="#vert-tabs-support-ticket-inquiry" role="tab" aria-controls="vert-tabs-support-ticket-inquiry" aria-selected="false">Support Ticket Inquiry</a>
                </div>
              </div>
              <div class="col-7 col-sm-7">
                <div class="tab-content" id="vert-tabs-tabContent">
                  <div class="tab-pane text-left fade show active" id="vert-tabs-create-support-ticket" role="tabpanel" aria-labelledby="vert-tabs-create-support-ticket-tab">
                     <h5>Create Support Ticket</h5>
                     <ul class="chart-legend clearfix">
                      <li><a href="createEmployeeSupportTicket.jsp"><i class="far fa-circle text-primary"></i> Create Support Ticket (Employees) </a></li>
                      <li><a href="createDepartmentSupportTicket.jsp"><i class="far fa-circle text-primary"></i> Create Support Ticket (Departments) </a></li>
                    </ul> 
                  </div>
                  <div class="tab-pane fade" id="vert-tabs-employee-support-ticket-inquiry" role="tabpanel" aria-labelledby="vert-tabs-employee-support-ticket-inquiry-tab">
                     <h5>Personal Support Ticket Inquiry (Employees)</h5>
                     <ul class="chart-legend clearfix">
                      <li><a href="employeePendingSupportTicketReportReport.jsp"><i class="far fa-circle text-primary"></i> All Pending Support Tickets </a></li>
                      <li><a href="employeeResolveSupportTicketReport.jsp"><i class="far fa-circle text-primary"></i> All Resolved Support Tickets </a></li>
                      <li><a href="employeeClosedSupportTicketReport.jsp"><i class="far fa-circle text-primary"></i> All Closed Support Tickets </a></li>
                    </ul> 
                  </div>
                  <div class="tab-pane fade" id="vert-tabs-department-support-ticket-inquiry" role="tabpanel" aria-labelledby="vert-tabs-department-support-ticket-inquiry-tab">
                     <h5>Personal Support Ticket Inquiry (Departments)</h5>
                     <ul class="chart-legend clearfix">
                      <li><a href="departmentPendingSupportTicketReport.jsp"><i class="far fa-circle text-primary"></i> All Pending Support Tickets </a></li>
                      <li><a href="departmentResolveSupportTicketReport.jsp"><i class="far fa-circle text-primary"></i> All Resolved Support Tickets </a></li>
                      <li><a href="departmentClosedSupportTicketReport.jsp"><i class="far fa-circle text-primary"></i> All Closed Support Tickets </a></li>
                    </ul> 
                  </div>
                  <div class="tab-pane fade" id="vert-tabs-support-ticket-inquiry" role="tabpanel" aria-labelledby="vert-tabs-support-ticket-inquiry-tab">
                     <h5>Support Ticket Inquiry</h5>
                     <ul class="chart-legend clearfix">
                      <li><a href="allPendingSupportTicketReportForEmployee.jsp"><i class="far fa-circle text-primary"></i> All Pending Support Ticket (Employee)</a></li>
                      <li><a href="allPendingSupportTicketReportForDepartment.jsp"><i class="far fa-circle text-primary"></i> All Pending Support Ticket (Department)</a></li>
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

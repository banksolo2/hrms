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
	session.setAttribute("page", "admin_payroll_setup");
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
  <title>HRMS | Admin Leave Setup</title>
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
            <h1>Payroll Setup</h1>
          </div>
          <div class="col-sm-6">
            <ol class="breadcrumb float-sm-right">
              <li class="breadcrumb-item"><a href="#">Admin</a></li>
              <li class="breadcrumb-item active">Payroll Setup</li>
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
              <div class="col-6 col-sm-4">
                <div class="nav flex-column nav-tabs h-100" id="vert-tabs-tab" role="tablist" aria-orientation="vertical">
                  <a class="nav-link active" id="vert-tabs-pay-element-inquiry-tab" data-toggle="pill" href="#vert-tabs-pay-element-inquiry" role="tab" aria-controls="vert-tabs-pay-element-inquiry" aria-selected="false">Pay Element Inquiry</a>
                  <a class="nav-link" id="vert-tabs-level-pay-element-inquiry-tab" data-toggle="pill" href="#vert-tabs-level-pay-element-inquiry" role="tab" aria-controls="vert-tabs-level-pay-element-inquiry" aria-selected="false">Level Pay Element Inquiry</a>
                  <a class="nav-link" id="vert-tabs-boundary-inquiry-tab" data-toggle="pill" href="#vert-tabs-boundary-inquiry" role="tab" aria-controls="vert-tabs-boundary-inquiry" aria-selected="false">Pay Boundary Inquiry</a>
                  <a class="nav-link" id="vert-tabs-pay-element-deduction-inquiry-tab" data-toggle="pill" href="#vert-tabs-pay-element-deduction-inquiry" role="tab" aria-controls="vert-tabs-pay-element-deduction-inquiry" aria-selected="false">Pay Element Deduction Inquiry</a>
                  <a class="nav-link" id="vert-tabs-employee-pay-element-inquiry-tab" data-toggle="pill" href="#vert-tabs-employee-pay-element-inquiry" role="tab" aria-controls="vert-tabs-employee-pay-element-inquiry" aria-selected="false">Employee Pay Element Inquiry</a>   
                </div>
              </div>
              <div class="col-6 col-sm-8">
                <div class="tab-content" id="vert-tabs-tabContent">
                  <div class="tab-pane text-left fade show active" id="vert-tabs-pay-element-inquiry" role="tabpanel" aria-labelledby="vert-tabs-pay-element-inquiry-tab">
                     <h5>Pay Element Inquiry</h5>
                     <ul class="chart-legend clearfix">
                      <li><a href="createPayElement.jsp"><i class="far fa-circle text-primary"></i> Create Pay Element </a></li>
                      <li><a href="allPayElementReport.jsp"><i class="far fa-circle text-primary"></i> All Pay Elements</a></li>
                    </ul> 
                  </div>
                  <div class="tab-pane fade" id="vert-tabs-level-pay-element-inquiry" role="tabpanel" aria-labelledby="vert-tabs-level-pay-element-inquiry-tab">
                     <h5>Level Pay Element Inquiry</h5>
                     <ul class="chart-legend clearfix">
                      <li><a href="createLevelPayElement.jsp"><i class="far fa-circle text-primary"></i> Create Level Pay Element </a></li>
                      <li><a href="allLevelPayElementReport.jsp"><i class="far fa-circle text-primary"></i> All Level Pay Elements</a></li>
                    </ul> 
                  </div>
                  <div class="tab-pane fade" id="vert-tabs-boundary-inquiry" role="tabpanel" aria-labelledby="vert-tabs-boundary-inquiry-tab">
                     <h5>Pay Boundary Inquiry</h5>
                     <ul class="chart-legend clearfix">
                      <li><a href="uncreatedPaymentBoundary.jsp"><i class="far fa-circle text-primary"></i> All UnCreated Pay Boundary </a></li>
                      <li><a href="allPaymentBoundariesReport.jsp"><i class="far fa-circle text-primary"></i> All Pay Boundary</a></li>
                    </ul> 
                  </div>
                  <div class="tab-pane fade" id="vert-tabs-pay-element-deduction-inquiry" role="tabpanel" aria-labelledby="vert-tabs-pay-element-deduction-inquiry-tab">
                     <h5>Pay Element Deduction Inquiry</h5>
                     <ul class="chart-legend clearfix">
                      <li><a href="createPayElementDeductionType.jsp"><i class="far fa-circle text-primary"></i> Create Pay Element Deduction Type </a></li>
                      <li><a href="allPayElementDeductionTypesReport.jsp"><i class="far fa-circle text-primary"></i> All Pay Element Deduction Types </a></li>
                    </ul> 
                  </div>
                  <div class="tab-pane fade" id="vert-tabs-employee-pay-element-inquiry" role="tabpanel" aria-labelledby="vert-tabs-employee-pay-element-inquiry-tab">
                     <h5>Employee Pay Element Inquiry</h5>
                     <ul class="chart-legend clearfix">
                      <li><a href="allEmployeePayElementReport.jsp"><i class="far fa-circle text-primary"></i> All Employees Pay Elements </a></li>
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

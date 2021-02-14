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
	session.setAttribute("page", "developer_setup");
	if(session.getAttribute("isSuperAdmin") != null){
		boolean isSuperAdmin = (boolean)session.getAttribute("isSuperAdmin");
		if(isSuperAdmin == false){
			response.sendRedirect("index.jsp");
		}
	}
	%>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>HRMS | Developer Setup</title>
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
            <h1>Developer Setup</h1>
          </div>
          <div class="col-sm-6">
            <ol class="breadcrumb float-sm-right">
              <li class="breadcrumb-item"><a href="#">Admin</a></li>
              <li class="breadcrumb-item active">Developer Setup</li>
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
              <div class="card-body">
                <h5 class="card-title"></h5>
                <a href="createEmployeeStatus.jsp" class="card-link">Create Employee Status</a><br/>
                <a href="allEmployeeStatusReport.jsp" class="card-link">All Employee Statues</a><br/>
                <a href="createRole.jsp" class="card-link">Create Role</a><br/>
                <a href="allRoles.jsp" class="card-link">All Roles</a><br/>
				<a href="allStates.jsp" class="card-link">All States</a><br/>
                <a href="createState.jsp" class="card-link">Create State</a><br/>
				<a href="allStates.jsp" class="card-link">All States</a><br/>
				<a href="createBank.jsp" class="card-link">Create Bank</a><br/>
				<a href="allBanks.jsp" class="card-link">All Banks</a><br/>
				<a href="createGender.jsp" class="card-link">Create Gender</a><br/>
				<a href="allGenders.jsp" class="card-link">All Genders</a><br/>
				<a href="createMartialStatus.jsp" class="card-link">Create Martial Status</a><br/>
				<a href="allMartialStatus.jsp" class="card-link">All Martial status</a><br/>
				<a href="createLeaveStatus.jsp" class="card-link">Create Leave Status</a><br/>
				<a href="allLeaveStatues.jsp" class="card-link">All Leave Statues</a><br/>
				<a href="createLeavePlanStatus.jsp" class="card-link">Create Leave Plan Status</a><br/>
				<a href="allLeavePlanStatues.jsp" class="card-link">All Leave Plan Statues</a><br/>
				<a href="createRequisitionStatus.jsp" class="card-link">Create Requisition Status</a><br/>
				<a href="allRequisitionStatus.jsp" class="card-link">All Requisition Statues</a><br/>
				<a href="createElementStatus.jsp" class="card-link">Create Element Status</a><br/>
				<a href="allElementStatues.jsp" class="card-link">All Element Statues</a><br/>
				<a href="createPayrollRole.jsp" class="card-link">Create Payroll Role</a><br/>
				<a href="allPayrollRole.jsp" class="card-link">All Payroll Roles</a><br/>
              </div>
            </div><!-- /.card -->
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

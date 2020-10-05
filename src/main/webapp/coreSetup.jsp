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
	session.setAttribute("page", "core_setup");
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
  <title>HRMS | Core Setup</title>
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
            <h1>Core Setup</h1>
          </div>
          <div class="col-sm-6">
            <ol class="breadcrumb float-sm-right">
              <li class="breadcrumb-item"><a href="#">Admin</a></li>
              <li class="breadcrumb-item active">Core Setup</li>
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
                  <a class="nav-link active" id="vert-tabs-employee-setup-tab" data-toggle="pill" href="#vert-tabs-employee-setup" role="tab" aria-controls="vert-tabs-employee-setup" aria-selected="true">Employee Setup</a>
                  <a class="nav-link" id="vert-tabs-department-setup-tab" data-toggle="pill" href="#vert-tabs-department-setup" role="tab" aria-controls="vert-tabs-department-setup" aria-selected="false">Department Setup</a>
                  <a class="nav-link" id="vert-tabs-level-setup-tab" data-toggle="pill" href="#vert-tabs-level-setup" role="tab" aria-controls="vert-tabs-level-setup" aria-selected="false">Level Setup</a>
                  <a class="nav-link" id="vert-tabs-branch-setup-tab" data-toggle="pill" href="#vert-tabs-branch-setup" role="tab" aria-controls="vert-tabs-branch-setup" aria-selected="false">Branch Setup</a>
                  <a class="nav-link" id="vert-tabs-company-setup-tab" data-toggle="pill" href="#vert-tabs-company-setup" role="tab" aria-controls="vert-tabs-company-setup" aria-selected="false">Company Setup</a>
                  <a class="nav-link" id="vert-tabs-ticket-support-setup-tab" data-toggle="pill" href="#vert-tabs-ticket-support-setup" role="tab" aria-controls="vert-tabs-ticket-support-setup" aria-selected="false">Ticket Support Setup</a>
                </div>
              </div>
              <div class="col-7 col-sm-9">
                <div class="tab-content" id="vert-tabs-tabContent">
                  <div class="tab-pane text-left fade show active" id="vert-tabs-employee-setup" role="tabpanel" aria-labelledby="vert-tabs-employee-setup-tab">
                     <h5>Employee Setup</h5>
                     <ul class="chart-legend clearfix">
                      <li><a href="createEmployee.jsp"><i class="far fa-circle text-primary"></i> Create Employee </a></li>
                      <li><a href="allEmployees.jsp"><i class="far fa-circle text-primary"></i> All Employees </a></li>
                      <li><a href="createEmployeeRole.jsp"><i class="far fa-circle text-primary"></i> Create Employee Role </a></li>
                      <li><a href="allEmployeeRoles.jsp"><i class="far fa-circle text-primary"></i> All Employee Roles </a></li>
                    </ul> 
                  </div>
                  <div class="tab-pane fade " id="vert-tabs-department-setup" role="tabpanel" aria-labelledby="vert-tabs-department-setup-tab">
                     <h5>Department Setup</h5>
                     <ul class="chart-legend clearfix">
                      <li><a href="createDepartment.jsp"><i class="far fa-circle text-primary"></i> Create Department </a></li>
                      <li><a href="allDepartments.jsp"><i class="far fa-circle text-primary"></i> All Departments </a></li>
                      <li><a href="createDepartmentHead.jsp"><i class="far fa-circle text-primary"></i> Create Department Head</a></li>
                      <li><a href="allDepartmentHeads.jsp"><i class="far fa-circle text-primary"></i> All Department Head</a></li>
                    </ul> 
                  </div>
                  <div class="tab-pane fade " id="vert-tabs-level-setup" role="tabpanel" aria-labelledby="vert-tabs-level-setup-tab">
                  	<h5>Level Setup</h5>
                  	<ul class="chart-legend clearfix">
                      <li><a href="createLevel.jsp"><i class="far fa-circle text-primary"></i> Create Level </a></li>
                      <li><a href="allLevels.jsp"><i class="far fa-circle text-primary"></i> All Levels </a></li>
                    </ul> 
                  </div>
                  <div class="tab-pane fade " id="vert-tabs-branch-setup" role="tabpanel" aria-labelledby="vert-tabs-branch-setup-tab">
                  	<h5>Branch Setup</h5>
                  	<ul class="chart-legend clearfix">
                      <li><a href="createBranch.jsp"><i class="far fa-circle text-primary"></i> Create Branch </a></li>
                      <li><a href="allBranches.jsp"><i class="far fa-circle text-primary"></i> All Branches </a></li>
                    </ul> 
                  </div>
                  <div class="tab-pane fade " id="vert-tabs-company-setup" role="tabpanel" aria-labelledby="vert-tabs-company-setup-tab">
                  	<h5>Company Setup</h5>
                  	<ul class="chart-legend clearfix">
                      <li><a href="createCompany.jsp"><i class="far fa-circle text-primary"></i> Create Company </a></li>
                      <li><a href="allCompanies.jsp"><i class="far fa-circle text-primary"></i> All Companies </a></li>
                    </ul> 
                  </div>
                  <div class="tab-pane fade " id="vert-tabs-ticket-support-setup" role="tabpanel" aria-labelledby="vert-tabs-company-setup-tab">
                  	<h5>Ticket Support Setup</h5>
                  	<ul class="chart-legend clearfix">
                      <li><a href="createIssueType.jsp"><i class="far fa-circle text-primary"></i> Create Issue Type </a></li>
                      <li><a href="allIssueTypes.jsp"><i class="far fa-circle text-primary"></i> All Issue Type </a></li>
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

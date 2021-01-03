<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.cust.hrms.dao.*"%>
<%@ page import="java.sql.*"%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>HRMS | All Payroll Role Levels</title>
  <!-- Tell the browser to be responsive to screen width -->
  <meta name="viewport" content="width=device-width, initial-scale=1">
<jsp:include page="css.jsp"></jsp:include>
</head>
<body class="hold-transition sidebar-mini layout-fixed">
	<%
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
	if (session.getAttribute("employeeId") == null || session.getAttribute("email") == null
			|| session.getAttribute("staffId") == null) {
		response.sendRedirect("login.jsp");

	}
	session.setAttribute("parent", "admin");
	session.setAttribute("page", "payroll_setup");
	boolean isHrAdmin = false;
	boolean isSuperAdmin = false;
	boolean isGlobalOfficer = false;
	boolean isSeniorOfficer = false;
	boolean isOfficer = false;
	if(session.getAttribute("isSupervisor") != null || session.getAttribute("isSuperAdmin") != null){
		isHrAdmin = (boolean) session.getAttribute("isHrAdmin");
		isSuperAdmin = (boolean) session.getAttribute("isSuperAdmin");
		isGlobalOfficer = (boolean) session.getAttribute("isGlobalOfficer");
		isSeniorOfficer = (boolean) session.getAttribute("isSeniorOfficer");
		isOfficer = (boolean) session.getAttribute("isOfficer");
		if(isHrAdmin == false){
			response.sendRedirect("index.jsp");
		}
	}
	%>
	<div class="wrapper">
		<jsp:include page="topNav.jsp"></jsp:include>

		<jsp:include page="sideNav.jsp"></jsp:include>


		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper">
			<!-- Content Header (Page header) -->
			<div class="content-header">
				<div class="container-fluid">
					<div class="row mb-2">
						<div class="col-sm-6">
							<h1 class="m-0 text-dark">All Payroll History</h1>
						</div>
						<!-- /.col -->
						<div class="col-sm-6">
							<ol class="breadcrumb float-sm-right">
								<li class="breadcrumb-item"><a href="#">Admin</a></li>
								<li class="breadcrumb-item"><a href="adminPayrollSetup.jsp">Payroll Setup</a></li>
								<li class="breadcrumb-item active">All Payroll History</li>
							</ol>
						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->
				</div>
				<!-- /.container-fluid -->
			</div>
			<!-- /.content-header -->


			<!-- Main content -->
			<section class="content">
				<div class="container-fluid">
					<div class="row">
						<div class="col-12">
							<div class="card">
								<div class="card-header">
									<h3 class="card-title"></h3>
								</div>
								<!-- /.card-header -->
								<div class="card-body">
									<table id="example1" class="table table-bordered table-striped">
										<thead>
											<tr>
												<th>Employee</th>
												<th></th>
											</tr>
										</thead>
										<tbody>
										<%
										EmployeeDao ed = new EmployeeDao();
										PayrollRoleNameDao prnd = new PayrollRoleNameDao();
										PayrollRoleLevelDao prld = new PayrollRoleLevelDao();
										int payrollRoleNameId = 0;
										String fullName;
										ResultSet rs;
										if(isSeniorOfficer){
											payrollRoleNameId = prnd.getPayrollRoleNameId("senior_officer");
											int levelsId[] = prld.getPayrollRoleLevelIds(payrollRoleNameId);
											rs = ed.getEmployeeByLevels(levelsId);
										}
										else if(isGlobalOfficer || isHrAdmin){
											rs = ed.getAllEmployee();
										}
										else{
											payrollRoleNameId = prnd.getPayrollRoleNameId("officer");
											int levelsId[] = prld.getPayrollRoleLevelIds(payrollRoleNameId);
											rs = ed.getEmployeeByLevels(levelsId);
										}
										while(rs.next()){
											fullName = rs.getString("first_name")+" "+rs.getString("middle_name")+" "+rs.getString("last_name")+" ("+rs.getString("staff_id")+")";
										%>
											<tr>
												<td><%=fullName %></td>
												<td>
													<form action="searchEmployeePayroll.jsp" method="post">
													<input type="hidden" name="employeeId" value="<%=rs.getInt("employee_id") %>" />
													<button type="submit" class="btn btn-primary">View Payroll</button>
													</form>
												</td>
											</tr>
										<%
										}
										%>
										</tbody>
										<tfoot>
											
										</tfoot>
									</table>
								</div>
								<!-- /.card-body -->
							</div>
							<!-- /.card -->
						</div>
					</div>
				</div>
				<!-- /.container-fluid -->
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

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.cust.hrms.dao.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.text.*" %>

<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>HRMS | All Leave Plans Approved</title>
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
	session.setAttribute("parent", "leave_plan");
	session.setAttribute("page", "hod_leave_plan_setup");

	if(session.getAttribute("isDepartmentHead") != null){
		boolean isDepartmentHead = (boolean) session.getAttribute("isDepartmentHead");
		if(isDepartmentHead == false){
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
							<h1 class="m-0 text-dark">All Leave Plans Approved</h1>
						</div>
						<!-- /.col -->
						<div class="col-sm-6">
							<ol class="breadcrumb float-sm-right">
								<li class="breadcrumb-item"><a href="#">Leave Plan</a></li>
								<li class="breadcrumb-item"><a href="hodLeavePlanSetup.jsp">Department Head Setup</a></li>
								<li class="breadcrumb-item active">All Leave Plans Approved</li>
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
						<%
						String successMessage =(String) session.getAttribute("success");
						if(successMessage != null){
						%>
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
				            <%
						}
						session.setAttribute("success", null);
				            %>
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
												<th>Department</th>
												<th>Start Date</th>
												<th>End Date</th>
												<th>No Of Days</th>
												<th>Status</th>
											</tr>
										</thead>
										<tbody>
										<%
										int employeeId = 0;
										if(session.getAttribute("email") != null){
											employeeId = (int) session.getAttribute("employeeId");
										}
										LeavePlanDao lpd = new LeavePlanDao();
										LeavePlanStatusDao lpsd = new LeavePlanStatusDao();
										ResultSet rs = lpd.getEmployeesApprovedLeavePlansReport(employeeId);
										String comment = "";
										DateDao dd = new DateDao();
										EmployeeDao ed = new EmployeeDao();
										DepartmentDao ded = new DepartmentDao();
										while(rs.next()){
											comment = rs.getString("comment");
											comment = (comment == null) ? "" : comment;
											String department = ded.getDepartmentName(rs.getInt("department_id"));
										%>
											<tr>
												<td><%=ed.getEmployeeName(rs.getInt("employee_id")) %></td>
												<td><%=department %></td>
												<td><%=dd.changeFormatDate(rs.getDate("start_date").toString()) %></td>
												<td><%=dd.changeFormatDate(rs.getDate("end_date").toString()) %></td>
												<td><%=rs.getInt("no_of_days") %></td>
												<td><%=lpsd.getLeavePlanStatusName(rs.getInt("leave_plan_status_id")) %></td>
											</tr>
										<%
										}
										rs.close();
										%>
										</tbody>
										<tfoot>
											<!--<tr>
												<th>Employee</th>
												<th>Department</th>
												<th>Start Date</th>
												<th>End Date</th>
												<th>No Of Days</th>
												<th>Status</th>
											</tr>-->
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

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.cust.hrms.dao.*"%>
<%@ page import="java.sql.*"%>

<!DOCTYPE html>
<html>
<jsp:include page="head.jsp"></jsp:include>
<body class="hold-transition sidebar-mini layout-fixed">
	<%
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
	if (session.getAttribute("employeeId") == null || session.getAttribute("email") == null
			|| session.getAttribute("staffId") == null) {
		response.sendRedirect("login.jsp");

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
							<h1 class="m-0 text-dark">All Employee Roles</h1>
						</div>
						<!-- /.col -->
						<div class="col-sm-6">
							<ol class="breadcrumb float-sm-right">
								<li class="breadcrumb-item"><a href="#">Admin</a></li>
								<li class="breadcrumb-item active">All Employee Roles</li>
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
												<th>Role</th>
												<th>Created At</th>
												<th>created BY</th>
												<th>Updated At</th>
												<th>Updated By</th>
												<th></th>
												<th></th>
											</tr>
										</thead>
										<tbody>
										<%
										EmployeeDao ed = new EmployeeDao();
										EmployeeRoleDao erd = new EmployeeRoleDao();
										RoleDao rd = new RoleDao();
										ResultSet rs = erd.getAllEmployeeRoles();
										int employeeId;
										int roleId;
										int createdById;
										int updatedById;
										String createdBy;
										String updatedBy;
										while(rs.next()){
											createdById = rs.getInt("created_by");
											updatedById = rs.getInt("updated_by");
											createdBy = (createdById < 1) ? "" : ed.getEmployeeName(createdById);
											updatedBy = (updatedById < 1) ? "" : ed.getEmployeeName(updatedById);
											employeeId = rs.getInt("employee_id");
											roleId = rs.getInt("role_id");
										%>
											<tr>
												<td><%=ed.getEmployeeName(employeeId) %></td>
												<td><%=rd.getRoleName(roleId) %></td>
												<td><%=rs.getTimestamp("created_at") %></td>
												<td><%=createdBy %></td>
												<td><%=rs.getTimestamp("updated_at") %></td>
												<td><%=updatedBy %></td>
												<td>
													<form action="editEmployeeRole.jsp" method="post">
													<input type="hidden" name="employeeRoleId" value="<%=rs.getInt("employee_role_id") %>" />
													<button type="submit" class="btn btn-primary"><i class="fas fa-pencil-alt"></i> Edit</button>
													</form>
												</td>
												
												<td>
													<form action="deleteEmployeeRole" method="post">
													<input type="hidden" name="employeeRoleId" value="<%=rs.getInt("employee_role_id") %>" />
													<button type="submit" class="btn btn-danger"><i class="fas fa-trash"></i> Delete</button>
													</form>
												</td>
											</tr>
										<%
										}
										%>
										</tbody>
										<tfoot>
											<tr>
												<th>Employee</th>
												<th>Role</th>
												<th>Created At</th>
												<th>Created By</th>
												<th>Updated At</th>
												<th>Updated By</th>
												<th></th>
												<th></th>
											</tr>
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

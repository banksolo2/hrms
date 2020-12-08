<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.cust.hrms.dao.*"%>
<%@ page import="java.sql.*"%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>HRMS | All Employees</title>
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
	session.setAttribute("page", "core_setup");
	if(session.getAttribute("isSupervisor") != null || session.getAttribute("isSuperAdmin") != null){
		boolean isHrAdmin = (boolean) session.getAttribute("isHrAdmin");
		boolean isSuperAdmin = (boolean) session.getAttribute("isSuperAdmin");
		if(isHrAdmin == false && isSuperAdmin == false){
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
							<h1 class="m-0 text-dark">All Employees</h1>
						</div>
						<!-- /.col -->
						<div class="col-sm-6">
							<ol class="breadcrumb float-sm-right">
								<li class="breadcrumb-item"><a href="#">Admin</a></li>
								<li class="breadcrumb-item"><a href="coreSetup.jsp">Core Setup</a></li>
								<li class="breadcrumb-item active">All Employees</li>
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
						<%
								String successMessage = (String) session.getAttribute("success");
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
								String errorMessage = (String) session.getAttribute("error");
								if(errorMessage != null){
							        %>
							        <div class="col-md-12">
							            <div class="card bg-danger">
							              <div class="card-header">
							                <h3 class="card-title"><i class="icon fas fa-ban"></i> Error Message</h3>
							                <div class="card-tools">
							                  <button type="button" class="btn btn-tool" data-card-widget="remove"><i class="fas fa-times"></i>
							                  </button>
							                </div>
							                <!-- /.card-tools -->
							              </div>
							              <div class="card-body">
							                <%=errorMessage %>
							              </div>
							              <!-- /.card-body -->
							            </div>
							            <!-- /.card -->
							          </div>
							          <!-- /.col -->
							          <%
								}
								session.setAttribute("error", null);
							          %>
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
												<th>First Name</th>
												<th>Middle Name</th>
												<th>Last Name</th>
												<th>Department</th>
												<th>Staff ID</th>
												<th>Email</th>
												<th></th>
												<th></th>
											</tr>
										</thead>
										<tbody>
										<%
										int employeeId = 0;
										if(session.getAttribute("email") != null){
											employeeId = (int)session.getAttribute("employeeId");
										}
										EmployeeDao ed = new EmployeeDao();
										DepartmentDao dd = new DepartmentDao();
										ResultSet rs = ed.getAllEmployeeOnUpdate(employeeId);
										int createdBy;
										int updatedBy;
										while(rs.next()){;
										%>
											<tr>
												<td><%=rs.getString("first_name") %></td>
												<td><%=rs.getString("middle_name") %></td>
												<td><%=rs.getString("last_name") %></td>
												<td><%=dd.getDepartmentName(rs.getInt("department_id")) %></td>
												<td><%=rs.getString("staff_id") %></td>
												<td><%=rs.getString("email") %></td>
												<td>
													<form action="editEmployee.jsp" method="post">
													<input type="hidden" name="employeeId" value="<%=rs.getInt("employee_id") %>" />
													<button type="submit" class="btn btn-primary"><i class="fas fa-pencil-alt"></i> Edit</button>
													</form>
												</td>
												
												<td>
													<form action="deleteEmployee" method="post">
													<input type="hidden" name="email" value="<%=rs.getString("email") %>" />
													<button type="submit" class="btn btn-danger" id="save" onsubmit="disableSaveButton()"><i class="fas fa-trash"></i> Delete</button>
													</form>
												</td>
											</tr>
										<%
										}
										%>
										</tbody>
										<tfoot>
											<!--  tr>
												<th>First Name</th>
												<th>Middle Name</th>
												<th>Last Name</th>
												<th>Department</th>
												<th>Staff ID</th>
												<th>Email</th>
												<th></th>
												<th></th>
											</tr-->
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

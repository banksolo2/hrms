<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.cust.hrms.dao.*"%>
<%@ page import="com.cust.hrms.models.*" %>
<%@ page import="java.sql.*"%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>HRMS | Edit Employee Role</title>
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
							<h1 class="m-0 text-dark">Edit Employee Role</h1>
						</div>
						<!-- /.col -->
						<div class="col-sm-6">
							<ol class="breadcrumb float-sm-right">
								<li class="breadcrumb-item"><a href="allEmployeeRoles.jsp">All Employee Roles</a></li>
								<li class="breadcrumb-item active">Edit Employee Role</li>
							</ol>
						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->
				</div>
				<!-- /.container-fluid -->
			</div>
			<!-- /.content-header -->

			<form action="editEmployeeRole" method="post" id="editEmployeeRole">
				<!-- Main content -->
				<section class="content">
					<div class="container-fluid">
						<!-- SELECT2 EXAMPLE -->
						<div class="card card-default">
							<div class="card-header">
							<%
							String message = (String)session.getAttribute("message");
							%>
								<h3 class="card-title">
									<%
										if(message != null)
											out.println(message);
									%>
								</h3>
							<%
							session.setAttribute("message", null);
							%>

								<div class="card-tools">
									<button type="button" class="btn btn-tool"
										data-card-widget="collapse">
										<i class="fas fa-minus"></i>
									</button>
									<button type="button" class="btn btn-tool"
										data-card-widget="remove">
										<i class="fas fa-times"></i>
									</button>
								</div>
							</div>
							<!-- /.card-header -->
							<div class="card-body">
								<div class="row">
								<%
								String errorMessage = (String) session.getAttribute("error");
								if(errorMessage != null){
								%>
									<!-- /.col -->
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
									<div class="col-md-12">
										<div class="form-group">
											<label>Employee</label>
											<%
											int employeeRoleId = Integer.parseInt(request.getParameter("employeeRoleId"));
											EmployeeRoleDao erd = new EmployeeRoleDao();
											EmployeeRole er = erd.getEmployeeRoleById(employeeRoleId);
											EmployeeDao ed = new EmployeeDao();
											
											%>
											<input type="hidden" value="<%=er.getEmployeeRoleId() %>" name="employeeRoleId" />
											<select class="form-control select2" style="width: 100%;" name="employeeId">
												<option selected="selected" value="<%=er.getEmployeeId() %>"><%=ed.getEmployeeName(er.getEmployeeId()).toUpperCase() %></option>
												<%
													
													ResultSet rs = ed.getAllEmployeeOnUpdate(er.getEmployeeId());
													String fullName = null;
													while(rs.next()){
													fullName = rs.getString("first_name")+" "+rs.getString("middle_name")+" "+rs.getString("last_name")+" ("+rs.getString("staff_id")+")";
												%>
												<option value="<%=rs.getInt("employee_id") %>"><%=fullName.toUpperCase() %></option>
												<%
													}
												%>
											</select>
										</div>
										<div class="form-group">
											<label>Role</label>
											<%
											RoleDao rd = new RoleDao();
											%>
											<select class="form-control select2" style="width: 100%;" name="roleId">
												<option selected="selected" value="<%=er.getRoleId() %>"><%=rd.getRoleName(er.getRoleId()).toUpperCase() %></option>
												<%
											
												rs = rd.getAllRolesOnUpdate(er.getRoleId());
												while(rs.next()){
												%>
												<option value="<%=rs.getInt("role_id") %>"><%=rs.getString("name").toUpperCase() %></option>
												<%
												}
												%>
											</select>
										</div>
									</div>
									<!-- /.col -->

								</div>
								<!-- /.row -->
							</div>
							<!-- /.card-body -->
							<div class="card-footer">
			                  <button type="submit" class="btn btn-primary">Save</button>
			                  <a class="btn btn-info" href="allEmployeeRoles.jsp">Go Back</a>
			                </div>
						</div>
						<!-- /.card -->
					</div>
					<!-- /.container-fluid -->
				</section>
				<!-- /.content -->
			</form>
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

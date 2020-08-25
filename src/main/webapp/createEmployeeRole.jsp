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
							<h1 class="m-0 text-dark">Create Employee Role</h1>
						</div>
						<!-- /.col -->
						<div class="col-sm-6">
							<ol class="breadcrumb float-sm-right">
								<li class="breadcrumb-item"><a href="#">Admin</a></li>
								<li class="breadcrumb-item active">Create Employee Role</li>
							</ol>
						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->
				</div>
				<!-- /.container-fluid -->
			</div>
			<!-- /.content-header -->

			<form action="createEmployeeRole" method="post">
				<!-- Main content -->
				<section class="content">
					<div class="container-fluid">
						<!-- SELECT2 EXAMPLE -->
						<div class="card card-default">
							<div class="card-header">
							
								<h3 class="card-title">
									
								</h3>
							

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
								String successMessage = (String) session.getAttribute("success");
								if(successMessage != null){
								%>
									<!-- /.col -->
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
											EmployeeDao ed = new EmployeeDao();
											String employee = request.getParameter("employeeId");
											int employeeId = (employee == null) ? 0 : Integer.parseInt(employee);
											%>
											<select class="form-control select2" style="width: 100%;" name="employeeId">
												<% if(employeeId == 0){ %>
												<option selected="selected" value="0">SELECT EMPLOYEE</option>
												<% } else{ %>
												<option selected="selected" value="<%=employeeId %>"><%=ed.getEmployeeName(employeeId).toUpperCase() %></option>
												<%
												}
													ResultSet rs = ed.getAllEmployeeOnUpdate(employeeId);
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
											String role = request.getParameter("roleId");
											int roleId = (role == null) ? 0 : Integer.parseInt(role);
											%>
											<select class="form-control select2" style="width: 100%;" name="roleId">
												<% if(roleId == 0){ %>
												<option selected="selected" value="0">SELECT ROLE</option>
												<% }else{ %>
												<option selected="selected" value="<%=roleId %>"><%=rd.getRoleName(roleId).toUpperCase() %></option>
												<%
												}
												rs = rd.getAllRolesOnUpdate(roleId);
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
			                  <button type="submit" class="btn btn-primary">Submit</button>
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

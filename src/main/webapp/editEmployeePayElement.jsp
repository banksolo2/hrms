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
  <title>HRMS | Edit Employee Pay Element</title>
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
	session.setAttribute("page", "admin_payroll_setup");
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
							<h1 class="m-0 text-dark">Edit Employee Pay Element</h1>
						</div>
						<!-- /.col -->
						<div class="col-sm-6">
							<ol class="breadcrumb float-sm-right">
								<li class="breadcrumb-item"><a href="#">Admin</a></li>
								<li class="breadcrumb-item"><a href="adminPayrollSetup.jsp">Payroll Setup</a></li>
								<li class="breadcrumb-item"><a href="allEmployeePayElementReport.jsp">All Employees Pay Elements</a></li>
								<li class="breadcrumb-item active">Edit Employee Pay Element</li>
							</ol>
						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->
				</div>
				<!-- /.container-fluid -->
			</div>
			<!-- /.content-header -->

			<form action="editEmployeePayElement" method="post" id="editEmployeePayElement">
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
									<div class="col-md-12">
										<div class="form-group">
											<%
											int employeePayElementId = Integer.parseInt(request.getParameter("employeePayElementId"));
											EmployeeDao ed = new EmployeeDao();
											PayElementDao ped = new PayElementDao();
											EmployeePayElementDao eped = new EmployeePayElementDao();
											EmployeePayElement epe = eped.getEmployeePayElementById(employeePayElementId);
											LevelDao ld = new LevelDao();
											ResultSet rs = ld.getAllLevelsOnUpdate(epe.getLevelId());
											%>
											<input type="hidden" name="employeePayElementId" value="<%=epe.getEmployeePayElementId() %>" >
											<label>Employee</label>
											<input type="text" class="form-control" readonly="readonly" value="<%=ed.getEmployeeName(epe.getEmployeeId()).toUpperCase() %>">
										</div>
										<div class="form-group">
											<label>Level</label>
											<input type="text" class="form-control" readonly="readonly" value="<%=ld.getLevelName(epe.getLevelId()).toUpperCase() %>">
										</div>
										<div class="form-group">
											<label>Pay Element</label>
											<input type="text" class="form-control" readonly="readonly" value="<%=ped.getName(epe.getPayElementId()).toUpperCase() %>">
										</div>
										<div class="form-group">
											<label>Amount</label>
											<input type="text" class="form-control" value="<%=epe.getAmount() %>" name="amount">
										</div>										
									</div>
									<!-- /.col -->

								</div>
								<!-- /.row -->
							</div>
							<!-- /.card-body -->
							<div class="card-footer">
			                  <button type="submit" class="btn btn-primary">Save</button>
			                  <a href="allEmployeePayElementReport.jsp" class="btn btn-info">Go Back</a>
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

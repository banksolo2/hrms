<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.cust.hrms.dao.*"%>
<%@ page import="com.cust.hrms.models.*"%>
<%@ page import="java.sql.*"%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>HRMS | Edit Payroll Role Level</title>
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
	if(session.getAttribute("isSupervisor") != null || session.getAttribute("isSuperAdmin") != null){
		isHrAdmin = (boolean) session.getAttribute("isHrAdmin");
		isSuperAdmin = (boolean) session.getAttribute("isSuperAdmin");
		isGlobalOfficer = (boolean) session.getAttribute("isGlobalOfficer");
		if(isSuperAdmin == false && isGlobalOfficer == false){
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
							<h1 class="m-0 text-dark">Edit Payroll Role Level</h1>
						</div>
						<!-- /.col -->
						<div class="col-sm-6">
							<ol class="breadcrumb float-sm-right">
								<li class="breadcrumb-item"><a href="#">Admin</a></li>
								<li class="breadcrumb-item"><a href="adminPayrollSetup.jsp">Payroll Setup</a></li>
								<li class="breadcrumb-item"><a href="viewPayrollRoleLevel.jsp">View Payroll Role Levels</a></li>
								<li class="breadcrumb-item active">Edit Payroll Role Level</li>
							</ol>
						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->
				</div>
				<!-- /.container-fluid -->
			</div>
			<!-- /.content-header -->

			<form action="editPayrollRoleLevel" method="post" id="createPayrollRoleLevel">
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
											<label>Role</label>
											<%
											String payrollRoleLevel = request.getParameter("payrollRoleLevelId");
											int payrollRoleLevelId = (payrollRoleLevel != null) ? Integer.parseInt(payrollRoleLevel) : (int)session.getAttribute("payrollRoleLevelId");
											session.setAttribute("payrollRoleLevelId", null);
											PayrollRoleLevelDao prld = new PayrollRoleLevelDao();
											PayrollRoleNameDao prnd = new PayrollRoleNameDao();
											PayrollRoleLevel prl = prld.getPayrollRoleLevelById(payrollRoleLevelId);
											LevelDao ld = new LevelDao();
											session.setAttribute("payrollRoleNameId", prl.getPayrollRoleNameId());
											%>
											<input type="hidden" name="payrollRoleLevelId" value="<%=prl.getPayrollRoleLevelId() %>">
											<select class="form-control select2" style="width: 100%;" name="payrollRoleNameId">
												<option selected="selected" value="<%=prl.getPayrollRoleNameId() %>"><%=prnd.getName(prl.getPayrollRoleNameId()).toUpperCase() %></option>	
											</select>
										</div>
										<div class="form-group">
											<label>Level</label>
											<select class="form-control select2" style="width: 100%;" name="levelId">
												<option selected="selected" value="<%=prl.getLevelId() %>"><%=ld.getLevelName(prl.getLevelId()).toUpperCase() %></option>
											<%
											ResultSet rs = ld.getAllLevelsOnUpdate(prl.getLevelId());
											while(rs.next()){
											%>
												<option value="<%=rs.getInt("level_id") %>"><%=rs.getString("name").toUpperCase() %></option>
											<%
											}
											rs.close();
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
			                  <button type="submit" class="btn btn-primary" id="save" onsubmit="disableSaveButton()">Save</button>
			                  <a href="viewPayrollRoleLevel.jsp" class="btn btn-info">Go Back</a>
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

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
  <title>HRMS | Employee Pay Elements</title>
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
							<h1 class="m-0 text-dark">Payroll Role Levels</h1>
						</div>
						<!-- /.col -->
						<div class="col-sm-6">
							<ol class="breadcrumb float-sm-right">
								<li class="breadcrumb-item"><a href="#">Admin</a></li>
								<li class="breadcrumb-item"><a href="adminPayrollSetup.jsp">Payroll Setup</a></li>
								<li class="breadcrumb-item"><a href="allPayrollRoleLevelReport.jsp">All Payroll Role Levels</a></li>
								<li class="breadcrumb-item active">Payroll Role Levels</li>
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
						
					</div>
				</div>
				<!-- /.container-fluid -->
			</section>
			<!-- /.content -->
			<section class="content">
		      <div class="container-fluid">
		        <div class="row">
		          <div class="col-12">
		            <!-- Main content -->
		            <div class="invoice p-3 mb-3">
		              <!-- title row -->
		              <div class="row">
		                <div class="col-12">
		                <%
		                String payrollRoleName = request.getParameter("payrollRoleNameId");
		                int payrollRoleNameId = (payrollRoleName != null) ? Integer.parseInt(payrollRoleName) : (int)session.getAttribute("payrollRoleNameId");
		                PayrollRoleNameDao prnd = new PayrollRoleNameDao();
		                PayrollRoleLevelDao prld = new PayrollRoleLevelDao();
		                ResultSet rs = prld.getAllPayrollRoleLevelReport(payrollRoleNameId);
		                LevelDao ld = new LevelDao();
		                session.setAttribute("payrollRoleNameId", null);
		                String payrollRole = prnd.getName(payrollRoleNameId);
		                
		                %>
		                  <h4>
		                    <i class="fas fa-user"></i> &nbsp;&nbsp;<%=payrollRole %></h4>
		                  
		                  
		                </div>
		                <!-- /.col -->
		              </div>
		              <!-- info row -->
		              <div class="row invoice-info">
		                
		                <!-- /.col -->
		              </div>
		              <!-- /.row -->
		
		              <!-- Table row -->
		              <div class="row">
		                <div class="col-12 table-responsive">
		                  <table class="table table-striped">
		                    <thead>
		                    <tr>
		                      <th>S/N</th>
		                      <th>Level</th>
		                      <th></th>
		                      <th></th>
		                      <th>
		                      	<form method="post" action="createPayrollRoleLevel.jsp">
				                  	<input type="hidden" name="payrollRoleNameId" value="<%=payrollRoleNameId %>" >
				                  	<button type="submit" class="btn btn-primary"><i class="fas fa-plus"></i> Add Level</button>
				                  </form>
		                      </th>
		                    </tr>
		                    </thead>
		                    <tbody>
		                    <%
		                    int rows = 0;
		                    while(rs.next()){
		                    	rows++;
		                    %>
		                    <tr>
		                      <td><%=rows %></td>
		                      <td><%=ld.getLevelName(rs.getInt("level_id")) %></td>
		                      <td>
		                      	<form action="editPayrollRoleLevel.jsp" method="post">
								<input type="hidden" name="payrollRoleLevelId" value="<%=rs.getInt("payroll_role_level_id") %>" />
								<button type="submit" class="btn btn-primary"><i class="fas fa-pencil-alt"></i> Edit</button>
								</form>
		                      </td>
		                      <td>
								<form action="deletePayrollRoleLevel" method="post">
								<input type="hidden" name="payrollRoleLevelId" value="<%=rs.getInt("payroll_role_level_id") %>" />
								<button type="submit" class="btn btn-danger" id="save" onsubmit="disableSaveButton()"><i class="fas fa-trash"></i> Delete</button>
								</form>
							</td>
							<td></td>
		                    </tr>
		                    <%
		                    
		                    }
		                    
		                    %>
		                    </tbody>
		                  </table>
		                </div>
		                <!-- /.col -->
		              </div>
		              <!-- /.row -->
		            </div>
		            <!-- /.invoice -->
		          </div><!-- /.col -->
		        </div><!-- /.row -->
		      </div><!-- /.container-fluid -->
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

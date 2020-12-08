<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.cust.hrms.dao.*"%>
<%@ page import="com.cust.hrms.models.*" %>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.*" %>

<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>HRMS | Create Leave Plan</title>
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
	session.setAttribute("page", "employee_leave_plan_setup");
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
							<h1 class="m-0 text-dark">Create Leave Plan</h1>
						</div>
						<!-- /.col -->
						<div class="col-sm-6">
							<ol class="breadcrumb float-sm-right">
								<li class="breadcrumb-item"><a href="#">Leave Plan</a></li>
								<li class="breadcrumb-item"><a href="employeeLeavePlanSetup.jsp">Employee Setup</a></li>
								<li class="breadcrumb-item active">Create Leave Plan</li>
							</ol>
						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->
				</div>
				<!-- /.container-fluid -->
			</div>
			<!-- /.content-header -->

			<form action="createLeavePlan" method="post" id="createLeavePlan">
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
								session.setAttribute("error", null);
								DepartmentHeadDao dhd = new DepartmentHeadDao();
								EmployeeDao ed = new EmployeeDao();
								session.setAttribute("error", null);
								int employeeId = 0;
								if(session.getAttribute("email") != null){
									employeeId = (int) session.getAttribute("employeeId");
								}
								int departmentId = ed.getEmployeeDepartmentId(employeeId);
								int departmentHeadId = dhd.getDepartmentHeadId(departmentId);
								int levelId = ed.getEmployeeLevelId(employeeId);
								LevelDao ld = new LevelDao();
								int leaveDays = ld.getLevelLeaveDays(levelId);
								LeavePlanDao lpd = new LeavePlanDao();
								LeavePlanStatusDao lpsd = new LeavePlanStatusDao();
							 	String currentYear  = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
								int leavePlanUsedDays = lpd.getEmployeeLeavePlanDaysByStatus(employeeId, lpsd.getLeavePlanStatusId("approved"), currentYear);
								int leavePlanDaysLeft = leaveDays - leavePlanUsedDays;
							         %>
							         <input type="hidden" name="employeeId" value="<%=employeeId %>" />
									<input type="hidden" name="departmentId" value="<%=departmentId %>" />
									<input type="hidden" name="departmentHeadId" value="<%=departmentHeadId %>" />
									<div class="col-md-4">
										<div class="form-group">
											<label>No Of Leave Plan Days</label>
											<input type="text" readonly="readonly" class="form-control" name="noOfLeavePlanDays" value="<%=leaveDays %>" />
										</div>
									</div>
									<div class="col-md-4">
										<div class="form-group">
											<label>No Of Leave Plan Days Used</label>
											<input type="text" readonly="readonly" class="form-control" name="noOfLeavePlanDaysUsed" value="<%=leavePlanUsedDays %>" />
										</div>
									</div>
									<div class="col-md-4">
										<div class="form-group">
											<label>No Of Leave Plan Days Left</label>
											<input type="text" readonly="readonly" class="form-control" name="noOfLeavePlanDaysLeft" value="<%=leavePlanDaysLeft %>" />
										</div>
									</div>
									<div class="col-md-12">
										<!-- Date range -->
						                <div class="form-group">
						                  <label>Start Date to End Date</label>
						
						                  <div class="input-group">
						                    <div class="input-group-prepend">
						                      <span class="input-group-text">
						                        <i class="far fa-calendar-alt"></i>
						                      </span>
						                    </div>
						                    <input type="text" class="form-control float-right" id="reservation" name="dates">
						                  </div>
						                  <!-- /.input group -->
						                </div>
						                <!-- /.form group -->
						                <div class="form-group">
						                	<%
											String leavePlanStatus = request.getParameter("leavePlanStatusId");
						                	int leavePlanStatusId = (leavePlanStatus == null) ? 0 : Integer.parseInt(leavePlanStatus);
					                		ResultSet rs = lpsd.getEmployeeLeavePlanStatuesOnUpdate(leavePlanStatusId);
						                	%>
						                	<label>Save AS</label>
						                	<select class="form-control select2" style="width: 100%;" name="leavePlanStatusId">
						                		<%
						                		if(leavePlanStatusId == 0){
						                		%>
						                		<option selected="selected" value="">SELECT SAVE AS OPTION</option>
						                		<%
						                		}
						                		else{
						                		%>
						                		<option selected="selected" value="<%=leavePlanStatusId %>"><%=lpsd.getLeavePlanStatusName(leavePlanStatusId).toUpperCase() %></option>
						                		<%
						                		}
						                		while(rs.next()){
						                		%>
						                		<option value="<%=rs.getInt("leave_plan_status_id") %>"><%=rs.getString("name").toUpperCase() %></option>
						                		<%
						                		}
						                		%>
						                	</select>
						                </div>
										
										<div class="form-group">
											
											<label>Department Head</label> 
											<input type="text" name="departmentHead" class="form-control" readonly="readonly" 
											value="<%=ed.getEmployeeName(departmentHeadId) %>" />
										</div>
									</div>
									<!-- /.col -->

								</div>
								<!-- /.row -->
							</div>
							<!-- /.card-body -->
							<div class="card-footer">
			                  <button type="submit" class="btn btn-primary" id="save" onsubmit="disableSaveButton()">Submit</button>
			                  <!--<a class="btn btn-info" href="allStates.jsp">Go Back</a> -->
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

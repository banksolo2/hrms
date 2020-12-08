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
  <title>HRMS | Edit Leave Plan Sent For Correction</title>
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
							<h1 class="m-0 text-dark">Edit Leave Plan Sent For Correction</h1>
						</div>
						<!-- /.col -->
						<div class="col-sm-6">
							<ol class="breadcrumb float-sm-right">
								<li class="breadcrumb-item"><a href="allLeavePlanSentForCorrection.jsp">All Leave Plans Sent For Correction </a></li>
								<li class="breadcrumb-item active">Edit Leave Plan Sent For Correction</li>
							</ol>
						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->
				</div>
				<!-- /.container-fluid -->
			</div>
			<!-- /.content-header -->

			<form action="editLeavePlanSentForCorrection" method="post" id="editLeavePlanSentForCorrection">
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
								int leavePlanId = Integer.parseInt(request.getParameter("leavePlanId"));
								DateDao dd = new DateDao();
								LeavePlanDao lpd = new LeavePlanDao();
								LeavePlan lp = lpd.getLeavePlan(leavePlanId);
								int levelId = ed.getEmployeeLevelId(lp.getEmployeeId());
								LevelDao ld = new LevelDao();
								int leaveDays = ld.getLevelLeaveDays(levelId);
								LeavePlanStatusDao lpsd = new LeavePlanStatusDao();
							 	String currentYear  = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
								int leavePlanUsedDays = lpd.getEmployeeLeavePlanDaysByStatus(lp.getEmployeeId(), lpsd.getLeavePlanStatusId("approved"), currentYear);
								int leavePlanDaysLeft = leaveDays - leavePlanUsedDays;
							         %>
							         <input type="hidden" name="leavePlanId" value="<%=lp.getLeavePlanId() %>" >
							         <input type="hidden" name="employeeId" value="<%=lp.getEmployeeId() %>" />
									<input type="hidden" name="departmentId" value="<%=lp.getDepartmentId() %>" />
									<input type="hidden" name="departmentHeadId" value="<%=lp.getDepartmentHeadId() %>" />
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
											<%
											String dates = dd.joinStartDateAndEndDate(lp.getStartDate(), lp.getEndDate());
											%>
						                  <div class="input-group">
						                    <div class="input-group-prepend">
						                      <span class="input-group-text">
						                        <i class="far fa-calendar-alt"></i>
						                      </span>
						                    </div>
						                    <input type="text" class="form-control float-right" id="reservation" name="dates" value="<%=dates %>">
						                  </div>
						                  <!-- /.input group -->
						                </div>
						                <!-- /.form group -->
						                <div class="form-group">
						                	<label>Comment</label>
						                	<textarea rows="5" cols="50" class="form-control" name="comment"><%=lp.getComment() %></textarea>
						                </div>
										
										<div class="form-group">
											
											<label>Department Head</label> 
											<input type="text" name="departmentHead" class="form-control" readonly="readonly" 
											value="<%=ed.getEmployeeName(lp.getDepartmentHeadId()) %>" />
										</div>
									</div>
									<!-- /.col -->

								</div>
								<!-- /.row -->
							</div>
							<!-- /.card-body -->
							<div class="card-footer">
			                  <button type="submit" class="btn btn-primary" id="save" onsubmit="disableSaveButton()">Sent For Approval</button>
			                  <a class="btn btn-info" href="allLeavePlanSentForCorrection.jsp">Go Back</a>
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

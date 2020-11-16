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
  <title>HRMS | Initiate Leave Recall Back (With Pay)</title>
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
	session.setAttribute("parent", "leave");
	session.setAttribute("page", "supervisor_leave_setup");
	if(session.getAttribute("isSupervisor") != null || session.getAttribute("isSuperAdmin") != null){
		boolean isSupervisor = (boolean) session.getAttribute("isSupervisor");
		boolean isSuperAdmin = (boolean) session.getAttribute("isSuperAdmin");
		if(isSupervisor == false && isSuperAdmin == false){
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
							<h1 class="m-0 text-dark">Initiate Leave Recall Back (With Pay)</h1>
						</div>
						<!-- /.col -->
						<div class="col-sm-6">
							<ol class="breadcrumb float-sm-right">
								<li class="breadcrumb-item"><a href="#">Leave</a></li>
								<li class="breadcrumb-item"><a href="SupervisorLeaveSetup.jsp">Supervisor Leave Setup</a></li>
								<li class="breadcrumb-item active">Initiate Leave Recall Back (With Pay)</li>
							</ol>
						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->
				</div>
				<!-- /.container-fluid -->
			</div>
			<!-- /.content-header -->

			<form action="initiateRecallBack" method="post" id="initiateRecallBack">
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
								int leaveId = Integer.parseInt(request.getParameter("leaveId"));
								LeaveDao led = new LeaveDao();
								Leave l = led.getLeaveById(leaveId);
								EmployeeDao ed = new EmployeeDao();
								int levelId = ed.getEmployeeLevelId(l.getEmployeeId());
								LevelDao ld = new LevelDao();
								int leaveDays = ld.getLevelLeaveDays(levelId);
								LeaveStatusDao lsd = new LeaveStatusDao();
								LeaveTypeDao ltd = new LeaveTypeDao();
							 	String currentYear  = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
							 	int annualLeaveTypeId  = ltd.getLeaveTypeId("annual");
							 	ResultSet rs = ltd.getAllLeaveTypeOnUpdate(annualLeaveTypeId);
							         %>
							         <input type="hidden" name="annualId" id="annualId" value="<%=annualLeaveTypeId %>" >
							         <input type="hidden" name="annualDays" id="annualDays" value="<%=leaveDays %>">
							         <%
							         	int noOfLeaveDays = 0;
							         	int usedLeaveDays = led.getEmployeeApprovedLeaveTypeDays(l.getEmployeeId(), annualLeaveTypeId, "yes", currentYear );
							         	int leftLeaveDays = leaveDays - usedLeaveDays;
							         %> 
							         <input type="hidden" name="annualUsedDays" id="annualUsedDays" value="<%=usedLeaveDays %>">
							         <input type="hidden" name="annualLeftDays" id="annualLeftDays" value="<%=leftLeaveDays %>">
							         <%
							         	while(rs.next()){
							         		noOfLeaveDays = rs.getInt("days");
							         		usedLeaveDays = led.getEmployeeApprovedLeaveTypeDays(l.getEmployeeId(), rs.getInt("leave_type_id"), "yes", currentYear );
							         		leftLeaveDays = noOfLeaveDays - usedLeaveDays;
							         %>
							         <input type="hidden" name="<%=rs.getString("code")+"Id" %>" id="<%=rs.getString("code")+"Id" %>" value="<%=rs.getInt("leave_type_id") %>" >
							         <input type="hidden" name="<%=rs.getString("code")+"Days" %>" id="<%=rs.getString("code")+"Days" %>" value="<%=noOfLeaveDays %>" >
							         <input type="hidden" name="<%=rs.getString("code")+"UsedDays" %>" id="<%=rs.getString("code")+"UsedDays" %>" value="<%=usedLeaveDays %>" >
							         <input type="hidden" name="<%=rs.getString("code")+"LeftDays" %>" id="<%=rs.getString("code")+"LeftDays" %>" value="<%=leftLeaveDays %>" >
							         <%
							         	}
							         %>
							         <input type="hidden" name="leaveId" value="<%=l.getLeaveId() %>">
							         <input type="hidden" name="levelId" value="<%=levelId %>">
							         <input type="hidden" name="year" value="<%=currentYear %>">
							         <input type="hidden" name="employeeId" value="<%=l.getEmployeeId() %>" />
									<input type="hidden" name="departmentId" value="<%=l.getDepartmentId() %>" />
									<input type="hidden" name="supervisorId" value="<%=l.getSupervisorId() %>" />
									<input type="hidden" name="withPay" value="<%=l.getWithPay() %>" />
									<!-- div class="col-md-4">
									<%
									int days = led.getEmployeeLeaveDays(l.getEmployeeId(), l.getLeaveTypeId(), l.getWithPay());
									int usedDays = led.getEmployeeApprovedLeaveTypeDays(l.getEmployeeId(), l.getLeaveTypeId(), l.getWithPay(), currentYear);
									int balanceDays = days - usedDays;
									%>
										<<div class="form-group">
											<label>No Of Leave Type Days</label>
											<input type="text" readonly="readonly" class="form-control" name="noOfLeaveDays" id="noOfLeaveDays" value="<%=days %>" />
										</div>
									</div>
									<div class="col-md-4">
										<div class="form-group">
											<label>No Of Leave Type Days Used</label>
											<input type="text" readonly="readonly" class="form-control" name="noOfLeaveDaysUsed" id="noOfLeaveDaysUsed" value="<%=usedDays %>" />
										</div>
									</div>
									<div class="col-md-4">
										<div class="form-group">
											<label>No Of Leave Type Days Left</label>
											<input type="text" readonly="readonly" class="form-control" name="noOfLeaveDaysLeft" id="noOfLeaveDaysLeft" value="<%=balanceDays %>" />
										</div>
									</div -->
									<div class="col-md-12">
										<div class="form-group">
											<label>Employee</label>
											<input type="text" class="form-control" value="<%=ed.getEmployeeName(l.getEmployeeId()).toUpperCase() %>" readonly="readonly">
										</div>
										<div class="form-group">
											<label>Leave Type</label>
											<%
											LeaveType lt =ltd.getLeaveTypeById(l.getLeaveTypeId());
											%>
											
						                	<select class="form-control select2" style="width: 100%;" name="leaveTypeId" id="leaveTypeId">
						                	
						                	<option selected="selected" value="<%=lt.getLeaveTypeId() %>"><%=lt.getName().toUpperCase() %></option>
						                
						                	</select>
						                </div>
						               </div>
										<!-- Date range -->
										<%
										DateDao dd = new DateDao();
										%>
									<div class="col-md-3">
										<div class="form-group">
											<label>Start Date</label>
											<input type="text" readonly="readonly" class="form-control" name="startDate" value="<%=dd.changeFormatDate(l.getStartDate()) %>">
										</div>
									</div>
						            <div class="col-md-3">
						            	<div class="form-group">
						            		<label>Number Of Days</label>
						            		<input type="text" readonly="readonly" class="form-control" name="noOfDays" value="<%=l.getNoOfDays() %>">
						            	</div>
						            </div>
						            <div class="col-md-3">
						            	<div class="form-group">
						            		<label>End Date</label>
						            		<input type="text" readonly="readonly" class="form-control" name="endDate" value="<%=dd.changeFormatDate(l.getEndDate()) %>" >
						            	</div>
						            </div>
						            <div class="col-md-3">
						            	<div class="form-group">
						            		<label>Resumption Date</label>
						            		<input type="text" readonly="readonly" class="form-control" name="resumptionDate" value="<%=dd.changeFormatDate(l.getResumptionDate()) %>">
						            	</div>
						            </div>
						            <div class="col-md-12">
						                <div class="form-group">
						                	<label>Is this leave request inline with your leave Plan?</label>
						                	<select class="form-control select2" style="width: 100%;" name="inLineWithLeavePlan">
						                		<option selected="selected" value="<%=l.getInlineWithLeavePlan() %>"><%=l.getInlineWithLeavePlan().toUpperCase() %></option>
						                	</select>
						                </div>
						                  <div class="form-group">
							                  <label>Staff To be Notified:</label>
							                 
							                  <select class="select2" multiple="multiple" data-placeholder="SELECT STAFF TO BE NOTIFIED" style="width: 100%;" name="staffToNotify">
							                    <%
							                    String fullName = null;
							                    int employeesId[] = ed.getEmployeesId(l.getStaffToNotify());
							                    for(int x : employeesId){
							                    %>
							                    <option selected="selected" value="<%=x %>"><%=ed.getEmployeeName(x).toUpperCase() %></option>
							                    <%
							                    }
							                    %>
							                  </select>
							                </div>							                
							           </div>
							                <div class="col-md-6">
							                <div class="form-group">
							                	<label>Primary Relief Officer</label>
							                	<input type="text" class="form-control" name="primaryReliefOfficeId" value="<%=ed.getEmployeeName(l.getPrimaryReliefOfficeId()).toUpperCase() %>" readonly="readonly">
							                </div>
							                </div>
							                <div class="col-md-6">
							                <div class="form-group">
							                	<label>Secondary Relief Officer</label>
							                	<%
							                		String secondaryReliefOfficer = (l.getSecondaryReliefOfficeId() == 0) ? "" : ed.getEmployeeName(l.getSecondaryReliefOfficeId());
							                	%>
							                	<input type="text" class="form-control" readonly="readonly" name="secondaryReliefOfficeId" value="<%=secondaryReliefOfficer.toUpperCase() %>">
							                </div>
							                </div>
						                <div class="col-md-12">
						                <!--<div class="form-group">
						                	<label>Save As</label>
						                	<%
						            
						                	
						                	int leaveStatusId = l.getLeaveStatusId();
						                	rs = lsd.getSupervisorSaveAsOption(l.getLeaveTypeId(), l.getLeaveStatusId(), l.getWithPay());
						                	%>
						                	<select class="form-control select2" style="width: 100%;" name="leaveStatusId">
						                	
						                	<option selected="selected" value="">SELECT SAVE AS OPTION</option>
						                	
						                	<%
						                	
						                	while(rs.next()){
						                	%>
						                	<option value="<%=rs.getInt("leave_status_id")%>"><%=rs.getString("name").toUpperCase() %></option>
						                	<%} %>
						                	</select>
						                </div>-->
						                <div class="form-group">
						                	<label>Comment</label>
						                	<textarea rows="5" cols="60" class="form-control" name="comment"></textarea>
						                </div>
										<div class="form-group">
											
											<label>Supervisor</label> 
											<input type="text" name="leaveSupervisor" class="form-control" readonly="readonly" 
											value="<%=ed.getEmployeeName(l.getSupervisorId()) %>" />
										</div>
									</div>
									<!-- /.col -->

								</div>
								<!-- /.row -->
							</div>
							<!-- /.card-body -->
							<div class="card-footer">
			                  <button type="submit" class="btn btn-primary">Initiate Recall Back</button>
			                  <a class="btn btn-info" href="supervisorApprovedLeaveRequestReportWithPay.jsp">Go Back</a> 
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

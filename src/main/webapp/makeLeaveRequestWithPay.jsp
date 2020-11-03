<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.cust.hrms.dao.*"%>
<%@ page import="com.cust.hrms.models.*" %>
<%@ page import="com.cust.hrms.statues.*" %>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.*" %>

<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>HRMS | Make Leave Request (With Pay)</title>
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
	session.setAttribute("page", "employee_leave_setup");
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
							<h1 class="m-0 text-dark">Make Leave Request (With Pay)</h1>
						</div>
						<!-- /.col -->
						<div class="col-sm-6">
							<ol class="breadcrumb float-sm-right">
								<li class="breadcrumb-item"><a href="#">Leave</a></li>
								<li class="breadcrumb-item"><a href="employeeLeaveSetup.jsp">Employee Leave Setup</a></li>
								<li class="breadcrumb-item active">Make Leave Request (With Pay)</li>
							</ol>
						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->
				</div>
				<!-- /.container-fluid -->
			</div>
			<!-- /.content-header -->

			<form action="makeLeaveRequestWithPay" method="post" id="makeLeaveRequestWithPay">
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
								EmployeeDao ed = new EmployeeDao();
								session.setAttribute("error", null);
								int employeeId = 0;
								if(session.getAttribute("email") != null){
									employeeId = (int) session.getAttribute("employeeId");
								}
								int departmentId = ed.getEmployeeDepartmentId(employeeId);
								int levelId = ed.getEmployeeLevelId(employeeId);
								LevelDao ld = new LevelDao();
								LeaveDao led = new LeaveDao();
								int leaveDays = ld.getLevelLeaveDays(levelId);
								int supervisorId = ed.getEmployeeLeaveSupervisorId(employeeId);
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
							         	int usedLeaveDays = led.getEmployeeApprovedLeaveTypeDays(employeeId, annualLeaveTypeId, "yes", currentYear );
							         	int leftLeaveDays = leaveDays - usedLeaveDays;
							         %> 
							         <input type="hidden" name="annualUsedDays" id="annualUsedDays" value="<%=usedLeaveDays %>">
							         <input type="hidden" name="annualLeftDays" id="annualLeftDays" value="<%=leftLeaveDays %>">
							         <%
							         	while(rs.next()){
							         		noOfLeaveDays = rs.getInt("days");
							         		usedLeaveDays = led.getEmployeeApprovedLeaveTypeDays(employeeId, rs.getInt("leave_type_id"), "yes", currentYear );
							         		leftLeaveDays = noOfLeaveDays - usedLeaveDays;
							         %>
							         <input type="hidden" name="<%=rs.getString("code")+"Id" %>" id="<%=rs.getString("code")+"Id" %>" value="<%=rs.getInt("leave_type_id") %>" >
							         <input type="hidden" name="<%=rs.getString("code")+"Days" %>" id="<%=rs.getString("code")+"Days" %>" value="<%=noOfLeaveDays %>" >
							         <input type="hidden" name="<%=rs.getString("code")+"UsedDays" %>" id="<%=rs.getString("code")+"UsedDays" %>" value="<%=usedLeaveDays %>" >
							         <input type="hidden" name="<%=rs.getString("code")+"LeftDays" %>" id="<%=rs.getString("code")+"LeftDays" %>" value="<%=leftLeaveDays %>" >
							         <%
							         	}
							         %>
							         <input type="hidden" name="levelId" value="<%=levelId %>">
							         <input type="hidden" name="year" value="<%=currentYear %>">
							         <input type="hidden" name="employeeId" value="<%=employeeId %>" />
									<input type="hidden" name="departmentId" value="<%=departmentId %>" />
									<input type="hidden" name="supervisorId" value="<%=supervisorId %>" />
									<input type="hidden" name="withPay" value="yes" />
									<div class="col-md-4">
									<script>
									

									</script>
									<%
									String leaveTypeCode = request.getParameter("leaveTypeId");
									int leaveTypeId = (leaveTypeCode == null) ? 0 : ltd.getLeaveTypeId(leaveTypeCode);
									int days = led.getEmployeeLeaveDays(employeeId, leaveTypeId, "yes");
									int usedDays = led.getEmployeeApprovedLeaveTypeDays(employeeId, leaveTypeId, "yes", currentYear);
									int balanceDays = days - usedDays;
									%>
										<div class="form-group">
											<label>No Of Leave Type Days</label>
											<input type="text" readonly="readonly" class="form-control" name="noOfLeaveDays" id="noOfLeaveDays" value="0" />
										</div>
									</div>
									<div class="col-md-4">
										<div class="form-group">
											<label>No Of Leave Type Days Used</label>
											<input type="text" readonly="readonly" class="form-control" name="noOfLeaveDaysUsed" id="noOfLeaveDaysUsed" value="0" />
										</div>
									</div>
									<div class="col-md-4">
										<div class="form-group">
											<label>No Of Leave Type Days Left</label>
											<input type="text" readonly="readonly" class="form-control" name="noOfLeaveDaysLeft" id="noOfLeaveDaysLeft" value="0" />
										</div>
									</div>
									<div class="col-md-12">
										<div class="form-group">
											<label>Leave Type</label>
											<%
											rs = ltd.getAllLeaveTypeOnUpdate(leaveTypeId);
											%>
											
						                	<select class="form-control select2" style="width: 100%;" name="leaveTypeId" id="leaveTypeId" onchange="changeLeaveType(); hideShowInLine()">
						                	<%
						                	if(leaveTypeCode == null){
						                	%>
						                	<option selected="selected" value="">SELECT LEAVE TYPE</option>
						                	<%
						                	}
						                	else{
						                	%>
						                	<option selected="selected" value="<%=leaveTypeCode %>"><%=ltd.getLeaveTypeName(ltd.getLeaveTypeId(leaveTypeCode)).toUpperCase() %></option>
						                	<%
						                	}
						                	while(rs.next()){
						                	%>
						                	<option value="<%=rs.getString("code") %>"><%=rs.getString("name").toUpperCase() %></option>
						                	<%
						                	}
						                	%>
						                	</select>
						                </div>
										<!-- Date range -->
						                <div class="form-group">
						                  <label>Start Date to End Date</label>
										<%
										String dates = request.getParameter("dates");
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
						                <div class="form-group" id="inline">
						                	<label>Is this leave request inline with your leave Plan?</label>
						                	<select class="form-control select2" style="width: 100%;" name="inLineWithLeavePlan">
						                		<option selected="selected" value="">SELECT OPTION</option>
						                		<option value="yes">YES</option>
						                		<option value="no">NO</option>
						                	</select>
						                </div>
						                 <div class="form-group">
							                  <label>Staff To be Notified:</label>
							                  <%
							                  String staffToNotify = request.getParameter("staffToNotify");
							                  rs = ed.getAllEmployee();
							                  %>
							                  <select class="select2" multiple="multiple" data-placeholder="SELECT STAFF TO BE NOTIFIED" style="width: 100%;" name="staffToNotify">
							                  	
							                    <%
							                    String fullName = null;
							                    while(rs.next()){
							                    	fullName = rs.getString("first_name")+" "+rs.getString("middle_name")+" "+rs.getString("last_name")+" ("+rs.getString("staff_id")+")";
							                    %>
							                    <option value="<%=rs.getInt("employee_id") %>"><%=fullName.toUpperCase() %></option>
							                    <%} %>
							                  </select>
							                </div>
							                </div>
							                <div class="col-md-6">
							                <div class="form-group">
							                	<label>Primary Relief Officer</label>
							                	<%
							                	String primaryReliefOffice = request.getParameter("primaryReliefOfficeId");
							                	int primaryReliefOfficeId = (primaryReliefOffice == null) ? 0 : Integer.parseInt(primaryReliefOffice);
							                	rs = ed.getAllEmployeeOnUpdate(primaryReliefOfficeId);
							                	%>
							                	<select class="form-control select2" style="width: 100%;" name="primaryReliefOfficeId">
												<%
													if(primaryReliefOfficeId == 0){
												%>
												<option selected="selected" value="">SELECT PRIMARY
													RELIEF OFFICER</option>
												<%
													} else {
												%>
												<option selected="selected" value="<%=primaryReliefOfficeId %>"><%=ed.getEmployeeName(primaryReliefOfficeId) %></option>
							                	<%
							                	}
							                	while(rs.next()){
							                		fullName = rs.getString("first_name")+" "+rs.getString("middle_name")+" "+rs.getString("last_name")+" ("+rs.getString("staff_id")+")";
							                	%>
							                	<option value="<%=rs.getInt("employee_id")%>"><%=fullName.toUpperCase() %></option>
							                	<%
							                	}
							                	%>
							                	</select>
							                </div>
							                </div>
							                <div class="col-md-6">
							                <div class="form-group">
							                	<label>Secondary Relief Officer</label>
							                	<%
							                	String secondaryReliefOffice = request.getParameter("secondaryReliefOfficeId");
							                	int secondaryReliefOfficeId = (secondaryReliefOffice == null || secondaryReliefOffice.equals("")) ? 0 : Integer.parseInt(secondaryReliefOffice);
							                	%>
							                	<select class="form-control select2" style="width: 100%;" name="secondaryReliefOfficeId">
												<% if(secondaryReliefOfficeId == 0){ %>
													<option selected="selected" value="">SELECT SECONDARY RELIEF OFFICER</option>
												<% }else{ %>
													<option selected="selected" value="<%=secondaryReliefOfficeId %>"><%=ed.getEmployeeName(secondaryReliefOfficeId) %></option>
												<%
												} 
												rs = ed.getAllEmployeeOnUpdate(secondaryReliefOfficeId);
												while(rs.next()){
													fullName = rs.getString("first_name")+" "+rs.getString("middle_name")+" "+rs.getString("last_name")+" ("+rs.getString("staff_id")+")";
												%>
													<option value="<%=rs.getInt("employee_id") %>"><%=fullName %></option>
												<%} %>
							                	</select>
							                </div>
							                </div>
						                <div class="col-md-12">
						                <div class="form-group">
						                	<label>Save As</label>
						                	<%
						            
						                	String leaveStatus = request.getParameter("leaveStatusId");
						                	int leaveStatusId = (leaveStatus == null) ? 0 : Integer.parseInt(leaveStatus);
						                	LeaveStatues les = new LeaveStatues();
						                	rs = lsd.getEmployeeSaveAsOptionsOnUpdateById(leaveStatusId);
						                	%>
						                	<select class="form-control select2" style="width: 100%;" name="leaveStatusId">
						                	<%
						                	if(leaveStatusId == 0){
						                	%>
						                	<option selected="selected" value="">SELECT SAVE AS OPTION</option>
						                	<%
						                	}
						                	else{
						                	%>
						                	<option selected="selected" value="<%=leaveStatusId %>"><%=les.getStatusName(lsd.getName(leaveStatusId)).toUpperCase() %></option>
						                	<%
						                	}
						                	while(rs.next()){
						                	%>
						                	<option value="<%=rs.getInt("leave_status_id")%>"><%=les.getStatusName(rs.getString("name")).toUpperCase() %></option>
						                	<%} %>
						                	</select>
						                </div>
										<div class="form-group">
											<%
											String supervisorName = ed.getEmployeeName(supervisorId);
											if(supervisorName == null){
												supervisorName = "";
											}
											%>
											<label>Supervisor</label> 
											<input type="text" name="leaveSupervisor" class="form-control" readonly="readonly" 
											value="<%=supervisorName %>" />
										</div>
										
									</div>
									<!-- /.col -->

								</div>
								<!-- /.row -->
							</div>
							<!-- /.card-body -->
							<div class="card-footer">
			                  <button type="submit" class="btn btn-primary">Create Leave</button>
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

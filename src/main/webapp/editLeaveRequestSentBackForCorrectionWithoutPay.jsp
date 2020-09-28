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
  <title>HRMS | Edit Leave Request (Without Pay)</title>
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
							<h1 class="m-0 text-dark">Edit Leave Request (Without Pay)</h1>
						</div>
						<!-- /.col -->
						<div class="col-sm-6">
							<ol class="breadcrumb float-sm-right">
								<li class="breadcrumb-item"><a href="#">Leave</a></li>
								<li class="breadcrumb-item"><a href="employeeLeaveSetup.jsp">Employee Leave Setup</a></li>
								<li class="breadcrumb-item active">Edit Leave Request (Without Pay)</li>
							</ol>
						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->
				</div>
				<!-- /.container-fluid -->
			</div>
			<!-- /.content-header -->

			<form action="editSendBackForCorrectWithoutPay" method="post" id="editSendBackForCorrectWithPay">
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
							 	ResultSet rs = ltd.getAllLeaveTypeOnUpdate(0);
							         %>
							         <%
							         	int noOfLeaveDays = 0;
							         	int usedLeaveDays = 0;
							         	int leftLeaveDays = 0;
							        
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
									<div class="col-md-4">
									<%
									int days = led.getEmployeeLeaveDays(l.getEmployeeId(), l.getLeaveTypeId(), l.getWithPay());
									int usedDays = led.getEmployeeApprovedLeaveTypeDays(l.getEmployeeId(), l.getLeaveTypeId(), l.getWithPay(), currentYear);
									int balanceDays = days - usedDays;
									%>
										<div class="form-group">
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
									</div>
									<div class="col-md-12">
										<div class="form-group">
											<label>Leave Type</label>
											<%
											LeaveType lt =ltd.getLeaveTypeById(l.getLeaveTypeId());
											%>
											
						                	<select class="form-control select2" style="width: 100%;" name="leaveTypeId" id="leaveTypeId" onchange="changeLeaveType()">
						                	
						                	<option selected="selected" value="<%=lt.getCode() %>"><%=lt.getName().toUpperCase() %></option>
						                	
						                	</select>
						                </div>
										<!-- Date range -->
						                <div class="form-group">
						                  <label>Start Date to End Date</label>
										<%
										DateDao dd = new DateDao();
										%>
						                  <div class="input-group">
						                    <div class="input-group-prepend">
						                      <span class="input-group-text">
						                        <i class="far fa-calendar-alt"></i>
						                      </span>
						                    </div>
						                    <input type="text" class="form-control float-right" id="reservation" name="dates" value="<%=dd.joinStartDateAndEndDate(l.getStartDate(), l.getEndDate()) %>">
						                  </div>
						                  <!-- /.input group -->
						                </div>
						                <!-- /.form group -->
						                <!-- div class="form-group">
						                	<label>Is this leave request in line with your leave Plan?</label>
						                	<select class="form-control select2" style="width: 100%;" name="inLineWithLeavePlan">
						                		<option selected="selected" value="<%=l.getInlineWithLeavePlan() %>"><%=l.getInlineWithLeavePlan().toUpperCase() %></option>
						                		<option value="yes">YES</option>
						                		<option value="no">NO</option>
						                	</select>
						                </div>-->
						                 <!-- <div class="form-group">
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
							                    <option value="<%=rs.getString("email") %>"><%=fullName.toUpperCase() %></option>
							                    <%} %>
							                  </select>
							                </div>-->
							                </div>
							                <div class="col-md-6">
							                <div class="form-group">
							                	<label>Primary Relief Office</label>
							                	<%
							                	int primaryReliefOfficeId = l.getPrimaryReliefOfficeId(); 
							                	rs = ed.getAllEmployeeOnUpdate(primaryReliefOfficeId);
							                	%>
							                	<select class="form-control select2" style="width: 100%;" name="primaryReliefOfficeId">
												<%
													if(primaryReliefOfficeId == 0){
												%>
												<option selected="selected" value="">SELECT PRIMARY
													RELIEF OFFICE</option>
												<%
													} else {
												%>
												<option selected="selected" value="<%=primaryReliefOfficeId %>"><%=ed.getEmployeeName(primaryReliefOfficeId).toUpperCase() %></option>
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
							                	<label>Secondary Relief Office</label>
							                	<%
							                	int secondaryReliefOfficeId = l.getSecondaryReliefOfficeId();
							                	rs = ed.getAllEmployeeOnUpdate(secondaryReliefOfficeId);
							                	%>
							                	<select class="form-control select2" style="width: 100%;" name="secondaryReliefOfficeId">
												<%
													if(secondaryReliefOfficeId == 0){
												%>
												<option selected="selected" value="">SELECT SECONDARY
													RELIEF OFFICE</option>
												<%
													} else {
												%>
												<option selected="selected" value="<%=secondaryReliefOfficeId %>"><%=ed.getEmployeeName(secondaryReliefOfficeId).toUpperCase() %></option>
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
						                <div class="col-md-12">
						                <div class="form-group">
						                	<label>Save As</label>
						                	<%	
						                	LeaveStatus ls = lsd.getLeaveStatusByCode("sent_to_supervisor_for_approval");
						                	%>
						                	<select class="form-control select2" style="width: 100%;" name="leaveStatusId">
						                	<option selected="selected" value="<%=ls.getLeaveStatusId() %>"><%=ls.getName().toUpperCase() %></option>
						                	</select>
						                </div>
						                <div class="form-group">
						                	<label>Comment</label>
						                	<%
						                	String comment = l.getComment();
						                	comment = (comment == null) ? "" : comment;
						                	%>
						                	<textarea rows="5" cols="50" class="form-control" name="comment"><%=comment %></textarea>
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
			                  <button type="submit" class="btn btn-primary">Authorize</button>
			                  <a class="btn btn-info" href="employeeLeaveRequestSentBackForCorrectionWithoutPay.jsp">Go Back</a> 
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

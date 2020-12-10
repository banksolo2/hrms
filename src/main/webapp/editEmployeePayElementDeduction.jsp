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
  <title>HRMS | Edit Employee Pay Element Deduction</title>
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
							<h1 class="m-0 text-dark">Edit Employee Pay Element Deduction</h1>
						</div>
						<!-- /.col -->
						<div class="col-sm-6">
							<ol class="breadcrumb float-sm-right">
								<li class="breadcrumb-item"><a href="#">Admin</a></li>
								<li class="breadcrumb-item"><a href="adminPayrollSetup.jsp">Payroll Setup</a></li>
								<li class="breadcrumb-item"><a href="allEmployeesPayElementDeductionReport.jsp">All Employees Pay Element Deduction</a></li>
								<li class="breadcrumb-item active">Edit Employee Pay Element Deduction</li>
							</ol>
						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->
				</div>
				<!-- /.container-fluid -->
			</div>
			<!-- /.content-header -->

			<form action="editEmployeePayElementDeduction" method="post" id="createEmployeePayElementDeduction">
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
											EmployeePayElementDeductionDao epedd = new EmployeePayElementDeductionDao();
											int employeePayElementDeductionId = Integer.parseInt(request.getParameter("employeePayElementDeductionId"));
											EmployeePayElementDeduction eped = epedd.getEmployeePayElementDeductionById(employeePayElementDeductionId);
											EmployeeDao ed = new EmployeeDao();
											%>
											<input type="hidden" name="employeePayElementDeductionId" value="<%=eped.getEmployeePayElementDeductionId() %>">
											<select class="form-control select2" style="width: 100%;" name="employeeId">
												<% if(eped.getEmployeeId() == 0){ %>
												<option selected="selected" value="">SELECT EMPLOYEE</option>
												<% } else{ %>
												<option selected="selected" value="<%=eped.getEmployeeId() %>"><%=ed.getEmployeeName(eped.getEmployeeId()).toUpperCase() %></option>
												<%
												}
													ResultSet rs = ed.getAllEmployeeOnUpdate(eped.getEmployeeId());
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
											<label>Deduction Type</label>
											<%
											PayElementDeductionTypeDao pedtd = new PayElementDeductionTypeDao();
											int payElementDeductionTypeId = eped.getPayElementDeductionTypeId();
											rs = pedtd.getPayElementDeductionTypeOptions(payElementDeductionTypeId);
											%>
											<select class="form-control select2" style="width: 100%;" name="payElementDeductionTypeId">
											<%
											if(payElementDeductionTypeId == 0){
											%>
												<option selected="selected" value="">SELECT DEDUCTION TYPE</option>
											<%
											}
											else{
											%>
												<option selected="selected" value="<%=payElementDeductionTypeId %>"><%=pedtd.getName(payElementDeductionTypeId).toUpperCase() %></option>
											<%
											}
											while(rs.next()){
											%>
												<option value="<%=rs.getInt("pay_element_deduction_type_id") %>"><%=rs.getString("name").toUpperCase() %></option>
											<%
											}
											%>
											</select>
										</div>
										<div class="form-group">
											<label>Amount</label>
											<input type="text" class="form-control" name="amount" value="<%=eped.getAmount() %>">
										</div>
										<div class="form-group">
											<label>Month</label>
											<%
											MonthDao md = new MonthDao();
											String month = eped.getMonthNo();
											rs = md.getMonthOption(month);
											%>
											<select class="form-control select2" style="width: 100%;" name="month">
											<%
											if(month.equals("00")){
											%>
												<option selected="selected" value="">SELECT MONTH</option>	
											<%
											}
											else{
											%>
												<option selected="selected" value="<%=month %>"><%=md.getName(month).toUpperCase() %></option>
											<%
											}
											while(rs.next()){
											%>
												<option value="<%=rs.getString("no") %>"><%=rs.getString("name").toUpperCase() %></option>
											<%
											}
											%>
											</select>
										</div>
										<div class="form-group">
											<label>Year (YYYY)</label>
											
											<input type="number" value="<%=eped.getYear() %>" class="form-control" name="year" >
										</div>
									</div>
									<!-- /.col -->

								</div>
								<!-- /.row -->
							</div>
							<!-- /.card-body -->
							<div class="card-footer">
			                  <button type="submit" class="btn btn-primary" id="save" onsubmit="disableSaveButton()">Save</button>
			                  <a href="allEmployeesPayElementDeductionReport.jsp" class="btn btn-info">Go Back</a>
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

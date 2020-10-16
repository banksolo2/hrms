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
  <title>HRMS | Create Support Ticket (Employees)</title>
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
	session.setAttribute("parent", "support_ticket");
	session.setAttribute("page", "employee_support_ticket_setup");
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
							<h1 class="m-0 text-dark">Create Support Ticket (Employees)</h1>
						</div>
						<!-- /.col -->
						<div class="col-sm-6">
							<ol class="breadcrumb float-sm-right">
								<li class="breadcrumb-item"><a href="#">Support Ticket</a></li>
								<li class="breadcrumb-item"><a href="employeeSupportTicketSetup.jsp">Support Ticket Setup</a></li>
								<li class="breadcrumb-item active">Create Support Ticket (Employees)</li>
							</ol>
						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->
				</div>
				<!-- /.container-fluid -->
			</div>
			<!-- /.content-header -->

	<form action="createEmployeeSupportTicket" method="post" id="createEmployeeSupportTicket" enctype="multipart/form-data">
				<!-- form action="createEmployeeSupportTicket" method="post" id="createEmployeeSupportTicket"-->
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
											<label>Issue Type</label>
											<%
											String issueType= request.getParameter("issueTypeId");
											int issueTypeId = (issueType  == null ) ? 0 : Integer.parseInt(issueType);
											IssueTypeDao itd = new IssueTypeDao();
											ResultSet rs;
											%>
											<select class="form-control select2" style="width: 100%;" name="issueTypeId">
											<% if(issueTypeId == 0){ %>
											<option selected="selected" value="">SELECT ISSUE TYPE</option>
											<%}else{ %>
											<option selected="selected" value="<%=issueTypeId %>" ><%=itd.getIssueTypeName(issueTypeId).toUpperCase() %></option>
											<%
											}
											rs = itd.getIssueTypeOptions(issueTypeId);
											while(rs.next()){
											%>
											<option value="<%=rs.getInt("issue_type_id") %>"><%=rs.getString("name").toUpperCase() %></option>
											<%
											}
											rs.close();
											%>
											</select>
										</div>
										<div class="form-group">
											<label>Issue Description</label>
											<%
											String issueDescription = request.getParameter("issueDescription");
											issueDescription = (issueDescription == null) ? "" : issueDescription;
											 %>
											<textarea cols="50" rows="4" class="form-control" name="issueDescription"><%=issueDescription %></textarea>
										</div>
										<div class="form-group">
											<label>Employees</label>
											<select class="select2" multiple="multiple" data-placeholder="Select Employees" style="width: 100%;" name="employees">
											<%
											EmployeeDao ed = new EmployeeDao();
											rs = ed.getAllEmployee();
											String fullName = null;
											while(rs.next()){
												fullName = rs.getString("first_name")+" "+rs.getString("middle_name")+" "+rs.getString("last_name");
											%>
											<option value="<%=rs.getInt("employee_id")%>"><%=fullName %></option>
											<%} %>
											</select>
										</div>
										<div class="form-group">
											<label>Upload Issue</label>
											<input type="file" class="form-control" name="file">
										</div>
									</div>

								</div>
								<!-- /.row -->
							</div>
							<!-- /.card-body -->
							<div class="card-footer">
			                  <button type="submit" class="btn btn-primary">Create Ticket</button>
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

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.cust.hrms.dao.*"%>
<%@ page import="java.sql.*"%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>HRMS | All Resolved Support Tickets (Employees)</title>
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
							<h1 class="m-0 text-dark">All Resolved Support Tickets (Employees)</h1>
						</div>
						<!-- /.col -->
						<div class="col-sm-6">
							<ol class="breadcrumb float-sm-right">
								<li class="breadcrumb-item"><a href="#">Support Ticket</a></li>
								<li class="breadcrumb-item"><a href="employeeSupportTicketSetup.jsp">Support Ticket Setup</a></li>
								<li class="breadcrumb-item active">All Resolved Support Tickets (Employees)</li>
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
									%>
						<div class="col-12">
							<div class="card">
								<div class="card-header">
									<h3 class="card-title"></h3>
								</div>
								<!-- /.card-header -->
								<div class="card-body">
									<table id="example1" class="table table-bordered table-striped">
										<thead>
											<tr>
												<th>Ticket No</th>
												<th>Issue Report Date</th>
												<th>Issue Type</th>
												<th>Issue Description</th>
												<th>Resolved By</th>
												<th>Comment</th>
												<th></th>
											</tr>
										</thead>
										<tbody>
										<%
										int employeeId = 0;
										if(session.getAttribute("email") != null){
											employeeId = (int)session.getAttribute("employeeId");
										}
										IssueTypeDao itd = new IssueTypeDao();
										SupportTicketStatusDao stsd = new SupportTicketStatusDao();
										SupportTicketDao std = new SupportTicketDao();
										EmployeeDao ed = new EmployeeDao();
										ResultSet rs =  std.getEmployeeSupportTicketReport(employeeId, "employees", stsd.getSupportTicketStatusId("resolved"));
										DateDao dd = new DateDao();
										String comment = null;
										while(rs.next()){
											if(rs.getString("comment") != null){
												comment = rs.getString("comment");
											}else{
												comment = "";
											}
											
										%>
											<tr>
												<td><%=rs.getInt("support_ticket_id") %></td>
												<td><%=dd.changeFormatDate(rs.getDate("issue_report_date").toString()) %></td>
												<td><%=itd.getIssueTypeName(rs.getInt("issue_type_id")) %></td>
												<td><%=rs.getString("issue_description") %></td>
												<td><%=ed.getEmployeeName(rs.getInt("resolved_by")) %></td>
												<td><%=comment %> </td>
												<td>
													<form action="employeeEditSupportTicket.jsp" method="post">
													<input type="hidden" name="supportTicketId" value="<%=rs.getInt("support_ticket_id") %>" />
													<button type="submit" class="btn btn-primary"><i class="fas fa-pencil-alt"></i> Edit</button>
													</form>
												</td>
											</tr>
										<%
										}
										rs.close();
										%>
										</tbody>
										<tfoot>
											<!--<tr>
												<th>Department</th>
												<th>Date From</th>
												<th>Date To</th>
												<th>Created By</th>
												<th>Updated By</th>
												<th></th>
												<th></th>
											</tr>-->
										</tfoot>
									</table>
								</div>
								<!-- /.card-body -->
							</div>
							<!-- /.card -->
						</div>
					</div>
				</div>
				<!-- /.container-fluid -->
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

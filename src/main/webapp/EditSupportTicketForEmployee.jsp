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
  <title>HRMS | Edit Support Ticket (Employees)</title>
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
							<h1 class="m-0 text-dark">Edit Support Ticket (Employees)</h1>
						</div>
						<!-- /.col -->
						<div class="col-sm-6">
							<ol class="breadcrumb float-sm-right">
								<li class="breadcrumb-item"><a href="employeeSupportTicketSetup.jsp">Support Ticket Setup</a></li>
								<li class="breadcrumb-item"><a href="allPendingSupportTicketReportForEmployee.jsp">All Pending Support Tickets (Employees)</a></li>
								<li class="breadcrumb-item active">Edit Support Ticket (Employees)</li>
							</ol>
						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->
				</div>
				<!-- /.container-fluid -->
			</div>
			<!-- /.content-header -->

			
				<form action="editSupportTicketForEmployee" method="post" id="editSupportTicketForEmployee">
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
										<%
										int supportTicketId = Integer.parseInt(request.getParameter("supportTicketId"));
										SupportTicketDao std = new SupportTicketDao();
										SupportTicket st = std.getSupportTicketById(supportTicketId);
										DateDao dd = new DateDao();
										EmployeeDao ed = new EmployeeDao();
										%>
										<input type="hidden" value="<%=st.getSupportTicketId() %>" name="supportTicketId" >
						                  <label>Issue Report Date:</label>
						                    <input type="text" class="form-control" name="issueReportDate" readonly="readonly" value="<%=dd.changeFormatDate(st.getIssueReportDate()) %>" >
						                </div>
						                
										<div class="form-group">
											<label>Created By</label>
											<input type="text" class="form-control" readonly="readonly" value="<%=ed.getEmployeeName(st.getCreatedBy()) %>">
										</div>
						                <div class="form-group">
											<label>Issue Type</label>
											<%
											IssueTypeDao itd = new IssueTypeDao();
											
											%>
											<select class="form-control select2" style="width: 100%;" name="issueTypeId">
												<option selected="selected" value="<%=st.getIssueTypeId() %>"><%=itd.getIssueTypeName(st.getIssueTypeId()).toUpperCase() %></option>
											</select>
										</div>
										<div class="form-group">
											<label>Issue Description</label>
											<textarea cols="50" readonly="readonly" rows="4" class="form-control" name="issueDescription"><%=st.getIssueDescription() %></textarea>
										</div>
										<div class="form-group">
											<label>Uploaded Issue</label><br/>
											<a class="btn btn-warning" href="<%=st.getFileUrl() %>" download>Download File</a>
										</div>
										<div class="form-group">
											<label>Comment</label>
											<textarea rows="5" cols="60" class="form-control" name="comment"></textarea>
										</div>
									</div>
									
								</div>
								<!-- /.row -->
							</div>
							<!-- /.card-body -->
							<div class="card-footer">
			                  <button type="submit" class="btn btn-primary" id="save" onsubmit="disableSaveButton()">Resolved</button>
			                  <a class="btn btn-info" href="allPendingSupportTicketReportForEmployee.jsp">Go Back</a>
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

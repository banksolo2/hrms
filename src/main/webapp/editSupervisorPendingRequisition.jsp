<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.cust.hrms.dao.*"%>
<%@ page import="com.cust.hrms.models.*" %>
<%@ page import="com.cust.hrms.statues.*" %>
<%@ page import="com.oreilly.servlet.MultipartRequest"%>
<%@ page import="java.sql.*"%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>HRMS | Edit Requisition</title>
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
	session.setAttribute("parent", "requisition");
	session.setAttribute("page", "requisition_setup");
	int employeeId = 0;
	if(session.getAttribute("email") != null){
		employeeId = (int)session.getAttribute("employeeId");
	}
	EmployeeDao ed = new EmployeeDao();
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
							<h1 class="m-0 text-dark">Edit Requisition</h1>
						</div>
						<!-- /.col -->
						<div class="col-sm-6">
							<ol class="breadcrumb float-sm-right">
								<li class="breadcrumb-item"><a href="#">Requisition</a></li>
								<li class="breadcrumb-item"><a href="requisitionSetup.jsp">Requisition Setup</a></li>
								<li class="breadcrumb-item"><a href="allPendingSupervisorRequisitionReport.jsp">All Requisitions Pending Approval</a></li>
								<li class="breadcrumb-item active">Edit Requisition</li>
							</ol>
						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->
				</div>
				<!-- /.container-fluid -->
			</div>
			<!-- /.content-header -->

	<form action="editSupervisorRequisition" method="post" id="editSupervisorRequisition">
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
											<label>Requisition Type</label>
											<%
											int requisitionId = Integer.parseInt(request.getParameter("requisitionId"));
											RequisitionDao rd = new RequisitionDao();
											Requisition r = rd.getRequisitionById(requisitionId);
											RequisitionTypeDao rtd = new RequisitionTypeDao();
											ResultSet rs;
											%>
											<input type="hidden" name="requisitionId" value="<%=r.getRequisitionId() %>">
											<input type="text" class="form-control" value="<%=rtd.getName(r.getRequisitionTypeId()).toUpperCase() %>" readonly="readonly" >
										</div>
										<div class="form-group">
											<label>Subject</label>
											<input type="text" class="form-control" name="subject" value="<%=r.getSubject() %>" readonly="readonly">
										</div>
										<div class="form-group">
											<label>Requester</label>
											<input type="hidden" name="supervisorId" value="<%=r.getSupervisorId() %>" >
											<input type="text" class="form-control" value="<%=ed.getEmployeeName(r.getRequesterId()) %>" readonly="readonly">
										</div>
										<div class="form-group">
											<label>Recipients</label>
											<select class="select2" multiple="multiple" data-placeholder="Select Recipients" style="width: 100%;" name="recipients">
										<%
										int employeesId[] = ed.getEmployeesId(r.getRecipients());
										rs = ed.getEmployeeArrayOptionInteger(employeesId);
										for(int x : employeesId){
										%>
											<option selected="selected" value="<%=x %>"><%=ed.getEmployeeName(x).toUpperCase() %></option>
										<%
										}
										String fullName = null;
										while(rs.next()){
											fullName = rs.getString("first_name")+" "+rs.getString("middle_name")+" "+rs.getString("last_name")+" ("+rs.getString("staff_id")+")";
										%>
										<option value="<%=rs.getInt("employee_id") %>"><%=fullName.toUpperCase() %></option>
										<%} %>
											</select>
										</div>
										<!-- div class="form-group">
											<label>Approved By</label>
											<input type="text" class="form-control" readonly="readonly" value="<%=ed.getEmployeeName(r.getApprovedBy()) %>" >
										</div-->
										<div class="form-group">
											<a download href="<%=r.getFileUrl() %>" class="btn btn-warning">Download File</a>
										</div>
										<div class="form-group">
											<label>Save As</label>
											<select class="form-control select2" style="width: 100%;" name="requisitionStatusId">
												<option selected="selected" value="">SELECT SAVE AS</option>
											<%
											RequisitionStatusDao rsd = new RequisitionStatusDao();
											RequisitionStatues rqs = new RequisitionStatues();
											rs = rsd.getSupervisorOption();
											while(rs.next()){
											%>
											<option value="<%=rs.getInt("requisition_status_id")%>"><%=rqs.getStatusName(rs.getString("name")).toUpperCase() %></option>
											<%
											}
											%>
											</select>
										</div>
										<div class="form-group">
											<label>Comment</label>
											<textarea class="form-control" name="comment" rows="4" cols="50"></textarea>
										</div>
										
									</div>

								</div>
								<!-- /.row -->
							</div>
							<!-- /.card-body -->
							<div class="card-footer">
			                  <button type="submit" class="btn btn-primary">Authorize</button>
			                  <a class="btn btn-info" href="allApprovedEmployeeRequisitionReport.jsp">Go Back</a>
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

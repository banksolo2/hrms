<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.cust.hrms.dao.*" %>
<%@ page import="java.sql.*" %>
<!DOCTYPE html>
<html>
<head>
	<%
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
	if (session.getAttribute("employeeId") == null || session.getAttribute("email") == null
			|| session.getAttribute("staffId") == null) {
		response.sendRedirect("login.jsp");

	}
	session.setAttribute("parent", "requisition");
	session.setAttribute("page", "requisition_setup");
	%>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>HRMS |  All Declined Requisitions</title>
  <!-- Tell the browser to be responsive to screen width -->
  <meta name="viewport" content="width=device-width, initial-scale=1">
<jsp:include page="css.jsp"></jsp:include>
</head>
<body class="hold-transition sidebar-mini">
<div class="wrapper">
<jsp:include page="topNav.jsp"></jsp:include>
<jsp:include page="sideNav.jsp"></jsp:include>

  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-6">
            <h1>All Declined Requisitions</h1>
          </div>
          <div class="col-sm-6">
            <ol class="breadcrumb float-sm-right">
              <li class="breadcrumb-item"><a href="#">Requisition</a></li>
              <li class="breadcrumb-item"><a href="requisitionSetup.jsp">Requisition Setup</a></li>
              <li class="breadcrumb-item active">All Declined Requisitions</li>
            </ol>
          </div>
        </div>
      </div><!-- /.container-fluid -->
    </section>

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
												<th>Requisition No</th>
												<th>Created Date</th>
												<th>Type</th>
												<th>Subject</th>
												<th>Supervisor</th>
												<th>Declined By</th>
												<th></th>
											</tr>
										</thead>
										<tbody>
										<%
										String comment = null;
										int employeeId = 0;
										if(session.getAttribute("email") != null){
											employeeId = (int)session.getAttribute("employeeId");
										}
										EmployeeDao ed = new EmployeeDao();
										DateDao dd = new DateDao();
										RequisitionStatusDao rsd = new RequisitionStatusDao();
										RequisitionTypeDao rtd = new RequisitionTypeDao();
										RequisitionDao rd = new RequisitionDao();
										FileUploadDao fud = new FileUploadDao();
										ResultSet rs = rd.getRequesterRequisitionReport(employeeId, rsd.getRequisitionStatusId("declined"));
										while(rs.next()){
											comment = rs.getString("comment");
											if(comment == null){
												comment = "";
											}
										%>
											<tr>
												<td><%=rs.getInt("requisition_id") %></td>
												<td><%=dd.changeFormatDate(rs.getDate("created_date").toString()) %></td>
												<td><%=rtd.getName(rs.getInt("requisition_type_id")) %></td>
												<td><%=rs.getString("subject") %></td>
												<td><%=ed.getEmployeeName(rs.getInt("supervisor_id")) %></td>
												<td><%=ed.getEmployeeName(rs.getInt("declined_by")) %></td>
												<td><a class="btn btn-warning" href="<%=rs.getString("file_url") %>">Download File</a></td>
												<!--td>
													<form action="editEmployeeApprovedRequisition.jsp" method="post">
													<input type="hidden" name="requisitionId" value="<%=rs.getInt("requisition_id") %>" />
													<button type="submit" class="btn btn-primary"><i class="fas fa-pencil-alt"></i> Edit</button>
													</form>
												</td>
												
												<td>
													<form action="deleteEmployeeRequisition" method="post">
													<input type="hidden" name="requisitionId" value="<%=rs.getInt("requisition_id") %>" />
													<button type="submit" class="btn btn-danger"><i class="fas fa-trash"></i> Delete</button>
													</form>
												</td-->
											</tr>
										<%
										}
										%>
										</tbody>
										<tfoot>
											<!--  tr>
												<th>Name</th>
												<th>Created At</th>
												<th>Updated At</th>
												<th>Created By</th>
												<th>Updated By</th>
												<th></th>
												<th></th>
											</tr-->
										</tfoot>
									</table>
					</div>
					<!-- /.card-body -->
				</div>
				<!-- /.card -->
			</div>
		</div>
      </div>
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

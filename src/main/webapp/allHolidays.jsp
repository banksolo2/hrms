<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.cust.hrms.dao.*"%>
<%@ page import="java.sql.*"%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>HRMS | All Holidays</title>
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
	session.setAttribute("page", "admin_leave_setup");
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
							<h1 class="m-0 text-dark">All Holidays</h1>
						</div>
						<!-- /.col -->
						<div class="col-sm-6">
							<ol class="breadcrumb float-sm-right">
								<li class="breadcrumb-item"><a href="#">Admin</a></li>
								<li class="breadcrumb-item"><a href="adminLeaveSetup.jsp">Admin Leave Setup</a></li>
								<li class="breadcrumb-item active">All Holidays</li>
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
												<th>Name</th>
												<th>Description</th>
												<th>Date At</th>
												<th>Created By</th>
												<th>Updated By</th>
												<th></th>
												<th></th>
											</tr>
										</thead>
										<tbody>
										<%
										EmployeeDao ed = new EmployeeDao();
										HolidayDao hd = new HolidayDao();
										ResultSet rs = hd.getAllHolidays();
										int createdById;
										int updatedById;
										String createdBy;
										String updatedBy;
										DateDao dd = new DateDao();
										while(rs.next()){
											createdById = rs.getInt("created_by");
											updatedById = rs.getInt("updated_by");
											createdBy = (createdById < 1) ? "" : ed.getEmployeeName(createdById);
											updatedBy = (updatedById < 1) ? "" : ed.getEmployeeName(updatedById);
										%>
											<tr>
												<td><%=rs.getString("name") %></td>
												<td><%=rs.getString("description") %></td>
												<td><%=dd.changeFormatDate(rs.getDate("date_at").toString()) %></td>
												<td><%=createdBy %></td>
												<td><%=updatedBy %></td>
												<td>
													<form action="editHoliday.jsp" method="post">
													<input type="hidden" name="holidayId" value="<%=rs.getInt("holiday_id") %>" />
													<button type="submit" class="btn btn-primary"><i class="fas fa-pencil-alt"></i> Edit</button>
													</form>
												</td>
												
												<td>
													<form action="deleteHoliday" method="post">
													<input type="hidden" name="holidayId" value="<%=rs.getInt("holiday_id") %>" />
													<button type="submit" class="btn btn-danger" id="save" onsubmit="disableSaveButton()"><i class="fas fa-trash"></i> Delete</button>
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
												<th>Name</th>
												<th>Description</th>
												<th>Date At</th>
												<th>Created By</th>
												<th>Updated By</th>
												<th></th>
												<th></th>
											</tr> -->
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

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.cust.hrms.dao.*" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.text.*" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
	<%
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
	if (session.getAttribute("employeeId") == null || session.getAttribute("email") == null
			|| session.getAttribute("staffId") == null) {
		response.sendRedirect("login.jsp");

	}
	%>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>HRMS | All Approved Leave Plans</title>
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
            <h1>All Approved Leave Plans</h1>
          </div>
          <div class="col-sm-6">
            <ol class="breadcrumb float-sm-right">
              <li class="breadcrumb-item"><a href="#">Leave Plan</a></li>
              <li class="breadcrumb-item"><a href="employeeLeavePlanSetup.jsp">Employee Setup</a></li>
              <li class="breadcrumb-item active">All Approved Leave Plans</li>
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
      		String successMessage = (String)session.getAttribute("success");
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
												<th>Start Date</th>
												<th>End Date</th>
												<th>No Of Days</th>
												<th>Status</th>
												<th>Comment</th>
											</tr>
										</thead>
										<tbody>
										<%
										int employeeId = 0;
										if(session.getAttribute("email") != null){
											employeeId = (int) session.getAttribute("employeeId");
										}
										LeavePlanDao lpd = new LeavePlanDao();
										LeavePlanStatusDao lpsd = new LeavePlanStatusDao();
										ResultSet rs = lpd.getEmployeeApprovedLeavePlansReport(employeeId);
										String comment = "";
										DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
										String dateFrom = "";
										String dateTo = "";
										java.util.Date startDate;
										java.util.Date endDate;
										while(rs.next()){
											comment = rs.getString("comment");
											comment = (comment == null) ? "" : comment;
											dateFrom = rs.getDate("start_date").toString();
											dateTo = rs.getDate("end_date").toString();
											startDate = df.parse(dateFrom);
											endDate = df.parse(dateTo);
										%>
											<tr>
												<td><%=startDate.toString().replace(" 00:00:00 WAT", ", ") %></td>
												<td><%=endDate.toString().replace(" 00:00:00 WAT", ", ") %></td>
												<td><%=rs.getInt("no_of_days") %></td>
												<td><%=lpsd.getLeavePlanStatusName(rs.getInt("leave_plan_status_id")) %></td>
												<td><%=comment %></td>
											</tr>
										<%
										}
										%>
										</tbody>
										<tfoot>
											<tr>
												<th>Start Date</th>
												<th>End Date</th>
												<th>No Of Days</th>
												<th>Status</th>
												<th>Comment</th>
											</tr>
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

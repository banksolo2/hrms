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
	session.setAttribute("parent", "admin");
	session.setAttribute("page", "core_setup");
	if(session.getAttribute("isSupervisor") != null || session.getAttribute("isSuperAdmin") != null){
		boolean isHrAdmin = (boolean) session.getAttribute("isHrAdmin");
		boolean isSuperAdmin = (boolean) session.getAttribute("isSuperAdmin");
		if(isHrAdmin == false && isSuperAdmin == false){
			response.sendRedirect("index.jsp");
		}
	}
	%>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>HRMS | All Branches</title>
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
            <h1>All Branches</h1>
          </div>
          <div class="col-sm-6">
            <ol class="breadcrumb float-sm-right">
              <li class="breadcrumb-item"><a href="#">Admin</a></li>
              <li class="breadcrumb-item"><a href="coreSetup.jsp">Core Setup</a></li>
              <li class="breadcrumb-item active">All Branches</li>
            </ol>
          </div>
        </div>
      </div><!-- /.container-fluid -->
    </section>

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
												<th>Created At</th>
												<th>Updated At</th>
												<th>Created By</th>
												<th>Updated By</th>
												<th></th>
												<th></th>
											</tr>
										</thead>
										<tbody>
										<%
										BranchDao bd = new BranchDao();
										ResultSet rs = bd.getAllBranches();
										EmployeeDao ed = new EmployeeDao();
										String createdBy;
										String updatedBy;
										while(rs.next()){
											createdBy = ed.getEmployeeName(rs.getInt("created_by"));
											if(createdBy == null) createdBy = "";
											updatedBy = ed.getEmployeeName(rs.getInt("updated_by"));
											if(updatedBy == null) updatedBy = "";
										%>
											<tr>
												<td><%=rs.getString("name") %></td>
												<td><%=rs.getTimestamp("created_at") %></td>
												<td><%=rs.getTimestamp("updated_at") %></td>
												<td><%=createdBy %></td>
												<td><%=updatedBy %></td>
												<td>
													<form action="editBranch.jsp" method="post">
													<input type="hidden" name="branchId" value="<%=rs.getInt("branch_id") %>" />
													<button type="submit" class="btn btn-primary"><i class="fas fa-pencil-alt"></i> Edit</button>
													</form>
												</td>
												
												<td>
													<form action="deleteBranch" method="post">
													<input type="hidden" name="branchId" value="<%=rs.getInt("branch_id") %>" />
													<button type="submit" class="btn btn-danger"><i class="fas fa-trash"></i> Delete</button>
													</form>
												</td>
											</tr>
										<%
										}
										%>
										</tbody>
										<tfoot>
											<tr>
												<th>Name</th>
												<th>Created At</th>
												<th>Updated At</th>
												<th>Created By</th>
												<th>Updated By</th>
												<th></th>
												<th></th>
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

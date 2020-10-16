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
  <title>HRMS | Edit Employee</title>
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
	session.setAttribute("page", "core_setup");
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
							<h1 class="m-0 text-dark">Edit Employee</h1>
						</div>
						<!-- /.col -->
						<div class="col-sm-6">
							<ol class="breadcrumb float-sm-right">
								<li class="breadcrumb-item"><a href="coreSetup.jsp">Core Setup</a></li>
								<li class="breadcrumb-item"><a href="allEmployees.jsp">All Employees</a></li>
								<li class="breadcrumb-item active">Edit Employee</li>
							</ol>
						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->
				</div>
				<!-- /.container-fluid -->
			</div>
			<!-- /.content-header -->
			<%
			int employeeId = Integer.parseInt(request.getParameter("employeeId"));
			EmployeeDao ed = new EmployeeDao();
			Employee e = ed.getEmployeeById(employeeId);
			%>

			<form action="editEmployee" method="post" id="editEmployee">
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
									<div class="col-md-6">
										<div class="form-group">
											<label>Title</label> 
											<input type="hidden" name="employeeId" value="<%=e.getEmployeeId() %>" />
											<select class="form-control select2"
												style="width: 100%;" name="title">
												<option selected="selected" value="<%=e.getTitle() %>"><%=e.getTitle().toUpperCase() %></option>
												<option value="Mr">MR</option>
												<option value="Mrs">MRS</option>
												<option value="Miss">MISS</option>
											</select>
										</div>
										<div class="form-group">
											<label>Middle Name:</label> <input type="text" value="<%=e.getMiddleName() %>"
												name="middleName" class="form-control" />
										</div>
										<div class="form-group">
											<label>Name Initials</label> <input type="text" value="<%=e.getNameInitials() %>"
												name="nameInitials" class="form-control" />
										</div>

										<div class="form-group">
											<label>Employee Status</label> <select
												class="form-control select2" style="width: 100%;"
												name="employeeStatusId">
												<%
												EmployeeStatusDao esd = new EmployeeStatusDao();
												int employeeStatusId = e.getEmployeeStatusId();
												String employeeStatusName = esd.getEmployeeStatusName(employeeStatusId).toUpperCase();
												%>
												<option selected="selected" value="<%=employeeStatusId%>"><%=employeeStatusName %></option>
												<%
												
												ResultSet rs = esd.getAllEmployeeStatusOnUpdate(employeeStatusId);
												while (rs.next()) {
												%>
												<option value="<%=rs.getInt("employee_status_id")%>"><%=rs.getString("name").toUpperCase()%></option>
												<%
													}
												%>
											</select>
										</div>
										<div class="form-group">
											<label>Mobile Number</label>
											<div class="input-group">
												<div class="input-group-prepend">
													<span class="input-group-text"><i
														class="fas fa-phone"></i></span>
												</div>

												<input type="text" class="form-control" value="<%=e.getMobileNumber() %>"
													data-inputmask='"mask": "(999) 999-9999-999"' data-mask name="mobileNumber">
											</div>
										</div>
										<div class="form-group">
											<label>Department</label>
											<select class="form-control select2" style="width: 100%;" name="departmentId">
												<%
												DepartmentDao dd = new DepartmentDao();
												int departmentId = e.getDepartmentId();
												%>
												<option selected="selected" value="<%=departmentId %>"><%=dd.getDepartmentName(departmentId).toUpperCase() %></option>
												<%
												
												rs = dd.getAllDepartmentonUpdate(departmentId);
												while(rs.next()){
												%>
												<option value="<%=rs.getInt("department_id") %>"><%=rs.getString("name").toUpperCase() %></option>
												<%
												}
			
												%>
											</select>
										</div>
										<div class="form-group">
											<label>Staff ID</label>
											<input type="text" class="form-control" name="staffId" value="<%=e.getStaffId() %>"  />
										</div>
										<div class="form-group">
											<label>Company</label>
											<select class="form-control select2" style="width: 100%;" name="companyId">
											<%
											CompanyDao cd = new CompanyDao();
											int companyId = e.getCompanyId();
											%>
												<option selected="selected" value="<%=companyId %>"><%=cd.getCompanyName(companyId).toUpperCase() %></option>
												<%
												
												rs = cd.getAllCompanyOnUpdate(companyId);
												while(rs.next()){
												%>
												<option value="<%=rs.getInt("company_id") %>"><%=rs.getString("name").toUpperCase() %></option>
												<%
												}
												%>
											</select>
										</div>
										<div class="form-group">
											<label>Martial Status</label>
											<select class="form-control select2" style="width: 100%;" name="martialStatusId">
												<%
												MartialStatusDao msd = new MartialStatusDao();
												int martialStatusId = e.getMartialStatusId();
												%>
												<option selected="selected" value="<%=martialStatusId %>"><%=msd.getMartialStatusName(martialStatusId).toUpperCase() %></option>
												<%
												
												rs = msd.getAllMartialStatusesOnUpdate(martialStatusId);
												while(rs.next()){
												%>
												<option value="<%=rs.getInt("martial_status_id") %>"><%=rs.getString("name").toUpperCase() %></option>
												<%
												}
												%>
											</select>
										</div>
										<div class="form-group">
											<label>Leave Supervisor</label>
											<select class="form-control select2" style="width: 100%;" name="leaveSupervisorId">
											<%
											int leaveSupervisorId = e.getLeaveSupervisorId();
											if(leaveSupervisorId == 0){
											%>
												<option selected="selected" value="0">SELECT LEAVE SUPERVISOR</option>
											<%
											}
											else{
											%>
												<option selected="selected" value="<%=leaveSupervisorId %>"><%=ed.getEmployeeName(leaveSupervisorId).toUpperCase() %></option>
											<%
											}
											rs = ed.getAllEmployeeOnUpdate(leaveSupervisorId);
											String fullName;
											while(rs.next()){
												fullName = rs.getString("first_name")+" "+rs.getString("middle_name")+" "+rs.getString("last_name")+" ("+rs.getString("staff_id")+")";
											%>
												<option value="<%=rs.getInt("employee_id") %>"><%=fullName %> </option>
											<%
											}
											%>
											</select>
										</div>
										<div class="form-group">
											<label>Current Address</label>
											<textarea rows="5" cols="50" class="form-control" name="currentAddress"><%=e.getCurrentAddress() %></textarea>
										</div>
									</div>
									<!-- /.col -->
									<div class="col-md-6">
										<div class="form-group">
											<label>First Name</label> <input type="text" name="firstName" value="<%=e.getFirstName() %>"
												class="form-control" />
										</div>

										<div class="form-group">
											<label>Last Name</label> <input type="text" name="lastName" value="<%=e.getLastName() %>"
												class="form-control" />
										</div>
										<div class="form-group">
											<label>Email:</label> <input type="email" name="email" value="<%=e.getEmail() %>"
												class="form-control" />
										</div>
										<!-- /.form-group -->
										<div class="form-group">
											<label>Date Of Employment:</label>

											<div class="input-group">
												<div class="input-group-prepend">
													<span class="input-group-text"><i
														class="far fa-calendar-alt"></i></span>
												</div>
												<input type="text" name="dateOfEmployment" value="<%=e.getDateOfEmployment() %>"
													class="form-control" data-inputmask-alias="datetime"
													data-inputmask-inputformat="yyyy-mm-dd" data-mask>
											</div>
											<!-- /.input group -->
										</div>
										<div class="form-group">
											<label>State of Origin</label>
											<select class="form-control select2" style="width: 100%;" name="stateId">
											<%
											StateDao sd = new StateDao();
											int stateId = e.getStateId();
											%>
												<option selected="selected" value="<%=stateId %>"><%=sd.getStateName(stateId).toUpperCase() %></option>
												<%
													rs = sd.getAllStateOnUpdate(stateId);
													while(rs.next()){
												%>
												<option value="<%=rs.getInt("state_id") %>"><%=rs.getString("name").toUpperCase() %></option>
												<%
													}
												%>
											</select>
										</div>
										<div class="form-group">
											<label>Level</label>
											<select class="form-control select2" style="width: 100%;" name="levelId">
												<%
												LevelDao ld = new LevelDao();
												int levelId = e.getLevelId();
												%>
												<option selected="selected" value="<%=levelId %>"><%=ld.getLevelName(levelId).toUpperCase() %></option>
												<%
												
												rs = ld.getAllLevelsOnUpdate(levelId);
												while(rs.next()){
												%>
												<option value="<%=rs.getInt("level_id") %>"><%=rs.getString("name").toUpperCase() %></option>
												<%
												}
												%>
											</select>
										</div>
										<div class="form-group">
											<label>Branch</label>
											<select class="form-control select2" style="width: 100%;" name="branchId">
												<%
												BranchDao bd = new BranchDao();
												int branchId = e.getBranchId();
												%>
												<option selected="selected" value="<%=branchId %>"><%=bd.getBranchName(branchId).toUpperCase() %></option>
												<%
												
												rs = bd.getAllBranchesOnUpdate(branchId);
												while(rs.next()){
												%>
												<option value="<%=rs.getInt("branch_id") %>"><%=rs.getString("name").toUpperCase() %></option>
												<%
												}
												%>
											</select>
										</div>
										<div class="form-group">
											<label>Gender</label>
											<select class="form-control select2" style="width: 100%;" name="genderId">
												<%
												GenderDao gd = new GenderDao();
												int genderId = e.getGenderId();
												%>
												<option selected="selected" value="<%=genderId %>"><%=gd.getGenderName(genderId).toUpperCase() %></option>
												<%
												
												rs = gd.getAllGendersOnUpdate(genderId);
												while(rs.next()){
												%>
												<option value="<%=rs.getInt("gender_id") %>"><%=rs.getString("name").toUpperCase() %></option>
												<%
												}
												%>
											</select>
										</div>
										<div class="form-group">
											<label>Personal Email Address</label>
											<input type="email" name="personalEmail" id="personalEmail" class="form-control" value="<%=e.getPersonalEmail() %>" />
										</div>
										<div class="form-group">
											<label>Date Of Birth:</label>

											<div class="input-group">
												<div class="input-group-prepend">
													<span class="input-group-text"><i
														class="far fa-calendar-alt"></i></span>
												</div>
												<input type="text" name="dateOfBirth" value="<%=e.getDateOfBirth() %>"
													class="form-control" data-inputmask-alias="datetime"
													data-inputmask-inputformat="yyyy-mm-dd" data-mask>
											</div>
											<!-- /.input group -->
										</div>
										<div class="form-group">
											<label>Personal Production Target</label>
											<input type="text" name="personalProductionTarget" class="form-control" value="<%=e.getPersonalProductionTarget() %>" >
										</div>
									</div>
									<!-- /.col -->

								</div>
								<!-- /.row -->
							</div>
							<!-- /.card-body -->
							<div class="card-footer">
			                  <button type="submit" class="btn btn-primary">Save</button>
			                  <a class="btn btn-info" href="allEmployees.jsp">Go Back</a>
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

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.cust.hrms.dao.*"%>
<%@ page import="java.sql.*"%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>HRMS | Create Employee</title>
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
							<h1 class="m-0 text-dark">Create Employee</h1>
						</div>
						<!-- /.col -->
						<div class="col-sm-6">
							<ol class="breadcrumb float-sm-right">
								<li class="breadcrumb-item"><a href="#">Admin</a></li>
								<li class="breadcrumb-item"><a href="adminEmployeeSetup.jsp">Employee Setup</a></li>
								<li class="breadcrumb-item active">Create Employee</li>
							</ol>
						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->
				</div>
				<!-- /.container-fluid -->
			</div>
			<!-- /.content-header -->

			<form action="createEmployee" method="post" id="createEmployee">
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
									<div class="col-md-6">
										<div class="form-group">
										<%
										String title = request.getParameter("title");
										%>
											<label>Title</label> <select class="form-control select2"
												style="width: 100%;" name="title">
												<% if(title == null){ %>
												<option selected="selected" value="">SELECT TITLE</option>
												<%}else{ %>
												<option selected="selected" value="<%=title %>"> <%=title.toUpperCase() %></option>
												<%} %>
												<option value="Mr">MR</option>
												<option value="Mrs">MRS</option>
												<option value="Miss">MISS</option>
											</select>
										</div>
										<div class="form-group">
										<%
										String middleName = request.getParameter("middleName");
										if(middleName == null) middleName = "";
										%>
											<label>Middle Name:</label> <input type="text" value="<%=middleName %>"
												name="middleName" class="form-control" />
										</div>
										<div class="form-group">
										<%
										String nameInitials = request.getParameter("nameInitials");
										if(nameInitials == null) nameInitials="";
										%>
											<label>Name Initials</label> <input type="text" value="<%=nameInitials %>"
												name="nameInitials" class="form-control" />
										</div>

										<div class="form-group">
										<%
										EmployeeStatusDao esd = new EmployeeStatusDao();
										String employeeStatus = request.getParameter("employeeStatusId");
										int employeeStatusId = (employeeStatus == null) ? 0 : Integer.parseInt(employeeStatus);
										%>
											<label>Employee Status</label> <select
												class="form-control select2" style="width: 100%;"
												name="employeeStatusId">
												<%if(employeeStatusId == 0){ %>
												<option selected="selected" value="">SELECT
													EMPLOYEE STATUS</option>
												<%
												}else{
												%>
												<option selected="selected" value="<%=employeeStatusId %>"><%=esd.getEmployeeStatusName(employeeStatusId).toUpperCase() %></option>
												<%
												}
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
											<%
											String mobileNumber = request.getParameter("mobileNumber");
											if(mobileNumber == null) mobileNumber = "";
											%>
											<div class="input-group">
												<div class="input-group-prepend">
													<span class="input-group-text"><i
														class="fas fa-phone"></i></span>
												</div>

												<input type="text" class="form-control" value="<%=mobileNumber %>"
													data-inputmask='"mask": "(999) 999-999-9999"' data-mask name="mobileNumber" >
											</div>
										</div>
										<div class="form-group">
											<label>Department</label>
											<%
											String department = request.getParameter("departmentId");
											int departmentId = (department == null) ? 0 : Integer.parseInt(department);
											DepartmentDao dd = new DepartmentDao();
											%>
											<select class="form-control select2" style="width: 100%;" name="departmentId">
												<% if(departmentId == 0){ %>
												<option selected="selected" value="">SELECT DEPARTMENT</option>
												<% }else{ %>
												<option selected="selected" value="<%=departmentId %>"><%=dd.getDepartmentName(departmentId).toUpperCase() %></option>
												<%
												}
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
											<%
											String staffId = request.getParameter("staffId");
											if(staffId == null) staffId = "";
											%>
											<input type="text" class="form-control" name="staffId" value="<%=staffId %>" />
										</div>
										<div class="form-group">
											<label>Company</label>
											<%
											CompanyDao cd = new CompanyDao();
											String company = request.getParameter("companyId");
											int companyId = (company == null) ? 0 : Integer.parseInt(company);
											%>
											<select class="form-control select2" style="width: 100%;" name="companyId">
											<% if(companyId == 0){ %>
												<option selected="selected" value="">SELECT COMPANY</option>
											<%
											}else{
											%>
												<option selected="selected" value="<%=companyId %>"><%=cd.getCompanyName(companyId).toUpperCase() %></option>											
												<%
											}
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
											<%
											MartialStatusDao msd = new MartialStatusDao();
											String martialStatus = request.getParameter("martialStatusId");
											int martialStatusId = (martialStatus == null) ? 0 : Integer.parseInt(martialStatus);
											%>
											<select class="form-control select2" style="width: 100%;" name="martialStatusId">
												<% if(martialStatusId == 0){ %>
												<option selected="selected" value="">SELECT MARTIAL STATUS</option>
												<% }else{ %>
												<option selected="selected" value="<%=martialStatusId %>"><%=msd.getMartialStatusName(martialStatusId).toUpperCase() %></option>
												<%
												}
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
											<%
											EmployeeDao ed = new EmployeeDao();
											String leaveSupervisor = request.getParameter("leaveSupervisorId");
											int leaveSupervisorId = (leaveSupervisor == null) ? 0 : Integer.parseInt(leaveSupervisor);
											%>
											<select class="form-control select2" style="width: 100%;" name="leaveSupervisorId">
												<%
												if(leaveSupervisorId == 0){
												%>
												<option selected="selected" value="">SELECT LEAVE SUPERVISOR</option>
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
												<option value="<%=rs.getInt("employee_id") %>"><%=fullName.toUpperCase() %></option>
												<%
												}
												%>
											</select>
										</div>
										<div class="form-group">
											<label>Current Address</label>
											<%
											String currentAddress = request.getParameter("currentAddress");
											if(currentAddress == null) currentAddress = "";
											%>
											<textarea rows="5" cols="50" class="form-control" name="currentAddress"><%=currentAddress %></textarea>
										</div>
									</div>
									<!-- /.col -->
									<div class="col-md-6">
										<div class="form-group">
											<%
											String firstName = request.getParameter("firstName");
											if(firstName == null) firstName = "";
											%>
											<label>First Name</label> <input type="text" name="firstName"
												class="form-control" value="<%=firstName %>" />
										</div>

										<div class="form-group">
											<%
											String lastName = request.getParameter("lastName");
											if(lastName == null) lastName = "";
											%>
											<label>Last Name</label> <input type="text" name="lastName"
												class="form-control" value="<%=lastName %>" />
										</div>
										<div class="form-group">
											<%
											String email = request.getParameter("email");
											if(email == null) email = "";
											%>
											<label>Email:</label> <input type="email" name="email"
												class="form-control" value="<%=email %>" />
										</div>
										<!-- /.form-group -->
										<div class="form-group">
											<label>Date Of Employment:</label>
											<%
											String dateOfEmployment = request.getParameter("dateOfEmployment");
											if(dateOfEmployment == null) dateOfEmployment="";
											%>

											<!-- <div class="input-group">
												<div class="input-group-prepend">
													<span class="input-group-text"><i
														class="far fa-calendar-alt"></i></span>
												</div>
												<input type="text" name="dateOfEmployment" value="<%=dateOfEmployment %>"
													class="form-control" data-inputmask-alias="datetime"<%=dateOfEmployment %>
													data-inputmask-inputformat="yyyy-mm-dd" data-mask>
											</div> -->
											<div class="input-group date" id="reservationdate" data-target-input="nearest">
												<input type="text" class="form-control datetimepicker-input" data-target="#reservationdate" 
												name="dateOfEmployment" value="<%=dateOfEmployment %>"/>
												<div class="input-group-append" data-target="#reservationdate" data-toggle="datetimepicker">
											    	<div class="input-group-text"><i class="fa fa-calendar"></i></div>
												</div>
											</div>
											<!-- /.input group -->
										</div>
										<div class="form-group">
											<label>State of Origin</label>
											<%
											StateDao sd = new StateDao();
											String state = request.getParameter("stateId");
											int stateId = (state == null) ? 0 : Integer.parseInt(state);
											%>
											<select class="form-control select2" style="width: 100%;" name="stateId">
												<% if(stateId == 0){ %>
												<option selected="selected" value="">SELECT STATE</option>
												<% }else{ %>
												<option value="<%=stateId %>" selected="selected"><%=sd.getStateName(stateId) %></option>
												<%
												}
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
											<%
											LevelDao ld = new LevelDao();
											String level = request.getParameter("levelId");
											int levelId = (level == null) ? 0 : Integer.parseInt(level);
											%>
											<select class="form-control select2" style="width: 100%;" name="levelId">
												<% if(levelId == 0){ %>
												<option selected="selected" value="">SELECT LEVEL</option>
												<% }else{ %>
												<option selected="selected" value="<%=levelId %>"><%=ld.getLevelName(levelId).toUpperCase() %></option>
												<%
												}
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
											<%
											BranchDao bd = new BranchDao();
											String branch = request.getParameter("branchId");
											int branchId = (branch == null) ? 0 : Integer.parseInt(branch);
											%>
											<select class="form-control select2" style="width: 100%;" name="branchId">
												<% if(branchId == 0){ %>
												<option selected="selected" value="">SELECT BRANCH</option>
												<% }else{ %>
												<option selected="selected" value="<%=branchId %>"><%=bd.getBranchName(branchId).toUpperCase() %></option>
												<%
												}
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
											<%
											GenderDao gd = new GenderDao();
											String gender = request.getParameter("genderId");
											int genderId = (gender == null) ? 0 : Integer.parseInt(gender);
											%>
											<select class="form-control select2" style="width: 100%;" name="genderId">
												<% if(genderId == 0){ %>
												<option selected="selected" value="">SELECT GENDER</option>
												<% }else{ %>
												<option selected="selected" value="<%=genderId %>"><%=gd.getGenderName(genderId).toUpperCase() %></option>
												<%
												}
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
											<%
											String personalEmail = request.getParameter("personalEmail");
											if(personalEmail == null) personalEmail="";
											%>
											<input type="email" value="<%=personalEmail %>" name="personalEmail" id="personalEmail" class="form-control" />
										</div>
										<div class="form-group">
											<label>Date Of Birth:</label>
											<%
											String dateOfBirth = request.getParameter("dateOfBirth");
											if(dateOfBirth == null) dateOfBirth = "";
											%>
											<div class="input-group">
												<div class="input-group-prepend">
													<span class="input-group-text"><i
														class="far fa-calendar-alt"></i></span>
												</div>
												<input type="text" name="dateOfBirth" value="<%=dateOfBirth %>"
													class="form-control" data-inputmask-alias="datetime"
													data-inputmask-inputformat="yyyy-mm-dd" data-mask>
											</div> 
											<!-- <div class="input-group date" id="reservationdate" data-target-input="nearest">
												<input type="text" class="form-control datetimepicker-input" data-target="#reservationdate" 
												name="dateOfBirth" value="<%=dateOfBirth %>"/>
												<div class="input-group-append" data-target="#reservationdate" data-toggle="datetimepicker">
											    	<div class="input-group-text"><i class="fa fa-calendar"></i></div>
												</div>
											</div> -->
											<!-- /.input group -->
										</div>
										<div class="form-group">
											<label>Personal Production Target</label>
											<%
											String personalProduction = request.getParameter("personalProductionTarget");
											if(personalProduction == null) personalProduction = "";
											%>
											<input type="text" class="form-control"
											name="personalProductionTarget" value="<%=personalProduction %>">
										</div>
									</div>
									<!-- /.col -->

								</div>
								<!-- /.row -->
							</div>
							<!-- /.card-body -->
							<div class="card-footer">
			                  <button type="submit" class="btn btn-primary">Submit</button>
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

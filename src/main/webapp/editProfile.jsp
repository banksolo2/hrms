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
  <title>HRMS | Edit Profile</title>
  <!-- Tell the browser to be responsive to screen width -->
  <meta name="viewport" content="width=device-width, initial-scale=1">
<jsp:include page="css.jsp"></jsp:include>
</head><jsp:include page="head.jsp"></jsp:include>
<body class="hold-transition sidebar-mini layout-fixed">
	<%
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
	if (session.getAttribute("employeeId") == null || session.getAttribute("email") == null
			|| session.getAttribute("staffId") == null) { 
		response.sendRedirect("login.jsp");

	}
	session.setAttribute("parent", "profile");
	session.setAttribute("page", "edit_profile");
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
							<h1 class="m-0 text-dark">Edit Profile</h1>
						</div>
						<!-- /.col -->
						<div class="col-sm-6">
							<ol class="breadcrumb float-sm-right">
								<li class="breadcrumb-item"><a href="#">Profile</a></li>
								<li class="breadcrumb-item active">Edit Profile</li>
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
			int employeeId = 0;
			Employee e;
			if(session.getAttribute("email") != null){
				employeeId = (int)session.getAttribute("employeeId");
				EmployeeDao ed = new EmployeeDao();
				e  = ed.getEmployeeById(employeeId);
			}
			else{
				e = new Employee();
			}
			%>

			<form action="editProfile" method="post" id="editProfile">
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
								 if(employeeId >= 1){
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
											<label>Employee Status</label> 
												<%
												EmployeeStatusDao esd = new EmployeeStatusDao();
												int employeeStatusId = e.getEmployeeStatusId();
												String employeeStatusName = esd.getEmployeeStatusName(employeeStatusId).toUpperCase();
												ResultSet rs;
												%>
											<input type="hidden" name="employeeStatusId" value="<%=employeeStatusId%>" />
											<input type="text" class="form-control" value="<%=employeeStatusName %>" readonly="readonly" />
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
												<%
												DepartmentDao dd = new DepartmentDao();
												int departmentId = e.getDepartmentId();
												%>
											<input type="hidden" name="departmentId" value="<%=departmentId %>" />
											<input type="text" class="form-control" value="<%=dd.getDepartmentName(departmentId).toUpperCase()  %>" readonly="readonly"/>
										</div>
										<div class="form-group">
											<label>Staff ID</label>
											<input type="text" class="form-control" name="staffId" value="<%=e.getStaffId() %>" readonly="readonly" />
										</div>
										<div class="form-group">
											<label>Company</label>
											<%
											CompanyDao cd = new CompanyDao();
											int companyId = e.getCompanyId();
											%>
											<input type="hidden" name="companyId" value="<%=companyId %>" />
											<input type="text" class="form-control" value="<%=cd.getCompanyName(companyId).toUpperCase() %>" readonly="readonly" />
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
												class="form-control" readonly="readonly" />
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
												<%
												LevelDao ld = new LevelDao();
												int levelId = e.getLevelId();
												%>
											<input type="hidden" name="levelId" value="<%=levelId %>" />
											<input type="text" class="form-control" value="<%=ld.getLevelName(levelId).toUpperCase() %>" readonly="readonly" />
										</div>
										<div class="form-group">
											<label>Branch</label>
												<%
												BranchDao bd = new BranchDao();
												int branchId = e.getBranchId();
												%>
											<input type="hidden" name="branchId" value="<%=branchId %>" />
											<input type="text" class="form-control" value="<%=bd.getBranchName(branchId).toUpperCase() %>" readonly="readonly" />
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
									</div>
									<!-- /.col -->
								<%
								 }
								%>
								</div>
								<!-- /.row -->
							</div>
							<!-- /.card-body -->
							<div class="card-footer">
			                  <button type="submit" class="btn btn-primary" id="save" onsubmit="disableSaveButton()">Submit</button>
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

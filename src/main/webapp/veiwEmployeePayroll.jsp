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
  <title>HRMS | View Employee Payroll</title>
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
	session.setAttribute("page", "admin_payroll_setup");
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
							<h1 class="m-0 text-dark">View Employee Payroll</h1>
						</div>
						<!-- /.col -->
						<div class="col-sm-6">
							<ol class="breadcrumb float-sm-right">
								<li class="breadcrumb-item"><a href="#">Admin</a></li>
								<li class="breadcrumb-item"><a href="adminPayrollSetup.jsp">Payroll Setup</a></li>
								<li class="breadcrumb-item"><a href="allPayrollHistory.jsp">All Payroll History</a></li>
								<li class="breadcrumb-item"><a href="searchEmployeePayroll.jsp">Search Employee Payroll</a></li>
								<li class="breadcrumb-item active">View Employee Payroll</li>
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
						
					</div>
				</div>
				<!-- /.container-fluid -->
			</section>
			<!-- /.content -->
			<section class="content">
		      <div class="container-fluid">
		        <div class="row">
		          <div class="col-12">
		            <!-- Main content -->
		            <div class="invoice p-3 mb-3">
		              <!-- title row -->
		              <div class="row">
		                <div class="col-12">
		                <%
		                int employeeId = Integer.parseInt(request.getParameter("employeeId"));
		                EmployeeDao ed = new EmployeeDao();
		                Employee e = ed.getEmployeeById(employeeId);
		                String fullName = e.getFirstName()+" "+e.getMiddleName()+" "+e.getLastName();
		                String year = request.getParameter("year");
		                String monthNo = request.getParameter("monthNo");
		                MonthDao md = new MonthDao();
		                LevelDao ld = new LevelDao();
		                PayElementDao ped = new PayElementDao();
		                PayrollDao pd = new PayrollDao();
		                ResultSet rs = pd.getEmployeePayRoll(employeeId, monthNo, year);
		                MoneyFormatDao mfd = new MoneyFormatDao();
		                EmployeePayElementDeductionDao epedd = new EmployeePayElementDeductionDao();
		                PayElementDeductionTypeDao pedtd = new PayElementDeductionTypeDao();
		                %>
		                  <h4>
		                    <i class="fas fa-user"></i> &nbsp;&nbsp;<%=fullName.toUpperCase() %>
		                  </h4>
		                </div>
		                <!-- /.col -->
		              </div>
		              <!-- info row -->
		              <div class="row invoice-info">
		                <div class="col-sm-3 invoice-col">
		                  <br>
		                  <b>Staff No:</b>&nbsp;&nbsp;<%=e.getStaffId() %><br>
		                  <b>&nbsp;</b> &nbsp;
		                  <br/>
		                </div>
		                <div class="col-sm-3 invoice-col">
		                  <br>
		                  <b>Level:</b>&nbsp;&nbsp;<%=ld.getLevelName(e.getLevelId()).toUpperCase() %><br>
		                  <b>&nbsp;</b> &nbsp;
		                  <br/>
		                </div>
		                <div class="col-sm-3 invoice-col">
		                  <br>
		                  <b>Month:</b>&nbsp;&nbsp;<%=md.getName(monthNo).toUpperCase() %><br>
		                  <b>&nbsp;</b> &nbsp;
		                  <br/>
		                </div>
		                <div class="col-sm-3 invoice-col">
		                  <br>
		                  <b>Year:</b>&nbsp;&nbsp;<%=year %><br>
		                  <b>&nbsp;</b> &nbsp;
		                  <br/>
		                </div>
		                <!-- /.col -->
		              </div>
		              <!-- /.row -->
		
		              <!-- Table row -->
		              <div class="row">
		                <div class="col-12 table-responsive">
		                  <table class="table table-striped">
		                    <thead>
		                    <tr>
		                      <th>Pay Element</th>
		                      <th>Amount (&#8358;)</th>
		                      <th>Total  (&#8358;)</th>
		                    </tr>
		                    </thead>
		                    <tbody>
		                    <%
		                    double payrollTotal = pd.getEmployeePayrollTotal(employeeId, monthNo, year);
		                    String date = year+"-"+monthNo+"-"+"10";
		                    double deductionTotal = epedd.getEmployeePayElementDeductionTotal(employeeId, date);
		                    double grandTotal = payrollTotal - deductionTotal;
		                    int deductionCount = epedd.getEmployeeDeductionCount(employeeId, date);
		                    while(rs.next()){
		                    %>
		                    <tr>
		                      <td><%=ped.getName(rs.getInt("pay_element_id")).toUpperCase() %></td>
		                      <td><%=mfd.moneyFormatPattern(rs.getDouble("amount"), 2) %></td>
		                      <td></td>
		                    </tr>
		                    <%
		                    
		                    }
		                    %>
		                    <tr>
		                      <td></td>
		                      <td></td>
		                      <td><%=mfd.moneyFormatPattern(payrollTotal, 2)%></td>
		                    </tr>
		                    <% 
		                    if(deductionCount >= 1){
		                    	rs = epedd.getEmployeePayElementDeduction(employeeId, date);
		                    %>
		                    <tr>
		                    	<th>Deduction</th>
		                    	<th>Amount(&#8358;)</th>
		                    	<th>Total(&#8358;)</th>
		                    </tr>
		                    <%
		                    while(rs.next()){
		                    %>
		                    <tr>
		                    	<td><%=pedtd.getName(rs.getInt("pay_element_deduction_type_id")) %></td>
		                    	<td><%=mfd.moneyFormatPattern(rs.getDouble("amount"), 2) %></td>
		                    	<td></td>
		                    </tr>
		                    <%
		                    }
		                    %>
		                    <tr>
		                    	<td></td>
		                    	<td></td>
		                    	<td><%=mfd.moneyFormatPattern(deductionTotal, 2) %></td>
		                    </tr>
		                    <%
		                    } 
		                    %>
		                    <tr>
		                    	<td></td>
		                    	<td></td>
		                    	<td></td>
		                    </tr>
		                    <tr>
		                    	<td></td>
		                    	<th>Grand Total(&#8358;)</th>
		                    	<th><%=mfd.moneyFormatPattern(grandTotal, 2) %></th>
		                    </tr>
		                    </tbody>
		                  </table>
		                </div>
		                <!-- /.col -->
		              </div>
		              <!-- /.row -->
		              <div class="row">
                <!-- accepted payments column -->
                <div class="col-6">
                  <p class="lead"></p>
                  <!-- img src="../../dist/img/credit/visa.png" alt="Visa">
                  <img src="../../dist/img/credit/mastercard.png" alt="Mastercard">
                  <img src="../../dist/img/credit/american-express.png" alt="American Express">
                  <img src="../../dist/img/credit/paypal2.png" alt="Paypal"-->

                  <p class="text-muted well well-sm shadow-none" style="margin-top: 10px;">
                    
                  </p>
                </div>
                <!-- /.col -->
                
                <!-- /.col -->
              </div>
              <!-- /.row -->
              	<div class="row no-print">
                <div class="col-12">
                  <a onClick="window.print()" class="btn btn-default"><i class="fas fa-print"></i> Print</a>
                  <!-- button type="button" class="btn btn-primary float-right" style="margin-right: 5px;">
                    <i class="fas fa-download"></i> Generate PDF
                  </button-->
                </div>
              </div>
		            </div>
		            <!-- /.invoice -->
		          </div><!-- /.col -->
		        </div><!-- /.row -->
		      </div><!-- /.container-fluid -->
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

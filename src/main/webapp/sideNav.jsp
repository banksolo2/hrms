<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <!-- Main Sidebar Container -->
  <aside class="main-sidebar sidebar-dark-primary elevation-4">
    <!-- Brand Logo -->
    <a href="#" class="brand-link">
      <img src="dist/img/AdminLTELogo.png" alt="AdminLTE Logo" class="brand-image img-circle elevation-3"
           style="opacity: .8">
      <span class="brand-text font-weight-light">HRMS</span>
    </a>

    <!-- Sidebar -->
    <div class="sidebar">
      <!-- Sidebar user panel (optional) -->
      <div class="user-panel mt-3 pb-3 mb-3 d-flex">
        <div class="image">
          <img src="dist/img/user2-160x160.jpg" class="img-circle elevation-2" alt="User Image">
        </div>
        <div class="info">
          <a href="#" class="d-block">${firstName} ${lastName}</a>
        </div>
      </div>
      <%
      String parent = "";
      String pages = "";
      if (session.getAttribute("parent") != null && session.getAttribute("page") != null){
      	parent =(String) session.getAttribute("parent");
      	pages = (String) session.getAttribute("page");
      }
      boolean isSuperAdmin = false;
      boolean isDepartmentHead = false;
      boolean isHrAdmin = false;
      boolean isSupervisor = false;
      if(session.getAttribute("email") != null){
    	  isSuperAdmin = (boolean) session.getAttribute("isSuperAdmin");
    	  isDepartmentHead = (boolean) session.getAttribute("isDepartmentHead");
    	  isHrAdmin = (boolean) session.getAttribute("isHrAdmin");
    	  isSupervisor = (boolean) session.getAttribute("isSupervisor");
      }
      %>

      <!-- Sidebar Menu -->
      <nav class="mt-2">
        <ul class="nav nav-pills nav-sidebar flex-column" data-widget="treeview" role="menu" data-accordion="false">
          <!-- Add icons to the links using the .nav-icon class
               with font-awesome or any other icon font library -->
          <li class="nav-item has-treeview menu-open">
            <% if(parent.equals("home")){ %><a href="index.jsp" class="nav-link active">
            <%}else{%><a href="index.jsp" class="nav-link"><% } %>
              <i class="nav-icon fas fa-tachometer-alt"></i>
              <p>
                Dashboard
                
              </p>
            </a>
         
          </li>
          <li class="nav-item has-treeview">
          
            <%
            
            if(parent.equals("profile")){
            %>
            <a href="#" class="nav-link active">
            <%
            }
            else{
            %>
            <a href="#" class="nav-link">
            <%} %>
         
              <i class="nav-icon fas fa-user"></i>
              <p>
                Profile
                <i class="fas fa-angle-left right"></i>
              </p>
            </a>
            <ul class="nav nav-treeview">
            	<li class="nav-item">
            	<% if(pages.equals("edit_profile")){ %> <a href="editProfile.jsp" class="nav-link active">
	            <%}else{ %>  <a href="editProfile.jsp" class="nav-link"><%} %>
	                  <i class="far fa-circle nav-icon"></i>
	                  <p>Edit Profile</p>
	                </a>
              	</li>
              	<li class="nav-item">
              		<%
              		if(pages.equals("change_password")){
              		%>
	                <a href="changePassword.jsp" class="nav-link active">
	                <%
              		}else{
	                %>
	                <a href="changePassword.jsp" class="nav-link">
	                <%} %>
	                  <i class="far fa-circle nav-icon"></i>
	                  <p>Change Password</p>
	                </a>
              	</li>
            </ul>
          </li>
          <li class="nav-item has-treeview">
            <%if(parent.equals("leave_plan")){ %><a href="#" class="nav-link active">
            <%}else{ %><a href="#" class="nav-link"><%} %>
              <i class="nav-icon fas fa-tree"></i>
              <p>
                Leave Plan
                <i class="fas fa-angle-left right"></i>
              </p>
            </a>
            <ul class="nav nav-treeview">
            	<li class="nav-item">
	                <%if(pages.equals("employee_leave_plan_setup")){%><a href="employeeLeavePlanSetup.jsp" class="nav-link active">
	                <%}else{ %><a href="employeeLeavePlanSetup.jsp" class="nav-link"><%} %>
	                  <i class="far fa-circle nav-icon"></i>
	                  <p>Employee Setup</p>
	                </a>
              	</li>
              	<%
              	 if(isDepartmentHead == true){
              	%>
              	<li class="nav-item">
	                <% if(pages.equals("hod_leave_plan_setup")){%><a href="hodLeavePlanSetup.jsp" class="nav-link active">
	                <%}else{ %><a href="hodLeavePlanSetup.jsp" class="nav-link"><%} %>
	                  <i class="far fa-circle nav-icon"></i>
	                  <p>Department Head Setup</p>
	                </a> 
              	</li>
              	<%}%>
              	
            </ul>
          </li>
          <li class="nav-item has-treeview">
            <% if(parent.equals("leave")){ %><a href="#" class="nav-link active">
            <%}else{ %><a href="#" class="nav-link"><%} %>
              <i class="nav-icon fas fa-tree"></i>
              <p>
                Leave
                <i class="fas fa-angle-left right"></i>
              </p>
            </a>
            <ul class="nav nav-treeview">
            	<li class="nav-item">
	                <% if(pages.equals("employee_leave_setup")){ %><a href="employeeLeaveSetup.jsp" class="nav-link active">
	                <%}else{ %><a href="employeeLeaveSetup.jsp" class="nav-link"><% } %>
	                  <i class="far fa-circle nav-icon"></i>
	                  <p>Employee Leave Setup</p>
	                </a>
              	</li>
              	<%if(isSupervisor == true || isSuperAdmin == true){ %>
              	<li class="nav-item">
	                <% if(pages.equals("supervisor_leave_setup")){ %><a href="SupervisorLeaveSetup.jsp" class="nav-link active">
	                <%}else{ %><a href="SupervisorLeaveSetup.jsp" class="nav-link"><%} %>
	                  <i class="far fa-circle nav-icon"></i>
	                  <p>Supervisor Leave Setup</p>
	                </a>
              	</li>
              	<%} %>
            </ul>
            
          </li>
          <li class="nav-item has-treeview">
            <%if(parent.equals("support_ticket")){ %><a href="#" class="nav-link active">
            <%}else{ %><a href="#" class="nav-link"> <%} %>
              <i class="nav-icon fas fa-file"></i>
              <p>
                Support Ticket
                <i class="fas fa-angle-left right"></i>
              </p>
            </a>
            <ul class="nav nav-treeview">
            	<li class="nav-item">
	                <%if(pages.equals("employee_support_ticket_setup")){ %><a href="employeeSupportTicketSetup.jsp" class="nav-link active">
	                <% }else{ %><a href="employeeSupportTicketSetup.jsp" class="nav-link"> <%} %>
	                  <i class="far fa-circle nav-icon"></i>
	                  <p>Support Ticket Setup</p>
	                </a>
              	</li>
            </ul>
         </li>
         <li class="nav-item has-treeview">
         	<%if(parent.equals("requisition")){ %><a href="#" class="nav-link active">
         	<%}else{ %><a href="#" class="nav-link "><%} %>
         		<i class="nav-icon fas fa-file"></i>
         		<p>
                Requisition 
                <i class="fas fa-angle-left right"></i>
              </p>
         	</a>
         	<ul class="nav nav-treeview">
         		<li class="nav-item">
	                <%if(pages.equals("requisition_setup")){ %><a href="requisitionSetup.jsp" class="nav-link active">
	                <% }else{ %><a href="requisitionSetup.jsp" class="nav-link"> <%} %>
	                  <i class="far fa-circle nav-icon"></i>
	                  <p>Requisition Setup</p>
	                </a>
              	</li>
         	</ul>
         </li>
         <li class="nav-item has-treeview">
            <%if(parent.equals("payroll")){ %><a href="#" class="nav-link active">
            <%}else{ %><a href="#" class="nav-link"><%} %>
              <i class="nav-icon fas fa-file"></i>
              <p>
                Payroll
                <i class="fas fa-angle-left right"></i>
              </p>
            </a>
            <ul class="nav nav-treeview">
            	<li class="nav-item">
	                <%if(pages.equals("view_payslip")){%><a href="viewPayslip.jsp" class="nav-link active">
	                <%}else{ %><a href="viewPayslip.jsp" class="nav-link"><%} %>
	                  <i class="far fa-circle nav-icon"></i>
	                  <p>View Payslip</p>
	                </a>
              	</li>
              	
            </ul>
          </li>
          <% if(isHrAdmin == true || isSuperAdmin == true){ %>
          <li class="nav-item has-treeview">
            <%if(parent.equals("admin")){ %><a href="#" class="nav-link active">
            <%}else{ %><a href="#" class="nav-link "><%} %>
              <i class="nav-icon fas fa-users"></i>
              <p>
                Admin
                <i class="fas fa-angle-left right"></i>
              </p>
            </a>
            <ul class="nav nav-treeview">
            	<li class="nav-item">
	                <%if(pages.equals("core_setup")){ %><a href="coreSetup.jsp" class="nav-link active">
	                <%}else{%><a href="coreSetup.jsp" class="nav-link"><%} %>
	                  <i class="far fa-circle nav-icon"></i>
	                  <p>Core Setup</p>
	                </a>
              	</li>
              	<li class="nav-item">
	                <%if(pages.equals("admin_leave_setup")){ %><a href="adminLeaveSetup.jsp" class="nav-link active">
	                <%}else{ %><a href="adminLeaveSetup.jsp" class="nav-link "><% } %>
	                  <i class="far fa-circle nav-icon"></i>
	                  <p>Leave Setup</p>
	                </a>
              	</li>
              	<li class="nav-item">
	                <%if(pages.equals("admin_payroll_setup")){ %><a href="adminPayrollSetup.jsp" class="nav-link active">
	                <%}else{ %><a href="adminPayrollSetup.jsp" class="nav-link "><% } %>
	                  <i class="far fa-circle nav-icon"></i>
	                  <p>Payroll Setup</p>
	                </a>
              	</li>
              	<%if(isSuperAdmin) { %>
              	<li class="nav-item">
              		<% if(pages.equals("developer_setup")){ %> <a href="adminDeveloperSetup.jsp" class="nav-link active">
	                <% }else{ %><a href="adminDeveloperSetup.jsp" class="nav-link"><%} %>
	                  <i class="far fa-circle nav-icon"></i>
	                  <p>Developer Setup</p>
	                </a>
              	</li>
              	<%} %>
            </ul>
           </li>
           <%} %>
        </ul>
      </nav>
      <!-- /.sidebar-menu -->
    </div>
    <!-- /.sidebar -->
  </aside>
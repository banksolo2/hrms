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

      <!-- Sidebar Menu -->
      <nav class="mt-2">
        <ul class="nav nav-pills nav-sidebar flex-column" data-widget="treeview" role="menu" data-accordion="false">
          <!-- Add icons to the links using the .nav-icon class
               with font-awesome or any other icon font library -->
          <li class="nav-item has-treeview menu-open">
            <a href="index.jsp" class="nav-link active">
              <i class="nav-icon fas fa-tachometer-alt"></i>
              <p>
                Dashboard
                
              </p>
            </a>
         
          </li>
          <li class="nav-item has-treeview">
            <a href="#" class="nav-link">
              <i class="nav-icon fas fa-user"></i>
              <p>
                Profile
                <i class="fas fa-angle-left right"></i>
              </p>
            </a>
            <ul class="nav nav-treeview">
            	<li class="nav-item">
	                <a href="editProfile.jsp" class="nav-link">
	                  <i class="far fa-circle nav-icon"></i>
	                  <p>Edit Profile</p>
	                </a>
              	</li>
              	<li class="nav-item">
	                <a href="changePassword.jsp" class="nav-link">
	                  <i class="far fa-circle nav-icon"></i>
	                  <p>Change Password</p>
	                </a>
              	</li>
            </ul>
          </li>
          <li class="nav-item has-treeview">
            <a href="#" class="nav-link">
              <i class="nav-icon fas fa-tree"></i>
              <p>
                Leave Plan
                <i class="fas fa-angle-left right"></i>
              </p>
            </a>
            <ul class="nav nav-treeview">
            	<li class="nav-item">
	                <a href="employeeLeavePlanSetup.jsp" class="nav-link">
	                  <i class="far fa-circle nav-icon"></i>
	                  <p>Employee Setup</p>
	                </a>
              	</li>
              	<li class="nav-item">
	                <a href="hodLeavePlanSetup.jsp" class="nav-link">
	                  <i class="far fa-circle nav-icon"></i>
	                  <p>Department Head Setup</p>
	                </a> 
              	</li>
              	
            </ul>
          </li>
          <li class="nav-item has-treeview">
            <a href="#" class="nav-link">
              <i class="nav-icon fas fa-tree"></i>
              <p>
                Leave
                <i class="fas fa-angle-left right"></i>
              </p>
            </a>
            <ul class="nav nav-treeview">
            	
            </ul>
          </li>
          <li class="nav-item has-treeview">
            <a href="#" class="nav-link">
              <i class="nav-icon fas fa-users"></i>
              <p>
                Admin
                <i class="fas fa-angle-left right"></i>
              </p>
            </a>
            <ul class="nav nav-treeview">
            	<li class="nav-item">
	                <a href="adminEmployeeSetup.jsp" class="nav-link">
	                  <i class="far fa-circle nav-icon"></i>
	                  <p>Employee Setup</p>
	                </a>
              	</li>
              	<li class="nav-item">
	                <a href="adminDepartmentSetup.jsp" class="nav-link">
	                  <i class="far fa-circle nav-icon"></i>
	                  <p>Department Setup</p>
	                </a>
              	</li>
              	<li class="nav-item">
	                <a href="adminDeveloperSetup.jsp" class="nav-link">
	                  <i class="far fa-circle nav-icon"></i>
	                  <p>Developer Setup</p>
	                </a>
              	</li>
              	<li class="nav-item">
	                <a href="createLevel.jsp" class="nav-link">
	                  <i class="far fa-circle nav-icon"></i>
	                  <p>Create Level</p>
	                </a>
              	</li>
              	<li class="nav-item">
	                <a href="allLevels.jsp" class="nav-link">
	                  <i class="far fa-circle nav-icon"></i>
	                  <p>All Levels</p>
	                </a>
              	</li>
              	<li class="nav-item">
	                <a href="createRedZone.jsp" class="nav-link">
	                  <i class="far fa-circle nav-icon"></i>
	                  <p>Create Red Zone</p>
	                </a>
              	</li>
              	<li class="nav-item">
	                <a href="createRedZoneAllDepartment.jsp" class="nav-link">
	                  <i class="far fa-circle nav-icon"></i>
	                  <p>Create Red Zone All Department</p>
	                </a>
              	</li>
              	<li class="nav-item">
	                <a href="allRedZone.jsp" class="nav-link">
	                  <i class="far fa-circle nav-icon"></i>
	                  <p>All Red Zone</p>
	                </a>
              	</li>
              	<li class="nav-item">
	                <a href="createBranch.jsp" class="nav-link">
	                  <i class="far fa-circle nav-icon"></i>
	                  <p>Create Branch</p>
	                </a>
              	</li>
              	<li class="nav-item">
	                <a href="allBranches.jsp" class="nav-link">
	                  <i class="far fa-circle nav-icon"></i>
	                  <p>All Branches</p>
	                </a>
              	</li>
              	<li class="nav-item">
	                <a href="createCompany.jsp" class="nav-link">
	                  <i class="far fa-circle nav-icon"></i>
	                  <p>Create Company</p>
	                </a>
              	</li>
              	<li class="nav-item">
              		<a href="allCompanies.jsp" class="nav-link">
              			<i class="far fa-circle nav-icon"></i>
              			<p>All Companies</p>
              		</a>
                </li>
                <li class="nav-item">
              		<a href="createHoliday.jsp" class="nav-link">
              			<i class="far fa-circle nav-icon"></i>
              			<p>Create Holiday</p>
              		</a>
                </li>
                <li class="nav-item">
              		<a href="allHolidays.jsp" class="nav-link">
              			<i class="far fa-circle nav-icon"></i>
              			<p>All Holidays</p>
              		</a>
                </li>
                <li class="nav-item">
              		<a href="createLeaveType.jsp" class="nav-link">
              			<i class="far fa-circle nav-icon"></i>
              			<p>Create Leave Type</p>
              		</a>
                </li>
                <li class="nav-item">
              		<a href="allLeaveTypes.jsp" class="nav-link">
              			<i class="far fa-circle nav-icon"></i>
              			<p>All Leave Types</p>
              		</a>
                </li>
            </ul>
           </li>
        </ul>
      </nav>
      <!-- /.sidebar-menu -->
    </div>
    <!-- /.sidebar -->
  </aside>
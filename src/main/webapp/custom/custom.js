$(document).ready(function(){
	
	
	$('#createCompany').one('submit', function() {
	    $(this).find('input[type="submit"]').attr('disabled','disabled');
	});

	$('#createCompany').validate({
		rules:{
			name: {
				required: true,
				minlength: 3
			},
			codeName: {
				required : true
			}
		},
		messages: {
			name: {
				required: "Please provide name.",
				minlength: "Name must be at least 3 characters long"
			},
			codeName: {
				required: "Please provide code name.",
			}
		},
		errorElement: 'span',
	    errorPlacement: function (error, element) {
	      error.addClass('invalid-feedback');
	      element.closest('.form-group').append(error);
	    },
	    highlight: function (element, errorClass, validClass) {
	        $(element).addClass('is-invalid');
	      },
	      unhighlight: function (element, errorClass, validClass) {
	        $(element).removeClass('is-invalid');
	      }
	});
	
	$('#changePassword').validate({
		rules:{
			password: {
				required: true
			},
			confirmPassword: {
				required: true
			}
		},
		messages:{
			password: {
				required: "Please provide password."
			},
			confirmPassword: {
				required: "Please provide confirm password."
			}
		},
		errorElement: 'span',
	    errorPlacement: function (error, element) {
	      error.addClass('invalid-feedback');
	      element.closest('.form-group').append(error);
	    },
	    highlight: function (element, errorClass, validClass) {
	        $(element).addClass('is-invalid');
	      },
	      unhighlight: function (element, errorClass, validClass) {
	        $(element).removeClass('is-invalid');
	      }
	});
	
	$('#createBranch').validate({
		rules: {
			name: {
				required: true
			}
		},
		messages: {
			name: {
				required: "Please provide name."
			}
		},
		errorElement: 'span',
		errorPlacement: function (error, element) {
		      error.addClass('invalid-feedback');
		      element.closest('.form-group').append(error);
		    },
		    highlight: function (element, errorClass, validClass) {
		        $(element).addClass('is-invalid');
		      },
		      unhighlight: function (element, errorClass, validClass) {
		        $(element).removeClass('is-invalid');
		      }
	});
	
	$('#createEmployee').validate({
		rules:{
			title: {
				required: true
			},
			firstName: {
				required: true
			},
			middleName: {
				required: true
			},
			lastName: {
				required: true
			},
			nameInitials: {
				required: true
			},
			email: {
				required: true,
				email: true
			},
			employeeStatusId:{
				required: true
			},
			dateOfEmployment: {
				required: true
			},
			mobileNumber:{
				required: true
			},
			stateId:{
				required: true
			},
			departmentId:{
				required: true
			},
			levelId:{
				required: true
			},
			staffId:{
				required: true
			},
			branchId:{
				required: true
			},
			companyId:{
				required: true
			},
			genderId:{
				required: true
			},
			martialStatusId:{
				required: true
			},
			personalEmail: {
				required: true,
				email: true
			},
			currentAddress: {
				required: true
			},
			dateOfBirth:{
				required: true
			},
			personalProductionTarget:{
				required: true
			}
		},
		messages:{
			title: {
				required: "Please provide title."
			},
			firstName: {
				required: "Please provide first name."
			},
			middleName:{
				required: "Please provide middle name."
			},
			lastName:{
				required: "Please provide last name."
			},
			nameInitials:{
				required: "Please provide name initials."
			},
			email:{
				required: "Please provide email.",
				email: "please provide a valid email address."
			},
			employeeStatusId:{
				required: "please provide employee status."
			},
			dateOfEmployment:{
				required: "Please provide date of employment."
			},
			mobileNumber:{
				required: "Please provide mobile number."
			},
			stateId:{
				required: "Please provide state of origin."
			},
			departmentId:{
				required: "Please provide department."
			},
			levelId:{
				required: "Please provide level."
			},
			staffId:{
				required: "Please provide staff ID."
			},
			branchId:{
				required: "Please provide branch."
			},
			companyId:{
				required: "Please provide company."
			},
			genderId:{
				required: "Please provide gender."
			},
			martialStatusId: {
				required: "Please provide martial status."
			},
			personalEmail: {
				required: "Please provide personal email.",
				email: "Please provide a valid email address."
			},
			currentAddress: {
				required: "Please provide current address."
			},
			dateOfBirth: {
				required: "Please provide date of birth."
			},
			personalProductionTarget:{
				required: "Please provide personal production target."
			}
		},
		errorElement: 'span',
		errorPlacement: function (error, element) {
		      error.addClass('invalid-feedback');
		      element.closest('.form-group').append(error);
		    },
	    highlight: function (element, errorClass, validClass) {
	        $(element).addClass('is-invalid');
	      },
	      unhighlight: function (element, errorClass, validClass) {
	        $(element).removeClass('is-invalid');
	      }
	});
	
	$('#createGender').validate({
		rules: {
			name : {
				required: true
			}
		},
		messages: {
			name: {
				required: "Please provide name."
			}
		},
		errorElement: 'span',
		errorPlacement: function (error, element) {
		      error.addClass('invalid-feedback');
		      element.closest('.form-group').append(error);
		    },
	    highlight: function (element, errorClass, validClass) {
	        $(element).addClass('is-invalid');
	      },
	      unhighlight: function (element, errorClass, validClass) {
	        $(element).removeClass('is-invalid');
	      }
		
	});
	
	$('#createMartialStatus').validate({
		rules: {
			name: {
				required: true
			}
		},
		messages: {
			name: {
				required: "Please provide name."
			}
		},
		errorElement: 'span',
		errorPlacement: function (error, element) {
		      error.addClass('invalid-feedback');
		      element.closest('.form-group').append(error);
		    },
	    highlight: function (element, errorClass, validClass) {
	        $(element).addClass('is-invalid');
	      },
	      unhighlight: function (element, errorClass, validClass) {
	        $(element).removeClass('is-invalid');
	      }
	});
	
	$('#createRole').validate({
		rules: {
			name: {
				required: true
			}
		},
		message: {
			name: {
				required: "Please provide name."
			}
		},
		errorElement: 'span',
		errorPlacement: function (error, element) {
		      error.addClass('invalid-feedback');
		      element.closest('.form-group').append(error);
		    },
	    highlight: function (element, errorClass, validClass) {
	        $(element).addClass('is-invalid');
	      },
	      unhighlight: function (element, errorClass, validClass) {
	        $(element).removeClass('is-invalid');
	      }
	});
	
	$('#createState').validate({
		rules:{
			name: {
				required: true
			}
		},
		messages:{
			name: {
				required: "Please provide name."
			}
		},
		errorElement: 'span',
		errorPlacement: function (error, element) {
		      error.addClass('invalid-feedback');
		      element.closest('.form-group').append(error);
		    },
	    highlight: function (element, errorClass, validClass) {
	        $(element).addClass('is-invalid');
	      },
	      unhighlight: function (element, errorClass, validClass) {
	        $(element).removeClass('is-invalid');
	      }
	});
	
	$('#editBranch').validate({
		rules:{
			name: {
				required: true
			}
		},
		messages:{
			name: {
				required: "Please provide name."
			}
		},
		errorElement: 'span',
		errorPlacement: function (error, element) {
		      error.addClass('invalid-feedback');
		      element.closest('.form-group').append(error);
		    },
	    highlight: function (element, errorClass, validClass) {
	        $(element).addClass('is-invalid');
	      },
	      unhighlight: function (element, errorClass, validClass) {
	        $(element).removeClass('is-invalid');
	      }
	});
	
	$('#editCompany').validate({
		rules: {
			name: {
				required : true
			},
			codeName: {
				required: true
			}
		},
		messages:{
			name : {
				required: "Please provide name."
			},
			codeName: {
				required: "Please provide code name."
			}
		},
		errorElement: 'span',
		errorPlacement: function (error, element) {
		      error.addClass('invalid-feedback');
		      element.closest('.form-group').append(error);
		    },
	    highlight: function (element, errorClass, validClass) {
	        $(element).addClass('is-invalid');
	      },
	      unhighlight: function (element, errorClass, validClass) {
	        $(element).removeClass('is-invalid');
	      }
	});
	
	$('#editEmployee').validate({
		rules: {
			firstName: {
				required: true
			},
			middleName: {
				required: true
			},
			lastName: {
				required: true
			},
			nameInitials: {
				required: true
			},
			email: {
				required: true,
				email: true
			},
			dateOfEmployment: {
				required: true
			},
			mobileNumber: {
				required: true,
				numeric: true
			},
			staffId: {
				required: true
			},
			personalEmail: {
				required: true,
				email: true
			},
			currentAddress: {
				required: true
			},
			dateOfBirth:{
				required: true
			}
		},
		messages:{
			firstName: {
				required: "Please provide first name."
			},
			middleName: {
				required: "Please provide middle name."
			},
			lastName: {
				required: "Please provide last name."
			},
			nameInitials: {
				required: "Please provide name initials."
			},
			email: {
				required: "Please provide email address.",
				email : "Please provide a valid email address."
			},
			dateOfEmployment: {
				required: "Please provide date of employment."
			},
			mobileNumber: {
				required: "Please provide mobile number.",
				numeric: "Please provide a valid phone number"
			},
			staffId:{
				required: "Please provide staff ID.",
			},
			personalEmail:{
				required: "Please provide personal email.",
				email: "Please provide a valid email address"
			},
			currentAddress:{
				required: "Please provide current address."
			},
			dateOfBirth:{
				required: "Please provide date of birth."
			}
		},
		errorElement: 'span',
		errorPlacement: function (error, element) {
		      error.addClass('invalid-feedback');
		      element.closest('.form-group').append(error);
		    },
	    highlight: function (element, errorClass, validClass) {
	        $(element).addClass('is-invalid');
	      },
	      unhighlight: function (element, errorClass, validClass) {
	        $(element).removeClass('is-invalid');
	      }
	});
	
	$("#editProfile").validate({
		rules: {
			firstName: {
				required: true
			},
			middleName: {
				required: true
			},
			lastName: {
				required: true
			},
			nameInitials: {
				required: true
			},
			email: {
				required: true,
				email: true
			},
			dateOfEmployment: {
				required: true
			},
			mobileNumber: {
				required: true,
				numeric: true
			},
			staffId: {
				required: true
			},
			personalEmail: {
				required: true,
				email: true
			},
			currentAddress: {
				required: true
			},
			dateOfBirth:{
				required: true
			}
		},
		messages:{
			firstName: {
				required: "Please provide first name."
			},
			middleName: {
				required: "Please provide middle name."
			},
			lastName: {
				required: "Please provide last name."
			},
			nameInitials: {
				required: "Please provide name initials."
			},
			email: {
				required: "Please provide email address.",
				email : "Please provide a valid email address."
			},
			dateOfEmployment: {
				required: "Please provide date of employment."
			},
			mobileNumber: {
				required: "Please provide mobile number.",
				numeric: "Please provide a valid phone number"
			},
			staffId:{
				required: "Please provide staff ID.",
			},
			personalEmail:{
				required: "Please provide personal email.",
				email: "Please provide a valid email address"
			},
			currentAddress:{
				required: "Please provide current address."
			},
			dateOfBirth:{
				required: "Please provide date of birth."
			}
		},
		errorElement: 'span',
		errorPlacement: function (error, element) {
		      error.addClass('invalid-feedback');
		      element.closest('.form-group').append(error);
		    },
	    highlight: function (element, errorClass, validClass) {
	        $(element).addClass('is-invalid');
	      },
	      unhighlight: function (element, errorClass, validClass) {
	        $(element).removeClass('is-invalid');
	      }

	});
	
	$('#createHoliday').validate({
		rules: {
			name : {
				required : true
			},
			description : {
				required : true
			},
			dateAt: {
				required : true
			}
		},
		messages:{
			name: {
				required: "Please provide name"
			},
			description: {
				required: "Please provide description"
			},
			dateAt: {
				required: "Please provide date"
			}
		},
		errorElement: 'span',
		errorPlacement: function (error, element) {
		      error.addClass('invalid-feedback');
		      element.closest('.form-group').append(error);
		    },
	    highlight: function (element, errorClass, validClass) {
	        $(element).addClass('is-invalid');
	      },
	      unhighlight: function (element, errorClass, validClass) {
	        $(element).removeClass('is-invalid');
	      }
	});
	
	$('#editHoliday').validate({
		rules: {
			name : {
				required : true
			},
			description : {
				required : true
			},
			dateAt: {
				required : true
			}
		},
		messages:{
			name: {
				required: "Please provide name"
			},
			description: {
				required: "Please provide description"
			},
			dateAt: {
				required: "Please provide date"
			}
		},
		errorElement: 'span',
		errorPlacement: function (error, element) {
		      error.addClass('invalid-feedback');
		      element.closest('.form-group').append(error);
		    },
	    highlight: function (element, errorClass, validClass) {
	        $(element).addClass('is-invalid');
	      },
	      unhighlight: function (element, errorClass, validClass) {
	        $(element).removeClass('is-invalid');
	      }
	});
	$("#createLeaveStatus").validate({
		rules: {
			name: {
				required: true
			}
		},
		messages: {
			required: "Please provide leave status name."
		},
		errorElement: 'span',
		errorPlacement: function (error, element) {
		      error.addClass('invalid-feedback');
		      element.closest('.form-group').append(error);
		    },
	    highlight: function (element, errorClass, validClass) {
	        $(element).addClass('is-invalid');
	      },
	      unhighlight: function (element, errorClass, validClass) {
	        $(element).removeClass('is-invalid');
	      }
	
	});
	
	$('#editLeaveStatus').validate({
		rules: {
			name: {
				required: true
			}
		},
		messages: {
			name: {
				required: "Please provide name.."
			}
		},
		errorElement: 'span',
		errorPlacement: function (error, element) {
		      error.addClass('invalid-feedback');
		      element.closest('.form-group').append(error);
		    },
	    highlight: function (element, errorClass, validClass) {
	        $(element).addClass('is-invalid');
	      },
	      unhighlight: function (element, errorClass, validClass) {
	        $(element).removeClass('is-invalid');
	      }
	});
	
	$('#createLeaveType').validate({
		rules: {
			name: {
				required: true
			},
			days: {
				required: true
			}
		},
		messages: {
			name: {
				required: "Please provide name"
			},
			days: {
				required: "Please provide days"
			}
		},
		errorElement: 'span',
		errorPlacement: function (error, element) {
		      error.addClass('invalid-feedback');
		      element.closest('.form-group').append(error);
		    },
	    highlight: function (element, errorClass, validClass) {
	        $(element).addClass('is-invalid');
	      },
	      unhighlight: function (element, errorClass, validClass) {
	        $(element).removeClass('is-invalid');
	      }
	});
	
	$('#editLeaveType').validate({
		rules: {
			name: {
				required: true
			},
			days: {
				required: true
			}
		},
		messages: {
			name: {
				required: "Please provide name"
			},
			days: {
				required: "Please provide days"
			}
		},
		errorElement: 'span',
		errorPlacement: function (error, element) {
		      error.addClass('invalid-feedback');
		      element.closest('.form-group').append(error);
		    },
	    highlight: function (element, errorClass, validClass) {
	        $(element).addClass('is-invalid');
	      },
	      unhighlight: function (element, errorClass, validClass) {
	        $(element).removeClass('is-invalid');
	      }
	});
	
	$('#createDepartment').validate({
		rules: {
			name: {
				required : true
			}
		},
		messages: {
			name: {
				required : "Please provide name"
			}
		},
		errorElement: 'span',
		errorPlacement: function (error, element) {
		      error.addClass('invalid-feedback');
		      element.closest('.form-group').append(error);
		    },
	    highlight: function (element, errorClass, validClass) {
	        $(element).addClass('is-invalid');
	      },
	      unhighlight: function (element, errorClass, validClass) {
	        $(element).removeClass('is-invalid');
	      }
	});
	
	$('#editDepartment').validate({
		rules: {
			name: {
				required : true
			}
		},
		messages: {
			name: {
				required : "Please provide name"
			}
		},
		errorElement: 'span',
		errorPlacement: function (error, element) {
		      error.addClass('invalid-feedback');
		      element.closest('.form-group').append(error);
		    },
	    highlight: function (element, errorClass, validClass) {
	        $(element).addClass('is-invalid');
	      },
	      unhighlight: function (element, errorClass, validClass) {
	        $(element).removeClass('is-invalid');
	      }
	});
	
	$('#createRedZone').validate({
		rules: {
			departmentId : {
				required : true
			},
			dateFrom: {
				required : true
			},
			dateTo: {
				required : true
			},
			description : {
				required : true
			}
		},
		messages: {
			departmentId: {
				required: "Please provide department"
			},
			dateFrom : {
				required: "Please provide date from"
			},
			dateTo : {
				required: "Please provide date to"
			},
			description: {
				required: "Please provide description"
			}
		},
		errorElement: 'span',
		errorPlacement: function (error, element) {
		      error.addClass('invalid-feedback');
		      element.closest('.form-group').append(error);
		    },
	    highlight: function (element, errorClass, validClass) {
	        $(element).addClass('is-invalid');
	      },
	      unhighlight: function (element, errorClass, validClass) {
	        $(element).removeClass('is-invalid');
	      }
	});
	$('#createRedZoneAllDepartment').validate({
		rules: {
			dateFrom: {
				required : true
			},
			dateTo: {
				required : true
			},
			description : {
				required : true
			}
		},
		messages: {
			dateFrom : {
				required: "Please provide date from"
			},
			dateTo : {
				required: "Please provide date to"
			},
			description: {
				required: "Please provide description"
			}
		},
		errorElement: 'span',
		errorPlacement: function (error, element) {
		      error.addClass('invalid-feedback');
		      element.closest('.form-group').append(error);
		    },
	    highlight: function (element, errorClass, validClass) {
	        $(element).addClass('is-invalid');
	      },
	      unhighlight: function (element, errorClass, validClass) {
	        $(element).removeClass('is-invalid');
	      }
	});
	$('#createDepartmentHead').validate({
		rules: {
			employeeId : {
				required : true
			},
			departmentId : {
				required : true
			}
		},
		messages : {
			employeeId : {
				required : "Please provide employee"
			},
			departmentId : {
				required : "Please provide department"
			}
		},
		errorElement: 'span',
		errorPlacement: function (error, element) {
		      error.addClass('invalid-feedback');
		      element.closest('.form-group').append(error);
		    },
	    highlight: function (element, errorClass, validClass) {
	        $(element).addClass('is-invalid');
	      },
	      unhighlight: function (element, errorClass, validClass) {
	        $(element).removeClass('is-invalid');
	      }
	});
	
	$('#createLevel').validate({
		rules: {
			name: {
				required : true
			},
			leaveDays : {
				required : true
			}
		},
		messages: {
			name: {
				required : "Please provide name"
			},
			leaveDays: {
				required : "Please provide leave days"
			}
		},
		errorElement: 'span',
		errorPlacement: function (error, element) {
		      error.addClass('invalid-feedback');
		      element.closest('.form-group').append(error);
		    },
	    highlight: function (element, errorClass, validClass) {
	        $(element).addClass('is-invalid');
	      },
	      unhighlight: function (element, errorClass, validClass) {
	        $(element).removeClass('is-invalid');
	      }
	});
	$('#createLeavePlan').validate({
		rules : {
			dates : {
				required : true
			},
			leavePlanStatusId : {
				required : true
			}
		},
		messages : {
			dates : {
				required : "Please provide start to end dates"
			},
			leavePlanStatusId: {
				required : "Please provide save as option"
			}
		},
		errorElement: 'span',
		errorPlacement: function (error, element) {
		      error.addClass('invalid-feedback');
		      element.closest('.form-group').append(error);
		    },
	    highlight: function (element, errorClass, validClass) {
	        $(element).addClass('is-invalid');
	      },
	      unhighlight: function (element, errorClass, validClass) {
	        $(element).removeClass('is-invalid');
	      }
	});
	
	$('#editLeavePlanSentForCorrection').validate({
		rules: {
			dates : {
				requried : true
			},
			comment : {
				required : true
			}
		},
		message: {
			dates : {
				required : "Please provide start to end date"
			},
			comment : {
				required : "Please provide comment"
			}
		},
		errorElement: 'span',
		errorPlacement: function (error, element) {
		      error.addClass('invalid-feedback');
		      element.closest('.form-group').append(error);
		    },
	    highlight: function (element, errorClass, validClass) {
	        $(element).addClass('is-invalid');
	      },
	      unhighlight: function (element, errorClass, validClass) {
	        $(element).removeClass('is-invalid');
	      }
	});
	$('#editLeavePlanPendingApproval').validate({
		rules : {
			leavePlanStatusId : {
				required : true
			},
			comment : {
				required : true
			}
		},
		messages : {
			leavePlanStatusId : {
				required : "Please provide a save as option"
			},
			comment: {
				required : "Please provide a comment"
			}
		},
		errorElement: 'span',
		errorPlacement: function (error, element) {
		      error.addClass('invalid-feedback');
		      element.closest('.form-group').append(error);
		    },
	    highlight: function (element, errorClass, validClass) {
	        $(element).addClass('is-invalid');
	      },
	      unhighlight: function (element, errorClass, validClass) {
	        $(element).removeClass('is-invalid');
	      }
	});
	
	$('#makeLeaveRequestWithPay').validate({
		rules : {
			leaveTypeId : {
				required : true
			},
			dates : {
				required : true
			},
			inLineWithLeavePlan : { 
				required : true
			},
			primaryReliefOfficeId : {
				required : true
			},
			leaveStatusId : {
				required : true
			},
			staffToNotify : {
				required : true
			}
		},
		messages : {
			leaveType : {
				required : "Please provide leave type"
			},
			dates : {
				required : "Please provide start and end dates"
			},
			inLineWithLeavePlan : {
				required : "Please select a inline with leave plan option"
			},
			primaryReliefOfficeId : {
				required : "Please provide primary relief officer"
			},
			leaveStatusId : {
				required : "Please select save as option"
			},
			staffToNotify : {
				required : "Please select a staff to be notified"
			}
		},
		errorElement: 'span',
		errorPlacement: function (error, element) {
		      error.addClass('invalid-feedback');
		      element.closest('.form-group').append(error);
		    },
	    highlight: function (element, errorClass, validClass) {
	        $(element).addClass('is-invalid');
	      },
	      unhighlight: function (element, errorClass, validClass) {
	        $(element).removeClass('is-invalid');
	      }
	});
	
	$('#editSendBackForCorrectWithPay').validate({
		rules : {
			dates : {
				required : true
			},
			comment : {
				required : true
			}
		},
		messages : {
			dates : {
				required : "Please provide start and end dates."
			},
			comment : {
				required : "Please provide comment."
			}
		},
		errorElement: 'span',
		errorPlacement: function (error, element) {
		      error.addClass('invalid-feedback');
		      element.closest('.form-group').append(error);
		    },
	    highlight: function (element, errorClass, validClass) {
	        $(element).addClass('is-invalid');
	      },
	      unhighlight: function (element, errorClass, validClass) {
	        $(element).removeClass('is-invalid');
	      }
	});
	
	$('#editSupervisorPendingLeaveRequest').validate({
		rules: {
			leaveStatusId : {
				required : true
			}
		},
		messages : {
			leaveStatusId : {
				required : "Please select a save as option"
			}
		},
		errorElement: 'span',
		errorPlacement: function (error, element) {
		      error.addClass('invalid-feedback');
		      element.closest('.form-group').append(error);
		    },
	    highlight: function (element, errorClass, validClass) {
	        $(element).addClass('is-invalid');
	      },
	      unhighlight: function (element, errorClass, validClass) {
	        $(element).removeClass('is-invalid');
	      }
	});
	$('#initiateRecallBack').validate({
		rules : {
			comment : {
				required : true
			}
		},
		messages : {
			comment : {
				required : "Please provide comment"
			}
		},
		errorElement: 'span',
		errorPlacement: function (error, element) {
		      error.addClass('invalid-feedback');
		      element.closest('.form-group').append(error);
		    },
	    highlight: function (element, errorClass, validClass) {
	        $(element).addClass('is-invalid');
	      },
	      unhighlight: function (element, errorClass, validClass) {
	        $(element).removeClass('is-invalid');
	      }
	});
	$('#makeLeaveRequestWithoutPay').validate({
		rules : {
			leaveTypeId : {
				required : true
			},
			dates : {
				required : true
			},
			primaryReliefOfficeId : {
				required : true
			},
			leaveStatusId : {
				required : true
			},
			staffToNotify : {
				required : true
			}
		},
		messages : {
			leaveType : {
				required : "Please provide leave type"
			},
			dates : {
				required : "Please provide start and end dates"
			},
			primaryReliefOfficeId : {
				required : "Please provide primary relief officer"
			},
			leaveStatusId : {
				required : "Please select save as option"
			},
			staffToNotify : {
				required : "Please select a staff to be notified"
			}
		},
		errorElement: 'span',
		errorPlacement: function (error, element) {
		      error.addClass('invalid-feedback');
		      element.closest('.form-group').append(error);
		    },
	    highlight: function (element, errorClass, validClass) {
	        $(element).addClass('is-invalid');
	      },
	      unhighlight: function (element, errorClass, validClass) {
	        $(element).removeClass('is-invalid');
	      }
	});
	
	$('#editEmployeeSupportTicket').validate({
		rules : {
			comment : {
				required : true
			},
			supportTicketStatusId : {
				required : true
			}
		},
		messages : {
			comment : {
				required : "Please provide a comment"
			},
			supportTicketStatusId : {
				required : "Please select a save as option"
			}
		},
		errorElement: 'span',
		errorPlacement: function (error, element) {
		      error.addClass('invalid-feedback');
		      element.closest('.form-group').append(error);
		    },
	    highlight: function (element, errorClass, validClass) {
	        $(element).addClass('is-invalid');
	      },
	      unhighlight: function (element, errorClass, validClass) {
	        $(element).removeClass('is-invalid');
	      }
	});
	
	$('#editSupportTicketForEmployee').validate({
		rules : {
			comment : {
				required : true
			}
		},
		messages : {
			comment : {
				required : "Please provide comment"
			}
		},
		errorElement: 'span',
		errorPlacement: function (error, element) {
		      error.addClass('invalid-feedback');
		      element.closest('.form-group').append(error);
		    },
	    highlight: function (element, errorClass, validClass) {
	        $(element).addClass('is-invalid');
	      },
	      unhighlight: function (element, errorClass, validClass) {
	        $(element).removeClass('is-invalid');
	      }
	});
	
	$('#createDepartmentSupportTicket').validate({
		rules : {
			issueTypeId : {
				required : true
			},
			issueDescription : {
				required : true
			},
			departmentId : {
				required : true
			},
			file : {
				required : true
			}
		},
		messages : {
			issueTypeId : {
				required : "Please select issue type"
			},
			issueDescription : {
				required : "Please provide issue description"
			},
			departmentId : {
				required : "Please select department"
			},
			file : {
				required : "Please upload issue"
			}
		},
		errorElement: 'span',
		errorPlacement: function (error, element) {
		      error.addClass('invalid-feedback');
		      element.closest('.form-group').append(error);
		    },
	    highlight: function (element, errorClass, validClass) {
	        $(element).addClass('is-invalid');
	      },
	      unhighlight: function (element, errorClass, validClass) {
	        $(element).removeClass('is-invalid');
	      }
	});
	
	$('#createEmployeeSupportTicket').validate({
		rules: {
			issueTypeId : {
				required : true
			},
			issueDescription : {
				required : true
			},
			employees : {
				required : true
			},
			file : {
				required : true
			}
		},
		messages :{
			issueTypeId : {
				required : "Please provide issue type"
			},
			issueDescription : {
				required : "Please provide issue description"
			},
			employees : {
				required : "Please select employees option"
			},
			file : {
				required : "Please upload a file"
			}
		},
		errorElement: 'span',
		errorPlacement: function (error, element) {
		      error.addClass('invalid-feedback');
		      element.closest('.form-group').append(error);
		    },
	    highlight: function (element, errorClass, validClass) {
	        $(element).addClass('is-invalid');
	      },
	      unhighlight: function (element, errorClass, validClass) {
	        $(element).removeClass('is-invalid');
	      }
	});
	
	$('#createRequisition').validate({
		rules : {
			requisitionTypeId : {
				required : true
			},
			subject : {
				required : true
			},
			recipients : {
				required : true
			},
			file : {
				required : true
			},
			requisitionStatusId : {
				required : true
			}
		},
		messages : {
			requisitionTypeId : {
				required : "Please select requisition type option"
			},
			subject : {
				required : "Please provide subject"
			},
			recipients : {
				required : "Please provide recipients"
			},
			file : {
				required : "please provide file"
			},
			requisitionStatusId : {
				required : "Please select save as option"
			}
		},
		errorElement: 'span',
		errorPlacement: function (error, element) {
		      error.addClass('invalid-feedback');
		      element.closest('.form-group').append(error);
		    },
	    highlight: function (element, errorClass, validClass) {
	        $(element).addClass('is-invalid');
	      },
	      unhighlight: function (element, errorClass, validClass) {
	        $(element).removeClass('is-invalid');
	      }
	});
	
	$('#editRequisition').validate({
		rules : {
			requisitionTypeId : {
				required : true
			},
			subject : {
				required : true
			},
			recipients : {
				required : true
			},
			requisitionStatusId : {
				required : true
			}
		},
		messages : {
			requisitionTypeId : {
				required : "Please select requisition type option"
			},
			subject : {
				required : "Please provide subject"
			},
			recipients : {
				required : "Please provide recipients"
			},
			requisitionStatusId : {
				required : "Please select save as option"
			}
		},
		errorElement: 'span',
		errorPlacement: function (error, element) {
		      error.addClass('invalid-feedback');
		      element.closest('.form-group').append(error);
		    },
	    highlight: function (element, errorClass, validClass) {
	        $(element).addClass('is-invalid');
	      },
	      unhighlight: function (element, errorClass, validClass) {
	        $(element).removeClass('is-invalid');
	      }
	});
	$('#editSupervisorRequisition').validate({
		rules : {
			requisitionStatusId : {
				required : true
			}
		},
		messages : {
			requisitionStatusId : {
				required : "Please select save as option"
			}
		},
		errorElement: 'span',
		errorPlacement: function (error, element) {
		      error.addClass('invalid-feedback');
		      element.closest('.form-group').append(error);
		    },
	    highlight: function (element, errorClass, validClass) {
	        $(element).addClass('is-invalid');
	      },
	      unhighlight: function (element, errorClass, validClass) {
	        $(element).removeClass('is-invalid');
	      }
	});
	$('#createPayElement').validate({
		rules : {
			name : {
				required : true
			},
			elementStatusId : {
				required : true
			},
			description : {
				required : true
			},
			dates : {
				required : true
			}
		},
		messages : {
			name : {
				required : "Please provide name"
			},
			elementStatusId : {
				required : "Please select status option"
			},
			description : {
				required : "Please provide description"
			},
			dates : {
				required : "Please provide start and end dates"
			}
		},
		errorElement: 'span',
		errorPlacement: function (error, element) {
		      error.addClass('invalid-feedback');
		      element.closest('.form-group').append(error);
		    },
	    highlight: function (element, errorClass, validClass) {
	        $(element).addClass('is-invalid');
	      },
	      unhighlight: function (element, errorClass, validClass) {
	        $(element).removeClass('is-invalid');
	      }
	});
	
	$('#createLevelPayElement').validate({
		rules : {
			levelId : {
				required : true
			},
			payElementId : {
				required : true
			}
		},
		messages : {
			levelId : {
				required : "Please select level option"
			},
			payElementId : {
				required : "Please select pay element option"
			}
		},
		errorElement: 'span',
		errorPlacement: function (error, element) {
		      error.addClass('invalid-feedback');
		      element.closest('.form-group').append(error);
		    },
	    highlight: function (element, errorClass, validClass) {
	        $(element).addClass('is-invalid');
	      },
	      unhighlight: function (element, errorClass, validClass) {
	        $(element).removeClass('is-invalid');
	      }
	});
	
	$('#editEmployeePayElement').validate({
		rules : {
			amount : {
				required : true
			}
		},
		messages : {
			amount : {
				required : "Please provide amount"
			}
		},
		errorElement: 'span',
		errorPlacement: function (error, element) {
		      error.addClass('invalid-feedback');
		      element.closest('.form-group').append(error);
		    },
	    highlight: function (element, errorClass, validClass) {
	        $(element).addClass('is-invalid');
	      },
	      unhighlight: function (element, errorClass, validClass) {
	        $(element).removeClass('is-invalid');
	      }
	});
	
	/* $("#createState").submit(function (e) {

         //stop submitting the form to see the disabled button effect
         e.preventDefault();

         //disable the submit button
         $("#save").attr("disabled", true);


         return true;

     });*/
	
	//document.getElementById("inline").style.display = "none";
	document.getElementById("comment").style.display = "none";
	
});
function changeLeaveType() {
	  var leaveTypeCode = document.getElementById("leaveTypeId").value;
	  document.getElementById("noOfLeaveDays").value = document.getElementById(leaveTypeCode+"Days").value;
	  document.getElementById("noOfLeaveDaysUsed").value = document.getElementById(leaveTypeCode+"UsedDays").value;
	  document.getElementById("noOfLeaveDaysLeft").value = document.getElementById(leaveTypeCode+"LeftDays").value;
	}
function disableSaveButton(){
	e.preventDefault();
	document.getElementById("save").disabled = true;
	return true;
}

function hideShowFile(){
	var leaveTypeCode = document.getElementById("leaveTypeId").value;
	var leaveFile = document.getElementById("leaveFile");
	
	if(leaveTypeCode != "examination"){
		leaveFile.style.display = "none";
	}
	else{
		leaveFile.style.display = "block";
	}
}

function hideComment(){
	var declined = document.getElementById("declined").value;
	var leaveStatusId = document.getElementById("leaveStatusId").value;
	var comment = document.getElementById("comment");
	
	if(leaveStatusId === declined){
		comment.style.display = "block";
	}
	else{
		comment.style.display = "none";
	}
}

function hideShowInLine(){
	var leaveTypeCode = document.getElementById("leaveTypeId").value;
	var inline = document.getElementById("inline");
	if(leaveTypeCode === "annual"){
		inline.style.display = "block";
	}
	else{
		inline.style.display = "none";
	}
}






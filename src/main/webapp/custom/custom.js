$(document).ready(function(){
	
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
});

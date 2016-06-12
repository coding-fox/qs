$(document).ready(function() {

	// 初始化选择框
	$('select').material_select();
	// 帮会列表选择组件
	var ca = $('.carousel');
	if (ca) {
		ca.carousel();
	}

	function initProfileList(){
		$('.navbar .dropdown-button').dropdown({
		      inDuration: 300,
		      outDuration: 225,
		      constrain_width: false, // Does not change width of dropdown to that of the activator
		      hover: true, // Activate on hover
		      gutter: 20, // Spacing from edge
		      belowOrigin: true, // Displays dropdown below the button
		      alignment: 'right' // Displays dropdown with edge aligned to the left of button
		    }
		  );
	}
	initProfileList();
	// 登录按钮-模态框
	function initLoginWin(){
		$('.navbar a.modal-trigger').leanModal({
			dismissible : true, // Modal can be dismissed by clicking
			// outside of the modal
			opacity : .5, // Opacity of modal background
			in_duration : 300, // Transition in duration
			out_duration : 200, // Transition out duration
			ready : function() {
			}, // Callback for Modal open
			complete : function() {

			} // Callback for Modal close
		});
	}
	initLoginWin();
	function initLogout(){
		$(".logout").click(function(event){
			event.preventDefault();
			$.getJSON("/qs/logout",function(res){
				if(res.data){//success logout
					$("#profile-li").html('<a href="#modal-login" class="modal-trigger">登陆</a>');
					initLoginWin();
				}else{
					Materialize.toast('糟糕，有点异常', 2000);
				}
			});
		});
	}
	initLogout();
	// 登陆模态窗口tab签
	// $('ul.tabs').tabs();
	$('select').material_select();
	// 小媒体设备收缩导航栏
	$(".button-collapse").sideNav();
	
	function checkForm(form){
		form.find('input').each(function(){
			 if(!$(this).val()){
				 $(this).addClass("invalid");
			 }
		 });
		 
	}
	
	//login
	$("#login-btn").click(function(event) {
		event.preventDefault();
		var form = $("#login-div form");
		checkForm(form);
		if(form.find("input.invalid").length>0){
			Materialize.toast('请完成验证', 2000);
			return;
		}
		
		$.post("/qs/login", $("#login-div form").serialize(),function(data){
			if(data.msg){
				Materialize.toast(data.msg, 2000);
			}else{
				$("#profile-li").loadTemplate($("#template-profile"),{
					username:data.data["name"],
					href:'/qs/profile/'+data.data["userId"]
				});
				initLogout();
				$("#modal-login").closeModal();
				
				$('.navbar .dropdown-button').dropdown({
				      inDuration: 300,
				      outDuration: 225,
				      constrain_width: false, // Does not change width of dropdown to that of the activator
				      hover: true, // Activate on hover
				      gutter: 20, // Spacing from edge
				      belowOrigin: true, // Displays dropdown below the button
				      alignment: 'right' // Displays dropdown with edge aligned to the left of button
				    }
				  );
			}
			$("#modal-login .progress").html('<div class="determinate" ></div>');
		});
		
		$("#modal-login .progress").html('<div class="indeterminate" ></div>');
	});

});

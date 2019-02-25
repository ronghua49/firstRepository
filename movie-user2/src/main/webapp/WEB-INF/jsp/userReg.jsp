<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>注册</title>
<!-- Tell the browser to be responsive to screen width -->
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
<%@include file="include/css.jsp"%>

<style type="text/css">
body {
	background-image: url(/static/dist/img/body-bg.png);
	padding-top: 120px;
}
</style>
</head>
<body class="col-sm-offset-3 col-sm-6 col-lg-offset-4 col-lg-4 ">

	<div style="background-color: #fff;">
		<!-- Main content -->
		<section class="content">
			<div class="" style="border-bottom: 1px solid #f0f0f0">
				<div class="box-header">
					<h3 class="text-center">注册</h3>
				</div>
			</div>
			<div class="box-solid">
				<div class="box-body">
					<div class="alert alert-error" id="error" hidden>用户名或密码错误</div>
					<!-- /.box-header -->
					<!-- form start -->
					<form class="form-horizontal" id="regForm">
						<div class="box-body">
							<fieldset>
								<div class="form-group">
									<label class="col-sm-1 control-label"><i
										class="fa fa-user"></i></label>
									<div class="col-sm-11">
										<input type="text" class="form-control" id="username"
											name="username" placeholder="请输入注册帐号">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-1 control-label"><i
										class="fa fa-lock"></i></label>
									<div class="col-sm-11">
										<input type="password" class="form-control" id="password"
											name="password" placeholder="请输入密码">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-1 control-label"><i
										class="fa fa-lock"></i></label>
									<div class="col-sm-11">
										<input type="password" class="form-control" id="rePassword"
											name="rePassword" placeholder="请再次输入密码">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-1 control-label"><i
										class="fa fa-lock"></i></label>
									<div class="col-sm-11">
										<input type="text" class="form-control" id="tel" name="tel"
											placeholder="请输入联系电话">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-1 control-label"><i
										class="fa fa-lock"></i></label>
									<div class="col-sm-11">
										<input type="text" class="form-control" id="email"
											name="email" placeholder="请输入邮箱">
									</div>
								</div>
							</fieldset>
							<br>

						</div>
						<!-- /.box-body -->

						<br>
					</form>
					<div>
						<button type="button" id="regBtn"
							class="btn btn-info  btn-lg btn-block">注册</button>
					</div>
				</div>
			</div>
		</section>
	</div>
	<!-- jQuery 2.2.3 -->
	<script src="/static/plugins/jQuery/jquery-2.2.3.min.js"></script>
	<script src="/static/dist/js/jquery.validate.min.js"></script>
	<script>
$(function(){
	
	var callback = "${param.reg}"
	var password = $("#password").val();
	$("#regBtn").click(function(){
		$("#regForm").submit();
	});
	$("#regForm").validate({
		errorElement:'span',
		errorClass:'text-danger',
		rules:{
			username : {
				required : true,
				remote:"/user/reg"
			},
			password : {
				required : true
			},
			rePassword : {
				required : true,
				equalTo:$("#password")
			},
			tel : {
				digits:true,
				rangelength:[11,11]
			},
			email : {
				required : true,
				email:true
			}
		},
		messages:{
			username : {
				required : "请输入用户名",
				remote:"该用户名已存在"
			},
			password : {
				required : "请输入密码"
			},
			rePassword : {
				required :"请再次输入密码",
				equalTo:"两次密码输入不一致"
			},
			tel:{
			    digits:"只能输入数字哦",
			    rangelength:"请输入11位手机号码"
			},
			email : {
				required :"请输入邮箱",
				email:"必须输入正确格式的电子邮件。"
			}
		},
		submitHandler:function(){
			$.ajax({
				url:"/user/reg",
				type:"post",
				data:$("#regForm").serialize(),
				beforeSend:function(){
					$("#regBtn").attr("disabled","disabled").text("注册中...");
				},
				success:function(res){
					if(res.state == "success") {
						//当发起的请求是过滤的请求切不为空时，直接重定向
						if(callback){
							 window.close();
						}else{
							window.location.href="/user/login";
						}
					} else {
						$("#error").text(res.message).show();
					}
				},
				error:function(){
					alert("系统异常");
				},
				complete:function(){
					$("#regBtn").removeAttr("disabled").text("登录");
				}
			});
			}
		});
});



</script>



</body>
</html>

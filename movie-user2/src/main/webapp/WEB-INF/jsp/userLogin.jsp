<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>登录</title>
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
					<h3 class="text-center">用户登录</h3>
				</div>
			</div>
			<div class="box-solid">
				<div class="box-body">
					<!-- /.box-header -->
					<!-- form start -->
					<form class="form-horizontal" id="loginForm">
						<div class="box-body">
							<div class="alert alert-error" id="error" hidden>用户名或密码错误</div>
							<fieldset>
								<div class="form-group">
									<label class="col-sm-1 control-label"><i
										class="fa fa-user"></i></label>
									<div class="col-sm-11">
										<input type="text" class="form-control" id="username"
											name="username" placeholder="登录帐号">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-1 control-label"><i
										class="fa fa-lock"></i></label>
									<div class="col-sm-11">
										<input type="password" class="form-control" id="password"
											name="password" placeholder="登录密码">
									</div>
								</div>
							</fieldset>
							<br>
							<div id="remember-me" class="form-group">

								<div class="col-sm-offset-1 col-sm-11">
									<input type="checkbox" name="remember" id="remember"
										style="margin-right: 4px;"> <label id="remember"
										for="remember">记住用户名</label>
								</div>
								<div class="col-sm-offset-1 col-sm-11">
									<a href="/user/reg">立即注册</a>
								</div>

							</div>
						</div>
						<!-- /.box-body -->

						<br>
					</form>
					<div>
						<button type="button" id="loginBtn"
							class="btn btn-info  btn-lg btn-block">进入系统</button>
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
	
	
	$("#loginBtn").click(function(){
		$("#loginForm").submit();
	});
	$("#loginForm").validate({
		errorElement:'span',
		errorClass:'text-danger',
		rules:{
			username : {
				required : true
			},
			password : {
				required : true
			}
		},
		messages:{
			username : {
				required : "请输入用户名"
			},
			password : {
				required : "请输入密码"
			}
		},
		submitHandler:function(){
			$.ajax({
				url:"/user/login",
				type:"post",
				data:$("#loginForm").serialize(),
				beforeSend:function(){
					$("#loginBtn").attr("disabled","disabled").text("登录中...");
				},
				success:function(res){
					if(res.state == "success") {
						//当发起的请求是过滤的请求切不为空时，直接重定向
							window.location.href="/user/index";
					} else {
						$("#error").text(res.message).show();
					}
				},
				error:function(){
					alert("系统异常");
				},
				complete:function(){
					$("#loginBtn").removeAttr("disabled").text("进入系统");
				}
			});
			}
		});
});



</script>



</body>
</html>

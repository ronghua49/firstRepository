<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>电影详情</title>
<!-- Tell the browser to be responsive to screen width -->
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
<%@ include file="include/css.jsp"%>
<style>
body {
	margin-top: 60px;
}
</style>
</head>
<body>
	<%@include file="include/header.jsp"%>
	<!-- 文章列表开始 -->
	<div class="container">
		<div class="box">
			<div class="topic-head">
				<span class="title">${movie.movieName}</span>
				<div class="info">${movie.directorName}</div>
				<ul class="list-inline"
					style="background-color: #fff; margin-right: 6px;">
					<c:forEach items="${movie.typelist}" var="type" varStatus="vs">
						<c:choose>
							<c:when test="${vs.count==1}">
								<span class="label label-info">${type.text}</span>
							</c:when>
							<c:when test="${vs.count==2}">
								<span class="label label-primary">${type.text}</span>
							</c:when>
							<c:otherwise>
								<span class="label label-default">${type.text}</span>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</ul>
			</div>
			<div class="topic-body">
				<p class="">${movie.content}</p>
			</div>
			<div class="topic-toolbar">
				<ul class="list-inline text-muted">
					<li><i class="fa fa-eye"></i></a> ${movie.scanNum}</li>
					<li><i class="fa fa-commenting"></i></a> ${movie.replyNum}</li>
				</ul>
			</div>
		</div>
		<!--box end-->
		<div class="box" style="margin-top: 20px;">
			<div class="talk-item muted" style="font-size: 12px">
				<!-- 获得该电影最后一次评论的事件 -->
				${movie.replyNum}个回复 | 直到<span id="lastTime">${movie.lastReplyTimestamp}</span>
			</div>
			<c:forEach items="${replyList}" var="reply">
				<c:if test="${empty replyList}">
					<div class="talk-item">
						<table class="talk-table">
							<tr>
								<td width="auto">暂无评论</td>
							</tr>
						</table>
					</div>
				</c:if>
				<div class="talk-item">
					<table class="talk-table">
						<tr>
							<td width="auto"><a href="" style="font-size: 12px">${reply.userName}</a>
								<span style="font-size: 12px" class="reply">${reply.createTime}</span>
								<br>
								<p style="font-size: 14px">${reply.content }</p></td>

						</tr>
					</table>
				</div>
			</c:forEach>

		</div>
		<div class="box" style="margin: 20px 0px;">
			<div class="talk-item muted" style="font-size: 12px">
				<i class="fa fa-plus"></i> 添加一条新回复
			</div>
			<form action="/movie/comment" method="post" id="replyForm"
				style="padding: 15px; margin-bottom: 0px;">
				<textarea name="replyContent" id="editor"></textarea>
				<input type="hidden" name="movieId" value="${movie.id}" />
			</form>
			<div class="talk-item muted"
				style="text-align: right; font-size: 12px">
				<span class="pull-left">请尽量让自己的回复能够对别人有帮助回复</span>
				<button class="btn btn-primary" id="replyBtn">发布</button>
			</div>
		</div>
	</div>
	<!-- 模态框  用户登录 -->
	<div class="modal fade" id="userlogin" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="exampleModalLabel">请登录后评论</h4>
				</div>
				<div class="modal-body">
					<div class="alert alert-error" id="error" hidden>用户名或密码错误</div>
					<form id="loginForm">
						<div class="form-group">
							<label for="recipient-name" class="control-label">请输入用户名:</label>
							<input type="text" class="form-control" id="username"
								name="username" placeholder="请输入用户名">
						</div>
						<div class="form-group">
							<label for="message-text" class="control-label">请输入密码:</label> <input
								type="password" class="form-control" id="password"
								name="password" placeholder="请输入密码">
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<a href="/user/reg?reg=modal" class="btn btn-default pull-left"
						target="_blank">还没有账号，立即注册？</a>
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary" id="loginBtn">登录</button>
				</div>
			</div>
		</div>
	</div>
	<!-- jQuery 2.2.3 -->
	<%@ include file="include/js.jsp"%>

	<script src="/static/plugins/moment/moment.js"></script>
	<script src="/static/plugins/moment/moment.locals.js"></script>
	<script>
    $(function(){
        var editor = new Simditor({
                textarea: $('#editor'),
                toolbarHidden: true
            });
        // 国际化
        moment.locale("zh-cn");
        $("#lastTime").text(moment($("#lastTime").text()).format("YYYY年MM月DD日 HH:mm:ss"))
        
		$(".reply").text(function(){
			var text = $(this).text(); // 2018-06-24 12:13:14.0
			// return moment(text).format("YYYY-MM-DD HH:mm:ss");
			return moment(text).fromNow();
		});
        //点击提交按钮时判断用户是否登录
        $("#replyBtn").click(function(){
       		var user = null;
       		$.post("/session/check").done(function(res){
       			user = res.data;
       			if(user){
    	        	var content = editor.getValue();
    	        	if(content){
    	        		$("#replyForm").submit();
    	        		layer.alert("评论成功，等待审核！");
    	        	}else{
    	        		layer.alert("内容不得为空");
    	        	}
            	}else{
            		$('#userlogin').modal('show')
            	} 
       		}).fail(function(){
       			alert("系统繁忙");
       		});
        	
        	
        });
			///模态框登录 表单按钮的点击事件
			$("#loginBtn").click(function(){
				$("#loginForm").submit();
			});
			
			$("#loginForm").validate({
				errorElement:'span',
				errorClass:'text-danger',
				rules:{
					 username: {
						required : true
					},
					password:{
						required:true
					}
				},
				messages:{
					username : {
						required :"请输入用户名"
					},
					password:{
						required:"请输入密码"
					}
				},
				submitHandler:function(){
					$.ajax({
						url:"/user/login",
						type:"post",
						data:$("#loginForm").serialize(),
						success:function(res){
							if(res.state == "success") {
								//当登录成功时，隐藏模态框
								$("#userlogin").modal('hide')
								$("#username").text(res.data.userName);
							} else {
								$("#error").text(res.message).show();
							}
						},
						error:function(){
							alert("系统异常");
						},
					})
				}
			});
    });
</script>
</body>
</html>
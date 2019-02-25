<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<nav class="navbar navbar-default navbar-inverse navbar-fixed-top">
	<div class="container">
		<!-- Brand and toggle get grouped for better mobile display -->
		<div class="navbar-header">
			<a class="navbar-brand" href="#"><i class="fa fa-film"></i></a>
		</div>

		<!-- Collect the nav links, forms, and other content for toggling -->
		<div class="collapse navbar-collapse"
			id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<li <c:if test="${empty param.typeId}"> class="active" </c:if>><a
					href="/user/index">全部</a></li>
				<c:forEach items="${typeList}" var="type" varStatus="vs">
					<c:if test="${vs.count<8}">
						<li
							<c:if test="${param.typeId == type.id}"> class="active" </c:if>><a
							href="/user/index?typeId=${type.id}">${type.text}</a></li>
					</c:if>
				</c:forEach>
				<li class="dropdown user user-menu"><a href="#"
					class="dropdown-toggle"> <img
						src="/static/dist/img/user1-128x128.jpg" class="user-image"
						alt="User Image"> <span class="hidden-xs" id="username">${user.userName}</span>
				</a></li>
			</ul>
			<form action="/user/index" class="navbar-form navbar-right">
				<div class="form-group">
					<input type="text" name="keys" value="${param.keys}"
						class="form-control" placeholder="Search">
				</div>
				<button class="btn btn-default">
					<i class="fa fa-search"></i>
				</button>
			</form>

		</div>
		<!-- /.navbar-collapse -->
	</div>
	<!-- /.container-fluid -->
</nav>
<!-- 导航栏结束 -->

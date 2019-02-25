<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>用户首页</title>
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
		<div class="row">
			<div class="col-md-9">
				<c:if test="${empty nowPage.items}">
					<div class="article-span">
						<div class="media article">
							<div class="media-body">
								<p class="">暂无数据</p>
							</div>

						</div>
					</div>
				</c:if>

				<c:forEach items="${nowPage.items}" var="movie" varStatus="vs">
					<div class="article-span">
						<div class="media article">
							<div class="media-body">
								<a href="/detail?mid=${movie.id}"><span
									class="media-heading">${movie.movieName}</span></a> <span class="">${movie.directorName}</span>

								<p class="">${movie.simpleContent}</p>
								<div class="meta">
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
								</div>
								<!-- <span class="label label-info">剧情</span> 
								<span class="label label-primary">犯罪</span> 
								<span class="label label-default">美国</span> -->
							</div>

							<div class="media-right">
								<a href=""> <img src="${movie.requestImgName}"
									style="width: 128px; height: 128px" class="media-object" alt="">
								</a>
							</div>
						</div>
					</div>
				</c:forEach>
				<div class="text-center">
					<ul id="pagination" class="pagination pagination-lg"></ul>
				</div>
			</div>
			<div class="col-md-3">
				<div class="panel panel-primary">
					<div class="panel-heading">
						<h3 class="panel-title">浏览排行</h3>
					</div>
					<!-- List group -->
					<ul class="list-group text-primary">
						<c:forEach items="${sortMovieList}" var="sortMovie" varStatus="vs">
							<li class="list-group-item"><a
								href="/detail?mid=${sortMovie.id}">${vs.count}.${sortMovie.movieName}</a>
								<c:choose>
									<c:when test="${vs.count==1}">
										<label class="label label-danger">${sortMovie.scanNum}</label>
									</c:when>
									<c:when test="${vs.count==2}">
										<label class="label label-warning">${sortMovie.scanNum}</label>
									</c:when>
									<c:when test="${vs.count==3}">
										<label class="label label-info">${sortMovie.scanNum}</label>
									</c:when>
									<c:otherwise>
										<label class="label label-default">${sortMovie.scanNum}</label>
									</c:otherwise>
								</c:choose></li>
						</c:forEach>
					</ul>

				</div>
			</div>
		</div>
	</div>




	<!-- jQuery 2.2.3 -->
	<script src="/static/plugins/jQuery/jquery-2.2.3.min.js"></script>
	<script
		src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<!-- page -->
	<script src="/static/dist/js/jquery.twbsPagination.min.js"></script>
	<script>
		$(function() {
  //分页会发起
			$("#pagination").twbsPagination({
				totalPages :"${nowPage.totalPage}",
				visiblePages : 3,
				//点击下一页时，会自动发起的请求 需要把当前第几页 查询类型和 查询关键字 带到后台
				href : "/user/index?p={{number}}&typeId=${param.typeId}&keys=" + encodeURIComponent("${param.keys}"),
				first : "首页",
				prev : "上一页",
				next : "下一页",
				last : "末页"
			});
		});
		
	</script>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>	
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>电影列表</title>
<!-- Tell the browser to be responsive to screen width -->
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
<!-- Bootstrap 3.3.6 -->
<%@include file="include/css.jsp"%>
</head>
<body class="hold-transition skin-blue sidebar-mini">
	<!-- Site wrapper -->
	<div class="wrapper">
			<%@include file="include/header.jsp"%>
<%-- 			<%@include file="include/sider.jsp"%> --%>
			<jsp:include page="include/sider.jsp">
				<jsp:param name="menu" value="filmlist"/> 
			</jsp:include>
		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper">
			<!-- Main content -->
			<section class="content">
				<!-- Default box -->
				<div class="box">
					<div class="box-header with-border">
						<form action="/film/filmList" class="form-inline pull-left">
						<!-- 获得get请求中的 参数key -->
							<input type="text" class="form-control" name="keys" value="${param.keys}" id="keys"
								placeholder="关键字" />
							<button class="btn btn-primary" id="btn">
								<i class="fa fa-search"></i>
							</button>
						</form>
						<a href="/film/add" class="btn btn-success pull-right">新增电影</a>
					</div>
					<div class="box-body">
						<table class="table" id="cust_table">
							<thead>
								<tr>
									<th>名称</th>
									<th>导演</th>
									<th>类型</th>
									<th>上映年份</th>
									<th>地区</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
							
							<c:forEach items="${nowPage.items}"  var="movie">
								<tr>
									<td>${movie.movieName }</td>
									<td>${movie.directorName }</td>
									<td>
									<c:forEach items="${movie.typelist}" var="type">
										${type.text}
									</c:forEach>
									</td>
									<td>${movie.year}</td>
									<td>${movie.area}</td>
									<td><a href="javaScript:;" class="del" rel="${movie.id}">删除</a> <a href="/film/filmEdit?mid=${movie.id}">修改</a></td>
								</tr>
							</c:forEach>
							</tbody>
						</table>
						<br>
						<ul id="pagination" class="pagination pull-right"></ul>
					</div>
					<!-- /.box-body -->

				</div>
				<!-- /.box -->

			</section>
			<!-- /.content -->
		</div>
		<!-- /.content-wrapper -->
		<%@include file="include/footer.jsp"%>
	</div>
	<!-- ./wrapper -->

	<%@include file="include/js.jsp"%>
	<!-- DataTables -->
	<script src="/static/plugins/datatables/jquery.dataTables.min.js"></script>
	<script src="/static/plugins/datatables/dataTables.bootstrap.min.js"></script>

	<!-- page -->
	<script src="/static/dist/js/jquery.twbsPagination.min.js"></script>
	<script>
		$(function() {
			 var keys = "${param.keys}";  
			 keys = encodeURIComponent(keys);
			$("#pagination").twbsPagination({
				totalPages : "${nowPage.totalPage}",
				visiblePages : 3,
				href : "/film/filmList?page={{number}}&keys=" + keys,
				first : "首页",
				prev : "上一页",
				next : "下一页",
				last : "末页"
			});
			
			
			$(".del").click(function(){
				
				if(confirm("确定要删除此条记录吗?")){
					var mid = $(this).attr("rel");
					window.location.href="/film/delete?mid="+mid;
				}
				
			});
			
		});
	</script>
</body>
</html>

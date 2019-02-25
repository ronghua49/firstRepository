<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>电影类型</title>
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
		<%-- <%@include file="include/sider.jsp"%> --%>
			<jsp:include page="include/sider.jsp">
				<jsp:param name="menu" value="typesmanage"/> 
			</jsp:include>
		
		
		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper">

			<!-- Main content -->
			<section class="content">
				<!-- Default box -->
				<div class="box">
					<div class="box-header with-border">
						<form action="/type/filmTypes" class="form-inline pull-left">
							<input type="text" class="form-control" name="typeName"
								id="typeName" placeholder="关键字" />
							<button class="btn btn-primary">
								<i class="fa fa-search"></i>
							</button>
						</form>
						<!--  <h5 class="pull-left">文章列表</h5> -->
						<a href="#" class="btn btn-success pull-right" data-toggle="modal"
							data-target="#addModal">新增类型</a>
					</div>

					<div class="box-body">

						<table class="table" id="cust_table">
							<thead>
								<tr>
									<th>类型名称</th>
									<th>创建时间</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${nowPage.items}" var="type">
									<tr>
										<td>${type.text}</td>
										<td>${type.createTime}</td>
										<td><a href="javaScript:;" rel="${type.id}" class="del">删除</a>
											<a href="javaScript:;" rel="${type.id}" class="edit" typeName="${type.text}"  data-target="#updateModal"   data-toggle="modal">修改</a></td>
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
		<%@include file="include/footer.jsp"%>
	</div>
	//模态框 新增电影类型
	<div class="modal fade" id="addModal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">新增节点</h4>
				</div>
				<div class="modal-body">
					<form action=""  class="form-horizontal" id="addForm">
						<div class="form-group">
							<label class="col-sm-2 control-label">节点名称:</label>
							<div class="col-sm-10">
								<input type="text" name="typeName" class="form-control" id="typeName"
									placeholder="请输入节点名称">
							</div>
						</div>

					</form>
				</div>
				<div class="modal-footer">
					<button class="btn btn-primary pull-left" id="addbtn">保存</button>
					<button class="btn btn-default pull-left" data-dismiss="modal">取消</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->
	//修改电影类型节点
	<div class="modal fade" id="updateModal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">更新节点</h4>
				</div>
				<div class="modal-body">
					<form action="" class="form-horizontal" id="editForm">
						<div class="form-group">
							<label class="col-sm-2 control-label">节点名称:</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" id="editName" name="typeName"
									placeholder="请输入节点名称">
								<input type="hidden"  name="editTypeId" id="editTypeId" >
									
							</div>
						</div>

					</form>
				</div>
				<div class="modal-footer">
					<button class="btn btn-primary pull-left" id="editbtn">保存</button>
					<button class="btn btn-default pull-left" data-dismiss="modal">取消</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->


	<!-- ./wrapper -->

	<%@include file="include/js.jsp"%>
	<!-- DataTables -->
	<script src="/static/plugins/datatables/jquery.dataTables.min.js"></script>
	<script src="/static/plugins/datatables/dataTables.bootstrap.min.js"></script>

	<!-- page -->
	<script src="/static/dist/js/jquery.twbsPagination.min.js"></script>
	<script>
		$(function() {
			$("#pagination").twbsPagination({
				totalPages : "${nowPage.totalPage}",
				visiblePages : 3,
				href : "/type/filmTypes?page={{number}}",
				first : "首页",
				prev : "上一页",
				next : "下一页",
				last : "末页"
			});
			//删除的业务处理
			$(".del").click(function() {
				if (confirm("确定要删除此条记录吗?")) {
					var tid = $(this).attr("rel");
						$.ajax({
							url:"/type/filmTypes",
							type:"post",
							data:{"tid":tid},
							success:function(res){
								alert("删除成功！");
								history.go(0); 
							},
							error:function(){
								alert("该类型下已有电影或者该类型不存在！");
							}
						});
				}
			});
		//新增的业务处理 新增类型表单的ajax提交
		$("#addbtn").click(function(){
			$("#addForm").submit();
		});
		$("#addForm").validate({
			errorElement:'span',
			errorClass:'text-danger',
			rules:{
				typeName : {
					required : true,
					remote:"/type/check/typeName"
				}
			},
			messages:{
				typeName : {
					required : "内容不得为空",
					remote:"该节点已存在，不得重复"
				}
			},
			submitHandler:function(){
				$.ajax({
					url:"/type/filmTypes",
					type:"post",
					data:$("#addForm").serialize(),
					success:function(res){
						alert("添加成功");
					history.go(0);
					},
					error:function(){
						alert("系统异常");
					},
				})
			} 
		});
		
		
		
		//更新节点的 ajax提交
		$(".edit").click(function(){
			
			var tid = $(this).attr("rel");
			var typeName =$(this).attr("typeName");
			
			//回显表单 当前类型
			$("#editName").val(typeName);
			//添加隐藏的 typeid值
			$("#editTypeId").val(tid);
			
			//表单按钮的点击事件
			$("#editbtn").click(function(){
				$("#editForm").submit();
			});
			
			$("#editForm").validate({
				errorElement:'span',
				errorClass:'text-danger',
				rules:{
					typeName : {
						required : true,
						remote:"/type/check/typeName?typeId="+tid//修改节点类型时验证，需加上当前typeid
					}
				},
				messages:{
					typeName : {
						required :"修改内容不得为空",
						remote:"不得和已有节点重复"
					}
				},
				submitHandler:function(){
					$.ajax({
						url:"/type/filmTypes",
						type:"post",
						data:$("#editForm").serialize(),
						success:function(res){
							alert("修改成功");
							history.go(0);
						},
						error:function(){
							alert("系统异常");
						},
					})
				}
			});
			
		});
	});
	</script>
</body>
</html>

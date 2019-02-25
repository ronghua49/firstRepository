<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>	
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>写文章</title>
<!-- Tell the browser to be responsive to screen width -->
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
<%@ include file="include/css.jsp"%>
<style>
.select2-container--default .select2-selection--multiple .select2-selection__choice
	{
	color: #555;
}
</style>
</head>
<body class="hold-transition skin-blue sidebar-mini">
	<!-- Site wrapper -->
	<div class="wrapper">

		<header class="main-header">
				<%@ include file="include/header.jsp"%>
		</header>
		<!-- Left side column. contains the sidebar -->
			<%@include file="include/sider.jsp"%>
		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper">
			<!-- Main content -->
			<section class="content">
				<!-- Default box -->
				<div class="box box-primary">
					<div class="container">
					<input type="hidden"  id="message" value="${requestScope.success}"/>
						<form action="/film/add" method="post" class="form-horizontal"
							id="editForm">
							<br>
							<div class="row">
								<div class="col-md-9">
									<div class="form-group">
										<input type="text" class="form-control" name="filmName"
											id="fileName" placeholder="请输入电影名称">
									</div>
									<div class="form-group">
										<input type="text" class="form-control" name="director"
											id="director" placeholder="请输入导演">
									</div>
									<div class="form-group">
										<input type="text" class="form-control" name="area" id="area"
											placeholder="请选择地区">
									</div>
									<div class="form-group">
										<input type="text" class="form-control" name="year" id="year"
											placeholder="请输入上映年份">
									</div>
									<div class="form-group">
										<div>
											<select name="type" id="classic" class="form-control">
												<!-- 多选下拉框 电影类型 -->
											</select>
										</div>
									</div>
									<br>
								</div>
								<div class="col-md-3">
									<div class="box">
										<div class="box-body">
											<div id="preview" style="height: 138px;text-align:center">
												<img  id="img" alt="电影封面" style="height:150px" />
											</div>
											<!-- 电影封面的图片名字  value 值为UUID生成值--> 
											<input type="text" hidden name="imgName" id="imgName" />
										</div>
										<div class="box-footer">
											<div id="picker" style="text-align: center">选择封面</div>
										</div>
									</div>
								</div>
							</div>
							<div class="form-group">
								<textarea name="content" class="from-control" id="editor"
									placeholder="电影的内容简介..."></textarea>
							</div>
						</form>
					<button class="btn btn-primary" id="btn">发布电影</button>
					</div>
				</div>
				<!-- /.box -->
			</section>
			<!-- /.content -->
		</div>
		<!-- /.content-wrapper -->
		<%@include file="include/footer.jsp"%>
	</div>
	<!-- ./wrapper -->
	<%@ include file="include/js.jsp"%>
	<script type="text/javascript" src="/static/plugins/uploader/webuploader.js"></script>
	<!-- 下拉框筛选插件 -->
	<script src="/static/plugins/select2/select2.min.js"></script>
	<script src="/static/plugins/select2/select2.full.min.js"></script>
</body>
<script>
	$(function() {
		
		if($("#message").val()){
			alert($("#message").val());
		}
		
		var editor = new Simditor({
			//文本内容
			textarea : $('#editor'),
		//添加插入图片弹框
		upload:{
			url:"/img/uploader",
			fileKey:"file"
			}
		});
		var uploader = WebUploader.create({
			// swf文件路径
			swf : 'plugins/uploader/Uploader.swf',
			// 文件接收服务端。
			server : '/img/uploader',
			// 选择文件的按钮。可选。
			// 内部根据当前运行是创建，可能是input元素，也可能是flash.
			pick : '#picker',
			auto : 'true',
			fileNumLimit:1,
			// 只允许选择图片文件。
			accept : {
				title : 'Images',
				extensions : 'gif,jpg,jpeg,bmp,png',
				mimeTypes : 'image/*'
			}
		});
		//接受server路径下的 ajax成功响应数据
		uploader.on("uploadSuccess", function(file, resp) {
			//此resp为后台 json数据中的map (键值对形式的数据)
			$("#img").attr("src", resp.file_path);
			$("#imgName").val(resp.fileName);
		});
		
		uploader.on("uploadError", function(file) {
			alert("上传失败");
		});
		$("#classic").select2({
			placeholder : '请选择电影类型',
			tags : true,
			multiple : true,
			height : '40px',
			maximumSelectionLength : 3,
			allowClear : true,
			language : "zh-CN",
		
		
			//ajax发起异步请求在加载页面时，请求后台数据 显示电影类型列表
			  ajax : {
				  url : '/type/list',
				  processResults: function (res) {
					  if(res.state == "success") {
						  return {
							  //把res.data 数组赋值给results 则就在下拉框 中出现多个选择项
							  results: res.data
						  };
					  } else {
						  layer.alert("系统异常");
						  return {
							  results: []
						  };
					  }
				  },
			  }
		});
		
		//$(".select2-selection__choice").css({ color: "#ff0011"});
		$("#btn").click(function() {
			var imgName = $("#imgName").val();
			var content = editor.getValue();
			if(imgName){
				if(content){
					$("#editForm").submit();
				}else{
					layer.alert("评论不得为空");
				}
			}else{
				layer.alert("请选择封面");
			}
				
		});
		$("#editForm").validate({
			errorElement : 'span',
			errorClass : 'text-danger',
			rules : {
				filmName : {
					required : true,
					remote:'/film/check/movieName'//远程访问   为true不显示
				},
				director : {
					required : true
				},
				area : {
					required : true
				},
				year : {
					required : true
				},
				type : {
					required : true
				},
			},
			messages : {
				filmName : {
					required : "亲输入电影名",
					remote:"电影名字不可重复"//为false 显示该句话
				},
				director : {
					required : "请输入导演名"
				},
				area : {
					required : "请输入上映地区"
				},
				year : {
					required : "请输入上映年份"
				},
				type : {
					required : "至少选择一个类型"
				},
			},
			errorPlacement : function(error, element) {
				//表示错误信息存放的位置 表示存放在当前元素的父元素中后
				error.appendTo(element.parent());
			},
		});
	});
</script>
</html>

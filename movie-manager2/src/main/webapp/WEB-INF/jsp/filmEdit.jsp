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
			<%@ include file="include/header.jsp"%>

			<%@include file="include/sider.jsp"%>
		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper">
			<!-- Main content -->
			<section class="content">
				<!-- Default box -->
				<div class="box box-primary">
					<div class="container">


						<form action="/film/filmEdit" method="post" class="form-horizontal"
							id="editForm">
							<br>
							<div class="row">
								<div class="col-md-9">
									<div class="form-group">
										<input type="hidden" value="${movie.id}"  name="mid"/> <input type="text"
											class="form-control" name="filmName"
											value="${movie.movieName}" id="fileName"
											placeholder="请输入电影名称">
									</div>
									<div class="form-group">
										<input type="text" class="form-control" name="director"
											value="${movie.directorName}" id="director"
											placeholder="请输入导演">
									</div>
									<div class="form-group">
										<input type="text" class="form-control" name="area" id="area"
											value="${movie.area}" placeholder="请选择地区">
									</div>
									<div class="form-group">
										<input type="text" class="form-control" name="year" id="year"
											value="${movie.year}" placeholder="请输入上映年份">
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
											<div id="preview" style="height: 138px ;text-align:center ">
												<img alt="电影封面" src="${movie.requestImgName}"
													style="height: 100%" />
											</div>

											<!-- 电影封面的图片名字 -->
											<input type="hidden"  name="imgName" value="${movie.imgName}"  id="imgName" />

										</div>
										<div class="box-footer">
											<div id="picker" style="text-align: center">选择封面</div>
										</div>
									</div>
								</div>
							</div>
							<div class="form-group">
								<textarea name="content" class="from-control" id="editor"
									placeholder="电影的内容简介...">${movie.content}</textarea>
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
	<script  src="/static/plugins/uploader/webuploader.js"></script>
	<!-- 下拉框筛选插件 -->
	<script src="/static/plugins/select2/select2.min.js"></script>
	<script src="/static/plugins/select2/select2.full.min.js"></script>

</body>
<script>
	$(function() {
		var editor = new Simditor({
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
			//fileNumLimit:"1",
			// 只允许选择图片文件。
			accept : {
				title : 'Images',
				extensions : 'gif,jpg,jpeg,bmp,png',
				mimeTypes : 'image/*'
			}
		});
		uploader.on("uploadSuccess", function(file, resp) {
			$("#preview").find("img").attr("src", resp.file_path);
			$("#imgName").attr("value", resp.fileName);
			/* if (resp.state=="success") {
				window.location.href="/filmList";
			} */
		})
		uploader.on("uploadError", function(file) {
			alert("上传失败");
		})
		
		$("#classic").select2({
			placeholder : '请选择电影类型',
			tags : true,
			multiple : true,
			height : '40px',
			maximumSelectionLength : 3,
			allowClear : true,
			language : "zh-CN",
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
		 $.ajax({
		    	url:'/type/list?mid=${movie.id}',
		    	type:'get',
		    	success : function(res){
					for(var i = 0; i < res.results.length; i ++) {
						var obj = res.results[i];
			    		var option = new Option(obj.text, obj.id, obj.selected, obj.selected);
			    		$("#classic").append(option);
					}    		
		    	},
		    	error : function(){
		    		alyer.alert("系统异常");
		    	}
		    	
		    })
		$("#btn").click(function() {
			var imgName = $("#imgName").val();
			var content = editor.getValue();
			if(imgName){
				if(content){
					$("#editForm").submit();
					layer.alert("发布成功！");
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
					remote:'/film/check/movieName?mid=${movie.id}'//除了表单参数之外的参数
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
					remote:"该电影名字已经存在"//为false 则显示
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

 <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <!-- 使用bootstrap的css样式 -->
    <link rel="stylesheet" href="/static/bootstrap/css/bootstrap.css" />
    <!-- 使用webuploader的插件 -->
    <link rel="stylesheet"  href="/static/plugins/uploader/webuploader.css" />
  
  
    <title>webuploader的上传用法</title>
    </head>
<body>
    <!-- webuploader.css样式 -->
    <div id="picker">文件上传</div>
    <ul class="list-group" id="files">
    </ul>
    <image id="prve" style="width:200px;height:200px;"></image>
   <!--  <button class="btn btn-primary" id="btn">开始上传</button> -->
    <!-- 导入js的两个插件 -->
     <script src="/static/dist/js/jquery3.js"></script>
     <script src="/static/plugins/uploader/webuploader.js"></script>
     
     
     <script>
     $(function(){
    	 //初始化webuploader 
    var uploader= WebUploader.create({
    	swf: '/static/plugins/uploader/Uploader.swf',
    	server:"upload3",
    	pick:'#picker',
    	auto:'true',
    	
    	 accept:{
    		title:'Images',
    		extensions:'gif,jpg,png,jpeg',
    		mimeTypes:'image/*'
    	} 
    }); 
    	 //当文件被添加到文件队列时触发
    	 uploader.on("fileQueued",function(file){
    		 console.log(file);
    		 var li = '<li class="list-group-item" id='+file.id+'>'+file.name+'<span></span></li>';
    		$("#files").append(li);
    		 
    		// $("li").appendTo("#files");
    	 });
    	 //文件的上传进度
    	 uploader.on("uploadProgress",function(file,percentage){
    		 $("#"+file.id).find("span").text(parseInt(percentage*100)+"%");
    	 });
    	 
    	 //文件上传成功（同会接到后端成功的响应数据）
    	 uploader.on("uploadSuccess",function(file,resp){
    		 $("#"+file.id).find("span").text("已上传");
    		 //设置图片的src属性 规定图片的URL 预览图片
    		 console.log(resp.img_path);
    		 $("#prve").attr("src",resp.img_path);//设置图片的站点路径信息  图片会自动发起get请求 
    	 })
    	 //文件上传错误
    	 uploader.on("uploadError",function(file){
    		 $("#"+file.id).find("span").text("上传失败");
    	 });
    	 $("#btn").click(function(){
    		 uploader.upload();
    	 });
    	
     });
     
     
     </script>


</body>
</html> 




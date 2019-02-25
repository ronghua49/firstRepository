<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>Document</title>
</head>
<body>

	<h1>文件上传</h1>
	<form action="/upload3" method="post" enctype="multipart/form-data">
		 <input type="text" name="desc" /><br /> 
		<input type="file" name="file" />
		<button>开始上传</button>
	</form>
	<a href="http://localhost/download?fileName=af31e257-318c-4ba5-9d2b-baa2f06313ed.jpg&name=我的照片.jpg">下载我的照片</a>
	<a href="http://localhost/download?fileName=af31e257-318c-4ba5-9d2b-baa2f06313ed.jpg">预览我的照片</a>
</body>
</html>
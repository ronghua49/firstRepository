package com.haohua.web;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.haohua.util.Config;

@WebServlet("/img/uploader")
@MultipartConfig
public class ImageUploder extends BaseServlet {
	private static final long serialVersionUID = 1L;
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		File file = new File(Config.getProperty("upload_path"));
		//根据请求头中 获得header对象 生成一个UUID name
		String fileName = getUUIDName(req);
		Map<String, Object> map = new HashMap<>();
		// 添加uuid到req（向工具类传入参数）
		req.setAttribute("UUID", fileName);
		//目前只发送 上传成功后的json 响应数据	
		map.put("success", true);//富文本框文件上传成功的json信号（富文本框发现此信号会自动把file_path 对应信息展示到图片src）
		map.put("file_path", Config.getProperty("http.address")+ fileName);//上传成功后，向前端传送此图片的请求资源路径名
		map.put("fileName", fileName);//向前端传送根据此图片生成的图片名
		javaUpload(file, req, resp, map);
	}

}
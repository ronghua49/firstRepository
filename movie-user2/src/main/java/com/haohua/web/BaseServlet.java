package com.haohua.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;

public class BaseServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void forward(String path, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		req.getRequestDispatcher("/WEB-INF/jsp/" + path + ".jsp").forward(req, resp);
	}

	protected void send(HttpServletResponse resp, Object result) throws IOException {

		PrintWriter writer = resp.getWriter();// 得到响应输出流
		writer.print(new Gson().toJson(result));// 把结果已json数据形式发送前端
		// 设置json的响应头
		resp.setContentType("application/json,charset=UTF-8");
		writer.flush();
		writer.close();
	}

	protected  void  javaUpload(File savepath, HttpServletRequest req, HttpServletResponse resp, Object object)
			throws IOException, ServletException {
		Part part = req.getPart("file");
		// 获得文件 名 大小 类型
		String header = part.getHeader("Content-Disposition");

		// 创建路径文件夹
		if (!savepath.exists()) {
			savepath.mkdirs();
		}
		// 拼接上传的文件名 生成唯一的文件名
		header = header.split(";")[2].split("\"")[1];
		String fileName = (String)req.getAttribute("UUID");
		if (StringUtils.isEmpty(fileName)) {
			fileName = UUID.randomUUID().toString() + header.substring(header.lastIndexOf("."));
		}
		// 获得文件的输入流
		InputStream in = part.getInputStream();
		OutputStream out = new FileOutputStream(new File(savepath, fileName));
		IOUtils.copy(in, out);
		// 向前端发送对象
		send(resp, object);
	}
	
	
	protected void apcheUpload(HttpServletRequest req, HttpServletResponse resp, File save, File temp, Object object)
			throws ServletException, IOException {
		// post解决中文乱码
		req.setCharacterEncoding("UTF-8");
		// 1.设置文件上传路径
		if (!save.exists()) {
			save.mkdirs();
		}
		// 2、设置临时路径
		if (!temp.exists()) {
			temp.mkdirs();
		}
		// 3.判断表单属性 enctype 是否为multipart/form-data
		if (ServletFileUpload.isMultipartContent(req)) {

			DiskFileItemFactory factory = new DiskFileItemFactory();

			factory.setSizeThreshold(1024);

			factory.setRepository(temp);
			// 根据factory 创建servlet文件上传对象
			ServletFileUpload upload = new ServletFileUpload(factory);
			// 用对象获得form表单 数据的集合

			try {
				// 把请求头中内容 转为 对象结集合
				List<FileItem> itemList = upload.parseRequest(req);
				for (FileItem item : itemList) {
					// 当对象是普通表单数据时
					if (item.isFormField()) {
						// 不同数据获得此数据的信息
						String fileName = item.getFieldName();
						String value = item.getString("UTF-8");
					} else {
						// 当对象为文件数据时
						String name = item.getName();
						// 生成唯一的文件名
						name = UUID.randomUUID().toString() + name.substring(name.lastIndexOf("."));
						InputStream in = item.getInputStream();
						// 向指定的位置存储 文件的输入流
						FileOutputStream out = new FileOutputStream(new File(save, name));
						IOUtils.copy(in, out);
						out.flush();
						out.close();
						in.close();
						// 把结果发送给前端
						send(resp, object);
					}
				}

			} catch (FileUploadException e) {
				e.printStackTrace();
			}
		} else {
			throw new ServletException("表单异常");
		}

	}

	protected void download(HttpServletRequest req, HttpServletResponse resp, String downloadName, String filename, String downloadPath)
			throws IOException {
		// 下载的文件名，在get 请求中
		String fileName = req.getParameter(filename);//获得后台 文件名
		String name = req.getParameter(downloadName);//获得文件中文名
		
		File file = new File(new File(downloadPath), fileName);
		if (file.exists()) {
			
			if (StringUtils.isNotEmpty(name)) {
				// 设置文件类型
				resp.setContentType("application/octet-stream");
				// 告知文件大小
				resp.setContentLength((int) file.length());
				// 添加响应头 传入前端
				resp.addHeader("Content-Disposition", "attachment; filename=\"" + name + "\"");
			}
			InputStream in = new FileInputStream(file);
			OutputStream out = resp.getOutputStream();

			IOUtils.copy(in, out);
			out.flush();
			out.close();
			in.close();
		} else {
			resp.sendError(404, "资源未找到");
		}

	}
	
	protected  String  getUUIDName(HttpServletRequest req) throws IOException, ServletException {
		Part part = req.getPart("file");
		// 获得文件 名 大小 类型
		String header = part.getHeader("Content-Disposition");

		// 拼接上传的文件名 生成唯一的文件名
		header = header.split(";")[2].split("\"")[1];

		String fileName = UUID.randomUUID().toString() + header.substring(header.lastIndexOf("."));
		return fileName;
	}
}

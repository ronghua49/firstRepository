package com.haohua.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.haohua.service.MovieService;
@WebServlet("/film/delete")
public class FilmDelete extends BaseServlet{

	
	private static final long serialVersionUID = 1L;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String mid = req.getParameter("mid");
		System.out.println(mid);
		Integer movieId = null;
		
		if (StringUtils.isNumeric(mid)) {
			movieId=Integer.parseInt(mid);
		}
		//从数据库中删除此条记录
		MovieService service = new MovieService();
		
		service.deleteMovieById(movieId);
		//请求转发到 filmlist 页面
		resp.sendRedirect("/filmList");
	
	}
}

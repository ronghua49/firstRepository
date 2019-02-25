package com.haohua.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.haohua.entity.Movie;
import com.haohua.service.MovieService;
@WebServlet("/film/filmEdit")
public class FilmEdit extends BaseServlet {
	
	MovieService service = new MovieService();
	private static final long serialVersionUID = 1L;
		@Override
		protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			//修改电影  从filmlist.jsp发过来的get请求 
			String mid = req.getParameter("mid");
			//如果mid不为空 ，表示修改
			if (StringUtils.isNotEmpty(mid)&&StringUtils.isNumeric(mid)) {
				MovieService service = new MovieService();
				Movie movie = service.findMovieById(mid);
				req.setAttribute("movie", movie);
				forward("filmEdit", req, resp);
			} else {
				//否则不得直接访问该请求，报参数异常
				resp.sendError(404, "参数异常");
			}
		}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String mid = req.getParameter("mid");
		String filmName = req.getParameter("filmName");
		String director = req.getParameter("director");
		String area = req.getParameter("area");
		String year = req.getParameter("year");
		String imgName = req.getParameter("imgName");
		String content = req.getParameter("content");
		String[] types = req.getParameterValues("type");
		
		service.EditMovie(mid,filmName,director,area,year,imgName,content,types);
		resp.sendRedirect("/film/filmList");
		//成功之后
		//send(resp, "alert("+"保存成功"+")");
	
	}
}

package com.haohua.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.haohua.service.MovieService;

@WebServlet("/film/check/movieName")
public class CheckMovieName extends BaseServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 验证电影名字不能重复
		String filmName = req.getParameter("filmName");
		String mid = req.getParameter("mid");
		//filmName = new String(filmName.getBytes("ISO8859-1"), "UTF-8");
		MovieService service = new MovieService();
		boolean isok = service.findMovieName(filmName,mid);
		send(resp, isok);
	}

}

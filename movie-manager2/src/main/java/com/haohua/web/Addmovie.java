package com.haohua.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.haohua.service.MovieService;

@WebServlet("/film/add")
public class Addmovie extends BaseServlet{
	
	private static final long serialVersionUID = 1L;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
					forward("addmovie", req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String filmName = req.getParameter("filmName");
		String director = req.getParameter("director");
		String area = req.getParameter("area");
		String year = req.getParameter("year");
		String imgName = req.getParameter("imgName");//名字为中文名字
		String content = req.getParameter("content");
		//根据select2 的name属性type 在服务端得到对应的typeid集合
		String[] types = req.getParameterValues("type");
		
	
		MovieService service = new MovieService();
		
		service.Addmovie(filmName,director,area,year,imgName,content,types);
		//成功之后 重新加载页面 提示添加成功
		req.setAttribute("success", "添加成功！");
		
		forward("addmovie", req, resp);
		
	}

}

package com.haohua.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.haohua.entity.Movie;
import com.haohua.service.MovieService;
import com.haohua.util.Page;
@WebServlet("/film/filmList")
public class FilmList extends BaseServlet{

	private static final long serialVersionUID = 1L;
	
	MovieService service = new MovieService();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//keys为空表示 查询所有
		String keys = req.getParameter("keys");
		String pageNo = req.getParameter("page");
		int pageNumNow=1;
		
		if (StringUtils.isNumeric(pageNo)) {
			pageNumNow= Integer.parseInt(pageNo);
		}
		//找到movie中所有的电影列表
		
		Page<Movie> nowPage = service.findMoviePageByPageNo(pageNumNow,keys);
		
		req.setAttribute("nowPage", nowPage);
		forward("filmlist", req, resp);
		
		
	}


	public void test(){
		System.out.println("aaab");
	}

	public void test2(){
		System.out.println("add3");
	}
	
}

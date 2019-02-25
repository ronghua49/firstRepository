package com.haohua.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.haohua.entity.Movie;
import com.haohua.entity.Type;
import com.haohua.service.MovieService;
import com.haohua.util.Page;
@WebServlet("/user/index")
public class FilmList extends BaseServlet{

	private static final long serialVersionUID = 1L;
	
	MovieService service = new MovieService();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 在jsp页面的请求数据以 分页插件 的href 属性发起的get请求  （第一次都null）
		String keys = req.getParameter("keys");
		String p = req.getParameter("p");//获得当前页码
		String typeId =req.getParameter("typeId");
		int pageNumNow=1;
		if (StringUtils.isNumeric(p)) {
			pageNumNow= Integer.parseInt(p);
		
		}
		try {
			//根据参数找到movie中所有的电影列表  封装page对象（包含当前页显示的起始条数，显示条数，查询到的总条数，和要显示的对象list）
			Page<Movie> nowPage = service.findMoviePageByPageNo(pageNumNow,keys,typeId);
			//找到排行榜电影列表
			List<Movie> sortMovieList = service.findSortMovies();
			//找到所有的电影类型
			List<Type> typelList = service.findTypeList();
			req.setAttribute("typeList", typelList);
			req.setAttribute("sortMovieList", sortMovieList);
			req.setAttribute("nowPage", nowPage);
			forward("index", req, resp);
		} catch (ServletException e) {
			resp.sendError(404,e.getMessage());
		}
	}
	
}

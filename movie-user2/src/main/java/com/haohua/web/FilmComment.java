package com.haohua.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.haohua.entity.User;
import com.haohua.service.MovieService;
@WebServlet("/movie/comment")
public class FilmComment extends BaseServlet {
	private static final long serialVersionUID = 1L;
	MovieService service  = new   MovieService();
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String content= req.getParameter("replyContent");
		String movieId = req.getParameter("movieId");
		HttpSession  session = req.getSession();
		User user = (User)session.getAttribute("user");
		if (user!=null) {
			int userId = user.getId();
			service.addComment(movieId,userId,content);
			//重定向到当详情页
			resp.sendRedirect("/detail?mid="+movieId);
		}else {
			resp.sendRedirect("/user/login");
		}
		
	}

}

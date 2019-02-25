package com.haohua.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.haohua.Exception.ServiceException;
import com.haohua.entity.Movie;
import com.haohua.entity.Reply;
import com.haohua.entity.Type;
import com.haohua.service.MovieService;
import com.haohua.util.Config;
@WebServlet("/detail")
public class DetailFilm extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	MovieService service = new MovieService();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String movieId = req.getParameter("mid");
		try {
			Movie movie = service.findDetailMovieById(movieId);
			List<Type> typeList = service.findTypeList();
			List<Reply> replyList = service.findReplysByMidState(movieId,Config.REVIEWDPASS_STATE);
			req.setAttribute("movie", movie);
			req.setAttribute("typeList", typeList);
			req.setAttribute("replyList", replyList);
		} catch (ServiceException e) {
			resp.sendError( 404	, e.getMessage());
		}
		
		forward("detail", req, resp);
	}
	
	
	
}

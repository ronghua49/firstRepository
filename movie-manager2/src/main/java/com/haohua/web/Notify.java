package com.haohua.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.haohua.service.MovieService;
import com.haohua.util.Config;
@WebServlet("/commentary/notify")
public class Notify  extends BaseServlet{
	MovieService service = new MovieService();
	private static final long serialVersionUID = 1L;
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//查询数据库 reply表是否有新的未审核的评论  state为 0
		int conut = service.countNewComment(Config.UNREVIEWD_STATE);
		send(resp, conut);
	
	}
}

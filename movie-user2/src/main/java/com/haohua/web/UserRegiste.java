package com.haohua.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.haohua.service.MovieService;
import com.haohua.util.Reqresult;
@WebServlet("/user/reg")
public class UserRegiste extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	MovieService service = new MovieService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getParameter("username");
		//如果username不为空则为注册时的名字校验请求
		if (StringUtils.isNotEmpty(username)) {
			boolean isOk = service.findUser(username);
			send(resp, isOk);
		}else {
		//为空，表示为开始的注册请求	
			forward("userReg", req, resp);
		}
	}
		@Override
		protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String tel = req.getParameter("tel");
		String email = req.getParameter("email");
		service.userRegiste(username,password,tel,email);
		send(resp, Reqresult.success());
		}
}

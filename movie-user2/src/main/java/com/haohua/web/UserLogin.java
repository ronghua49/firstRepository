package com.haohua.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.haohua.Exception.ServiceException;
import com.haohua.entity.User;
import com.haohua.service.MovieService;
import com.haohua.util.Config;
import com.haohua.util.Reqresult;
@WebServlet("/user/login")
public class UserLogin extends BaseServlet {
	private static final long serialVersionUID = 1L;
	MovieService service = new MovieService();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Cookie[] cookies = req.getCookies();
		if (cookies!=null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("name")) {
					req.setAttribute("adminName", cookie.getValue());
					break;
				}
			}
		}
		forward("userLogin", req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession seesion = req.getSession();
			
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String remember = req.getParameter("remember");
		
		if (StringUtils.isNotEmpty(remember)) {
			//把session放入cookie 
			
			Cookie cookie = new Cookie("name", username);
			cookie.setDomain("localhost");
			cookie.setPath("/");
			cookie.setMaxAge(60*60*24*7);
			cookie.setHttpOnly(true);
			resp.addCookie(cookie);
		} else {
			Cookie[] cookies = req.getCookies();
			if (cookies!=null) {
				for (Cookie cookie : cookies) {
					if ("name".equals(cookie.getName())) {
						cookie.setDomain(Config.getProperty("classroom.http"));
						cookie.setPath("/");
						cookie.setMaxAge(0);
						resp.addCookie(cookie);
						break;
					}
				}
			}
		}
		try {
			User user = service.userLogin(username,password);
			seesion.setAttribute("user", user);
			send(resp, Reqresult.success(user));
		} catch (ServiceException e) {
			req.setAttribute("error", e.getMessage());
		}
	}
	
}

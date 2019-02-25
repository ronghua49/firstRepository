package com.haohua.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.haohua.Exception.ServiceException;
import com.haohua.entity.Admin;
import com.haohua.service.MovieService;
import com.haohua.util.Config;
import com.haohua.util.Reqresult;

@WebServlet("/login")
public class Login extends BaseServlet {
	Logger logger  = LoggerFactory.getLogger(Login.class);
	MovieService service = new MovieService();
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		session.removeAttribute("user");
		Cookie[] cookies = req.getCookies();
		if (cookies!=null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("name")) {
					req.setAttribute("adminName", cookie.getValue());
					break;
				}
			}
		}
		forward("login", req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String adminName = req.getParameter("adminName");
		String password = req.getParameter("password");
		String value = req.getParameter("remember");
		//判断打钩
		if (StringUtils.isNotEmpty(value)) {
			//把session放入cookie 
			
			Cookie cookie = new Cookie("name", adminName);
			cookie.setDomain(Config.getProperty("classroom.http"));
			cookie.setPath("/");
			cookie.setMaxAge(60*60*24*7);
			cookie.setHttpOnly(true);
			resp.addCookie(cookie);
		} else {
			Cookie[] cookies = req.getCookies();
			if (cookies!=null) {
				for (Cookie cookie : cookies) {
					if ("name".equals(cookie.getName())) {
						cookie.setDomain("localhost");
						cookie.setPath("/");
						cookie.setMaxAge(0);
						resp.addCookie(cookie);
						break;
					}
				}
			}
		}

		try {
			Admin admin = service.Login(adminName, password);
			HttpSession session = req.getSession();
			session.setAttribute("admin", admin);
			// 成功的话则 发送success提示信息和对象(把成功信息封装对象)
			Reqresult result = Reqresult.success(admin);
			// 把成功后的数据以json形式响应前端
			send(resp, result);
			logger.debug("{}管理员登录了",admin.getAdmin_name());
		} catch (ServiceException e) {
			Reqresult result = Reqresult.error(e.getMessage());
			send(resp, result);
		}

	}

}

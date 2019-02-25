package com.haohua.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.haohua.entity.User;
import com.haohua.util.Reqresult;

@WebServlet("/session/check")
public class SessionCheck  extends BaseServlet{
	private static final long serialVersionUID = 1L;
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");
		if (user!=null) {
			System.out.println(user.getUserName());
		}
		send(resp, Reqresult.success(user));
	
	}
}

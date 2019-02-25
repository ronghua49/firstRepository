package com.haohua.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.haohua.Exception.ServiceException;
import com.haohua.service.MovieService;
@WebServlet("/type/check/typeName")
public class CheckTypeName extends BaseServlet{
	MovieService service = new MovieService();

	private static final long serialVersionUID = 1L;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String tid = req.getParameter("typeId");
		String typeName = req.getParameter("typeName");
		//typeName = new String(typeName.getBytes("ISO8859-1"), "UTF-8");
		try {
			boolean isok = service.findTypeName(typeName,tid);
			send(resp, isok);
		} catch (ServiceException e) {
			resp.sendError(404, e.getMessage());
		}
	}
	
}

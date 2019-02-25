package com.haohua.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.haohua.entity.Type;
import com.haohua.service.MovieService;
import com.haohua.util.Page;

@WebServlet("/type/filmTypes")
public class filmTypesManage extends BaseServlet {

	private static final long serialVersionUID = 1L;

	MovieService service = new MovieService();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String keys = req.getParameter("typeName");
		String p = req.getParameter("page");
		int pageNumNow = 1;
		if (StringUtils.isNumeric(p)) {
			pageNumNow = Integer.parseInt(p);
		}
		// 找到type中所有的type列表
		Page<Type> nowPage = service.findTypeByTypeName(pageNumNow, keys);
		req.setAttribute("nowPage", nowPage);
		forward("typesManage", req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String tid = req.getParameter("tid");
		String typeName = req.getParameter("typeName");
		String editName = req.getParameter("editName");
		String editTypeId = req.getParameter("editTypeId");
		//删除节点（ajax请求，不传送数据，通过抛异常，让前端判断是否可删除）
		if (StringUtils.isNotEmpty(tid)) {
			service.deleteType(tid);
		}
		//新增节点（ajax请求，不传送数据,失败抛异常）
		if (StringUtils.isNotEmpty(typeName)) {
			service.AddType(typeName);
		}
		//修改节点（ajax请求，不传送数据,失败抛异常）
		if (StringUtils.isNotEmpty(editName)){
			service.editType(editName,editTypeId);
		}
	}
}

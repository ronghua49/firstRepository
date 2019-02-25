package com.haohua.web;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.haohua.entity.Type;
import com.haohua.service.MovieService;
import com.haohua.util.Reqresult;

@WebServlet("/type/list")
public class TypeList extends BaseServlet {
	//ajax发起异步请求在加载页面时，请求后台数据 显示电影类型列表
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		MovieService service = new MovieService();
		String mid = req.getParameter("mid");
		if (StringUtils.isEmpty(mid)) {
			//mid为空表示是增加添加电影
			List<Type> typelList = service.findTypeList();
			send(resp, Reqresult.success(typelList));
			
		} else {
			//mid不为空表示，表示为修改电影
			//根据电影id找到电影的类型集合
			//Map<String , List<Type>> typemaps= service.findTypeList(mid);
			List<Type> selectedTypeList = service.findTypeList(mid);
			//封装查询到的当前电影的类型集合  按照 results：{id：  text： }  发送json数据
			Map<String, Object> map = new HashMap<>();
			map.put("results", selectedTypeList);
			send(resp, map);
		}

	}

}

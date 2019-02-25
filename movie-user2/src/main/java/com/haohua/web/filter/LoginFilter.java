package com.haohua.web.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.haohua.entity.Admin;

public class LoginFilter extends AbstractFilter {
	List<String> uriList = null;

	@Override
	public void init(FilterConfig config) throws ServletException {
		String FiltUri = config.getInitParameter("FiltUri");
		// 将得到的过滤请求名转为数组
		String[] split = FiltUri.split(",");
		// 将数组转为list 集合
		uriList = Arrays.asList(split);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		String uri = req.getRequestURI();
		// 判断请求名是否以/movie,/type,/commentary开始。
		if (isFilter(uri)) {
			// 判断是否登录
			HttpSession session = req.getSession();
			Admin admin = (Admin) session.getAttribute("admin");
			if (admin != null) {
				chain.doFilter(req, resp);
			} else {
				resp.sendRedirect("/login?callback=" + uri);
			}
		} else {
			chain.doFilter(req, resp);
		}

	}

	private boolean isFilter(String uri) {
		// 表示初始化的过滤请求名中没有 数据
		if (uriList == null) {
			return false;
		}
		// 请求中有以 这些请求名开头的 则判断
		for (String string : uriList) {
			if (uri.startsWith(string)) {
				return true;
			}
		}
		return false;
	}

}

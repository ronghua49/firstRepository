package com.haohua.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.lang3.StringUtils;

public class EncodingFilter extends AbstractFilter {
	String encode = "UTF-8";
	@Override
	public void init(FilterConfig config) throws ServletException {
		String encode = config.getInitParameter("Encoding");
		if (StringUtils.isNotEmpty(encode)) {
			this.encode =  encode;
		}
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		
		req.setCharacterEncoding(encode);
		resp.setCharacterEncoding(encode);
		
		chain.doFilter(req, resp);
	}

}

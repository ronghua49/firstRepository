package com.haohua.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.haohua.entity.Reply;
import com.haohua.service.MovieService;
import com.haohua.util.Config;
import com.haohua.util.Page;
@WebServlet("/commentary/commentManage")
public class CommentaryManage extends BaseServlet{
	
	private static final long serialVersionUID = 1L;
	MovieService service = new MovieService();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//查询数据库所有未审核的回复列表 ，展示页面
		int page = 1;
		String p = req.getParameter("page");
		
		if (StringUtils.isNotEmpty(p)) {
			page = Integer.parseInt(p);
		}
		
		Page<Reply> replypage = service.findReplyList(Config.UNREVIEWD_STATE,page);
		req.setAttribute("replypage", replypage);
		forward("commentaryManage", req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			String replyId = req.getParameter("replyId");
			//通过或者不通过的状态
			String state=req.getParameter("state");
			//根据replyid 更新reply表,若通过，则电影的评论数加一
			service.updateReply(Integer.parseInt(replyId),Integer.parseInt(state));
			
		
	}
		
}

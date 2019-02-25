package com.haohua.dao;

import java.util.List;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.GenerousBeanProcessor;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.haohua.entity.Reply;
import com.haohua.util.Dbhelp;

public class ReplyDao {


	public List<Reply> findReplyByMidAndState(int mid, int state) {
		
			String sql="select r.*,u.username from t_reply r,t_user u where  r.user_id=u.id and r.movie_id=? and r.state=?";
			return Dbhelp.Query(sql, new BeanListHandler<>(Reply.class, new BasicRowProcessor(new GenerousBeanProcessor())), mid,state);
	}

	public void addComment(int movieId, int userId, String content) {

		String sql = "insert into t_reply (content,state,user_id,movie_id) values (?,0,?,?)";
		Dbhelp.update(sql, content,userId,movieId);
	}

	public List<Reply> findReplyList(int unchecked,int start,int pageSize) {
		String sql = "select r.*,m.movie_name,u.username from t_reply r, t_movie m,t_user u where r.movie_id=m.id and r.user_id=u.id and r.state=? limit ?,?";
		
		return Dbhelp.Query(sql, new BeanListHandler<>(Reply.class, new BasicRowProcessor(new  GenerousBeanProcessor())), unchecked,start,pageSize);
	}

	public void updateReply(int replyId, int state) {
			String sql = "update t_reply set state=? where id=?";
			Dbhelp.update(sql, state,replyId);
	}

	public int countUnview(int unviewState) {
		String sql = "select count(*) from t_reply where state=?";
		
		return Dbhelp.Query(sql, new ScalarHandler<Long>(), unviewState).intValue();
	}

	
}

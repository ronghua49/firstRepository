package com.haohua.dao;

import java.util.List;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.GenerousBeanProcessor;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

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

	/**根据电影id查询最后一次 评论的时间 评论状态为通过
	 * @param mid 电影id
	 * @param rEVIEWDPASS_STATE 通过状态
	 * @return
	 */
	public Reply findLastReply(int mid, int state) {
		String sql = "select * from t_reply where state=? and movie_id =? order by create_time desc limit 0,1";
		return Dbhelp.Query(sql, new BeanHandler<>(Reply.class,new BasicRowProcessor(new GenerousBeanProcessor())), state,mid);
	}

}

package com.haohua.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.GenerousBeanProcessor;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang3.StringUtils;

import com.haohua.entity.Movie;
import com.haohua.util.Dbhelp;

public class MovieDao {

	public Integer save(Movie movie) {
		System.out.println("执行sql开始添加电影了 ");
		String sql="insert into t_movie (movie_name,director_name,area,year,img_name,content,simple_content,scan_num,reply_num)values(?,?,?,?,?,?,?,?,?)";
		return Dbhelp.insert(sql, new ScalarHandler<Long>(), movie.getMovieName(),movie.getDirectorName(),movie.getArea(),movie.getYear(),movie.getImgName(),movie.getContent(),movie.getSimpleContent(),movie.getScanNum(),movie.getReplyNum()).intValue();
	}

	public List<Movie> findAll(Map<String , String > params) {
		String sql= "select*from t_movie m ";
		List<Object> paramList = new ArrayList<>();
		
		 if (StringUtils.isNotEmpty(params.get("keys"))) {
			String keys = params.get("keys");
			keys = "%"+keys+"%";
			sql+="where movie_name like ? ";
			paramList.add(keys);
		}
		sql+="limit ?,?";
		paramList.add(Integer.parseInt(params.get("start")));
		paramList.add(Integer.parseInt(params.get("pageSize")));
		return Dbhelp.Query(sql, new BeanListHandler<>(Movie.class, new BasicRowProcessor(new GenerousBeanProcessor())),paramList.toArray());
	}

	public Movie  findMovieByName(String filmName) {
		String sql = "select*from t_movie where movie_name=?";
		return Dbhelp.Query(sql, new BeanHandler<>(Movie.class, new BasicRowProcessor(new GenerousBeanProcessor())),filmName);
	}

	public int findMovieCount(String keys) {
		String sql = "select count(*) from t_movie  ";
		
		List<String > params = new ArrayList<>();
		if (StringUtils.isNotEmpty(keys)) {
			sql+="where movie_name like?";
			keys = "%"+keys+"%";
			params.add(keys);
		}
		return Dbhelp.Query(sql, new ScalarHandler<Long>(),params.toArray()).intValue();
	}
	public void deleteById(Integer movieId) {
		String sql = "delete from t_movie where id=?";
		Dbhelp.update(sql, movieId);
	}
	public Movie findById(int mid) {
		String sql = "select * from t_movie where t_movie.id=? ";
		return Dbhelp.Query(sql, new BeanHandler<>(Movie.class, new BasicRowProcessor(new GenerousBeanProcessor())), mid);
	}

	public void editBy(Movie movie) {
		String sql = "update t_movie  set movie_name=?,director_name=?,area=?,year=?,img_name=?,content=?,scan_num=?,reply_num=?,remark=? where id=?";
		Dbhelp.update(sql, movie.getMovieName(),movie.getDirectorName(),movie.getArea(),movie.getYear(),movie.getImgName(),movie.getContent(),movie.getScanNum(),movie.getReplyNum(),movie.getRemark(),movie.getId());
	}
	public List<Movie> findMovies(int tid) {
		String sql = "select * from t_movie m,t_type t,t_movie_type mt where m.id=mt.movie_id and t.id=mt.type_id and t.id=? ";
		return Dbhelp.Query(sql, new BeanListHandler<>(Movie.class, new BasicRowProcessor(new GenerousBeanProcessor())), tid);
	}
	public Movie findMovieByReplyId(int replyId) {
		String sql = "select m.* from t_movie m,t_reply r where m.id=r.movie_id and r.id=? ";
		return Dbhelp.Query(sql, new BeanHandler<>(Movie.class,new BasicRowProcessor(new GenerousBeanProcessor())), replyId);
	}
	
}

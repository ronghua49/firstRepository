package com.haohua.dao;

import java.util.List;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.GenerousBeanProcessor;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.haohua.entity.MovieType;
import com.haohua.entity.Type;
import com.haohua.util.Dbhelp;

public class MovieTypeDao {

	public void save(MovieType movieType) {
		String sql="insert into t_movie_type (type_id,movie_id) values (?,?)";
		Dbhelp.update(sql, movieType.getType_id(),movieType.getMovie_id());
		
	}

	public List<Type> findTypesByMid(int mid) {
		
		String sql = "select id,type_name as text from t_type t,t_movie_type mt where t.id=mt.type_id and mt.movie_id=?";
		
		return Dbhelp.Query(sql, new BeanListHandler<>(Type.class, new BasicRowProcessor(new GenerousBeanProcessor())), mid);
	}

	public  void deleteByMid(Integer movieId) {
		String sql = "delete from t_movie_type where t_movie_type.movie_id=?";
		Dbhelp.update(sql, movieId);
	}

	/*public void editBy(int mid, List<Integer> typeIdlist) {
	
			
	String sql = "update t_movie_type mt set mt.type_id=?,where mt.movie_id=? and mt.type_id= t.id";
	
	}*/
	
	

}

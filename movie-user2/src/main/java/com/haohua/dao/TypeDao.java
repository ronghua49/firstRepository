package com.haohua.dao;

import java.util.List;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.GenerousBeanProcessor;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.haohua.entity.Type;
import com.haohua.util.Dbhelp;

public class TypeDao {

	public List<Type> findAll() {
		String sql = "select id,type_name as text from t_type";
		return Dbhelp.Query(sql, new BeanListHandler<>(Type.class));
	}
	
	public static void deleteByMid(Integer movieId) {
		String sql = "delete from t_movie_type where t_movie_type.movie_id=?";
		Dbhelp.update(sql, movieId);
	}

	public List<Type> findTypesByMid(int mid) {

		String sql = "select id,type_name as text,create_time,remark from t_type t,t_movie_type mt where t.id=mt.type_id and mt.movie_id=?";

		return Dbhelp.Query(sql, new BeanListHandler<>(Type.class, new BasicRowProcessor(new GenerousBeanProcessor())),
				mid);
	}

}

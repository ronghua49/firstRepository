package com.haohua.dao;

import org.apache.commons.dbutils.handlers.BeanHandler;

import com.haohua.entity.User;
import com.haohua.util.Dbhelp;

public class UserDao {

	public User findUserByName(String username) {
		String sql ="select * from t_user where username=?";
		return Dbhelp.Query(sql, new BeanHandler<>(User.class), username);
	}

	public void insert(String username, String password,String tel,String email) {
		String sql = "insert into t_user (username,password,tel,email) values (?,?,?,?)";
		Dbhelp.update(sql, username,password,tel,email);
	}


}

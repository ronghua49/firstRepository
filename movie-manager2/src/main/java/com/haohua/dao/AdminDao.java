package com.haohua.dao;

import org.apache.commons.dbutils.handlers.BeanHandler;

import com.haohua.entity.Admin;
import com.haohua.util.Dbhelp;

public class AdminDao {

	public Admin findAdminByName(String adminName) {
		String sql = "select * from t_admin where admin_name=?";
		return Dbhelp.Query(sql, new BeanHandler<>(Admin.class), adminName);
	}
}

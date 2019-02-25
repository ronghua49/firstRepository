package com.haohua.dao;



import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import com.haohua.service.Slat;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Before;
import org.junit.Test;

import com.haohua.entity.Type;

public class TypeDaoTextCase {

	TypeDao typeDao;

	@Before
	public void before() {
		typeDao = new TypeDao();
	}
	@Test
	public void textFindTypelist() {
		List<Type> findAll = typeDao.findAll();
		assertEquals(findAll.size(), 9);
	}
	@Test
	public void OrcaleJdbcText() throws Exception{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:ORCL", "SCOTT", "tiger");
		String sql="select * from emp where empno = ?";
		PreparedStatement prep = conn.prepareStatement(sql);
		prep.setInt(1, 7788);
		
		ResultSet res =  prep.executeQuery();
		if (res.next()) {
			System.out.println(res.getString("ename"));
		}
	}

	@Test
	public void openSecret(){
		String s = DigestUtils.md5Hex("110" + Slat.SLAT);
		System.out.println(s
		);
	}
}

package com.haohua.dao;

import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.haohua.entity.Admin;
public class AdminDaoTestCase {
	private AdminDao adminDao;
	@Before
	public void before() {
		adminDao = new AdminDao();
	}
	@Test
	public void textFindAdminByName() {
		Admin admin = adminDao.findAdminByName("tom");
		assertNotNull(admin);
	}
	@After
	public void after() {
		System.out.println("测试结束了");
	}
}

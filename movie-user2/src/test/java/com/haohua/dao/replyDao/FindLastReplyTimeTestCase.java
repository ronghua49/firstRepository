package com.haohua.dao.replyDao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.Timestamp;

import org.junit.Test;

import com.haohua.dao.ReplyDao;
import com.haohua.entity.Reply;

public class FindLastReplyTimeTestCase {

	ReplyDao replyDao = new ReplyDao();
	@Test
	public void testFindLastReplyTime() {
		Reply lastrepReply = replyDao.findLastReply(43, 1);
		assertNotNull(lastrepReply);
		
	}
	
}

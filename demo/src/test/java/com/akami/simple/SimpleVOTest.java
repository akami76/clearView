package com.akami.simple;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import com.akami.clearview.simple.dao.SimpleDAO;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/**.xml" })
public class SimpleVOTest {
	
	private static final Logger logger = LoggerFactory.getLogger(SimpleVOTest.class);

	@Inject
	private SimpleDAO dao;
	
	@Test
	public void 리스트조회(){
	 Assert.isTrue(dao.getList().size() > 0);
	}

}

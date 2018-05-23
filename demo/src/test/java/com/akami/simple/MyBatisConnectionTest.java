package com.akami.simple;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		locations = {"file:src/main/webapp/WEB-INF/spring/**.xml"}
		)
public class MyBatisConnectionTest {
	
	@Inject
	private SqlSessionFactory sqlSessionFactory;
	
	@Test
	public void 마이바티스_연동테스트(){
		System.out.println(sqlSessionFactory);
	}

	@Test
	public void 마이바티스_세션연결(){
		try(SqlSession session = sqlSessionFactory.openSession()){
			System.out.println(session);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}

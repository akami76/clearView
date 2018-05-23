package com.akami.clearview.simple.dao;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@Repository
public class SimpleDAOImpl implements SimpleDAO{

	@Inject
	private SqlSession sqlsession;
	private static final String namespace ="com.akami.mapper.simple";

	@Override
	public List getList() {
		
		return sqlsession.selectList(namespace+".read");
	}
	
	
	
	
}

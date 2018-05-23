package com.akami.clearview.simple.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.akami.clearview.simple.dao.SimpleDAO;
import com.akami.clearview.simple.domain.SimpleVO;


@Service
public class SimpleServiceImpl implements SimpleService{

    @Inject 
	private SimpleDAO dao;
	
	@Override
	public List<SimpleVO> getList() {
		
		return dao.getList();
	}

}

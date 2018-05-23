package com.akami.simple;

import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.Test;

public class MySQLConnnetionTest {
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String URL = "jdbc:mysql://127.0.0.1:3306/akamidb";
	private static final String USER = "root";
	private static final String PW = "qazwsx";
	
	@Test
	public void MySQL연결테스트() throws Exception{
		Class.forName(DRIVER);
		try(Connection con = DriverManager.getConnection(URL, USER, PW)){
			
			System.out.println(con);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	

}

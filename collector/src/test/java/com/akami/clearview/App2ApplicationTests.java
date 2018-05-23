package com.akami.clearview;

import com.WebsokcetApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * Created by akamikang on 2017. 7. 11..
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = WebsokcetApplication.class)
@WebAppConfiguration
public class App2ApplicationTests {
    @Autowired
    private DataSource ds;

    @Test
    public void contextLoads(){

    }

    @Test
    public void testConnection() throws Exception{
        System.out.println(ds);
        Connection con = ds.getConnection();
        System.out.println(con);
        con.close();
    }
}

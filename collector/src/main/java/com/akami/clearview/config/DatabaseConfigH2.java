package com.akami.clearview.config;

/**
 * Created by akamikang on 2017. 7. 11..
 */


import com.akami.clearview.domain.JdbcSettings;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.SQLException;

@Slf4j
@Configuration
@MapperScan(
        basePackages = {"com.akami.clearview.db"},
        sqlSessionFactoryRef = "sqlSessionFactory"
)
public class DatabaseConfigH2 {

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Inject Beans
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    @Inject
    private JdbcSettings jdbcSettings;


    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Member Variables
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Constructors
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Public Methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    @Bean
    public DataSource dataSource() throws SQLException {

        log.debug("================================================");
        log.debug(String.format("DataSource : [%s] %s", jdbcSettings.getDriverClassName(), jdbcSettings.getUrl()));
        log.debug("================================================");

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(jdbcSettings.getDriverClassName());
        dataSource.setUrl(jdbcSettings.getUrl());
        dataSource.setUsername(jdbcSettings.getUsername());
        dataSource.setPassword(jdbcSettings.getPassword());

        return dataSource;
    }


//    @Bean
//    public DataSource dataSource() {
//        DataSource dataSource = Datcom.akami.clearview.dbaSourceBuilder.create().build();
//        return dataSource;
//        //        return DataSourceBuilder
//        //                .create()
//        //                .build();
//    }

   /* @Bean(name = "h2SqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(
            @Qualifier("h2DataSource")
                    DataSource dataSource) throws Exception {

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:mapper/h2*//*.xml"));
        sqlSessionFactoryBean.setConfigLocation(resolver.getResource("classpath:config/mybatis-config.xml"));
        sqlSessionFactoryBean.setFailFast(true);
        return sqlSessionFactoryBean.getObject();
    }

    */

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {

        Resource[] res = new PathMatchingResourcePatternResolver().getResources("classpath:mapper/h2/*.xml");

        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setMapperLocations(res);

        return sessionFactory.getObject();
    }

}


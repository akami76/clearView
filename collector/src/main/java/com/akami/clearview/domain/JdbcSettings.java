package com.akami.clearview.domain;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.inject.Named;

/**
 * Created by akamikang on 2017. 7. 13..
 */
@Data
@Named
@ConfigurationProperties(prefix = "my.jdbc")
public class JdbcSettings {

    private String url;
    private String driverClassName;

    private String username;
    private String password;

}

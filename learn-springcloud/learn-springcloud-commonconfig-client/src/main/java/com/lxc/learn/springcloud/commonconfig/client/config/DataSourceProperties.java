package com.lxc.learn.springcloud.commonconfig.client.config;

import com.lxc.learn.common.util.core.BaseDto;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author lixianchun
 * @description
 * @date 2020/8/12
 */
@Slf4j
@Configuration
@ConfigurationProperties(prefix = "datasource")
@Data
public class DataSourceProperties extends BaseDto {


    private String url;

    private String userName;

    private String password;

}

package com.lxc.learn.springcloud.commonconfig.client.config;

import com.lxc.learn.common.util.core.BaseDto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author lixianchun
 * @description
 * @date 2020/8/12
 */
@Configuration
@ConfigurationProperties(prefix = "datasource")
@Setter
@Getter
public class DataSourceProperties  {


    private String url;

    private String userName;

    private String password;

    @Override
    public String toString() {
        return new StringBuilder().append("url:" ).append(this.url)
                .append(",userName:").append(this.userName)
                .append(",password:").append(this.password).toString();
    }
}

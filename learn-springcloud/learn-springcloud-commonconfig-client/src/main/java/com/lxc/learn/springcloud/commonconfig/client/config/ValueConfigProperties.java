package com.lxc.learn.springcloud.commonconfig.client.config;

import com.lxc.learn.common.util.core.BaseDto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author lixianchun
 * @description
 * @date 2020/8/12
 */
@Component
@Setter
@Getter
public class ValueConfigProperties{

    /**
     * 此注解配置的属性，即使通过actuator/refresh刷新后，任然无法更新
     * 解决办法：
     * 1.可通过扩展actuator/refresh 实现
     * 2.自定endpoint
     */

    @Value("${datasource.url}")
    private String url;
    @Value("${datasource.userName}")
    private String userName;
    @Value("${datasource.password}")
    private String password;

    @Override
    public String toString() {
        return new StringBuilder().append("url:" ).append(this.url)
                .append(",userName:").append(this.userName)
                .append(",password:").append(this.password).toString();
    }

}

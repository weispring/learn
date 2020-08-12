package com.lxc.learn.springcloud.commonconfig.client.config;

import com.lxc.learn.common.util.core.BaseDto;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author lixianchun
 * @description
 * @date 2020/8/12
 */
@Slf4j
@Component
@Data
public class ValueConfigProperties extends BaseDto{

    @Value("${sms.host}")
    private String host;

    @Value("${sms.port}")
    private Integer port;

}

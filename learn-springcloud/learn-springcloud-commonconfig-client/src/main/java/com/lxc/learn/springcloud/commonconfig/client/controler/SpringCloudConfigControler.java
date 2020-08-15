package com.lxc.learn.springcloud.commonconfig.client.controler;

import com.alibaba.fastjson.JSON;
import com.lxc.learn.common.util.JsonUtil;
import com.lxc.learn.common.util.core.Resp;
import com.lxc.learn.common.util.core.RespUtil;
import com.lxc.learn.springcloud.commonconfig.client.config.DataSourceProperties;
import com.lxc.learn.springcloud.commonconfig.client.config.ValueConfigProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.bootstrap.config.PropertySourceBootstrapConfiguration;
import org.springframework.cloud.config.client.ConfigServicePropertySourceLocator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lixianchun
 * @description
 * @date 2020/8/12
 */
@Slf4j
//@RefreshScope 不加此注解也能刷新DataSourceProperties
@RestController
public class SpringCloudConfigControler {

    @Autowired
    private ValueConfigProperties valueConfigProperties;
    @Autowired
    private DataSourceProperties dataSourceProperties;


    @RequestMapping("/configProperies")
    public Resp configProperies(){
        log.info("dataSourceProperties: {}", dataSourceProperties.toString());
        return RespUtil.success(dataSourceProperties.toString());
    }


    @RequestMapping("/valueConfigProperties")
    public Resp valueConfigProperties(){
        log.info("valueConfigProperties: {}", valueConfigProperties.toString());
        return RespUtil.success(valueConfigProperties.toString());
    }


}

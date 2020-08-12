package com.lxc.learn.springcloud.commonconfig.client.controler;

import com.alibaba.fastjson.JSON;
import com.lxc.learn.common.util.core.Resp;
import com.lxc.learn.common.util.core.RespUtil;
import com.lxc.learn.springcloud.commonconfig.client.config.DataSourceProperties;
import com.lxc.learn.springcloud.commonconfig.client.config.ValueConfigProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lixianchun
 * @description
 * @date 2020/8/12
 */
@Slf4j
@RestController
public class SpringCloudConfigControler {

    @Autowired
    private ValueConfigProperties valueConfigProperties;
    @Autowired
    private DataSourceProperties dataSourceProperties;


    @RequestMapping("/configProperies")
    public Resp configProperies(){
        log.info("dataSourceProperties: {}", JSON.toJSONString(dataSourceProperties));
        return RespUtil.success(dataSourceProperties);
    }


    @RequestMapping("/valueConfigProperties")
    public Resp valueConfigProperties(){
        log.info("valueConfigProperties: {}", JSON.toJSONString(valueConfigProperties));
        return RespUtil.success(valueConfigProperties);
    }
}

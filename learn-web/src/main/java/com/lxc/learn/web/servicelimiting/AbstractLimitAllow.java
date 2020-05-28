package com.lxc.learn.web.servicelimiting;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author lixianchun
 * @description
 * @date 2020/5/26
 */
@Slf4j
public abstract class AbstractLimitAllow {

    public abstract boolean disposal(String apiCode,Object obj);

}

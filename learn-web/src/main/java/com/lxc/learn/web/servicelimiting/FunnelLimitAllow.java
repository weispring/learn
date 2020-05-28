package com.lxc.learn.web.servicelimiting;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author lixianchun
 * @description
 * @date 2020/5/26
 */
@Component
@Slf4j
public class FunnelLimitAllow extends AbstractLimitAllow {

    private Map<String,LinkedBlockingDeque> map = new ConcurrentHashMap();
    /** 容量 = 处理速率 * 接口超时时间 */
    private static final Integer REQUEST_QUEUE_CAPACITY = 10 * 30;


    @Override
    public boolean disposal(String apiCode,Object obj) {
        if (map.get(apiCode) == null){
            map.putIfAbsent(apiCode,new LinkedBlockingDeque(REQUEST_QUEUE_CAPACITY));
        }
        return map.get(apiCode).offer(obj);
    }

}

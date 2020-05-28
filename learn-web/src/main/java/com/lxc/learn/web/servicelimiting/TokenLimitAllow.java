package com.lxc.learn.web.servicelimiting;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.lxc.learn.web.servicelimiting.GenerateToken.TOKEN_QUEUE;

/**
 * @author lixianchun
 * @description
 * @date 2020/5/26
 */
@Component
@Slf4j
public class TokenLimitAllow extends AbstractLimitAllow {

    @Override
    public boolean disposal(String apiCode, Object obj) {
        if (TOKEN_QUEUE.poll() == null){
            throw new RuntimeException("Service limit has reached the limit");
        }
        return true;
    }
}

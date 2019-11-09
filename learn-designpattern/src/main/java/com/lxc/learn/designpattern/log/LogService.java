package com.lxc.learn.designpattern.log;

import com.lxc.learn.common.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lixianchun
 * @Description
 * @date 2019/11/9 12:04
 */
@Slf4j
@Service
public class LogService {

    public void addBatch(List<LogEntity> logEntities){
        log.info("保存日志入库：{}", JsonUtil.objectToJson(logEntities));
    }

}

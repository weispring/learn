package com.lxc.learn.junit.controller;

import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.lxc.learn.common.util.DateUtil;
import com.lxc.learn.common.util.core.Resp;
import com.lxc.learn.common.util.core.RespUtil;
import com.lxc.learn.junit.entity.Crecord;
import com.lxc.learn.junit.mapper.CrecordMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

import static com.lxc.learn.common.util.DateUtil.FORMATDAY;
import static com.lxc.learn.common.util.DateUtil.FORMAT_LONG;

/**
 * @author lixianchun
 * @description
 * @date 2020/7/28
 */
@Slf4j
@RestController
public class RecordControler {

    @Autowired
    private CrecordMapper crecordMapper;

    @RequestMapping("/record")
    public Resp record(HttpServletRequest request){
        synchronized (RecordControler.class){
            Crecord crecord = crecordMapper.last();
            if (crecord != null){
                if (System.currentTimeMillis() - crecord.getTime() < 30 * 60 * 1000){
                    return RespUtil.fail("重复了:" + DateUtil.parse(new Date(crecord.getTime()),FORMAT_LONG));
                }
            }
            int result = crecordMapper.insert(new Crecord().setId(IdWorker.getId()).setTime(System.currentTimeMillis()).setDate(DateUtil.format(FORMATDAY)).setCreatedTime(System.currentTimeMillis()));
            if (result > 0){
                return RespUtil.success();
            }
        }
        return RespUtil.fail();
    }
}

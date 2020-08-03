package com.lxc.learn.junit.test.api;

import com.lxc.learn.junit.entity.Crecord;
import com.lxc.learn.junit.mapper.CrecordMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author lixianchun
 * @description
 * @date 2020/8/3
 */
@Slf4j
public class MapperTest extends BaseTest  {

    @Autowired
    private CrecordMapper crecordMapper;

    @Test
    public void test(){
        log.info("-");

        Crecord crecord = crecordMapper.last();
        crecord = crecordMapper.getSelect(crecord.getId());
        crecord = crecordMapper.getSelectProvider(crecord.getId());

        log.info("-");
    }
}

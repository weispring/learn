package com.lxc.learn.junit.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.lxc.learn.junit.entity.Crecord;


public interface CrecordMapper extends BaseMapper<Crecord> {

    public Crecord last();

}

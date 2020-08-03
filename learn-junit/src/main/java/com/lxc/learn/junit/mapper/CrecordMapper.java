package com.lxc.learn.junit.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.lxc.learn.junit.entity.Crecord;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;


public interface CrecordMapper extends BaseMapper<Crecord> {

    public Crecord last();


    @Select(value = "select * from crecord where id = #{id}")
    public Crecord getSelect(@Param("id") Long id);


    @SelectProvider(type = CrecordMapperProvider.class, method = "select")
    public Crecord getSelectProvider(@Param("id") Long id);
}

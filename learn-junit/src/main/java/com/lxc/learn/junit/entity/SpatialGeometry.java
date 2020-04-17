package com.lxc.learn.junit.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 测试空间所应
 * </p>
 *
 * @author li xian chun
 * @since 2020-04-17
 */
@TableName("spatial_geometry")
public class SpatialGeometry implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField("NAME")
    private String name;
    private Integer dot;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDot() {
        return dot;
    }

    public void setDot(Integer dot) {
        this.dot = dot;
    }

    @Override
    public String toString() {
        return "SpatialGeometry{" +
        "id=" + id +
        ", name=" + name +
        ", dot=" + dot +
        "}";
    }
}

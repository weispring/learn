package com.lxc.learn.common.util.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.io.Serializable;

/**
 * @author lixianchun
 * @Description
 * @date 2019/7/1 11:41
 */
public class BaseDto implements Serializable, Cloneable{

    private static final long serialVersionUID = 1472242277044799282L;

    public BaseDto() {
    }

    public String toString() {
        return JSON.toJSONString(this, new SerializerFeature[]{SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullListAsEmpty, SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.WriteDateUseDateFormat});
    }

    public int hashCode() {
        return this.toString().hashCode();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else {
            return this.toString().equals(obj.toString());
        }
    }

}

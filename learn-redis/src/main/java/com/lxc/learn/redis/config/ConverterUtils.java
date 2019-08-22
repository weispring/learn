package com.lxc.learn.redis.config;

/**
 * @author lixianchun
 * @Description
 * @date 2019/8/22 10:31
 */
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.lxc.learn.common.util.RefectUtils;

import java.util.ArrayList;
import java.util.List;

public class ConverterUtils {
    public ConverterUtils() {
    }

    public static String converterJson(Object obj) {
        return JSONObject.toJSONString(obj, new ValueFilter() {
            public Object process(Object obj, String s, Object v) {
                Class clazz;
                if (v == null) {
                    clazz = RefectUtils.getFieldClassAllSupper(obj, s);
                    if (clazz != null) {
                        if (clazz == String.class) {
                            return "";
                        }

                        if (clazz == Integer.class || clazz == Float.class || clazz == Double.class || clazz == Long.class || clazz == Short.class) {
                            return "";
                        }

                        if (clazz == Boolean.TYPE || clazz == Boolean.class) {
                            return "";
                        }

                        if (clazz == List.class) {
                            return new ArrayList();
                        }

                        if (clazz.isArray()) {
                            return new ArrayList();
                        }

                    }
                } else {
                    clazz = RefectUtils.getFieldClassAllSupper(obj, s);
                    if (clazz != null && (clazz == Integer.class || clazz == Float.class || clazz == Double.class || clazz == Long.class || clazz == Short.class)) {
                        return v.toString();
                    }
                }

                return v;
            }
        }, new SerializerFeature[]{SerializerFeature.WriteDateUseDateFormat});
    }
}


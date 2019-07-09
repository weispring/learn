package com.lxc.learn.common.util;

/**
 * @author lixianchun
 * @Description
 * @date 2019/6/26 15:13
 */
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

public class JsonUtil {
    private static ObjectMapper JSON_MAPPER = new ObjectMapper();

    {
        /** 反序列化时 忽略不知道的字段
         *  第二种解决方案
         *  在需要转化的对象的类中添加注解，注解信息如下：
         @JsonIgnoreProperties(ignoreUnknown = true)
         */
        JSON_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, false);

        //JSON_MAPPER.convertValue(, )
    }
    protected JsonUtil() {
    }

    public static <T> String objectToJson(T data) {
        String json = null;
        if (data != null) {
            try {


                /*
                 1.实体上
                @JsonInclude(Include.NON_NULL)将该标记放在属性上，如果该属性为NULL则不参与序列化
                如果放在类上边,那对这个类的全部属性起作用
                Include.Include.ALWAYS 默认
                Include.NON_DEFAULT 属性为默认值不序列化
                Include.NON_EMPTY 属性为 空（“”） 或者为 NULL 都不序列化
                Include.NON_NULL 属性为NULL 不序列化 */
                //2. 反序列化忽略null值
                JSON_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
                json = JSON_MAPPER.writeValueAsString(data);
            } catch (Exception var3) {
                throw new RuntimeException("objectToJson method error: " + var3);
            }
        }

        return json;
    }

    public static <T> T jsonToObject(String json, Class<T> cls) {
        T object = null;
        if (!StringUtils.isEmpty(json) && cls != null) {
            try {
                if (json.startsWith("\"{")) {
                    json = json.replace("\"{", "{").replace("}\"", "}").replace("\\\"", "\"");
                }

                object = JSON_MAPPER.readValue(json, cls);
            } catch (Exception var4) {
                throw new RuntimeException("jsonToObject method error: " + var4);
            }
        }

        return object;
    }

    public static List<Map<String, Object>> jsonToList(String json) {
        List<Map<String, Object>> list = null;
        if (!StringUtils.isEmpty(json)) {
            try {
                if (json.startsWith("\"[")) {
                    json = json.replace("\"[", "[").replace("]\"", "]").replace("\\\"", "\"");
                }

                list = (List)JSON_MAPPER.readValue(json, List.class);
            } catch (Exception var3) {
                throw new RuntimeException("jsonToList method error: " + var3);
            }
        }

        return list;
    }

    public static Map<String, Object> jsonToMap(String json) {
        Map<String, Object> maps = null;
        if (!StringUtils.isEmpty(json)) {
            try {
                if (json.startsWith("\"{")) {
                    json = json.replace("\"{", "{").replace("}\"", "}").replace("\\\"", "\"");
                }

                maps = (Map)JSON_MAPPER.readValue(json, Map.class);
            } catch (Exception var3) {
                throw new RuntimeException("jsonToMap method error: " + var3);
            }
        }

        return maps;
    }
}


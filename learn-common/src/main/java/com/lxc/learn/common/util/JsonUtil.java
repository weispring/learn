package com.lxc.learn.common.util;

/**
 * @author lixianchun
 * @Description
 * @date 2019/6/26 15:13
 */

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.lxc.learn.common.util.core.BaseDto;
import com.lxc.learn.common.util.xml.CustomSerializer;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
@Slf4j
public class JsonUtil {
    private static ObjectMapper JSON_MAPPER = new ObjectMapper();

    static {
        /** 反序列化时 忽略不知道的字段
         *  第二种解决方案
         *  在需要转化的对象的类中添加注解，注解信息如下：
         @JsonIgnoreProperties(ignoreUnknown = true)
         */
        JSON_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, false)
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);


        /**
         * 入口com.fasterxml.jackson.databind.ser.DefaultSerializerProvider#serializeValue(com.fasterxml.jackson.core.JsonGenerator, java.lang.Object)
         * 在寻找类的对应Serializer对象时，可以在其查找流程种进行添加我们需要的序列化器
         * 核心方法com.fasterxml.jackson.databind.ser.BasicSerializerFactory#findSerializerByPrimaryType(com.fasterxml.jackson.databind.SerializerProvider, com.fasterxml.jackson.databind.JavaType, com.fasterxml.jackson.databind.BeanDescription, boolean)

         默认bean的序列化器 BeanSerializer
         默认属性序列化器 com.fasterxml.jackson.databind.ser.BeanPropertyWriter#serializeAsField(java.lang.Object, com.fasterxml.jackson.core.JsonGenerator, com.fasterxml.jackson.databind.SerializerProvider)

         */
        com.fasterxml.jackson.databind.ser.SerializerFactory serializerFactory = JSON_MAPPER.getSerializerFactory();
        serializerFactory = serializerFactory.withAdditionalSerializers( new Serializers.Base(){
            @Override
            public JsonSerializer<?> findSerializer(SerializationConfig config, JavaType type, BeanDescription beanDesc) {
                if (type.getRawClass().getName().equals(String.class.getName())){
                    return new JsonSerializer<Object>() {
                        @Override
                        public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
                            if (o == null){
                                jsonGenerator.writeString("888");
                            }else {
                                jsonGenerator.writeString(o.toString());
                            }

                        }
                    };
                }
                return null;
            }
        });
        serializerFactory = serializerFactory.withAdditionalSerializers(new CustomSerializer());
        JSON_MAPPER.setSerializerFactory(serializerFactory);


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
                //JSON_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);

                //@JsonSerialize 指定属性对应的序列化器
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


    public static void main(String[] args) {
        System.out.println(objectToJson(new Us()));
    }


    //@JsonSerialize
    @Data
    public static class Us extends BaseDto{
        private String li;

        private String name = "ddd";

        private List list = Arrays.asList("333333");

        private Long id ;

        private Long userId = 1L;

        //private Long a;
    }
}


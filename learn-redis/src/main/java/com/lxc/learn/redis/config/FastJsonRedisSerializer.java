package com.lxc.learn.redis.config;
import com.alibaba.fastjson.JSONObject;
import java.nio.charset.Charset;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

public class FastJsonRedisSerializer<T> implements RedisSerializer<T> {
    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
    private Class<T> clazz;

    public FastJsonRedisSerializer(Class<T> clazz) {
        this.clazz = clazz;
    }

    public byte[] serialize(T t) throws SerializationException {
        return t == null ? new byte[0] : ConverterUtils.converterJson(t).getBytes(DEFAULT_CHARSET);
    }

    public T deserialize(byte[] bytes) throws SerializationException {
        if (bytes != null && bytes.length > 0) {
            String str = new String(bytes, DEFAULT_CHARSET);
            return JSONObject.parseObject(str, this.clazz);
        } else {
            return null;
        }
    }
}


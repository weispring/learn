package com.lxc.learn.common.util.xml;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.ser.Serializers.Base;
import com.lxc.learn.common.util.core.BaseDto;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;

/**
 * 自定义序列化器
 * @author lixianchun
 * @description
 * @date 2020/7/17
 */
@Slf4j
public class CustomSerializer extends Base {

    @Override
    public JsonSerializer<?> findSerializer(SerializationConfig config, JavaType type, BeanDescription beanDesc) {
        if (BaseDto.class.isAssignableFrom(type.getRawClass())){
            return new CustomJsonSerializer();
        }
        return null;
    }

    public static class CustomJsonSerializer extends JsonSerializer{
        @Override
        public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            Assert.isTrue(o != null, "序列化对象不允许为空");
            if (o.getClass().isPrimitive()){
                JsonSerializer serializer = serializerProvider.findTypedValueSerializer(o.getClass(),true, null);
                serializer.serialize(o,jsonGenerator,serializerProvider);
            }else {
                serializeField(o,jsonGenerator,serializerProvider);
            }
        }

        private void serializeField(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            if (o instanceof Collection){
                JsonSerializer serializer = serializerProvider.findTypedValueSerializer(o.getClass(),true, (BeanProperty)null);;
                serializer.serialize(o,jsonGenerator,serializerProvider);
                return;
            }
            jsonGenerator.writeStartObject();
            Object value;
            Field[] fields = o.getClass().getDeclaredFields();
            for (Field field : fields){
                log.info("=================== : {}, {}", field.getName(), field.getType().getName());
                try {
                    field.setAccessible(true);
                    value = field.get(o);
                    if (value instanceof LoggerFactory
                            || Arrays.asList("log").contains(field.getName())){
                        continue;
                    }
                    jsonGenerator.writeFieldName(field.getName());
                    if (field.getType().getName().equals(String.class.getName())
                            || value instanceof String){
                        if (value != null){
                            jsonGenerator.writeString(value.toString());
                        }else {
                            jsonGenerator.writeString("");
                        }
                    }else if (/*this.basicType(field.getType()) &&*/ value == null){
                        serializerProvider.getDefaultNullValueSerializer().serialize(null,jsonGenerator,serializerProvider);
                    }else {
                        //field type 替换为 value的 class 类型更准确
                        JsonSerializer serializer = serializerProvider.findTypedValueSerializer(value.getClass(),true, (BeanProperty)null);;
                        //JsonSerializer serializer = serializerProvider.findTypedValueSerializer(field.getType(), true, (BeanProperty)null);;
                        serializer.serialize(value,jsonGenerator,serializerProvider);
                    }
                }catch (Exception e){
                    log.error(e.getMessage(),e);
                }
            }
            jsonGenerator.writeEndObject();
        }

        boolean basicType(Class cla){
            if (Number.class.isAssignableFrom(cla)
                    || Character.class.isAssignableFrom(cla)){
                return true;
            }
            return false;
        }
    }


    public static void main(String[] args) {
        String a = "{\"result\":{\"returnCode\":\"0\",\"offerList\":[{\"discountOfferName\":\"DEV New sub -$800 Discount Without Contract $98/>\",\"offerName\":\"Apple iPhone11Pro 64GB HS-Grey\",\"dataStatus\":\"1\",\"discountOfferCode\":\"NEW$800$98_0M\",\"discountOfferAmount\":\"-80000\",\"offerType\":\"1\",\"sysdate\":\"2020-07-17 12:20:29\",\"offerCode\":\"HANIP11PRO64GAPPG\",\"offerAmount\":\"859900\",\"offerId\":\"1000427\",\"discountType\":\"3\",\"discountOfferId\":\"870800\",\"skuCode\":\"HANIP11PRO64GAPPG\"},{\"discountOfferName\":\"DEV New sub -$800 Discount 6mth Contract $98/>\",\"offerName\":\"Apple iPhone11Pro 64GB HS-Grey\",\"dataStatus\":\"1\",\"discountOfferCode\":\"NEW$800$98_6M\",\"discountOfferAmount\":\"-80000\",\"offerType\":\"1\",\"sysdate\":\"2020-07-17 12:20:29\",\"offerCode\":\"HANIP11PRO64GAPPG\",\"offerAmount\":\"859900\",\"offerId\":\"1000427\",\"discountType\":\"2\",\"discountOfferId\":\"850800\",\"skuCode\":\"HANIP11PRO64GAPPG\"},{\"discountOfferName\":\"DEV Existing sub -$800 Discount Without Contract $98/>\",\"offerName\":\"Apple iPhone11Pro 64GB HS-Grey\",\"dataStatus\":\"1\",\"discountOfferCode\":\"EXIS$800$98_0M\",\"discountOfferAmount\":\"-80000\",\"offerType\":\"1\",\"sysdate\":\"2020-07-17 12:20:29\",\"offerCode\":\"HANIP11PRO64GAPPG\",\"offerAmount\":\"859900\",\"offerId\":\"1000427\",\"discountType\":\"3\",\"discountOfferId\":\"880800\",\"skuCode\":\"HANIP11PRO64GAPPG\"},{\"discountOfferName\":\"DEV All sub –$550 Discount Without Contract $98/>\",\"offerName\":\"Apple iPhone11Pro 64GB HS-Grey\",\"dataStatus\":\"1\",\"discountOfferCode\":\"ALL$550$98_0M\",\"discountOfferAmount\":\"-55000\",\"offerType\":\"1\",\"sysdate\":\"2020-07-17 12:20:29\",\"offerCode\":\"HANIP11PRO64GAPPG\",\"offerAmount\":\"859900\",\"offerId\":\"1000427\",\"discountType\":\"3\",\"discountOfferId\":\"820550\",\"skuCode\":\"HANIP11PRO64GAPPG\"},{\"discountOfferName\":\"StandaLone-$450 Discount\",\"offerName\":\"Apple iPhone11Pro 64GB HS-Grey\",\"dataStatus\":\"1\",\"discountOfferCode\":\"StandaLone-$450 Discount\",\"discountOfferAmou\n" +
                "nt\":\"-45000\",\"offerType\":\"1\",\"sysdate\":\"2020-07-17 12:20:29\",\"offerCode\":\"HANIP11PRO64GAPPG\",\"offerAmount\":\"859900\",\"offerId\":\"1000427\",\"discountType\":\"1\",\"discountOfferId\":\"800450\",\"skuCode\":\"HANIP11PRO64GAPPG\"},{\"discountOfferName\":\"DEV Existing sub -$800 Discount 6mth Contract $98/>\",\"offerName\":\"Apple iPhone11Pro 64GB HS-Grey\",\"dataStatus\":\"1\",\"discountOfferCode\":\"EXIS$800$98_6M\",\"discountOfferAmount\":\"-80000\",\"offerType\":\"1\",\"sysdate\":\"2020-07-17 12:20:29\",\"offerCode\":\"HANIP11PRO64GAPPG\",\"offerAmount\":\"859900\",\"offerId\":\"1000427\",\"discountType\":\"2\",\"discountOfferId\":\"860800\",\"skuCode\":\"HANIP11PRO64GAPPG\"},{\"discountOfferName\":\"DEV All sub –$550 Discount 6mth Contract $98/>\",\"offerName\":\"Apple iPhone11Pro 64GB HS-Grey\",\"dataStatus\":\"1\",\"discountOfferCode\":\"ALL$550$98_6M\",\"discountOfferAmount\":\"-55000\",\"offerType\":\"1\",\"sysdate\":\"2020-07-17 12:20:29\",\"offerCode\":\"HANIP11PRO64GAPPG\",\"offerAmount\":\"859900\",\"offerId\":\"1000427\",\"discountType\":\"2\",\"discountOfferId\":\"810550\",\"skuCode\":\"HANIP11PRO64GAPPG\"},{\"discountOfferName\":\"DEV All sub –$550 Discount Without Contract $98/>\",\"offerName\":\"Apple iPhone11Pro 64GB HS-Grey\",\"dataStatus\":\"1\",\"discountOfferCode\":\"ALL$550$98_0M\",\"discountOfferAmount\":\"-55000\",\"offerType\":\"1\",\"sysdate\":\"2020-07-17 12:20:29\",\"offerCode\":\"HANIP11PRO64GAPPG\",\"offerAmount\":\"859900\",\"offerId\":\"1000427\",\"discountType\":\"1\",\"discountOfferId\":\"820550\",\"skuCode\":\"HANIP11PRO64GAPPG\"},{\"discountOfferName\":\"DEV All sub -$700 Discount Without Contract $148/>\",\"offerName\":\"Apple iPhone11Pro 64GB HS-Grey\",\"dataStatus\":\"1\",\"discountOfferCode\":\"ALL700$148_0M\",\"discountOfferAmount\":\"-70000\",\"offerType\":\"1\",\"sysdate\":\"2020-07-17 12:20:29\",\"offerCode\":\"HANIP11PRO64GAPPG\",\"offerAmount\":\"859900\",\"offerId\":\"1000427\",\"discountType\":\"1\",\"discountOfferId\":\"890314\",\"skuCode\":\"HANIP11PRO64GAPPG\"}],\"returnDesc\":\"success\"},\"respDesc\":\"调用成功!\",\"respCode\":\"00000\"}";

        System.out.println(JSON.parseObject(a));

        System.out.println(Long.MAX_VALUE);
    }


}

package com.lxc.learn.jdk.reflect;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author lixianchun
 * @Description
 * @date 2019/11/22 15:20
 */
@Slf4j
public class MyService {


    public static void main(String[] args) {

    }


    @Test
    public void test0001(){
        MyBean myBean = new MyBean();

        //获取父类属性
        try{
            getFieldValue(myBean, true, "BEAN_NAME_P");
        }catch (Exception e){
            log.info(e.getMessage(), e);
        }

        //获取自身属性
        try{
            getFieldValue(myBean, false, "BEAN_NAME");
        }catch (Exception e){
            log.info(e.getMessage(), e);
        }
        //获取自身静态属性
        try{
           getFieldValue(myBean, false, "BEAN_NAME_STATIC");
        }catch (Exception e){
            log.info(e.getMessage(), e);
        }

        //获取接口属性
        try{
            Class cla = myBean.getClass().getInterfaces()[0];
            Field field = cla.getDeclaredField("BEAN_NAME_I");
            field.setAccessible(true);
            Object value = field.get(myBean);
            System.err.println(value);
        }catch (Exception e){
            log.info(e.getMessage(), e);
        }

    }


    public static void getFieldValue(Object object, boolean parent, String fieldName){
        Class cla = object.getClass();
        if (parent){
            cla = object.getClass().getSuperclass();
        }
        try {
            Field field = cla.getDeclaredField(fieldName);
            field.setAccessible(true);
            Object value = field.get(object);

            //TODO ERROR 下面方法，对于静态属性和接口属性取不到值
  /*      long offset = UnsafeUtils.getUnsafe().objectFieldOffset(field);
        value = UnsafeUtils.getUnsafe().getObject(object, offset);*/
            log.error("获取到值：{}",value);
        }catch (Exception e){
            log.error(fieldName,e.getMessage(), e);
        }

    }



    /***
     * 通过反射执行类上的方法
     */
    @Test
    public void test101(){
        IMyBean myBean = new MyBean();
        Method[] methods = myBean.getClass().getDeclaredMethods();
        for (Method m : methods){
            if (m.getName().equals("reflect")){
                try {
                    //setAccessible(true)并非将方法的訪问权限改成了public。
                    // 而是取消java的权限控制检查。所以即使是public方法。其accessible属相默认也是false
                    m.setAccessible(true);
                    m.invoke(myBean,null);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}

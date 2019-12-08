package com.lxc.learn.common.web;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Auther: lixianchun
 * @Date: 2018/9/22 16:27
 * @Description:
 */
@Slf4j
public class ParamUtil {

    public static void valid(Object obj){
        Class clazz = obj.getClass();
        try {
            Field[] fields = clazz.getDeclaredFields();
            for (Field f:fields) {
                try{
                  Annotation[] sss =  f.getDeclaredAnnotations();
                    f.setAccessible(true);
                    if (f.getAnnotation(NotEmpty.class) != null){
                        if (f.get(obj) == null){
                            log.debug("list " +obj.getClass()+f.getName() + "不允许为空");
                            //throw new BusinessException(ReturnCodeOrderEnum.PARAM_ILEGAL.getCode(),ReturnCodeOrderEnum.PARAM_ILEGAL.getValue());
                        }
                        if (f.get(obj) instanceof List){
                            List<Object> list = (List) f.get(obj);
                            if (((List) f.get(obj)).size() == 0){
                                log.debug("list " +obj.getClass()+f.getName() + "不允许为空");
                                //throw new BusinessException(ReturnCodeOrderEnum.PARAM_ILEGAL.getCode(),ReturnCodeOrderEnum.PARAM_ILEGAL.getValue());
                            }
                            list.forEach(e->valid(e));
                        }
                    }
                    if (f.getAnnotation(Valid.class) != null){
                        valid(f.get(obj));
                    }
                    NotNull a = f.getAnnotation(NotNull.class);
                    if (a != null && f.get(obj) == null){
                        log.debug("field " + obj.getClass()+f.getName() + "不允许为空");
                        //throw new BusinessException(ReturnCodeOrderEnum.PARAM_ILEGAL.getCode(),ReturnCodeOrderEnum.PARAM_ILEGAL.getValue());
                    }
                }catch (IllegalAccessException e){
                }

            }
        } catch (SecurityException  var5) {
            ;
        }
    }

    public static class Par{

        @NotNull
        private String a;

        @NotNull
        private String b = "b";

        @Valid
        private Sub sub = new Sub();

        //TODO 包不对
        @NotEmpty
        //private List<@javax.validation.Valid Sub> subs;

        @NotEmpty
        private List<Sub> subList = new ArrayList<>();
    }


    @Setter
    @Getter
    public static class Sub{

        @NotNull
        private String a = "a";

        @NotNull
        private String b;

    }

    public static void main(String[] args) throws Exception {
       /* Par par = new Par();
        valid(par);*/

        /*new Thread(new Runnable(){
            @Override
            public void run() {
                try{
                    Thread.currentThread().sleep(1000 * 3);
                }catch (exception e){

                }
                for (int i=0;i<1000;i++){

                }
                log.info("结束1");
            }
        }).start();
        new Thread(new Runnable(){
            @Override
            public void run() {
                try{
                    Thread.currentThread().sleep(1000 * 3);
                }catch (exception e){

                }
                for (int i=0;i<1000;i++){

                }
                log.info("结束2");
            }
        }).start();*/

        Map<Thread, StackTraceElement[]> maps =Thread.getAllStackTraces();
        Set<Thread> threads = maps.keySet();
        for (Thread m : threads){
            m.getName();

        }
        log.info(maps.toString());

        System.gc();



        //DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("please_rename_unique_group_name");
        //consumer.start();
    }

}

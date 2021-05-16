package com.lxc.learn.jdk.t;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 1.泛型集合类型中的运用，可解决实际类型满足需求，但声明类型不符合的方法参数
 *
 * @Description
 * @Author lixianchun
 * @Date 2021/4/26
 **/
public class MapParamT {

    private void test(Map<String, Object> map){

    }

    private void test1(){
        Map<String, List> map = new HashMap<>();
        //test(map);//ideal 编译错误
        modify(map);

        Map<Object, List> map1 = new HashMap<>();
        modify(map1);
    }

    private <T, R> void modify(Map<T, R> map){

    }

}

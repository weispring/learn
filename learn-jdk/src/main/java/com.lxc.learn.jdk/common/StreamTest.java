package com.lxc.learn.jdk.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author lixianchun
 * @description
 * @date 2020/6/6
 */
@Slf4j
public class StreamTest {

    @Test
    public void test(){
        List<Pojo> list = new ArrayList<>();
        List<Long> idList = list.stream().map(e->e.getId()).collect(Collectors.toList());
        List flist = list.stream().filter(e->idList.contains(e.getId())).collect(Collectors.toList());
        System.out.println(flist);
        System.out.println(idList);
    }


    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();

        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    @Test
    public void test2(){
        List<Pojo> list = new ArrayList<>();
        list.add(new Pojo(1L,""));
        list.add(new Pojo(1L,""));
        list.add(new Pojo(2L,""));
        list.add(new Pojo(1L,""));


        //根据某个实体属性去重
        List<Pojo> list2 = list.stream().filter(distinctByKey((p) -> (p.getId()))).collect(Collectors.toList());

        System.out.println(list2);
    }







    @AllArgsConstructor
    @Data
    public static class Pojo{

        private Long id;

        private String name;
    }
}

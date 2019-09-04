package com.lxc.learn.jdk.common;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * @author lixianchun
 * @Description
 * @date 2019/8/23 16:10
 */
@Slf4j
public class MapTest {

    public static void main(String[] args) {
        //初始容量 为2的n次幂，n为initialCapacity的二进制高位第一位为1至最低位的位数
        Map map = new HashMap(16);
        Map map1 = new HashMap(15);
        map1.put("1", "1");
        Map map2 = new HashMap(17);
        map1.put("1", "1");
        map.put("key001", "value");
        map.put("key001", "value1");
        map.put("key002", "value2");
        map.put("key003", "value3");

        int c = (16 -1)  & 17;

        int a = (16 -1)  & 17;

        log.info("{}", 1);

    }

    private static InheritableThreadLocal<String> threadLocal = new InheritableThreadLocal<>();


    public void testMap(){
        Object object = null;

        threadLocal.set("test");
        new Thread(new Runnable() {
            @Override
            public void run() {
                log.info("线程变量:{}",threadLocal.get());
            }
        }).start();
        threadLocal.remove();

        Map map = new HashMap();


        map.put("key1","value001");

        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("1","");

        Hashtable hashtable = new Hashtable();

        EnumMap enumMap = new EnumMap(String.class);


        //左旋、右旋、平衡树
        TreeMap treeMap = new TreeMap();
        treeMap.put("","");
        treeMap.get("");
        //key为弱引用，key回收后，value在WeakHashMap被访问时清除
        WeakHashMap weakHashMap = new WeakHashMap();

        //1.数组存储 2. key==key 3. equals key==key and value == value
        IdentityHashMap identityHashMap = new IdentityHashMap();

        identityHashMap.put("","");

        HashSet<String> set = new HashSet<>();
        set.add("");

        ArrayList<String> list = new ArrayList<>();
        list.add("");



    }

    @Data
    public static class User{
        private Long id;
        private String code;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            User user = (User) o;
            return Objects.equals(id, user.id) &&
                    Objects.equals(code, user.code);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, code);
        }
    }

    public static void setBitLight(int cap){
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        log.info("{} >>>{}", cap,n);
        log.info("{} >>>{}", Integer.toBinaryString(cap),Integer.toBinaryString(n));
    }
}

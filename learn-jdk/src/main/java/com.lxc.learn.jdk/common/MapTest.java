package com.lxc.learn.jdk.common;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lixianchun
 * @Description
 * @date 2019/8/23 16:10
 */
@Slf4j
public class MapTest {


    /**
     *
     *
     */
    public static void main(String[] args) {
        //初始容量 为2的n次幂，n为initialCapacity的二进制高位第一位为1至最低位的位数
        Map<String,String> map = new HashMap(16);
        Map map1 = new HashMap(15);
        map1.put("1", "1");
        Map map2 = new HashMap(17);
        map1.put("1", "1");
        map.put("key001", "value");
        map.put("key001", "value1");
        map.put("key002", "value2");
        map.put("key003", "value3");
        map.put(null,"00000000");

        Iterator iterator = map.entrySet().iterator();
        for (Map.Entry<String,String> entry : map.entrySet()){
            log.info(entry.getValue());
        }

        int c = (16 -1)  & 17;

        int a = (16 -1)  & 17;

        log.info("{}", 1);

    }


    @Test
    public void testMap() throws InterruptedException {
        Map map = new HashMap(1);
        map.put("key1","value001");

        //实现lru, 1.设置 accessOrder true
        // 2.重写方法 removeEldestEntry
        // 重写的afterNodeAccess 保证双向链表顺序
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("1","");
        linkedHashMap.get("1");

        Hashtable hashtable = new Hashtable();

        EnumMap enumMap = new EnumMap(String.class);
        enumMap.put(null,null);


        //左旋、右旋、平衡树
        TreeMap treeMap = new TreeMap();
        treeMap.put("","");
        treeMap.get("");
        treeMap.keySet().iterator();
        //key为弱引用，key回收后，value在WeakHashMap被访问时清除
        WeakHashMap weakHashMap = new WeakHashMap();

        //1.数组存储 2. key==key 3. equals key==key and value == value
        IdentityHashMap identityHashMap = new IdentityHashMap();

        identityHashMap.put("","");

        HashSet<String> set = new HashSet<>();
        set.add("");


        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
        concurrentHashMap.put("","");


        LinkedHashSet linkedHashSet = new LinkedHashSet();
        linkedHashSet.add("");

        Hashtable HashTable = new Hashtable();
        hashtable.put("","");
        hashtable.get("");



    }


    /**
     * 保证容量，避免resize
     */
    @Test
    public void ensureCapacityTest(){
        Map<String,Object> map = new HashMap<>();
        final int N = 10000000;
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < N; i++) {
            map.put(String.valueOf(i),"");
        }
        long endTime = System.currentTimeMillis();
        System.out.println("制定容量前："+(endTime - startTime));

        map = new HashMap<>(N*4/3);
        long startTime1 = System.currentTimeMillis();
        for (int i = 0; i < N; i++) {
            map.put(String.valueOf(i),"");
        }
        long endTime1 = System.currentTimeMillis();
        System.out.println("制定容量后："+(endTime1 - startTime1));
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
        n = (n < 0) ? 1 : (n >= 2147483647) ? 2147483647 : n + 1;

        log.info("{} >>>{}", cap,n);
        log.info("{} >>>{}", Integer.toBinaryString(cap),Integer.toBinaryString(n));
    }


    @Test
    public void test(){
        setBitLight(3);
        setBitLight(15);
        setBitLight(17);
    }

    @Test
    public void testThree(){
        int a = 1;
        Integer b = 2;
        Integer c = null;

        int result = true ? a*b : c;
    }

}

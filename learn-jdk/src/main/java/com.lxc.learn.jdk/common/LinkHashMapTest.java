package com.lxc.learn.jdk.common;

import lombok.extern.slf4j.Slf4j;

import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author lixianchun
 * @description
 * @date 2020/4/26
 */
@Slf4j
public class LinkHashMapTest {

/*    LRU 缓存
    以下是使用 LinkedHashMap 实现的一个 LRU 缓存：

    设定最大缓存空间 MAX_ENTRIES 为 3；
    使用 LinkedHashMap 的构造函数将 accessOrder 设置为 true，开启 LRU 顺序；
    覆盖 removeEldestEntry() 方法实现，在节点多于 MAX_ENTRIES 就会将最近最久未使用的数据移除。*/
    public static class LRUCache<K, V> extends LinkedHashMap<K, V> {
        private static final int MAX_ENTRIES = 3;

        //put 时移除最少使用元素
        protected boolean removeEldestEntry(Map.Entry eldest) {
            return size() > MAX_ENTRIES;
        }

        LRUCache() {
            //accessOrder 为true 实现get 的元素移动到最后
            super(MAX_ENTRIES, 0.75f, true);
        }
    }
    public static void main(String[] args) {
        LRUCache<Integer, String> cache = new LRUCache<>();
        cache.put(1, "a");
        cache.put(2, "b");
        cache.put(3, "c");
        cache.get(1);
        cache.put(4, "d");
        System.out.println(cache.keySet());

        Set set = new HashSet();
    }

}

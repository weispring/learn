package com.lxc.learn.junit.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.Hashtable;

/**
 * 利用软引用实现的内存缓存工具
 * @Auther: lixianchun
 * @Date: 2018/10/19 14:15
 * @Description:
 */
public class CacheMangerUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(CacheMangerUtil.class);
    public static final String SPLIT_CHAR = "-";
    private static Hashtable<String, CacheRef> refsMap = new Hashtable();
    private static ReferenceQueue<Object> refsQueue = new ReferenceQueue();
    public static final long SLEEP_TIME = 10*60*1000L;

    public static final int EXPIRED = 5*60;

    private CacheMangerUtil() {
    }

    public static Object get(String key) {
        CacheRef ref = refsMap.get(key);
        if (ref == null) {
            return null;
        } else if (ref.getTimeout() > System.currentTimeMillis()) {
            return ref.get();
        } else {
            //ref.clear();
            refsMap.remove(key);
            return null;
        }
    }


    public static void put(String key, Object target) {
        CacheRef ref = new CacheRef(key, target, refsQueue, EXPIRED);
        refsMap.put(key, ref);
    }

    public static void put(String key, Object target,int expired) {
        CacheRef ref = new CacheRef(key, target, refsQueue, expired);
        refsMap.put(key, ref);
    }

    static {
        new Thread(() -> {
            LOGGER.info("ref remove start");

            while(true) {
                try {
                    Thread.sleep(SLEEP_TIME);
                } catch (InterruptedException var3) {
                    LOGGER.info("", var3);
                }
                CacheRef ref = null;
                while((ref = ((CacheRef)refsQueue.poll())) != null) {
                        refsMap.remove(ref.getKey());
                }
            }
        }).start();
    }


    private static class CacheRef extends SoftReference<Object> {
        private long timeout;
        private String key;

        protected CacheRef(String key, Object target, ReferenceQueue<Object> q, int indate) {
            super(target, q);
            this.timeout = System.currentTimeMillis() + (long)(indate * 1000);
            this.key = key;
        }

        protected long getTimeout() {
            return this.timeout;
        }

        public String getKey() {
            return this.key;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("CacheRef{");
            sb.append("timeout=").append(timeout);
            sb.append(", key='").append(key).append('\'');
            sb.append('}');
            return sb.toString();
        }


    }


   /* public static void main(String[] args) {
        CacheMangerUtil.put("test1",new Infoaa().setName("123"),1 );
        CacheMangerUtil.put("test2",new Infoaa().setName("456"),10 );
        CacheMangerUtil.put("test3",new Infoaa().setName("789"),1 );
        try{
            Thread.sleep(1000*3L);
        }catch (Exception e){

        }
        LOGGER.info("获取1：{}",get("test1"));
        LOGGER.info("获取2：{}",get("test2"));

        LOGGER.info("获取3：{}",get("test3"));

       *//* try{
            Thread.sleep(1000*30L);
        }catch (Exception e){

        }*//*
    }

    @Getter
    @Setter
    @Accessors(chain = true)
    private static class Infoaa{
        private String name;
    }
*/
}

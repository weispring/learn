package com.lxc.learn.jdk.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Auther: lixianchun
 * @Date: 2019/9/15 11:14
 * @Description:
 */
@Slf4j
public class ReentrantReadWriteLockTest {

    /**
     * 若读锁达到最大时，抛出Error
     * if (sharedCount(c) == MAX_COUNT)
     throw new Error("Maximum lock count exceeded");
     *
     * 高16位读锁，低16位写锁，获取读锁后不能获取写锁。同一个线程获取写锁后可获取读锁
     */
    public static void main(String[] args) {
        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();


        reentrantReadWriteLock.writeLock().lock();
        reentrantReadWriteLock.writeLock().unlock();

        reentrantReadWriteLock.readLock().lock();
        reentrantReadWriteLock.readLock().unlock();
    }

    class RWDictionary {
        private final Map<String, Object> m = new TreeMap<String, Object>();
        private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
        private final Lock r = rwl.readLock();
        private final Lock w = rwl.writeLock();

        public Object get(String key) {
            r.lock();
            try { return m.get(key); }
            finally { r.unlock(); }
        }
        public Object[] allKeys() {
            r.lock();
            try { return m.keySet().toArray(); }
            finally { r.unlock(); }
        }
        public Object put(String key, Object value) {
            w.lock();
            try { return m.put(key, value); }
            finally { w.unlock(); }
        }
        public void clear() {
            w.lock();
            try { m.clear(); }
            finally { w.unlock(); }
        }
    }


}

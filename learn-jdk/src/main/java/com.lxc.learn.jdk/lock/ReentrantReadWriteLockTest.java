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

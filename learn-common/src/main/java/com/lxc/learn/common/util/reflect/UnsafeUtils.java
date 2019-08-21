package com.lxc.learn.common.util.reflect;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * @author lixianchun
 * @Description
 * @date 2019/8/19 21:00
 */
public final class UnsafeUtils {
    private UnsafeUtils() {
    }

    private static class UnsafeHolder {
        private static final Unsafe unsafe = AccessController.doPrivileged(new PrivilegedAction<Unsafe>() {
            public Unsafe run() {
                try {
                    Field unsafe = Unsafe.class.getDeclaredField("theUnsafe");
                    unsafe.setAccessible(true);
                    return (Unsafe) unsafe.get(null);
                } catch (NoSuchFieldException e) {
                    throw new IllegalStateException(e);
                } catch (IllegalAccessException e) {
                    throw new IllegalStateException(e);
                }
            }
        });
    }

    public static Unsafe getUnsafe() {
        return UnsafeHolder.unsafe;
    }
}


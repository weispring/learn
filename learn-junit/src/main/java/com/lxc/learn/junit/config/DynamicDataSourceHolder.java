//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.lxc.learn.junit.config;

import com.lxc.learn.junit.config.DynamicDataSourceOptions;

public final class DynamicDataSourceHolder {
    private static final ThreadLocal<DynamicDataSourceOptions> holder = new ThreadLocal();

    private DynamicDataSourceHolder() {
    }

    public static void putDataSource(DynamicDataSourceOptions dataSource) {
        holder.set(dataSource);
    }

    public static DynamicDataSourceOptions getDataSource() {
        return (DynamicDataSourceOptions)holder.get();
    }

    public static void clearDataSource() {
        holder.remove();
    }
}

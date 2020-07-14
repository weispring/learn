package com.lxc.learn.jdk.spi;

import com.sun.jna.platform.win32.WinDef;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lixianchun
 * @description
 * @date 2020/7/8
 */
@Slf4j
public class ServiceProviderImpl implements IServiceProvider {
    @Override
    public String getType() {
        return "1";
    }

    @Override
    public void invoke() {
        log.info("this name : {} ",this.getClass().getName());
    }
}

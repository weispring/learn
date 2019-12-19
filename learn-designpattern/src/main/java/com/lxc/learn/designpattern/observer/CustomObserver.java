package com.lxc.learn.designpattern.observer;

import lombok.extern.slf4j.Slf4j;

import java.util.Observable;
import java.util.Observer;
/**
 * @author lixianchun
 * @description
 * @date 2019/12/19
 */

@Slf4j
public class CustomObserver implements Observer {
    @Override
    public void update(Observable o, Object arg) {
        log.info("被观察者:{},变动:{}",o,arg);
    }
}

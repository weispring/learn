package com.lxc.learn.designpattern.observer;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * @author lixianchun
 * @description
 * @date 2019/12/19
 */

@Getter
@Setter
public class Subject extends Observable {

    /**
     * 注册一个观察者
     */
    @Override
    public synchronized void addObserver(Observer o) {
        super.addObserver(o);
    }

    /**
     * 移除一个观察者
     */
    @Override
    public synchronized void deleteObserver(Observer o) {
        super.deleteObserver(o);
    }


    /**
     * 通知所有观察者
     */
    public void notify(Object arg) {
        super.setChanged();
        super.notifyObservers(arg);
    }

    public static void main(String[] args) {
        Subject subject = new Subject();
        subject.addObserver(new CustomObserver());
        subject.addObserver(new CustomObserver());

        subject.notify("哈哈！！");
    }

}

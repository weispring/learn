package com.lxc.learn.designpattern.command;

/**
 * @author lixianchun
 * @description
 * @date 2020/10/18
 */
public abstract class Command {
    Receiver receiver;

    public abstract void send();

    public Command(Receiver receiver){
        this.receiver = receiver;
    }

}

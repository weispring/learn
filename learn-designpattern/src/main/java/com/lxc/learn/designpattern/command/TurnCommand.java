package com.lxc.learn.designpattern.command;

import lombok.extern.slf4j.Slf4j;

/**
 * 开关灯命令
 * @author lixianchun
 * @description
 * @date 2020/10/18
 */
@Slf4j
public class TurnCommand extends Command {

    public TurnCommand(Receiver receiver) {
        super(receiver);
    }

    @Override
    public void send() {
        receiver.receice(null);
    }
}

package com.lxc.learn.designpattern.command;

import lombok.extern.slf4j.Slf4j;

/**
 * @author lixianchun
 * @description
 * @date 2020/10/18
 */
@Slf4j
public class CookingCommand extends Command {
    public CookingCommand(Receiver receiver) {
        super(receiver);
    }

    @Override
    public void send() {
        receiver.receice(null);
    }
}

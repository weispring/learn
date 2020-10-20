package com.lxc.learn.designpattern.command;

import lombok.extern.slf4j.Slf4j;

/**
 * @author lixianchun
 * @description
 * @date 2020/10/18
 */
@Slf4j
public class Main {

    public static void main(String[] args) {
        Receiver cookingReceiver = new CookingReceiver();
        Command cookingCommand = new CookingCommand(cookingReceiver);
        Invoker invoker = new Invoker(cookingCommand, null);
        invoker.cooking();
    }
}

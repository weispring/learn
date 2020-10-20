package com.lxc.learn.designpattern.command;

import lombok.extern.slf4j.Slf4j;

/**
 * @author lixianchun
 * @description
 * @date 2020/10/18
 */
@Slf4j
public class Invoker {

    /**
     * 命令模式的定义与特点
     命令（Command）模式的定义如下：将一个请求封装为一个对象，使发出请求的责任和执行请求的责任分割开。
     这样两者之间通过命令对象进行沟通，这样方便将命令对象进行储存、传递、调用、增加与管理。

     命令模式的主要优点如下。
     降低系统的耦合度。命令模式能将调用操作的对象与实现该操作的对象解耦。
     增加或删除命令非常方便。采用命令模式增加与删除命令不会影响其他类，它满足“开闭原则”，对扩展比较灵活。
     可以实现宏命令。命令模式可以与组合模式结合，将多个命令装配成一个组合命令，即宏命令。
     方便实现 Undo 和 Redo 操作。命令模式可以与后面介绍的备忘录模式结合，实现命令的撤销与恢复。

     其缺点是：可能产生大量具体命令类。因为计对每一个具体操作都需要设计一个具体命令类，这将增加系统的复杂性。
     命令模式的结构与实现
     可以将系统中的相关操作抽象成命令，使调用者与实现者相关分离，其结构如下。
     1. 模式的结构
     命令模式包含以下主要角色。
     抽象命令类（Command）角色：声明执行命令的接口，拥有执行命令的抽象方法 execute()。
     具体命令角色（Concrete    Command）角色：是抽象命令类的具体实现类，它拥有接收者对象，并通过调用接收者的功能来完成命令要执行的操作。
     实现者/接收者（Receiver）角色：执行命令功能的相关操作，是具体命令对象业务的真正实现者。
     调用者/请求者（Invoker）角色：是请求的发送者，它通常拥有很多的命令对象，并通过访问命令对象来执行相关请求，它不直接访问接收者。


     */
    private Command  cookingCommand, turnCommand;

    public Invoker(Command cookingCommand, Command turnCommand) {
        this.cookingCommand = cookingCommand;
        this.turnCommand = turnCommand;
    }

    public void cooking(){
        this.cookingCommand.send();
    }

    public void turn(){
        this.turnCommand.send();
    }
}

package com.lxc.learn.designpattern.builder;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lixianchun
 * @description
 * @date 2019/12/27
 */
@Slf4j
@Getter
@Setter
public class Product{
    private String partA;

    private String partB;

    private String partC;


    public void show(){
        //显示产品的特性
    }
}
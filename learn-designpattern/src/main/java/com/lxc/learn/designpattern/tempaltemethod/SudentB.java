package com.lxc.learn.designpattern.tempaltemethod;

import lombok.extern.slf4j.Slf4j;

/**
 * @author lixianchun
 * @description
 * @date 2019/12/19
 */
@Slf4j
public class SudentB extends ExaminationPaper {
    @Override
    public String answer1() {
        return this.getClass().getSimpleName() + ":" + "1";
    }

    @Override
    public String answer2() {
        return this.getClass().getSimpleName() + ":" + "2";
    }

    @Override
    public String answer3() {
        return this.getClass().getSimpleName() + ":" + "3";
    }
}

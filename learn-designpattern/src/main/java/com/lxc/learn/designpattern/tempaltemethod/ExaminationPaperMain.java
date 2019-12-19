package com.lxc.learn.designpattern.tempaltemethod;

import lombok.extern.slf4j.Slf4j;

/**
 * @author lixianchun
 * @description
 * @date 2019/12/19
 */
@Slf4j
public class ExaminationPaperMain {
    public static void main(String[] args) {
        new SudentA().examination();
        new SudentB().examination();
    }
}

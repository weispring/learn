package com.lxc.learn.arithmetic.massdataprocessing.tree;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Auther: lixianchun
 * @Date: 2020/4/12 14:59
 * @Description:
 */
public class CommonQueue {

    public static LinkedBlockingQueue QUEUE = new LinkedBlockingQueue(){};

    static {
        QUEUE.addAll(Arrays.asList("A","B","D","G","#","#","H","#","#","#",
                "C","E","#","I","#","#","F","#","#"));
    }
}

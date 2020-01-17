package com.lxc.learn.jvm;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lixianchun
 * @description
 * @date 2020/1/16
 */
@Slf4j
public class OomTest {

    /**
     * VM Args：-Xms20m-Xmx20m-XX：+HeapDumpOnOutOfMemoryError
     */

    public static void main(String[] args) {
        List<OOMObject> list = new ArrayList<OOMObject>();
        while(true){
            list.add(new OOMObject());
        }
    }

    public static class OOMObject {

    }
}

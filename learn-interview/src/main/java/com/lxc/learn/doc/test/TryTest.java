package com.lxc.learn.doc.test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TryTest {

    public static void main(String[] args) {
        System.out.println(test());
    }


    private static String test(){
        try{
            String s = null;
            //下面这一行是否注释，不影响结果
            System.out.println(s.toString());
            return "1";
        }catch (Exception e){
            return "2";
        }finally {
            return "3";
        }
    }


}

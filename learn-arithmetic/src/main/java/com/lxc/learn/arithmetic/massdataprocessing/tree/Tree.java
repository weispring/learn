package com.lxc.learn.arithmetic.massdataprocessing.tree;

import lombok.Getter;
import lombok.Setter;

import java.util.Scanner;

/**
 * @Auther: lixianchun
 * @Date: 2020/4/11 21:37
 * @Description:
 */
@Getter
@Setter
public class Tree {


    private Object data;

    private Tree left;

    private Tree right;

    public Tree(Object data){
        this.data = data;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()){
            if ("^D".equals(sc.next())){
                break;
            }
            System.out.println(sc.next());
        }
    }
}

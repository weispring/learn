package com.lxc.learn.sonar;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;

/**
 * @Description
 * @Author lixianchun
 * @Date 2021/6/8
 **/
public class StringTest {


    /**
     * This method passes a constant literal String of length 1 as a parameter to a method,
     * when a similar method is exposed that takes a char.
     * It is simpler and more expedient to handle one character, rather than a String.
     * Instead of making calls like:
     */
    public void test1(){
        String myString = "";
        if (myString.indexOf("e") != -1) {
            int i = myString.lastIndexOf("e");
            System.out.println(myString + ":" + i);  //the Java compiler will use a StringBuilder internally here [builder.append(":")]
           myString.replace("m","z");
        }

    }

    public void test2(){
        //Replace the single letter Strings with their char equivalents like so:
        String myString = "";
        if (myString.indexOf('e') != -1) {
            int i = myString.lastIndexOf('e');
            System.out.println(myString + ':' + i);  //the Java compiler will use a StringBuilder internally here [builder.append(':')]
            myString.replace('m','z');
        }
    }


    /**
     * This method defines an anonymous lambda function to be called to fetch a single value from the passed in value.
     * While this will work, it is needlessly complex as this function merely calls a single getter method on the object, and thus the code can be simplied by just passing in a method reference instead.
     * Instead of
     * baubles.stream().map(b -> b.getName()).collect(Collectors.toSet())
     * do baubles.stream().map(Bauble::getName).collect(Collectors.toSet())
     */
    @Test
    public void test3(){
        Integer[] a = new Integer[]{};
        Object[] objs = Arrays.stream(a).map(e -> e.intValue()).toArray();
        //objs = Arrays.stream(a).map(e -> e.intValue()).toArray();
        objs = Arrays.stream(a).map(Integer::intValue).toArray();

        new HashSet<>().retainAll(null);



    }
}

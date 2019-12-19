package com.lxc.learn.designpattern.tempaltemethod;

import lombok.extern.slf4j.Slf4j;

/**
 * @author lixianchun
 * @description
 * @date 2019/12/19
 */

@Slf4j
public abstract class ExaminationPaper {

    /**
     * 模板方法模式，定义一个操作中的算法骨架，而将一些步骤延迟到子类中。模板方法使得子类可以不改变一个算法的结构即可重新定义该算法的某些特定步骤。

     2.模板方法模式的结构
     AbstractClass：实现一个模板方法，定义了算法的骨架，具体子类将重定义abstract method以实现一个算法的步骤。
     AbstractClass其实就是一个抽象模板，定义并实现了一个模板方法。这个模板方法一般是一个具体的方法。
     它给出了一个顶级逻辑的骨架，而逻辑的组成步骤在相应的抽象操作中，推迟到子类实现。顶级逻辑也有可能调用一些具体方法。
     ConcreteClasses：实现 abstract method 以完成算法与特定子类相关的步骤。ConcreteClass实现父类所定义的一个或多个抽象方法。
     每一个AbstractClass都可以有任意多个ConcreteClass与之对应，而每一个ConcreteClass都可以给出这些抽象方法（也就是顶级逻辑的组成步骤）的不同实现，从而使得顶级逻辑的实现各不相同。


     实际例子：
     假设老师在黑板上出了一套题，让学生A和学生B将黑板上的题目抄下来做好了交上去给老师进行批改。但是学生很有可能因为一不小心将题目抄错了而将答案选错，这样就会让老师误以为这位同学没有掌握。并且，当老师改了黑板上的题目后，那么这两个同学也要跟着去修改，非常的麻烦。那么怎样解决这些问题呢？
     　　其实老师将题目出好制作一份试卷，然后将这个试卷打印多份发给学生，这样，学生拿到的题目就是一样的了，并且学生只需要填写答案，不需要抄题，也省去了工作量。这个就是模板方法模式的体现。其中试卷就相当于是一个模板，而多个学生做这个试卷就相当于多个ConcreteClass去实现试卷这个模板中的方法。

     */
    public void examination(){
        testQuestion1();
        testQuestion2();
        testQuestion3();
    }

    private void testQuestion1() {

        System.out.println("杨过得到，后来给了郭靖，" +
                "炼成倚天剑、屠龙刀的玄铁可能是[ ]\n" +
                "a.球墨铸铁 b.马口铁 c.高速合金钢 d.碳素纤维");
        System.out.println("答案: " + answer1());
    }

    private void testQuestion2() {

        System.out.println("杨过、程英、陆无双铲除了情花，造成[ ]\n" +
                "a.使这种植物不再伤人 b.使一种珍惜物种灭绝\n" +
                "c.破坏了那个生物圈的生态平衡 d.造成该地区荒漠化");
        System.out.println("答案: " + answer2());
    }

    private void testQuestion3() {

        System.out.println("蓝凤凰致使华山师徒、桃谷六仙呕吐不止，" +
                "如果你是大夫，会给他们开什么药[ ]\n" +
                "a.阿司匹林 b.牛黄解毒片 c.氟哌酸 d.大量生牛奶");
        System.out.println("答案: " + answer3());
    }

    public abstract String answer1();

    public abstract String answer2();

    public abstract String answer3();
}

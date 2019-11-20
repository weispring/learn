package com.lxc.learn.jdk.queuetest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * @author lixianchun
 * @Description
 * @date 2019/11/19 20:24
 */
@Slf4j
public class ArrayBlockingQueueTest {

    public static void main(String[] args) throws Exception{
        ArrayBlockingQueue arrayBlockingQueue = new ArrayBlockingQueue(5);

        for (int i=0;i<5;i++){
            arrayBlockingQueue.add(i);
        }
        arrayBlockingQueue.take();
        log.info("{}", "");
    }

    @Test
    public void test02(){
        LinkedBlockingQueue linkedBlockingQueue = new LinkedBlockingQueue();
        linkedBlockingQueue.offer("11");
        System.out.println("");
    }


    /**
     * 测试 PriorityBlockingQueue
     */

    public static class Person implements Comparable<Person> {
        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Person(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public Person() {
        }

        @Override
        public String toString() {
            return this.id + ":" + this.name;
        }

        @Override
        public int compareTo(Person person) {
            return this.id > person.getId() ? 1 : (this.id < person.getId() ? -1 : 0);
        }
    }

    private Random random =  new Random(10);


    /**
     * PriorityBlockingQueue 入队时 进行初步的排序
     * 出队时会从 前面[1][2]个元素中找出最小的一个赋值给 第一个元素[0]，供下次获取。
     * @throws Exception
     */
    @Test
    public void test03() throws Exception{
        PriorityBlockingQueue priorityBlockingQueue = new PriorityBlockingQueue();

        for (int i=0;i<10;i++){
            priorityBlockingQueue.add(new Person(random.nextInt(100),""));
        }

        for (int i=0;i<10;i++){
            log.info("priorityBlockingQueue.take:{}", priorityBlockingQueue.take());
        }
        System.out.println("");
    }


}

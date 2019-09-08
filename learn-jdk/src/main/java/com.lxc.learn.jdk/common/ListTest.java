package com.lxc.learn.jdk.common;

import lombok.Data;
import lombok.Synchronized;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @Auther: lixianchun
 * @Date: 2019/9/2 16:01
 * @Description:
 */
@Slf4j
public class ListTest {
    public static void main(String[] args) {

        int[] data = new int[] {5,8,6,7,2,9,1,0};
        Arrays.sort(data);
        log.info("数组是: "+ Arrays.toString(data));
        //二分法查找
        log.info("6对应的下标是：" + Arrays.binarySearch(data, 6));
        log.info("3对应的下标是：" + Arrays.binarySearch(data, 3));
        log.info("4对应的下标是：" + Arrays.binarySearch(data, 4));
        log.info("9对应的下标是：" + Arrays.binarySearch(data, 9));
        log.info("-1对应的下标是：" + Arrays.binarySearch(data, -1));
        log.info("11对应的下标是：" + Arrays.binarySearch(data, 11));

        //note RandomAccess 支持快速访问
        Collections.binarySearch(null,null);



    }

    @Test
    public void list(){
        List<String> list = new ArrayList<>();
        //note 链表、双向链表、双向循环链表
        List<String> linkList = new LinkedList<>();

        Object o = null;
        synchronized (o){

        }
    }


    @Test
    public void test(){
        List arrayList = new ArrayList();
        arrayList.add(1);
        arrayList.add(111);
        Collections.reverse(arrayList);
    }


    /**
     * ArrayList 源码中有一个 ensureCapacity 方法不知道大家注意到没有，这个方法 ArrayList 内部没有被调用过，
     * 所以很显然是提供给用户调用的，那么这个方法有什么作用呢？
     * 如有必要，增加此 ArrayList 实例的容量，以确保它至少可以容纳由minimum capacity参数指定的元素数。
     *      */
    @Test
    public void ensureCapacity(){
        ArrayList<Object> list = new ArrayList<Object>();
        final int N = 10000000;
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < N; i++) {
            list.add(i);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("使用ensureCapacity方法前："+(endTime - startTime));

        list = new ArrayList<Object>();
        long startTime1 = System.currentTimeMillis();
        list.ensureCapacity(N);
        for (int i = 0; i < N; i++) {
            list.add(i);
        }
        long endTime1 = System.currentTimeMillis();
        System.out.println("使用ensureCapacity方法后："+(endTime1 - startTime1));
    }




    @Test
    public void testArrayCopy(){
        List<String> list = new ArrayList<>();
        list.add("2");
        User user1 = new User().setId(1);
        User user2 = new User().setId(2);
        User[] users1 = new User[2];
        User[] users2 = new User[2];
        users1[0] = user1;
        users1[1] = user2;

        System.arraycopy(users1, 0, users2, 0, 2);

        users2[0].setId(333333);


//        Integer[] integer2 = new Integer[list.size()];
//        integer2 = (Integer[]) list.toArray();

        log.info("", "");
    }


    @Data
    @Accessors(chain = true)
    public static class User{

        private Integer id;
    }
}

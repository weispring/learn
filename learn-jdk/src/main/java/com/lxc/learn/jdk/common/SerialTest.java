package com.lxc.learn.jdk.common;

import com.sun.jna.platform.win32.Winspool;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author lixianchun
 * @description
 * @date 2020/4/16
 */
@Slf4j
@Data
@Accessors(chain = true)
public class SerialTest implements Serializable {

    //此格式严格遵循private static final long serialVersionUID ，否则无效;此值一致即可，字段增减不影响。

    private static final long serialVersionUID = 153335511L;

    private transient String name;

    private Long id;

    private List list;

    private String name2;


    public SerialTest(){
    }

    public SerialTest(String name,Long id){
        this.name = name;
        this.id = id;
    }



    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Object o = new SerialTest("张三",23L);
        ArrayList list = new ArrayList();
        list.add("909jjj");
        ((SerialTest) o).setList(list);

        ObjectOutputStream os=new ObjectOutputStream(new FileOutputStream("record.txt"));
        os.writeObject(o);
        System.out.println("集合对象写入到文件成功！");
        os.close();

        try {
            ObjectInputStream osi=new ObjectInputStream(new FileInputStream("record.txt"));
            SerialTest s = (SerialTest) osi.readObject();
            osi.close();
            System.out.println("从磁盘读取到的集合遍历结果");
            System.out.println(s);
        }catch (Exception e){
            e.printStackTrace();
        }
        Collections.synchronizedList(list);
    }

    /**
     * transient用来表示一个域不是该对象序行化的一部分，当一个对象被序行化的时候，transient修饰的变量的值是不包括在序行化的表示中的。但是ArrayList又是可序行化的类，elementData是ArrayList具体存放元素的成员，用transient来修饰elementData，岂不是反序列化后的ArrayList丢失了原先的元素？
     *        其实玄机在于ArrayList中的两个方法：
     * writeObject
     * readObject
     *   ArrayList在序列化的时候会调用writeObject，直接将size和element写入ObjectOutputStream；反序列化时调用readObject，从ObjectInputStream获取size和element，再恢复到elementData。
     *  为什么不直接用elementData来序列化，而采用上诉的方式来实现序列化呢？原因在于elementData是一个缓存数组，它通常会预留一些容量，等容量不足时再扩充容量，那么有些空间可能就没有实际存储元素，采用上诉的方式来实现序列化时，就可以保证只序列化实际存储的那些元素，而不是整个数组，从而节省空间和时间。

     *
     *
     *
     */


}

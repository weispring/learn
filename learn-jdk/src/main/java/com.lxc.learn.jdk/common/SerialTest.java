package com.lxc.learn.jdk.common;

import com.sun.jna.platform.win32.Winspool;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;

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

    private String name;

    private Long id;

    private String name2;


    public SerialTest(){
    }

    public SerialTest(String name,Long id){
        this.name = name;
        this.id = id;
    }



    public static void main(String[] args) throws IOException, ClassNotFoundException {
/*        Object o = new SerialTest("张三",23L);
        ObjectOutputStream os=new ObjectOutputStream(new FileOutputStream("record.txt"));
        os.writeObject(o);
        System.out.println("集合对象写入到文件成功！");
        os.close();*/
        try {
            ObjectInputStream osi=new ObjectInputStream(new FileInputStream("record.txt"));
            SerialTest s = (SerialTest) osi.readObject();
            osi.close();
            System.out.println("从磁盘读取到的集合遍历结果");
            System.out.println(s);
        }catch (Exception e){
            e.printStackTrace();
        }
    }



}

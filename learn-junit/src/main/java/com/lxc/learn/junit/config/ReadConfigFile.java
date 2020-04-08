package com.lxc.learn.junit.config;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.net.URL;

/**
 * @author lixianchun
 * @description
 * @date 2020/4/8
 */
@Slf4j
public class ReadConfigFile {

    /**
     小结：class.getResource 读取配置的路径时，会按相对路径读取,相对的是当前类的class 这个文件。
     而采用class的classLoader则是相对于根路径（也就是我们说的classpath）。

     形如："jar:file:/data/simpleJava/com.hyq.simple-1.0.jar !/p1.properties" 这样的路径，
     getResource是无法读取的，因为他不是一个文件路径。而getResourceAsStream会以流的方式，打开文件来读取数据，上图的文件树，就是我用unzip命令解压jar包后显示出来的。

     */
    public static void readConfig(String[] args) {
        if (args.length != 1) {
            log.info("usage: java -jar com.hyq.simple-1.0.jar args");
            return;
        }

        try{
            URL url = ReadConfigFile.class.getResource(args[0]);
            log.info("ReadConfigFile.class.getResource(args[0])：{}",url);
        }catch (Exception e){
            log.error("1 :{}",e.getMessage());
        }
        try{
            URL url = ReadConfigFile.class.getClassLoader().getResource(args[0]);
            log.info("ReadConfigFile.class.getClassLoader().getResource(args[0])：{}",url);
        }catch (Exception e){
            log.error("2 :{}",e.getMessage());
        }


        try{
            InputStream inputStream = ReadConfigFile.class.getResourceAsStream(args[0]);
            log.info("ReadConfigFile.class.getResourceAsStream(args[0])：{}",inputStream);
        }catch (Exception e){
            log.error("3 :{}",e.getMessage());
        }
        try{
            InputStream inputStream = ReadConfigFile.class.getClassLoader().getResourceAsStream(args[0]);
            log.info("ReadConfigFile.class.getClassLoader().getResourceAsStream(args[0])：{}", inputStream);
        }catch (Exception e){
            log.error("4 :{}",e.getMessage());
        }
    }

    public static void main(String[] args) {
        readConfig(new String[]{"application.yml"});
        readConfig(new String[]{"application-1.yml"});
        readConfig(new String[]{"config-common.yml"});
    }
}

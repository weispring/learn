package com.lxc.learn.jdk.clasload;

import com.lxc.learn.jdk.basedatatype.NumTest;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.lang.reflect.InvocationTargetException;

/**
 * @author lixianchun
 * @Description
 * @date 2019/10/8 12:22
 */
@Slf4j
public class FileSystemClassLoader extends ClassLoader{

    private String rootDir;


    /**
     *
     */
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Class classNum = new FileSystemClassLoader("D:\\lcode\\learn\\learn-jdk\\target\\classes").findClass("com.lxc.learn.jdk.basedatatype.NumTest");
        Object object = classNum.newInstance();
        System.out.println(System.clearProperty("java.class.path"));
        log.info("object 类加载器 {}", object.getClass().getClassLoader());
        log.info("object 类加载器 {}", NumTest.class.getClassLoader());
        if (object instanceof NumTest){
            log.info("类加载器一致{}", "");
        }else {
            log.info("类加载器不一致{}", "");
        }
        classNum.getMethod("testByte").invoke(object, null);
    }

    public FileSystemClassLoader(String rootDir){
        super();
        this.rootDir = rootDir;
    }

    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] classData = getClassData(name);
        if (classData == null){
            throw new ClassNotFoundException();
        }
        else {
            //name 类全名
            return defineClass(name, classData, 0, classData.length);
        }
    }

    private byte[] getClassData(String className) {
        String path = classNameToPath(className);
        try {
            InputStream ins = new FileInputStream(path);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int bufferSize = 4096;
            byte[] buffer = new byte[bufferSize];
            int bytesNumRead = 0;
            while ((bytesNumRead = ins.read(buffer)) != -1){
                baos.write(buffer, 0, bytesNumRead);
            }
            return baos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String classNameToPath(String className) {
        return rootDir + File.separatorChar + className.replace('.', File.separatorChar) + ".class";
    }

}

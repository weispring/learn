package com.lxc.learn.jdk.lock;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author lixianchun
 * @Description
 * @date 2019/9/10 15:54
 */
@Slf4j
public class UnsafeTest {

    public static void main(String[] args) {

    }

    /**
     * 获取Unsafe
     */
    public Unsafe getUnsafe(){
        //不允许直接调用
        //Unsafe unsafe = sun.misc.Unsafe.getUnsafe();
        try {
            //获取 Unsafe 内部的私有的实例化单例对象
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            //无视权限
            field.setAccessible(true);
            Unsafe unsafe = (Unsafe) field.get(null);
            log.info("{}", unsafe);
            return unsafe;
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 内存
     */
    @Test
    public void testMemory(){
        Unsafe unsafe = getUnsafe();
        //在 Unsafe 中可以直接申请一块内存：
        //需要传入一个 long 类型的参数，作为申请的内存的大小，单位为 byte
        //返回这块内存的 long 类型地址
        long memoryAddress = unsafe.allocateMemory(1*1024*1000);
        //Unsafe 申请的内存不在 jvm 管辖范围内，需要手动释放：
        //传入之前申请的内存的地址就可以释放该块内存了
        unsafe.freeMemory(memoryAddress);
        //注意，如果申请了内存，但是中途报错导致中断了代码执行，没有执行内存的释放，就出现了内存泄漏。所以为了保险起见，实际生产中尽量在 finally 区域里进行内存的释放操作。
        //还有一个重新分配内存的方法：

        //传入之前申请的内存的地址和一个 long 类型的参数作为新的内存的 byte 大小
        //此方法会释放掉之前地址的内存，然后重新申请一块符合要求大小的内存
        //如果之前那块内存上已经存在对象了，就会被拷贝到新的内存上
        long newMemoryAddress = unsafe.reallocateMemory(memoryAddress, 32);
    }

    /**
     * 存取对象
     */
    @Test
    public void testObject() throws Exception{
        Unsafe unsafe = getUnsafe();
        UnsafeBean bean = new UnsafeBean();

        //1 测试 staticInt
        //先通过变量名反射获取到该变量
        Field staticIntField = UnsafeBean.class.getDeclaredField("staticInt");
        //无视权限
        staticIntField.setAccessible(true);
        //staticFieldOffset(...) 方法能够获取到类中的 static 修饰的变量
        long staticIntAddress = unsafe.staticFieldOffset(staticIntField);
        //使用 put 方法进行值改变，需要传入其所在的 class 对象、内存地址和新的值
        unsafe.putInt(UnsafeBean.class,staticIntAddress,10);
        //使用 get 方法去获取值，需要传入其所在的 class 对象和内存地址
        int stiatcIntTest = unsafe.getInt(UnsafeBean.class,staticIntAddress);
        //此处输出为 10
        System.out.println(stiatcIntTest);

        //2 测试 staticString
        //基本流程相同，只是 put 和 get 方法换成了 getObject(...) 和 putObject(...)
        Field staticStringField = UnsafeBean.class.getDeclaredField("staticString");
        staticStringField.setAccessible(true);
        long staticStringAddress = unsafe.staticFieldOffset(staticStringField);
        unsafe.putObject(UnsafeBean.class,staticStringAddress,"static_string_2");
        String staticStringTest = (String)unsafe.getObject(UnsafeBean.class,staticStringAddress);
        //此处输出为 static_string_2
        System.out.println(staticStringTest);

        //3 测试 finalInt
        //基本流程相同，只是 staticFieldOffset(...) 方法换成了 objectFieldOffset(...) 方法
        Field finalIntField = UnsafeBean.class.getDeclaredField("finalInt");
        finalIntField.setAccessible(true);
        long finalIntAddress = unsafe.objectFieldOffset(finalIntField);
        //需要注意的是，虽然该变量是 final 修饰的，理论上是不可变的变量，但是 unsafe 是具有修改权限的
        unsafe.putInt(bean,finalIntAddress,10);
        int finalIntTest = unsafe.getInt(bean,finalIntAddress);
        //此处输出为 10
        System.out.println(finalIntTest);

        //4 测试 finalString
        Field finalStringField = UnsafeBean.class.getDeclaredField("finalString");
        finalStringField.setAccessible(true);
        long finalStringAddress = unsafe.objectFieldOffset(finalStringField);
        unsafe.putObject(bean,finalStringAddress,"final_string_2");
        String finalStringTest = (String)unsafe.getObject(bean,finalStringAddress);
        //此处输出为 final_string_2
        System.out.println(finalStringTest);

        //测试5 和 测试6 此处省略，因为和上述 final 部分的测试代码一模一样
    }

    public static class UnsafeBean{
        //测试1 测试 static 修饰的 int 类型的存取
        private static int staticInt = 5;
        //测试2 测试 static 修饰的 object 类型的存取
        private static String staticString = "static_string";
        //测试3 测试 final 修饰的 int 类型的存取
        private final int finalInt = 5;
        //测试4 测试 final 修饰的 object 类型的存取
        private final String finalString = "final_string";
        //测试5 测试一般的 int 类型的存取
        private int privateInt;
        //测试6 测试一般的 object 类型的存取
        private String privateString;
    }

    /**
     * 线程的挂起和恢复
     */
    @Test
    public void testParkAndUnpark(){
        Unsafe unsafe = getUnsafe();
        log.info("挂起:{}",Thread.currentThread().getId());

        Thread mainThread = Thread.currentThread();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.currentThread().sleep(10*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                unsafe.unpark(mainThread);
            }
        }).start();
        //该方法第二个参数为 long 类型对象，表示该线程准备挂起到的时间点
        //注意，此为时间点，而非时间，该时间点从 1970 年(即元年)开始
        //第一个参数为 boolean 类型的对象，用来表示挂起时间的单位，true 表示毫秒，false 表示纳秒
        unsafe.park(true, System.currentTimeMillis() + 60*1000);
        log.info("挂起结束:{}",Thread.currentThread().getId());
    }


    /**
     * CAS
     * Unsafe 中提供了一套原子化的判断和值替换 api，来看一下例子
     */
    @Test
    public void testCas() throws Exception{
        Unsafe unsafe = getUnsafe();
        //创建一个 Integer 对象，value 为 1
        Integer i = 1;
        //获取到内部变量 value，这个变量用于存放值
        Field valueField = Integer.class.getDeclaredField("value");

        //获取到内存地址
        long valueAddress = unsafe.objectFieldOffset(valueField);
        valueField.setAccessible(true);
        //该方法用户比较及替换值
        //第一个参数为要替换的对象本身，第二个参数为值的内存地址
        //第三个参数为变量的预期值，第四个参数为变量要换的值
        //如果变量目前的值等于预期值(第三个参数)，则会将变量的值换成新值(第四个参数)，返回 true
        //如果不等于预期，则不会改变，并返回 false
        boolean isOk = unsafe.compareAndSwapInt(i,valueAddress,1,5);
        //此处输出 true
        System.out.println(isOk);
        //此处输出 5
        System.out.println(i);
    }
}

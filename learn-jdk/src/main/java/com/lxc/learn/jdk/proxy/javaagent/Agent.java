package com.lxc.learn.jdk.proxy.javaagent;

import java.lang.instrument.Instrumentation;
import java.util.LinkedList;
import java.util.List;

/**
 * @Description
 * @Author lixianchun
 * @Date 2021/6/19
 **/
public class Agent {

    /**
     * -javaagent:path
     * javaagent 改变字节码的两种方式
     * 1.premain
     * 用于在启动时，类加载前定义类的TransFormer，在类加载的时候更新对应的类的字节码
     *
     * 2.agentmain
     * 用于在运行时进行类的字节码的修改，步骤整体分为两步
     * 注册类的TransFormer
     * 调用retransformClasses函数进行类的重加载
     * 运行时行字节码替换依赖于VMThread，VMThread是一个在虚拟机创建时生成的单例原生线程，这个线程能派生出其他线程。
     * 因此，在运行时执行字节码替换的时候需要在safepoint下执行，因此整体会触发stop-the-world。
     *
     * 3.实现方式
     * 需要实现的方法
     * Java Agent支持目标JVM启动时加载，也支持在目标JVM运行时加载，这两种不同的加载模式会使用不同的入口函数，如果需要在目标JVM启动的同时加载Agent，那么可以选择实现下面的方法：
     * [1] public static void premain(String agentArgs, Instrumentation inst);
     * [2] public static void premain(String agentArgs);
     * JVM将首先寻找[1]，如果没有发现[1]，再寻找[2]。
     * 如果希望在目标JVM运行时加载Agent，则需要实现下面的方法：
     * [1] public static void agentmain(String agentArgs, Instrumentation inst);
     * [2] public static void agentmain(String agentArgs);
     * 这两组方法的第一个参数AgentArgs是随同 “–javaagent”一起传入的程序参数，如果这个字符串代表了多个参数，就需要自己解析这些参数。inst是Instrumentation类型的对象，是JVM自动传入的，我们可以拿这个参数进行类增强等操作。
     *
     * 指定Main-Class
     * Agent需要打包成一个jar包，在ManiFest属性中指定“Premain-Class”或者“Agent-Class”,且需根据需求定义Can-Redefine-Classes和Can-Retransform-Classes：
     */


    public static void agentmain(String args, Instrumentation instrumentation) {
        System.out.println("agentmain: args, instrumentation" + args + ", " + instrumentation);
        try {
            List<Class> needRetransFormClasses = new LinkedList<>();
            Class[] loadedClass = instrumentation.getAllLoadedClasses();
            for (int i = 0; i < loadedClass.length; i++) {
                needRetransFormClasses.add(loadedClass[i]);
            }

            instrumentation.addTransformer(new TransformerByJavassist());
            instrumentation.retransformClasses(needRetransFormClasses.toArray(new Class[0]));
        } catch (Exception e) {

        }
    }

    public static void premain(String args, Instrumentation instrumentation) {
        System.out.println("premain: args, instrumentation" + args + ", " + instrumentation);
        instrumentation.addTransformer(new TransformerByJavassist());
    }

}

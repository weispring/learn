package com.lxc.learn.jdk.thread.threadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * todo ERROR
 *https://mp.weixin.qq.com/s?__biz=MzAxNjM2MTk0Ng==&mid=2247491177&idx=2&sn=86ec78a4c643f3e014b28e1bfea04c53&chksm=9bf4aedcac8327caa6c8e91d4f177e9cfa17a8b0f2c475308e615dca39820f661fedd159fcee&xtrack=1&scene=90&subscene=93&sessionid=1591934701&clicktime=1591934715&enterid=1591934715&ascene=1&devicetype=Windows+10+x64&version=62090070&nettype=WIFI&abtest_cookie=AAACAA%3D%3D&lang=zh_CN&exportkey=A0kcN3kt8dkCL%2FAolrYMluQ%3D&pass_ticket=my2uVv%2BbEefAvcf8WnZGGF5qqgmPzxkhfT2YEM4IgC03ykk0VlnXMExr9%2F4hkkf5&wx_header=1&key=5dd492e10b98b5a24d8f8bc7a4458be1a5d007f41fed0edea2be7a630e75bafe721aa3bb4325fad9af9a9550146e87564838908ab8cda4395c690e8e6cfa1529a305f6f33b3a39f4cf5bcfec1b79b506&uin=MjY2OTY1MjkwNg%3D%3D
 *
 * 可达对象(reachable object)是可以从任何活动线程的任何潜在的持续访问中的任何对象
 *
 * 由此可得，如果在执行的过程中，发生一次显式的线程切换，
 * 则会让编译器/代码生成器认为外层包装对象不可达
 *
 * 总结
 * 虽然GC只会回收不可达GC ROOT的对象，但是在编译器（没有明确指出，也可能是JIT）/
 * 代码生成器的优化下，可能会出现对象提前置null，或者线程切换导致的“提前对象不可达”的情况。
 *
 * 所以如果想在finalize方法里做些事情的话，一定在最后显示的引用一下对象
 *（toString/hashcode都可以），保持对象的可达性（reachable）
 * 综上所述，这种回收机制并不是JDK的bug，而算是一个优化策略，提前回收而已；
 * 但Executors.newSingleThreadExecutor的实现里通过finalize来自动关闭线程池的做法是有Bug的，
 * 在经过优化后可能会导致线程池的提前shutdown，从而导致异常。
 *
 * @Auther: lixianchun
 * @Date: 2020/6/27 10:46
 * @Description:
 */
@Slf4j
public class TestFinalize {

    private AtomicBoolean ctl = new AtomicBoolean();

    @Override
    protected void finalize() {
        log.info("{} was finalized!", this);
        ctl.set(true);
    }

    public static void main(String[] args) throws InterruptedException {
        TestFinalize a = new TestFinalize();
        System.out.println("Created " + a);
        log.info("Created : {}",a);

        for (int i = 0; i < 7; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        TestFinalize testFinalize = new TestFinalize();
                        testFinalize.test(testFinalize);
                        //a = null;
                        //模拟gc自动回收
                        for (int i = 0; i < 1_000_000_000; i++) {
                            if (i % 1_000_00 == 0)
                                System.gc();
                        }
                    }
                }
            }).start();
        }

        log.info("--");
        System.out.println("done.");
        //若后面无引用（或者引用前置为null），可能导致gc提前执行finalize方法，因此finalize一定要慎用
        //System.out.println(a);
    }

    public void test(TestFinalize finalize) {
        new Thread().start();
        for (int i = 0; i < 1_000_000_000; i++) {
            if(finalize.ctl.get()){
                throw new RuntimeException("reject!!!["+ctl.get()+"]");
            }
        }
    }
}

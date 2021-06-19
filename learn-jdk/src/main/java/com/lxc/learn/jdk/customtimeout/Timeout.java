package com.lxc.learn.jdk.customtimeout;

import java.util.concurrent.*;

/**
 * @Auther: lixianchun
 * @Date: 2020/5/22 22:45
 * @Description:
 */
public class Timeout {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ThreadPoolExecutor j = null;

        ExecutorService exec = Executors.newFixedThreadPool(1);

        Callable<Integer> call = new Callable<Integer>() {

            public Integer call() throws Exception {

                Thread.sleep(1000 * 5);// 耗时操作

                return 1;

            }

        };

        try {

            Future<Integer> future = exec.submit(call);

            int ret = future.get(1000 * 1, TimeUnit.MILLISECONDS); // 任务处理超时时间设为 1 秒

            System.out.println("任务执行结果：" + ret);

        } catch (TimeoutException e) {

            e.printStackTrace();

        } catch (Exception e) {

            e.printStackTrace();

        }

        exec.shutdown();

    }

}

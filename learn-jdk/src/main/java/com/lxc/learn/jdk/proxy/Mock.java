package com.lxc.learn.jdk.proxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.core.DebuggingClassWriter;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.LinkedList;

/**
 * @Auther: lixianchun
 * @Date: 2018/10/22 12:58
 * @Description:
 */
public class Mock {

    private static final Logger log = LoggerFactory.getLogger(Mock.class);
    private static LinkedList<Object> list = new LinkedList();

    public Mock() {
    }

    public static void push(Object resp) {
        list.add(resp);
    }

    public static void clear() {
        list.clear();
    }


    public static <T> T wrapper(T t) {
        if (t.getClass().getInterfaces() != null && t.getClass().getInterfaces().length > 0) {
            DynamicProxyHandlerJavaProxy dynamicProxyHandler = new DynamicProxyHandlerJavaProxy(t);
            return (T) Proxy.newProxyInstance(dynamicProxyHandler.getClass().getClassLoader(), t.getClass().getInterfaces(), dynamicProxyHandler);
        } else {
            DynamicProxyHandlerCglib handler = new DynamicProxyHandlerCglib();
            return (T) handler.getProxy(t);
        }
    }


    /**
     * 原理：重写被代理类实现代理，也就是继承
     */
    static class DynamicProxyHandlerCglib implements MethodInterceptor {
        private Enhancer enhancer;

        private DynamicProxyHandlerCglib() {
            this.enhancer = new Enhancer();
        }

        public Object getProxy(Object c) {
            this.enhancer.setSuperclass(c.getClass());
            this.enhancer.setCallback(this);
            return this.enhancer.create();//nextInstance 将自己放到threadLocal
        }

        @Override
        public Object intercept(Object obj, Method method, Object[] arg, MethodProxy proxy) throws Throwable {
            //log.info("invoke{}-{},", proxy.getClass(), method.getName());
            //Object object1 = method.invoke(obj,arg);  Object object2 = proxy.invoke(obj,arg); //只要执行这两个种的一个，会一直执行代理办法，无限递归下去。

            // list.poll();
            Object object = proxy.invokeSuper(obj, arg);//获取真实值

            //log.info("object=", object.toString());
            return object;
        }
    }


    /**
     * 实现原理: 实现被代理接口，达到代理的目的。
     */
    private static class DynamicProxyHandlerJavaProxy implements InvocationHandler {
        private Object t;

        DynamicProxyHandlerJavaProxy(Object t) {
            this.t = t;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) {
       /*     log.info("invoke{}-{},", proxy.getClass(), method.getName());
            Object object = list.poll();

            log.info("object=", object.toString());
            return object;*/
       return ((ITestProxy)t).sayHello();
        }
    }

    public static void main(String[] args) {
        //cglib 继承
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "D:\\wsrc");
        //接口实现
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles","true");

        Mock.push(new Integer(123456));


        log.info("测试");
        TestCglib testCglib = new TestCglib();
         testCglib = wrapper(testCglib);
         Object object = null;
         Long startCglib = System.currentTimeMillis();
         for (int i=0; i < 100000; i++){
             object = testCglib.test();
         }
         log.info("cglib : {} ", System.currentTimeMillis() - startCglib);

        ITestProxy value = Mock.wrapper(new ITestProxy() {
            @Override
            public Object sayHello() {
                return "测试";
            }
        });
        Long startProxy = System.currentTimeMillis();
        for (int i=0; i < 100000; i++){
            object = value.sayHello();
        }
        log.info("proxy : {} ", System.currentTimeMillis() - startProxy);
         log.info("::{}", object);
       /* String url = "";
        String param = "";
        Mock.push("123nhj");
        String result = httpWrapper(HttpClientUtil.class).sendPostRequest(url,param);

        log.info("result : {}",result);*/
    }



   /*

       public static <T> T httpWrapper(Class<T> t) {
        HttpDynamicProxyHandlerCglib handler = new HttpDynamicProxyHandlerCglib();
        return (T) handler.getProxy(t);
    }

   private class Handle implements InvocationHandler {
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            return "测试";
        }

    }


    static class HttpDynamicProxyHandlerCglib implements MethodInterceptor {
        private Enhancer enhancer;

        private HttpDynamicProxyHandlerCglib() {
            this.enhancer = new Enhancer();
        }

        public Object getProxy(Class<?> c) {
            this.enhancer.setSuperclass(c);
            this.enhancer.setCallback(this);
            return this.enhancer.create();
        }


        @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy){
            return list.poll();
        }
    }*/
}
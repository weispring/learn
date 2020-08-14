package com.lxc.learn.common.util.pdf;

import com.itextpdf.text.pdf.PdfReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.nio.charset.Charset;

/**
 * @author lixianchun
 * @description
 * @date 2020/8/13
 */
@Slf4j
public class Test {

    public static void main(String[] args) throws Exception {


        PdfReader reader = new PdfReader("C:\\Users\\vpclub\\Desktop\\rBAFJF80usSAMp9fAAHyMxjh49o261.pdf");
        int n = reader.getNumberOfPages();

        String a = new String("".getBytes());
    }


    public static <T> T wrapper(T t) {
        DynamicProxyHandlerCglib handler = new DynamicProxyHandlerCglib();
        return (T) handler.getProxy(t);
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
            log.info("invoke{}-{},", proxy.getClass(), method.getName());
            //Object object1 = method.invoke(obj,arg);  Object object2 = proxy.invoke(obj,arg); //只要执行这两个种的一个，会一直执行代理办法，无限递归下去。

            if (method.getName().equals("equalsArray")){
                return Boolean.valueOf("true");
            }

            // list.poll();
            Object object = proxy.invokeSuper(obj, arg);//获取真实值

            log.info("object=", object.toString());
            return object;
        }
    }
}
